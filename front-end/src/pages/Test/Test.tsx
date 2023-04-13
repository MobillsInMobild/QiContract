import { Table } from 'antd';
import { useState } from 'react';
const columns = [
  {
    title: '合同ID',
    dataIndex: 'contractID',
  },
  {
    title: '合同部署地址',
    dataIndex: 'contractAddress',
  },
  {
    title: '合同名称',
    dataIndex: 'title',
  },
  {
    title: '合同签署状态',
    dataIndex: 'status',
  },
  {
    title: '合同下载',
    dataIndex: 'target',
    render: (text) => (
      <>
        {text && <a href={text}>点击预览</a>}
        {!text && <div>未签署</div>}
      </>
    ),
  },
];

const Test = () => {
  const [loading, setLoading] = useState(true);
  const [data, setData] = useState([]);
  if (loading) {
    fetch('/api/user/contractInfo', {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
      }),
      body: JSON.stringify({
        address: { account: '0xd6f3b4e66f31ad2783e5db9867fc50353cf97d00' },
      }),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        for (var k of data) {
          if (k.status) k.status = '已完成';
          else k.status = '未完成';
        }
        setData(data);
        setLoading(false);
      });
  }
  return <Table dataSource={data} columns={columns} loading={loading} />;
};
export default Test;
