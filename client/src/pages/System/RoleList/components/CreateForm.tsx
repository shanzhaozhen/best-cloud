import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {RoleForm} from "@/services/uaa/type/role";
import FormBody from "@/pages/System/RoleList/components/FormBody";
import {addRole} from "@/services/uaa/role";

interface CreateFormProps {
  createModalVisible: boolean;
  handleCreateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
}

/**
 * 新建角色
 * @param fields
 */
const handleAdd = async (fields: RoleForm) => {
  const hide = message.loading('添加中...');
  try {
    await addRole({ ...fields });
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
    <DrawerForm
      title="新建角色"
      width="748px"
      visible={createModalVisible}
      onVisibleChange={handleCreateModalVisible}
      onFinish={async (value) => {
        const success = await handleAdd(value as RoleForm);
        if (success) {
          handleCreateModalVisible(false);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <FormBody formType="create" />
    </DrawerForm>
  );
}

export default CreateForm;
