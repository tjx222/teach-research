package com.tmser.tr.schoolres.service;

import java.util.List;

import com.tmser.tr.common.page.PageList;
import com.tmser.tr.schoolres.vo.TopResourceVo;


public interface SchoolResCountService {
	public  List<TopResourceVo> getTopN(TopResourceVo t, int N);

	public PageList<TopResourceVo> findByPage(TopResourceVo model);

}
