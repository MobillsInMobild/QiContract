import React, { useState } from 'react';
import { Button, Descriptions, Avatar, Card, Input, Table } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import ProLayout, {
  PageContainer,
  FooterToolbar,
} from '@ant-design/pro-layout';
import { useModel } from 'umi';

const columns = [
  {
    title: '合同ID',
    dataIndex: 'contractID',
  },
  {
    title: '合同部署地址',
    dataIndex: 'contractAddress',
  },
  {
    title: '合同名称',
    dataIndex: 'title',
  },
  {
    title: '合同签署状态',
    dataIndex: 'status',
  },
  {
    title: '合同下载',
    dataIndex: 'target',
    render: (text) => (
      <>
        {text && (
          <Button
            onClick={function () {
              window.open(text);
            }}
          >
            点击预览
          </Button>
        )}
        {!text && <div>未签署</div>}
      </>
    ),
  },
];

const ContractTb = () => {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState([]);
  const { user, setUserData } = useModel('appstore');
  if (loading) {
    fetch('/api/user/contractInfo', {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
      }),
      body: JSON.stringify({ address: user }),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        for (var k of data) {
          if (k.status) k.status = '已完成';
          else k.status = '未完成';
        }
        setData(data);
        setLoading(false);
      });
  }
  return <Table dataSource={data} columns={columns} loading={loading} />;
};
const ContractDetails = (item) => {
  const [pathname, setPathname] = useState('/welcome');
  const { user, setUserData } = useModel('appstore');
  const date = new Date();
  return (
    <PageContainer
      content={
        <Descriptions size="small" column={2}>
          <Descriptions.Item label="以太坊账户">
            {user.account}
          </Descriptions.Item>
          <Descriptions.Item label="联系方式">
            <a></a>
          </Descriptions.Item>
          <Descriptions.Item label="当前时间">
            {date.getFullYear() +
              '-' +
              (date.getMonth() + 1) +
              '-' +
              date.getDate()}
          </Descriptions.Item>
          <Descriptions.Item label="备注">北京航空航天大学</Descriptions.Item>
        </Descriptions>
      }
      tabList={[
        {
          tab: '我的合同',
          key: 'create',
        },
      ]}
      extra={[
        <Button key="1" type="primary">
          返回
        </Button>,
      ]}
    >
      <Card style={{ height: '80vh' }}>
        <ContractTb />
      </Card>
    </PageContainer>
  );
};
const DashBoard = () => {
  return <ContractDetails />;
};
export default DashBoard;
