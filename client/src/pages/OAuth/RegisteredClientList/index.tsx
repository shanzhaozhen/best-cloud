import React, {useRef, useState} from 'react';
import {FooterToolbar, PageContainer} from '@ant-design/pro-layout';
import type {ActionType, ProColumns} from "@ant-design/pro-table";
import ProTable from "@ant-design/pro-table";
import type {RegisteredClientVO} from "@/services/uaa/type/registered-client";
import {Button, Drawer, Input, message, Modal} from "antd";
import {batchDeleteRegisteredClient, deleteRegisteredClient, getRegisteredClientPage} from "@/services/uaa/registered-client";
import {ExclamationCircleOutlined, PlusOutlined} from "@ant-design/icons";
import type {PageParams} from "@/services/common/typings";
import {convertPageParams} from "@/utils/common";
import type {ProDescriptionsItemProps} from "@ant-design/pro-descriptions";
import ProDescriptions from "@ant-design/pro-descriptions";
import UpdateForm from "@/pages/OAuth/RegisteredClientList/components/UpdateForm";
import CreateForm from "@/pages/OAuth/RegisteredClientList/components/CreateForm";

const RegisteredClientList: React.FC = () => {
  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();

  const [currentRow, setCurrentRow] = useState<RegisteredClientVO>();
  const [selectedRowsState, setSelectedRows] = useState<RegisteredClientVO[]>([]);

  /**
   * 删除客户端
   * @param selectedRows
   */
  const handleRemove = async (selectedRows: RegisteredClientVO[]) => {
    const hide = message.loading('正在删除');
    if (!selectedRows) return true;
    try {
      await batchDeleteRegisteredClient(selectedRows.map((row) => row.id));
      hide();
      message.success('删除成功！');
      return true;
    } catch (error) {
      hide();
      message.error('删除失败，请重试！');
      return false;
    }
  };

  const columns: ProColumns<RegisteredClientVO>[] = [
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
      title: '客户端名称',
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
      title: '客户端编码',
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
          key="edit"
          onClick={async () => {
            setShowDetail(false);
            if (entity && entity.id) {
              setCurrentRow(entity);
              handleUpdateModalVisible(true);
              // message.error(res.message || `没有获取到客户端信息（id:${entity.id}）`);
            } else {
              message.warn('没有选中有效的客户端');
            }
          }}
        >
          编辑
        </a>,
        <a
          key="delete"
          onClick={async () => {
            Modal.confirm({
              title: '请确认',
              icon: <ExclamationCircleOutlined />,
              content: '确定删除改客户端？',
              onOk: async () => {
                if (entity && entity.id) {
                  await deleteRegisteredClient(entity.id);
                  message.success('删除成功！');
                  actionRef.current?.reloadAndRest?.();
                } else {
                  message.warn('没有选中有效的客户端！');
                }
              },
              onCancel() {
              },
            });
          }}
        >
          删除
        </a>
      ],
    },
  ];

  return (
    <PageContainer>
      <ProTable<RegisteredClientVO, PageParams>
        headerTitle="客户端列表"
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
            <PlusOutlined /> 新建客户端
          </Button>,
        ]}
        request={async (params, sort) => {
          const { data } = await getRegisteredClientPage(convertPageParams(params, sort));
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
                content: '确定删除选中的客户端吗？',
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
          <ProDescriptions<RegisteredClientVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<RegisteredClientVO>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default RegisteredClientList;
