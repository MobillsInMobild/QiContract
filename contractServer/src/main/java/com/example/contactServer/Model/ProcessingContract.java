package com.example.contactServer.Model;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import it.unisa.dia.gas.jpbc.Element;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.example.contactServer.Service.PersonRepository;
import com.example.contactServer.Utils.ProjectUtils;
import com.pdflib.pdflib;

public class ProcessingContract 
{
	private PersonRepository personRepository;
	
	private int userCount;
	public List<String> userAddress=Collections.synchronizedList(new ArrayList<>());
	public List<String> userPrivateKey=Collections.synchronizedList(new ArrayList<>());
	public List<Boolean> userSign=Collections.synchronizedList(new ArrayList<>());
	public List<String> signProcess=Collections.synchronizedList(new ArrayList<>());
	public List<Boolean> userVerify=Collections.synchronizedList(new ArrayList<>());
	public List<JSONObject> verifyProcess=Collections.synchronizedList(new ArrayList<>());
	public Map<String, Integer> userMap=new HashMap<>(); 
	private String message;
	private String contractAdress;	
	private static final int a4_width=595;
	private static final int a4_height=842;
	private static final int contentLine=29;
	private static final int contentWeight=36;
	private static final int titleLine=16;
	private static final int titleWeight=54;
	private static final int tipLine=50;
	private static final int tipWeight=22;
	private static final int picHeight=20;
	private static final int picWidth=100;
	public  ProcessingContract(String contractAdress,int userCount,String message,PersonRepository p)
	{
		this.userCount=userCount;
		this.message=message;
		this.contractAdress=contractAdress;
		this.personRepository=p;
	}
	

