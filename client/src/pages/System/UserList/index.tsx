import { PlusOutlined } from '@ant-design/icons';
import {Button, message, Drawer, Popconfirm, Space, Tag} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {UserVO} from "@/services/uaa/type/user";
import {batchDeleteUser, deleteUser, getUserById, getUserPage} from "@/services/uaa/user";
import type {PageParams} from "@/services/common/typings";
import {convertPageParams} from "@/utils/common";
import CreateForm from "@/pages/System/UserList/components/CreateForm";
import UpdateForm from "@/pages/System/UserList/components/UpdateForm";

/**
 * 删除用户
 * @param selectedRows
 */
const handleRemove = async (selectedRows: UserVO[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await batchDeleteUser(selectedRows.map((row) => row.id));
    hide();
    message.success('删除成功过！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试！');
    return false;
  }
};

const UserList: React.FC = () => {

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<UserVO>();
  const [selectedRowsState, setSelectedRows] = useState<UserVO[]>([]);

  const columns: ProColumns<UserVO>[] = [
    // {
    //   title: '关键字',
    //   key: 'keyword',
    //   hideInTable: true,
    //   hideInForm: true,
    //   dataIndex: 'keyword',
    //   renderFormItem: () => {
    //     return <Input placeholder="请输入关键字" />;
    //   },
    // },
    {
      dataIndex: 'index',
      valueType: 'indexBorder',
      width: 48,
    },
    {
      title: '用户名',
      dataIndex: 'username',
      valueType: 'text',
      sorter: true,
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
              console.log('entity: ', entity)
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
      title: '姓名',
      dataIndex: 'name',
      valueType: 'text',
      sorter: true,
    },
    {
      title: '昵称',
      dataIndex: 'nickname',
      valueType: 'text',
    },
    {
      title: '性别',
      dataIndex: 'sex',
      valueType: 'text',
      valueEnum: {
        0: { text: '男' },
        1: { text: '女' },
      },
    },
    {
      title: '头像',
      dataIndex: 'avatar',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '是否过期',
      dataIndex: 'accountNonExpired',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.accountNonExpired ? <Tag color="green">未过期</Tag> : <Tag color="red">已过期</Tag>}
        </Space>
      ),
    },
    {
      title: '是否锁定',
      dataIndex: 'accountNonLocked',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.accountNonLocked ? <Tag color="green">否</Tag> : <Tag color="red">是</Tag>}
        </Space>
      ),
    },
    {
      title: '密码过期',
      dataIndex: 'credentialsNonExpired',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.credentialsNonExpired ? <Tag color="green">未过期</Tag> : <Tag color="red">已过期</Tag>}
        </Space>
      ),
    },
    {
      title: '是否禁用',
      dataIndex: 'enabled',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.enabled ? <Tag color="green">可用</Tag> : <Tag color="red">禁用</Tag>}
        </Space>
      ),
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
          key="update"
          onClick={async () => {
            if (entity && entity.id) {
              const { data } = await getUserById(entity.id);
              setCurrentRow(data || {});
              handleUpdateModalVisible(true);
              // message.error(res.message || `没有获取到用户信息（id:${entity.id}）`);
            } else {
              message.warn('没有选中有效的用户');
            }
          }}
        >
          修改
        </a>,
        <Popconfirm
          key="delete"
          title="确定删除该用户?"
          onConfirm={async () => {
            if (entity && entity.id) {
              await deleteUser(entity.id);
              message.success('删除成功！');
              actionRef.current?.reloadAndRest?.();
            } else {
              message.warn('没有选中有效的用户');
            }
          }}
          okText="确定"
          cancelText="取消"
        >
          <a href="#">删除</a>
        </Popconfirm>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<UserVO, PageParams>
        headerTitle="用户列表"
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
            <PlusOutlined /> 新建用户
          </Button>,
        ]}
        request={async (params, sort) => {
          const { data } = await getUserPage(convertPageParams(params, sort));
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
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
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
        values={currentRow || {}}
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
          <ProDescriptions<UserVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<UserVO>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default UserList;
