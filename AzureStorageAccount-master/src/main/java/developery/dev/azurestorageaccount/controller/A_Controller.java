package developery.dev.azurestorageaccount.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import developery.dev.azurestorageaccount.service.A_service;
import developery.dev.azurestorageaccount.service.AzureStorageQueueService;
import developery.dev.azurestorageaccount.service.AzureStorageService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/A")

public class A_Controller {
	
	@Autowired
	A_service service;
	
	@GetMapping("/save/{containerName}/{blobName}")
	public String saveBlobByAccessKeyCredential(
			@PathVariable("containerName") String containerName
			, @PathVariable("blobName") String blobName) throws IOException {
		//log.info("readBlobByAccessKeyCredential. containerName: {}, blobName: {}", containerName, blobName);
		
		return service.saveBlobByAccessKeyCredential(containerName, blobName);		
	}
	
	@GetMapping("/read/{containerName}")
	public String[]   readFileList(
			@PathVariable("containerName") String containerName)
			throws IOException {
		
		return service.readFileList(containerName);		
	}
	
	@GetMapping("/select/{containerName}/{blobName}")
	public String readSelectFile(
			@PathVariable("containerName") String containerName
			, @PathVariable("blobName") String blobName) throws IOException {
		//log.info("readBlobByAccessKeyCredential. containerName: {}, blobName: {}", containerName, blobName);
		
		return service.readSelectFile(containerName, blobName);		
	}
	
	
	@GetMapping("insertQueue/{msg}")
	public String insertQueue(
			@PathVariable("msg") String msg
			) {
		
		return service.insertQueue(msg);		
	}


}
