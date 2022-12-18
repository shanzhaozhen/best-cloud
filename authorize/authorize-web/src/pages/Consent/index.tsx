import React from 'react';
import { SelectLang } from 'umi';
import Footer from '@/components/Footer';
import {Button, message} from "antd";
import {ProForm, ProFormCheckbox} from '@ant-design/pro-components';
import styles from './style.less';
import {resourcesPath} from "../../../config/config";

const Consent: React.FC = () => {

  const consentInfo = {
    // @ts-ignore
    ...window.mvcModel
  }

  let scopeOptions = []
  if (consentInfo?.scopes) {
    scopeOptions = consentInfo.scopes.map((scope: any) => ({
      label: scope.description,
      value: scope.scope
    }))
  }

  let previouslyApprovedScopeOptions = []
  let previouslyApprovedScopeOptionsSelected = []
  if (consentInfo?.previouslyApprovedScopes) {
    previouslyApprovedScopeOptions = consentInfo.previouslyApprovedScopes.map((scope: any) => ({
      label: scope.description,
      value: scope.scope
    }))

    previouslyApprovedScopeOptionsSelected = consentInfo.previouslyApprovedScopes.map((scope: any) => scope.scope)
    console.log(previouslyApprovedScopeOptionsSelected)
  }

  const submitConsent = (type: string, values: any) => {
    const consentForm = document.createElement('form');
    try {
      consentForm.method = 'POST';
      consentForm.style.display = "none";
      consentForm.action = consentInfo.authorizationEndpoint;

      const clientIdInput = document.createElement('input');
      clientIdInput.name = 'client_id';
      clientIdInput.value = consentInfo.clientId;
      consentForm.appendChild(clientIdInput);

      const stateInput = document.createElement('input');
      stateInput.name = 'state';
      stateInput.value = consentInfo.state;
      consentForm.appendChild(stateInput);

      if (type === 'confirm') {
        for (const key in values) {
          const input = document.createElement('input');
          input.name = 'scope';
          input.value = values[key];
          consentForm.appendChild(input);
        }
      }

      console.log(consentForm)
      document.body.appendChild(consentForm);
      consentForm.submit();
    } catch (error) {
      console.log(error);
      message.error(type === 'cancel' ? '取消失败！' : '授权失败，请重试！');
    } finally {
      document.body.removeChild(consentForm);
    }
  }

  const submit = (values: any) => {
    submitConsent('confirm', values)
  }

  const cancel = () => {
    submitConsent('cancel', null)
  }

  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>
        <div className={styles.logoTop}>
          <div className={styles.logoHeader}>
            <span className={styles.logo}>
              <img alt="logo" src={`${resourcesPath}logo.svg`} />
            </span>
            <span className={styles.logoTitle}>Best Cloud</span>
          </div>
          <div className={styles.logoDesc}>Best Cloud</div>
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
          {
            scopeOptions.length > 0 ? (
              <div>
                <div>
                  <p>上述应用程序请求以下权限<br/>
                    如果您同意，请勾选这些选项并予以同意。</p>
                </div>
              </div>
            ):null
          }
          <div className="row">
            <div className="col text-center">
              <ProForm
                name="consent-form"
                submitter={{
                  render: ({ form }) => {
                    return [
                      <Button key="submit" type="primary" onClick={() => submit(form?.getFieldValue('scopes'))}>同意</Button>,
                      <Button key="cancel" onClick={cancel}>取消</Button>
                    ]
                  }
                }}
              >
                {
                  scopeOptions.length > 0 ? (
                    <>
                      <ProFormCheckbox.Group name="scopes" layout="vertical" options={scopeOptions} />
                    </>
                  ):null
                }
                {
                  previouslyApprovedScopeOptions.length > 0 ? (
                    <>
                      <p>您已向上述应用授予以下权限：</p>
                      <ProFormCheckbox.Group
                        layout="vertical"
                        options={previouslyApprovedScopeOptions}
                        fieldProps={{
                          defaultValue: previouslyApprovedScopeOptionsSelected
                        }}
                        disabled
                      />
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
