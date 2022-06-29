import React from 'react';
import {Col, Row, Tabs} from 'antd';
import {ProFormDatePicker, ProFormSelect, ProFormSwitch, ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import AvatarView from "@/components/AvatarView";
import ProFormItem from '@ant-design/pro-form/lib/components/FormItem';
import type { FormType } from '@/services/common/typings';
// import { getAllRoles } from '@/services/role/role';
// import type { RoleVO } from '@/services/role/typings';
// import FormTreeSelect from '@/components/FormTreeSelect';
// import { useDepartmentTree } from '@/utils/department';

interface FormBodyProps {
  formType?: FormType;
  disable?: boolean;
  readonly?: boolean;
}



const FormBody: React.FC<FormBodyProps> = (props) => {
  const { disable, formType } = props;

  // const departmentTree = useDepartmentTree();

  return (
    <>
      <ProFormText name="id" label="用户id" hidden={true} />
      <ProFormText name={['userInfo', 'id']} label="用户信息id" hidden={true} />
      <Tabs defaultActiveKey="account">
        <Tabs.TabPane tab="账户信息" key="account" forceRender>
          <Row gutter={24}>
            <Col xl={12} lg={12} md={24}>
              <ProFormText
                width="md"
                name="username"
                label="用户名"
                disabled={disable}
                // fieldProps={{ autoComplete: 'off' }}
                placeholder="请输入用户名"
                rules={[{ required: true, message: '请输入用户名' }]}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormText width="md" name={['userInfo', 'name']} label="姓名" />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormText.Password
                width="md"
                label="密码"
                name="password"
                fieldProps={{ autoComplete: 'new-password' }}
                placeholder={formType === 'create' ? '请输入密码' : '留空则不更新密码'}
                rules={[
                  {
                    required: formType === 'create',
                    validator: async (rule, value) => {
                      // 编辑模式时不为空才判断
                      if (formType === 'update' && !value) return;

                      // 密码不能小于六位，至少含字母、数字、特殊字符其中的2种！
                      const regExp = new RegExp(
                        /^.*(?=.{6,16})(?=.*\d)(?=.*[A-Za-z])(?=.*[/\\?.,~!@#$%^&*()_+={}|:<>[\]]).*$/,
                      );
                      if (!regExp.test(value)) {
                        throw new Error('密码长度为6-20位，且含字母、数字、特殊字符！');
                      }
                    },
                  },
                ]}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormText.Password
                width="md"
                label="确认密码"
                name="re-password"
                placeholder={formType === 'create' ? '请再次输入密码' : '留空则不更新密码'}
                rules={[
                  { required: formType === 'create' },
                  ({ getFieldValue }) => ({
                    validator: async (rule, value) => {
                      const password = getFieldValue('password');

                      // 编辑状态时，如果密码为空不进行校验
                      if (formType === 'update' && !password) return;

                      if (!value) {
                        throw new Error('确认密码不能为空');
                      }

                      if (password !== value) {
                        throw new Error('两次输入的密码不一致');
                      }
                    },
                  }),
                ]}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormText width="md" name={['userInfo', 'nickname']} label="昵称" placeholder="请输入用户名" />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name={['userInfo', 'sex']}
                label="性别"
                options={[
                  { label: '男', value: 0 },
                  { label: '女', value: 1 },
                ]}
                placeholder="请选择性别"
              />
            </Col>
            {/*<Col xl={12} lg={12} md={24}>
          <Form.Item
            name="depId"
            label="所属部门"
            rules={[{ required: false, message: '请选择所属部门' }]}
          >
            <FormTreeSelect
              treeData={departmentTree}
              style={{ width: 328 }}
              placeholder="请选择所属部门"
            />
          </Form.Item>
        </Col>*/}
            {/*<Col span={24}>
          <ProFormSelect
            mode="multiple"
            name="roleIds"
            label="角色"
            params={{}}
            showSearch={false}
            placeholder="请选择用户角色"
            request={async () => {
              const { data } = await getAllRoles();
              if (data) {
                return data.map((item: RoleVO) => ({
                  label: item.name,
                  value: item.id,
                }));
              }
              return [];
            }}
          />
        </Col>*/}
            <Col xl={6} md={12} sm={24}>
              <ProFormSwitch
                name="accountNonExpired"
                label="是否过期"
                checkedChildren="未过期"
                unCheckedChildren="已过期"
                fieldProps={{ defaultChecked: true }}
              />
            </Col>
            <Col xl={6} md={12} sm={24}>
              <ProFormSwitch
                name="accountNonLocked"
                label="是否锁定"
                checkedChildren="开启"
                unCheckedChildren="锁定"
                fieldProps={{ defaultChecked: true }}
              />
            </Col>
            <Col xl={6} md={12} sm={24}>
              <ProFormSwitch
                name="credentialsNonExpired"
                label="密码过期"
                checkedChildren="未过期"
                unCheckedChildren="已过期"
                fieldProps={{ defaultChecked: true }}
              />
            </Col>
            <Col xl={6} md={12} sm={24}>
              <ProFormSwitch
                name="enabled"
                label="是否禁用"
                checkedChildren="可用"
                unCheckedChildren="禁用"
                fieldProps={{ defaultChecked: true }}
              />
            </Col>
          </Row>
        </Tabs.TabPane>
        <Tabs.TabPane tab="用户资料" key="detail">
          <Row>
            <Col xl={12} lg={12} md={24}>
              <Row gutter={24}>
                <Col xl={24} lg={24} md={24}>
                  <ProFormDatePicker width="md" name={['userInfo', 'birthday']} label="生日" />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText
                    width="md"
                    name={['userInfo', 'phoneNumber']}
                    label="手机号码" rules={[{ type: 'number', pattern: /^1[3-5|7-9][0-9]\d{8}$/, message: '请输入正确的手机号' }]}
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText width="md" name={['userInfo', 'email']} label="邮箱" rules={[{ type: 'email', message: '请填入正确的邮箱' }]} />
                </Col>
              </Row>
            </Col>
            <Col xl={12} lg={12} md={24}>
              {/*<ProFormText width="md" name={['userInfo', 'email']} label="头像" />*/}
              <ProFormItem name={['userInfo', 'avatar']}>
                <AvatarView />
              </ProFormItem>
            </Col>
          </Row>

          <Row gutter={24}>
            <Col xl={24} lg={24} md={24}>
              <ProFormTextArea name={['userInfo', 'introduction']} label="个人介绍" />
            </Col>
          </Row>
        </Tabs.TabPane>
      </Tabs>
    </>
  );
};

FormBody.defaultProps = {
  disable: false,
};

export default FormBody;
