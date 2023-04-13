package com.example.contactServer.Model;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.List;

import org.web3j.protocol.Web3j;

import it.unisa.dia.gas.jpbc.Element;

public abstract class UserInstance {
	public Element pPub;
	public UserInstance(byte[] id,byte[] message,byte[] systemParam,params param,NetInstance net,Element privateKey) {
	}
	public abstract void UserKeyGen();
	public abstract void PreSignAgree();
	public abstract void VESSign();
	public abstract void Aggregate();
	public abstract boolean AggregateVerify();
	
	
	public abstract void load(Web3j web3,String contractAddress,String publicKey,String privateKey);
	public abstract String sign();
	public abstract String deposit(int i);
	public abstract String claim(KGCInstance KGC);
	public abstract String refund();
	
	public abstract Element SignExtract(BigInteger xPub);
	
	public abstract void connect(ServerSocket ss) throws IOException;
	public abstract void transmission_1() throws IOException;
	public abstract void transmission_2() throws IOException;
	public abstract String transmission_3() throws IOException;
	public abstract void retSigns(List<Element> vi) throws IOException;
}
