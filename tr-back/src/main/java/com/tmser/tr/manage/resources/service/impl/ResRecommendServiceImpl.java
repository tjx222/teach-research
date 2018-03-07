package com.tmser.tr.manage.resources.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tmser.tr.back.zygl.vo.ResourcesVo;
import com.tmser.tr.common.dao.BaseDAO;
import com.tmser.tr.common.service.AbstractService;
import com.tmser.tr.common.utils.WebThreadLocalUtils;
import com.tmser.tr.manage.meta.bo.BookChapter;
import com.tmser.tr.manage.meta.service.BookChapterService;
import com.tmser.tr.manage.resources.bo.ResRecommend;
import com.tmser.tr.manage.resources.bo.Resources;
import com.tmser.tr.manage.resources.dao.ResRecommendDao;
import com.tmser.tr.manage.resources.service.ResRecommendService;
import com.tmser.tr.manage.resources.service.ResourcesService;
import com.tmser.tr.uc.bo.User;
import com.tmser.tr.uc.utils.SessionKey;
import com.tmser.tr.utils.FileUtils;
import com.tmser.tr.utils.StringUtils;
/**
 * 
 * 推荐资源
 * @author csj
 * @version $Id: ResRecommendServiceImpl.java, v 1.0 2015年2月11日 上午11:13:25 csj Exp $
 */
@Service
@Transactional
public class ResRecommendServiceImpl extends AbstractService<ResRecommend, Integer> implements ResRecommendService {

	private static final Pattern CHAPTER_ID_PATTERN = Pattern.compile("\\+\\(#&(\\w+)\\)");
	
	private final static Pattern FILTER_PATTERN  = Pattern.compile("[\\*<>\\\\\\/\\?\\|:\"]") ;
	
	private static final Map<String,Integer> TYPEMAP = new HashMap<String,Integer>();
	private static final Map<Integer,Integer> RESTYPE_DIC_MAP = new HashMap<Integer,Integer>();

	private static int BATCH_SAVE_COUNT = 0;

	private static final int UNKOWN_TYPE = 9;
	
	static{
		TYPEMAP.put("doc", 0);
		TYPEMAP.put("docx", 0);
		TYPEMAP.put("ppt", 0);
		TYPEMAP.put("pptx", 0);
		TYPEMAP.put("xls", 0);
		TYPEMAP.put("xlsx", 0);
		TYPEMAP.put("pdf", 0);
		TYPEMAP.put("txt", 0);
		
		TYPEMAP.put("jpg", 1);
		TYPEMAP.put("jpeg", 1);
		TYPEMAP.put("gif", 1);
		TYPEMAP.put("png", 1);
		
		TYPEMAP.put("mp3", 2);
		TYPEMAP.put("wav", 2);
		
		TYPEMAP.put("mp4", 3);
		TYPEMAP.put("avi", 3);
		TYPEMAP.put("rm", 3);
		TYPEMAP.put("wma", 3);
		TYPEMAP.put("asf", 3);
		TYPEMAP.put("swf", 3);
		TYPEMAP.put("flv", 3);
		TYPEMAP.put("fla", 3);
		TYPEMAP.put("flash", 3);
		
		TYPEMAP.put("zip", 5);
		TYPEMAP.put("rar", 5);
		
		RESTYPE_DIC_MAP.put(0, 319);
		RESTYPE_DIC_MAP.put(1, 318);
		RESTYPE_DIC_MAP.put(2, 317);
		RESTYPE_DIC_MAP.put(3, 320);
	}
	
	
	@Autowired
	private ResRecommendDao resRecommendDao;
	
	@Autowired
	private ResourcesService resourcesService;

	
	@Override
	public BaseDAO<ResRecommend, Integer> getDAO() {
		return resRecommendDao;
	}
	
	@Autowired
	private BookChapterService bookChapterService;
	
	@Autowired
	private ResourcesService  resService;
	
	private String succ = "成功.txt";
	
	private String fail = "失败.txt";

	
	/**
	 * 保存资源
	 * @param bc 章节
	 * @param file 章节对应的文件夹
	 * @throws IOException 
	 */
	@Override
	public boolean saveComRes(File file,String path) throws IOException {
		//读取成功log中的内容，第二次不再导入成功的数据
		List<String> logList = getLog(path);
		if(file.exists() && file.isDirectory()){
				//递归所有的子文件夹
			for(File childfile : file.listFiles()){
				if(childfile.exists() && !childfile.isHidden()){
					if(childfile.isDirectory()){
						BATCH_SAVE_COUNT = 0;
						saveComRes(childfile,path);
						if(logList == null || logList.size() == 0){
							writeLog("成功导入文件夹--"+childfile.getAbsolutePath()+"--的数据",0,path);
						}
					}else{
					   if(logList == null || !logList.contains(childfile.getAbsolutePath())){
						  File parent =  childfile.getParentFile();
						   boolean flag = false;
						   if(parent != null){
							   parent = parent.getParentFile();
							   if(parent != null){
								   String ppname = parent.getName();
								   BookChapter bc = findBcId(ppname);
								   if(bc != null){
										saveresfile(bc,childfile,path);
										BATCH_SAVE_COUNT ++;
										flag = true;
								    }
							   }
						   }
						   if(!flag){
							   writeLog("文件名为--"+childfile.getAbsolutePath()+"--的数据导入失败：",1,path);
						   }
						  
					   }
					}
				}
			}
		}
		
		return true;
	}

	
	private BookChapter findBcId(String filename){
		Matcher m  = CHAPTER_ID_PATTERN.matcher(filename);
		String bcid  =  null;
		while(m.find()){
			bcid = m.group(1);
			break;
		}
		if(bcid != null){
			return bookChapterService.findOne(bcid);
		}
		
		return null;
	}
	
