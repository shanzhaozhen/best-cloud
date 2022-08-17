import type {Dispatch, MutableRefObject, SetStateAction} from "react";
import React from "react";
import {DrawerForm, ProFormText} from "@ant-design/pro-form";
import type {ActionType} from "@ant-design/pro-table";
import {Col, message} from "antd";
import {ProFormItem} from "@ant-design/pro-components";
import type {RoleAuthorizeData} from "@/services/uaa/type/role";
import FormTree from "@/components/FormTree";
import {useMenuTree} from "@/utils/menu";
import {usePermissionTree} from "@/utils/permission";
import {getRoleAuthorizeById, updateRoleAuthorize} from "@/services/uaa/role";
import type {RoleForm, RoleVO} from "@/services/uaa/type/role";

interface AuthorizeFormProps {
  roleAuthorizeVisible: boolean;
  handleRoleAuthorizeVisible: Dispatch<SetStateAction<boolean>>;
  actionRef: MutableRefObject<ActionType | undefined>;
  setCurrentRow: Dispatch<SetStateAction<RoleVO | undefined>>
  currentRow: Partial<RoleForm | undefined>;
}


/**
 * 更新角色授权信息
 * @param fields
 */
const handleUpdate = async (fields: RoleAuthorizeData) => {
  const hide = message.loading('更新中...');
  try {
    await updateRoleAuthorize(fields);
    hide();
    message.success('角色授权更新成功');
    return true;
  } catch (error) {
    hide();
    message.error('角色授权更新失败，请重试!');
    return false;
  }
};

const RoleAuthorize: React.FC<AuthorizeFormProps> = (props) => {

  const {roleAuthorizeVisible, handleRoleAuthorizeVisible, actionRef, setCurrentRow, currentRow} = props;

  // const [loading, setLoading] = useState<boolean>(false);

  const menuTree = useMenuTree();
  const permissionTree = usePermissionTree();

  return (
    <DrawerForm
      title="角色授权"
      width="548px"
      drawerProps={{
        destroyOnClose: true,
        onClose: () => {
          handleRoleAuthorizeVisible(false);
          if (!roleAuthorizeVisible) {
            setCurrentRow(undefined);
          }
        }
      }}
      request={async () => {
        try {
          if (currentRow?.id) {
            const { data } = await getRoleAuthorizeById(currentRow?.id);
            return data;
          } else {
            message.warn("权限id不能为空");
            handleRoleAuthorizeVisible(false);
          }
        } catch (e) {
          message.warn("远程获取数据失败！");
          handleRoleAuthorizeVisible(false);
        }
        return undefined;
      }}
      visible={roleAuthorizeVisible}
      onVisibleChange={handleRoleAuthorizeVisible}
      onFinish={async (value) => {
        const success = await handleUpdate(value as RoleAuthorizeData);
        if (success) {
          handleRoleAuthorizeVisible(false);
          setCurrentRow(undefined);
          if (actionRef.current) {
            actionRef.current.reload();
          }
        }
      }}
    >
      <ProFormText name="roleId" label="角色id" hidden={true} />
      <Col span={24}>
        <ProFormItem name="permissionIds" label="权限分配">
          { permissionTree && permissionTree.length > 0 ? (
            <FormTree treeData={permissionTree} />
          ) : (
            <div style={{textAlign: "center"}}>
              <span>（无）</span>
            </div>
          ) }
        </ProFormItem>
      </Col>
      <Col span={24}>
        <ProFormItem name="menuIds" label="菜单分配">
          { menuTree && menuTree.length > 0 ? (
            <FormTree treeData={menuTree} />
          ) : (
            <div style={{textAlign: "center"}}>
              <span>（无）</span>
            </div>
          ) }
        </ProFormItem>
      </Col>
    </DrawerForm>
  );
};

export default RoleAuthorize;


