package com.tmser.tr.back.feedback.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.feedback.service.FeedbackService;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.feedback.bo.Recieve;
import com.tmser.tr.feedback.dao.RecieveDao;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
@Service
@Transactional
public class FeedBackServiceImpl extends AbstractService<Recieve, Integer> implements FeedbackService{
	@Autowired
	private RecieveDao recievedao;
	@Autowired
	private ResourcesService resourcesService;
	@Override
	public BaseDAO<Recieve, Integer> getDAO() {
		return recievedao;
	}

	@Override
	public Recieve getRecieveDetail(Recieve recieve) {
		List<Resources> list = new ArrayList<Resources>();
		Recieve  newrecieve = recievedao.getOne(recieve);
		String recieveAttachment1 = newrecieve.getAttachment1();
		String[] recieveAttachment1Array =  recieveAttachment1.split(",");
		if(org.apache.commons.lang3.StringUtils.isNotBlank(recieveAttachment1)){
			for(String forAttachment:recieveAttachment1Array){
				list.add(resourcesService.findOne(forAttachment));
			}
		}
		newrecieve.setList(list);
		return newrecieve;
	}
}
