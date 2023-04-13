package com.example.contactServer.Model;

import javax.persistence.*;

@Entity
@Table(name="person")
public class Person
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	@Column(name="name")
	private String name;
	@Column(name="age")
	private Integer age;
	@Column(name="parent")
	private String parent;
	
	public Integer getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public Integer getAge()
	{
		return age;
	}
	public String getParent()
	{
		return parent;
	}
	
	public void setId(Integer id)
	{
		this.id=id;
	}
	public void setName(String name)
	{
		this.name=name;
	}
	public void setAge(Integer age)
	{
		this.age=age;
	}
	public void setParent(String parent)
	{
		this.parent=parent;
	}
}