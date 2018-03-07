package com.tmser.tr.back.jxtx.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.tmser.tr.back.jxtx.service.LessonPlanTemplateService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.page.Page;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate;
import com.tmser.tr.lessonplantemplate.dao.LessonPlanTemplateDao;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.org.service.AreaService;
import com.tmser.tr.manage.org.service.OrganizationService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;
/**
 * 教案模板 服务实现类
 * @author wangdawei
 * @version 1.0
 * 2015-01-28
 */
@Service
@Transactional
public class LessonPlanTemplateServiceImpl extends AbstractService<LessonPlanTemplate, Integer> implements LessonPlanTemplateService {
	@Autowired
	private LessonPlanTemplateDao lessonPlanTemplateDao;
	@Autowired
	public OrganizationService orgService;
	@Autowired
	public AreaService areaService;
	@Autowired
	private ResourcesService resourcesService;
	
	/**
	 * 获取模板集合，包含系统自带的
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getTemplateListByOrg(java.lang.Integer)
	 */
	@Override
	public List<LessonPlanTemplate> getTemplateListByOrg(Integer orgId) {
		List<LessonPlanTemplate> lptList = lessonPlanTemplateDao.getTmplateListByOrg(orgId);
		return lptList;
	}

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<LessonPlanTemplate, Integer> getDAO() {
		return lessonPlanTemplateDao;
	}

	/**
	 * 获取机构下的默认模板
	 * @param orgId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getDefaultTemplateByOrgId(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getDefaultTemplateByOrgId(Integer orgId) {
		LessonPlanTemplate lessonPlanTemplate = lessonPlanTemplateDao.getDefaultTemplateByOrgId(orgId);
		return lessonPlanTemplate;
	}

	/**
	 * 根据id获取模板
	 * @param tpId
	 * @return
	 * @see com.tmser.tr.lessonplantemplate.service.LessonPlanTemplateService#getTemplateById(java.lang.Integer)
	 */
	@Override
	public LessonPlanTemplate getTemplateById(Integer tpId) {
		LessonPlanTemplate lessonPlanTemplate = lessonPlanTemplateDao.getTemplateById(tpId);
		return lessonPlanTemplate;
	}

	/**
	 * 分页获取系统模板
	 * @param page
	 * @return
	 * @see com.tmser.tr.back.jxtx.service.LessonPlanTemplateService#getSysTemplateList(com.tmser.tr.common.page.Page)
	 */
	@Override
	public PageList<LessonPlanTemplate> getSysTemplateList(Page page) {
		LessonPlanTemplate lpt = new LessonPlanTemplate();
		lpt.setTpType(0);
		lpt.addOrder(" sort asc");
		lpt.addPage(page);
		return lessonPlanTemplateDao.listPage(lpt);
	}

	/**
	 * 新增教案模板
	 * @param template
	 * @see com.tmser.tr.back.jxtx.service.LessonPlanTemplateService#saveLessonPlanTemplate(com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate)
	 */
	@Override
	public LessonPlanTemplate saveLessonPlanTemplate(LessonPlanTemplate template) {
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER); //用户
		template.setTpIsdefault(0);
		template.setCrtId(user.getId());
		template.setCrtDttm(new Date());
		template.setLastupId(user.getId());
		template.setLastupDttm(new Date());
		template.setEnable(1);
		template.setSort(template.getSort()==null?0:template.getSort());
		if(template.getTpType().intValue()==1){ //如果是新增学校模板
			Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
			String areasName = areaService.getAreaNamesByAreaIds(org.getAreaIds());
			template.setOrgId(org.getId());
			template.setOrgName(areasName+org.getName());
		}
		resourcesService.updateTmptResources(template.getResId());//更新资源状态，使之有效
		return lessonPlanTemplateDao.insert(template);
	}

	/**
	 * 更新教案模板
	 * @param template
	 * @see com.tmser.tr.back.jxtx.service.LessonPlanTemplateService#updateLessonPlanTemplate(com.tmser.tr.lessonplantemplate.bo.LessonPlanTemplate)
	 */
	@Override
	public void updateLessonPlanTemplate(LessonPlanTemplate template) {
		LessonPlanTemplate temp = lessonPlanTemplateDao.get(template.getTpId());
		if(!StringUtils.isBlank(template.getResId())){
			resourcesService.updateTmptResources(template.getResId());//更新资源状态，使之有效
			resourcesService.deleteResources(temp.getResId()); //删除原来的资源
		}else{
			template.setResId(null);
		}
		if(!StringUtils.isBlank(template.getIco())){
			resourcesService.updateTmptResources(template.getIco());//更新资源状态，使之有效
			resourcesService.deleteResources(temp.getIco()); //删除原来的资源
		}else{
			template.setIco(null);
		}
		lessonPlanTemplateDao.update(template);
	}


}
