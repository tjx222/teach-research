package com.tmser.tr.common.vo;

import java.util.List;

import com.tmser.tr.manage.meta.bo.Book;
import com.tmser.tr.manage.meta.bo.MetaRelationship;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

public class Datas {

	private List<MetaRelationship> metas;
	
	private List<PublishRelationship> publishs;
	
	private List<Book> books;

	public List<MetaRelationship> getMetas() {
		return metas;
	}

	public void setMetas(List<MetaRelationship> metas) {
		this.metas = metas;
	}

	/** 
	 * Getter method for property <tt>publishs</tt>. 
	 * @return property value of publishs 
	 */
	public List<PublishRelationship> getPublishs() {
		return publishs;
	}

	/**
	 * Setter method for property <tt>publishs</tt>.
	 * @param publishs value to be assigned to property publishs
	 */
	public void setPublishs(List<PublishRelationship> publishs) {
		this.publishs = publishs;
	}

	/** 
	 * Getter method for property <tt>books</tt>. 
	 * @return property value of books 
	 */
	public List<Book> getBooks() {
		return books;
	}

	/**
	 * Setter method for property <tt>books</tt>.
	 * @param books value to be assigned to property books
	 */
	public void setBooks(List<Book> books) {
		this.books = books;
	}

	
	
}
