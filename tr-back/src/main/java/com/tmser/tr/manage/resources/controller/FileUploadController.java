/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tmser.tr.common.utils.WebUtils;
import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.utils.FileUtils;

/**
 * <pre>
 *
 * </pre>
 *
 * @author tmser
 * @version $Id: FileUploadController.java, v 1.0 2015年3月4日 下午2:35:33 tmser Exp
 *          $
 */
@Controller
@RequestMapping("/jy/manage/res")
public class FileUploadController extends AbstractController {

	@Autowired
	private ResourcesService resourcesService;

	@Value("#{config.getProperty('container_url_encode','iso-8859-1')}")
	private String urlEncode;

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			@RequestParam(value = "relativePath", required = false) String relativePath,
			@RequestParam(value = "isWebRes", required = false, defaultValue = "false") Boolean isWebRes,
			Writer writer) throws IOException {
		Result rs = new Result();
		rs.setCode(-1);
		if (file != null) {
			if (isWebRes) {
				Resources res = resourcesService.saveWebResources(file,
						relativePath);
				if (res != null) {
					rs.setData(res.getId());
					rs.setCode(0);
					rs.setMsg("上传成功！");
				}
			} else {
				Resources res = resourcesService.saveTmptResources(file,
						relativePath);
				if (res != null) {
					rs.setData(res.getId());
					rs.setCode(0);
					rs.setMsg("上传成功！");
				}
			}

		}

		if (rs.getCode() != 0) {
			rs.setMsg("上传失败！");
		}
		StringBuilder rb = new StringBuilder();
		rb.append("{code:").append(rs.getCode())
		.append(",data:\"").append(rs.getData())
		.append("\",msg:\"").append(rs.getMsg()).append("\"}");

		writer.write(rb.toString());
	}

	@RequestMapping("/download/{resid}")
	@ResponseBody
	public ResponseEntity<byte[]> download(
			@PathVariable(value = "resid") String resid,
			@RequestParam(value = "filename", required = false) String filename,
			@RequestParam(value = "isweb", required = false,defaultValue="false") Boolean isWeb,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		return downloadFile(resid,filename,isWeb,userAgent);
	}

	@RequestMapping("/download/path")
	@ResponseBody
	public ResponseEntity<byte[]> downloadWithPath(
			@RequestParam(value = "path") String path,
			@RequestParam(value = "filename", required = false) String filename,
			@RequestParam(value = "isweb", required = false,defaultValue="true") Boolean isWeb,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		String resid = FileUtils.getFileName(new File(path).getName());
		return downloadFile(resid,filename,isWeb,userAgent);
	}

	protected ResponseEntity<byte[]> downloadFile(String resid,String filename,Boolean isWeb,String userAgent) throws IOException{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fn = filename;
		Resources r = resourcesService.findOne(resid);
		if (StringUtils.isEmpty(filename)) {
			fn = r.getName() + "." + r.getExt();
		} else {
			fn = filename + "." + r.getExt();
		}
		String encode = urlEncode.toLowerCase();

		if (null != userAgent) {
			userAgent = userAgent.toLowerCase();
			if(userAgent.indexOf("firefox") == -1  &&  userAgent.indexOf("chrome") == -1){
				if(!"utf-8".equals(encode))
					fn = new String(fn.getBytes(encode),"utf-8");
				fn = java.net.URLEncoder.encode(fn, "utf-8").replace("+", "%20");
			}else{
				if(!"iso-8859-1".equals(encode))
					fn = new String(fn.getBytes(encode),"iso-8859-1");
			}

		}
		List<Charset> charList = new ArrayList<Charset>();
		headers.setAcceptCharset(charList);

		headers.setContentDispositionFormData("attachment", fn);
		File f = null;
		if(isWeb){
			f = new File(WebUtils.getRootPath(),r.getPath());
		}else{
			f = new File(resourcesService
					.download(resid));
		}

		return new ResponseEntity<byte[]>(
				FileUtils.readFileToByteArray(f), headers, HttpStatus.OK);
	}
}
