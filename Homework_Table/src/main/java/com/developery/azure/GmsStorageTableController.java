package com.developery.azure;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/azureStorageTable")
public class GmsStorageTableController {

	@Autowired
	GmsStorageTableService service;
	
	@PostMapping("/table/{tableName}")
	public String createTable(
			@PathVariable("tableName") String tableName) throws IOException {
		
		boolean res = service.createTable(tableName);
		
		return "{\"res\": " + res + "}";
	}
	
	@GetMapping("/table")
	public List<String> getTableList( ) throws IOException {
		
		return service.getTableList();
	}
	
	@PostMapping("/insert/user")
	public String insertPeople(@RequestBody GmsVO u ) throws IOException {
		
		return service.insertUser(u);
	}
	
	@GetMapping("/user")
	public GmsVO getPeople(@RequestParam("partitionKey") String partitionKey, @RequestParam("rowKey") String rowKey)  {
		
		return service.getUser(partitionKey, rowKey);
	}
	
	@GetMapping("/search/user")
	public List<GmsVO> getUser(@RequestParam("userID") String userID, @RequestParam("userNM") String userNM)  {
		
		return service.searchUser(userID, userNM);
	}
}
