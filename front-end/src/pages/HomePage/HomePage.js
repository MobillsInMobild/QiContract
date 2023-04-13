import './HomePage.css';
import { Button, Radio } from 'antd';
import { Input, Space , Slider,Avatar} from 'antd';
import { EyeInvisibleOutlined, EyeTwoTone } from '@ant-design/icons';
import { Card } from 'antd';
import React, { useState } from 'react';
import { Form, Checkbox } from 'antd';
import { message } from 'antd';
import ProForm, { ProFormText} from '@ant-design/pro-form';
import { UserOutlined,LockOutlined,BulbTwoTone} from '@ant-design/icons';
import { PageHeader } from 'antd';
import {Redirect} from 'umi'

function Homepage(){
    const { Meta } = Card;
    const [size, setSize] = useState(100);
    const [size2, setSize2] = useState(0);
    const [login,setLogin]=useState(false);
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
    if (login)
    {
        return <Redirect to='/Login'></Redirect>
    }
    const onFinish = (values) => {
        console.log('Success:', values);
    };

    const onFinishFailed = (errorInfo) => {
        console.log('Failed:', errorInfo);
    };

    return (

    <div className="Homepage" style={{
        margin: 'auto',
    }}>

        <PageHeader
            className="site-page-header-ghost-wrapper"
            title="基于区块链的公平高效多方合同签署平台"
            extra={[
                <Button key="1" type="primary" size ='large' className="HomePage-btn"  onClick={()=>{setLogin(true)}}>
                    登录
                </Button>,
            ]}
        />

      <header className="Homepage-header">

          <br />

          <Space >
              <img
                  style={{
                      height: '400px',
                  }}
                  alt="logo"
                  src="p1.jfif"
              />




              <Space direction="vertical" size={size2}>
                  <p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;基于区块链的公平高效</p>
                  <p>&nbsp;&nbsp;&nbsp;多方合同签署平台</p>
              </Space>
          </Space>



          <br />
          <br /><br />
          <br />
          <Space size={size}>
            <Card  bordered={false} cover={
                <Space direction="vertical">
                <br />
                <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#eb2f96"/>
            </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(12,15,153)',fontSize: '25px',color: 'white'}}>
                <p>公平签署</p>

                <Meta
                    description="公平性意味着各方拥有相同的权利，任何人无法中途毁约，否则将会被罚金"
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
                      description="在此平台进行合同签署，无需繁杂的步骤，只要1分钟即可完成签署"
                  />
              </Card>

              <Card bordered={false} cover={<Space direction="vertical">
                  <br />
                  <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#fa8c16" />
              </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(253,165,14)',fontSize: '25px',color: 'white'}}>
                  <p>安全签署</p>
                  <Meta
                      description="利用密码学技术，为客户生成专有数字签名，以此保证合同签署的安全性"
                  />
              </Card>

              <Card bordered={false} cover={<Space direction="vertical">
                  <br />
                  <BulbTwoTone style={{ fontSize: '35px'}} twoToneColor="#10239e"/>
              </Space>}  style={{ width: 300 ,backgroundColor: 'rgb(224,27,66)' ,fontSize: '25px',color: 'white'}}>
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
               <Space size={size2}>
                   <img
                       style={{
                           height: '500px',
                           width:'900px',
                           marginRight: 240,
                       }}
                       alt="logo"
                       src="p2.jpg"
                   />
                   <Space direction="vertical" size={size2}>
                        <Card bordered={false} style={{ width: 400,height:120,marginLeft: 70, backgroundColor: 'rgb(66,64,64)' ,fontSize: '40px',color: 'white'}}>
                            <p>区块链+密码学</p>
                        </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 10, backgroundColor: 'rgb(66,64,64)' ,fontSize: '23px',color: 'white'}}>
                           <p>使用智能合约</p>
                       </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 5, backgroundColor: 'rgb(66,64,64)' ,fontSize: '23px',color: 'white'}}>
                           <p>CLAVES方案</p>
                       </Card>
                       <Card bordered={false} style={{ width: 400,height:80,marginLeft: 70, backgroundColor: 'rgb(66,64,64)' ,fontSize: '23px',color: 'white'}}>
                           <p>基于罚金的公平交换协议</p>
                       </Card>
                   </Space >
               </Space>}  style={{ width: 1800 ,height:500,backgroundColor: 'rgb(66,64,64)' ,fontSize: '25px',color: 'white'}}>
          </Card>

          <br /><br />
          <br /><br />
          <br />


          <Button type="primary" className="HomePage-btn" onClick={()=>{setLogin(true)}}>
              开始使用
          </Button>

          <br />
          <br /><br />
          <br />

      </header>

        © Copyright 基于区块链的高效公平多方合同签署平台 2021
    </div>
  );
}

export default Homepage;
