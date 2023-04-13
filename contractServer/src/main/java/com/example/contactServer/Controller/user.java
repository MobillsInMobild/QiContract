package com.example.contactServer.Controller;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.map.HashedMap;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import com.example.contactServer.Service.PersonRepository;
import com.example.contactServer.Utils.ProjectUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
@RestController
@SpringBootApplication
class userLogin
{
	@Autowired
	private PersonRepository personRepository;
	static Web3j web3j;
	static {
		web3j=Web3j.build(new HttpService(ProjectUtils.WEB3JADDRESS));
	}
	@PostMapping("/api/login")
	public Map<String,Boolean> login(@RequestBody Map<String,String> data)
	{
		Map<String, Boolean> result=new HashMap<>();
		result.put("login", false);
		String address=data.get("address");
		String privateKey=data.get("privateKey");
		try {
			
			var accounts=web3j.ethAccounts().send().getAccounts();
			var exist=false;
			for (var account:accounts)
			{
				if (account.equals(address))
					exist=true;
			}
			if (!exist)
				return result;
			personRepository.addPerson(address, privateKey);
			result.put("login", true);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		return result;
	}
	@PostMapping("/api/user/details")
	public Map<String, Object> details(@RequestBody Map<String, String> data)
	{
		Map<String, Object> result=new HashMap<>();
		try {
			var detail=personRepository.personDetails(data.get("address")).get(0);
			result.put("address", detail[0]);
			result.put("phone", detail[1]);
			result.put("email", detail[2]);
			result.put("name", detail[3]);
			result.put("tips", detail[4]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	@PostMapping("/api/user/update")
	public Map<String, Object> Update(@RequestBody Map<String, String> data)
	{
		Map<String, Object> result=new HashMap<>();
		var address=data.get("address");
		var password=data.get("password");
		var name=data.get("name");
		var email=data.get("email");
		var tips=data.get("tips");
		var phone=data.get("phone");
		personRepository.updatePersonDetails(address, password, name, email, phone, tips);
		return result;
	}
	@PostMapping("/api/user/contractInfo")
	public List<Map<String, Object>> contractInfo(@RequestBody Map<String, Object> data)
	{
		List<Map<String, Object>> result=new ArrayList<>();
		try {
			String account=((Map<String,String>)data.get("address")).get("account");
			var detail=personRepository.personContracts(account).get(0);
			var jsonData=JSONObject.fromObject(detail[0].get("contracts"));
			for (var contract:jsonData.keySet())
			{
				Map<String, Object> json=new HashMap<>();
				var now=Integer.parseInt(contract.toString());
				var _result=personRepository.getContract(now).get(0);
				json.put("contractID", _result[0]);
				json.put("status", jsonData.getInt(contract.toString())==4);
				json.put("title", JSONObject.fromObject(_result[1].toString().replace("\n","\\n")).get("title"));
				json.put("contractAddress", _result[2]);
				if (_result[3]!=null)
					json.put("target", "/files?file="+(String)_result[3]);
				result.add(json);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}