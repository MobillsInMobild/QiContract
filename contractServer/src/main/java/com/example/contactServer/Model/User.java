package com.example.contactServer.Model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;
import org.web3j.utils.Convert;

import it.unisa.dia.gas.jpbc.Element;

public class User extends UserInstance{
	
	private byte[] id;
	private byte[] message;
	private byte[] systemParam;
	private params params;
	private NetInstance network;
	private Element privateKey;
	private BigInteger x;
	private BigInteger xPub=new BigInteger("0");
	
	private Element Pi;
	private triple<Element, Element, Element> sigma;
	
	
	private List<pair<byte[], triple<Element,Element,Element>>> messagePair=new ArrayList<>();
	private List<Element> Ss=new ArrayList<>();
	private Element Ps=null;
	private Element Qs=null;
	private Element VES=null;
	private int userCount=0;
	
	private ShareSecret secret;
	
	private Socket s;
	private BufferedReader br;
	private BufferedWriter bw;
	
	class comms {
		byte[] ID;
		byte[] Ci;
		byte[] G;
		byte[] H;
		byte[] Pi;
		byte[] ki;
		public void setid(byte[] ID){
			this.ID = ID;
		}
		public void setcomm(byte[] Ci){
			this.Ci = Ci;
		}
		public void setG(byte[] G){
			this.G = G;
		}
		public void setH(byte[] H){
			this.H = H;
		}
		public void setp(byte[] Pi){
			this.Pi = Pi;
		}
		public void setk(byte[] ki){
			this.ki = ki;
		}
	}
	public User(byte[] id,byte[] message,byte[] systemParam,params param,NetInstance net,Element privateKey) {
		super(id, message, systemParam, param, net, privateKey);
		this.id=id;
		this.message=message;
		this.systemParam=systemParam;
		this.params=param;
		this.network=net;
		this.privateKey=privateKey;
	}
	public void UserKeyGen()
	{
		BigInteger q = params.g1.getOrder().subtract(new BigInteger("1"));
		x = StringTools.BigRandom(q);
		Pi = params.p.pow(x);
	}
	public void PreSignAgree()
	{
		BigInteger q = params.g1.getOrder().subtract(new BigInteger("1"));
		BigInteger xx = StringTools.BigRandom(q);//xx is xi'
		Element Ppi = params.p.pow(xx);//Ppi is Pi'



		List<pair<byte[], byte[]>> mid;
		List<byte[]> ui = new ArrayList<>();
		Map<byte[],comms> C = new HashMap<>();
		comms uc;
		BigInteger ki = StringTools.BigRandom(q);//kki is ki'
		Element g = params.g1.newRandomElement();
		network.send(id,g.toBytes());
		while(true){
			if(network.allUserSent()){ break; }
		}	
		mid=network.receive();
		while(true){
			if(network.allUserRecieved()){ break; }
		}
		network.init();
		for (int i = 0; i < mid.size(); i++) {
			uc = new comms();
			uc.setG(mid.get(i).second);
			ui.add(mid.get(i).first);
			C.put(mid.get(i).first,uc);
		}



		Element h = params.g1.newRandomElement();
		network.send(id,h.toBytes());
		while(true){
			if(network.allUserSent()){ break; }
		}
		mid=network.receive();
		for (int i = 0; i < mid.size(); i++) {
			uc = C.get(mid.get(i).first);
			uc.setH(mid.get(i).second);
			C.put(mid.get(i).first,uc);
		}
		while(true){
			if(network.allUserRecieved()){ break; }
		}

		BigInteger Ci = new BigInteger(g.pow(new BigInteger(Ppi.toBytes())).mul(h.pow(ki)).toBytes()).mod(q);

		network.send(id,Ci.toByteArray());
		while(true){
			if(network.allUserSent()){ break; }
		}
		mid=network.receive();
		for (int i = 0; i < mid.size(); i++) {
			uc = C.get(mid.get(i).first);
			uc.setcomm(mid.get(i).second);
			C.put(mid.get(i).first,uc);
		}
		while(true){
			if(network.allUserRecieved()){ break; }
		}


		network.send(id,Ppi.toBytes());
		while(true){
			if(network.allUserSent()){ break; }
		}
		mid=network.receive();
		for (int i = 0; i < mid.size(); i++) {
			uc = C.get(mid.get(i).first);
			uc.setp(mid.get(i).second);
			C.put(mid.get(i).first,uc);
		}
		while(true){
			if(network.allUserRecieved()){ break; }
		}


		network.send(id,ki.toByteArray());
		while(true){
			if(network.allUserSent()){ break; }
		}
		mid=network.receive();
		for (int i = 0; i < mid.size(); i++) {
			uc = C.get(mid.get(i).first);
			uc.setk(mid.get(i).second);
			C.put(mid.get(i).first,uc);
		}
		while(true){
			if(network.allUserRecieved()){ break; }
		}


		Element gg;
		Element hh;
		Element pp;
		BigInteger Cci;
		BigInteger kk;
		for(int i=0;i<C.size();i++){
			gg=params.g1.newElementFromBytes(C.get(ui.get(i)).G);
			hh=params.g1.newElementFromBytes(C.get(ui.get(i)).H);
			pp=params.g1.newElementFromBytes(C.get(ui.get(i)).Pi);
			kk = new BigInteger(C.get(ui.get(i)).ki);
			Cci = new BigInteger(C.get(ui.get(i)).Ci);
			if(!new BigInteger(gg.pow(new BigInteger(pp.toBytes())).mul(hh.pow(kk)).toBytes()).mod(q).equals(Cci)){
				throw new RuntimeException();
			}
			else{
				xPub = xPub.add(kk);
				if(i==0){
					pPub = pp;
				}
				else{
					pPub = pPub.mul(pp).getImmutable();
				}
			}
		}
	}
	public void VESSign() {
		BigInteger r = StringTools.BigRandom(this.params.g1.getOrder());
		BigInteger b = StringTools.BigRandom(this.params.g1.getOrder());

		Element R = this.params.p.duplicate().pow(r).getImmutable();
		Element B = this.params.p.duplicate().pow(b).getImmutable();

		Element w;
		Element S;
		try {
			w = this.params.H2(this.systemParam);
			S = this.params.H3(StringTools.addSuffix(systemParam, StringTools.addSuffix(message, StringTools.addSuffix(this.id, StringTools.addSuffix(this.Pi.toBytes(), StringTools.addSuffix(R.toBytes(), B.toBytes()))))));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("No Such Algorithm!");
		}
		Element VES1 =  this.privateKey.duplicate().mul((w.pow(this.x)));
		Element VES2 =VES1.duplicate().mul(S.pow(r));
		Element VES3 =this.pPub.duplicate().pow(b);
        Element VESi = VES2.duplicate().mul(VES3).getImmutable();
		this.sigma= new triple<>(R,B,VESi);
	}

