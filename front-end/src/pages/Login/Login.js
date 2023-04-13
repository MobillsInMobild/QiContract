import './Login.css';
import { Button, Radio } from 'antd';
import { Input, Space, Slider, Avatar } from 'antd';
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Card } from 'antd';
import React, { useState } from 'react';
import { Form, Checkbox } from 'antd';
import { message } from 'antd';
import ProForm, { ProFormText } from '@ant-design/pro-form';
import { UserOutlined, LockOutlined, BulbTwoTone } from '@ant-design/icons';
import { PageHeader } from 'antd';
import { Redirect, useModel } from 'umi';

function Login() {
  const { Meta } = Card;
  const [size, setSize] = useState(100);
  const [size2, setSize2] = useState(0);
  const [login, setLogin] = useState(false);
  const { user, setUserData } = useModel('appstore');
  if (login) {
    console.log('redirected');
    return <Redirect to="/welcome"></Redirect>;
  }
  const layout = {
    labelCol: {
      span: 8,
    },
    wrapperCol: {
      span: 16,
    },
  };
  const tailLayout = {
    wrapperCol: {
      offset: 8,
      span: 16,
    },
  };

  const onFinish = (values) => {
    console.log('Success:', values);
  };

  const onFinishFailed = (errorInfo) => {
    console.log('Failed:', errorInfo);
  };

  return (
    <div
      className="Login"
      style={{
        margin: 'auto',
      }}
    >
      <header className="Login-header">
        <ProForm
          onFinish={(values) => {
            fetch('/api/login', {
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
                if (data.login) {
                  setUserData(values.address);
                  setLogin(true);
                  message.success('登陆成功'); //然后加上页面跳转
                } else message.error('登陆失败');
              })
              .catch((err) => {
                setUserData(values.address);
                setLogin(true);
                console.log(err);
              });
          }}
          submitter={{
            searchConfig: {
              submitText: '登录',
            },
            render: (_, dom) => dom.pop(),
            submitButtonProps: {
              size: 'large',
              style: {
                width: '100%',
              },
            },
          }}
        >
          <h1
            style={{
              textAlign: 'center',
              color: 'white',
            }}
          >
            <img
              style={{
                height: '44px',
                marginRight: 16,
              }}
              alt="logo"
              src="/files?file=0.png"
            />
            登录入口
          </h1>
          <div
            style={{
              marginTop: 12,
              textAlign: 'center',
              marginBottom: 40,
            }}
          ></div>
          <ProFormText
            fieldProps={{
              size: 'large',
              prefix: <UserOutlined />,
            }}
            name="address"
            placeholder="请输入以太坊账户"
            rules={[
              {
                required: true,
                message: '请输入以太坊账户!',
              },
              {
                pattern: /^0x[0-9a-z]*$/,
                message: '不合法的格式!',
              },
            ]}
          />
          <br />
          <ProFormText.Password
            fieldProps={{
              size: 'large',
              prefix: <LockOutlined />,
            }}
            captchaProps={{
              size: 'large',
            }}
            name="privateKey"
            rules={[
              {
                required: true,
                message: '请输入密码',
              },
            ]}
            placeholder="请输入密码"
          />
          <br />
        </ProForm>
        <br />
        <br />
      </header>
    </div>
  );
}

export default Login;
