import React, { useRef, useState } from 'react';
import type { Dispatch, SetStateAction } from 'react';
import { Drawer, Input, message, Modal, Space } from 'antd';
import type { ActionType, ProColumns } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import ProDescriptions from '@ant-design/pro-descriptions';
import {convertPageParams} from '@/utils/common';
import type {RoleVO} from "@/services/uaa/type/role";
import type {UserVO} from "@/services/uaa/type/user";
import {getUserPage} from "@/services/uaa/user";

interface CheckBoxUserProps {
  checkBoxUserVisible: boolean;
  handleCheckBoxUserVisible: Dispatch<SetStateAction<boolean>>;
  handleBatchAddUserRelate: (selectRows: RoleVO[]) => void;
  values: RoleVO;
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
      title: '姓名',
      dataIndex: 'name',
      valueType: 'text',
      sorter: true,
      hideInSearch: true,
    },
    {
      title: '昵称',
      dataIndex: 'nickname',
      valueType: 'text',
      hideInSearch: true,
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
                message.warn('没有选中有效的用户');
              }
            }}
          >
            添加
          </a>
        </>
      ),
    },
  ];

  return (
    <Modal
      title="用户选择"
      width={820}
      visible={checkBoxUserVisible}
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
        visible={!!currentRow}
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
            columns={columns}
          />
        )}
      </Drawer>
    </Modal>
  );
};

export default CheckBoxUser;
