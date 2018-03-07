/**
 * Tmser.com Inc.
 * Copyright (c) 2015-2017 All Rights Reserved.
 */
package com.tmser.tr.manage.resources.controller;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
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

import com.tmser.tr.common.vo.Result;
import com.tmser.tr.common.web.controller.AbstractController;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.utils.FileUtils;
import com.tmser.tr.utils.Identities;
import com.tmser.tr.utils.ZipUtils;

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
		String filename = "";
		rs.setCode(-1);
		if (file != null) {
			filename =new String(
					file.getOriginalFilename().getBytes("UTF-8"),
					"ISO8859-1");
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
		rb.append("{\"code\":")
				.append(rs.getCode())
				.append(",\"data\":\"")
				.append(rs.getData())
				.append("\",\"msg\":\"")
				.append(new String(rs.getMsg().getBytes("UTF-8"), "ISO8859-1"))
				.append("\",\"filename\":\"")
				.append(filename).append("\"}");
		writer.write(rb.toString());
	}

	@RequestMapping("/download/path/{path}")
	@ResponseBody
	public ResponseEntity<byte[]> downloadWithPath(
			@PathVariable(value = "path") String path,
			@RequestParam(value = "filename", required = false) String filename,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		String resid = "";
		if (path.lastIndexOf(".") > 0) {
			resid = FileUtils.getFileName(new File(path).getName());
		} else {
			resid = path;
		}

		return downloadFile(resid, filename, userAgent);
	}

	@RequestMapping("/download/{resid}")
	@ResponseBody
	public ResponseEntity<byte[]> download(
			@PathVariable(value = "resid") String resid,
			@RequestParam(value = "filename", required = false) String filename,
			@RequestHeader("User-Agent") String userAgent) throws IOException {
		return downloadFile(resid, filename, userAgent);
	}

	@RequestMapping("/batchDownload")
	@ResponseBody
	public ResponseEntity<byte[]> batchDownload(
			@RequestParam("resids") List<String> resids,
			@RequestParam(value = "filename", required = false) String filename,
			@RequestHeader("User-Agent") String userAgent) throws IOException {

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		List<String> srcPath = new ArrayList<String>(resids.size());
		for (String resid : resids) {
			srcPath.add(resourcesService.download(resid));
		}
		String fn = filename;
		if (StringUtils.isEmpty(filename)) {
			fn = "批量下载.zip";
		} else {
			fn = filename + ".zip";
		}

		String encode = urlEncode.toLowerCase();

		if (null != userAgent) {
			userAgent = userAgent.toLowerCase();
			if (userAgent.indexOf("firefox") == -1
					&& userAgent.indexOf("chrome") == -1) {
				if (!"utf-8".equals(encode))
					fn = new String(fn.getBytes(encode), "utf-8");
				fn = java.net.URLEncoder.encode(fn, "utf-8")
						.replace("+", "%20");
			} else {
				if (!"iso-8859-1".equals(encode))
					fn = new String(fn.getBytes(encode), "iso-8859-1");
			}

		}

		headers.setContentDispositionFormData("attachment", fn);
		File fTemp = null;
		try {
			fTemp = File.createTempFile(Identities.uuid2(), ".zip");
			ZipUtils.compress(srcPath, fTemp.getAbsolutePath());
			return new ResponseEntity<byte[]>(
					FileUtils.readFileToByteArray(fTemp), headers,
					HttpStatus.OK);
		} catch (Exception e) {
			logger.error("download failed!", e);
			return null;
		} finally {
			if (fTemp != null) {
				fTemp.delete();
				fTemp.deleteOnExit();
			}
		}

	}

	protected ResponseEntity<byte[]> downloadFile(String resid,
			String filename, String userAgent) throws IOException {
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
			if (userAgent.indexOf("firefox") == -1
					&& userAgent.indexOf("chrome") == -1) {
				if (!"utf-8".equals(encode))
					fn = new String(fn.getBytes(encode), "utf-8");
				fn = java.net.URLEncoder.encode(fn, "utf-8")
						.replace("+", "%20");
			} else {
				if (!"iso-8859-1".equals(encode))
					fn = new String(fn.getBytes(encode), "iso-8859-1");
			}

		}
		headers.setContentDispositionFormData("attachment", fn);
		File f = new File(resourcesService.download(resid));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(f),
				headers, HttpStatus.OK);
	}
}
