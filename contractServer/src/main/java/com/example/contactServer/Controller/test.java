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
@RestController
@SpringBootApplication
class test
{
	@Autowired
	private PersonRepository personRepository;
	@RequestMapping("/test")
	public List<Object[]> login(@RequestParam("data")String data)
	{
		Map<String, Boolean> result=new HashMap<>();
		return personRepository.getPictureByAddress(data);
	}
}