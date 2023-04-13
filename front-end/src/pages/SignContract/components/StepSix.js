import React from 'react';
import { Card, Button, message, Spin } from 'antd';
import { PDFReader } from 'react-read-pdf';
const StepSix = (item) => {
  const [loading, setLoading] = React.useState(0);
  const [PDFUrl, setPDFUrl] = React.useState('');
  const getPDF = () => {
    const values = {
      contractID: item.contractID,
      //'contractID':item.contractID
    };
    setLoading(1);
    fetch('/api/contract/get', {
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
        setPDFUrl(data.data);
        console.log(data);
        setLoading(2);
      })
      .catch((err) => {
        message.error('网络错误');
      });
  };
  return (
    <Card>
      {loading === 2 && (
        <div style={{ overflow: 'scroll', height: 600 }}>
          <PDFReader url={PDFUrl} />
        </div>
      )}
      <>
        <Button type="primary">完成</Button>
        {loading === 0 && (
          <Button
            onClick={() => {
              getPDF();
            }}
          >
            获取PDF
          </Button>
        )}
        {loading === 1 && <Spin />}
        {loading === 2 && <Button>下载PDF</Button>}
      </>
    </Card>
  );
};
export default StepSix;
