import React from 'react';
import {Col, Row} from 'antd';
import {ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import type { FormType } from '@/services/common/typings';

interface FormBodyProps {
  formType?: FormType;
  readonly?: boolean;
}

const FormBody: React.FC<FormBodyProps> = (props) => {

  console.log(props)

  return (
    <>
      <ProFormText name="id" label="角色id" hidden={true} />
      <Row gutter={24}>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="name"
            label="角色名称"
            rules={[{ required: true, message: '请输入角色名称' }]}
          />
        </Col>
        <Col xl={12} lg={12} md={24}>
          <ProFormText
            width="md"
            name="code"
            label="角色代码"
            rules={[{ required: true, message: '请输入角色代码' }]}
          />
        </Col>
        <Col span={24}>
          <ProFormTextArea name="description" label="描述" />
        </Col>
        {/*<Col span={24}>
          <Form.Item name="menuIds" label="菜单分配">
            <FormTree treeData={menuTree} />
          </Form.Item>
        </Col>
        <Col span={24}>
          <Form.Item name="resourceIds" label="资源分配">
            <FormTree treeData={resourceTree} />
          </Form.Item>
        </Col>*/}
      </Row>
    </>
  );
};

export default FormBody;
