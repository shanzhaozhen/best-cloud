import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {message} from "antd";
import {updateRegisteredClient} from "@/services/uaa/registered-client";
import FormBody from "@/pages/OAuth/RegisteredClientList/components/FormBody";
import type {OAuth2RegisteredClientDTO, OAuth2RegisteredClientForm } from "@/services/uaa/type/registered-client";
import {convertRegisteredClient} from "@/pages/OAuth/RegisteredClientList";

interface UpdateFormProps {
  updateModalVisible: boolean;
  handleUpdateModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<OAuth2RegisteredClientDTO | undefined>>
  loadData: () => Promise<OAuth2RegisteredClientDTO | undefined>;
}

/**
 * 更新客户端
 * @param fields
 */
const handleUpdate = async (fields: OAuth2RegisteredClientForm) => {
  const hide = message.loading('更新中...');
  try {
    convertRegisteredClient(fields);
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
  const {updateModalVisible, handleUpdateModalVisible, actionRef, setCurrentRow, loadData} = props;

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
      request={loadData}
      visible={updateModalVisible}
      onVisibleChange={handleUpdateModalVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as OAuth2RegisteredClientForm);
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
