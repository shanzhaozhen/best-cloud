import {Link, useLocation} from 'umi';
import { Result, Button } from 'antd';
import React from "react";

const Page500: React.FC = () => {
  const location = useLocation();

  const errorUrl = location.search ? '/' + location.search.slice(1) : null

  console.log(errorUrl)

  return (
    <Result
      status="500"
      title="500"
      style={{
        background: 'none',
      }}
      subTitle="抱歉, 服务器出现问题。"
      extra={
        <>
          <Link to="/">
            <Button type="primary">返回首页</Button>
          </Link>
          { errorUrl ? (
            <Link to={errorUrl}>
              <Button type="primary">返回上次操作</Button>
            </Link>
          ) : null }
        </>

      }
    />
  )
}

export default Page500;
