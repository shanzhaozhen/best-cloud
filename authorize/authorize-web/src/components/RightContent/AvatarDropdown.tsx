import { LogoutOutlined } from '@ant-design/icons';
import { history } from '@umijs/max';
import { Avatar, Menu, Spin } from 'antd';
import type { ItemType } from 'antd/lib/menu/hooks/useItems';
import type { MenuInfo } from 'rc-menu/lib/interface';
import React, { useCallback } from 'react';
import HeaderDropdown from '../HeaderDropdown';
import styles from './index.less';
import {useRequest} from "@@/exports";
import {getCurrentUserInfo} from "@/services/user";

export type GlobalHeaderRightProps = {
  menu?: boolean;
};


const AvatarDropdown: React.FC<GlobalHeaderRightProps> = () => {
  const {data, loading} = useRequest(async () => {
    return getCurrentUserInfo();
  });

  const onMenuClick = useCallback(
    (event: MenuInfo) => {
      const { key } = event;
      if (key === 'logout') {
        window.location.href = '/logout'
        return;
      }
      history.push(`/account/${key}`);
    },
    [],
  );

  const loadingUser = (
    <span className={`${styles.action} ${styles.account}`}>
      <Spin
        size="small"
        style={{
          marginLeft: 8,
          marginRight: 8,
        }}
      />
    </span>
  );

  const menuItems: ItemType[] = [
    {
      key: 'logout',
      icon: <LogoutOutlined />,
      label: '退出登录',
    },
  ];

  const menuHeaderDropdown = (
    <Menu className={styles.menu} selectedKeys={[]} onClick={onMenuClick} items={menuItems} />
  );

  return (
    <HeaderDropdown overlay={menuHeaderDropdown}>
      <span className={`${styles.action} ${styles.account}`}>
        {
          loading ? (
            loadingUser
          ) : (
            <>
              <Avatar size="small" className={styles.avatar} src={data?.avatar || '/default-avatar.png'} alt="avatar" />
              <span className={`${styles.name} anticon`}>{data?.name || '（未命名）'}</span>
            </>
          )
        }
      </span>
    </HeaderDropdown>
  );
};

export default AvatarDropdown;
