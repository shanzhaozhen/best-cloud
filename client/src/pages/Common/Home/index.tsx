import React from 'react';
import Footer from '@/components/Footer';
import styles from './index.less';
import {Button} from "antd";
import { useModel } from '@umijs/max';

const Home: React.FC = () => {

  const { initialState } = useModel('@@initialState');

  const userManager = initialState?.userManager;

  return (
    <div className={styles.container}>
      <div className={styles.content}>
        <div className={styles.homeContainer}>
          <div className={styles.homeTop}>
            <div className={styles.homeHeader}>
              <span className={styles.homeHeaderLogo}>
                <img alt="logo" src="/logo.svg" />
              </span>
              <span className={styles.homeHeaderTitle}>Best Cloud</span>
            </div>
            <div className={styles.homeDesc}>欢迎使用 Best Cloud，点击下方登陆开启使用</div>
          </div>
          <div className={styles.homeContent}>
            <Button type="primary" onClick={() => {
              userManager?.signinRedirect();
            }}>
              登陆
            </Button>
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default Home;
