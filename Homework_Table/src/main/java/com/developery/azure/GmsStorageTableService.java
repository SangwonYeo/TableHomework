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
public class GmsStorageTableService {
	
	String storageConnectionString = "DefaultEndpointsProtocol=https;AccountName=sangwonyeo;AccountKey=VRVWpWA0xxSlgJsImS2S+hqPvzVNSNm1HixIgouRJZ1fTAwRSOIxxhR5y92dC06S4xxJ2J4uC4N2xfOVEMWI3A==;EndpointSuffix=core.windows.net";
	
	
	public boolean createTable(String tableName)
	{
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    CloudTable cloudTable = tableClient.getTableReference(tableName);
		    return cloudTable.createIfNotExists();
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return false;
	}

	public List<String> getTableList() {

	    List<String> resList = new ArrayList<>();
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    
		    // Loop through the collection of table names.
		    for (String table : tableClient.listTables())
		    {
		        // Output each table name.
		        System.out.println(table);
		        resList.add(table);
		    }
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		
		return resList;
	}
	
	public GmsVO getUser(String partitionKey, String rowKey) {

		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("gmsUser");
		    
		    TableOperation retrieveOperation = TableOperation.retrieve(partitionKey, rowKey, GmsVO.class);
		    
		    
		    // Submit the operation to the table service.
		    TableResult res = cloudTable.execute(retrieveOperation);
		    
		    GmsVO user = res.getResultAsType();
		    
		    return user;
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return null;
	
	}
	
	public ArrayList<GmsVO> searchUser(String userID, String userNM) {

		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("gmsUser");
		    
		    String nameFilter = TableQuery.generateFilterCondition(
		    		"UserID",
		            QueryComparisons.EQUAL,
		            userID);
		    
		    String ageFilter = TableQuery.generateFilterCondition(
		            "UserNM",
		            QueryComparisons.EQUAL,
		            userNM);
		    
		    String combinedFilter = TableQuery.combineFilters(nameFilter,
		            Operators.AND, ageFilter);
		    

		    
		    TableQuery<GmsVO> rangeQuery =
		            TableQuery.from(GmsVO.class)
		            .where(combinedFilter);
		    
		    Iterable<GmsVO> iter = cloudTable.execute(rangeQuery);
		    
		    ArrayList<GmsVO> list = Lists.newArrayList(iter);
	        // Loop through the results, displaying information about the entity
	        for (GmsVO entity : list) {

	        }
		    
		    
		    
		    return list;
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return null;
	
	}
	
	
	public String insertUser(GmsVO u)
	{
	
		try
		{
		    // Retrieve storage account from connection-string.
		    CloudStorageAccount storageAccount =
		        CloudStorageAccount.parse(storageConnectionString);

		    // Create the table client.
		    CloudTableClient tableClient = storageAccount.createCloudTableClient();

		    // Create a cloud table object for the table.
		    CloudTable cloudTable = tableClient.getTableReference("people");
		    // unique 한 값조합이 되도록 아래 2개 세팅
		    u.setPartitionKey(u.getUserID());
		    u.setRowKey(u.getUserID());

		    TableOperation insertCustomer1 = TableOperation.insertOrReplace(u);		    

		    // Submit the operation to the table service.
		    TableResult res = cloudTable.execute(insertCustomer1);		    
		    
		
		    
		}
		catch (Exception e)
		{
		    // Output the stack trace.
		    e.printStackTrace();
		}
		return u.getUserID() + "/" + u.getUserNM();
	}
}
