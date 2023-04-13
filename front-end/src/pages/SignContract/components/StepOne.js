import ShareSecret from '../../../contract/ShareSecret.json';
import Web3 from 'web3';
import React from 'react';
import { Card, message, Modal } from 'antd';
import { FooterToolbar } from '@ant-design/pro-layout';
import ProForm, { ProFormText } from '@ant-design/pro-form';

const StepOne = (item) => {
  return (
    <Card>
      <ProForm
        submitter={{
          render: (_, dom) => <FooterToolbar>{dom}</FooterToolbar>,
        }}
        onFinish={async (values) => {
          console.log(values);
          fetch('/api/contract/query', {
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
              console.log(data);
              if (data.currentStatus == -1) {
                Modal.error({
                  title: '合同不存在或非法用户！',
                  onOk: () => {},
                });
                return;
              }
              values.address = data.contractAddress;
              item.setCurrent(data.currentStatus + 1);
              item.setContractID(values.contractID);
              ethereum
                .enable()
                .catch(function (reason) {
                  console.log(reason);
                })
                .then(function (accounts) {
                  const web3 = new Web3(window.web3.currentProvider);
                  web3.eth.defaultAccount = accounts[0];
                  const contract = new web3.eth.Contract(
                    ShareSecret.abi,
                    values.address,
                  );
                  item.setContract(contract);
                  console.log(contract);
                });
              //提交成功
            })
            .catch((err) => {
              //提交失败
              message.error('网络错误');
            });
        }}
      >
        <ProForm.Group>
          <ProFormText
            name={['address']}
            disabled
            label="以太坊账户"
            rules={[
              {
                required: true,
                message: '请输入以太坊账户',
              },
            ]}
            initialValue={item.address}
          ></ProFormText>
        </ProForm.Group>
        <ProForm.Group>
          <ProFormText
            name={['contractID']}
            label="合同ID"
            rules={[
              {
                required: true,
                message: '请输入合同ID',
              },
            ]}
          ></ProFormText>
        </ProForm.Group>
      </ProForm>
    </Card>
  );
};
export default StepOne;
