import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {OAuth2UserForm, OAuth2UserVO} from "@/services/oauth/type/oauth2-user";
import FormBody from "@/pages/OAuth/OAuthUserList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getOAuth2UserById, updateOAuth2User} from "@/services/oauth/oauth2-user";

interface UpdateFormProps {
  updateModalOpen: boolean;
  handleUpdateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<OAuth2UserVO | undefined>>
  currentRow: Partial<OAuth2UserForm | OAuth2UserVO | undefined>;
}

/**
 * 更新用户
 * @param fields
 */
const handleUpdate = async (fields: OAuth2UserForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateOAuth2User(fields);
    hide();
    message.success('用户更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('用户更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalOpen, handleUpdateModalOpen, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑用户"
      width="748px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleUpdateModalOpen(false);
          if (!updateModalOpen) {
            setCurrentRow(undefined);
          }
        }
      }}
      request={async () => {
        try {
          if (currentRow?.id) {
            const { data } = await getOAuth2UserById(currentRow?.id);
            return data;
          } else {
            message.warning("权限id不能为空");
            handleUpdateModalOpen(false);
          }
        } catch (e) {
          message.warning("远程获取数据失败！");
          handleUpdateModalOpen(false);
        }
        return undefined;
      }}
      open={updateModalOpen}
      onOpenChange={handleUpdateModalOpen}
      onFinish={async (value) => {
        const success = await handleUpdate(value as OAuth2UserForm);
        if (success) {
          handleUpdateModalOpen(false);
          setCurrentRow(undefined);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <FormBody formType="update" />
    </DrawerForm>
  )
}

export default UpdateForm;
