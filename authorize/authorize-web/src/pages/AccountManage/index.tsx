import React, {useState, useRef, useLayoutEffect, useEffect} from 'react';
import { GridContent } from '@ant-design/pro-layout';
import {Menu, message} from 'antd';
import BaseView from './components/base';
import BindView from './components/binding';
import SecurityView from './components/security';
import type {ItemType} from "antd/lib/menu/hooks/useItems";
import {useSearchParams} from "umi";
import {useEmotionCss} from "@ant-design/use-emotion-css";


type SettingsStateKeys = 'base' | 'security' | 'binding' | 'notification';
type SettingsState = {
  mode: 'inline' | 'horizontal';
  selectKey: SettingsStateKeys;
};

const Settings: React.FC = () => {

  const mainClassName = useEmotionCss(({ token }) => ({
    display: 'flex',
    width: '100%',
    height: '100%',
    paddingTop: '16px',
    paddingBottom: '16px',
    backgroundColor: token.colorBgBase,
  }));

  const leftMenuClassName = useEmotionCss(({ token }) => ({
    width: '224px',
    borderRight: `${token.lineWidth} ${token.lineType} ${token.colorSplit}`,
  }));

  const rightClassName = useEmotionCss(() => ({
    flex: 1,
    padding: '8px 40px',
  }));

  const titleClassName = useEmotionCss(({ token }) => ({
    marginBottom: '12px',
    color: token.colorTextHeading,
    fontWeight: 500,
    fontSize: '20px',
    lineHeight: '28px',
  }));

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
        return <BindView />;
      default:
        return null;
    }
  };

/*  useEffect(() => {
    const biz = searchParams.get('biz');
    const msg = searchParams.get('msg');
    if (biz === '0') {
      message.success(`账号绑定成功${msg ? ('：' + msg) : '!'}`)
    } else if (biz === '-1') {
      message.error(`账号绑定失败：${msg}`)
    }
  }, [])*/

  // @ts-ignore
  const sucMsg = window['sucMsg']
  // @ts-ignore
  const excMsg = window['excMsg']

  useEffect(() => {
    if (sucMsg) {
      message.success(sucMsg)
      return
    }
    if (excMsg) {
      message.error(excMsg);
    }
  }, [sucMsg, excMsg])


  return (
    <GridContent>
      <div
        className={mainClassName}
        ref={(ref) => {
          if (ref) {
            dom.current = ref;
          }
        }}
      >
        <div className={leftMenuClassName}>
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
        <div className={rightClassName}>
          <div className={titleClassName}>{menuMap[initConfig.selectKey]}</div>
          {renderChildren()}
        </div>
      </div>
    </GridContent>
  );
};
export default Settings;
