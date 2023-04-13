package com.example.contactServer.Model;
import java.util.List;

import it.unisa.dia.gas.jpbc.Element;

public class Factory {
	public static KGCInstance getKGCInstance()
	{
		return new KGC();
	}
	public static NetInstance getNetInstance(List<byte[]> userID)
	{
		return new Net(userID);
	}
	public static UserInstance getUserInstance(byte[] id,byte[] message,byte[] systemParam,params param,NetInstance net,Element privateKey)
	{
		return new User(id,message,systemParam,param,net,privateKey);
	}
}
