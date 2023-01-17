import type {Dispatch, SetStateAction} from "react";
import React from "react";
import {DrawerForm} from "@ant-design/pro-form";
import FormBody from "@/pages/System/UserList/components/FormBody";
import type {OAuth2UserForm} from "@/services/oauth/type/oauth2-user";
import type {OAuth2UserVO} from "@/services/oauth/type/oauth2-user";

interface CreateFormProps {
  viewModalOpen: boolean;
  handleViewModalOpen: Dispatch<SetStateAction<boolean>>;
  setCurrentRow: Dispatch<SetStateAction<OAuth2UserVO | undefined>>
  values: Partial<OAuth2UserForm | OAuth2UserVO>;
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
