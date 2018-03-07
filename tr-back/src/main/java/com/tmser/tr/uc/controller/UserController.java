package com.tmser.tr.uc.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.common.vo.JuiResult;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.service.UserService;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.StringUtils;

@Controller
@RequestMapping("/jy/uc")
public class UserController extends AbstractController {

	@Autowired
	private UserService userService;

	// 保存用户信息
	@RequestMapping("/saveuserinfo")
	@ResponseBody
	public JuiResult saveUserInfo(User user, Model m) {
		User oldUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		JuiResult rs = new JuiResult();
		oldUser.setSchoolAge(user.getSchoolAge());
		oldUser.setProfession(user.getProfession());
		oldUser.setHonorary(user.getHonorary());
		oldUser.setSex(user.getSex());
		oldUser.setCellphone(user.getCellphone());
		oldUser.setName(user.getName());
		oldUser.setMail(user.getMail());
		oldUser.setBirthday(user.getBirthday());
		oldUser.setExplains(user.getExplains());
		oldUser.setAddress(user.getAddress());
		oldUser.setPostcode(user.getPostcode());
		oldUser.setLastupDttm(new Date());
		oldUser.setLastupId(oldUser.getId());
		
		userService.update(oldUser);
		m.addAttribute("msg", "保存成功");
		WebThreadLocalUtils.setSessionAttrbitue(SessionKey.CURRENT_USER, oldUser);
		return rs;
	}

	// 验证用户电话
	@RequestMapping("/verifyUseCell")
	@ResponseBody
	public Object verifyUseCell(@RequestParam("cellphone") String cellphoneTo) {
		Boolean rs = true;
		User oldUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (StringUtils.isNotEmpty(cellphoneTo) && !cellphoneTo.equals(oldUser.getCellphone())) {
			if (!StringUtils.isEmpty(cellphoneTo)) {
				User user = new User();
				user.setCellphone(cellphoneTo);
				List<User> uList = userService.find(user, 1);
				if (uList.size() > 0) {
					rs = false;
				}
			}
		}
		return rs;
	}

	// 验证用户邮箱
	@RequestMapping("/verifyUserMail")
	@ResponseBody
	public Object verifyUserMail(@RequestParam("mail") String mailTo) {
		Object result = true;
		User oldUser = (User) WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		if (StringUtils.isNotEmpty(mailTo) && !mailTo.equals(oldUser.getMail())) {
			if (!StringUtils.isEmpty(mailTo)) {
				User user = new User();
				user.setMail(mailTo);
				List<User> uList = userService.find(user,1);
				if (uList.size() > 0) {
					result = false;
				}
			}
		}
		return result;
	}

	// 上传头像
	@RequestMapping("/modifyPhoto")
	@ResponseBody
	public JuiResult upload(String photoPath) {
		JuiResult rs = new JuiResult();
		rs.setMessage("修改成功！");
		userService.modifyPhoto(photoPath);
		return rs;

	}

	/**
	 * 跳转到批量注册页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/users/index", method = RequestMethod.GET)
	public ModelAndView toBatchRegist() {
		ModelAndView mv = new ModelAndView("user/user_batchAdd");
		return mv;
	}

}
