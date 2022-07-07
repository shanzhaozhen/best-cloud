import type {Dispatch, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import FormBody from "@/pages/System/UserList/components/FormBody";
import type {UserForm} from "@/services/uaa/type/user";
import type {UserVO} from "@/services/uaa/type/user";

interface CreateFormProps {
  viewModalVisible: boolean;
  handleViewModalVisible: Dispatch<SetStateAction<boolean>>;
  setCurrentRow: Dispatch<SetStateAction<UserVO | undefined>>
  values: Partial<UserForm>;
}

const ViewForm: React.FC<CreateFormProps> = (props) => {
  const { viewModalVisible, handleViewModalVisible, setCurrentRow, values } = props;

  return (
    <DrawerForm
      title="用户详情"
      width="748px"
      visible={viewModalVisible}
      onVisibleChange={handleViewModalVisible}
      submitter={false}
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleViewModalVisible(false);
          if (!viewModalVisible) {
            setCurrentRow(undefined);
          }
        }
      }}
      initialValues={values}
    >
      <FormBody formType="view" />
    </DrawerForm>
  );
}

export default ViewForm;
