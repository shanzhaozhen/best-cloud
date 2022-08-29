// 头像组件 方便以后独立，增加裁剪之类的功能
import type { UploadProps} from "antd";
import {Button, message, Upload} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import styles from "./AvatarView.less";
import type {RcFile} from "antd/es/upload";
import {useState} from "react";

interface AvatarViewProps {
  readonly?: boolean;
  onChange?: (value: any) => void;
  value?: string;
}

const AvatarView = (props: AvatarViewProps) => {
  const { readonly, value, onChange } = props

  const [loading, setLoading] = useState(false);

  const uploadProps: UploadProps = {
    name: 'file',
    showUploadList: false,
    action: '/api/uaa/files',
    maxCount: 1,
    beforeUpload: async (file: RcFile) => {
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
      if (!isJpgOrPng) {
        message.error('您只能上传JPG/PNG文件格式！');
      }
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        message.error('图片必须少于2MB！');
      }
      return isJpgOrPng && isLt2M;
    },
    onChange: async (info) => {
      if (info.file.status === 'uploading') {
        setLoading(true);
        return;
      }

      if (info.file.status === 'done') {
        const { response } = info.file;
        if (response.code === '0') {
          console.log(response.data)
          onChange?.(response.data || '');
        } else {
          message.error('上传失败！');
        }
        setLoading(false);
      }
    },
  };


  return (
    <div className={styles.avatar_box}>
      <div className={styles.avatar_title}>头像</div>
      <div className={styles.avatar}>
        <img src={value || '/front/default-avatar.png'} alt="avatar" />
      </div>
      { readonly ? null : (
        <Upload {...uploadProps}>
          <div className={styles.button_view}>
            <Button icon={<UploadOutlined />} loading={loading}>
              更换头像
            </Button>
          </div>
        </Upload>
      )}
    </div>
  )
}

export default AvatarView;
