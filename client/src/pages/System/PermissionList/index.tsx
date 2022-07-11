import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {Button, message, Drawer, Popconfirm, Space, Tag, Modal} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {PermissionVO} from "@/services/uaa/type/permission";
import {
  batchDeletePermission,
  deletePermission,
  getPermissionById,
  getPermissionByPid,
} from "@/services/uaa/permission";
import type {PageParams} from "@/services/common/typings";
import CreateForm from "@/pages/System/PermissionList/components/CreateForm";
import UpdateForm from "@/pages/System/PermissionList/components/UpdateForm";

/**
 * 删除权限
 * @param selectedRows
 */
const handleRemove = async (selectedRows: PermissionVO[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await batchDeletePermission(selectedRows.map((row) => row.id));
    hide();
    message.success('删除成功！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试！');
    return false;
  }
};

const PermissionList: React.FC = () => {

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [permissionData, setPermissionData] = useState<PermissionVO[]>([]);
  const [currentRow, setCurrentRow] = useState<PermissionVO>();
  const [selectedRowsState, setSelectedRows] = useState<PermissionVO[]>([]);

  const updatePermissionData = (
    list: PermissionVO[],
    key: number | undefined,
    children: PermissionVO[] | null,
  ): PermissionVO[] => {
    return list.map((node) => {
      if (node.id === key) {
        return {
          ...node,
          children: children && children.length > 0 ? children : undefined,
        };
      }
      if (node.children && node.children.length > 0) {
        return {
          ...node,
          children: updatePermissionData(node.children, key, children),
        };
      }
      return { ...node };
    });
  };

  const onLoadPermissionChildren = async (expanded: boolean, record: PermissionVO) => {
    if (expanded && record && record.id) {
      const { data } = await getPermissionByPid(record.id);
      setPermissionData((permission) =>
        updatePermissionData(
          permission,
          record.id,
          data && data.length > 0 ? data.map((item) => ({
            ...item,
            children: []
          })) : []
        ),
      );
    }
  };

  const columns: ProColumns<PermissionVO>[] = [
    {
      title: '名称',
      dataIndex: 'name',
      valueType: 'text',
      hideInSearch: true,
      formItemProps: {
        rules: [
          {
            required: true,
            message: '此项为必填项',
          },
        ],
      },
    },
    {
      title: '权限路由',
      dataIndex: 'path',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '权限类型',
      dataIndex: 'type',
      valueType: 'text',
      hideInSearch: true,
      align: 'center',
      valueEnum: {
        0: { text: '分类' },
        1: { text: 'API' },
      },
      render: (_, record) => (
        <Space>
          {record.type === 0 ? <Tag color="blue">分类</Tag> : <Tag color="green">API</Tag>}
        </Space>
      ),
    },
    {
      title: '排序等级',
      dataIndex: 'priority',
      valueType: 'text',
      align: 'center',
      hideInSearch: true,
    },
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      valueType: 'dateTime',
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
      render: (_, record) => [
        <a
          key="update"
          onClick={async () => {
            if (record && record.id) {
              const { data } = await getPermissionById(record.id);
              setCurrentRow(data || {});
              handleUpdateModalVisible(true);
            } else {
              message.warn('没有选中有效的权限');
            }
          }}
        >
          修改
        </a>,
        <Popconfirm
          key="delete"
          title="确定删除该权限节点?"
          onConfirm={async () => {
            if (record && record.id) {
              if (record.children && record.children.length > 0) {
                message.warn('该权限节点存在子节点，删除已被拒绝');
                return;
              }
              await deletePermission(record.id);
              message.success('删除成功！');
              actionRef.current?.reloadAndRest?.();
            } else {
              message.warn('没有选中有效的权限');
            }
          }}
          okText="确定"
          cancelText="取消"
        >
          <a href="#">删除</a>
        </Popconfirm>,
        <a
          key="add-sub"
          onClick={async () => {
            setCurrentRow({ pid: record.id });
            handleCreateModalVisible(true);
          }}
        >
          添加下级
        </a>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<PermissionVO, PageParams>
        headerTitle="权限列表"
        actionRef={actionRef}
        rowKey="id"
        search={false}
        toolBarRender={() => [
          <Button
            type="primary"
            key="add"
            onClick={() => {
              handleCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建权限
          </Button>,
        ]}
        expandable={{
          onExpand: onLoadPermissionChildren
        }}
        request={async () => {
          const { data } = await getPermissionByPid();

          const list = data ? data.map((item) => ({
            ...item,
            children: [],
          })) : [];

          setPermissionData(list);

          return {
            // success 请返回 true，
            // 不然 table 会停止解析数据，即使有数据
            success: true,
            data: list,
            // 不传会使用 data 的长度，如果是分页一定要传
            total: list.length,
          };
        }}
        columns={columns}
        dataSource={permissionData}
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
                title: '请确认！',
                icon: <ExclamationCircleOutlined />,
                content: '确定删除选中的权限吗？（包含子节点）',
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
        values={currentRow || {}}
      />

      {currentRow && Object.keys(currentRow).length ? (
        <UpdateForm
          updateModalVisible={updateModalVisible}
          handleUpdateModalVisible={handleUpdateModalVisible}
          actionRef={actionRef}
          setCurrentRow={setCurrentRow}
          values={currentRow || {}}
        />
      ) : null}

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
          <ProDescriptions<PermissionVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<PermissionVO>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default PermissionList;
