import React, {Dispatch, SetStateAction, useState} from 'react';
import {Col, List, message, Row, Skeleton} from 'antd';
import {ModalForm, ProFormCaptcha, ProFormText} from "@ant-design/pro-components";
import {changePassword, getSecurityInfo} from "@/services/user";
import type {ChangePasswordForm} from "@/services/typings";
import {FormattedMessage, useIntl, useRequest} from "@@/exports";
import {LockOutlined, MobileOutlined} from "@ant-design/icons";
import {getCaptcha} from "@/services/login";

type Unpacked<T> = T extends (infer U)[] ? U : T;

const passwordStrength = {
  strong: <span className="strong">强</span>,
  medium: <span className="medium">中</span>,
  weak: <span className="weak">弱 Weak</span>,
};

type PasswordChangeModalProps = {
  changePasswordModalOpen: boolean,
  handleChangePasswordModalOpen: Dispatch<SetStateAction<boolean>>;
}

type BindPhoneModalProps = {
  bindPhoneModalOpen: boolean,
  handleBindPhoneModalOpen: Dispatch<SetStateAction<boolean>>;
}

const PasswordChangeModal: React.FC<PasswordChangeModalProps> = (props) => {
  const { changePasswordModalOpen, handleChangePasswordModalOpen } = props

  const handleChangePassword = async (fields: ChangePasswordForm) => {
    const hide = message.loading('修改中...');
    try {
      await changePassword(fields);
      hide();
      message.success('修改密码成功！');
      return true;
    } catch (error) {
      hide();
      message.error('修改密码失败，请重试!');
      return false;
    }
  };

  return (
    <>
      <ModalForm
        title="密码修改"
        width="368px"
        open={changePasswordModalOpen}
        modalProps={{ destroyOnClose: true }}
        onOpenChange={handleChangePasswordModalOpen}
        onFinish={handleChangePassword}
      >
        <Row gutter={24}>
          <Col xl={24} lg={24} md={24}>
            <ProFormText.Password
              width="md"
              label="原密码"
              name="oldPassword"
              placeholder="请再次输入密码"
              fieldProps={{ autoComplete: 'oldPassword' }}
              rules={[{ required: true, message: "请输入原密码" }]}
            />
          </Col>
          <Col xl={24} lg={24} md={24}>
            <ProFormText.Password
              width="md"
              label="新密码"
              name="newPassword"
              fieldProps={{ autoComplete: 'newPassword' }}
              placeholder="请输入密码"
              rules={[
                {
                  required: true,
                  validator: async (rule, value) => {
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
          <Col xl={24} lg={24} md={24}>
            <ProFormText.Password
              width="md"
              label="确认密码"
              name="rePassword"
              placeholder="请再次输入密码"
              rules={[
                { required: true },
                ({ getFieldValue }) => ({
                  validator: async (rule, value) => {
                    const password = getFieldValue('newPassword');
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
        </Row>
      </ModalForm>
    </>
  )
}

const BindPhoneModal: React.FC<BindPhoneModalProps> = (props) => {
  const { bindPhoneModalOpen, handleBindPhoneModalOpen } = props

  const intl = useIntl();

  const handleBindPhone = async (fields: ChangePasswordForm) => {
    const hide = message.loading('修改中...');
    try {
      await changePassword(fields);
      hide();
      message.success('修改密码成功！');
      return true;
    } catch (error) {
      hide();
      message.error('修改密码失败，请重试!');
      return false;
    }
  };

  return (
    <>
      <ModalForm
        title="绑定手机"
        width="368px"
        open={bindPhoneModalOpen}
        modalProps={{ destroyOnClose: true }}
        onOpenChange={handleBindPhoneModalOpen}
        onFinish={handleBindPhone}
      >
        <Row gutter={24}>
          <Col xl={24} lg={24} md={24}>
            <ProFormText
              fieldProps={{
                size: 'large',
                prefix: <MobileOutlined/>,
              }}
              width="md"

              name="phone"
              placeholder={intl.formatMessage({
                id: 'pages.login.phoneNumber.placeholder',
                defaultMessage: '手机号',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.phoneNumber.required"
                      defaultMessage="请输入手机号！"
                    />
                  ),
                },
                {
                  pattern: /^1\d{10}$/,
                  message: (
                    <FormattedMessage
                      id="pages.login.phoneNumber.invalid"
                      defaultMessage="手机号格式错误！"
                    />
                  ),
                },
              ]}
            />
          </Col>
          <Col xl={24} lg={24} md={24}>
            <ProFormCaptcha
              width="md"
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined/>,
              }}
              captchaProps={{
                size: 'large',
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.captcha.placeholder',
                defaultMessage: '请输入验证码',
              })}
              captchaTextRender={(timing, count) => {
                if (timing) {
                  return `${count} ${intl.formatMessage({
                    id: 'pages.getCaptchaSecondText',
                    defaultMessage: '获取验证码',
                  })}`;
                }
                return intl.formatMessage({
                  id: 'pages.login.phoneLogin.getVerificationCode',
                  defaultMessage: '获取验证码',
                });
              }}
              phoneName="phone"
              name="captcha"
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.captcha.required"
                      defaultMessage="请输入验证码！"
                    />
                  ),
                },
              ]}
              onGetCaptcha={async (phone) => {
                try {
                  await getCaptcha({phone});
                  message.success('验证码已通过短信发送至您的手机，请查收！');
                } catch (error) {
                  message.error('获取验证码错误！');
                  throw new Error("获取验证码错误")
                }
              }}
            />
          </Col>
        </Row>
      </ModalForm>
    </>
  )
}


const SecurityView: React.FC = () => {
  const [changePasswordModalOpen, handleChangePasswordModalOpen] = useState<boolean>(false);
  const [bindPhoneModalOpen, handleBindPhoneModalOpen] = useState<boolean>(false);

  const {data: securityInfo, loading} = useRequest(async () => {
    return getSecurityInfo();
  });

  const getData = () => [
    {
      title: '账户密码',
      description: (
        <>
          当前密码强度：
          {passwordStrength.strong}
        </>
      ),
      actions: [<a key="Modify" onClick={() => handleChangePasswordModalOpen(true)}>修改</a>],
    },
    securityInfo?.phone ? ({
      title: '密保手机',
      description: `已绑定手机：${securityInfo.phone}`,
      actions: [<a key="Modify" onClick={() => handleBindPhoneModalOpen(true)}>修改</a>],
    }) : ({
      title: '密保手机',
      description: `未绑定手机`,
      actions: [<a key="Modify" onClick={() => handleBindPhoneModalOpen(true)}>绑定</a>],
    }),
    // {
    //   title: '密保问题',
    //   description: '未设置密保问题，密保问题可有效保护账户安全',
    //   actions: [<a key="Set">设置</a>],
    // },
    // {
    //   title: '备用邮箱',
    //   description: `已绑定邮箱：ant***sign.com`,
    //   actions: [<a key="Modify">修改</a>],
    // },
    // {
    //   title: 'MFA 设备',
    //   description: '未绑定 MFA 设备，绑定后，可以进行二次确认',
    //   actions: [<a key="bind">绑定</a>],
    // },
  ];

  const data = getData();
  return (
    <>
      <Skeleton loading={loading}>
        <List<Unpacked<typeof data>>
          itemLayout="horizontal"
          dataSource={data}
          renderItem={(item) => (
            <List.Item actions={item.actions}>
              <List.Item.Meta title={item.title} description={item.description} />
            </List.Item>
          )}
        />
        <PasswordChangeModal
          changePasswordModalOpen={changePasswordModalOpen}
          handleChangePasswordModalOpen={handleChangePasswordModalOpen}
        />
        <BindPhoneModal
          bindPhoneModalOpen={bindPhoneModalOpen}
          handleBindPhoneModalOpen={handleBindPhoneModalOpen}
        />
      </Skeleton>
    </>
  );
};

export default SecurityView;
