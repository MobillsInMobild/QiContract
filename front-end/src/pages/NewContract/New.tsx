import Web3 from 'web3';
import ShareSecret from '../../contract/ShareSecret.json';
import React, { useState, useRef } from 'react';
import {
  Button,
  Descriptions,
  Avatar,
  Card,
  Alert,
  Modal,
  Table,
  Input,
} from 'antd';
import type { FormInstance } from 'antd';
const { Search } = Input;
import {
  DesktopOutlined,
  GlobalOutlined,
  DeploymentUnitOutlined,
  ExclamationCircleOutlined,
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
} from '@ant-design/pro-form';
import { useModel, Redirect } from 'umi';

const toJsonObject = (json) => {
  var j = [];
  var k = 0;
  for (var i of json) {
    k = k + 1;
    j.push({ addressGroupItem: i, key: Date.now() + k });
  }
  return j;
};
const deleteJsonItem = (json, str) => {
  var j = [];
  for (var i of json) if (i != str) j.push(i);
  return j;
};
const NewContract = (item) => {
  const [pathname, setPathname] = useState('/welcome');
  const { user, setUserData } = useModel('appstore');
  const formRef = useRef<FormInstance>();
  const [back, setBack] = useState(false);
  const [address, setAddress] = useState(user.account);
  const [dataSource, setDataSource] = useState(toJsonObject([user.account]));
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
          tab: '创建合同',
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
          formRef={formRef}
          submitter={{
            render: (_, dom) => <FooterToolbar>{dom}</FooterToolbar>,
          }}
          onFinish={async (values) => {
            ethereum
              .enable()
              .catch(function (reason) {
                console.log(reason);
              })
              .then(function (accounts) {
                console.log(accounts);
                const web3 = new Web3(window.web3.currentProvider);
                web3.eth.defaultAccount = accounts[0];
                console.log(ShareSecret.abi);
                var tokenContract = new web3.eth.Contract(
                  ShareSecret.abi,
                  null,
                );
                console.log(values.num);
                tokenContract
                  .deploy({
                    data: ShareSecret.bytecode,
                    arguments: [values.num],
                  })
                  .send(
                    {
                      from: web3.eth.defaultAccount,
                    },
                    function (error, transactionHash) {
                      if (error != null) {
                        Modal.error({
                          title: '创建失败',
                          onOk: () => {
                            setBack(true);
                          },
                        });
                      }
                      values.hash = transactionHash;
                    },
                  )
                  .then(function (newContractInstance) {
                    values.contractAddress =
                      newContractInstance.options.address;
                    fetch('/api/contract/new', {
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
                        Modal.success({
                          title: '创建成功',
                          content: (
                            <>
                              <p>
                                &nbsp;&nbsp;&nbsp;&nbsp;合同已创建成功，信息存储至链上
                              </p>
                              <DesktopOutlined />
                              <p>交易哈希: {values.hash}</p>
                              <GlobalOutlined />
                              <p>合同地址: {values.contractAddress}</p>
                              <DeploymentUnitOutlined />
                              <p>合同ID: {data.contractID}</p>
                            </>
                          ),
                          onOk: () => {
                            setBack(true);
                          },
                        });
                      })
                      .catch((err) => {
                        Modal.error({
                          title: '创建失败',
                          onOk: () => {
                            setBack(true);
                          },
                        });
                        console.log(err);
                        //提交失败
                      });
                    console.log(values);
                  });
              });
          }}
        >
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
            initialValue={address}
          ></ProFormText>
          <ProFormDigit
            width="xs"
            name="num"
            label="签署人数"
            rules={[
              {
                required: true,
                message: '请输入签署人数',
              },
            ]}
            initialValue={3}
          />
          <ProForm.Group>
            <ProFormText
              name={['userGroup']}
              label="用户ID"
              rules={[
                {
                  required: true,
                  message: '请设置用户ID策略',
                },
              ]}
              initialValue={JSON.stringify([user.account])}
            ></ProFormText>
            <Button
              onClick={() => {
                const modal = Modal.success({
                  title: '用户ID管理',
                  width: 1000,
                  content: (
                    <>
                      <Search
                        placeholder="请输入用户以太坊地址"
                        allowClear
                        enterButton="添加"
                        size="large"
                        onSearch={(text) => {
                          var array = JSON.parse(
                            formRef?.current?.getFieldValue('userGroup'),
                          );
                          array.push(text);
                          var update = [...array];
                          formRef?.current?.setFieldsValue({
                            userGroup: JSON.stringify(update),
                          });
                          setDataSource(toJsonObject(update));
                          console.log(toJsonObject(update));
                          modal.destroy();
                        }}
                      />
                      <Table
                        dataSource={dataSource}
                        rowKey={(record) => {
                          return record.id + Date.now();
                        }}
                        columns={[
                          {
                            title: '用户ID',
                            dataIndex: 'addressGroupItem',
                          },
                          {
                            title: '操作',
                            dataIndex: 'addressGroupItem',
                            render: (text) => (
                              <Button
                                type="primary"
                                danger
                                onClick={() => {
                                  var array = JSON.parse(
                                    formRef?.current?.getFieldValue(
                                      'userGroup',
                                    ),
                                  );
                                  var update = deleteJsonItem(array, text);
                                  formRef?.current?.setFieldsValue({
                                    userGroup: JSON.stringify(update),
                                  });
                                  setDataSource(toJsonObject(update));
                                  var collection =
                                    document.getElementsByClassName(
                                      'ant-table-row ant-table-row-level-0',
                                    );
                                  for (var node of collection) {
                                    if (node.children[0].innerHTML == text)
                                      node.remove();
                                  }
                                }}
                              >
                                删除
                              </Button>
                            ),
                          },
                        ]}
                      ></Table>
                    </>
                  ),
                  icon: <ExclamationCircleOutlined />,
                });
              }}
            >
              查看/修改
            </Button>
          </ProForm.Group>
          <ProForm.Group>
            <ProFormText
              name={['title']}
              label="合同名称"
              placeholder="请输入名称"
              rules={[
                {
                  required: true,
                  message: '请输入合同名称',
                },
              ]}
            />
            <ProFormDateRangePicker
              name={['contract', 'createTime']}
              label="合同生效时间"
            />
          </ProForm.Group>
          <ProFormTextArea
            width="xl"
            name={['content']}
            label="合同内容"
            rules={[
              {
                required: true,
                message: '请输入合同内容',
              },
            ]}
          />
          <ProFormTextArea
            width="xl"
            name={['tip']}
            label="合同备注说明"
            rules={[
              {
                required: true,
                message: '请输入合同备注',
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
