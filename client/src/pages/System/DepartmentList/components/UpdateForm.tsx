import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {DepartmentForm, DepartmentVO} from "@/services/uaa/type/department";
import FormBody from "@/pages/System/DepartmentList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getDepartmentById, updateDepartment} from "@/services/uaa/department";

interface UpdateFormProps {
  updateModalOpen: boolean;
  handleUpdateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<DepartmentVO | undefined>>
  currentRow: Partial<DepartmentForm | undefined>;
}

/**
 * 更新部门
 * @param fields
 */
const handleUpdate = async (fields: DepartmentForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateDepartment(fields);
    hide();
    message.success('部门更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('部门更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalOpen, handleUpdateModalOpen, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑部门"
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
            const { data } = await getDepartmentById(currentRow?.id);
            return data;
          } else {
            message.warning("部门id不能为空！");
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
        const success = await handleUpdate(value as DepartmentForm);
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
