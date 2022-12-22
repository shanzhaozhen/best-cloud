import React, {useEffect, useRef, useState} from 'react';
import {message, Skeleton} from 'antd';
import ProForm, {
  ProFormSelect,
  ProFormText,
  ProFormTextArea,
} from '@ant-design/pro-form';
import { useRequest } from 'umi';
import {getCurrentUserInfo, updateUserInfo} from "@/services/user";
import {ProFormDatePicker, ProFormInstance} from "@ant-design/pro-components";
import AvatarView from "@/components/AvatarView";
import type { UserInfoForm } from '@/services/typings';
import {useEmotionCss} from "@ant-design/use-emotion-css";
import {resourcesPath} from "../../../../config/constants";


const BaseView: React.FC = () => {

  const baseViewClassName = useEmotionCss(({token}) => ({
    [`@media screen and (max-width: ${token.screenXL})`]: {
      flexDirection: 'column-reverse',
    },
    display: 'flex',
    paddingTop: '12px'
  }));

  const leftClassName = useEmotionCss(() => ({
    minWidth: '224px',
    maxWidth: '448px',
  }));

  const rightClassName = useEmotionCss(({ token }) => ({
    [`@media screen and (max-width: ${token.screenXL})`]: {
      display: 'flex',
      flexDirection: 'column',
      alignItems: 'center',
      maxWidth: '448px',
      padding: '20px',
    },
    flex: 1,
    paddingLeft: '104px'
  }));

  const formRef = useRef<ProFormInstance>();
  const [avatarUrl, setAvatarUrl] = useState(`${resourcesPath}default-avatar.png`);

  const {data, loading} = useRequest(async () => {
    return getCurrentUserInfo();
  });

  useEffect(() => {
    if (data?.avatar) {
      setAvatarUrl(data.avatar);
    }
  }, [loading])

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
        <div className={baseViewClassName}>
          <div className={leftClassName}>
            <ProForm
              formRef={formRef}
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
              <ProFormText width="md" name="avatar" label="头像" hidden={true} />
              <ProFormText width="md" name="name" label="姓名" placeholder="请输入用户名"/>
              <ProFormText width="md" name="nickname" label="昵称" placeholder="请输入昵称"/>
              <ProFormSelect
                width="md"
                name="sex"
                label="性别"
                options={[
                  {label: '男', value: 0},
                  {label: '女', value: 1},
                ]}
                placeholder="请选择性别"
              />
              <ProFormDatePicker width="md" name="birthday" label="生日"/>
              <ProFormText width="md" name="phoneNumber" label="手机号码"
                           rules={[{pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号'}]}/>
              <ProFormText width="md" name="email" label="邮箱" rules={[{type: 'email', message: '请填入正确的邮箱'}]}/>
              <ProFormTextArea name="introduction" label="个人介绍" />
            </ProForm>
          </div>
          <div className={rightClassName}>
            <AvatarView
              value={avatarUrl}
              onChange={(avatarUrl: string) => {
                formRef.current?.setFieldValue('avatar', avatarUrl);
                setAvatarUrl(avatarUrl);
              }}
            />
          </div>
        </div>
      </Skeleton>
    </div>
  );
};

export default BaseView;
