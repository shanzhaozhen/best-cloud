import React, {useState} from 'react';
import {ProCard, ProForm, ProFormText} from "@ant-design/pro-components";
import {Button, message, Tabs} from "antd";
import {FormattedMessage, useIntl} from "../../.umi/exports";
import {
  LockOutlined, MobileOutlined,
  UserOutlined,
} from "@ant-design/icons";
import {ProFormCaptcha, ProFormCheckbox} from "@ant-design/pro-form";
import {getFakeCaptcha} from "../../services/login";
import styles from './index.less';
import {bindAccount} from "../../services/social";


const SocialBind: React.FC = () => {

  const [type, setType] = useState<string>('account');

  const intl = useIntl();

  const handleSubmit = async (values: any) => {
    const hide = message.loading('登陆中...');
    console.log(values)
    try {
      const { data } = await bindAccount(values);
      console.log(data)
      message.success("绑定成功！");
      window.location.href = '/';
    } catch (error) {
      // @ts-ignore
      message.error(error?.response.data.message || "绑定失败！");
    } finally {
      hide();
    }
  };

  return (
    <ProCard>
      <div className={styles.bindTop}>
        {/*<h1>账号绑定</h1>*/}
      </div>
      <div className={styles.bindMain}>
        <ProForm
          name="consent-form"
          onFinish={handleSubmit}
          submitter={{
            searchConfig: { submitText: '登陆' },
            render:(props)=> (
              <Button type="primary" block onClick={props.submit}>绑定账号</Button>
            )
          }}
        >
          <Tabs activeKey={type} onChange={setType}>
            <Tabs.TabPane
              key="account"
              tab={intl.formatMessage({
                id: 'pages.login.accountLogin.tab',
                defaultMessage: '账户密码登录',
              })}
            />
            <Tabs.TabPane
              key="mobile"
              tab={intl.formatMessage({
                id: 'pages.login.phoneLogin.tab',
                defaultMessage: '手机号登录',
              })}
            />
          </Tabs>
          {type === 'account' && (
            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'pages.login.username.placeholder',
                  defaultMessage: '用户名',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pages.login.username.required"
                        defaultMessage="请输入用户名!"
                      />
                    ),
                  },
                ]}
              />
              <ProFormText.Password
                name="password"
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
                }}
                placeholder={intl.formatMessage({
                  id: 'pages.login.password.placeholder',
                  defaultMessage: '密码',
                })}
                rules={[
                  {
                    required: true,
                    message: (
                      <FormattedMessage
                        id="pages.login.password.required"
                        defaultMessage="请输入密码！"
                      />
                    ),
                  },
                ]}
              />
            </>
          )}
          {type === 'mobile' && (
            <>
              <ProFormText
                fieldProps={{
                  size: 'large',
                  prefix: <MobileOutlined className={styles.prefixIcon} />,
                }}
                name="mobile"
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
              <ProFormCaptcha
                fieldProps={{
                  size: 'large',
                  prefix: <LockOutlined className={styles.prefixIcon} />,
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
                  const result = await getFakeCaptcha({
                    phone,
                  });
                  if (result === false) {
                    return;
                  }
                  message.success('获取验证码成功！验证码为：1234');
                }}
              />
            </>
          )}
          <div
            style={{
              marginBottom: 24,
            }}
          >
            <ProFormCheckbox noStyle name="autoLogin">
              <FormattedMessage id="pages.login.rememberMe" defaultMessage="自动登录" />
            </ProFormCheckbox>
            <span
              style={{
                float: 'right',
              }}
            >
            <a href="/register"><FormattedMessage id="pages.login.registerAccount" defaultMessage="注册账户" /></a>
            <span> / </span>
            <a href="/forget"><FormattedMessage id="pages.login.forgotPassword" defaultMessage="忘记密码" /></a>
          </span>
          </div>
        </ProForm>
      </div>
    </ProCard>
  );
};

export default SocialBind;
