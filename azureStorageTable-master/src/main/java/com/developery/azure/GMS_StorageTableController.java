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
@RequestMapping("/swTable")
public class GMS_StorageTableController {

	@Autowired
	GMS_StorageTableService service;
	
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
	
	
	  @PostMapping("/insert/gms") public String insertPeople(@RequestBody GMS_VO g )
	  throws IOException {
	  
	  return service.insertGMS(g); }
	 
	
	
	@GetMapping("/gms")
	public GMS_VO getGMS(@RequestParam("partitionKey") String partitionKey, @RequestParam("rowKey") String rowKey)  {
		
		return service.getGMS(partitionKey, rowKey);
	}
	
	@GetMapping("/search/gms")
	public List<GMS_VO> getPeople(@RequestParam("work_dt") String work_dt, @RequestParam("oper_id") String oper_id)  {
		
		return service.searchGMS(work_dt, oper_id);
	}
}
