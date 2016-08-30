package com.oitar.virtuallib.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
public abstract class Reference implements Serializable, Cloneable {
	
	private Long id;
	private String title;
	private String author;
	private List<Comment> _comments;
	private Date publicationDate;
	private String publisher;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public List<Comment> getComments() {
		return _comments;
	}
	public void setComments(List<Comment> _comments) {
		this._comments = _comments;
	}
	public Date getPublicationDate() {
		return publicationDate;
	}
	public void setPublicationDate(Date _publicationDate) {
		this.publicationDate = _publicationDate;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String _publisher) {
		this.publisher = _publisher;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (this.getTitle() == null) {
			return false;
		}

		if (obj instanceof Reference && obj.getClass().equals(getClass())) {
			return this.getId().equals(((Reference) obj).getId());
//			return this.getTitle().equalsIgnoreCase(((Reference) obj).getTitle()) &&
//					this.getPublicationDate().compareTo(((Reference) obj).getPublicationDate()) == 0 &&
//					Arrays.equals(this.getAuthors().toArray(), ((Reference) obj).getAuthors().toArray());
		}

		return false;
	}
	
	@Override
	public Reference clone() throws CloneNotSupportedException {
		return (Reference) super.clone();
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 43 * hash + (id == null ? 0 : id.hashCode());
		return hash;
	}

	@Override
	public String toString() {
		return "Title: " + getTitle() + ", Author: " + getAuthor();
	}
	public String toSearchString() {
		return getTitle() + getAuthor() + getPublisher();
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}