	/**
	 * 保存资源文件
	 * @param bc	章节
	 * @param file	章节对应的资源文件
	 * @throws IOException 
	 */
	private void saveresfile(BookChapter bc, File childfile,String path) throws IOException {
		try{
			int type = switchType(childfile.getParentFile());
			int filetype = switchFileType(FileUtils.getFileExt(childfile.getName()));
			if(type != -1 && filetype != UNKOWN_TYPE && !childfile.isHidden()){//跳过不在特定文件夹下的文件,及隐藏文件
				Resources res = new Resources();
				FileInputStream stream = null;
				stream = new FileInputStream(childfile);
				String relativeUrl = "/comres/"+bc.getComId(); //相对路径 如：/2015/3/3
				res = resService.saveResources(stream, childfile.getName(),childfile.length(), relativeUrl);
				
				ResRecommend comres = new ResRecommend();
				comres.setBookId(bc.getComId());
				comres.setResId(res.getId());
				comres.setTitle(FileUtils.getFileNameNoExtension(childfile.getName()));
				comres.setExt(FileUtils.getFileExt(childfile.getName()));
				comres.setResType(type);
				comres.setResSecondType(filetype);
				comres.setDicType(RESTYPE_DIC_MAP.get(type));
				comres.setUploadUserId(0);
				comres.setUploadUserName("admin");
				comres.setUploadTime(new Date());
				comres.setModifiedTime(new Date());
				comres.setEnable(1);
				comres.setSort(BATCH_SAVE_COUNT);
				comres.setQualify(0);
				comres.setLessonId(bc.getChapterId());
				comres.setDownNumb(0);
				comres.setUploadOrgId(0);
				this.save(comres);
				writeLog("成功导入文件名为--"+childfile.getAbsolutePath()+"--的数据",0,path);
			}else{
				String errmsg  = "";
				if(type == -1 ){
					errmsg = "文件位置错误，资源需在【教案、课件、习题、素材】文件夹的其中一个下";
				}else if(filetype == UNKOWN_TYPE){
					errmsg = "文件类型错误，可上传类型【*.doc;*.docx;*.xls;*.xlsx;*.ppt;*.pptx;*.pdf;*.txt;*.zip;*.rar;*.jpg;*.jpeg;*.gif;*.png;*.mp3;*.mp4;*.wma;*.rm;*.rmvb;*.flv;*.swf;*.avi】";
				}else{
					errmsg = "隐藏文件";
				}
				writeLog("文件名为--"+childfile.getAbsolutePath()+"--的数据导入失败，错误为："+errmsg,1,path);
			}
			
		}catch(Exception e){
			writeLog("文件名为--"+childfile.getAbsolutePath()+"--的数据导入失败，错误为："+e,1,path);
		}
	}

	/**
	 * 资源文件类型
	 * @param name
	 * @return
	 */
	private int switchType(File file){
		String name = file.getName();
		int type = -1;
		if(name.contains("教案")){
				type=0;
		}else if(name.contains("课件")){
				type=1;
		}else if(name.contains("习题")|| name.contains("试题") || name.contains("文化")){
				type=2;
		}else if(name.contains("素材")){
			type = 3;
		}
		return type;
	}
	
	
	/**
	 * 素材文件类型
	 * @param name
	 * @return
	 */
	private Integer switchFileType(String fileext){
		Integer doctype = TYPEMAP.get(fileext.toLowerCase());
		if(doctype == null){
			doctype = UNKOWN_TYPE;
		}
		return doctype;
	}
	
	//type =  0  成功，1  失败。
	private void  writeLog(String message,int type,String path) throws IOException{
			String  name = "";
			if(type==0){
				name = succ;
			}else if(type==1){
				name = fail;
			}
			FileWriter fileWritter = new FileWriter(path+File.separator+name,true);
	        BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(message+"\r\n");
            bufferWritter.close();
	}
	
