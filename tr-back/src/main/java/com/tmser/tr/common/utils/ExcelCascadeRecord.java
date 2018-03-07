/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  excel 关联记录表 
 * </pre>
 *
 * @author tmser
 * @version $Id: ExcelCascadeRecord.java, v 1.0 2016年12月30日 下午1:52:05 tmser Exp $
 */
public final class ExcelCascadeRecord {

	private final String name;
	
	private String expression;
	
	private Map<String,ExcelCascadeRecord> children;
	
	private List<CellData> cellDatas;
	
	private final Object lock = new Object();
	
	public ExcelCascadeRecord(final String name){
		this.name = name;
	}

	/** 
	 * Getter method for property <tt>expression</tt>. 
	 * @return property value of expression 
	 */
	public String getExpression() {
		return expression;
	}

	/**
	 * Setter method for property <tt>expression</tt>.
	 * @param expression value to be assigned to property expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/** 
	 * Getter method for property <tt>name</tt>. 
	 * @return property value of name 
	 */
	public String getName() {
		return name;
	}
	
	/** 
	 * Getter method for property <tt>cellDatas</tt>. 
	 * @return property value of cellDatas 
	 */
	public List<CellData> getCellDatas() {
		return cellDatas;
	}

	/**
	 * Setter method for property <tt>cellDatas</tt>.
	 * @param cellDatas value to be assigned to property cellDatas
	 */
	public void setCellDatas(List<CellData> cellDatas) {
		this.cellDatas = cellDatas;
	}

	/** 
	 * Getter method for property <tt>children</tt>. 
	 * @return property value of children 
	 */
	public Map<String, ExcelCascadeRecord> getChildren() {
		return children;
	}

	/**
	 * Setter method for property <tt>children</tt>.
	 * @param children value to be assigned to property children
	 */
	public void setChildren(Map<String, ExcelCascadeRecord> children) {
		this.children = children;
	}
	
	public void addCellData(CellData cellData){
		synchronized (lock) {
			if(cellDatas == null){
				cellDatas = new ArrayList<CellData>();
			}
		}
		
		cellDatas.add(cellData);
	}
	
	public void putChildRecord(String name,ExcelCascadeRecord record){
		synchronized (lock) {
			if(children == null){
				children = new HashMap<String,ExcelCascadeRecord>();
			}
		}
		
		children.put(name, record);
	}

	public static class CellData{
		private final int row;
		
		private final int col;
		
		private final String value;
		public CellData(int row, int col, String value){
			this.row = row;
			this.col = col;
			this.value = value;
		}
		/** 
		 * Getter method for property <tt>row</tt>. 
		 * @return property value of row 
		 */
		public int getRow() {
			return row;
		}
		/** 
		 * Getter method for property <tt>col</tt>. 
		 * @return property value of col 
		 */
		public int getCol() {
			return col;
		}
		/** 
		 * Getter method for property <tt>value</tt>. 
		 * @return property value of value 
		 */
		public String getValue() {
			return value;
		}
		
	}
}
