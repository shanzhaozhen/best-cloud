import { useIntl } from 'umi';
import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-layout';

const Footer: React.FC = () => {
  const intl = useIntl();
  const defaultMessage = intl.formatMessage({
    id: 'app.copyright.produced',
    defaultMessage: 'shanzhaozhen',
  });

  const currentYear = new Date().getFullYear();

  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'Best-Cloud',
          title: 'Best Cloud',
          href: 'https://github.com/shanzhaozhen/best-cloud',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <GithubOutlined />,
          href: 'https://github.com/shanzhaozhen',
          blankTarget: true,
        },
        {
          key: 'Best-Cloud2',
          title: 'Best Cloud',
          href: 'https://github.com/shanzhaozhen/best-cloud',
          blankTarget: true,
        },
      ]}
    />
  );
};

export default Footer;
