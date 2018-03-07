package com.tmser.tr.back.schconfig.teach.service;

import java.util.List;
import java.util.Map;

import com.tmser.tr.back.schconfig.vo.PublisherVo;
import com.tmser.tr.manage.meta.Meta;
import com.tmser.tr.manage.meta.bo.PublishRelationship;

public interface PublisherManageService {

  /**
   * 获取未添加的所有出版社
   * 
   * @param phaseId
   *          学段id
   * @param subjectId
   *          学科id
   * @return list ofpublisher
   */
  List<Meta> getUnAddList(PublishRelationship model);

  /**
   * 学段学科
   * 
   * @return list of map
   */
  List<Map<String, Object>> findXDXKtree();

  /**
   * 获取学校的学段学科树
   * 
   * @param orgId
   *          学校id
   * @return list if map
   */
  List<Map<String, Object>> findXDXKtree(Integer orgId);

  /**
   * 获取区域的学段学科树
   * 
   * @param areaId
   *          区域id
   * @return list of map
   */
  List<Map<String, Object>> findXDXKtree1(Integer areaId);

  void save(Integer[] publisherIds, PublishRelationship model);

  void del(Integer id, PublishRelationship model);

  /**
   * 保存机构自定义出版社
   * 
   * @param vo
   *          com.mainbo.platform.back.teach.vo.PublisherVo
   * @param orgId
   *          学校id
   */
  void saveCustom(PublisherVo vo);

  List<Meta> listAllPubliserMetaByScope(PublishRelationship model);

}
