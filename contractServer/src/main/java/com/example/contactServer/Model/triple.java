package com.example.contactServer.Model;
import java.lang.reflect.InvocationTargetException;

public class triple<T,U,V>
{
	public T first;
	public U second;
	public V third;
	public triple(T first,U second,V third)
	{
		this.first=first;
		this.second=second;
		this.third=third;
	}

	public byte[] toBytes()
	{
		try {
			return StringTools.addSuffix((StringTools.toBytes(first)),StringTools.addSuffix(StringTools.toBytes(second), StringTools.toBytes(third)));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			return null;
		}
	}
}
