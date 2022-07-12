import React from 'react';
import {Col, Row} from 'antd';
import {
  ProFormDigit,
  ProFormText,
  ProFormTextArea,
  ProFormTreeSelect
} from '@ant-design/pro-form';
import type { FormType } from '@/services/common/typings';
import {getDepartmentTree} from "@/services/uaa/department";
import type {DepartmentVO} from "@/services/uaa/type/department";

interface FormBodyProps {
  formType?: FormType;
  readonly?: boolean;
}

const loopDepartmentData = (departmentData: DepartmentVO[]): any =>
  departmentData.map(({ id, name, children }) => ({
    title: name,
    value: id,
    children: children && loopDepartmentData(children),
  }));

const FormBody: React.FC<FormBodyProps> = () => {

  return (
    <>
      <ProFormText name="id" label="部门id" hidden={true} />
      <Row gutter={24}>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="name"
            label="部门名称"
            rules={[{ required: true, message: '请输入部门名称' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="code"
            label="部门编码"
            rules={[{ required: true, message: '请输入部门编码' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormTreeSelect
            width="md"
            name="pid"
            label="上级部门"
            allowClear
            rules={[
              ({ getFieldValue }) => ({
                validator: async (rule, value) => {
                  const departmentId = getFieldValue('id');
                  if (value && value === departmentId) {
                    throw new Error('上级部门不能选择自己');
                  }
                },
              }),
            ]}
            request={async () => {
              try {
                const { data } = await getDepartmentTree();
                if (data) {
                  return data ? loopDepartmentData(data) : []
                }
              } catch (e) {
                return [];
              }
            }}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormDigit width="md" name="priority" label="排序等级" min={0} initialValue={0} />
        </Col>
        <Col span={24}>
          <ProFormTextArea name="description" label="部门描述" />
        </Col>
      </Row>
    </>
  );
};

export default FormBody;
