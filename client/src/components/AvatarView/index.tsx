// 头像组件 方便以后独立，增加裁剪之类的功能
import {Button, Upload} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import styles from "./AvatarView.less";

const AvatarView = (props: Record<string, any>) => {
  const { value } = props

  console.log(props)

  return (
    <>
      <div className={styles.avatar_title}>头像</div>
      <div className={styles.avatar}>
        <img src={null || 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png'} alt="avatar" />
      </div>
      <Upload showUploadList={false}>
        <div className={styles.button_view}>
          <Button>
            <UploadOutlined />
            更换头像
          </Button>
        </div>
      </Upload>
    </>
  )
}

export default AvatarView;
