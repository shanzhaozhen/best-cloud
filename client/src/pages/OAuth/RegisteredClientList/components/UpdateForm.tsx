import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {RegisteredClientForm, RegisteredClientVO} from "@/services/uaa/type/registered-client";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {getRegisteredClientById, updateRegisteredClient} from "@/services/uaa/registered-client";
import FormBody from "@/pages/OAuth/RegisteredClientList/components/FormBody";

interface UpdateFormProps {
  updateModalVisible: boolean;
  handleUpdateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<RegisteredClientVO | undefined>>
  currentRow: Partial<RegisteredClientForm | undefined>;
}

/**
 * 更新客户端
 * @param fields
 */
const handleUpdate = async (fields: RegisteredClientForm) => {
  const hide = message.loading('更新中...');
  try {
    await updateRegisteredClient(fields);
    hide();
    message.success('客户端更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('客户端更新失败，请重试!');
    return false;
  }
};

const UpdateForm: React.FC<UpdateFormProps> = (props) => {
  const {updateModalVisible, handleUpdateModalVisible, actionRef, setCurrentRow, currentRow} = props;

  return (
    <DrawerForm
      title="编辑客户端"
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
            const { data } = await getRegisteredClientById(currentRow?.id);
            return data;
          } else {
            message.warn("客户端id不能为空");
            handleUpdateModalVisible(false);
          }
        } catch (e) {
          message.warn("远程获取数据失败！");
          handleUpdateModalVisible(false);
        }
        return undefined;
      }}
      visible={updateModalVisible}
      onVisibleChange={handleUpdateModalVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as RegisteredClientForm);
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
