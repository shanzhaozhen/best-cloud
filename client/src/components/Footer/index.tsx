import { useIntl } from 'umi';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: '神大人嚟啦！',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'shanzhaozhen',
          title: 'Home',
          href: 'https://github.com/shanzhaozhen',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/shanzhaozhen/best-cloud',
          blankTarget: true,
        },
        {
          key: 'shanzhaozhen1',
          title: 'shanzhaozhen',
          href: 'https://github.com/shanzhaozhen',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
