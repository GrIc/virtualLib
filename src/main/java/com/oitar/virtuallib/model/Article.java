package com.oitar.virtuallib.model;

import java.net.URL;

public class Article extends Reference {
	private URL _url;

	public URL getUrl() {
		return _url;
	}

	public void setUrl(URL _url) {
		this._url = _url;
	}
}
