import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import type {RoleForm} from "@/services/uaa/type/role";
import {DrawerForm} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {Col, message, Tree} from "antd";
import {updateRole} from "@/services/uaa/role";
import {ProFormItem} from "@ant-design/pro-components";
import type {RoleAuthorizeData} from "@/services/uaa/type/role";

interface AuthorizeFormProps {
  roleAuthorizeVisible: boolean;
  handleRoleAuthorizeVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setRoleAuthorizeData: Dispatch<SetStateAction<RoleAuthorizeData | undefined>>
  values: Partial<RoleAuthorizeData>;
}


/**
 * 更新角色授权信息
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

const RoleAuthorize: React.FC<AuthorizeFormProps> = (props) => {

  const {roleAuthorizeVisible, handleRoleAuthorizeVisible, actionRef, setRoleAuthorizeData, values} = props;

  return (
    <DrawerForm
      title="角色授权"
      width="748px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleRoleAuthorizeVisible(false);
          if (!roleAuthorizeVisible) {
            setRoleAuthorizeData(undefined);
          }
        }
      }}
      initialValues={values}
      visible={roleAuthorizeVisible}
      onVisibleChange={handleRoleAuthorizeVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as RoleForm);
        if (success) {
          handleRoleAuthorizeVisible(false);
          setRoleAuthorizeData(undefined);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <Col span={24}>
        <ProFormItem name="permissionIds" label="权限分配">
          <Tree/>
        </ProFormItem>
      </Col>
      <Col span={24}>
        <ProFormItem name="menuIds" label="菜单分配">
          <Tree/>
        </ProFormItem>
      </Col>
    </DrawerForm>
  );
};

export default RoleAuthorize;
