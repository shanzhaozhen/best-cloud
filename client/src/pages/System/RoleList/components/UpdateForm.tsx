import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {RoleForm, RoleVO} from "@/services/uaa/type/role";
import FormBody from "@/pages/System/RoleList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getRoleById, updateRole} from "@/services/uaa/role";

interface UpdateFormProps {
  updateModalVisible: boolean;
  handleUpdateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<RoleVO | undefined>>
  currentRow: Partial<RoleForm | undefined>;
}

/**
 * 更新角色
 * @param fields
 */
const handleUpdate = async (fields: RoleForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateRole(fields);
    hide();
    message.success('角色更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('角色更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalVisible, handleUpdateModalVisible, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑角色"
      width="748px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleUpdateModalVisible(false);
          if (!updateModalVisible) {
            setCurrentRow(undefined);
          }
        }
      }}
      request={async () => {
        try {
          if (currentRow?.id) {
            const { data } = await getRoleById(currentRow?.id);
            return data;
          } else {
            message.warning("角色id不能为空");
            handleUpdateModalVisible(false);
          }
        } catch (e) {
          message.warning("远程获取数据失败！");
          handleUpdateModalVisible(false);
        }
        return undefined;
      }}
      visible={updateModalVisible}
      onVisibleChange={handleUpdateModalVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as RoleForm);
        if (success) {
          handleUpdateModalVisible(false);
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
