import { PlusOutlined } from '@ant-design/icons';
import {Button, message, Drawer, Popconfirm, Input} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {RoleVO} from "@/services/uaa/type/role";
import {batchDeleteRole, deleteRole, getRoleById, getRolePage} from "@/services/uaa/role";
import type {PageParams} from "@/services/common/typings";
import {convertPageParams} from "@/utils/common";
import CreateForm from "@/pages/System/RoleList/components/CreateForm";
import UpdateForm from "@/pages/System/RoleList/components/UpdateForm";

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

const RoleList: React.FC = () => {

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<RoleVO>();
  const [selectedRowsState, setSelectedRows] = useState<RoleVO[]>([]);

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
      title: '角色代码',
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
          key="update"
          onClick={async () => {
            if (entity && entity.id) {
              const { data } = await getRoleById(entity.id);
              setCurrentRow(data || {});
              handleUpdateModalVisible(true);
              // message.error(res.message || `没有获取到角色信息（id:${entity.id}）`);
            } else {
              message.warn('没有选中有效的角色');
            }
          }}
        >
          修改
        </a>,
        <Popconfirm
          key="delete"
          title="确定删除该角色?"
          onConfirm={async () => {
            if (entity && entity.id) {
              await deleteRole(entity.id);
              message.success('删除成功！');
              actionRef.current?.reloadAndRest?.();
            } else {
              message.warn('没有选中有效的角色');
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
    </PageContainer>
  );
};

export default RoleList;
