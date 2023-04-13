import React from 'react';
import { Space, Input, Steps, Button } from 'antd';
import StepOne from './StepOne';
import StepTwo from './StepTwo';
import StepThree from './StepThree';
import StepFour from './StepFour';
import StepFive from './StepFive';
import StepSix from './StepSix';
const Login = (param) => {
  return (
    <Space direction="vertical">
      <Input
        placeholder="以太坊账号"
        onChange={(e) => param.setUser(e.target.value)}
      />
      <Input.Password
        placeholder="私钥"
        onChange={(e) => param.setPass(e.target.value)}
      />
    </Space>
  );
};

const SignContract = (item) => {
  const [current, setCurrent] = React.useState(0);
  const [contract, setContract] = React.useState({});
  const [contractID, setContractID] = React.useState(0);
  const { Step } = Steps;
  const steps = [
    {
      title: '查找合同',
      content: (
        <StepOne
          address={item.address}
          setCurrent={setCurrent}
          setContractID={setContractID}
          setContract={setContract}
        />
      ),
    },
    {
      title: '连接到以太坊',
      content: (
        <StepTwo
          address={item.address}
          setCurrent={setCurrent}
          contractID={contractID}
          contract={contract}
        />
      ),
    },
    {
      title: '确认合同内容',
      content: (
        <StepThree
          address={item.address}
          setCurrent={setCurrent}
          contractID={contractID}
          contract={contract}
        />
      ),
    },
    {
      title: '签署合同',
      content: (
        <StepFour
          address={item.address}
          setCurrent={setCurrent}
          contractID={contractID}
          contract={contract}
        />
      ),
    },
    {
      title: '验证合同',
      content: (
        <StepFive
          address={item.address}
          setCurrent={setCurrent}
          contractID={contractID}
          contract={contract}
        />
      ),
    },
    {
      title: '完成签署',
      content: <StepSix contractID={contractID} />,
    },
  ];

  return (
    <>
      <Steps current={current}>
        {steps.map((item) => (
          <Step key={item.title} title={item.title} />
        ))}
      </Steps>
      <div className="steps-content">{steps[current].content}</div>
      <div className="steps-action"></div>
    </>
  );
};
export default SignContract;
