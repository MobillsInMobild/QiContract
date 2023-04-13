import React from 'react';
import ReactDOM from 'react-dom';
import 'antd/dist/antd.css';
import { List, message, Avatar, Spin } from 'antd';

const ContractList=(items)=> {
    return (
          <List
            dataSource={items.data}
            renderItem={item => (
              (((item.status&&!items.check)||(!item.status&&items.check))&&
              <List.Item key={item.id}>
                <List.Item.Meta
                  avatar={
                    <Avatar src="https://zos.alipayobjects.com/rmsportal/ODTLcjxAfvqbxHnVXCYX.png" />
                  }
                  title={<a href="#" onClick={()=>{items.setNavigate(true)}}>{item.contractID}</a>}
                  description={item.title}
                />
              </List.Item>)
            )}
          >
          </List>
    );

}
export default ContractList;