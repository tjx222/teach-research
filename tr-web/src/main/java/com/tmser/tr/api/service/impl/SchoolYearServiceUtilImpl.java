/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.tmser.tr.api.service.SchoolYearUtilService;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: SchoolYearServiceImpl.java, v 1.0 2015年2月3日 下午5:32:38 tmser Exp
 *          $
 */
@Service
public class SchoolYearServiceUtilImpl implements SchoolYearUtilService {

	@Value("#{config.getProperty('schoolyear_split','07-15')}")
	private String schoolYearSplit;

	@Value("#{config.getProperty('term_split','02-01')}")
	private String termSplit;

	public String getSchoolYearSplit() {
		return schoolYearSplit;
	}

	public void setSchoolYearSplit(String schoolYearSplit) {
		this.schoolYearSplit = schoolYearSplit;
	}

	public String getTermSplit() {
		return termSplit;
	}

	public void setTermSplit(String termSplit) {
		this.termSplit = termSplit;
	}

	/**
	 * @return
	 * @see com.tmser.tr.uc.service.SchoolYearService#getCurrentSchoolYear()
	 */
	@Override
	public Integer getCurrentSchoolYear() {
		int m = 6; // 默认月
		int d = 15;// 默认天
		try {
			String[] md = getSchoolYearSplit().trim().split("-");
			m = Integer.valueOf(md[0]) - 1;
			d = Integer.valueOf(md[1]);
		} catch (Exception e) {
			e.printStackTrace();
			// do nothing
		}
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		if (c.get(Calendar.MONTH) < m || (m == c.get(Calendar.MONTH) && c.get(Calendar.DAY_OF_MONTH) < d)) {
			year--;
		}
		return year;
	}

	/**
	 * @return
	 * @see com.tmser.tr.uc.service.SchoolYearService#getCurrentTerm()
	 */
	@Override
	public Integer getCurrentTerm() {
		int m = 1; // 默认月
		int d = 1;// 默认天
		try {
			String[] md = getTermSplit().trim().split("-");
			m = Integer.valueOf(md[0]) - 1;
			d = Integer.valueOf(md[1]);
		} catch (Exception e) {
			// do nothing
		}

		int sm = 6; // 默认月
		int sd = 15;// 默认天
		try {
			String[] md = getSchoolYearSplit().trim().split("-");
			sm = Integer.valueOf(md[0]) - 1;
			sd = Integer.valueOf(md[1]);
		} catch (Exception e) {
			// do nothing
		}

		int term = 0;
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		if ((month < sm && month > m) || (month == m && day > d) || (month == sm && day < sd)) {
			term = 1;
		}
		return term;
	}

	@Override
	public Date getCurrentTermStartTime() {
		Calendar c = Calendar.getInstance();
		if (this.getCurrentTerm() == 0) {
			int m = 6; // 默认月
			int d = 15;// 默认天
			try {
				String[] md = getSchoolYearSplit().trim().split("-");
				m = Integer.valueOf(md[0]) - 1;
				d = Integer.valueOf(md[1]);
			} catch (Exception e) {
				// do nothing
			}
			c.set(Calendar.MONTH, m);
			c.set(Calendar.DAY_OF_MONTH, d);
			return c.getTime();
		} else {
			int m = 1; // 默认月
			int d = 1;// 默认天
			try {
				String[] md = getTermSplit().trim().split("-");
				m = Integer.valueOf(md[0]) - 1;
				d = Integer.valueOf(md[1]);
			} catch (Exception e) {
				// do nothing
			}
			c.set(Calendar.MONTH, m);
			c.set(Calendar.DAY_OF_MONTH, d);
			return c.getTime();
		}
	}

	@Override
	public Date getNextTremStartTime() {
		Integer currentSchoolYear = getCurrentSchoolYear();
		Date date1 = null;
		if (currentSchoolYear != null) {
			String monthStart = getTermSplit();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date1 = sdf.parse((currentSchoolYear + 1) + "-" + monthStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date1;
	}

	public Date getUpTremStartTime() {
		Integer currentSchoolYear = getCurrentSchoolYear();
		Date date1 = null;
		if (currentSchoolYear != null) {
			String monthStart = getSchoolYearSplit();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date1 = sdf.parse(currentSchoolYear + "-" + monthStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date1;
	}

	public Date getNextYearUpTremStartTime() {
		Integer currentSchoolYear = getCurrentSchoolYear();
		Date date1 = null;
		if (currentSchoolYear != null) {
			String monthStart = getSchoolYearSplit();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				date1 = sdf.parse((currentSchoolYear + 1) + "-" + monthStart);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return date1;
	}
}
