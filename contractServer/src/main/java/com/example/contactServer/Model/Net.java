package com.example.contactServer.Model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Net extends NetInstance{
	private int users;
	private int recievedUsers=0;
	private ReentrantLock lock=new ReentrantLock();
	private List<pair<byte[],byte[]>> data=Collections.synchronizedList(new ArrayList<>());
	public Net(List<byte[]> users) {
		super(users);
		this.users=users.size();
	}
	public void init() {
	}
	public void send(byte[] userid, byte[] data) {
		lock.lock();
		if(this.data.size()==users)
		{
			this.data=Collections.synchronizedList(new ArrayList<>());
		}
		this.data.add(new pair<>(userid,data));
		lock.unlock();
	}
	public boolean allUserSent() {
		if (!lock.tryLock())
			return false;
		int size=data.size();
		lock.unlock();
		return size==users;
	}
	public boolean allUserRecieved()
	{
		lock.lock();
		lock.unlock();
		return recievedUsers==users;
	}
	public List<pair<byte[], byte[]>> receive() {
		if (recievedUsers==users)
			recievedUsers=0;
		lock.lock();
		recievedUsers+=1;
		lock.unlock();
		return data;
	}

}
