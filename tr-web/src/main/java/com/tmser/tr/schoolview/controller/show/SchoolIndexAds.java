package com.tmser.tr.schoolview.controller.show;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.tmser.tr.indexPicAds.IndexPicAdsService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.ptgg.bo.FlatformAnnouncement;
/**
 * 首页弹出广告
 * @author ljh
 */
@Controller
@RequestMapping("/jy/schoolview/show/")
public class SchoolIndexAds {
	@Autowired
	private IndexPicAdsService indexPicAdsService;
	@Autowired
	private ResourcesService resourcesService;

	/**
	 * 加载首页广告
	 */
	@RequestMapping("/indexPicAds")
	public String indexPicAds(Model m) {
		FlatformAnnouncement indexPicData = new FlatformAnnouncement();
		indexPicData.addOrder("cdate desc");
		indexPicData.setIsview(1);// 显示
		List<FlatformAnnouncement> list = indexPicAdsService.find(indexPicData, 1);
		if (null != list && list.size() > 0) {
			indexPicData = indexPicAdsService.find(indexPicData, 1).get(0);
			if (!StringUtils.isBlank(indexPicData.getPictureid())) {
				Map<String, String> map = new HashMap<String, String>();
				String bigreourcesid = indexPicData.getPictureid();
				String littlereourcesid = indexPicData.getLittlepictureId();
				String bigpath = resourcesService.findOne(bigreourcesid)
						.getPath();
				String littlepath = resourcesService.findOne(littlereourcesid)
						.getPath();
				map.put("littlePath", littlepath);
				map.put("bigPath", bigpath);
				m.addAttribute("mapUrl", map);
			}
		} else {
			m.addAttribute("mapUrl", null);
		}
		return "/schoolview/show/sygg/sygg";
	}
}
