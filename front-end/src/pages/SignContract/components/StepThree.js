import React from 'react';
import { Card, Button, message, Typography } from 'antd';
const { Title, Paragraph } = Typography;

const StepThree = (item) => {
  const buttonDisplayText = ['获取合同内容', '获取中...'];
  const [status, setStatus] = React.useState(0);
  const [displayContract, setDisplayContract] = React.useState(false);
  const [title, setTitle] = React.useState('');
  const [content, setContent] = React.useState('');
  const [tip, setTip] = React.useState('');
  const [num, setNum] = React.useState(0);
  const getContract = () => {
    setStatus(1);
    const values = {
      address: item.address,
      contractID: item.contractID,
      currentStatus: 1,
    };
    fetch('/api/contract/perform', {
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
        //提交成功
        setTitle(data.title);
        setContent(data.content);
        setTip(data.tip);
        setNum(data.num);
        setDisplayContract(true);
      })
      .catch((err) => {
        const data = {
          title: '标题',
          content: '合同内容',
          tip: '备注',
          num: 3,
        };
        setTitle(data.title);
        setContent(data.content);
        setTip(data.tip);
        setNum(data.num);
        setDisplayContract(true);

        setStatus(0);
        message.error('网络错误');
      });
  };
  return (
    <Card>
      {!displayContract && (
        <Button
          onClick={() => {
            getContract();
          }}
        >
          {buttonDisplayText[status]}
        </Button>
      )}
      {displayContract && (
        <Typography>
          <Title>{title}</Title>
          <Title level={2}>合同ID: {item.contractID}</Title>
          <Title level={2}>签署人数： {num}</Title>
          <Title level={2}>合同内容：</Title>
          <Paragraph>{content}</Paragraph>
          <Title level={2}>备注：</Title>
          <Paragraph>{tip}</Paragraph>
        </Typography>
      )}
      {displayContract && (
        <Button
          onClick={() => {
            item.setCurrent(3);
          }}
        >
          确认合同
        </Button>
      )}
    </Card>
  );
};
export default StepThree;
