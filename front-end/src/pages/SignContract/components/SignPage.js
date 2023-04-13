import { Button, Spin, message } from 'antd';
import React, { useState } from 'react';
import SignatureCanvas from 'react-signature-canvas';

const SignPage = (item) => {
  const [signTip, setSignTip] = useState('请签名');
  const [loading, setLoading] = useState(false);
  let sigCanvas = null;
  const clearSign = () => {
    sigCanvas.clear();
  };
  const handleSign = () => {
    setLoading(true);
    const data = {
      image: sigCanvas.toDataURL('image/png'),
      contractID: item.contractID,
      address: item.address,
    };
    fetch('/api/contract/upload', {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
      }),
      body: JSON.stringify(data),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        //提交成功
      })
      .catch((err) => {
        message.error('网络错误');
      });
    const data1 = {
      currentStatus: 2,
      contractID: item.contractID,
      address: item.address,
    };
    fetch('/api/contract/perform', {
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json;charset=UTF-8',
      }),
      body: JSON.stringify(data1),
    })
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        message.info(data.data);
        //提交成功
        item.setCurrent(4);
      })
      .catch((err) => {
        item.setCurrent(4);
        message.error('网络错误');
      });
  };

  return (
    <div>
      <div>
        <SignatureCanvas
          penColor="#000"
          canvasProps={{
            width: 800,
            height: 160,
          }}
          ref={(ref) => {
            sigCanvas = ref;
          }}
          onBegin={() => setSignTip('')}
        />
        {signTip && <div>{signTip}</div>}
      </div>
      {!loading && (
        <div>
          <Button onClick={clearSign}>清除</Button>
          <Button onClick={handleSign} type="primary">
            签字确认
          </Button>
        </div>
      )}
      {loading && <Spin />}
    </div>
  );
};

export default SignPage;
