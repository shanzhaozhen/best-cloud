import React, {useEffect, useState} from 'react';
import { SelectLang } from 'umi';
import Footer from '@/components/Footer';
import styles from './index.less';
import {Button, Checkbox, message} from "antd";
import {ProForm, ProFormCheckbox} from '@ant-design/pro-components';

const Consent: React.FC = () => {

  // const consentInfo = {
  //   authorizationEndpoint: '',
  //   clientId: '',
  //   clientName: '',
  //   state: '',
  //   scopes: '',
  //   previouslyApprovedScopes: '',
  //   principalName: '',
  //   href: '',
  // }

  const [consentInfo, setConsentInfo] = useState<any>({});
  const [scopeOptions, setScopeOptions] = useState<any[]>([]);
  const [previouslyApprovedScopeOptions, setPreviouslyApprovedScopeOptions] = useState<any[]>([]);

  useEffect(() => {
    // @ts-ignore
    setConsentInfo(window.consentData);

    console.log(window.consentData)
    console.log(consentInfo)

    if (consentInfo?.scopes) {
      setScopeOptions(consentInfo.scopes.map((scope: any) => ({
        label: scope.description,
        value: scope.scope
      })))
    }

    if (consentInfo?.previouslyApprovedScopes) {
      setPreviouslyApprovedScopeOptions(consentInfo.previouslyApprovedScopes.map((scope: any) => ({
        label: scope.description,
        value: scope.scope
      })))
    }

  }, [])

  const submit = () => {
    const loginForm = document.createElement('form');
    try {
      loginForm.method = 'POST';
      loginForm.style.display = "none";
      loginForm.action = consentInfo.authorizationEndpoint;

      // const input = document.createElement('input');
      // input.name = key;
      // input.value = values[key];
      // loginForm.appendChild(input);

      console.log(loginForm)
      document.body.appendChild(loginForm);
      loginForm.submit();
    } catch (error) {
      console.log(error);
      message.error('授权失败，请重试！');
    } finally {
      document.body.removeChild(loginForm);
    }
  }

  const cancel = () => {

  }

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>
        <div className="ant-pro-form-login-top">
          <div className="ant-pro-form-login-header">
            <span className="ant-pro-form-login-logo">
              <img alt="logo" src={process.env.NODE_ENV === 'production' ? '/front/logo.svg' : '/logo.svg'} />
            </span>
            <span className="ant-pro-form-login-title">Best Cloud</span>
          </div>
          <div className="ant-pro-form-login-desc">Best Cloud</div>
        </div>

        <div className={styles.consentContent}>
          <h1 className={styles.textCenter}>应用授权</h1>
          <div>
            <p>
              应用
              <a href={consentInfo?.href}>
                <span className="font-weight-bold text-primary">{consentInfo?.clientName}</span>
              </a>
              想要访问您的账号
              <span className="font-weight-bold">{consentInfo?.principalName}</span>
            </p>
          </div>
          <div className="row pb-3">
            <div className="col text-center"><p>上述应用程序请求以下权限。<br/>请看
              如果您同意，请勾选这些文件并予以同意。</p></div>
          </div>
          <div className="row">
            <div className="col text-center">
              <ProForm
                name="consent-form"
                submitter={{
                  render: () => {
                    return [
                      <Button key="submit" type="primary" onClick={submit}>同意</Button>,
                      <Button key="cancel" onClick={cancel}>取消</Button>
                    ]
                  }
                }}
              >
                <ProFormCheckbox.Group name="scopes" layout="vertical" options={scopeOptions} />
                {
                  consentInfo?.previouslyApprovedScopes && consentInfo?.previouslyApprovedScopes.length > 0 ? (
                    <>
                      <p>您已向上述应用授予以下权限：</p>
                      <Checkbox.Group options={previouslyApprovedScopeOptions} />
                    </>
                  ) : null
                }
              </ProForm>
            </div>
          </div>
          <div className={styles.consentFooter}>
            <div>
              <p>
                <small>
                  需要您同意并提供访问权限。
                  <br/>如果您不同意，请单击<span className="font-weight-bold text-primary">取消授权</span>，将不会为上述应用程序提供任何您的信息。
                </small>
              </p>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Consent;
