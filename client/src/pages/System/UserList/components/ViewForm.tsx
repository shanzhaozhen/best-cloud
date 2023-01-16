import type {Dispatch, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import FormBody from "@/pages/System/UserList/components/FormBody";
import type {UserForm} from "@/services/uaa/type/user";
import type {UserVO} from "@/services/uaa/type/user";

interface CreateFormProps {
  viewModalOpen: boolean;
  handleViewModalOpen: Dispatch<SetStateAction<boolean>>;
  setCurrentRow: Dispatch<SetStateAction<UserVO | undefined>>
  values: Partial<UserForm | UserVO>;
}

const ViewForm: React.FC<CreateFormProps> = (props) => {
  const { viewModalOpen, handleViewModalOpen, setCurrentRow, values } = props;

  return (
    <DrawerForm
      title="用户详情"
      width="748px"
      open={viewModalOpen}
      onOpenChange={handleViewModalOpen}
      submitter={false}
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleViewModalOpen(false);
          if (!viewModalOpen) {
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