	private List<String> getLog(String path) throws FileNotFoundException{
		return readFileByLines(path+File.separator+succ);
	}
	
	
    /**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static List<String> readFileByLines(String fileName) {
    	List<String> list = new ArrayList<String>();
        File file = new File(fileName);
        if(!file.exists()){
        	return Collections.emptyList();
        }
        
        BufferedReader reader = null;
        try {
           //以行为单位读取文件内容，一次读一整行：
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                list.add(getString(tempString));
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    
    return list;
}
    
    private static String  getString ( String input)
    { 
        String regex = "--([^#]*)--";
        Pattern pattern = Pattern.compile (regex);
        Matcher matcher = pattern.matcher (input);
        String str = "";
        while (matcher.find ())
        {
            str = matcher.group (1);
        }
		return str;
    }

	protected boolean createAndDownRes(String bookId,String parentId,File banben){
		List<BookChapter> chapters = bookChapterService.listBookChapterWithChildState(bookId, parentId);
		boolean rs = false;
		try {
			if (chapters != null && chapters.size() > 0) {
				for (BookChapter bc : chapters) {
					Boolean hasChild = Boolean.valueOf(bc.getFlago());
					//##是有子文件夹的，#&是没有子文件夹的
					File bcFile = null;
					if(hasChild){
						String nm = bc.getChapterId();
						Integer index = nm.length() - 4 > 0 ? nm.length() -4 : 0;
						bcFile = new File(banben,
								filterFilename(bc.getChapterName()) + "+(##"
										+ nm.substring(index) + ")");
					}else{
						bcFile = new File(banben,
								filterFilename(bc.getChapterName()) + "+(#&"
										+ bc.getChapterId() + ")");
					}
					
					if (bcFile.mkdirs()) {
						if (hasChild) {
							createAndDownRes(bookId, bc.getChapterId(), bcFile);
						}
					}
				}
				rs = true;
			}
		} catch (Exception e) {
			logger.error("download resource failed！",e);
		}
		return rs;
	}

	protected String filterFilename(String oldName){
		String s = oldName;
		if(!StringUtils.isEmpty(oldName)){
			Matcher m = FILTER_PATTERN.matcher(oldName);
			StringBuffer sb = new StringBuffer();
			while(m.find()) {
					 m.appendReplacement(sb, ""); 
		    }
			m.appendTail(sb);
			s = sb.toString().trim();
		}
		return s;
	}
	
	
	@Override
	public void batchInsert(ResourcesVo resVo,String lessonId) {
		User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);
		List<ResRecommend> list = new ArrayList<ResRecommend>();
		BookChapter bk = bookChapterService.findOne(lessonId);
		for (ResRecommend resRecommend : resVo.getRcdVo()) {
			resourcesService.updateTmptResources(resRecommend.getResId());
			Resources resources = resourcesService.findOne(resRecommend.getResId());
			resRecommend.setUploadTime(new Date());
		 	resRecommend.setExt(resources.getExt());
			resRecommend.setResSecondType(switchFileType(resources.getExt()));
			resRecommend.setBookId(bk.getComId());
			resRecommend.setUploadUserName(user.getName());
			resRecommend.setUploadUserId(user.getId());
			resRecommend.setModifiedTime(new Date());
			resRecommend.setDicType(RESTYPE_DIC_MAP.get(resRecommend.getResType()));
			resRecommend.setEnable(1);
			resRecommend.setLessonId(lessonId);
			list.add(resRecommend);
		}
		
		resRecommendDao.batchInsert(list);
	}


	@Override
	public boolean editResRecommend(ResRecommend resRecommend) {
		
		ResRecommend old = findOne(resRecommend.getId());
		if(old != null){
			ResRecommend newRes = new ResRecommend();
			newRes.setId(old.getId());
			if(!old.getResId().equals(resRecommend.getResId())){
				resourcesService.updateTmptResources(resRecommend.getResId());
				Resources resources = resourcesService.findOne(resRecommend.getResId());
				newRes.setExt(resources.getExt());
				newRes.setResSecondType(switchFileType(resources.getExt()));
				newRes.setResId(resRecommend.getResId());
				
				resourcesService.deleteResources(old.getResId()); 
			}
			
			if(old.getTitle() == null || !old.getTitle().equals(resRecommend.getTitle())){
				newRes.setTitle(resRecommend.getTitle());
			}
			
			if(old.getResType() ==  null || !old.getResType().equals(resRecommend.getResType())){
				newRes.setDicType(RESTYPE_DIC_MAP.get(resRecommend.getResType()));
			}
			
			if(old.getQualify() == null || !old.getQualify().equals(resRecommend.getQualify())){
				newRes.setQualify(resRecommend.getQualify());
			}
			
			if(old.getSort() == null || !old.getQualify().equals(resRecommend.getSort())){
				newRes.setSort(resRecommend.getSort());
			}
			
			User user = (User)WebThreadLocalUtils.getSessionAttrbitue(SessionKey.CURRENT_USER);

			newRes.setUploadUserName(user.getName());
			newRes.setUploadUserId(user.getId());
			newRes.setModifiedTime(new Date());
			
			this.update(newRes);
		}
		
		return true;
	}


	/**
	 * @param ids
	 * @see com.tmser.tr.manage.resources.service.ResRecommendService#batchDelete(java.lang.Integer[])
	 */
	@Override
	public void batchDelete(Integer[] ids) {
		for(Integer id : ids){
			ResRecommend one = this.findOne(id);
			if(one != null){
				//先删除sys_resource表中数据
				resourcesService.deleteResources(one.getResId());
				//删除sys_recommend_res表中数据
				this.delete(id);
			}
		}
		
	}
	
}
