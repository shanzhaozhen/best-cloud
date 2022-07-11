import {ExclamationCircleOutlined, PlusOutlined} from '@ant-design/icons';
import {Button, message, Drawer, Popconfirm, Space, Tag, Modal} from 'antd';
import React, { useState, useRef } from 'react';
import { PageContainer, FooterToolbar } from '@ant-design/pro-layout';
import type { ProColumns, ActionType } from '@ant-design/pro-table';
import ProTable from '@ant-design/pro-table';
import type { ProDescriptionsItemProps } from '@ant-design/pro-descriptions';
import ProDescriptions from '@ant-design/pro-descriptions';
import type {MenuVO} from "@/services/uaa/type/menu";
import {
  batchDeleteMenu,
  deleteMenu,
  getMenuById,
  getMenuByPid,
} from "@/services/uaa/menu";
import type {PageParams} from "@/services/common/typings";
import CreateForm from "@/pages/System/MenuList/components/CreateForm";
import UpdateForm from "@/pages/System/MenuList/components/UpdateForm";
import * as Icon from '@ant-design/icons';

/**
 * 删除菜单
 * @param selectedRows
 */
const handleRemove = async (selectedRows: MenuVO[]) => {
  const hide = message.loading('正在删除');
  if (!selectedRows) return true;
  try {
    await batchDeleteMenu(selectedRows.map((row) => row.id));
    hide();
    message.success('删除成功！');
    return true;
  } catch (error) {
    hide();
    message.error('删除失败，请重试！');
    return false;
  }
};

const MenuList: React.FC = () => {

  const [createModalVisible, handleCreateModalVisible] = useState<boolean>(false);
  const [updateModalVisible, handleUpdateModalVisible] = useState<boolean>(false);

  const [showDetail, setShowDetail] = useState<boolean>(false);

  const actionRef = useRef<ActionType>();
  const [menuData, setMenuData] = useState<MenuVO[]>([]);
  const [currentRow, setCurrentRow] = useState<MenuVO>();
  const [selectedRowsState, setSelectedRows] = useState<MenuVO[]>([]);

  const updateMenuData = (
    list: MenuVO[],
    key: number | undefined,
    children: MenuVO[] | null,
  ): MenuVO[] => {
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
          children: updateMenuData(node.children, key, children),
        };
      }
      return { ...node };
    });
  };

  const onLoadMenuChildren = async (expanded: boolean, record: MenuVO) => {
    if (expanded && record && record.id) {
      const { data } = await getMenuByPid(record.id);
      setMenuData((menu) =>
        updateMenuData(
          menu,
          record.id,
          data && data.length > 0 ? data.map((item) => ({
            ...item,
            children: []
          })) : []
        ),
      );
    }
  };

  const columns: ProColumns<MenuVO>[] = [
    {
      title: '菜单名称',
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
      title: '菜单名称（本地化）',
      dataIndex: 'locale',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '菜单路径',
      dataIndex: 'path',
      valueType: 'text',
      hideInSearch: true,
    },
    {
      title: '图标',
      dataIndex: 'icon',
      valueType: 'text',
      align: 'center',
      hideInSearch: true,
      render: (_, record) => (
        <Space>{record.icon && React.createElement(Icon[record.icon])}</Space>
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
      title: '菜单是否隐藏',
      dataIndex: 'hideInMenu',
      align: 'center',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.hideInMenu ? <Tag color="default">是</Tag> : <Tag color="green">否</Tag>}
        </Space>
      ),
    },
    {
      title: '隐藏子节点',
      dataIndex: 'hideChildrenInMenu',
      align: 'center',
      hideInSearch: true,
      render: (_, entity) => (
        <Space>
          {entity.hideChildrenInMenu ? <Tag color="default">是</Tag> : <Tag color="green">否</Tag>}
        </Space>
      ),
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
              const { data } = await getMenuById(record.id);
              setCurrentRow(data || {});
              handleUpdateModalVisible(true);
            } else {
              message.warn('没有选中有效的菜单');
            }
          }}
        >
          修改
        </a>,
        <Popconfirm
          key="delete"
          title="确定删除该菜单节点?"
          onConfirm={async () => {
            if (record && record.id) {
              if (record.children && record.children.length > 0) {
                message.warn('该菜单节点存在子节点，删除已被拒绝');
                return;
              }
              await deleteMenu(record.id);
              message.success('删除成功！');
              actionRef.current?.reloadAndRest?.();
            } else {
              message.warn('没有选中有效的菜单');
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
      <ProTable<MenuVO, PageParams>
        headerTitle="菜单列表"
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
            <PlusOutlined /> 新建菜单
          </Button>,
        ]}
        expandable={{
          onExpand: onLoadMenuChildren
        }}
        request={async () => {
          const { data } = await getMenuByPid();

          const list = data ? data.map((item) => ({
            ...item,
            children: [],
          })) : [];

          setMenuData(list);

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
        dataSource={menuData}
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
                content: '确定删除选中的菜单吗？（包含子节点）',
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
          <ProDescriptions<MenuVO>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.id,
            }}
            columns={columns as ProDescriptionsItemProps<MenuVO>[]}
          />
        )}
      </Drawer>
    </PageContainer>
  );
};

export default MenuList;
