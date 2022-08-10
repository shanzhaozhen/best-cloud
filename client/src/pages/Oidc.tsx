import React, {useEffect} from "react";
import {useSearchParams} from "@@/exports";
import {useModel} from "@@/plugin-model";

import { history } from 'umi';
import {PageLoading} from "@ant-design/pro-components";

const Oidc: React.FC = () => {
  const { initialState } = useModel('@@initialState');

  const userManager = initialState?.userManager;

  console.log(userManager)

  const [searchParams] = useSearchParams();

  console.log(searchParams)

  // useEffect(() => {
  //   async function signinAsync() {
  //     await userManager?.signinRedirectCallback();
  //     // redirect user to home page
  //     // history.push('/')
  //   }
  //
  //   signinAsync();
  //   // userManager?.signinSilentCallback().catch(function (error) {
  //   //   console.error(error);
  //   // });
  // }, [])


  return (<div>
    <PageLoading />
  </div>);
};

export default Oidc;
