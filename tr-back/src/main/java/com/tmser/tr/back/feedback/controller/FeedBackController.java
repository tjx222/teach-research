package com.tmser.tr.back.feedback.controller;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tmser.tr.back.feedback.service.FeedbackReplyService;
import com.tmser.tr.back.feedback.service.FeedbackService;
import com.tmser.tr.back.logger.LoggerModule;
import com.tmser.tr.common.orm.SqlMapping;
import com.tmser.tr.common.page.PageList;
import com.tmser.tr.common.utils.LoggerUtils;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.bo.Reply;
import com.tmser.tr.feedback.vo.FeedBackVo;
import com.tmser.tr.manage.org.bo.Organization;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.utils.CurrentUserContext;
import com.tmser.tr.uc.utils.SessionKey;
/**
 * <pre>
 * 用户反馈
 *@author lijianghu
 * </pre>
 *
 * @author tmser
 * @version $Id: FeedBackController.java, v 1.0 2015年9月22日14:34:33 tmser Exp $
 */
@Controller
@RequestMapping("/jy/back/fkgl")
public class FeedBackController extends AbstractController {
	@Autowired
	private FeedbackService feedBackService;
	@Autowired
	private FeedbackReplyService feedbackReplyService;
	@Autowired
	private ResourcesService resourcesService;
	private final static Logger logger = LoggerFactory.getLogger(FeedBackController.class);
	@RequestMapping("/feedbackList")
	public String index(Recieve recieve, Model m){
		if(StringUtils.isNotEmpty(recieve.order())){
			recieve.addOrder("senderTime desc");
		}
		String uname = recieve.getUserNameSender();
		if(uname != null){
			recieve.setUserNameSender(SqlMapping.LIKE_PRFIX +uname+SqlMapping.LIKE_PRFIX );
		}

		Organization org = (Organization)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_ORG);
		if(null!=org){
			recieve.setOrgId(org.getId());
			m.addAttribute("orgId", recieve.getOrgId());
			
		}else{
			//判断是否已经选择orgid
			if(recieve==null||recieve.getOrgId()==null){
				//模块列表url
				m.addAttribute("orgurl", "/jy/back/fkgl/feedbackList?orgId=");
				//模块加载divId
				m.addAttribute("divId", "feedbackId");
				return "/back/xxsy/selectOrg";
			}else{
				m.addAttribute("orgId", recieve.getOrgId());
			}
		}
		
		PageList<Recieve> feedbacklist = this.feedBackService.findByPage(recieve);
		m.addAttribute("feedbacklist", feedbacklist);
		recieve.setUserNameSender(uname);
		m.addAttribute("recieve",recieve);
		return  viewName("/feedbackList");
	}
	/**
	 * 跳转到添加反馈意见列表
	 * @return
	 */
	@RequestMapping("/editfeedback")
	public String editfeedback(Reply re,Model m){
		m.addAttribute("pid",re.getPid());
		return viewName("editfeedback");
	}
	/**
	 * 保存回复信息
	 */
	@RequestMapping("/savefeedback")
	@ResponseBody
	public JuiResult savefeedback(Reply reply){
		JuiResult rs = new JuiResult();
		try {
			feedbackReplyService.saveFeedbackReply(reply);//这个要在service层处理，而且要维护反馈表的ishavareply状态,反馈表的id
			LoggerUtils.insertLogger(LoggerModule.YJFK, "反馈管理——反馈回复，回复人："+CurrentUserContext.getCurrentUser().getName());
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("保存反馈信息异常",e);
		}
		return rs;
	}
	/**
	 * 批量删除反馈信息
	 */
	@RequestMapping("/delfeedback")
	public JuiResult delfeedback(String ids){
		JuiResult rs = new JuiResult();
		try {
			if (ids != null) {
				for (String id : ids.split(",")) {
					feedBackService.delete(Integer.parseInt(id));
					Recieve model =new Recieve();
					model.setId(Integer.parseInt(id));
					for(String resId : model.getAttachment1().split(",")){
						this.deleteImg(resId, false);
					}
					LoggerUtils.deleteLogger(LoggerModule.YJFK, "反馈管理——删除反馈，操作人："+CurrentUserContext.getCurrentUser().getId());
					rs.setRel("feedbackId");
				}
				rs.setRel("schoolshow");
			} else {
				rs.setStatusCode(JuiResult.FAILED);
			}
		}catch(Exception e){
			rs.setStatusCode(JuiResult.FAILED);
			logger.error("删除反馈列表信息异常", e);
		}
		return rs;
	}
	
	/**
	 * 查看反馈和回复详情
	 */
	@RequestMapping("/fkgldetail")
	public String feedbackDetail(Recieve recieve,Model m){
		//反馈详情页面数据
		Recieve  re = new Recieve();
		re  = feedBackService.getRecieveDetail(recieve);
		//回复详情页面
		List<FeedBackVo> voList = new ArrayList<FeedBackVo>();
		voList =  feedbackReplyService.getReplyDetail(re);
		m.addAttribute("recieve",re);
		m.addAttribute("voList", voList);
		return viewName("feedbackDetail");
	}
	/**
	 * 删除文件
	 * 
	 * @param imgId
	 *            资源id
	 * @param isweb
	 *            是否是web下资源
	 * @return
	 */
	@RequestMapping("/deleteImg")
	public void deleteImg(String imgId, Boolean isweb) {
		if (isweb == true) {
			if (imgId != null) {
				Resources resources = resourcesService.findOne(imgId);
				if (resources != null) {
					resourcesService.deleteWebResources(resources.getPath());
				}
			}
		} else {
			resourcesService.deleteResources(imgId);
		}
	}

}
