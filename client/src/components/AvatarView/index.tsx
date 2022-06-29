// 头像组件 方便以后独立，增加裁剪之类的功能
import {Button, message, Upload, UploadProps} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import styles from "./AvatarView.less";
import {getToken} from "@/utils/common";

const AvatarView = (props: Record<string, any>) => {
  const { value, maxCount } = props

  console.log(props)

  const uploadProps: UploadProps = {
    name: 'file',
    showUploadList: false,
    action: '/api/uaa/files',
    maxCount,
    // headers: {
    //   Authorization: getToken(),
    // },
    onChange: async (info) => {
      if (!info.event && info.fileList[0].response) {
        const { data } = info.fileList[0].response;
        console.log(info)
        if (data && data.length > 0) {
          // setPhotoUrl(targetUrlNotDiagonal + data[0].urlPath);
          // onChange?.(data[0].id);
        }
      }
    },
  };


  return (
    <>
      <div className={styles.avatar_title}>头像</div>
      <div className={styles.avatar}>
        <img src={null || 'https://gw.alipayobjects.com/zos/rmsportal/BiazfanxmamNRoxxVxka.png'} alt="avatar" />
      </div>
      <Upload {...uploadProps}>
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
