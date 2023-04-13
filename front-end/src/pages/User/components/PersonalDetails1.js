import React, { version } from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { Avatar, Descriptions, Row, Space } from 'antd';
import { UserOutlined } from '@ant-design/icons';
import { Typography } from 'antd';

const { Title } = Typography;
const PersonalDetails = () => {
  return (
    <>
      <Space size={100}>
        <Avatar size={192} icon={<UserOutlined />} />
        <Title>欢迎使用</Title>
      </Space>
      <br />
      <br />
      <Descriptions title="" bordered column={2}>
        <Descriptions.Item label="姓名">Cloud Database</Descriptions.Item>
        <Descriptions.Item label="以太坊账户">0x123123123</Descriptions.Item>
        <Descriptions.Item label="手机号">1145345</Descriptions.Item>
        <Descriptions.Item label="邮箱">12345667@163.com</Descriptions.Item>
        <Descriptions.Item label="备注" span={2}>
          这里是备注
        </Descriptions.Item>
      </Descriptions>
    </>
  );
};
export default PersonalDetails;
