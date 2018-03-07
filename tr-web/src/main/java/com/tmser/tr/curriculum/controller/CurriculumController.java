package com.tmser.tr.curriculum.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tmser.tr.common.annotation.UseToken;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.curriculum.bo.Curriculum;
import com.tmser.tr.curriculum.service.CurriculumService;
import com.tmser.tr.curriculum.vo.Curriculums;
import com.tmser.tr.uc.bo.UserSpace;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

/**
* 课程表  控制器接口
* <pre>
*
* </pre>
*
* @author Generate Tools
* @version $Id: Thesis.java, v 1.0 2015-02-02 Generate Tools Exp $
*/

@Controller
@RequestMapping("/jy/")
public class CurriculumController extends AbstractController {

	@Autowired
	private CurriculumService cs;

	@RequestMapping("curriculum")
	@UseToken
	public String curriculum(Model m) {
		
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		Curriculum curriculum = new Curriculum();
		curriculum.setUserid(us.getUserId());
		List<Curriculum> curriculumList = cs.findAll(curriculum);
		Map<String, Map<String,Curriculum >> map = new HashMap<>();
		Map<String, Curriculum> m1;
		for (Curriculum c : curriculumList) {
			m1 = map.get(c.getWeek().toString());
			if (m1 == null) {
				m1 = new HashMap<String, Curriculum>();
			}
			m1.put(c.getLesson().toString(), c);
			map.put(c.getWeek().toString(), m1);

		}
		m.addAttribute("map", map);
		return "curriculum/curriculum";
	}

	@RequestMapping("saveCurriculum")
	@UseToken
	public String saveCurriculum(Curriculums curriculumList,Model m) {
		UserSpace us = (UserSpace) WebThreadLocalUtils
				.getSessionAttrbitue(SessionKey.CURRENT_SPACE);
		for(Curriculum c:curriculumList.getCurList()){
			Curriculum oldCurriculum = null;
			if(c.getId() != null){
				oldCurriculum = cs.findOne(c.getId());
			}
			
			if(!StringUtils.isEmpty(c.getContent()) && oldCurriculum == null){
				c.setUserid(us.getUserId());
				c.setContent(c.getContent().trim());
				cs.save(c);
			}
			
			if(oldCurriculum !=  null && (oldCurriculum.getContent() == null 
					|| !oldCurriculum.getContent().equals(c.getContent()))){
				Curriculum model = new Curriculum();
				model.setId(oldCurriculum.getId());
				model.setContent(StringUtils.nullToEmpty(c.getContent()));
				cs.update(model);
			}
			m.addAttribute("msg", "保存成功");
		}
		m.addAttribute("close", true);
		return "curriculum/curriculum";
	}

}
