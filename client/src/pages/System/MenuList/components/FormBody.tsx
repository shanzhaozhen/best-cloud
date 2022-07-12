import React from 'react';
import {Col, Row} from 'antd';
import {
  ProFormDigit,
  ProFormSelect,
  ProFormSwitch,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect
} from '@ant-design/pro-form';
import type { FormType } from '@/services/common/typings';
import IconOption from "@/components/IconOption";
import {getMenuTree} from "@/services/uaa/menu";
import type {MenuVO} from "@/services/uaa/type/menu";

interface FormBodyProps {
  formType?: FormType;
  readonly?: boolean;
}

const loopMenuData = (menuData: MenuVO[]): any =>
  menuData.map(({ id, name, path, children }) => ({
    title: name + (path ? `(${path})` : ''),
    value: id,
    children: children && loopMenuData(children),
  }));

const FormBody: React.FC<FormBodyProps> = (props) => {

  console.log(props)

  return (
    <>
      <ProFormText name="id" label="菜单id" hidden={true} />
      <Row gutter={24}>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="name"
            label="菜单名称"
            rules={[{ required: true, message: '请输入菜单名称' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormText width="md" name="locale" label="菜单名称（本地化）" />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="path"
            label="菜单路径"
            rules={[{ required: true, message: '请输入菜单路径' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormTreeSelect
            width="md"
            name="pid"
            label="上级菜单"
            allowClear
            rules={[
              ({ getFieldValue }) => ({
                validator: async (rule, value) => {
                  const menuId = getFieldValue('id');
                  if (value && value === menuId) {
                    throw new Error('上级菜单不能选择自己');
                  }
                },
              }),
            ]}
            request={async () => {
              try {
                const { data } = await getMenuTree();
                if (data) {
                  return data ? loopMenuData(data) : []
                }
              } catch (e) {
                return [];
              }
            }}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormSelect
            width="md"
            name="icon"
            label="图标"
            showSearch
            fieldProps={{
              filterOption: (inputValue, option) => !!(option?.value && String(option?.value).indexOf(inputValue) > -1)
            }}
            options={IconOption} />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormDigit width="md" name="priority" label="排序等级"  min={0} initialValue={0} />
        </Col>
        <Col xl={12} md={12} sm={24}>
          <ProFormSwitch
            name="hideInMenu"
            label="菜单是否隐藏"
            checkedChildren="是"
            unCheckedChildren="否"
          />
        </Col>
        <Col xl={12} md={12} sm={24}>
          <ProFormSwitch
            name="hideChildrenInMenu"
            label="隐藏子节点"
            checkedChildren="是"
            unCheckedChildren="否"
          />
        </Col>
        <Col span={24}>
          <ProFormTextArea name="props" label="参数" />
        </Col>
        <Col span={24}>
          <ProFormTextArea name="description" label="菜单描述" />
        </Col>
      </Row>
    </>
  );
};

export default FormBody;
