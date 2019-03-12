package com.developer.UInvFISI.rest;

public class ResponseBaseOperacion {

	private String status;
	
	private Object data;

	public ResponseBaseOperacion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ResponseBaseOperacion(String status, Object data) {
		super();
		this.status = status;
		this.data = data;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