	private Map<byte[],byte[]> sendByte(byte[] data)
	{
		Map<byte[], byte[]> result=new HashMap<>();
		network.send(id, data);
		while (!network.allUserSent());
		var recieved=network.receive();
		for (var i=0;i<recieved.size();i++)
		{
			var message=recieved.get(i);
			result.put(message.first, message.second);
		}
		while (!network.allUserRecieved());
		return result;
	}
	private Map<byte[], Element> sendG1Element(Element data)
	{
		Map<byte[], Element> result=new HashMap<>();
		network.send(id, data.toBytes());
		while (!network.allUserSent());
		var recieved=network.receive();
		for (var i=0;i<recieved.size();i++)
		{
			var message=recieved.get(i);
			result.put(message.first, params.g1.newElementFromBytes(message.second));
		}
		while (!network.allUserRecieved());
		return result;
	}
	private void preAggregate()
	{	
		var messageMap=sendByte(message);
		var RMap=sendG1Element(sigma.first);
		var BMap=sendG1Element(sigma.second);
		var VESMap=sendG1Element(sigma.third);
		var PiMap=sendG1Element(Pi);
		for (var user:messageMap.keySet())
		{
			userCount+=1;
			var message=messageMap.get(user);
			var Ri=RMap.get(user);
			var Bi=BMap.get(user);
			var VESi=VESMap.get(user);
			var P=PiMap.get(user);
			if (Ri==null||Bi==null||VESi==null||P==null)
				throw new RuntimeException("network error");
			messagePair.add(new pair<>(message,new triple<>(Ri,Bi,VESi)));
			if (Ps==null)
				Ps=P.getImmutable();
			else
				Ps=Ps.mul(P.getImmutable()).getImmutable();
			try {
				if (Qs==null)
					Qs=params.H1(user).getImmutable();
				else 
					Qs=Qs.mul(params.H1(user).getImmutable()).getImmutable();
				Ss.add(params.H3(StringTools.addSuffix(systemParam, StringTools.addSuffix(message, StringTools.addSuffix(user, StringTools.addSuffix(P.toBytes(), StringTools.addSuffix(Ri.toBytes(), Bi.toBytes())))))).getImmutable());
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException("No Such Algorithm!");
			}
		}
	}
	public void Aggregate()
	{
		preAggregate();
		for (var _message:messagePair)
		{
			var VESi=_message.second.third;
			if (VES==null)
				VES=VESi.getImmutable();
			else 
				VES=VES.mul(VESi.getImmutable()).getImmutable();
		}
	}
	public boolean AggregateVerify()
	{
		List<Element> Rs=new ArrayList<>();
		Element Bs=null;
		for (var _message:messagePair)
		{
			var sigmai=_message.second;
			if (Bs==null)
				Bs=sigmai.second.getImmutable();
			else
				Bs=Bs.mul(sigmai.second.getImmutable()).getImmutable();
			Rs.add(sigmai.first);
		}
		Element omega;
		try {
			omega = params.H2(systemParam).getImmutable();
		} catch (NoSuchAlgorithmException e) {
			omega=null;
		}
		var result=params.e.pairing(VES, params.p).getImmutable();
		
		var tmp=params.e.pairing(params.pT, Qs).getImmutable();
		var tmp1=params.e.pairing(omega, Ps).getImmutable();
		tmp=tmp.mul(tmp1).getImmutable();
		for (int i=0;i<userCount;i++)
			tmp=tmp.mul(params.e.pairing(Ss.get(i), Rs.get(i)));
		tmp=tmp.mul(params.e.pairing(pPub, Bs)).getImmutable();
		return result.isEqual(tmp);
	}
	@Override
	public void load(Web3j web3, String contractAddress, String publicKey, String privateKey) {
		// TODO Auto-generated method stub
		secret = ShareSecret.load(contractAddress, web3, Credentials.create(privateKey), new DefaultGasProvider());
	}
	@Override
	public String sign() {
		// TODO Auto-generated method stub
		try {
			return secret.sign().send().getTransactionHash();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String deposit(int i) {
		// TODO Auto-generated method stub
		try {
			BigInteger value = Convert.toWei(""+(i+1), Convert.Unit.ETHER).toBigInteger();
			return secret.Deposit(value).send().getTransactionHash();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	@Override
	public String claim(KGCInstance KGC) {
		// TODO Auto-generated method stub
		return KGC.claim(x, Pi, id, secret);
	}
	@Override
	public String refund() {
		// TODO Auto-generated method stub
		try {
			 return secret.refund().send().getTransactionHash();
		} catch (Exception e) {
			System.out.println("Needn't to refund...");
			return null;
		}
	}
	@Override
	public Element SignExtract(BigInteger xPub) {
		// TODO Auto-generated method stub
		return sigma.third.sub(sigma.second.pow(xPub));
	}
	
	@Override
	public void connect(ServerSocket ss) throws IOException {
		s = ss.accept();
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
		bw = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
	}
	@Override
	public void transmission_1() throws IOException{
		//��һ�δ���
		if(br.readLine().equals("1")) {
			bw.write("Success");
			bw.flush();
		}
	}
	public void transmission_2() throws IOException{
		//�ڶ��δ���
		if(br.readLine().equals("2")) {
			bw.write(new String(message));
			bw.flush();
		}
	}
	public String transmission_3() throws IOException{
		//�����δ��䣬�����û�˽Կ
		if(br.readLine().equals("3")) {
			bw.write("Received");
			bw.flush();
		}
		var result=br.readLine();
		System.out.println(result);
		return result;
	}
	@Override
	public void retSigns(List<Element> vi) throws IOException {
		//��ͻ��˷��������û�ǩ����ĺ�ͬ
		bw.write("Success");
		bw.flush();
		br.readLine();
		bw.write(new String(message)+vi.toString());
		bw.flush();
	}
}
