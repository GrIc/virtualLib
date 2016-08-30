package com.oitar.virtuallib.model;

public class Book extends Reference {
	private String _tableContents;

	public String getTableContents() {
		return _tableContents;
	}

	public void setTableContents(String _tableContents) {
		this._tableContents = _tableContents;
	}
	
	@Override
	public Book clone() throws CloneNotSupportedException {
		return (Book) super.clone();
	}
}
