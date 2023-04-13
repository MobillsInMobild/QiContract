import React from 'react';
import { Card, message, Typography, Spin, Button } from 'antd';
import ProLayout, { FooterToolbar } from '@ant-design/pro-layout';
import ProForm, { ProFormText } from '@ant-design/pro-form';
const { Title } = Typography;
const StepTwo = (item) => {
  return (
    <Card>
      {!item.contract.methods && <Spin></Spin>}
      {item.contract.methods && (
        <Button
          onClick={() => {
            const values = {
              contractID: item.contractID,
              currentStatus: 0,
              privateKey: 'hide',
              address: item.address,
            };
            console.log(values);
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
                console.log('fuck');
                item.setCurrent(2);
                //提交成功
              })
              .catch((err) => {
                item.setCurrent(2); //测试用
                console.log(values.contractID);

                message.error('网络错误');
              });
          }}
        >
          下一步
        </Button>
      )}
    </Card>
  );
};
export default StepTwo;
