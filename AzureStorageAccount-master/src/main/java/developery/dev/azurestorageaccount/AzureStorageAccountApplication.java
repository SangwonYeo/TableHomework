package developery.dev.azurestorageaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import developery.dev.azurestorageaccount.service.B_service;

@SpringBootApplication
public class AzureStorageAccountApplication {

	public static void main(String[] args) {
		

		SpringApplication.run(AzureStorageAccountApplication.class, args);
		
		B_service b= new B_service();
		b.run();
	}

}
