import React from "react";
import {useModel} from "@@/plugin-model";

import { history } from 'umi';
import {PageLoading} from "@ant-design/pro-components";

const Oidc: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  const userManager = initialState?.userManager;

  userManager?.signinRedirectCallback().catch(() => {
    localStorage.clear();
    sessionStorage.clear();
  }).finally(() => {
    history.push("/");
  })

  return (<div>
    <PageLoading />
  </div>);
};

export default Oidc;
