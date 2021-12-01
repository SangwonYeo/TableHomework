package com.developery.azure;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.table.CloudTable;
import com.microsoft.azure.storage.table.CloudTableClient;
import com.microsoft.azure.storage.table.TableOperation;
import com.microsoft.azure.storage.table.TableQuery;
import com.microsoft.azure.storage.table.TableQuery.Operators;
import com.microsoft.azure.storage.table.TableQuery.QueryComparisons;
import com.microsoft.azure.storage.table.TableResult;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GMS_StorageTableService {

	String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=sangwonyeo;AccountKey=WM2SCq6QXL1STUG0PCSn9/axNsEVoiQdAfao2D8ZAJnY0cRX/n+EnY2uFpBEaNRc0ogDo02EkHJFuR0TEcCFNw==;EndpointSuffix=core.windows.net";

	public boolean createTable(String tableName) {
		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			CloudTable cloudTable = tableClient.getTableReference(tableName);
			return cloudTable.createIfNotExists();
		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
		return false;
	}

	public List<String> getTableList() {

		List<String> resList = new ArrayList<>();
		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Loop through the collection of table names.
			for (String table : tableClient.listTables()) {
				// Output each table name.
				System.out.println(table);
				resList.add(table);
			}

		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}

		return resList;
	}

	public GMS_VO getGMS(String partitionKey, String rowKey) {

		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference("GMS");

			TableOperation retrieveOperation = TableOperation.retrieve(partitionKey, rowKey, GMS_VO.class);

			// Submit the operation to the table service.
			TableResult res = cloudTable.execute(retrieveOperation);

			GMS_VO gms = res.getResultAsType();

			// log.info("people: {}", people);

			return gms;

		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
		return null;

	}

	public ArrayList<GMS_VO> searchGMS(String work_dt, String oper_id) {

		try {
			// Retrieve storage account from connection-string.
			CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

			// Create the table client.
			CloudTableClient tableClient = storageAccount.createCloudTableClient();

			// Create a cloud table object for the table.
			CloudTable cloudTable = tableClient.getTableReference("gms");

			String dateFilter = TableQuery.generateFilterCondition("Work_dt", QueryComparisons.EQUAL, work_dt);

			String operFilter = TableQuery.generateFilterCondition("Oper_id", QueryComparisons.EQUAL, oper_id);

			String combinedFilter = TableQuery.combineFilters(dateFilter, Operators.AND, operFilter);

			// log.info("combinedFilter: " + combinedFilter);

			TableQuery<GMS_VO> rangeQuery = TableQuery.from(GMS_VO.class).where(combinedFilter);

			Iterable<GMS_VO> iter = cloudTable.execute(rangeQuery);

			ArrayList<GMS_VO> list = Lists.newArrayList(iter);
			// Loop through the results, displaying information about the entity
			for (GMS_VO entity : list) {
				// log.info("name: {}, age: {}", entity.getName(), entity.getAge(),
				// entity.getAddr());
			}

			return list;

		} catch (Exception e) {
			// Output the stack trace.
			e.printStackTrace();
		}
		return null;

	}

	public String insertGMS(GMS_VO g)
	{
		System.out.println(g);
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("gms");
		    // unique 한 값조합이 되도록 아래 2개 세팅
		    g.setPartitionKey(g.getRowNum());
		    g.setRowKey(g.getRowNum());

		    TableOperation insertCustomer1 = TableOperation.insertOrReplace(g);		    

		    // Submit the operation to the table service.
		    TableResult res = cloudTable.execute(insertCustomer1);		    
		    
		    System.out.println(res.getHttpStatusCode());
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return g.getRowNum() + "/" + g.getRowNum();
	}
	
	 
}
