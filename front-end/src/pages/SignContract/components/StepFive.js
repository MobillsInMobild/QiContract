import React from 'react';
import { Card, Button, message, Spin, Typography, Result, Modal } from 'antd';
import {
  DesktopOutlined,
  GlobalOutlined,
  DeploymentUnitOutlined,
  BranchesOutlined,
} from '@ant-design/icons';
const { Title } = Typography;
const StepFour = (item) => {
  const [loading, setLoading] = React.useState(false);
  const [finished, setFinished] = React.useState(false);

  const [showModal, setShowModal] = React.useState(false);

  const [sign, setSign] = React.useState(true);
  const [claim, setClaim] = React.useState(true);
  const [refund, setRefund] = React.useState(true);
  const [deposit, setDesposit] = React.useState(true);

  const [data, setData] = React.useState({});
  const verify = () => {
    const values = {
      address: item.address,
      contractID: item.contractID,
      currentStatus: 3,
      step: 1,
    };
    console.log(item.contract);
    setLoading(true);
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
        /*console.log(data);
        setData(data.data);
        setShowModal(true);
        setTimeout(() => {
          setSign(false);
        }, 1000);
        setTimeout(() => setClaim(false), 3000);
        setTimeout(() => {
          setRefund(false);
        }, 4000);
        setTimeout(() => {
          setDesposit(false);
          setFinished(true);
        }, 2000);*/
        setShowModal(true);
        var _data = {};
        console.log(item.address);
        item.contract.methods
          .sign()
          .send({ from: item.address }, function (errorCode, hash) {
            _data.sign = hash;
            setData(_data);
            setSign(false);
            values.step = 2;
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
                item.contract.methods
                  .Deposit()
                  .send(
                    { from: item.address, value: 3000000000000000000 },
                    function (errorCode, hash) {
                      _data.deposit = hash;
                      setData(_data);
                      setDesposit(false);
                      values.step = 3;
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
                          item.contract.methods
                            .Claim(true)
                            .send(
                              { from: item.address },
                              function (errorCode, hash) {
                                _data.claim = hash;
                                setData(_data);
                                setClaim(false);
                                values.step = 4;
                                fetch('/api/contract/perform', {
                                  method: 'POST',
                                  headers: new Headers({
                                    'Content-Type':
                                      'application/json;charset=UTF-8',
                                  }),
                                  body: JSON.stringify(values),
                                })
                                  .then((response) => {
                                    return response.json();
                                  })
                                  .then((data) => {
                                    item.contract.methods
                                      .refund()
                                      .send(
                                        { from: item.address },
                                        function (errorCode, hash) {
                                          _data.refund = hash;
                                          setData(_data);
                                          setRefund(false);
                                          values.step = 5;
                                          fetch('/api/contract/perform', {
                                            method: 'POST',
                                            headers: new Headers({
                                              'Content-Type':
                                                'application/json;charset=UTF-8',
                                            }),
                                            body: JSON.stringify(values),
                                          }).then();
                                        },
                                      )
                                      .then();
                                  });
                              },
                            )
                            .then();
                        });
                    },
                  )
                  .then();
              });
          })
          .then();
      })
      .catch((err) => {
        message.error('网络错误');
      });
  };
  return (
    <Card>
      <Title level={2}>合同ID: {item.contractID}</Title>
      {!loading && (
        <Button
          onClick={() => {
            verify();
          }}
        >
          验证合同
        </Button>
      )}
      {loading && !showModal && !finished && <Spin />}
      <Modal
        title="验证中"
        centered
        visible={showModal}
        onOk={() => {
          if (!deposit) {
            setShowModal(false);
            setFinished(true);
          }
        }}
      >
        <div>
          <DesktopOutlined />
          <p>
            SignHash: {sign && <Spin></Spin>}
            {!sign && <div>{data.sign}</div>}
          </p>
          <BranchesOutlined />
          <p>
            DepositHash:{deposit && <Spin></Spin>}
            {!deposit && <div>{data.deposit}</div>}{' '}
          </p>
          <GlobalOutlined />
          <p>
            ClaimHash: {claim && <Spin></Spin>}
            {!claim && <div>{data.claim}</div>}
          </p>
          <DeploymentUnitOutlined />
          <p>
            RefundHash: {refund && <Spin></Spin>}
            {!refund && <div>{data.refund}</div>}
          </p>
        </div>
      </Modal>
      {finished && (
        <Result
          status="success"
          title="合同验证成功"
          extra={[
            <Button
              type="primary"
              onClick={() => {
                item.setCurrent(5);
              }}
            >
              确认
            </Button>,
          ]}
        />
      )}
    </Card>
  );
};
export default StepFour;
