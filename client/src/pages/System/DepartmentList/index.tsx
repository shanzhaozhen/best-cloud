import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {Button, message, Drawer, Popconfirm, Modal, Input} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {DepartmentVO} from "@/services/uaa/type/department";
import {
  batchDeleteDepartment,
  deleteDepartment,
  getDepartmentByPid, getDepartmentPage,
} from "@/services/uaa/department";
import type {PageParams} from "@/services/common/typings";
import CreateForm from "@/pages/System/DepartmentList/components/CreateForm";
import UpdateForm from "@/pages/System/DepartmentList/components/UpdateForm";
import {convertPageParams} from "@/utils/common";

/**
 * 删除部门
 * @param selectedRows
 */
const handleRemove = async (selectedRows: DepartmentVO[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await batchDeleteDepartment(selectedRows.map((row) => row.id));
    hide();
    message.success('删除成功！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试！');
    return false;
  }
};

const DepartmentList: React.FC = () => {

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const [pagination, setPagination] = useState<false | undefined>(false);

  const actionRef = useRef<ActionType>();
  const [departmentData, setDepartmentData] = useState<DepartmentVO[]>([]);
  const [currentRow, setCurrentRow] = useState<DepartmentVO>();
  const [selectedRowsState, setSelectedRows] = useState<DepartmentVO[]>([]);

  const updateDepartmentData = (
    list: DepartmentVO[],
    key: string | undefined,
    children: DepartmentVO[] | null,
  ): DepartmentVO[] => {
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
          children: updateDepartmentData(node.children, key, children),
        };
      }
      return { ...node };
    });
  };

  const onLoadDepartmentChildren = async (expanded: boolean, record: DepartmentVO) => {
    if (expanded && record && record.id) {
      const { data } = await getDepartmentByPid(record.id);
      setDepartmentData((department) =>
        updateDepartmentData(
          department,
          record.id,
          data && data.length > 0 ? data.map((item) => ({
            ...item,
            children: []
          })) : []
        ),
      );
    }
  };

  const columns: ProColumns<DepartmentVO>[] = [
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
      title: '部门名称',
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
      title: '部门编码',
      dataIndex: 'code',
      valueType: 'text',
      align: 'center',
      hideInSearch: true,
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
              setCurrentRow(record);
              handleUpdateModalVisible(true);
            } else {
              message.warning('没有选中有效的部门');
            }
          }}
        >
          编辑
        </a>,
        <Popconfirm
          key="delete"
          title="确定删除该部门节点?"
          arrowPointAtCenter
          onConfirm={async () => {
            if (record && record.id) {
              if (record.children && record.children.length > 0) {
                message.warning('该部门节点存在子节点，删除已被拒绝');
                return;
              }
              await deleteDepartment(record.id);
              message.success('删除成功！');
              actionRef.current?.reloadAndRest?.();
            } else {
              message.warning('没有选中有效的部门');
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
      <ProTable<DepartmentVO, PageParams>
        headerTitle="部门列表"
        actionRef={actionRef}
        rowKey="id"
        pagination={pagination}
        toolBarRender={() => [
          <Button
            type="primary"
            key="add"
            onClick={() => {
              handleCreateModalVisible(true);
            }}
          >
            <PlusOutlined /> 新建部门
          </Button>,
        ]}
        expandable={{
          onExpand: onLoadDepartmentChildren
        }}
        request={async (params, sort) => {
          let list: DepartmentVO[];
          if (params.keyword) {
            const { data } = await getDepartmentPage(convertPageParams(params, sort))
            list = data && data.records ? data.records : [];
            setPagination(undefined);
          } else {
            const { data } = await getDepartmentByPid();
            list = data ? data.map((item) => ({
              ...item,
              children: [],
            })) : [];
            setPagination(false);
          }

          setDepartmentData(list);

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
        dataSource={departmentData}
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
                content: '确定删除选中的部门吗？（包含子节点）',
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
          <ProDescriptions<DepartmentVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<DepartmentVO>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default DepartmentList;
