/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebUtils;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.dao.ResourcesDao;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.storage.service.StorageService;
import com.tmser.tr.utils.FileUtils;
import com.tmser.tr.utils.Identities;
import com.tmser.tr.utils.StringUtils;
import com.tmser.tr.utils.image.ImageUtil;

/**
 * <pre>
 * 资源存储表 服务实现类
 * </pre>
 *
 * @author tmser
 * @version $Id: Resources.java, v 1.0 2015-02-07 tmser Exp $
 */
@Service
@Transactional
public class ResourcesServiceImpl extends AbstractService<Resources, String>
		implements ResourcesService {
	
	@Autowired
	private ResourcesDao resourcesDao;

	@Autowired
	private StorageService storageservice;
	
	@Value("#{config.getProperty('uploadBasePath','static/upload')}")
	private String uploadBasePath;

	/**
	 * @return
	 * @see com.tmser.tr.common.service.BaseService#getDAO()
	 */
	@Override
	public BaseDAO<Resources, String> getDAO() {
		return resourcesDao;
	}
	
	protected Resources saveResources(InputStream stream, String fileName,
			Long fileSize, String relativePath,int fileState) {

		 String id = Identities.uuid2();
		 String ext = FileUtils.getFileExt(fileName);
		 String aimUrl = new StringBuilder(StringUtils.nullToEmpty(relativePath)).append(File.separator)
		 .append(id).append(".")
		 .append(ext).toString();
		 
		 String filePath = storageservice.upload(stream, fileName, aimUrl);
		 
		 Resources resources = new Resources();
		 resources.setId(id);
		 resources.setPath(filePath);
		 resources.setName(FileUtils.getFileName(fileName));//文件名，不含扩展名
		 resources.setExt(ext); //扩展名不含.
		 resources.setSize(fileSize);
		 resources.setDeviceName(storageservice.parseDevice(filePath));
		 resources.setState(fileState);
		 resources.setCrtDttm(new Date());
		 resources = resourcesDao.insert(resources);
		 
		return resources;
	}
	/**
	 * @param stream
	 *            上传文件流
	 * @param fileName
	 *            上传文件名称
	 * @param fileSize
	 *            上传文件大小
	 * @param relativePath
	 *            上传文件保存相对路径，包含文件名称
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#saveResources(java.io.FileInputStream,
	 *      java.lang.String, java.lang.Integer, java.lang.String)
	 */
	@Override
	public Resources saveResources(InputStream stream, String fileName,
			Long fileSize, String relativePath) {
		return saveResources(stream,  fileName,
			 fileSize,  relativePath,Resources.S_NORMAL);
	}

	/**
	 * @param myfile
	 * @param relativePath
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#saveResources(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public Resources saveTmptResources(MultipartFile myfile, String relativePath) {
		try {
			return saveResources(myfile.getInputStream(),myfile.getOriginalFilename(),myfile.getSize(),relativePath,Resources.S_TEMP);
		} catch (IOException e) {
			logger.error("cann't get fileinputStream", e);
		}
		return null;
	}

	/**
	 * @param myfile
	 * @param relativePath
	 * @param oldResId
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#updateResources(org.springframework.web.multipart.MultipartFile, java.lang.String, java.lang.String)
	 */
	@Override
	public Resources updateTmptResources(String oldResId) {
		Resources resources = resourcesDao.get(oldResId);
		if(resources != null && Resources.S_TEMP == resources.getState()){
				Resources newResources = new Resources();
				newResources.setId(resources.getId());
				newResources.setState(Resources.S_NORMAL);
				resources.setState(Resources.S_NORMAL);
				resourcesDao.update(newResources);
		}
		return resources;
	}

	/**
	 * @param stream
	 * @param fileName
	 * @param fileSize
	 * @param relativePath
	 * @param oldResId
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#updateResources(java.io.InputStream, java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public Resources updateResources(InputStream stream, String fileName,
			Long fileSize, String relativePath, String oldResId) {
		Resources resources = null;
		 if(!org.apache.commons.lang3.StringUtils.isEmpty(oldResId)){
			 resources = resourcesDao.get(oldResId);
			 if(resources != null){
				 String oldPath = resources.getPath();
				 String id = Identities.uuid2();
				 String ext = FileUtils.getFileExt(fileName);
				 String aimUrl = new StringBuilder(relativePath).append(File.separator)
				 .append(id).append(".")
				 .append(ext).toString();
				 
				 String filePath = storageservice.upload(stream, fileName, aimUrl);
				 
				 resources.setPath(filePath);
				 resources.setName(FileUtils.getFileName(fileName));//文件名，不含扩展名
				 resources.setExt(ext); //扩展名不含.
				 resources.setSize(fileSize);
				 resources.setDeviceName(storageservice.parseDevice(filePath));
				 resourcesDao.update(resources);
				 
				 storageservice.delete(oldPath);
			 }
		 }
		 
		return resources;
	}
	
	/**
	 * @param stream
	 * @param fileName
	 * @param fileSize
	 * @param relativePath
	 * @param oldResId
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#updateResources(java.io.InputStream, java.lang.String, java.lang.Long, java.lang.String, java.lang.String)
	 */
	@Override
	public Resources replaceResources(InputStream stream, String fileName,
			Long fileSize, String relativePath, String oldResId) {
		Resources resources = null;
		 if(!org.apache.commons.lang3.StringUtils.isEmpty(oldResId)){
			 resources = resourcesDao.get(oldResId);
			 if(resources != null){
				 String oldPath = resources.getPath();
				 storageservice.delete(oldPath);
				 String ext = FileUtils.getFileExt(fileName);
				 String filePath = storageservice.upload(stream, fileName, oldPath);
				 resources.setPath(filePath);
				 resources.setName(FileUtils.getFileName(fileName));//文件名，不含扩展名
				 resources.setExt(ext); //扩展名不含.
				 resources.setSize(fileSize);
				 resources.setDeviceName(storageservice.parseDevice(filePath));
				 resourcesDao.update(resources);
			 }
		 }
		 
		return resources;
	}

	/**
	 * @param resId
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#viewResources(java.lang.String)
	 */
	@Override
	public String viewResources(String resId) {
		 if(!org.apache.commons.lang3.StringUtils.isEmpty(resId)){
			 Resources resources = resourcesDao.get(resId);
			 if(resources != null){
				 return storageservice.download(resources.getPath());
			 }
		 }
		return null;
	}
	
	/**
	 * @param myfile
	 * @param relativePath
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#saveWebResources(org.springframework.web.multipart.MultipartFile, java.lang.String)
	 */
	@Override
	public Resources saveWebResources(MultipartFile myfile, String relativePath) {
		 String id = Identities.uuid2();
		 String ext = FileUtils.getFileExt(myfile.getOriginalFilename());
		 String aimUrl = new StringBuilder(uploadBasePath)
		 .append(File.separator).append(relativePath).append(File.separator)
		 .append(id).append(".")
		 .append(ext).toString();
		 if(saveToWeb(myfile,aimUrl)){
			 Resources resources = new Resources();
			 resources.setId(id);
			 resources.setPath(aimUrl.replace("\\", "/"));
			 	//部分浏览器web路径只支持 '/'
			 resources.setName(FileUtils.getFileName(myfile.getOriginalFilename()));//文件名，不含扩展名
			 resources.setExt(ext); //扩展名不含.
			 resources.setSize(myfile.getSize());
			 resources.setDeviceName(WEB_DEVICE_NAME);
			 resources.setDeviceId(WEB_DEVICE_ID);
			 resources.setState(Resources.S_TEMP);
			 resources.setCrtDttm(new Date());
			 resources = resourcesDao.insert(resources);
			 return resources;
		 }
		return null;
	}
	
	protected boolean saveToWeb(MultipartFile myfile,String aimUrl) {
        try {
        	if(StringUtils.isBlank(aimUrl)) {
				throw new IllegalStateException("aimUrl cann't be null!");
			}
			File aimFile = new File(WebUtils.getRootPath(),aimUrl);
			File parentFolder = aimFile.getParentFile();
			if(parentFolder != null  && !parentFolder.exists())
				parentFolder.mkdirs();
			org.apache.commons.io.FileUtils.copyInputStreamToFile(myfile.getInputStream(),aimFile);
	        logger.info(">> [save success ] " + aimUrl);
		} catch (Exception e) {
			logger.error("upload error ", e);
			return false;
		}
        return true;
	}
	
	@Override
	public String resizeImage(Resources resources,int newWidth,int newHeight){
		String originPath  = resources.getPath();
		File origin = new File(WebUtils.getRootPath(),originPath);
		String smallPath = originPath.substring(0,originPath.lastIndexOf("/"));
		String filename = originPath.substring(originPath.lastIndexOf("/"));
		String aimUrl = new StringBuilder(smallPath)
		 .append("/").append("small").append(filename).toString();
		
		File small = new File(WebUtils.getRootPath(),aimUrl);
		if(!small.getParentFile().exists()){
			FileUtils.createDirectory(small.getParent());
		}
		ImageUtil.resize(origin, small, newWidth, newHeight, FileUtils.getFileExt(filename));
		return aimUrl;
	}
	/**
	 * @param path
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#deleteWebResources(java.lang.String)
	 */
	@Override
	public boolean deleteWebResources(String path) {
		if(StringUtils.isNotEmpty(path)){
			File aimFile = new File(WebUtils.getRootPath(),path);
			String resid = FileUtils.getFileName(aimFile.getName());
			deleteResources(resid);
		}
		return true;
	}
	
	/** 
	 * 删除实际文件
	 * @param path
	 */
	protected void deleteWebFile(String path){
		File aimFile = new File(WebUtils.getRootPath(),path);
		if(aimFile.exists() && aimFile.isFile()){
			 aimFile.delete();
		}
	}
	
	/**
	 * @param resid
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#deleteResources(java.lang.String)
	 */
	@Override
	public boolean deleteResources(String resid) {
		Resources resources = null;
		 if(StringUtils.isNotEmpty(resid)){
			 resources = resourcesDao.get(resid);
			 if(resources != null){
				 if(Resources.S_TEMP != resources.getState()){
					 Resources rs =  new Resources();
					 rs.setState(Resources.S_TEMP);
					 rs.setId(resid);
					 resourcesDao.update(rs);
				 }else{
					 resourcesDao.delete(resid);
					 if(WEB_DEVICE_ID.equals(resources.getDeviceId())){
						 deleteWebFile(resources.getPath());
					 }else{
						 storageservice.delete(resources.getPath());
					 }
				 }
				 return true;
			 }
		 }
		 return false;
	}
	/**
	 * @param resid
	 * @return
	 * @see com.tmser.tr.manage.resources.service.ResourcesService#download(java.lang.String)
	 */
	@Override
	public String download(String resid) {
		Resources r = resourcesDao.get(resid);
		if(r !=  null){
			return storageservice.download(r.getPath());
		}
		return null;
	}
	

	@Override
	public String copyRes(String resid){
		Resources r = resourcesDao.get(resid);
		String oldfile = null;
		if(r !=  null){
			oldfile = storageservice.download(r.getPath());
		}
		if(oldfile != null){
			String relativePath = new File(r.getPath()).getParent();
			try {
				Resources newres = saveResources(
						new FileInputStream(new File(oldfile)),
						r.getName() + "." + r.getExt(), r.getSize(),
						relativePath);
				updateTmptResources(newres.getId());
				return newres.getId();
			} catch (Exception e) {
				logger.error("copy file failed ,resid=[{}]",resid);
				throw new RuntimeException(e);
			}
		}
		return null;
	}

}
