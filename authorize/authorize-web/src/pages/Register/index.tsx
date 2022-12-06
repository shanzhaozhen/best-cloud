import React, {useEffect} from 'react';
import { SelectLang } from '@umijs/max';
import Footer from '@/components/Footer';
import styles from './index.less';



const Register: React.FC = () => {

  useEffect(() => {

  }, [])


  return (
    <div className={styles.container}>
      <div className={styles.lang} data-lang>
        {SelectLang && <SelectLang />}
      </div>
      <div className={styles.content}>

      </div>
      <Footer />
    </div>
  );
};

export default Register;
