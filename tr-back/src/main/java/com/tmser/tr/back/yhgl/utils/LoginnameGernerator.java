/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.back.yhgl.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import org.apache.commons.lang3.StringUtils;

import com.tmser.tr.utils.PinYinTool;

/**
 * <pre>
 * 用户名生成器
 * </pre>
 * 
 * @author tmser
 * @version $Id: LoginnameGernerator.java, v 1.0 2015年12月5日 上午10:13:38 tmser Exp
 *          $
 */
public abstract class LoginnameGernerator {

	static final HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
	static {
		// UPPERCASE：大写 (ZHONG)
		// LOWERCASE：小写 (zhong)
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);

		// WITHOUT_TONE：无音标 (zhong)
		// WITH_TONE_NUMBER：1-4数字表示英标 (zhong4)
		// WITH_TONE_MARK：直接用音标符（必须WITH_U_UNICODE否则异常） (zhòng)
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		// WITH_V：用v表示ü (nv)
		// WITH_U_AND_COLON：用"u:"表示ü (nu:)
		// WITH_U_UNICODE：直接用ü (nü)
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
	}

	/**
	 * 教研批量注册用户名生成方法
	 * 
	 * @param prefix
	 *            前缀，目前使用学校简称，没有简称使用学校全称
	 * @param realname
	 *            真实姓名
	 * @return prefix 汉语取拼音首字母，真实姓名小于三个汉字，取拼音全拼，大于等于三个汉字， 首个汉字全拼，其余部分取首字母
	 */
	public static final String gerneratorLoginName(String prefix, String realname) {
		StringBuilder loginName = new StringBuilder();
		if (StringUtils.isNotEmpty(prefix)) {
			loginName.append(PinYinTool.toChineseSpell(prefix));
		}
		if (StringUtils.isNotEmpty(realname)) {
			char firstName = realname.charAt(0);
			loginName.append(parseToPinyin(firstName));

			String lastName = realname.substring(1);
			if (StringUtils.isNotEmpty(lastName)) {
				if (lastName.length() > 1) {
					loginName.append(PinYinTool.toChineseSpell(lastName));
				} else {
					loginName.append(parseToPinyin(lastName.charAt(0)));
				}
			}
		}
		return loginName.toString().toLowerCase();
	}

	static final String parseToPinyin(char c) {
		String pinyin = null;
		try {
			String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c, format);
			if (pinyins != null && pinyins.length > 0) {
				pinyin = pinyins[0];
			} else {
				pinyin = String.valueOf(c);
			}
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			pinyin = PinYinTool.toChineseSpell(String.valueOf(c));
		}

		return pinyin;
	}
}
