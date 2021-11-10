package developery.dev.azurestorageaccount.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.azure.identity.DefaultAzureCredentialBuilder;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobItem;
import com.azure.storage.blob.models.BlobProperties;
import com.azure.storage.queue.QueueClient;
import com.azure.storage.queue.QueueClientBuilder;
import com.azure.storage.queue.models.SendMessageResult;

import developery.dev.azurestorageaccount.common.CommonUtils;
import lombok.extern.slf4j.Slf4j;



@Slf4j
@Service

public class A_service {
	
	final String BLOB_END_POINT = "https://sangwonyeo.blob.core.windows.net/";
	final String accessKey = "DefaultEndpointsProtocol=https;AccountName=sangwonyeo;AccountKey=WM2SCq6QXL1STUG0PCSn9/axNsEVoiQdAfao2D8ZAJnY0cRX/n+EnY2uFpBEaNRc0ogDo02EkHJFuR0TEcCFNw==;EndpointSuffix=core.windows.net";
	BlobServiceClient blobServiceClient = new BlobServiceClientBuilder()
		    .endpoint(BLOB_END_POINT)
		    .connectionString(accessKey)
		    .buildClient();
	
	final String ACCOUNT_END_POINT = "https://sangwonyeo.queue.core.windows.net/";
	final String QUEUE_END_POINT = ACCOUNT_END_POINT + "demo";
	String SAS_TOKEN= "?sv=2020-08-04&ss=bfqt&srt=sco&sp=rwdlacupitfx&se=2021-11-09T20:22:05Z&st=2021-11-09T12:22:05Z&spr=https&sig=3sTdDfZ2Ndh5OwWAPx7GC%2BkNmmUCbhJzRKQ4PmEzGk8%3D";

		
	public String saveBlobByAccessKeyCredential(String containerName, String blobName) throws IOException {
							

		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		
	
		String fileName = blobName+ ".txt";
		File localFile = new File(fileName);

		
		FileWriter writer = new FileWriter(fileName, true);
		writer.write("Hello, World!");
		writer.close();

		
		BlobClient blobClient = containerClient.getBlobClient(fileName);

		
		blobClient.uploadFromFile(fileName);
		
		
		return "Save Success!";	
	}
	
	public String[] readFileList (String containerName) throws IOException {
		
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		ArrayList<String> blobList = new ArrayList();
		
		for (BlobItem blobItem : containerClient.listBlobs()) {
		    //System.out.println("\t" + blobItem.getName());
			blobList.add(blobItem.toString());
		}
		
		String [] list = new String [blobList.size()];

		for(int i=0; i< list.length;i++) {
			
			list[i] =  blobList.get(i).toString();
		}
		
		
		return list;
	}
	
	public String readSelectFile(String containerName, String blobName) throws IOException {
		
		BlobContainerClient containerClient = blobServiceClient.getBlobContainerClient(containerName);
		
		BlobClient blobClient = containerClient.getBlobClient(blobName);
		
		String textInFile = CommonUtils.readStringFromFile(blobName+".txt");
		
		return textInFile;
	}
	
	public String insertQueue(String msg) {
		
		QueueClient queueClient = new QueueClientBuilder()
				.endpoint(QUEUE_END_POINT)
				.sasToken(SAS_TOKEN)
				.buildClient();
		
		SendMessageResult result = queueClient.sendMessage(LocalDateTime.now() + "___________________" + msg);
		
		System.out.println("msgId: " + result.getMessageId());
		
		return result.getMessageId();
		
	}
	

}
