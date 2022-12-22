import React from 'react';
import Footer from '@/components/Footer';
import {Button, message} from "antd";
import {ProForm, ProFormCheckbox} from '@ant-design/pro-components';
import {useEmotionCss} from "@ant-design/use-emotion-css";
import Lang from "@/components/Lang";
import {resourcesPath} from "../../../config/constants";

const Consent: React.FC = () => {

  const containerClassName = useEmotionCss(({ token }) => ({
    display: 'flex',
    flexDirection: 'column',
    height: '100vh',
    overflow: 'auto',
    background: token.colorBgLayout,
    backgroundImage: `url('${resourcesPath}background.png')`,
    backgroundSize: '100% 100%',
  }));

  const contentClassName = useEmotionCss(() => ({
    flex: 1,
    padding: '32px 0'
  }));

  const logoTopClassName = useEmotionCss(() => ({
    textAlign: 'center'
  }));

  const logoHeaderClassName = useEmotionCss(() => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '44px',
    lineHeight: '44px',
  }));

  const logoClassName = useEmotionCss(() => ({
    width: '44px',
    height: '44px',
    marginRight: '16px',
    verticalAlign: 'top'
  }));

  const logoTitleClassName = useEmotionCss(() => ({
    position: 'relative',
    top: '2px',
    color: 'rgba(0, 0, 0, 0.85)',
    fontWeight: 600,
    fontSize: '33px',
  }));

  const logoDescClassName = useEmotionCss(() => ({
    marginTop: '12px',
    marginBottom: '40px',
    color: 'rgba(0, 0, 0, 0.45)',
    fontSize: '14px'
  }));

  const consentContentClassName = useEmotionCss(() => ({
    minWidth: '328px',
    maxWidth: '500px',
    margin: '0 auto',
    textAlign: 'center'
  }));

  const textCenterClassName = useEmotionCss(() => ({
  }));

  const consentFooterClassName = useEmotionCss(() => ({
    marginTop: '12px'
  }));

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
        for (let key in values) {
          if (values.hasOwnProperty(key)) {
            const input = document.createElement('input');
            input.name = 'scope';
            input.value = values[key];
            consentForm.appendChild(input);
          }
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
    <div className={containerClassName}>
      <Lang/>
      <div className={contentClassName}>
        <div className={logoTopClassName}>
          <div className={logoHeaderClassName}>
            <span className={logoClassName}>
              <img style={{width: '100%'}} alt="logo" src={`${resourcesPath}logo.svg`} />
            </span>
            <span className={logoTitleClassName}>Best Cloud</span>
          </div>
          <div className={logoDescClassName}>Best Cloud</div>
        </div>

        <div className={consentContentClassName}>
          <h1 className={textCenterClassName}>应用授权</h1>
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
          <div className={consentFooterClassName}>
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
