import React from 'react';
import {Col, Row} from 'antd';
import {
  ProFormDigit,
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect
} from '@ant-design/pro-form';
import type { FormType } from '@/services/common/typings';
import {getPermissionTree} from "@/services/uaa/permission";
import type {PermissionVO} from "@/services/uaa/type/permission";

interface FormBodyProps {
  formType?: FormType;
  readonly?: boolean;
}

const loopPermissionData = (permissionData: PermissionVO[]): any =>
  permissionData.map(({ id, name, path, children }) => ({
    title: name + (path ? `(${path})` : ''),
    value: id,
    children: children && loopPermissionData(children),
  }));

const FormBody: React.FC<FormBodyProps> = (props) => {

  const { formType } = props;

  return (
    <>
      <ProFormText name="id" label="权限id" hidden={true} />
      <Row gutter={24}>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="name"
            label="资源名称"
            rules={[{ required: true, message: '请输入资源名称' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormSelect
            width="md"
            name="type"
            label="资源类型"
            options={[
              { label: '分类', value: 0 },
              { label: 'API', value: 1 },
            ]}
            placeholder="请选择资源类型"
            rules={[{ required: true, message: '请选择资源类型' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="path"
            label="资源路由"
            rules={[
              ({ getFieldValue }) => ({
                required: getFieldValue('type') === 1,
                message: '请输入资源路由',
              }),
            ]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormTreeSelect
            width="md"
            name="pid"
            label="上级权限"
            allowClear
            rules={[
              ({ getFieldValue }) => ({
                validator: async (rule, value) => {
                  const permissionId = getFieldValue('id');
                  if (value && value === permissionId) {
                    throw new Error('上级权限不能选择自己');
                  }
                },
              }),
            ]}
            request={async () => {
              try {
                const { data } = await getPermissionTree();
                if (data) {
                  return data ? loopPermissionData(data) : []
                }
              } catch (e) {
                return [];
              }
            }}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          {
            formType === 'update' ? (
              <ProFormDigit width="md" name="priority" label="排序等级"  min={0} />
            ) : (
              <ProFormDigit width="md" name="priority" label="排序等级"  min={0} initialValue={0} />
            )
          }
        </Col>
        <Col span={24}>
          <ProFormTextArea name="description" label="权限描述" />
        </Col>
      </Row>
    </>
  );
};

export default FormBody;
