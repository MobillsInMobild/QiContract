import React, { useState } from 'react';
import { Avatar, Row, Space, Button, message } from 'antd';
import ProDescriptions from '@ant-design/pro-descriptions';
import { UserOutlined } from '@ant-design/icons';
import { Typography } from 'antd';
import { useModel } from 'umi';

const { Title } = Typography;
export default () => {
  const [data, setData] = useState({});
  const { user, setUserData } = useModel('appstore');
  return (
    <>
      <Space size={100}>
        <Avatar size={192} icon={<UserOutlined />} />
        <Title>欢迎使用</Title>
      </Space>
      <br />
      <br />
      <ProDescriptions
        title=""
        request={async () => {
          console.log('hello');
          const values = { address: user.account };
          fetch('/api/user/details', {
            method: 'POST',
            headers: new Headers({
              'Content-Type': 'application/json;charset=UTF-8',
            }),
            body: JSON.stringify(values),
          })
            .then((response) => {
              return response.json();
            })
            .then((data) => {
              setData(data);
            })
            .catch((err) => {
              message.error('网络错误');
            });
        }}
        bordered
        column={2}
      >
        <ProDescriptions.Item label="姓名">{data.name}</ProDescriptions.Item>
        <ProDescriptions.Item label="以太坊账户">
          {data.address}
        </ProDescriptions.Item>
        <ProDescriptions.Item label="手机号">{data.phone}</ProDescriptions.Item>
        <ProDescriptions.Item label="邮箱">{data.email}</ProDescriptions.Item>
        <ProDescriptions.Item label="备注" span={2}>
          {data.tips}
        </ProDescriptions.Item>
      </ProDescriptions>
    </>
  );
};
