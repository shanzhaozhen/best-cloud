import React, { useState } from 'react';
import type { MutableRefObject } from 'react';
import {Button, Divider, Input, message, Popconfirm, Space, Tag} from 'antd';
import type {ActionType, ColumnsState, ProColumns} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import {CheckCircleOutlined, PlusOutlined} from '@ant-design/icons';
import UpdateForm from '@/pages/System/UserList/components/UpdateForm';
import CreateForm from '@/pages/System/UserList/components/CreateForm';
import CheckBoxUser from '@/pages/System/UserRelateList/components/CheckBoxUser';
import type {Page, R} from '@/services/common/typings';
import type { PageParams } from '@/services/common/typings';
import type { SortOrder } from 'antd/lib/table/interface';
import { getPageParams } from '@/utils/common';
import type {UserVO} from "@/services/uaa/type/user";
import {getUserById} from "@/services/uaa/user";

interface UserRelateListProps {
  userRelateActionRef: MutableRefObject<ActionType | undefined>;
  handleBatchAddUserRelate: (selectRows: UserVO[]) => void;
  handleDeleteUserRelate: (record: UserVO) => void;
  handleBatchDeleteUserRelate: (selectRowIds: UserVO[]) => void;
  queryList: (
    params: PageParams,
    sorter: Record<string, SortOrder>,
  ) => Promise<R<Page<UserVO>>>;
  callBackFinish?: (userId: number) => Promise<any>;
}

const UserRelateList: React.FC<UserRelateListProps> = (props) => {
  const {
    userRelateActionRef: actionRef,
    handleBatchAddUserRelate,
    handleDeleteUserRelate,
    handleBatchDeleteUserRelate,
    queryList,
    callBackFinish,
  } = props;

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);
  const [checkBoxUserVisible, handleCheckBoxUserVisible] = useState<boolean>(false);
  const [currentRow, setCurrentRow] = useState<UserVO>();
  const [selectedRowsState, setSelectedRows] = useState<UserVO[]>([]);

  const columns: ProColumns<UserVO>[] = [
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
      title: '用户名',
      dataIndex: 'username',
      valueType: 'text',
      sorter: true,
      hideInSearch: true,
      hideInSetting: true,
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
      title: '所属部门',
      dataIndex: ['departmentInfo', 'name'],
      valueType: 'text',
      hideInSearch: true
    },
    {
      title: '姓名',
      dataIndex: ['userInfo', 'name'],
      valueType: 'text',
      sorter: 'u.name',
      hideInSearch: true,
    },
    {
      title: '昵称',
      dataIndex: ['userInfo', 'nickname'],
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '性别',
      dataIndex: ['userInfo', 'sex'],
      valueType: 'text',
      valueEnum: {
        0: { text: '男' },
        1: { text: '女' },
      },
      hideInSearch: true,
    },
    {
      title: '头像',
      dataIndex: ['userInfo', 'avatar'],
      valueType: 'image',
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
          {entity.enabled ? <Tag color="green">否</Tag> : <Tag color="red">是</Tag>}
        </Space>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createdDate',
      valueType: 'dateTime',
      sorter: true,
      hideInSearch: true,
      hideInForm: true,
    },
    {
      title: '修改时间',
      dataIndex: 'lastModifiedDate',
      valueType: 'dateTime',
      sorter: true,
      hideInSearch: true,
      hideInForm: true,
    },
    // {
    //   title: '所属部门',
    //   dataIndex: 'depId',
    //   hideInSearch: true,
    //   valueType: 'select',
    //   renderText: (_, record) => tableFilter(record.depId, departmentList, '未分配'),
    // },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => (
        <>
          <a
            onClick={async () => {
              if (record && record.id) {
                const { data } = await getUserById(record.id);
                setCurrentRow(data || {});
                handleUpdateModalVisible(true);
                // message.error(res.message || `没有获取到用户信息（id:${record.id}）`);
              } else {
                message.warn('没有选中有效的用户');
              }
            }}
          >
            编辑
          </a>
          <Divider type="vertical" />
          <Popconfirm
            title="确定取消该用户的关联？"
            arrowPointAtCenter
            onConfirm={() => handleDeleteUserRelate(record)}
            okText="确定"
            cancelText="取消"
          >
            <a href="#">取消关联</a>
          </Popconfirm>
        </>
      ),
    },
  ];

  const [columnsStateMap, setColumnsStateMap] = useState<Record<string, ColumnsState>>({
    'userInfo,sex': {
      show: false
    },
    'userInfo,avatar': {
      show: false
    },
    accountNonExpired: {
      show: false
    },
    accountNonLocked: {
      show: false
    },
    credentialsNonExpired: {
      show: false
    },
    enabled: {
      show: false
    },
    createdDate: {
      show: false
    },
    lastModifiedDate: {
      show: false
    },
  });

  return (
    <>
      <ProTable<UserVO>
        actionRef={actionRef}
        rowKey="id"
        cardBordered
        bordered={true}
        search={{
          labelWidth: 120,
        }}
        columnsState={{
          value: columnsStateMap,
          onChange: setColumnsStateMap,
        }}
        toolBarRender={() => [
          <Button
            key="add-new"
            type="primary"
            onClick={() => handleCreateModalVisible(true)}
          >
            <PlusOutlined /> 新建用户
          </Button>,
          <Button
            key="add-had"
            type="primary"
            onClick={() => {
              handleCheckBoxUserVisible(true);
            }}
          >
            <CheckCircleOutlined /> 已有用户
          </Button>,
        ]}
        tableAlertOptionRender={({ onCleanSelected }) => {
          return (
            <Space size={16}>
              <a onClick={onCleanSelected}>取消选择</a>
              <a
                onClick={async () => {
                  handleBatchDeleteUserRelate(selectedRowsState);
                }}
              >
                批量取消关联
              </a>
            </Space>
          );
        }}
        request={async (params, sorter) => {
          const { data } = await queryList(getPageParams(params), sorter);
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
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
      />
      <CreateForm
        createModalVisible={createModalVisible}
        handleCreateModalVisible={handleCreateModalVisible}
        actionRef={actionRef}
        callBackFinish={callBackFinish}
      />
      {currentRow && Object.keys(currentRow).length ? (
        <UpdateForm
          updateModalVisible={updateModalVisible}
          handleUpdateModalVisible={handleUpdateModalVisible}
          values={currentRow}
          actionRef={actionRef}
          setCurrentRow={setCurrentRow}
        />
      ) : null}
      <CheckBoxUser
        checkBoxUserVisible={checkBoxUserVisible}
        handleCheckBoxUserVisible={handleCheckBoxUserVisible}
        handleBatchAddUserRelate={handleBatchAddUserRelate}
      />
    </>
  );
};

export default UserRelateList;
