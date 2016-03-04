package com.wellgood.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")
public class User 
{
	@DatabaseField(generatedId = true)
	private int _id=-1;
	@DatabaseField
	private int id=-1;
	@DatabaseField(columnName = "name")
	private String name="default";


	public User()
	{
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", name=" + name 
				+ "]";
	}

	


	
}
