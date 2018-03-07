/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tmser.tr.common.utils.ExcelUtils;
import com.tmser.tr.utils.StringUtils;

/**
 * <pre>
 *  Excel 批量导入导出，基础类
 * </pre>
 *
 * @author tmser
 * @version $Id: BatchImportService.java, v 1.0 2016年8月30日 下午4:42:06 tmser Exp $
 */
public abstract class ExcelBatchService implements BatchService {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	private List<ExcelTitle> requiredTitles;

	private ExcelTitle[] allTitles;
	
	/**
	 * 批量导出到excel 
	 * @param os 导出到的输出流
	 * @param template excel模板文件，可以为空。
	 * @param params，导入需要的附加参数
	 */
	@Override
	public void exportData(OutputStream os,Map<String,Object> params, final StringBuilder resultMsg,File template){
		Workbook workbook = null;
		InputStream is = null;
		boolean usetemp = false;
		try {
			if(template != null && template.exists() 
					&& template.isFile() && template.canRead()
					&& template.canWrite()){
				is = new FileInputStream(template);
				workbook = ExcelUtils.createWorkBook(is);
				usetemp = true;
			}else{
				workbook = new HSSFWorkbook();
			}
			
			String name = sheetname();
			Sheet sheet;
			if(!usetemp){//生成全新excel 导出
				if(StringUtils.isBlank(name)){
					name = "Sheet1";
				}
				sheet = workbook.createSheet(name);
				fillData(sheet, params, resultMsg);
			}else{//使用模板导出
				if(StringUtils.isNotBlank(name)){
					sheet = workbook.getSheet(name);
					fillData(sheet, params, resultMsg);
				}else{
					for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
						sheet = workbook.getSheetAt(i);
						logger.debug("fill sheet data {} start!", i);
						fillData(sheet, params, resultMsg);
						logger.debug("fill sheet data {} end!", i);
					}
				}
			}
			
			workbook.write(os);
		} catch (IOException e) {
			logger.error("read excel file failed", e);
		}finally{
			try {
				if(is != null){
					is.close();
				}
				if(workbook != null){
					workbook.close();
				}
			} catch (IOException e) {
				logger.error("close workbook failed", e);
			}
		}
		
	}
	
	
	protected void fillData(Sheet sheet,Map<String,Object> params, StringBuilder resultMsg){
		beforeSheetFill(sheet,params,resultMsg);
		fillSheetData(sheet, params, resultMsg);
		endSheetFill(sheet, params, resultMsg);
	}
	
	
	/**
	 * 填充sheet 前回调
	 * 子类可覆盖做相应初始化工作，一般做样式处理
	 * @param sheet 当前处理的
	 * @return
	 */
	protected void beforeSheetFill(Sheet sheet,Map<String,Object> params,StringBuilder returnMsg){
		
	}
	
	/**
	 * 填充sheet 完成后回调
	 * 子类可覆盖实现批量插入相关收尾工作
	 * @param sheet 当前处理的
	 * @return
	 */
	protected void endSheetFill(Sheet sheet,Map<String,Object> params,StringBuilder returnMsg){
		
	}
		
	/**
	 * 导出数据放置的sheet 名称
	 * @return sheet 名称，如果使用模板导出，为空则遍历所有sheet
	 */
	protected String sheetname(){
		return null;
	}
	
	
	/**
	 * 填充excel 数据
	 * 实现自动判定当前sheet 是否是自己需要填充的页
	 * @param name
	 * @param sheet
	 * @param params
	 * @param resultMsg
	 */
	protected  void fillSheetData(Sheet sheet,Map<String,Object> params, StringBuilder resultMsg){
		
	}
	
	
	/**
	 * 批量导入
	 * 
	 * @param is
	 *            要导入的execl文件流， 由来源负责关闭文件流
	 * @param params
	 *            ，导入需要的附加参数
	 */
	@Override
	public void importData(InputStream is, Map<String, Object> params, final StringBuilder resultMsg) {
		Workbook workbook = null;
		try {
			workbook = ExcelUtils.createWorkBook(is);
			int sheetIndex = sheetIndex();
			Sheet sheet;
			if (sheetIndex > 0) {
				sheet = workbook.getSheetAt(0);
				logger.debug("parse sheet {} start!", sheetIndex);
				beforeSheetParse(sheet, params, resultMsg);
				parseSheet(sheet, params, resultMsg);
				endSheetParse(sheet,params, resultMsg);
				logger.debug("parse sheet {} end!", sheetIndex);
			} else {
				for (int i = 0; i < workbook.getNumberOfSheets(); i++) {// 获取每个Sheet表
					sheet = workbook.getSheetAt(i);
					boolean sheetHidden = workbook.isSheetHidden(i);
					if(!sheetHidden){
						logger.debug("parse sheet {} start!", i);
						beforeSheetParse(sheet, params,resultMsg);
						parseSheet(sheet, params, resultMsg);
						endSheetParse(sheet, params, resultMsg);
						logger.debug("parse sheet {} end!", i);
					}
				}
			}
		} catch (IOException e) {
			logger.error("read excel file failed", e);
		} finally {
			try {
				if (workbook != null) {
					workbook.close();
				}
			} catch (IOException e) {
				logger.error("close workbook failed", e);
			}
		}

	}

	protected void parseSheet(Sheet sheet, Map<String, Object> params, StringBuilder resultMsg) {
		if (sheet.getLastRowNum() < titleLine()) {
			write(resultMsg, "没有待批量注册的数据！");
			logger.info("当前页没有待批量注册的数据");
			return;
		}

		Map<ExcelTitle, Column> headers = parseExcelHeader(sheet,titleLine());
		if (!checkExcelStruct(headers, resultMsg)) {
			logger.info("当前页表头结构不符合导入要求");
			return;
		}

		Row row = null;
		for (int i = titleLine() + 1; i <= sheet.getLastRowNum(); i++) {
			row = sheet.getRow(i);
			if (row != null) {
				Map<ExcelTitle, String> rowValueMap = new HashMap<>();
				if (!readRows(row, headers, rowValueMap, resultMsg)) {
					continue;
				}

				parseRow(rowValueMap, params, row, resultMsg);
			}
		}
	}

	/**
	 * 解析excel 头部
	 * 
	 * @param sheet
	 * @param headerIndexLine
	 * @return
	 */
	protected Map<ExcelTitle, Column> parseExcelHeader(Sheet sheet,int titleLine) {
		Row row = sheet.getRow(titleLine);
		int columnNum = row.getPhysicalNumberOfCells();
		Map<ExcelTitle, Column> headerIndexMap = new HashMap<ExcelTitle, Column>();
		for (int index = 0; index < columnNum; index++) {
			String name = prepareTitle(ExcelUtils.getCellValue(row.getCell(index)));
			ExcelTitle eh = map(name);
			if (eh != null) {
				Column cn = Column.newInstance(name, index);
				headerIndexMap.put(eh, cn);
			}
		}

		return headerIndexMap;
	}

	/**
	 * 读取行数据，存储到 returnValue 中
	 * 
	 * @param row
	 *            要读取的行
	 * @param headerIndexMap
	 *            excel 列表头结构
	 * @param returnValue
	 *            用于存储行数据
	 * @param returnMsg
	 *            用于存储错误信息
	 * @return
	 */
	private boolean readRows(Row row, Map<ExcelTitle, Column> headerIndexMap, Map<ExcelTitle, String> returnValue, StringBuilder returnMsg) {
		for (ExcelTitle eh : getTitles()) {
			Column cn = headerIndexMap.get(eh);
			if (cn != null) {
				String v = ExcelUtils.getCellValue(row.getCell(cn.getIndex()));
				if (StringUtils.isEmpty(v) && eh.isRequired()) {
					returnMsg.append("数据错误：第").append(row.getRowNum() + 1).append("行中的").append(cn.getName()).append("不能为空,已自动忽略忽略该行;<br>");
					logger.warn("数据错误：第{}行中的{}不能为空,已自动忽略忽略该行。", row.getRowNum() + 1, cn.getName());
					return false;
				}
				if (StringUtils.isNotEmpty(v) && v.length() > eh.size()) { // 校验长度
					returnMsg.append("数据警告：第").append(row.getRowNum() + 1).append("行中的").append(cn.getName()).append("内容不得超过").append(eh.size())
							.append("个字符,已自动截取;<br>");
					v = v.substring(0, eh.size());
					logger.info("数据错误：第{}行中的{}内容不得超过{}个取字符,已自动截。", row.getRowNum() + 1, cn.getName(), eh.size());
				}

				returnValue.put(eh, v);
			}
		}
		return true;
	}

	private ExcelTitle map(String name) {
		ExcelTitle header = null;
		for (ExcelTitle eh : getTitles()) {
			if (eh.getMapNames().contains(name)) {
				header = eh;
				break;
			}
		}
		return header;
	}

	/**
	 * 检查excel 结构是否正确，根据必须的列头判定
	 * 
	 * @param headerIndexMap
	 * @return 结构正确 true, 错误为false
	 */
	private boolean checkExcelStruct(Map<ExcelTitle, Column> headerIndexMap, StringBuilder returnMsg) {
		boolean rs = true;
		for (ExcelTitle eh : requiedHeaders()) {
			if (headerIndexMap.get(eh) == null) {
				returnMsg.append("Excel 缺少必须列： ").append(eh.getMapNames().get(0)).append("<br/>");
				logger.warn("Excel 缺少必须列：{}", eh.getMapNames().get(0));
				rs = false;
			}
		}
		return rs;
	}

	protected List<ExcelTitle> requiedHeaders() {
		if (requiredTitles != null) {
			return requiredTitles;
		}
		requiredTitles = new ArrayList<ExcelTitle>();
		for (ExcelTitle eh : getTitles()) {
			if (eh.isRequired()) {
				requiredTitles.add(eh);
			}
		}
		return requiredTitles;
	}

	private void write(StringBuilder resultMsg, String msg) {
		if (resultMsg != null) {
			resultMsg.append(msg);
		}
	}

	private ExcelTitle[] getTitles() {
		if (allTitles == null) {
			allTitles = titles();
		}

		return allTitles;
	}

	/**
	 * 解析sheet 前回调 子类可覆盖做相应初始化工作
	 * 
	 * @param sheet
	 *            当前处理的
	 * @return
	 */
	protected void beforeSheetParse(Sheet sheet, Map<String, Object> params, StringBuilder returnMsg) {

	}

	/**
	 * 解析sheet 完成后回调 子类可覆盖实现批量插入相关收尾工作
	 * 
	 * @param sheet
	 *            当前处理的
	 * @return
	 */
	protected void endSheetParse(Sheet sheet, Map<String, Object> params, StringBuilder returnMsg) {

	}

	/**
	 * 获取当前要读取的sheet
	 * 
	 * @return 当前sheet,小于0 则遍历读取excel 所有sheet
	 */
	public abstract int sheetIndex();

	/**
	 * 获取excel模板标题所在行 用于解析标题行，确定excel 模板正确性
	 * 
	 * @return
	 */
	public abstract int titleLine();

	/**
	 * 获取头部信息
	 * 
	 * @param name
	 * @return
	 */
	protected abstract ExcelTitle[] titles();

	/**
	 * 处理解析好的单行数据
	 * 
	 * @param rowValueMap
	 * @param row
	 */
	protected abstract void parseRow(Map<ExcelTitle, String> rowValueMap, Map<String, Object> params, Row row, StringBuilder returnMsg);

	/**
	 * 处理标题内容，去掉空白字符，去掉* 号 子类覆盖做特殊处理
	 * 
	 * @param name
	 * @return
	 */
	protected String prepareTitle(String name) {
		return name != null ? name.replaceAll("\\*", "").replaceAll("\\s", "") : "";
	}

	
}
