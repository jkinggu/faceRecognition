package com.dx.query;

import java.sql.ResultSet;

public interface IResultHandler<T> {
	public T handler(ResultSet rs) throws Exception;
}
