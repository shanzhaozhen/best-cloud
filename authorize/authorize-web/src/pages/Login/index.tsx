import {
  GithubOutlined,
  LockOutlined,
  MobileOutlined,
  QqOutlined,
  UserOutlined,
  WechatOutlined,
  WeiboCircleOutlined,
} from '@ant-design/icons';
import {
  LoginForm,
  ProFormCaptcha,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import {FormattedMessage, useIntl} from '@umijs/max';
import {Alert, message, Tabs} from 'antd';
import {useEmotionCss} from '@ant-design/use-emotion-css';
import {getFakeCaptcha} from '@/services/login';
import {useLocation, useSearchParams} from "umi";
import React, {useEffect, useState} from 'react';
import PublicPageComponent from "@/components/PublicPageComponent";

const ActionIcons = () => {
  const langClassName = useEmotionCss(({token}) => {
    return {
      marginLeft: '8px',
      color: 'rgba(0, 0, 0, 0.2)',
      fontSize: '24px',
      verticalAlign: 'middle',
      cursor: 'pointer',
      transition: 'color 0.3s',
      '&:hover': {
        color: token.colorPrimaryActive,
      },
    };
  });

  return (
    <>
      <a key="githubOutlined" href="/oauth2/authorization/github-idp">
        <GithubOutlined className={langClassName}/>
      </a>
      <WechatOutlined key="wechatOutlined" className={langClassName}/>
      <QqOutlined key="qqOutlined" className={langClassName}/>
      <WeiboCircleOutlined key="weiboCircleOutlined" className={langClassName}/>
    </>
  );
};


const LoginMessage: React.FC<{
  content: string;
}> = ({content}) => {
  return (
    <Alert
      style={{
        marginBottom: 24,
      }}
      message={content}
      type="error"
      showIcon
    />
  );
};

const Login: React.FC = () => {
  const [userLoginState] = useState<any>({});
  const [type, setType] = useState<string>('account');
  const [searchParams] = useSearchParams();

  const intl = useIntl();

  const location = useLocation();

  useEffect(() => {
    const err = searchParams.get('error');
    const msg = searchParams.get('msg');

    if (err !== null) {
      message.error(msg ? msg : '用户名或密码错误!');
    } else if (location.search.indexOf('logout') > -1) {
      message.success('登出成功！');
    }
  }, [])

  const handleSubmit = async (values: any) => {
    const loginForm = document.createElement('form');
    try {
      loginForm.method = 'POST';
      loginForm.style.display = "none";

      for (const key in values) {
        const input = document.createElement('input');
        input.name = key;
        input.value = values[key];
        loginForm.appendChild(input);
      }

      if (type === 'account') { // 账号登陆
        loginForm.action = '/login/account';
      } else if (type === 'mobile') {  // 手机验证码登陆
        loginForm.action = '/login/account';
      }
      console.log(loginForm)
      document.body.appendChild(loginForm);
      loginForm.submit();
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: '登录失败，请重试！',
      });
      console.log(error);
      message.error(defaultLoginFailureMessage);
    } finally {
      document.body.removeChild(loginForm);
    }
  };
  const {status, type: loginType} = userLoginState;

  return (
    <PublicPageComponent
      pageTitle={
        intl.formatMessage({
          id: 'menu.login',
          defaultMessage: '登录页',
        })
      }
    >
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src={process.env.NODE_ENV === 'production' ? '/front/logo.svg' : '/logo.svg'}/>}
          title="Best Cloud"
          subTitle={intl.formatMessage({id: 'pages.layouts.userLayout.title'})}
          initialValues={{
            autoLogin: true,
          }}
          actions={[
            <FormattedMessage
              key="loginWith"
              id="pages.login.loginWith"
              defaultMessage="其他登录方式"
            />,
            <ActionIcons key="icons"/>,
          ]}
          onFinish={async (values) => {
            await handleSubmit(values as any);
          }}
        >
          <Tabs
            activeKey={type}
            onChange={setType}
            items={[
              {
                key: 'account',
                label: intl.formatMessage({
                  id: 'pages.login.accountLogin.tab',
                  defaultMessage: '账户密码登录',
                }),
              },
              {
                key: 'mobile',
                label: intl.formatMessage({
                  id: 'pages.login.phoneLogin.tab',
                  defaultMessage: '手机号登录',
                }),
              },
            ]}
          />

          {status === 'error' && loginType === 'account' && (
            <LoginMessage
              content={intl.formatMessage({
                id: 'pages.login.accountLogin.errorMessage',
                defaultMessage: '账户或密码错误',
              })}
            />
          )}
          {type === 'account' && (
            <>
              <ProFormText
                name="username"
                fieldProps={{
                  size: 'large',
                  prefix: <UserOutlined/>,
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
                  prefix: <LockOutlined/>,
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

          {status === 'error' && loginType === 'mobile' && <LoginMessage content="验证码错误"/>}
          {type === 'mobile' && (
            <>
              <ProFormText
                fieldProps={{
                  size: 'large',
                  prefix: <MobileOutlined/>,
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
                  if (!result) {
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
              <FormattedMessage id="pages.login.rememberMe" defaultMessage="自动登录"/>
            </ProFormCheckbox>
            <span
              style={{
                float: 'right',
              }}
            >
            <a href="/register"><FormattedMessage id="pages.login.registerAccount" defaultMessage="注册账户"/></a>
            <span> / </span>
            <a href="/forget"><FormattedMessage id="pages.login.forgotPassword" defaultMessage="忘记密码"/></a>
          </span>
          </div>
        </LoginForm>
      </div>
    </PublicPageComponent>
  );
};

export default Login;
