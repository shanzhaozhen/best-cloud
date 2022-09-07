import {GithubOutlined} from '@ant-design/icons';
import {Badge, List, message, Skeleton} from 'antd';
import React, { Fragment } from 'react';
import {useRequest} from "@@/exports";
import {getSocialInfo, unbindSocial} from "@/services/social";


const handleUnbind = async (type: string) => {
  const hide = message.loading('解绑中...');
  try {
    await unbindSocial(type);
    hide();
    message.success('解绑成功！');
    window.location.reload();
  } catch (error) {
    hide();
    message.error('解绑失败!');
  }
}

const BindingView: React.FC = () => {

  const {data, loading} = useRequest(async () => {
    return getSocialInfo();
  });

  /*const getData = () => [
    // {
    //   title: '绑定淘宝',
    //   description: '当前未绑定淘宝账号',
    //   actions: [<a key="Bind">绑定</a>],
    //   avatar: <TaobaoOutlined className="taobao" />,
    // },
    // {
    //   title: '绑定支付宝',
    //   description: '当前未绑定支付宝账号',
    //   actions: [<a key="Bind">绑定</a>],
    //   avatar: <AlipayOutlined className="alipay" />,
    // },
    // {
    //   title: '绑定钉钉',
    //   description: '当前未绑定钉钉账号',
    //   actions: [<a key="Bind">绑定</a>],
    //   avatar: <DingdingOutlined className="dingding" />,
    // },
    {
      title: '绑定 Github',
      description: '当前未绑定 Github 账号',
      actions: [<a key="Bind" href="/oauth2/authorization/github-idp?action=bind">绑定</a>],
      avatar: <GithubOutlined className="dingding" />,
    },
  ];*/

  return (
    <Fragment>
      <Skeleton loading={loading}>
        <List
          itemLayout="horizontal"
          /*dataSource={getData()}
          renderItem={(item) => (
            <List.Item actions={item.actions}>
              <List.Item.Meta
                avatar={item.avatar}
                title={item.title}
                description={item.description}
              />
            </List.Item>
          )}*/
        >
          { data && data.github ? (
            <List.Item actions={[<a key="unbind" onClick={() => handleUnbind('github')}>解绑</a>]}>
              <List.Item.Meta
                avatar={
                  <Badge count={<GithubOutlined className="github-badge" />} offset={[0, 44]}>
                    <img className="social-avatar" src={data.github.avatarUrl}  alt={data.github.username} />
                  </Badge>

               /* <div className="social-avatar-box">
                  <GithubOutlined className="social-avatar-badge github-badge" />
                </div>*/
                }
                title={data.github.username}
                description={`绑定时间：${data.github.bindDate}`}
              />
            </List.Item>
            ) : (
            <List.Item actions={[<a key="bind" href="/oauth2/authorization/github-idp?action=bind">绑定</a>]}>
              <List.Item.Meta
                avatar={<GithubOutlined className="dingding" />}
                title="绑定 Github"
                description="当前未绑定 Github 账号"
              />
            </List.Item>
          ) }

        </List>
      </Skeleton>
    </Fragment>
  );
};

export default BindingView;
