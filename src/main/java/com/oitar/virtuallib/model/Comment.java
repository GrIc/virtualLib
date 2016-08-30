package com.oitar.virtuallib.model;

public class Comment {
	private String _submitter;
	private String _content;
	
	public String getSubmitter() {
		return _submitter;
	}

	public String getContent() {
		return _content;
	}

	public Comment(String submitter, String content) {
		this._content = content;
		this._submitter = submitter;
	}
}
