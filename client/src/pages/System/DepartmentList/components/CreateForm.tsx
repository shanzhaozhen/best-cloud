import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import type {DepartmentForm} from "@/services/uaa/type/department";
import FormBody from "@/pages/System/DepartmentList/components/FormBody";
import {addDepartment} from "@/services/uaa/department";

interface CreateFormProps {
  createModalOpen: boolean;
  handleCreateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  values: Partial<DepartmentForm>;
}

/**
 * 新建部门
 * @param fields
 */
const handleAdd = async (fields: DepartmentForm) => {
  const hide = message.loading('添加中...');
  try {
    await addDepartment({ ...fields });
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
      title="新建部门"
      width="748px"
      open={createModalOpen}
      initialValues={values}
      drawerProps={{ destroyOnClose: true }}
      onOpenChange={handleCreateModalOpen}
      onFinish={async (value) => {
        const success = await handleAdd(value as DepartmentForm);
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
