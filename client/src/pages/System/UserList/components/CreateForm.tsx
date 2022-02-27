import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {ModalForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {UserForm} from "@/services/uaa/type/user";
import FormBody from "@/pages/System/UserList/components/FormBody";
import {addUser} from "@/services/uaa/user";

interface CreateFormProps {
  createModalVisible: boolean;
  handleCreateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
}

/**
 * 新建用户
 * @param fields
 */
const handleAdd = async (fields: UserForm) => {
  const hide = message.loading('添加中...');
  try {
    await addUser({ ...fields });
    hide();
    message.success('新建成功！');
    return true;
  } catch (error) {
    hide();
    message.error('新建失败!');
    return false;
  }
};

const CreateForm: React.FC<CreateFormProps> = (props) => {
  const { createModalVisible, handleCreateModalVisible, actionRef } = props;

  return (
    <ModalForm
      title="新建用户"
      width="748px"
      visible={createModalVisible}
      onVisibleChange={handleCreateModalVisible}
      onFinish={async (value) => {
        const success = await handleAdd(value as UserForm);
        if (success) {
          handleCreateModalVisible(false);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <FormBody />
    </ModalForm>
  );
}

export default CreateForm;
