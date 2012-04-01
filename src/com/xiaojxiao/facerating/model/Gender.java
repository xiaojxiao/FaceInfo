package com.xiaojxiao.facerating.model;

public enum Gender
{
	male(), female(), unknown();

	public static Gender getValue(String value)
	{
		if(value.equalsIgnoreCase("male"))
		{
			return Gender.male;
		}

		if(value.equalsIgnoreCase("female"))
		{
			return Gender.female;
		}

		else
		{
			return Gender.unknown;
		}
	}
}