import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {UserForm} from "@/services/uaa/type/user";
import FormBody from "@/pages/System/UserList/components/FormBody";
import {addOAuth2User} from "@/services/oauth/oauth2-user";

interface CreateFormProps {
  createModalOpen: boolean;
  handleCreateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  callBackFinish?: (userId: string) => Promise<any>;
}

const CreateForm: React.FC<CreateFormProps> = (props) => {
  const { createModalOpen, handleCreateModalOpen, actionRef, callBackFinish } = props;

  /**
   * 新建用户
   * @param fields
   */
  const handleAdd = async (fields: UserForm) => {
    const hide = message.loading('添加中...');
    try {
      const { data } = await addOAuth2User({ ...fields });
      if (data) {
        // 回调完成操作
        if (callBackFinish) {
          console.log('进入回调')
          await callBackFinish(data);
        }
        message.success('添加成功！');
        handleCreateModalOpen(false);
        if (actionRef.current) {
          actionRef.current.reload();
        }
      }
    } catch (error) {
      hide();
      message.error('新建失败!');
    }
  };

  return (
    <DrawerForm
      title="新建用户"
      width="748px"
      drawerProps={{ destroyOnClose: true }}
      open={createModalOpen}
      onOpenChange={handleCreateModalOpen}
      onFinish={handleAdd}
    >
      <FormBody formType="create" />
    </DrawerForm>
  );
}

export default CreateForm;
