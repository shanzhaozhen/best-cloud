import React from 'react';
import {Col, Row, Tabs} from 'antd';
import {ProFormDigit, ProFormSelect, ProFormSwitch, ProFormText, ProFormTextArea} from '@ant-design/pro-form';
import type { FormType } from '@/services/common/typings';
import {ProFormDateTimePicker, ProFormList} from "@ant-design/pro-components";

interface FormBodyProps {
  formType?: FormType;
  readonly?: boolean;
}

const FormBody: React.FC<FormBodyProps> = (props) => {

  const { formType } = props;

  const scopeOptions = [
    { label: '用户信息', value: 'userinfo' },
    { label: '信息读取', value: 'message.read' },
    { label: '信息修改', value: 'message.write' },
    { label: '其他', value: 'other.scope' },
  ];

  const authorizationGrantTypeOptions = [
    { label: 'AUTHORIZATION_CODE', value: 'authorization_code' },
    { label: 'IMPLICIT', value: 'implicit' },
    { label: 'REFRESH_TOKEN', value: 'refresh_token' },
    { label: 'CLIENT_CREDENTIALS', value: 'client_credentials' },
    { label: 'PASSWORD', value: 'password', disabled: true },
    { label: 'JWT_BEARER', value: 'urn:ietf:params:oauth:grant-type:jwt-bearer' },
  ]

  const clientAuthenticationMethodOptions = [
    { label: 'BASIC', value: 'basic' },
    { label: 'CLIENT_SECRET_BASIC', value: 'client_secret_basic' },
    { label: 'CLIENT_SECRET_POST', value: 'client_secret_post' },
    { label: 'CLIENT_SECRET_JWT', value: 'client_secret_jwt' },
    { label: 'PRIVATE_KEY_JWT', value: 'private_key_jwt' },
    { label: 'NONE', value: 'none' },
  ];

  const algorithmOptions = [
    { label: 'HS256', value: 'HS256' },
    { label: 'HS384', value: 'HS384' },
    { label: 'HS512', value: 'HS512' },
    { label: 'RS256', value: 'RS256' },
    { label: 'RS384', value: 'RS384' },
    { label: 'RS512', value: 'RS512' },
    { label: 'ES256', value: 'ES256' },
    { label: 'ES384', value: 'ES384' },
    { label: 'ES512', value: 'ES512' },
    { label: 'PS256', value: 'PS256' },
    { label: 'PS384', value: 'PS384' },
    { label: 'PS512', value: 'PS512' }
  ];

  return (
    <>
      <ProFormText name="id" label="主键id" hidden={true} />
      <Tabs defaultActiveKey="basic">
        <Tabs.TabPane tab="账户信息" key="basic" forceRender>
          <Row gutter={24}>
            <Col xl={12} lg={12} md={24}>
              <ProFormText
                width="md"
                name="clientId"
                label="客户端id"
                disabled={true}
              />
            </Col>
          </Row>
          <Row gutter={24}>
            <Col xl={12} lg={12} md={24}>
              <ProFormText
                width="md"
                name="clientName"
                label="客户端名称"
                rules={[{ required: true, message: '请输入客户端名称' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              {
                formType !== 'view' ? (
                  <ProFormText.Password
                    width="md"
                    name="clientSecret"
                    label="客户端密码"
                  />
                ) : null
              }
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormDateTimePicker
                width="md"
                name="clientIdIssuedAt"
                label="客户端到期时间"
                placeholder="请选择客户端到期时间"
                rules={[{ required: true, message: '请选择客户端到期时间' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormDateTimePicker
                width="md"
                name="clientSecretExpiresAt"
                label="客户端密码到期时间"
                placeholder="请选择客户端密码到期时间"
                rules={[{ required: true, message: '请选择客户端密码到期时间' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name="authorizationGrantTypeList"
                label="客户端授权方式"
                mode="multiple"
                options={authorizationGrantTypeOptions}
                placeholder="请选择资源类型"
                rules={[{ required: true, message: '请选择客户端授权方式' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name="clientAuthenticationMethodList"
                label="客户端认证方式"
                mode="multiple"
                options={clientAuthenticationMethodOptions}
                placeholder="请选择客户端认证方式"
                rules={[{ required: true, message: '请选择客户端认证方式' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name="scopeList"
                label="客户端允许的授权范围"
                mode="multiple"
                options={scopeOptions}
                placeholder="请选择客户端允许的授权范围"
                rules={[{ required: true, message: '请选择客户端允许的授权范围' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col span={24}>
              {
                formType === 'view' ? (
                  <ProFormList name="redirectUriList" label="客户端允许重定向的uri" creatorButtonProps={false} deleteIconProps={false} copyIconProps={false}>
                    <ProFormText name="uri" width="xl" readonly={formType === 'view'}/>
                  </ProFormList>
                ) : (
                  <ProFormList name="redirectUriList" label="客户端允许重定向的uri">
                    <ProFormText name="uri" width="xl"/>
                  </ProFormList>
                )
              }
            </Col>
            <Col span={24}>
              <ProFormTextArea name="description" label="描述" readonly={formType === 'view'} />
            </Col>
          </Row>
        </Tabs.TabPane>
        <Tabs.TabPane tab="客户端配置" key="client" forceRender>
          <ProFormText name={['clientSettings', 'id']} label="客户端配置id" hidden={true} />
          <ProFormText name={['clientSettings', 'registeredClientId']} label="oauth2客户端id" hidden={true} />
          <Row gutter={24}>
            <Col xl={12} lg={12} md={24}>
              <ProFormSwitch
                name={['clientSettings', 'requireProofKey']}
                label="客户端是否需要证明密钥"
                checkedChildren="是"
                unCheckedChildren="否"
                rules={[{ required: true, message: '请选择客户端是否需要证明密钥' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSwitch
                width="md"
                name={['clientSettings', 'requireAuthorizationConsent']}
                label="客户端是否需要授权确认页面"
                checkedChildren="是"
                unCheckedChildren="否"
                rules={[{ required: true, message: '请选择客户端是否需要授权确认页面' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name={['clientSettings', 'tokenEndpointAuthenticationSigningAlgorithm']}
                label="支持的签名算法"
                placeholder="请选择支持的签名算法"
                // rules={[{ required: true, message: '请选择支持的签名算法' }]}
                options={algorithmOptions}
                readonly={formType === 'view'}
              />
            </Col>
            <Col span={24}>
              <ProFormText
                width="xl"
                name={['clientSettings', 'jwkSetUrl']}
                label="jwkSet url"
                // rules={[{ required: true, message: '请输入jwkSet url' }]}
                readonly={formType === 'view'}
              />
            </Col>
          </Row>
        </Tabs.TabPane>
        <Tabs.TabPane tab="token配置" key="token" forceRender>
          <ProFormText name={['tokenSettings', 'id']} label="token配置id" hidden={true} />
          <ProFormText name={['tokenSettings', 'registeredClientId']} label="oauth2客户端id" hidden={true} />
          <Row gutter={24}>
            <Col xl={12} lg={12} md={24}>
              <ProFormDigit
                width="md"
                name={['tokenSettings', 'accessTokenTimeToLive']}
                label="access_token 有效时间（分钟）"
                min={0}
                rules={[{ required: true, message: '请输入 access_token 有效时间' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormDigit
                width="md"
                name={['tokenSettings', 'refreshTokenTimeToLive']}
                label="refresh_token 有效时间（分钟）"
                min={0}
                rules={[{ required: true, message: '请输入 refresh_token 有效时间' }]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name={['tokenSettings', 'accessTokenFormat']}
                label="支持的签名算法"
                placeholder="请选择支持的签名算法"
                rules={[{ required: true, message: '请选择支持的签名算法' }]}
                options={[
                  { label: 'SELF_CONTAINED', value: 'self-contained' },
                  { label: 'REFERENCE', value: 'reference' }
                ]}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSelect
                width="md"
                name={['tokenSettings', 'idTokenSignatureAlgorithm']}
                label="oidc id_token 签名算法"
                placeholder="请选择 oidc id_token 签名算法"
                rules={[{ required: true, message: '请选择 oidc id_token 签名算法' }]}
                options={algorithmOptions}
                readonly={formType === 'view'}
              />
            </Col>
            <Col xl={12} lg={12} md={24}>
              <ProFormSwitch
                width="md"
                name={['tokenSettings', 'reuseRefreshTokens']}
                label="是否重用 refresh_token"
                checkedChildren="是"
                unCheckedChildren="否"
                rules={[{ required: true, message: '请选择是否重用 refresh_token' }]}
                readonly={formType === 'view'}
              />
            </Col>
          </Row>
        </Tabs.TabPane>
      </Tabs>
    </>
  );
};

export default FormBody;
