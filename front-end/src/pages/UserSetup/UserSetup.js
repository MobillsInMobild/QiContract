import Web3 from 'web3';
import { message } from 'antd';
import ShareSecret from '../../contract/ShareSecret.json';
import React, { useState } from 'react';
import { Button, Descriptions, Avatar, Card, Alert, Modal } from 'antd';
import {
  DesktopOutlined,
  GlobalOutlined,
  DeploymentUnitOutlined,
} from '@ant-design/icons';
import ProLayout, {
  PageContainer,
  FooterToolbar,
} from '@ant-design/pro-layout';
import ProForm, {
  ProFormText,
  ProFormDigit,
  ProFormTextArea,
  ProFormDateRangePicker,
  ProFormUploadDragger,
} from '@ant-design/pro-form';
import { useModel, Redirect } from 'umi';

const content = (
  <Descriptions size="small" column={2}>
    <Descriptions.Item label="以太坊账户">0x12345</Descriptions.Item>
    <Descriptions.Item label="联系方式">
      <a>10086</a>
    </Descriptions.Item>
    <Descriptions.Item label="当前时间">2021-07-17</Descriptions.Item>
    <Descriptions.Item label="备注">北京航空航天大学</Descriptions.Item>
  </Descriptions>
);

const NewContract = (item) => {
  const [pathname, setPathname] = useState('/welcome');
  const { user, setUserData } = useModel('appstore');
  const [back, setBack] = useState(false);
  const date = new Date();
  if (back) return <Redirect to="/welcome"></Redirect>;
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
          tab: '个人信息修改',
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
        <ProForm
          submitter={{
            render: (_, dom) => <FooterToolbar>{dom}</FooterToolbar>,
          }}
          onFinish={async (values) => {
            values.address = user.account;
            console.log(values);
            fetch('/api/user/update', {
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
                message.success('修改成功');
              })
              .catch((err) => {
                message.error('网络错误');
              });
          }}
        >
          <ProFormText
            width="xl"
            label="姓名"
            name="name"
            placeholder="请输入名称"
            rules={[
              {
                required: true,
                message: '请输入姓名',
              },
            ]}
          ></ProFormText>

          <ProFormText.Password
            width="xl"
            label="修改密码"
            placeholder="请输入原密码"
          />
          <ProFormText.Password
            width="xl"
            placeholder="请输入新密码"
            name="password"
          />
          <ProForm.Group>
            <ProFormText
              label="联系方式"
              placeholder="请输入联系方式"
              rules={[
                {
                  required: true,
                  message: '请输入联系方式',
                },
              ]}
              name="phone"
            />
            <ProFormText
              label="邮箱"
              placeholder="请输入邮箱"
              rules={[
                {
                  required: true,
                  message: '请输入邮箱',
                },
              ]}
              name="email"
            />
          </ProForm.Group>
          <ProFormTextArea
            width="xl"
            label="备注"
            name="tips"
            rules={[
              {
                required: true,
                message: '请输入备注',
              },
            ]}
          />
        </ProForm>
      </Card>
    </PageContainer>
  );
};
const New = () => {
  return <NewContract address="0x1232342"></NewContract>;
};
export default New;
