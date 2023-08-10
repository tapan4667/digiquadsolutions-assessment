package com.digiquadsolutions.assessment.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.digiquadsolutions.assessment.service.XmlReaderService;

@Controller
public class DataExtractorConroller {
	@Autowired
	XmlReaderService rs;

	@PostMapping("/uploadFile")
	public String submit(@RequestParam("file") MultipartFile file, @RequestParam("tagname") String tagname,
			@RequestParam("attributename") String attributename, @RequestParam("attributevalue") String attributevalue)
			throws IOException {
		rs.parsXml(file.getInputStream(), tagname, attributename, attributevalue);
		System.out.println("digiquad solution");
		return "fileUploadView";
	}

	@PostMapping("/printXmlData")
	public String Submitblankdata(@RequestParam("file") MultipartFile file) throws IOException {
		rs.parsXml(file.getInputStream(), "", "", "");
		System.out.println("digiquad solution");
		return "fileUploadView";
	}

	@GetMapping("/uploadFile")
	public String submit() throws IOException {

		return "fileupload";
	}
}
