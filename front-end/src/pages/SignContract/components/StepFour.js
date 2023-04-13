import React from 'react';
import {Card, Button,message,Typography} from 'antd';
import SignPage from "./SignPage"
const { Title} = Typography;
const StepFour=(item)=>{
    return (
        <Card>
            <Title level={2}>合同ID:    {item.contractID}</Title>
            <SignPage contractID={item.contractID} address={item.address} setCurrent={item.setCurrent}/>
        </Card>
    )
}
export default StepFour;