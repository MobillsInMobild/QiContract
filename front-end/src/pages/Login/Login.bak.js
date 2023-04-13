import { Button, Radio } from 'antd';
import "./Login.css"
import { Input, Space , Slider,Avatar} from 'antd';
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Card } from 'antd';
import React, { useState } from 'react';
import { Form, Checkbox } from 'antd';
import { message } from 'antd';
import ProForm, { ProFormText} from '@ant-design/pro-form';
import { UserOutlined,LockOutlined,BulbTwoTone} from '@ant-design/icons';
import {Redirect,useModel} from 'umi'
function Login(){
    const { Meta } = Card;
    const [size, setSize] = useState(100);
    const [login,setLogin]=useState(false);
    const { user, setUserData } = useModel('appstore');
    const [size2, setSize2] = useState(0);

    if (login)
    {
        console.log("redirected");
        return <Redirect to='/welcome'></Redirect>
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

    <div className="App"style={{
        margin: 'auto',
    }}>

      <header className="App-header">

          <br />

          <Space >
              <img
                  style={{
                      height: '400px',
                  }}
                  alt="logo"
                  src="images/p1.jfif"
              />
              <Space direction="vertical" size={size2}>
              <Card bordered={false} style={{ width: 600,height:115,marginLeft: 140, backgroundColor: 'rgb(3,8,82)' ,fontSize: '60px',color: 'white'}}>
                  <p>基于区块链的</p>
              </Card><Card bordered={false} style={{ width: 600,height:110,marginLeft: 195, backgroundColor: 'rgb(3,8,82)' ,fontSize: '60px',color: 'white'}}>
                  <p>多方合同签署平台</p>
              </Card>
              </Space>
          </Space>



          <br /><br />
          <br /><br />
          <br />
          <Space size={size}>
            <Card  bordered={false} cover={
                <Space direction="vertical">
                <br />
                <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#eb2f96"/>
            </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(234,192,11)',fontSize: '25px',color: 'white'}}>
                <p>公平签署</p>

                <Meta
                    description="This is the description"
                />
            </Card>


              <Card bordered={false} cover={
                  <Space direction="vertical">
                      <br />
                      <BulbTwoTone style={{ fontSize: '35px' }} twoToneColor="#10239e"/>
                  </Space>}
                    style={{backgroundColor: 'rgb(55,206,1)',width: 300 ,fontSize: '25px',color: 'white'}}>
                  <p>高效签署</p>
                  <Meta
                      description="This is the description"
                  />
              </Card>

              <Card bordered={false} cover={<Space direction="vertical">
                  <br />
                  <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#fa8c16" />
              </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(0,250,192)',fontSize: '25px',color: 'white'}}>
                  <p>多方签署</p>
                  <Meta
                      description="This is the description"
                  />
              </Card>

              <Card bordered={false} cover={<Space direction="vertical">
                  <br />
                  <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#10239e"/>
              </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(234,51,88)' ,fontSize: '25px',color: 'white'}}>
                  <p>在线签署</p>
                  <Meta
                      description="所有步骤均将在线完成，免除地域为线下签署带来的困难，足不出户，完成签署"
                  />
              </Card>

          </Space>

          <br /><br />
          <br /><br />
          <br />

          <Card  bordered={false}
                cover={
               <Space >
                   <img
                       style={{
                           height: '400px',
                           marginRight: 240,
                       }}
                       alt="logo"
                       src="p1.jfif"
                   />
                   <Space direction="vertical" size={size2}>
                        <Card bordered={false} style={{ width: 400,height:120,marginLeft: 70, backgroundColor: 'rgb(0,0,0)' ,fontSize: '40px',color: 'white'}}>
                            <p>区块链+密码学</p>
                        </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 10, backgroundColor: 'rgb(0,0,0)' ,fontSize: '23px',color: 'white'}}>
                           <p>使用智能合约</p>
                       </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 5, backgroundColor: 'rgb(0,0,0)' ,fontSize: '23px',color: 'white'}}>
                           <p>CLAVES方案</p>
                       </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 70, backgroundColor: 'rgb(0,0,0)' ,fontSize: '23px',color: 'white'}}>
                           <p>基于罚金的公平交换协议</p>
                       </Card>
                   </Space >
               </Space>}  style={{ width: 1800 ,height:400,backgroundColor: 'rgb(0,0,0)' ,fontSize: '25px',color: 'white'}}>
          </Card>

          <br /><br />
          <br /><br />
          <br />


          签署过程高效便捷








          <br />
          <br /><br />
          <br />
          <ProForm
              onFinish={(values) => {
                  fetch('/api/login',{
                      method:"POST",
                      body:JSON.stringify(values)
                  }).then((response) => {
                      return response.json();
                  }).then((data)=>{
                      if (data.login)
                      {
                        setUserData(values.address);
                          setLogin(true);
                          message.success('登陆成功');//然后加上页面跳转
                      }
                      else
                          message.error("登陆失败");

                  }).catch((err)=>{setUserData(values.address); setLogin(true); console.log(err)})
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
                      src="https://gw.alipayobjects.com/zos/rmsportal/KDpgvguMpGfqaHPjicRK.svg"
                  />
                  登录入口
              </h1>
              <div
                  style={{
                      marginTop: 12,
                      textAlign: 'center',
                      marginBottom: 40,
                  }}
              >
              </div>
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
                          message: '请输入以太坊私钥',
                      },
                  ]}
                  placeholder="请输入以太坊私钥"
              />
          </ProForm>
          <br />
          <br />
          <br />
          <br />


      </header>
    </div>
  );
}

export default Login;
