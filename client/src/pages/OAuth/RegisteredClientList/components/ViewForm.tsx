import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import FormBody from "@/pages/OAuth/RegisteredClientList/components/FormBody";
import type {OAuth2RegisteredClientDTO } from "@/services/uaa/type/registered-client";

interface UpdateFormProps {
  viewModalVisible: boolean;
  handleViewModalVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<OAuth2RegisteredClientDTO | undefined>>
  loadData: () => Promise<OAuth2RegisteredClientDTO | undefined>;
}

const ViewForm: React.FC<UpdateFormProps> = (props) => {
  const {viewModalVisible, handleViewModalVisible, setCurrentRow, loadData} = props;

  return (
    <DrawerForm
      title="客户端详情"
      width="748px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleViewModalVisible(false);
          if (!viewModalVisible) {
            setCurrentRow(undefined);
          }
        }
      }}
      request={loadData}
      visible={viewModalVisible}
      onVisibleChange={handleViewModalVisible}
    >
      <FormBody formType="view" />
    </DrawerForm>
  )
}

export default ViewForm;
