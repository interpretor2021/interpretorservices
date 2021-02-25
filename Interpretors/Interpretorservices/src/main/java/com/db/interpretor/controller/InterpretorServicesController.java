package com.db.interpretor.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.db.interpretor.service.InterpretorUploadFileService;
import com.db.interpretor.service.viewTextService;
import com.db.interpretor.service.SearchTextService;


@RestController
@RequestMapping("/interpretor")
public class InterpretorServicesController {

@Autowired
private InterpretorUploadFileService interpretorUploadFileService;
@Autowired
private viewTextService viewTextService;
@Autowired
private SearchTextService searchTextService;

protected Environment runtimeEnv;

@GetMapping("/")
public String index() {
    return "upload";
}

@PostMapping("/uploadFile")
public String uploadFile(@RequestParam("file") MultipartFile file,RedirectAttributes redirectAttributes) {
	System.out.println("file"+"-"+file);
 	interpretorUploadFileService.uploadFile(file);
	redirectAttributes.addFlashAttribute("message",
			"You successfully uploaded " + file.getOriginalFilename() + "!");

	return "redirect:/";
}

@RequestMapping(value = "/extracttext", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public Map preViewTxt(@RequestParam(value="file", required=true) String fileName
		)
{
	//System.out.println("file"+"-"+fileName+"Type-"+fileType);
    String retText;
	retText = viewTextService.extractText(fileName);
    return Collections.singletonMap("response", retText);
}	

@RequestMapping(value = "/searchtext", method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
public Map searchTxt(@RequestParam(value="text", required=true) String searctStr,
		@RequestParam(value="type", required=true) String fileType
		)
	{
		//System.out.println("file"+"-"+fileName+"Type-"+fileType);
	    String retText;
		retText = searchTextService.searchText(searctStr,fileType);
	    return Collections.singletonMap("response", retText);
	}	
}

