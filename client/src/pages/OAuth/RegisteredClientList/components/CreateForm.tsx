import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {RegisteredClientForm} from "@/services/uaa/type/registered-client";
import {addRegisteredClient} from "@/services/uaa/registered-client";
import FormBody from "@/pages/OAuth/RegisteredClientList/components/FormBody";

interface CreateFormProps {
  createModalVisible: boolean;
  handleCreateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
}

/**
 * 新建客户端
 * @param fields
 */
const handleAdd = async (fields: RegisteredClientForm) => {
  const hide = message.loading('添加中...');
  try {
    await addRegisteredClient({ ...fields });
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
      title="新建客户端"
      width="748px"
      visible={createModalVisible}
      drawerProps={{ destroyOnClose: true }}
      onVisibleChange={handleCreateModalVisible}
      onFinish={async (value) => {
        const success = await handleAdd(value as RegisteredClientForm);
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
