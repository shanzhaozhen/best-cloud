import React from 'react';
import {Col, message, Row, Skeleton} from 'antd';
import ProForm, {
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { useRequest } from 'umi';
import {getCurrentUserInfo, updateUserInfo} from "@/services/user";
import {ProFormDatePicker, ProFormItem} from "@ant-design/pro-components";
import AvatarView from "@/components/AvatarView";
import type { UserInfoForm } from '@/services/typings';


const BaseView: React.FC = () => {

  const {data, loading} = useRequest(async () => {
    return getCurrentUserInfo();
  });

  const handleFinish = async (fields: UserInfoForm) => {
    const hide = message.loading('更新中...');
    try {
      await updateUserInfo(fields);
      hide();
      message.success('更新基本信息成功！');
      return true;
    } catch (error) {
      hide();
      message.error('更新基本信息失败，请重试!');
      return false;
    }
  };

  return (
    <div>
      <Skeleton loading={loading}>
        <ProForm
          layout="vertical"
          onFinish={handleFinish}
          submitter={{
            searchConfig: {
              submitText: '更新基本信息',
            },
            render: (_, dom) => dom[1],
          }}
          initialValues={{
            ...(data ? data : {}),
          }}
          requiredMark
        >
          <Row>
            <Col xl={12} lg={12} md={24}>
              <Row gutter={24}>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText
                    width="md"
                    name="name"
                    label="姓名"
                    placeholder="请输入用户名"
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText
                    width="md"
                    name="nickname"
                    label="昵称"
                    placeholder="请输入昵称"
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormSelect
                    width="md"
                    name="sex"
                    label="性别"
                    options={[
                      { label: '男', value: 0 },
                      { label: '女', value: 1 },
                    ]}
                    placeholder="请选择性别"
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormDatePicker
                    width="md"
                    name="birthday"
                    label="生日"
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText
                    width="md"
                    name="phoneNumber"
                    label="手机号码" rules={[{ pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号' }]}
                  />
                </Col>
                <Col xl={24} lg={24} md={24}>
                  <ProFormText
                    width="md"
                    name="email"
                    label="邮箱"
                    rules={[{ type: 'email', message: '请填入正确的邮箱' }]}
                  />
                </Col>
              </Row>
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormItem name="avatar">
                <AvatarView />
              </ProFormItem>
            </Col>
          </Row>
          <Row gutter={24}>
            <Col xl={24} lg={24} md={24}>
              <ProFormTextArea name="introduction" label="个人介绍" />
            </Col>
          </Row>
        </ProForm>
      </Skeleton>
    </div>
  );
};

export default BaseView;
