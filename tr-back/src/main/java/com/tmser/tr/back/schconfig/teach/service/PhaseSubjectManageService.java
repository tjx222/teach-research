package com.tmser.tr.back.schconfig.teach.service;

import java.util.List;

import com.tmser.tr.manage.meta.Meta;


public interface PhaseSubjectManageService {

  /**
   * 获取机构未添加的列表
   * 
   * @param orgId
   *          机构id
   * @param phaseId
   *          学段id
   * @return list of subject
   */
  List<Meta> getUnAddList(String orgId, Integer phaseId);

  /**
   * 获取区域未添加的列表
   * 
   * @param areaId
   *          区域id
   * @param phaseId
   *          学段id
   * @return list of subject
   */
  List<Meta> getUnAddList(Integer areaId, Integer phaseId);

  /**
   * 根据名字查找机构下的元数据
   * 
   * @param orgId
   *          学校id
   * @param name
   *          名称
   * @return subject
   */
  Meta get(String orgId, String name);

  /**
   * 根据名字查找区域下的元数据
   * 
   * @param areaId
   *          区域id
   * @param name
   *          名称
   * @return subject
   */
  Meta get(Integer areaId, String name);

  /**
   * 保存区域自定义的学科元数据和学段学科关系
   * 
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   * @param name
   *          名称
   * @param descs
   *          描述
   */
  void saveCustom(Integer phaseId, Integer areaId, String name, String descs);

  /**
   * 保存机构自定义的学科元数据和学段学科关系
   * 
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   * @param name
   *          名称
   * @param descs
   *          描述
   */
  void saveCustom(Integer phaseId, String orgId, String name, String descs);

  /**
   * 保存机构配置学段学科关系
   * 
   * @param subjectIds
   *          subjects's ids, split by ",";
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   */
  void save(String subjectIds, Integer phaseId, Integer orgId);

  /**
   * 保存区域配置学段学科关系
   * 
   * @param ids
   *          array of ids
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   */
  void save(Integer[] ids, Integer phaseId, Integer areaId);

  /**
   * 删除机构配置的学科
   * 
   * @param id
   *          学科id
   * @param phaseId
   *          学段id
   * @param orgId
   *          学校id
   */
  void delete(Integer id, Integer phaseId, String orgId);

  /**
   * 删除学科配置的学科
   * 
   * @param id
   *          id to be deleted
   * @param phaseId
   *          学段id
   * @param areaId
   *          区域id
   */
  void delete(Integer id, Integer phaseId, Integer areaId);

}
