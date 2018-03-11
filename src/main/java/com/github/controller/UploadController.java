package com.github.controller;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("upload")
public class UploadController {

	@CrossOrigin
	@RequestMapping(value = "logo", method= RequestMethod.POST)
	public Object upload(@Value("${spring.web.upload-location}") String uploadPath, @RequestParam MultipartFile uploadFile, String directory,String fileName) throws Exception {

		File file = new File(uploadPath);
		if (StringUtils.isNotEmpty(directory)) {
			file = new File(file, directory);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		fileName = fileName + "." + FilenameUtils.getExtension(uploadFile.getOriginalFilename());//命名是fid+.xxx
		file = new File(file, fileName);
		uploadFile.transferTo(file);
		Map<String, String> data = new HashMap<>();
		data.put("url", "/" + file.getAbsolutePath().replace(uploadPath, ""));
		return data;
	}

	@CrossOrigin
	@RequestMapping(value = "banner", method= RequestMethod.POST)
	public Object uploadLogo(@Value("${spring.web.upload-location}") String uploadPath, @RequestParam MultipartFile uploadFile, String directory) throws Exception {

		File file = new File(uploadPath);
		if (StringUtils.isNotEmpty(directory)) {
			file = new File(file, directory);
			if (!file.exists()) {
				file.mkdirs();
			}
		}
		String fileName = System.currentTimeMillis() + new Random().nextInt(10) + "." + FilenameUtils.getExtension(uploadFile.getOriginalFilename());
		file = new File(file, fileName);
		uploadFile.transferTo(file);
		Map<String, String> data = new HashMap<>();
		data.put("url", "/" + file.getAbsolutePath().replace(uploadPath, ""));
		return data;
	}

	@CrossOrigin
	@RequestMapping(value="image/{directory}",method= RequestMethod.POST)
	public Object uploadImage(@Value("${spring.web.upload-location}") String uploadPath, @RequestParam MultipartFile file,@PathVariable String directory) throws Exception {

		File tmpfile = new File(uploadPath);
		if (StringUtils.isNotEmpty(directory)) {
			tmpfile = new File(tmpfile, directory);
			if (!tmpfile.exists()) {
				tmpfile.mkdirs();
			}
		}
		String fileName = System.currentTimeMillis() + new Random().nextInt(10) + "." + FilenameUtils.getExtension(file.getOriginalFilename());
		tmpfile = new File(tmpfile, fileName);
		file.transferTo(tmpfile);
		Map<String, String> data = new HashMap<>();
		data.put("src", "/" + tmpfile.getAbsolutePath().replace(uploadPath, ""));
		Map<String, Object> result = new HashMap<>();
		result.put("code",0);
		result.put("msg","");
		result.put("data",data);

		return result;
	}

}