	private int min(int a,int b)
	{
		if (a<b)
			return a;
		return b;
	}
	public void _createPDF(String filePath,String title,List<String> contents,String tip,List<String> pics)
	{
		try {
			var p=new pdflib();
			p.set_parameter("license","w900105-009100-A5R2BK-PP6MH2-3KARA2");
			p.open_file(filePath);
			int pos=0;
			int h=0;
			while (true)
			{
				h=50;
				p.begin_page(a4_width,a4_height);
				while (pos<title.length()&&h<a4_height)
				{
					var fontSong=p.load_font("C:\\Windows\\Fonts\\simsun:1","unicode", "");
					p.setfont(fontSong, 24);
					if (title.length()-pos<titleLine)
					{
						p.set_text_pos(a4_width/2-(a4_width-100)*(title.length()-pos)/titleLine/2, a4_height-h);
						p.show(title.substring(pos,pos+title.length()-pos));
					}
					else {
						p.set_text_pos(50,a4_height-h);
						p.show(title.substring(pos,pos+titleLine));
					}
					pos+=titleLine;
					h+=titleWeight;
				}
				if (pos<title.length())
					p.end_page();
				else 
					break;
			}
			pos=0;
			boolean firstPage;
			for (var content:contents)
			{
				firstPage=true;
				while (true)
				{
					if (firstPage)
						firstPage=false;
					else {
						h=50;
						p.begin_page(a4_width, a4_height);
					}
					while (pos<content.length()&&h<a4_height)
					{
						var fontSong=p.load_font("C:\\Windows\\Fonts\\simsun:1","unicode", "");
						p.setfont(fontSong, 16);
						p.set_text_pos(50, a4_height-h);
						p.show(content.substring(pos,pos+min(content.length()-pos, contentLine)));
						pos+=contentLine;
						h+=contentWeight;
					}
					if (pos<content.length())
						p.end_page();
					else
						break;
				}
				pos=0;
			}
			firstPage=true;
			while (true)
			{
				if (firstPage)
					firstPage=false;
				else {
					h=50;
					p.begin_page(a4_width, a4_height);					
				}
				while (pos<tip.length()&&h<a4_height)
				{
					var fontSong=p.load_font("C:\\Windows\\Fonts\\simsun:1","unicode", "");
					p.setfont(fontSong, 12);
					p.set_text_pos(50, a4_height-h);
					p.show(tip.substring(pos,pos+min(tip.length()-pos, tipLine)));
					pos+=tipLine;
					h+=tipWeight;
				}
				if (pos>=tip.length())
				{
					for (var pic:pics)
					{
						if (h+picHeight*3<a4_height)
						{
							var nImage=p.open_image_file("png", pic, "", 0);
							p.place_image(nImage, a4_width-picWidth*3/2, a4_height-(h+picHeight), 0.25);							
							System.out.println((a4_width-picWidth*3/2)+" "+(a4_height-(h+picHeight)));

							p.close_image(nImage);
							h+=2*picHeight;
						}
						else {
							p.end_page();
							p.begin_page(a4_width, a4_height);
							var nImage=p.open_image_file("png", pic, "", 0);
							p.place_image(nImage, a4_width-picWidth*3/2, a4_height-picHeight, 0.25);
							p.close_image(nImage);
							h=2*picHeight;
						}
					}
					p.end_page();
					break;
				}
			}
			p.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    public String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
          int number=random.nextInt(62);
          sb.append(str.charAt(number));
        }
        return sb.toString();
    }
	public void createPDF(String contractAddress)
	{
		try {
			var result=personRepository.getPictureByAddress(contractAddress).get(0);
			var json=JSONArray.fromObject(result[0]);
			List<String> pic=new ArrayList<>();
			for (var _pic:json)
			{
				var filename=ProjectUtils.UPLOADPATH+_pic;
				System.out.println(filename);
				pic.add(filename);
			}
			var _content=(String)result[1];
			var json1=JSONObject.fromObject(_content.replace("\n","\\n"));
			var title=json1.getString("title");
			var contents=Arrays.asList(json1.getString("content").split("\n"));
			var tip=json1.getString("tip");
			var rand=getRandomString(10)+".pdf";
			var pdfname=ProjectUtils.UPLOADPATH+rand;
			_createPDF(pdfname, title, contents, tip, pic);
			personRepository.setPDF(rand, contractAddress);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
    public void process( ) throws IOException, Exception
    {
System.out.println("loaded:"+userCount);
    	while (userPrivateKey.size()!=userCount);
System.out.println("joined");
    	for (var i=0;i<userCount;i++)
    		userMap.put(userAddress.get(i), i);
		var KGC=Factory.getKGCInstance();
		List<byte[]> userId=new ArrayList<>();//�û����id
		List<byte[]> messages=new ArrayList<>();//�û���Ϣ
		List<UserInstance> users=new ArrayList<>();//�û���
		for (var i=0;i<userCount;i++)
		{
			userId.add(new String("No."+i).getBytes());
			messages.add(message.getBytes());
		}
		var network=Factory.getNetInstance(userId);
		//Setup
		var param=KGC.Setup();
		var sysParam=param.e.getG1().newRandomElement().toBytes();
				
		//PartialPrivateKeyExtract
		for (var i=0;i<userCount;i++)
			users.add(Factory.getUserInstance(userId.get(i), messages.get(i), sysParam, param, network, KGC.PartialPrivateKeyExtract(userId.get(i))));
System.out.println("waiting for sign");	
		while (userSign.size()!=userCount);
System.out.println("start sign");
		//UserKeyGen
		for (var i=0;i<userCount;i++)
			users.get(i).UserKeyGen();
		//PreSignAgree
		List<Thread> threads=new ArrayList<>();
		for (var i=0;i<userCount;i++)
		{
			var k=i;
			var thread=new Thread(new Runnable() {
				public void run() {
					users.get(k).PreSignAgree();	
				}
			});
			threads.add(thread);
			thread.start();
		}
		for (var i=0;i<userCount;i++)
			threads.get(i).join();
		//VESSign
		for (var i=0;i<userCount;i++)
			users.get(i).VESSign();
		//Aggregate
		threads=new ArrayList<>();
		for (var i=0;i<userCount;i++)
		{
			var k=i;
			var thread=new Thread(new Runnable() {
				public void run() {
					users.get(k).Aggregate();	
				}
			});
			threads.add(thread);
			thread.start();
		}
		for (var i=0;i<userCount;i++)
			threads.get(i).join();
		//AggregateVerify
		boolean result=true;
		for (var i=0;i<userCount;i++)
			result=result&&users.get(i).AggregateVerify();
		if (result)
			System.out.println("Success!");
		else 
			throw new RuntimeException("Failed!");
		for (var i=0;i<userCount;i++)
			signProcess.add(StringTools.byteToBiginteger(users.get(i).pPub.toBytes()).toString());
		System.out.println("链下部分完成");
		while (userVerify.size()!=userCount);

		createPDF(contractAdress);
    }
}
