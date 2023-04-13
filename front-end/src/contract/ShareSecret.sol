pragma solidity ^0.4.26;


contract ShareSecret{
    

    uint all;//总人数
    uint signer=0;//当前报名人数
    uint payer=0;//当前提交保证金人数
    uint wrong=10000;//提交错误秘密值的用户编号
    

    struct User{
        uint value;//已经存入的保证金
        bool depositFlag;//是否提交保证金
        bool claimFlag;//是否提交正确的秘密值
        bool refundFlag;//是否退款
        uint time;//记录签署方的截止时间
    }

    struct Id{
        uint id;
        bool signFlag;
    }


    // 地址到用户编号的映射（从1开始）
    mapping (address=>Id) addr2Id;

    User[] public users;

    // 全部用户已注册
    modifier allSigned() {
      require (signer==all);
         _;
    }

    // 全部用户尚未注册
    modifier unAllSigned() {
      require (signer<all);
         _;
    }

    // 全部用户已提交保证金
    modifier allDeposit() {
      require (payer==all);
         _;
    }

    // 全部用户尚未提交保证金
    modifier unAllDeposit() {
      require (payer<all);
         _;
    }




    //确定本次交换秘密值总人数
    constructor(uint _all) public {
        all=_all;
    }

    //签署者获取对应的编号(从1开始，对应数组从0开始)
    function sign() public unAllSigned{
        require(addr2Id[msg.sender].signFlag==false);
        addr2Id[msg.sender].signFlag=true;
        addr2Id[msg.sender].id=signer;
        users.push(User(0,false,false,false,0));
        signer+=1;
    }

    //签署者缴纳保证金
    function Deposit() public payable allSigned unAllDeposit{
        //仅允许未缴纳保证金的用户缴纳保证金
        require(users[addr2Id[msg.sender].id].depositFlag==false);
        users[addr2Id[msg.sender].id].value+=msg.value;
        //提交了满足要求的保证金
        if (users[addr2Id[msg.sender].id].value>=(addr2Id[msg.sender].id+1)*1 ether){
            users[addr2Id[msg.sender].id].depositFlag=true;
            //分配截止时间
            users[addr2Id[msg.sender].id].time=now+((addr2Id[msg.sender].id+1)*1 seconds);
            //找零
            msg.sender.transfer(users[addr2Id[msg.sender].id].value-(addr2Id[msg.sender].id+1)*1 ether);            //gas 不变的情况下，transfer可防止重入攻击
            payer+=1;
        }        
    }


    //释放秘密值
    function Claim(bool secret)public allDeposit returns(bool){
        //无人提交错误秘密值
        require(wrong==10000);
        //只有之前序号的用户释放了秘密值才可进行
        uint tmp=addr2Id[msg.sender].id;
        require(tmp==0||users[tmp-1].claimFlag==true);
        //仅能提交一次
        require(users[addr2Id[msg.sender].id].claimFlag==false);
        if(now<=users[addr2Id[msg.sender].id].time && secret==true){
            users[addr2Id[msg.sender].id].claimFlag=true;
            msg.sender.transfer((addr2Id[msg.sender].id+1)*1 ether);
            return true;
        }else{
            //提交错误秘密值，记录编号
            users[addr2Id[msg.sender].id].claimFlag=false;
            wrong=addr2Id[msg.sender].id;
            return false;
        }
    }




    //拿回保证金
    function refund()public allDeposit{
        require(wrong!=10000);
        require(users[addr2Id[msg.sender].id].refundFlag==false);
        //在错误秘密值的用户之前的获得 1dB 奖励
        if(addr2Id[msg.sender].id<wrong){
            msg.sender.transfer(1 ether);
            users[addr2Id[msg.sender].id].refundFlag=true;
        //在错误秘密值的用户之后的退回保证金
        }else if(addr2Id[msg.sender].id>wrong){
            msg.sender.transfer((addr2Id[msg.sender].id+1)*1 ether);
            users[addr2Id[msg.sender].id].refundFlag=true;
        }
    }
    
    
}