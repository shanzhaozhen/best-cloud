import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {MenuForm, MenuVO} from "@/services/uaa/type/menu";
import FormBody from "@/pages/System/MenuList/components/FormBody";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getMenuById, updateMenu} from "@/services/uaa/menu";

interface UpdateFormProps {
  updateModalOpen: boolean;
  handleUpdateModalOpen: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<MenuVO | undefined>>
  currentRow: Partial<MenuForm | undefined>;
}

/**
 * 更新菜单
 * @param fields
 */
const handleUpdate = async (fields: MenuForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateMenu(fields);
    hide();
    message.success('菜单更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('菜单更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalOpen, handleUpdateModalOpen, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑菜单"
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
            const { data } = await getMenuById(currentRow?.id);
            return data;
          } else {
            message.warning("菜单id不能为空");
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
        const success = await handleUpdate(value as MenuForm);
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
