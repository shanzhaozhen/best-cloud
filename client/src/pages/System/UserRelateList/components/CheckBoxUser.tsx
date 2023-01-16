import React, { useRef, useState } from 'react';
import type { Dispatch, SetStateAction } from 'react';
import {Drawer, Input, message, Modal, Space, Tag} from 'antd';
import type { ActionType, ProColumns ,ColumnsState} from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type {ProDescriptionsItemProps} from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import {convertPageParams} from '@/utils/common';
import type {UserVO} from "@/services/uaa/type/user";
import {getUserPage} from "@/services/uaa/user";

interface CheckBoxUserProps {
  checkBoxUserVisible: boolean;
  handleCheckBoxUserVisible: Dispatch<SetStateAction<boolean>>;
  handleBatchAddUserRelate: (selectRows: UserVO[]) => void;
}

const CheckBoxUser: React.FC<CheckBoxUserProps> = (props) => {
  const { checkBoxUserVisible, handleCheckBoxUserVisible, handleBatchAddUserRelate } = props;

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<UserVO>();
  const [selectedRowsState, setSelectedRows] = useState<UserVO[]>([]);

  // const departmentList = useDepartmentList();

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
                await handleBatchAddUserRelate([record]);
                actionRef.current?.reloadAndRest?.();
              } else {
                message.warning('没有选中有效的用户');
              }
            }}
          >
            添加
          </a>
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
    <Modal
      title="用户选择"
      width={820}
      open={checkBoxUserVisible}
      destroyOnClose
      onCancel={() => {
        handleCheckBoxUserVisible(false);
        actionRef.current?.clearSelected?.();
      }}
      footer={null}
    >
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
        tableAlertOptionRender={({ onCleanSelected }) => {
          return (
            <Space size={16}>
              <a onClick={onCleanSelected}>取消选择</a>
              <a
                onClick={async () => {
                  await handleBatchAddUserRelate(selectedRowsState);
                  onCleanSelected();
                }}
              >
                批量关联
              </a>
            </Space>
          );
        }}
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
          onChange: (_, selectedRows) => setSelectedRows(selectedRows),
        }}
      />
      <Drawer
        width={600}
        open={!!currentRow}
        onClose={() => {
          setCurrentRow(undefined);
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
    </Modal>
  );
};

export default CheckBoxUser;
