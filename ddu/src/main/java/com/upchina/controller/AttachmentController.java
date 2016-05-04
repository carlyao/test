package com.upchina.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.upchina.service.AttachmentService;
import com.upchina.util.ImagePathUtil;
import com.upchina.util.ImageUtils;
import com.upchina.util.PropertyUtils;
import com.upchina.vo.rest.output.FileInfoOutVo;

/**
 * Created by zhangjm on 2015/8/15.
 */

@Controller
@RequestMapping("/attachment")
public class AttachmentController {

    @Autowired
    private AttachmentService attachmentService;
    
    @Autowired
    private PropertyUtils propertyUtils;

    /** 上传目录名*/
   // private static final String uploadFolderName = "uploadFiles";

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
	public @ResponseBody List<FileInfoOutVo> fileUpload(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	List<FileInfoOutVo> fileInfoOutVos = new ArrayList<FileInfoOutVo>();
    	MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    	List<MultipartFile> multipartFiless = multipartRequest.getFiles("files");
    	for (MultipartFile multipartFile : multipartFiless) {
    		Random rd=new Random();
    		int send = rd.nextInt(1000);
    		String wholeName = "";
    		String fileExtension = "";
    		String fileName = "";
    		FileInfoOutVo fi = new FileInfoOutVo();
    		String curProjectPath = ImagePathUtil.getUploadAttachmentPath();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		Date date = new Date();
    		String dateFolder = sdf.format(date);
    		String saveDirectoryPath = curProjectPath + "/" + dateFolder;
    		File saveDirectory = new File(saveDirectoryPath);
    		// 如果不存在则创建文件夹
    		if (!saveDirectory.exists()) {
    			saveDirectory.mkdirs();
    		}
    		try {
    			// 判断文件是否存在
    			if (!multipartFile.isEmpty()) {
    				wholeName = multipartFile.getOriginalFilename();// 真实名字
    				fileExtension = FilenameUtils.getExtension(wholeName);
    				fileName = new Date().getTime() + "_" + send + "." + fileExtension;
    				// String fileExtension = FilenameUtils.getExtension(fileName);
    				multipartFile.transferTo(new File(saveDirectory, fileName));
    				//创建缩略图
    				ImageUtils.createThumbnail(saveDirectory,fileName,fileExtension);
    				
    			}
    			// 返回给客户端
    			String imagehost = ImagePathUtil.getUploadAttachmentUrl();
    			fi = new FileInfoOutVo(wholeName, fileName, "/" + dateFolder + "/" + fileName, imagehost + "/" + dateFolder + "/" + fileName);
    			fileInfoOutVos.add(fi);
    		} catch (Exception ex) {
    			ex.printStackTrace();
    			// 记录日志（并且抛出自定义 ，后期补上）
    			throw ex;
    		}
    	}
		// 返回
		return fileInfoOutVos;
	}
}
