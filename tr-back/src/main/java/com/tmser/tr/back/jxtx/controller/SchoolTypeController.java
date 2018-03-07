package com.tmser.tr.back.jxtx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.tmser.tr.back.jxtx.exception.SchoolTypeNameAlreadyExistException;
import com.tmser.tr.back.jxtx.service.JXTXSchoolTypeService;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.meta.MetaUtils;
import com.tmser.tr.manage.meta.bo.MetaRelationship;

/**
 * 
 * <pre>
 * 
 * </pre>
 * 
 * @author 川子
 * @version $Id: SchTypeManagerController.java, v 1.0 2015年8月25日 上午10:26:16 川子
 *          Exp $
 */
@Controller
@RequestMapping("/jy/back/jxtx")
public class SchoolTypeController extends AbstractController {

	@Autowired
	private JXTXSchoolTypeService schoolTypeService;

	/**
	 * 学校类型列表
	 * 
	 * @param m
	 * @return
	 */
	@RequestMapping("show_schoolType")
	public String schSystemList(Model m) {
		List<MetaRelationship> schTypeList = MetaUtils.getOrgTypeMetaProvider().listAll();
		m.addAttribute("schTypeList", schTypeList);
		return viewName("xxlxgl/show_schoolType");
	}

	/**
	 * 添加和修改页面
	 * 
	 * @return
	 */
	@RequestMapping("goAddOrUpdate")
	public String goAddOrUpdate(Integer id, Model m) {
		Map<String, Object> map = this.schoolTypeService.goSaveOrUpdate(id);
		if (id != null) {
			m.addAttribute("mrp", map.get("mrp"));
		}
		m.addAttribute("xdList", map.get("xdList"));
		m.addAttribute("xdnjMap", JSON.toJSON(map.get("xdnjMap")));
		return viewName("xxlxgl/saveOrUpdateSchType");
	}

	/**
	 * 添加及修改学校类型
	 * 
	 * @param relship
	 * @return
	 */
	@RequestMapping("addOrUpdateSchType")
	@ResponseBody
	public JuiResult addOrUpdateSchType(MetaRelationship relship) {
		JuiResult rs = new JuiResult();
		try {
			this.schoolTypeService.addOrUpdateSchType(relship);
			rs.setRel("schType");
			rs.setMessage("保存成功！");
			rs.setNavTabId("schType");
		}catch (SchoolTypeNameAlreadyExistException e) {
			rs.setMessage("学校类型名称重复");
			logger.error("学校类型保存失败", e);
		} catch (Exception e) {
			rs.setMessage("保存失败！");
			logger.error("学校类型保存失败", e);
		}

		return rs;
	}

	@RequestMapping("delSchType")
	@ResponseBody
	public JuiResult delSchType(Integer id, Model m) {
		JuiResult rs = new JuiResult();
		try {
			this.schoolTypeService.delSchType(id);
			rs.setNavTabId("schType");
			rs.setCallbackType("");
			rs.setMessage("删除成功！");
		} catch (Exception e) {
			rs.setMessage("删除失败！");
			logger.error("学制删除失败！", e);
		}
		return rs;
	}
	
}
