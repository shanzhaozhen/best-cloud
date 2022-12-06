import React, {useState, useRef, useLayoutEffect, useEffect} from 'react';
import { GridContent } from '@ant-design/pro-layout';
import {Menu, message} from 'antd';
import BaseView from './components/base';
import BindingView from './components/binding';
import SecurityView from './components/security';
import styles from './style.less';
import type {ItemType} from "antd/lib/menu/hooks/useItems";
import {useSearchParams} from "umi";


type SettingsStateKeys = 'base' | 'security' | 'binding' | 'notification';
type SettingsState = {
  mode: 'inline' | 'horizontal';
  selectKey: SettingsStateKeys;
};

const Settings: React.FC = () => {
  const [searchParams, setSearchParams] = useSearchParams();

  const menuMap: Record<string, React.ReactNode> = {
    base: '基本设置',
    security: '安全设置',
    binding: '账号绑定',
  };

  const [initConfig, setInitConfig] = useState<SettingsState>({
    mode: 'inline',
    selectKey: (searchParams.get('action') || 'base') as SettingsStateKeys,
  });
  const dom = useRef<HTMLDivElement>();

  const resize = () => {
    requestAnimationFrame(() => {
      if (!dom.current) {
        return;
      }
      let mode: 'inline' | 'horizontal' = 'inline';
      const { offsetWidth } = dom.current;
      if (dom.current.offsetWidth < 641 && offsetWidth > 400) {
        mode = 'horizontal';
      }
      if (window.innerWidth < 768 && offsetWidth > 400) {
        mode = 'horizontal';
      }
      setInitConfig({
        ...initConfig,
        mode: mode as SettingsState['mode'],
        // selectKey: (searchParams.get('action') || 'base') as SettingsStateKeys,
      });
    });
  };

  useLayoutEffect(() => {
    if (dom.current) {
      window.addEventListener('resize', resize);
      resize();
    }
    return () => {
      window.removeEventListener('resize', resize);
    };
  }, [dom.current]);

  const getMenu = (): ItemType[] => {
    return Object.keys(menuMap).map((item) => ({
      key: item,
      label: menuMap[item],
    }));
  };

  const renderChildren = () => {
    const { selectKey } = initConfig;
    switch (selectKey) {
      case 'base':
        return <BaseView />;
      case 'security':
        return <SecurityView />;
      case 'binding':
        return <BindingView />;
      default:
        return null;
    }
  };

  useEffect(() => {
    const biz = searchParams.get('biz');
    const msg = searchParams.get('msg');
    if (biz === '0') {
      message.success(`账号绑定成功${msg ? ('：' + msg) : '!'}`)
    } else if (biz === '-1') {
      message.error(`账号绑定失败：${msg}`)
    }
  }, [])

  return (
    <GridContent>
      <div
        className={styles.main}
        ref={(ref) => {
          if (ref) {
            dom.current = ref;
          }
        }}
      >
        <div className={styles.leftMenu}>
          <Menu
            mode={initConfig.mode}
            selectedKeys={[initConfig.selectKey]}
            onClick={({ key }) => {
              setSearchParams({
                action: key
              });

              setInitConfig({
                ...initConfig,
                selectKey: key as SettingsStateKeys,
              });
            }}
            items={getMenu()}
          />
        </div>
        <div className={styles.right}>
          <div className={styles.title}>{menuMap[initConfig.selectKey]}</div>
          {renderChildren()}
        </div>
      </div>
    </GridContent>
  );
};
export default Settings;
