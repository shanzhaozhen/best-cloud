import {DownOutlined, ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {Button, message, Drawer, Input, Modal} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable, {TableDropdown} from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {RoleVO} from "@/services/uaa/type/role";
import {batchDeleteRole, deleteRole, getRolePage} from "@/services/uaa/role";
import type {PageParams} from "@/services/common/typings";
import {convertPageParams} from "@/utils/common";
import CreateForm from "@/pages/System/RoleList/components/CreateForm";
import UpdateForm from "@/pages/System/RoleList/components/UpdateForm";
import UserRelateList from "@/pages/System/UserRelateList";
import {getUserPageByRoleId} from "@/services/uaa/user";
import {addUserRoles, deleteUserRoles} from "@/services/uaa/user-role";
import type {UserVO} from "@/services/uaa/type/user";
import type {SortOrder} from "antd/es/table/interface";
import RoleAuthorize from "@/pages/System/RoleList/components/RoleAuthorize";

const RoleList: React.FC = () => {
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [userRelateListVisible, handleUserRelateListVisible] = useState<boolean>(false);
  const [roleAuthorizeVisible, handleRoleAuthorizeVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const userRoleActionRef = useRef<ActionType>();

  const [currentRow, setCurrentRow] = useState<RoleVO>();
  const [selectedRowsState, setSelectedRows] = useState<RoleVO[]>([]);


  /**
   * 删除角色
   * @param selectedRows
   */
  const handleRemove = async (selectedRows: RoleVO[]) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      await batchDeleteRole(selectedRows.map((row) => row.id));
      hide();
      message.success('删除成功！');
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试！');
      return false;
    }
  };

  /**
   * 批量添加用户角色关联
   * @param selectedUserRoleRows
   */
  const handleBatchAddUserRole = async (selectedUserRoleRows: UserVO[]) => {
    const hide = message.loading('正在添加...');
    if (!selectedUserRoleRows) return true;
    try {
      const userIds = selectedUserRoleRows.map((user) => user.id);
      await addUserRoles({
        userIds,
        roleId: currentRow?.id,
      });
      hide();
      message.success('添加成功');
      userRoleActionRef.current?.reloadAndRest?.();
      return true;
    } catch (error) {
      hide();
      message.error('添加失败，请重试');
      return false;
    }
  };

  /**
   * 取消用户角色关联
   * @param record
   */
  const handleDeleteUserRole = async (record: UserVO) => {
    if (record && record.id) {
      await deleteUserRoles({
        userIds: [record.id],
        roleId: currentRow?.id,
      });
      message.success('取消关联成功！');
      userRoleActionRef.current?.reloadAndRest?.();
    } else {
      message.warn('没有选中有效的用户');
    }
  };

  /**
   * 批量取消用户角色关联
   * @param selectedUserRoleRows
   */
  const handleBatchDeleteUserRole = (selectedUserRoleRows: UserVO[]) => {
    Modal.confirm({
      title: '请确认',
      icon: <ExclamationCircleOutlined />,
      content: '确定批量取消勾选中的用户与角色的关联吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在取消关联...');
        if (!selectedUserRoleRows) return true;
        try {
          await deleteUserRoles({
            userIds: selectedUserRoleRows.map((selectedRow) => selectedRow.id) || [],
            roleId: currentRow?.id,
          });
          hide();
          message.success('取消关联成功，即将刷新');
          userRoleActionRef.current?.reloadAndRest?.();
          return true;
        } catch (error) {
          hide();
          message.error('取消关联失败，请重试');
          return false;
        }
      },
    });
  };

  const columns: ProColumns<RoleVO>[] = [
    {
      title: '关键字',
      key: 'keyword',
      hideInTable: true,
      hideInForm: true,
      dataIndex: 'keyword',
      renderFormItem: () => {
        return <Input placeholder="请输入关键字" />;
      },
    },
    {
      dataIndex: 'index',
      valueType: 'indexBorder',
      width: 48,
    },
    {
      title: '角色名称',
      dataIndex: 'name',
      valueType: 'text',
      sorter: true,
      hideInSearch: true,
      formItemProps: {
        rules: [
          {
            required: true,
            message: '此项为必填项',
          },
        ],
      },
      render: (dom, entity) => {
        return (
          <a
            onClick={() => {
              setCurrentRow(entity);
              setShowDetail(true);
            }}
          >
            {dom}
          </a>
        );
      },
    },
    {
      title: '角色编码',
      dataIndex: 'code',
      valueType: 'text',
      hideInSearch: true,
      sorter: true,
    },
    {
      title: '描述',
      dataIndex: 'description',
      hideInSearch: true,
      valueType: 'text',
    },
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      valueType: 'dateTime',
      sorter: true,
      defaultSortOrder: 'descend',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '修改时间',
      dataIndex: 'lastModifiedDate',
      valueType: 'dateTime',
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, entity) => [
        <a
          key="view-user"
          onClick={async () => {
            if (entity && entity.id) {
              setCurrentRow(entity);
              handleUserRelateListVisible(true);
            } else {
              message.warn('没有选中有效的角色');
            }
          }}
        >
          用户
        </a>,
        <a
          key="grant"
          onClick={async () => {
            if (entity && entity.id) {
              setCurrentRow(entity)
              handleRoleAuthorizeVisible(true);
              // message.error(res.message || `没有获取到角色信息（id:${entity.id}）`);
            } else {
              message.warn('没有选中有效的角色');
            }
          }}
        >
          授权
        </a>,
        <TableDropdown
          key="actionGroup"
          onSelect={async (key) => {
            if (key === 'edit') {
              setShowDetail(false);
              if (entity && entity.id) {
                setCurrentRow(entity);
                handleUpdateModalVisible(true);
                // message.error(res.message || `没有获取到角色信息（id:${entity.id}）`);
              } else {
                message.warn('没有选中有效的角色');
              }
            } else if (key === 'delete') {
              Modal.confirm({
                title: '请确认',
                icon: <ExclamationCircleOutlined />,
                content: '确定删除改角色？',
                onOk: async () => {
                  if (entity && entity.id) {
                    await deleteRole(entity.id);
                    message.success('删除成功！');
                    actionRef.current?.reloadAndRest?.();
                  } else {
                    message.warn('没有选中有效的角色！');
                  }
                },
                onCancel() {
                },
              });
            }
          }}
          menus={[
            { key: 'edit', name: '编辑' },
            { key: 'delete', name: '删除' },
          ]}
        >
          更多
          <DownOutlined />
        </TableDropdown>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<RoleVO, PageParams>
        headerTitle="角色列表"
        actionRef={actionRef}
        rowKey="id"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="add"
            onClick={() => {
              handleCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建角色
          </Button>,
        ]}
        request={async (params, sort) => {
          const { data } = await getRolePage(convertPageParams(params, sort));
          return {
            // success 请返回 true，
            // 不然 table 会停止解析数据，即使有数据
            success: true,
            data: data ? data.records : [],
            // 不传会使用 data 的长度，如果是分页一定要传
            total: data ? data.total : 0,
          };
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />
      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择 <a style={{ fontWeight: 600 }}>{selectedRowsState.length}</a> 项&nbsp;&nbsp;
            </div>
          }
        >
          <Button
            type="primary"
            danger
            onClick={() => {
              Modal.confirm({
                title: '请确认',
                icon: <ExclamationCircleOutlined />,
                content: '确定删除选中的角色吗？',
                onOk: async () => {
                  await handleRemove(selectedRowsState);
                  setSelectedRows([]);
                  actionRef.current?.reloadAndRest?.();
                },
                onCancel() {
                },
              });
            }}
          >
            删除
          </Button>
        </FooterToolbar>
      )}

      <CreateForm
        createModalVisible={createModalVisible}
        handleCreateModalVisible={handleCreateModalVisible}
        actionRef={actionRef}
      />

      <UpdateForm
        updateModalVisible={updateModalVisible}
        handleUpdateModalVisible={handleUpdateModalVisible}
        actionRef={actionRef}
        setCurrentRow={setCurrentRow}
        currentRow={currentRow}
      />

      <Drawer
        width={600}
        visible={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.id && (
          <ProDescriptions<RoleVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<RoleVO>[]}
          />
        )}
      </Drawer>

      {currentRow && Object.keys(currentRow).length ? (
        <Drawer
          title="角色分配"
          placement="right"
          width={820}
          zIndex={500}
          destroyOnClose
          onClose={() => {
            setCurrentRow({});
            handleUserRelateListVisible(false);
          }}
          visible={userRelateListVisible}
        >
          <UserRelateList
            userRelateActionRef={userRoleActionRef}
            handleBatchAddUserRelate={handleBatchAddUserRole}
            handleDeleteUserRelate={handleDeleteUserRole}
            handleBatchDeleteUserRelate={handleBatchDeleteUserRole}
            queryList={async (params: PageParams, sorter: Record<string, SortOrder>) =>
              await getUserPageByRoleId({
                ...convertPageParams(params, sorter),
                roleId: currentRow.id
              })
            }
            callBackFinish={async (userId) => {
              await addUserRoles({
                userIds: [userId],
                roleId: currentRow?.id,
              });
            }}
          />
        </Drawer>
      ) : null}

      <RoleAuthorize
        roleAuthorizeVisible={roleAuthorizeVisible}
        handleRoleAuthorizeVisible={handleRoleAuthorizeVisible}
        actionRef={actionRef}
        setCurrentRow={setCurrentRow}
        currentRow={currentRow}
      />

    </PageContainer>
  );
};

export default RoleList;
