import React, {Dispatch, MutableRefObject, SetStateAction, useEffect, useState} from "react";
import type {DataNode} from "antd/lib/tree";
import {getDepartmentByPid, getDepartmentList} from "@/services/uaa/department";
import Search from "antd/es/input/Search";
import {Divider, Tree} from "antd";
import type {DepartmentVO} from "@/services/uaa/type/department";
import type {ActionType} from "@ant-design/pro-table";


interface DepartmentTreeProps {
  setSelectDepartment: Dispatch<SetStateAction<DepartmentVO | undefined>>;
  userRelateActionRef: MutableRefObject<ActionType | undefined>;
}

const DepartmentTree: React.FC<DepartmentTreeProps> = (props) => {

  const { setSelectDepartment, userRelateActionRef } = props;

  const [departmentTreeData, setDepartmentTreeData] = useState<DataNode[]>([]);

  /**
   * 更新树节点
   * @param list
   * @param key
   * @param children
   */
  const updateTreeData = (list: DataNode[], key: number, children: DataNode[] | null): DataNode[] => (
    list.map((node) => {
      if (node.key === key) {
        return {
          ...node,
          children: children && children.length > 0 ? children : undefined,
        };
      }
      if (node.children && node.children.length > 0) {
        return {
          ...node,
          children: updateTreeData(node.children, key, children),
        };
      }
      return { ...node };
    })
  );

  /**
   * 加载部门数据
   * @param key
   */
  const onLoadDepartmentData = async ({ key }: any) => {
    const { data } = await getDepartmentByPid(key);

    if (data && data.length > 0) {
      const list: DataNode[] = data.map(item => ({
        title: item.name,
        key: item.id || 0,
        dataRef: { ...item }
      }))

      if (key) {
        setDepartmentTreeData((department) =>
          updateTreeData(department, key, list)
        );
      } else {
        setDepartmentTreeData(list);
      }
    }
  }

  /**
   * 刷新部门数据
   */
  const refreshDepartmentData = () => {
    getDepartmentByPid().then(({ data }) => {
      setDepartmentTreeData(data && data.length > 0 ? data.map(item => ({
        key: item.id || 0,
        title: item.name,
        dataRef: { ...item }
      })) : [])
    }).catch(() => {
      setDepartmentTreeData([])
    })
  }

  /**
   * 搜索部门
   * @param keyword 关键字
   */
  const onSearchDepartment = async (keyword?: string) => {
    setSelectDepartment(undefined);
    if (keyword) {
      const { data } = await getDepartmentList(keyword);
      setDepartmentTreeData(data && data.length > 0 ? data.map(item => ({
        key: item.id || 0,
        title: item.name,
        dataRef: { ...item }
      })) : [])
    } else {
      refreshDepartmentData();
    }
  }

  /**
   * 选中部门事件
   * @param key
   * @param info
   */
  const onSelectDepartment = (key: any, info: any) => {
    console.log(key)
    if (key && key.length > 0) {
      setSelectDepartment(info.selectedNodes[0].dataRef);
      userRelateActionRef.current?.reloadAndRest?.();
    } else {
      setSelectDepartment(undefined);
    }
  }

  useEffect(() => {
    refreshDepartmentData();
  }, []);

  return (
    <>
      <Search size="small" placeholder="请输入部门名称" onSearch={onSearchDepartment}/>
      <Divider style={{ margin: '12px 0' }}/>
      <Tree
        showLine
        loadData={onLoadDepartmentData}
        treeData={departmentTreeData}
        onSelect={onSelectDepartment}
      />
    </>
  )
}

export default DepartmentTree;
