package com.example.contactServer.Model;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.util.Random;

public class StringTools {
	public static byte[] addSuffix(byte[] _byte,String str)
	{  
	    var result = new byte[_byte.length+str.length()];  
	    System.arraycopy(_byte, 0, result, 0, _byte.length);  
	    System.arraycopy(str.getBytes(), 0, result, _byte.length, str.length());  
	    return result;  
	}
	public static byte[] addSuffix(byte[] _byte,byte[] str)
	{  
	    var result = new byte[_byte.length+str.length];  
	    System.arraycopy(_byte, 0, result, 0, _byte.length);  
	    System.arraycopy(str, 0, result, _byte.length, str.length);  
	    return result;  
	}
	public static<T> byte[] toBytes(T now) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Class<?> _class=now.getClass();
		var method=_class.getMethod("toBytes");
		return (byte[]) method.invoke(now);
	}
	public static BigInteger BigRandom(BigInteger q){
		 String qq = q.toString();
	        String xx = "";
	        String str = "0123456789";
	        Random random = new Random();
	        boolean flag = false;
	        int index;
	        xx += str.charAt(random.nextInt(qq.charAt(0)-'0')+1);
	        if(xx.charAt(0)<qq.charAt(0)){
	            flag = true;
	        }
	        if(qq.length()>0) {
	            for (int i = 1; i < qq.length(); ) {
	                index = random.nextInt(10);
	                if (flag == false) {
	                    if (str.charAt(index) > qq.charAt(i)) {
	                        continue;
	                    } else if (str.charAt(index) < qq.charAt(i)) {
	                        flag = true;
	                        xx += str.charAt(index);
	                    }
	                } else {
	                    xx += str.charAt(index);
	                }
	                i++;
	            }
	        }
	        BigInteger x= new BigInteger(xx.toString());
	        return x ;
	    }
	public static BigInteger byteToBiginteger(byte[] data)
	{
		return new BigInteger(data);
	}
	public static byte[] bigintegerToByte(BigInteger data)
	{
		return data.toByteArray();
	}
}
