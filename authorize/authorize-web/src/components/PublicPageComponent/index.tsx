import {useEmotionCss} from "@ant-design/use-emotion-css";
import React, {useEffect} from "react";
import Settings from "../../../config/defaultSettings";
import Footer from "@/components/Footer";
import Lang from "@/components/Lang";
import {Helmet} from "@@/exports";
import {resourcesPath} from "../../../config/constants";

interface PublicPageComponentProps {
  // 标题
  pageTitle?: string
}

const PublicPageComponent: React.FC<PublicPageComponentProps> = (props) => {

  const { pageTitle, children } = props;

  const containerClassName = useEmotionCss(() => {
    return {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage: `url('${resourcesPath}background.png')`,
      backgroundSize: '100% 100%',
    };
  });

  useEffect(() => {
  }, [])


  return (
    <div className={containerClassName}>
      {/*@ts-ignore*/}
      <Helmet>
        <title>
          {pageTitle ? (pageTitle + '-' + Settings.title) : Settings.title}
        </title>
      </Helmet>
      <Lang />
      {children}
      <Footer />
    </div>
  );
};

export default PublicPageComponent;
