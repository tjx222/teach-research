package com.tmser.tr.common.orm.parse;

import java.util.Map;

import net.sf.jsqlparser.parser.NameReplacement;
import com.tmser.tr.common.orm.Column;
import com.tmser.tr.common.orm.MapperContainer;
import com.tmser.tr.common.orm.Table;
import com.tmser.tr.common.orm.TableMapHolder;
import com.tmser.tr.utils.SpringContextHolder;

/**
 * orm 字段及表名替换器
 * 依赖与  MapperContainer
 * @author jxtan
 * @date 2015年1月8日
 */
public class OrmNameReplacement implements NameReplacement{

	private MapperContainer mapperContainer;
	
	 protected MapperContainer getContainer(){
	    	return mapperContainer != null ? mapperContainer : 
	    		(mapperContainer = SpringContextHolder.getBean(MapperContainer.class));
	    }
	 
	@Override
	public String replaceTableName(final String tablename) {
		String tname = tablename.replace("`", "");
		Table t = getContainer().getTable(tname);
		return t != null ? t.getTableName():tablename;
	}

	@Override
	public String replaceColumnName(final String tableName, final String columnName) {
		Map<String,String> map = TableMapHolder.getTableMap();
		if(map != null){
			String boName = map.get(tableName);
			if(boName != null){
				boName = boName.replace("`", "");
			}
			Table t = getContainer().getTable(boName);
			String cn = columnName.replaceAll("`", "");
			if(t != null){
				Column c = t.getColumnByAttrName(cn);
	        	return c != null ? c.getColumn():columnName;
			}
		}
		return columnName;
	}

	@Override
	public String replaceAlias(String alias) {
		return alias == null ? TablesNameMapFinder.DEFAULT_ALIAS:alias;
	}

}
