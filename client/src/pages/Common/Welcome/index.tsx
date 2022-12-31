import React from 'react';
import {Button} from "antd";
import {oauth2Login} from "../../../../config/oidcConfig";
import {useEmotionCss} from "@ant-design/use-emotion-css";
import PublicPageComponent from "@/components/PublicPageComponent";
import {useIntl} from "@@/exports";


const Welcome: React.FC = () => {
  const containerClassName = useEmotionCss(() => ({
    padding: '32px 0 24px',
    backgroundRepeat: 'no-repeat',
    backgroundPosition: 'center 110px',
    backgroundSize: '100%',
  }));

  const topClassName = useEmotionCss(() => ({
    textAlign: 'center'
  }));

  const headerClassName = useEmotionCss(() => ({
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
    height: '44px',
    lineHeight: '44px',
  }));

  const headerTitleClassName = useEmotionCss(() => ({
    position: 'relative',
    top: '2px',
    color: 'rgba(0, 0, 0, 0.85)',
    fontWeight: '600',
    fontSize: '33px',
  }));

  const headerLogoClassName = useEmotionCss(() => ({
    width: '44px',
    height: '44px',
    marginRight: '16px',
    verticalAlign: 'top',
  }));

  const descClassName = useEmotionCss(() => ({
    marginTop: '12px',
    marginBottom: '40px',
    color: 'rgba(0, 0, 0, 0.45)',
    fontSize: '14px',
  }));

  const contentClassName = useEmotionCss(() => ({
    textAlign: 'center',
    marginTop: '12px',
    marginBottom: '40px',
  }));

  const intl = useIntl();

  return (

    <PublicPageComponent
      pageTitle={
        intl.formatMessage({
          id: 'menu.login',
          defaultMessage: '欢迎页',
        })
      }
    >
      <div
        style={{
          flex: '1',
          padding: '172px 0',
        }}
      >
        <div className={containerClassName}>
          <div className={topClassName}>
            <div className={headerClassName}>
              <span className={headerLogoClassName}>
                <img style={{width: '100%'}} alt="logo" src="/logo.svg" />
              </span>
              <span className={headerTitleClassName}>Best Cloud</span>
            </div>
            <div className={descClassName}>欢迎使用 Best Cloud，点击下方登陆开启使用</div>
          </div>
          <div className={contentClassName}>
            <Button type="primary" onClick={oauth2Login}>
              登陆
            </Button>
          </div>
        </div>
      </div>
    </PublicPageComponent>
  );
};

export default Welcome;
