package com.xiaojxiao.facerating.model;

abstract class Pair<A, B>
{
	public A first;
	public B second;

	public Pair()
	{
		this(null, null);
	}

	public Pair(final A first, final B second)
	{
		this.first = first;
		this.second = second;
	}
}