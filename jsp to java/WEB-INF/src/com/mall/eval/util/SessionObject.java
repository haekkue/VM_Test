package com.hmall.eval.util;

import java.io.Serializable;

import com.hmall.eval.dto.CustUserDto;

public class SessionObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1223141191466970585L;
	private CustUserDto custUserDto;
	
	public CustUserDto getCustUserDto() {
		return custUserDto;
	}
	public void setCustUserDto(CustUserDto custUserDto) {
		this.custUserDto = custUserDto;
	}
}
