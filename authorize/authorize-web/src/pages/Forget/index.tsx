import React, {useEffect} from 'react';
import Footer from '@/components/Footer';
import {Helmet, useIntl} from "@@/exports";
import Settings from "../../../config/defaultSettings";
import Lang from "@/components/Lang";
import {useEmotionCss} from "@ant-design/use-emotion-css";

const Forget: React.FC = () => {

  const containerClassName = useEmotionCss(() => {
    return {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage: "url('/background.png')",
      backgroundSize: '100% 100%',
    };
  });

  const intl = useIntl();

  useEffect(() => {

  }, [])


  return (
    <div className={containerClassName}>
      <Helmet>
        <title>
          {intl.formatMessage({
            id: 'menu.forget',
            defaultMessage: '忘记密码',
          })}
          - {Settings.title}
        </title>
      </Helmet>
      <Lang />
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
      </div>
      <Footer />
    </div>
  );
};

export default Forget;
