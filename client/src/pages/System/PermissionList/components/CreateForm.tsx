import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {PermissionForm} from "@/services/uaa/type/permission";
import FormBody from "@/pages/System/PermissionList/components/FormBody";
import {addPermission} from "@/services/uaa/permission";

interface CreateFormProps {
  createModalOpen: boolean;
  handleCreateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  values: Partial<PermissionForm>;
}

/**
 * 新建权限
 * @param fields
 */
const handleAdd = async (fields: PermissionForm) => {
  const hide = message.loading('添加中...');
  try {
    await addPermission({ ...fields });
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
  const { createModalOpen, handleCreateModalOpen, actionRef, values } = props;

  return (
    <DrawerForm
      title="新建权限"
      width="748px"
      open={createModalOpen}
      initialValues={values}
      drawerProps={{ destroyOnClose: true }}
      onOpenChange={handleCreateModalOpen}
      onFinish={async (value) => {
        const success = await handleAdd(value as PermissionForm);
        if (success) {
          handleCreateModalOpen(false);
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
