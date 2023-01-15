import React, {useEffect} from "react";

import {PageLoading} from "@ant-design/pro-components";
import {history} from "@umijs/max";

const Index: React.FC = () => {

  useEffect(() => {
    const redirect = localStorage.getItem('redirect');

    localStorage.removeItem('redirect');
    history.replace(redirect || '/');
  }, [])

  return (<div>
    <PageLoading />
  </div>);
};

export default Index;
