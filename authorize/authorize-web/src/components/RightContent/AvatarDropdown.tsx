import { LogoutOutlined, SettingOutlined } from '@ant-design/icons';
import { useEmotionCss } from '@ant-design/use-emotion-css';
import { history } from '@umijs/max';
import { Avatar } from 'antd';
import { setAlpha } from '@ant-design/pro-components';
import type { MenuInfo } from 'rc-menu/lib/interface';
import React, {useCallback} from 'react';
import HeaderDropdown from '../HeaderDropdown';
import {resourcesPath} from "../../../config/constants";

type NameProps = {
  name?: string;
};

type AvatarLogoProps = {
  avatar?: string;
};

type GlobalHeaderRightProps = {
  menu?: boolean;
};

const Name: React.FC<NameProps> = ({ name }) => {

  const nameClassName = useEmotionCss(({ token }) => ({
    width: '70px',
    height: '48px',
    overflow: 'hidden',
    lineHeight: '48px',
    whiteSpace: 'nowrap',
    textOverflow: 'ellipsis',
    [`@media only screen and (max-width: ${token.screenMD}px)`]: {
      display: 'none',
    },
  }));

  return <span className={`${nameClassName} anticon`}>{name || '（未命名）'}</span>;
};

const AvatarLogo: React.FC<AvatarLogoProps> = ({ avatar }) => {

  const avatarClassName = useEmotionCss(({ token }) => ({
    marginRight: '8px',
    color: token.colorPrimary,
    verticalAlign: 'top',
    background: setAlpha(token.colorBgContainer, 0.85),
    [`@media only screen and (max-width: ${token.screenMD}px)`]: {
      margin: 0,
    },
  }));

  return <Avatar size="small" className={avatarClassName} src={avatar || `${resourcesPath}default-avatar.png`} alt="avatar" />;
};

const AvatarDropdown: React.FC<GlobalHeaderRightProps> = ({ menu }) => {

  const actionClassName = useEmotionCss(({ token }) => ({
    display: 'flex',
    height: '48px',
    marginLeft: 'auto',
    overflow: 'hidden',
    alignItems: 'center',
    padding: '0 8px',
    cursor: 'pointer',
    borderRadius: token.borderRadius,
    '&:hover': {
      backgroundColor: token.colorBgTextHover,
    },
  }));


  const userInfo = {
    // @ts-ignore
    ...window.userInfo
  }

  const onMenuClick = useCallback(
    (event: MenuInfo) => {
      const { key } = event;
      if (key === 'settings') {
        window.location.href = '/account'
        return;
      } else if (key === 'logout') {
        window.location.href = '/logout'
        return;
      }
      history.push(`/`);
    },
    [],
  );

  const menuItems = [
    ...(menu
      ? [
        // {
        //   key: 'center',
        //   icon: <UserOutlined />,
        //   label: '个人中心',
        // },
        {
          key: 'settings',
          icon: <SettingOutlined />,
          label: '个人设置',
        },
        {
          type: 'divider' as const,
        },
      ]
      : []),
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  return (
    <HeaderDropdown
      menu={{
        selectedKeys: [],
        onClick: onMenuClick,
        items: menuItems,
      }}
    >
      <span className={actionClassName}>
        <AvatarLogo avatar={userInfo?.avatar} />
        <Name name={userInfo?.name} />
      </span>
    </HeaderDropdown>
  );
};

export default AvatarDropdown;
