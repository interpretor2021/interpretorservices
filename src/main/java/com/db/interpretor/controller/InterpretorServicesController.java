package com.db.interpretor.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.db.interpretor.service.InterpretorUploadFileService;



@RestController
@RequestMapping("/interpretor")
public class InterpretorServicesController {

@Autowired
private InterpretorUploadFileService interpretorUploadFileService;

@Value("${app.myCredentials}")
public String appmyCredentials;

@Value("${app.projectId}")
public String appprojectId;

@Value("${app.bucketName}")
public String appbucketName;

@Value("${app.objectName}")
public String appobjectName;

@Value("${app.filePath}")
public String appfilePath;

@GetMapping("/")
public String index() {
    return "upload";
}

@PostMapping("/uploadFile")
public String uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {
	interpretorUploadFileService.uploadFile(file);
	redirectAttributes.addFlashAttribute("message",
			"You successfully uploaded " + file.getOriginalFilename() + "!");

	return "redirect:/";
}

@PostMapping("/uploadFileone")
public String uploadFile(@RequestParam("file") String file) {
	interpretorUploadFileService.uploadObject(appmyCredentials,appprojectId,appbucketName,appobjectName,file);
	return "redirect:/";
}


@SuppressWarnings("rawtypes")
@GetMapping("/filelistall")
public ArrayList listFiles() {
    return interpretorUploadFileService.getFileListing();
}

}

