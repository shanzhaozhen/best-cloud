import React, {useEffect} from 'react';
import {useIntl} from "@@/exports";
import PublicPageComponent from "@/components/PublicPageComponent";


const Register: React.FC = () => {

  const intl = useIntl();

  useEffect(() => {

  }, [])

  return (
    <PublicPageComponent
      pageTitle={
        intl.formatMessage({
            id: 'menu.register',
            defaultMessage: '注册页',
          })
      }
    >
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
      </div>
    </PublicPageComponent>
  );
};

export default Register;
