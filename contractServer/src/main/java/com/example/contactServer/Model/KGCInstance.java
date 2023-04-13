package com.example.contactServer.Model;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import org.web3j.protocol.Web3j;

import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Field;
import it.unisa.dia.gas.jpbc.Pairing;

class params{
	public Field<?> g1,g2;
	public Pairing e;
	public Element p,pT;
	public params(Field<?> g1,Field<?> g2,Pairing e,Element p,Element pT)
	{
		this.g1=g1;
		this.g2=g2;
		this.e=e;
		this.p=p;
		this.pT=pT;
	}
	private Element SHA256(byte []bytes,String key) throws NoSuchAlgorithmException
	{
		var SHA256Instance=MessageDigest.getInstance("SHA-256");
		SHA256Instance.update(StringTools.addSuffix(bytes,key));
		var data=SHA256Instance.digest();
		return g1.newElement().setFromHash(data, 0, data.length);

	}
	public Element H1(byte[] id) throws NoSuchAlgorithmException
	{
		return SHA256(id,"key1");
	}
	public Element H2(byte[] id) throws NoSuchAlgorithmException
	{
		return SHA256(id,"key2");
	}
	public Element H3(byte[] id) throws NoSuchAlgorithmException
	{
		return SHA256(id,"key3");
	}
}
public abstract class KGCInstance {
	public abstract params Setup();
	public abstract Element PartialPrivateKeyExtract(byte[] id) throws NoSuchAlgorithmException;
	
	
	public abstract String deploy(Web3j web3,String publicKey,String privateKey, BigInteger userCo) throws Exception;
	public abstract String claim(BigInteger x,Element pi,byte[] userId,ShareSecret secret);
	public abstract BigInteger getXpub();
}
