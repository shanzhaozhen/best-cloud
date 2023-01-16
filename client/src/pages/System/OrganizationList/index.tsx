import {message, Modal, Space, Tabs} from 'antd';
import React, {useRef, useState} from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProCard from "@ant-design/pro-card";
import type {DepartmentVO} from "@/services/uaa/type/department";
import type {ProDescriptionsItemProps} from "@ant-design/pro-descriptions";
import ProDescriptions from "@ant-design/pro-descriptions";
import type {ProColumns} from "@ant-design/pro-table";
import DepartmentTree from "@/pages/System/OrganizationList/components/DepartmentTree";
import UserRelateList from "@/pages/System/UserRelateList";
import type {PageParams} from "@/services/common/typings";
import type {SortOrder} from "antd/es/table/interface";
import {getUserPageByDepartmentId} from "@/services/uaa/user";
import {convertPageParams} from "@/utils/common";
import type {ActionType} from "@ant-design/pro-table";
import type {UserVO} from "@/services/uaa/type/user";
import {ExclamationCircleOutlined} from "@ant-design/icons";
import {addDepartmentUsers, deleteDepartmentUsers} from "@/services/uaa/department-user";


const DepartmentList: React.FC = () => {
  const [selectDepartment, setSelectDepartment] = useState<DepartmentVO | undefined>(undefined);

  const userRelateActionRef = useRef<ActionType>();

  /**
   * 批量添加部门用户关联
   * @param selectedUserRoleRows
   */
  const handleBatchAddDepartmentUser = async (selectedUserRoleRows: UserVO[]) => {
    const hide = message.loading('正在添加...');
    if (!selectedUserRoleRows) return true;
    try {
      const userIds = selectedUserRoleRows.map((user) => user.id);
      await addDepartmentUsers({
        departmentId: selectDepartment?.id,
        userIds,
      });
      hide();
      message.success('添加成功');
      userRelateActionRef.current?.reloadAndRest?.();
      return true;
    } catch (error) {
      hide();
      message.error('添加失败，请重试');
      return false;
    }
  };

  /**
   * 取消部门用户关联
   * @param record
   */
  const handleDeleteDepartmentUser = async (record: UserVO) => {
    if (record && record.id) {
      await deleteDepartmentUsers({
        departmentId: selectDepartment?.id,
        userIds: [record.id],
      });
      message.success('取消关联成功！');
      userRelateActionRef.current?.reloadAndRest?.();
    } else {
      message.warning('没有选中有效的用户');
    }
  };

  /**
   * 批量取消部门用户关联
   * @param selectedUserRoleRows
   */
  const handleBatchDeleteDepartmentUser = (selectedUserRoleRows: UserVO[]) => {
    Modal.confirm({
      title: '请确认',
      icon: <ExclamationCircleOutlined />,
      content: '确定批量取消勾选中的用户与部门的关联吗？',
      okText: '确认',
      cancelText: '取消',
      onOk: async () => {
        const hide = message.loading('正在取消关联...');
        if (!selectedUserRoleRows) return true;
        try {
          await deleteDepartmentUsers({
            departmentId: selectDepartment?.id,
            userIds: selectedUserRoleRows.map((selectedRow) => selectedRow.id) || [],
          });
          hide();
          message.success('取消关联成功，即将刷新');
          userRelateActionRef.current?.reloadAndRest?.();
          return true;
        } catch (error) {
          hide();
          message.error('取消关联失败，请重试');
          return false;
        }
      },
    });
  };

  const columns: ProColumns<DepartmentVO>[] = [
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
    }
  ];


  return (
    <PageContainer>
      <ProCard gutter={24} split="vertical" ghost>
        <ProCard colSpan={{xl: 6, lg: 6, md: 24}} bordered style={{height: '100%'}}>
          <DepartmentTree setSelectDepartment={setSelectDepartment} userRelateActionRef={userRelateActionRef}/>
        </ProCard>
        <ProCard
          colSpan={{xl: 18, lg: 18, md: 24}}
          bordered
          layout={selectDepartment && Object.keys(selectDepartment).length > 0 ? 'default' : 'center'}
          style={{height: '100%'}}>
          { selectDepartment && Object.keys(selectDepartment).length > 0 ? (
            <Tabs defaultActiveKey="info" items={[
              {
                key: 'info',
                label: '部门信息',
                forceRender: true,
                children: <>
                  <ProDescriptions<DepartmentVO>
                    column={2}
                    title={selectDepartment?.name}
                    request={async () => ({
                      data: selectDepartment || {},
                    })}
                    params={{
                      id: selectDepartment?.id,
                    }}
                    columns={columns as ProDescriptionsItemProps<DepartmentVO>[]}
                  />
                </>
              },
              {
                key: 'user',
                label: '用户信息',
                forceRender: true,
                children: <>
                  <UserRelateList
                    userRelateActionRef={userRelateActionRef}
                    handleBatchAddUserRelate={handleBatchAddDepartmentUser}
                    handleDeleteUserRelate={handleDeleteDepartmentUser}
                    handleBatchDeleteUserRelate={handleBatchDeleteDepartmentUser}
                    queryList={async (params: PageParams, sorter: Record<string, SortOrder>) =>
                      await getUserPageByDepartmentId({
                        ...convertPageParams(params, sorter),
                        departmentId: selectDepartment.id
                      })
                    }
                    callBackFinish={async (userId: string) => {
                      await addDepartmentUsers({
                        departmentId: selectDepartment?.id,
                        userIds: [userId],
                      });
                    }}
                  />
                </>
              },
            ]}>
            </Tabs>
          ) : <Space>请选择部门</Space> }
        </ProCard>
      </ProCard>
    </PageContainer>
  );
};

export default DepartmentList;
