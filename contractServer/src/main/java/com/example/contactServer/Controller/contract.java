package com.example.contactServer.Controller;
import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

import com.example.contactServer.Model.ProcessingContract;
import com.example.contactServer.Model.ShareSecret;
import com.example.contactServer.Service.PersonRepository;
import com.example.contactServer.Utils.ProjectUtils;
import com.example.contactServer.Utils.Tools;

import jnr.ffi.Struct.off_t;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@RestController
@SpringBootApplication
class contract
{
	@Autowired
	private PersonRepository personRepository;
	static Web3j web3j;
	static Map<String, ProcessingContract> contracts=new HashMap<>();
	static Map<String, Integer> signNum=new HashMap<>();
	static ReentrantLock lock=new ReentrantLock();
	static {
		web3j=Web3j.build(new HttpService(ProjectUtils.WEB3JADDRESS));
	}
	@PostMapping("/api/contract/query")
	public Map<String, Object> query(@RequestBody Map<String, String> data)
	{
		Map<String, Object> result=new HashMap<>();
		var address=data.get("address");
		var contractID=data.get("contractID");
		result.put("currentStatus", -1);
		var contractInfo=personRepository.getContractInfo(contractID).get(0);
		try {		
		if (!JSONArray.fromObject(contractInfo[3]).contains(address))
		{
			System.out.println("invalid user!!");
			return result;
		}
		}
		catch (Exception e) {
			return result;
		}
		try
		{		
			result.put("contractAddress", contractInfo[0]);
			var contract=personRepository.personContracts(address).get(0);
			var json=new JSONObject();
			if (contract!=null)
			{
				json=JSONObject.fromObject(contract[0].get("contracts"));
				var now=json.get(contractID);
				if (now!=null)
				{
					result.put("currentStatus", now);
					return result;
				}
			}
			json.put(contractID, 0);
			personRepository.newContractStatus(address, json.toString());
			result.put("currentStatus", 0);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@PostMapping("/api/contract/get")
	public Map<String, Object> get(@RequestBody Map<String, String> data)
	{
		Map<String, Object> result=new HashMap<>();
		var filename=personRepository.getPDF(data.get("contractID")).get(0)[0];
		var pdfUrl="/files?file="+filename;
		
		result.put("data", pdfUrl);
		return result;
	}
	@PostMapping("/api/contract/new")
	public Map<String, Object> newContract(@RequestBody Map<String, Object> data)
	{
		Map<String, Object> result=new HashMap<>();
		String address=(String)data.get("address");
		String privateKey=(String)data.get("privateKey");
		String title=(String)data.get("title");
		String content=(String)data.get("content");
		String tip=(String)data.get("tip");
		String contractAddress=(String)data.get("contractAddress");
		String userGroup=(String)data.get("userGroup");
		int num=(int)data.get("num");
		try {
		    var contractId=Math.abs(new Random().nextInt());
	    	var text=new JSONObject();
	    	text.put("title", title);
	    	text.put("content", content);
	    	text.put("tip", tip);
	    	personRepository.newContract(""+contractId, address, contractAddress, text.toString(), num,userGroup);
	    	result.put("contractID", contractId);
	    	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@PostMapping("/api/contract/upload")
	public Map<String, Object> upload(@RequestBody Map<String, String> data)
	{
		Map<String, Object> result=new HashMap<>();
		var address=data.get("address");
		var contractID=data.get("contractID");
		var image=data.get("image");
		var rand=Tools.getRandomString(10)+".png";
		var filename=ProjectUtils.UPLOADPATH+rand;
		Tools.base64StringToImage(image, filename);
		var detail=personRepository.getPicture(contractID).get(0);
		String picSet="";
		if (detail==null)
			picSet="[\""+rand+"\"]";
		else {
			var pics=JSONArray.fromObject(detail[0].get("pictures"));
			var tmp=pics;
			tmp.add(rand);
			picSet=tmp.toString();
		}
		personRepository.setPicture(picSet, contractID);
		result.put("recieved", true);
		return result;
	}
	@PostMapping("/api/contract/perform")
	public Map<String, Object> perform(@RequestBody Map<String, Object> data)
	{
		Map<String, Object> result=new HashMap<>();
		String address=(String)data.get("address");
		String contractID=(String)data.get("contractID");
		int currentStatus=(int)data.get("currentStatus");
		String privateKey=(String)data.get("privateKey");
		int step=0;
		if (data.get("step")!=null)
			step=(int)data.get("step");
		if (currentStatus==0)
        {
        		return joinContract(contractID, address, privateKey);
        }
		if (currentStatus==1)
	        	return getContract(contractID, address);
	    if (currentStatus==2)
	        	return signContract(contractID, address);
	    if (currentStatus==3)
	        	return signVerify(contractID, address,step);
		return result;
	}
	private void setContractStatus(String address,String contractID,Integer status)
	{
		var detail=personRepository.personContracts(address).get(0);
		var contracts=JSONObject.fromObject(detail[0].get("contracts"));
		contracts.put(contractID, status);
		personRepository.newContractStatus(address, contracts.toString());
	}
	private Map<String, Object> joinContract(String contractID,String address,String privateKey)
	{
		Map<String, Object> r=new HashMap<>();
		lock.lock();
		try {
		if (!contracts.containsKey(contractID))
		{
			var result=personRepository.getContractInfo(contractID).get(0);
			String contractAddress=(String)result[0];
			int userCount=(int)result[1];
			String message=(String)result[2];
			var processingContract=new ProcessingContract(contractAddress,userCount, message,personRepository);
			processingContract.userAddress.add(address);
			processingContract.userPrivateKey.add(privateKey);
			contracts.put(contractID, processingContract);
			signNum.put(contractID, 0);
			var thread=new Thread(new Runnable() {
				public void run() {
					try {
						processingContract.process();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			thread.start();
		}
		else {
			var processingContract=contracts.get(contractID);
			processingContract.userAddress.add(address);
			processingContract.userPrivateKey.add(privateKey);
		}
			setContractStatus(address, contractID, 1);
			r.put("status", true);
		}
		catch (Exception e) {
			r.put("status", false);
			e.printStackTrace();
		}
		lock.unlock();
		return r;
	}
	public Map<String, Object> getContract(String contractID,String address)
	{
		Map<String, Object> r=new HashMap<>();
		try {
			var result=personRepository.getContractInfo(contractID).get(0);
			String contractAddress=(String)result[0];
			int userCount=(int)result[1];
			var message=JSONObject.fromObject(((String)result[2]).replace("\n","\\n"));
			var title=message.get("title");
			var content=message.get("content");
			var tip=message.get("tip");
			setContractStatus(address, contractID, 2);
			r.put("contractAddress", contractAddress);
			r.put("title", title);
			r.put("content", content);
			r.put("tip", tip);
			r.put("num", userCount);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	public Map<String, Object> signContract(String contractID,String address)
	{
		Map<String, Object> r=new HashMap<>();
		try
		{
			var result=personRepository.getContractInfo(contractID).get(0);
			int userCount=(int)result[1];
			var processingContract=contracts.get(contractID);
			processingContract.userSign.add(true);
			while (processingContract.signProcess.size()!=userCount);
			lock.lock();
			setContractStatus(address, contractID, 3);
			r.put("data", processingContract.signProcess.get(processingContract.userMap.get(address)));
			lock.unlock();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
	public Map<String, Object> signVerify(String contractID,String address,int step)
	{
		Map<String, Object> r=new HashMap<>();
		try {
			var result=personRepository.getContractInfo(contractID).get(0);
			int userCount=(int)result[1];
			var processingContract=contracts.get(contractID);
			int now;
			while (true)
			{
				now=(signNum.get(contractID)%(2*userCount));
				if (processingContract.userAddress.get(now/2).toLowerCase().equals(address.toLowerCase()))
				{
					if (now%2==1)
					{
						signNum.put(contractID, now+1);
						if (step==5)
							return r;
					}
					else 
						break;
				}
			}
			signNum.put(contractID, now+1);
			System.out.println(address);
			if (step<4)
				return r;
			processingContract.userVerify.add(true);
			setContractStatus(address, contractID, 4);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return r;
	}
}