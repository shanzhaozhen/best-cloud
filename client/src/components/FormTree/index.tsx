import React, { useState } from 'react';
import { Tree } from 'antd';
import type { DataNode } from 'rc-tree/lib/interface';

interface FormTreeProps {
  value?: any;
  checkStrictly?: boolean;
  treeData?: DataNode[];
  onChange?: (value: any) => void;
}

const FormTree: React.FC<FormTreeProps> = (props) => {
  const { value, checkStrictly, treeData, onChange } = props;

  const [treeCheckedKeys, setTreeCheckedKeys] = useState<React.Key[]>(value);

  const triggerChange = (changeValue: number[]) => {
    onChange?.(changeValue);
  };

  const loopTreeCheck = (children: any[]): number[] => {
    let closeNode: number[] = [];
    if (children) {
      children.forEach((item: any) => {
        closeNode.push(item.key);
        const tmp = loopTreeCheck(item.children);
        closeNode = closeNode.concat(tmp);
      });
    }
    return closeNode;
  };

  const getTreeCheckParentKey = (node: { children: any[] | undefined; pos: string }): number[] => {
    // 通过pos获取父节点
    const { pos } = node;
    const posSplit = pos.split('-');
    const depth = posSplit.length;
    const parentKeys = [];

    if (depth > 2 && treeData) {
      let tree: any = treeData;

      for (let i = 1; i < depth - 1; i += 1) {
        parentKeys.push(tree[posSplit[i]].key);
        if (i === depth - 2) {
          break;
        }
        tree = tree[posSplit[i]].children;

        if (!tree) {
          break;
        }
      }
    }
    return parentKeys;
  };

  const onCheck = (checkedKeysValue: React.Key[] | any, info: any) => {
    let values = checkedKeysValue.checked;

    if (checkStrictly) {
      if (info.checked) {
        // 选中子节点同时激活父节点
        const parentKeys = getTreeCheckParentKey(info.node);
        values = [...new Set(values.concat(parentKeys))];
      } else {
        // 取消父节点同时取消子节点
        const closeNode = loopTreeCheck(info.node.children);
        values = values.filter((item: number) => closeNode.indexOf(item) === -1);
      }
    }

    setTreeCheckedKeys(values);
    triggerChange(values);
  };

  return (
    <Tree
      checkable
      checkStrictly={checkStrictly}
      onCheck={onCheck}
      checkedKeys={treeCheckedKeys}
      treeData={treeData}
    />
  );
};

FormTree.defaultProps = {
  checkStrictly: true,
};

export default FormTree;
