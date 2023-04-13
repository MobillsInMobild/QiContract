import React, { useState } from 'react';
import { Button, Descriptions, Avatar, Card, Input } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { useModel } from 'umi';
import ProLayout, {
  PageContainer,
  FooterToolbar,
} from '@ant-design/pro-layout';
import PersonalDetails from './components/PersonalDetails';
const _PersonalDetails = (item) => {
  const { user, setUserData } = useModel('appstore');
  const [pathname, setPathname] = useState('/welcome');
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
          tab: '个人信息',
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
        <PersonalDetails />
      </Card>
    </PageContainer>
  );
};
const User = () => {
  return <_PersonalDetails />;
};
export default User;
