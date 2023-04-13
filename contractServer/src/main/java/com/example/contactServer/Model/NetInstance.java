package com.example.contactServer.Model;
import java.util.List;

public abstract class NetInstance {
	public NetInstance(List<byte[]> users)
	{
		
	}
	public abstract void init();//����ǰ��ʼ��
	public abstract void send(byte[] userid,byte[] data);//�����û�user����data
	public abstract boolean allUserSent();//�����û��Ƿ��ѷ���
	public abstract boolean allUserRecieved();//�����û��Ƿ��ѽ���
	public abstract List<pair<byte[], byte[]>> receive();//��������
}
