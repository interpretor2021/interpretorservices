package com.db.interpretor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

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

}

