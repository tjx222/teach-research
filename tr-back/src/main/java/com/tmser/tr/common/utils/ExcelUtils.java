/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationConstraint.ValidationType;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.utils.Identities;
import com.tmser.tr.utils.StringUtils;


/**
 * <pre>
 * Excle操作的工具
 * </pre>
 *
 * @author zpp
 * @version $Id: ExcleUtils.java, v 1.0 2015年10月16日 下午5:15:40 zpp Exp $
 */
public abstract class ExcelUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);
	
	
	public static final String DEFAULT_NAME_PRE = "tmpt_row_";//数据字典sheet
	
	public static final String DEFAULT_HIDDEN_SHEET = "hidden";//隐藏sheet
	
	/**
	 * 无效的名称
	 */
	private static final Pattern NAME_VALID = Pattern.compile("^[cr]$|\\s+|^[\\d]|[^\u4e00-\u9fa5\\w_\\.\\\\]");

	public static Workbook createWorkBook(String filePath) {
		try {
			String ext = filePath.substring(filePath.lastIndexOf(".")+1);
			if("xls".equals(ext)){				//使用xls方式读取
				return new HSSFWorkbook(new FileInputStream(filePath));
			}else if("xlsx".equals(ext)){		//使用xlsx方式读取
				return new XSSFWorkbook(new FileInputStream(filePath));
			}
		} catch (Exception e) {
			logger.error("open {} failed!", filePath);
			logger.error("", e);
		}
		return null;
	}
	
	/**
	 * 
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Workbook createWorkBook(InputStream is) throws IOException {
		Workbook workbook = null;
		try {
			workbook = new HSSFWorkbook(is);
		} catch (OfficeXmlFileException e) {
			workbook = new XSSFWorkbook(is);
		}
		
		return workbook;
	}
	
	
	/**
	 * 获取poi excel 中cell 内容
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}
		String cellValue = "";
		DecimalFormat df = new DecimalFormat("#");
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			cellValue = cell.getRichStringCellValue().getString().trim();
			break;
		case Cell.CELL_TYPE_NUMERIC:
			cellValue = df.format(cell.getNumericCellValue()).toString().trim();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case Cell.CELL_TYPE_FORMULA:
			cellValue = cell.getCellFormula();
			break;
		default:
			cellValue = "";
		}
		return cellValue;
	}
	/**
	* 用于将excel表格中列索引转成列号字母，从A对应1开始
	* 
	* @param index
	*            列索引
	* @return 列号
	*/
	public static String indexToColumn(int index) {
		if(index == 0){
			return "A";
		}
        if (index <= 0) {                
        	try {                         
        		throw new Exception("Invalid parameter");                
        	} catch (Exception e) {                        
        		e.printStackTrace();                 
        	}        
        }         
        index--;         
        String column = "";   
        do {                 
        	if (column.length() > 0) {
                        index--;
            }
            column = ((char) (index % 26 + 'A')) + column;
            index = (index - index % 26) / 26;
        } while (index > 0);
        return column;
	}
	/**
	 *  1）不能以数字和问号“？”开头。                        
		2）不能以R、r、C、c单个字母作为名称，R和C在R1C1样式中表示行、列                        
		3）不能包含空格，可以用下划线或点号代替。                        
		4）不能使用除：下划线“_”、点号“.”和反斜杠“\”外的其他符号。问号也允许但不能作为名称开头。        
		5）字符个数不能超过255。                        
		6）不区分大小写                        
	 * 创建名称
	 * @param wb
	 * @param name
	 * @param expression
	 * @return
	 */
	public static Name createName(Workbook wb, String name, String expression){
		if(wb == null || !validName(name)){
			return null;
		}
		Name refer = wb.createName();
		refer.setNameName(name);
		refer.setRefersToFormula(expression);
		return refer;
	}
	/**
	 * 校验数据字符串的长度是否大于255
	 * @param textList
	 * @return
	 */
	public static boolean validateLength(String[] textList){
		StringBuilder sb = new StringBuilder(textList.length * 16);
		for (int i = 0; i < textList.length; i++) {
			if (i > 0) {
				sb.append('\0'); // list delimiter is the nul char
			}
			sb.append(textList[i]);
		}
		return sb.length() >= 255;
	}
	
	/**
	 * 设置数据有效性（通过列表设置）
	 * 数据长度超限制，将自动转换为name 方式
	 * @param workbook
	 * @param dataList
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 */
	public static DataValidation createDataValidation(Sheet sheet, String[] dataList, int firstRow, int endRow, int firstCol, int endCol){
		if(sheet == null){
			return null;
		}
		DataValidationConstraint constraint = null;
		Workbook workbook = sheet.getWorkbook();
		boolean isOldVersion = true;
		if (sheet instanceof XSSFSheet) {
			isOldVersion = false;
		}
		if(validateLength(dataList)){
			//设置下拉列表的内容
			Sheet hidden = workbook.createSheet(DEFAULT_NAME_PRE + Identities.randomLong());
			ExcelCascadeRecord record = createCascadeRecord(hidden,Arrays.asList(dataList));
			constraint =isOldVersion ? DVConstraint.createFormulaListConstraint(record.getName()) :
				new XSSFDataValidationConstraint(ValidationType.LIST, record.getName());
			workbook.setSheetHidden(workbook.getSheetIndex(hidden), true);
		}else{
			constraint = isOldVersion ? DVConstraint.createExplicitListConstraint(dataList) : 
				new XSSFDataValidationConstraint(dataList);; 
		}
		
		return createDataValidation(sheet,constraint, firstRow,  endRow,  firstCol, endCol);
	}
	/**
	 * 设置数据有效性（通过列表设置）
	 * 数据长度超限制，将自动转换为name 方式
	 * @param workbook
	 * @param dataList
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 */
	public static DataValidation createDataValidation(Sheet sheet, String name, int firstRow, int endRow, int firstCol, int endCol){
		if(sheet == null){
			return null;
		}
		DataValidationConstraint constraint = null;
		boolean isOldVersion = true;
		if (sheet instanceof XSSFSheet) {
			isOldVersion = false;
		}
			//设置下拉列表的内容
		constraint =isOldVersion ? DVConstraint.createFormulaListConstraint(name) :
			new XSSFDataValidationConstraint(ValidationType.LIST, name);
		return createDataValidation(sheet,constraint, firstRow,  endRow,  firstCol, endCol);
	}
	
	/**
	 * 设置数据有效性（通过@see DVConstraint 设置）
	 * @param name
	 * @param firstRow
	 * @param endRow
	 * @param firstCol
	 * @param endCol
	 * @return
	 */
	public static DataValidation createDataValidation(Sheet sheet,DataValidationConstraint constraint, int firstRow, int endRow, int firstCol, int endCol){
		boolean isOldVersion = true;
		if (sheet instanceof XSSFSheet) {
			isOldVersion = false;
		}
		logger.info("起始行:" + firstRow + " - 起始列:" + firstCol + ", 终止行:" + endRow + " - 终止列:" + endCol);
		if(isOldVersion){
			//设置下拉列表的内容
			// 四个参数分别是：起始行、终止行、起始列、终止列
			CellRangeAddressList regions = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);
			// 数据有效性对象
			return new HSSFDataValidation(regions, constraint);
		}else{
			XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);  
	         CellRangeAddressList addressList = new CellRangeAddressList((short) firstRow, (short) endRow, (short) firstCol, (short) endCol);  
	         XSSFDataValidation validation = (XSSFDataValidation) dvHelper.createValidation(constraint, addressList);  
	         validation.setSuppressDropDownArrow(true);
	         return validation;
		}
	}
	
	private static boolean validName(String name){
		boolean rs = false;
		if(StringUtils.isNotBlank(name)){
			if(!NAME_VALID.matcher(name.trim()).find()){
				rs = true;
			}
		}
		return rs;
	}
	
	/**
	 * 创建级联关系
	 * @param workbook
	 * @param name
	 * @param dataMap
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Workbook workbook, Map<String, Object> dataMap){
		return createCascadeRecord(workbook,null,dataMap);
	}
	
	/**
	 * 创建级联关系
	 * @param workbook
	 * @param name
	 * @param dataMap
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Workbook workbook, String name, Map<String, Object> dataMap){
		if(workbook == null || dataMap == null){
			return null;
		}
		
		Sheet hidden = workbook.createSheet(DEFAULT_HIDDEN_SHEET + Identities.randomLong());
		ExcelCascadeRecord record = createCascadeRecord(hidden, name, dataMap);
		workbook.setSheetHidden(workbook.getSheetIndex(hidden), true);
		return record;
	}
	
	/**
	 * 使用已有sheet 创建级联关系
	 * @param sheet
	 * @param name
	 * @param isAppendName 是否对超过两级级联名称进行拼接,如果使用拼接，引用时，也可拼接两列，如INDIRECT($D$18&$E$18)
	 * @param dataMap
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Sheet sheet, String name,boolean isAppendName, Map<String, Object> dataMap){
		if(sheet == null || dataMap == null){
			return null;
		}
		
		ExcelCascadeRecord record = createCascadeRecord(sheet,name,dataMap.keySet());
		if(record != null){
			for(String key : dataMap.keySet()){
				record.putChildRecord(name, createCascadeRecord(sheet,key,dataMap.get(key),isAppendName));
			}
		}
		return record;
	}
	
	/**
	 * 使用已有sheet 创建级联关系
	 * @param sheet
	 * @param name
	 * @param dataMap
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Sheet sheet, String name, Map<String, Object> dataMap){
		return createCascadeRecord(sheet,name,dataMap,false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static ExcelCascadeRecord createCascadeRecord(Sheet hidden, String name, Object data,boolean isAppendName){
		ExcelCascadeRecord record = null;
		if(data instanceof Collection){
			record = createCascadeRecord(hidden,name,(Collection)data);
		}else if(data instanceof Map){
			Map<String,Object> mapdata = (Map<String,Object>) data;
			record = createCascadeRecord(hidden,name,mapdata.keySet());
			if(record == null){
				return null;
			}
			String childName;
			for(String key : mapdata.keySet()){
				if(isAppendName){
					childName = name + key;
				}else{
					childName = key;
				}
				ExcelCascadeRecord tmpRecord = createCascadeRecord(hidden,childName,mapdata.get(key),isAppendName);
				if(tmpRecord != null){
					record.putChildRecord(name, tmpRecord);
				}
			}
		}
		
		return record;
	}
	
	/**
	 * 
	 * @param workbook
	 * @param dicSheet
	 * @param datas
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Sheet dicSheet, Collection<String> datas) {
			return createCascadeRecord(dicSheet,0,null,datas);
	}
	/**
	 * 
	 * @param workbook
	 * @param dicSheet
	 * @param name
	 * @param datas
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Sheet dicSheet, String name, Collection<String> datas) {
			return createCascadeRecord(dicSheet,0,name,datas);
	}
	
	/**
	 * 创建级联excel 记录
	 * @param workbook
	 * @param dicSheet
	 * @param startColumn
	 * @param name
	 * @param datas
	 * @return
	 */
	public static ExcelCascadeRecord createCascadeRecord(Sheet dicSheet, int startColumn, final String name, Collection<String> datas) {
		if(dicSheet == null || datas == null || datas.size() ==0){
			return null;
		}
		
		if(startColumn < 0){
			startColumn = 0;
		}
		
		String cname = name;
		if(StringUtils.isBlank(name)){
			cname = DEFAULT_NAME_PRE + Identities.randomLong();
		}
		
		int curCellColumn = startColumn;
		int endCellColumn = -1;
		Row valueRow = getAvailableRow(dicSheet);
		Set<String> dataSet = new HashSet<String>();
		dataSet.addAll(datas);
		int rowIndex = valueRow.getRowNum()+1;
		int endRowIndex = rowIndex;
		ExcelCascadeRecord record = new ExcelCascadeRecord(cname);
		
		Iterator<String> iterator = datas.iterator();
		while(iterator.hasNext()){
			String value = iterator.next();
			if(StringUtils.isNotBlank(value) && dataSet.contains(value)){
				valueRow.createCell(curCellColumn);
				valueRow.getCell(curCellColumn).setCellValue(value);
				record.addCellData(new ExcelCascadeRecord.CellData(rowIndex, curCellColumn, value));
				
				dataSet.remove(value);
				curCellColumn++;
				if(curCellColumn==255){
					endCellColumn = curCellColumn;
					curCellColumn = startColumn;
					if(iterator.hasNext()){
						valueRow = getAvailableRow(dicSheet);
						endRowIndex = valueRow.getRowNum()+1;
					}
				}
			}
		}
		
		String endColumnName = ExcelUtils.indexToColumn(endCellColumn==-1?curCellColumn:endCellColumn);//找到对应列对应的列名
		String startColumnName = ExcelUtils.indexToColumn(startColumn);//找到对应列对应的列名
		String expression = dicSheet.getSheetName()+"!$"+startColumnName+"$"+rowIndex+":$"+endColumnName+"$"+endRowIndex;
		ExcelUtils.createName(dicSheet.getWorkbook(), cname, expression);
		record.setExpression(expression);
		
		return record;
	}
	
	
	private static Row getAvailableRow(Sheet sheet){
		Row row = null;
		int begin = sheet.getFirstRowNum();
		int end = sheet.getLastRowNum();
		for (int i = begin; i <= end; i++) {
			if (null == sheet.getRow(i)) {
				row = sheet.createRow(i);
			}
		}
		
		if(row == null){
			row = sheet.createRow(end + 1);
		}
		
		return row;	
	}
}
