import React, {useEffect} from "react";

import {PageLoading} from "@ant-design/pro-components";
import {history} from "@@/core/history";

const Index: React.FC = () => {

  useEffect(() => {
    history.push('/');
  }, [])

  return (<div>
    <PageLoading />
  </div>);
};

export default Index;
