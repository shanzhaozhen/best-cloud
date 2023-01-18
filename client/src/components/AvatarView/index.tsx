// 头像组件 方便以后独立，增加裁剪之类的功能
import type { UploadProps} from "antd";
import {Button, message, Upload} from "antd";
import {UploadOutlined} from "@ant-design/icons";
import type {RcFile} from "antd/es/upload";
import {useState} from "react";
import {useEmotionCss} from "@ant-design/use-emotion-css";
import {getToken} from "@/utils/oauth";

interface AvatarViewProps {
  readonly?: boolean;
  onChange?: (value: any) => void;
  action: string;
  value?: string;
}

const AvatarView = (props: AvatarViewProps) => {

  const avatarBoxClassName = useEmotionCss(() => ({
    marginLeft: '15px'
  }))

  const avatarTitleClassName = useEmotionCss(() => ({
    height: '22px',
    marginBottom: '8px',
    color: 'fade(#000, 85%)',
    fontSize: '14px',
    lineHeight: '22px',
  }))

  const avatarClassName = useEmotionCss(() => ({
    width: '144px',
    height: '144px',
    marginBottom: '12px',
    overflow: 'hidden',
    //border: #0d0c0c solid 1px;
    border: '1px solid #d9d9d9',
    boxShadow: '0 2px 0 rgb(0 0 0 / 2%)',
    // img {
    //   width: 100%;
    // }
  }));

  const buttonViewClassName = useEmotionCss(() => ({
    width: '144px',
    textAlign: 'center'
  }));

  const { readonly, action, value, onChange: onAvatarChange } = props

  const [loading, setLoading] = useState(false);

  const uploadProps: UploadProps = {
    name: 'file',
    showUploadList: false,
    action: action,
    maxCount: 1,
    headers: {
      Authorization: getToken(),
    },
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
          const avatarUrl = response.data || '';
          console.log(avatarUrl)
          onAvatarChange?.(avatarUrl);
        } else {
          message.error('上传失败！');
        }
      }

      if (info.file.status === 'error') {
        message.error('上传失败！');
      }

      setLoading(false);
    },
  };

  return (
    <div className={avatarBoxClassName}>
      <div className={avatarTitleClassName}>头像</div>
      <div className={avatarClassName}>
        <img style={{width: '100%'}} src={value || '/default-avatar.png'} alt="avatar" />
      </div>
      { readonly ? null : (
        <Upload {...uploadProps}>
          <div className={buttonViewClassName}>
            <Button icon={<UploadOutlined />} loading={loading} disabled={loading}>
              更换头像
            </Button>
          </div>
        </Upload>
      )}
    </div>
  )
}

export default AvatarView;
