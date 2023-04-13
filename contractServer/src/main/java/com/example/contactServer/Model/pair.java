package com.example.contactServer.Model;
import java.lang.reflect.InvocationTargetException;

public class pair<T,U>{
	public T first;
	public U second;
	public pair(T first,U second) {
		this.first=first;
		this.second=second;
	}
	public byte[] toBytes()
	{
		try {
			return StringTools.addSuffix(StringTools.toBytes(first),StringTools.toBytes(second));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
	}
}