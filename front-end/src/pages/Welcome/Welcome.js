import React, { useState, Component, UseEffect, useEffect } from 'react';
import moment from 'moment';
import {
  Button,
  Descriptions,
  Avatar,
  Card,
  Alert,
  Space,
  Row,
  message,
} from 'antd';
import { PageContainer } from '@ant-design/pro-layout';
import { UserOutlined } from '@ant-design/icons';
import 'echarts/lib/chart/pie';
import 'echarts/lib/component/tooltip';
import 'echarts/lib/component/title';
import 'echarts/lib/component/legend';
import 'echarts/lib/component/markPoint';
import ReactEcharts from 'echarts-for-react';
import { useModel, Redirect } from 'umi';
import { Typography } from 'antd';
import ContractList from './components/ContractList';

const { Title } = Typography;
const Pie = (item) => {
  // componentWillMount(){
  //     echarts.registerTheme("Imooc", echartTheme) //注入主题
  // }
  const getOption = () => {
    let option = {
      title: {
        text: '合同概况',
        x: 'center',
      },
      tooltip: {
        trigger: 'item',
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
      legend: {
        orient: 'vertical',
        top: 20,
        right: 5,
        data: ['签署中的合同', '已完成的合同'],
      },
      series: [
        {
          name: '订单量',
          type: 'pie',
          radius: ['30%', '80%'],
          data: [
            { value: item.unfinished, name: '签署中的合同' },
            { value: item.finished, name: '已完成的合同' },
          ],
        },
      ],
    };
    return option;
  };
  return <ReactEcharts option={getOption()} />;
};

const NewContract = (item) => {
  const { user, setUserData } = useModel('appstore');
  const [loading, setLoading] = useState(true);
  const [respData, setdata] = useState({});
  const [finished, setFinished] = useState(0);
  const [unfinished, setUnfinished] = useState(0);
  const [locked, setLocked] = useState(false);
  const [navigate, setNavigate] = useState(false);
  const date = new Date();
  if (navigate) return <Redirect to="/contract/sign"></Redirect>;
  if (loading && !locked) {
    setLocked(true);
    const values = { address: user };
    fetch('/api/user/contractInfo', {
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
        setdata(data);
        var i = 0;
        var j = 0;
        data.forEach((element) => {
          console.log(element.status);

          if (element.status) i += 1;
          else j += 1;
        });
        setFinished(i);
        setUnfinished(j);
        setLoading(false);
      })
      .catch((err) => {
        setdata([{ title: '合同', contractID: '456', status: false }]); //测试用
        console.log(err);
        setLoading(false);
        message.error('网络错误');
      });
  }
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
    >
      <Card loading={loading}>
        <Title>欢迎使用</Title>
        <Pie finished={finished} unfinished={unfinished}></Pie>
      </Card>
      <Card loading={loading}>
        <Title level={2}>正在签署的合同</Title>
        <ContractList
          data={respData}
          check={true}
          setNavigate={setNavigate}
        ></ContractList>
      </Card>
      <Card loading={loading}>
        <Title level={2}>已完成的合同</Title>
        <ContractList
          data={respData}
          check={false}
          setNavigate={setNavigate}
        ></ContractList>
      </Card>
    </PageContainer>
  );
};
const New = () => {
  return <NewContract address="0x1232342"></NewContract>;
};
export default New;
