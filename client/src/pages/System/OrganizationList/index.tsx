import {Divider, Space, Tabs, Tree} from 'antd';
import React, {useEffect, useState} from 'react';
import { PageContainer } from '@ant-design/pro-layout';
import ProCard from "@ant-design/pro-card";
import type { DataNode } from 'antd/lib/tree';
import {getDepartmentByPid, getDepartmentList} from "@/services/uaa/department";
import Search from "antd/es/input/Search";
import type {DepartmentVO} from "@/services/uaa/type/department";
import type {ProDescriptionsItemProps} from "@ant-design/pro-descriptions";
import ProDescriptions from "@ant-design/pro-descriptions";
import type {ProColumns} from "@ant-design/pro-table";
import UserRelateList from "@/pages/System/UserRelateList";


const DepartmentList: React.FC = () => {

  const [departmentTreeData, setDepartmentTreeData] = useState<DataNode[]>([]);
  const [selectDepartment, setSelectDepartment] = useState<DepartmentVO|undefined>(undefined);


  const updateTreeData = (list: DataNode[], key: number, children: DataNode[] | null): DataNode[] => (
    list.map((node) => {
      if (node.key === key) {
        return {
          ...node,
          children: children && children.length > 0 ? children : undefined,
        };
      }
      if (node.children && node.children.length > 0) {
        return {
          ...node,
          children: updateTreeData(node.children, key, children),
        };
      }
      return { ...node };
    })
  );

  const onLoadDepartmentData = async ({ key }: any) => {
    const { data } = await getDepartmentByPid(key);

    if (data && data.length > 0) {
      const list: DataNode[] = data.map(item => ({
        title: item.name,
        key: item.id || 0,
        dataRef: { ...item }
      }))

      if (key) {
        setDepartmentTreeData((department) =>
          updateTreeData(department, key, list)
        );
      } else {
        setDepartmentTreeData(list);
      }
    }
  }

  const refreshDepartmentData = () => {
    getDepartmentByPid().then(({ data }) => {
      setDepartmentTreeData(data && data.length > 0 ? data.map(item => ({
        key: item.id || 0,
        title: item.name,
        dataRef: { ...item }
      })) : [])
    }).catch(() => {
      setDepartmentTreeData([])
    })
  }

  const onSearchDepartment = async (keyword?: string) => {
    setSelectDepartment(undefined);
    if (keyword) {
      const { data } = await getDepartmentList(keyword);
      setDepartmentTreeData(data && data.length > 0 ? data.map(item => ({
        key: item.id || 0,
        title: item.name,
        dataRef: { ...item }
      })) : [])
    } else {
      refreshDepartmentData();
    }
  }

  const onSelectDepartment = (key: any, info: any) => {
    console.log(key)
    if (key && key.length > 0) {
      setSelectDepartment(info.selectedNodes[0].dataRef)
    } else {
      setSelectDepartment(undefined)
    }
  }

  useEffect(() => {
    refreshDepartmentData();
  }, []);

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
        <ProCard colSpan={{xl: 8, lg: 8, md: 24}} bordered style={{height: '100%'}}>
          <Search size="small" placeholder="请输入部门名称" onSearch={onSearchDepartment}/>
          <Divider style={{ margin: '12px 0' }}/>
          <Tree
            showLine
            loadData={onLoadDepartmentData}
            treeData={departmentTreeData}
            onSelect={onSelectDepartment}
          />
        </ProCard>
        <ProCard
          colSpan={{xl: 16, lg: 16, md: 24}}
          bordered
          layout={selectDepartment && Object.keys(selectDepartment).length > 0 ? 'default' : 'center'}
          style={{height: '100%'}}>
          { selectDepartment && Object.keys(selectDepartment).length > 0 ? (
            <Tabs defaultActiveKey="info">
              <Tabs.TabPane tab="部门信息" key="info" forceRender>
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
              </Tabs.TabPane>
              <Tabs.TabPane tab="用户信息" key="user" forceRender>
              </Tabs.TabPane>
            </Tabs>
          ) : <Space>请选择部门</Space> }
        </ProCard>
      </ProCard>
    </PageContainer>
  );
};

export default DepartmentList;
