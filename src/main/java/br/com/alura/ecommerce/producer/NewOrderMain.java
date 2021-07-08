package br.com.alura.ecommerce.producer;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;


public class NewOrderMain {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
	  try(	var kafkaDispatcher= new KafkaDispatcher()){
					
		var key= UUID.randomUUID().toString();	
		var value = "132123,67523,7894589745";
		
		var email = "Obrigado voce por sua ordem! nos sao processo sua ordem!!!";
		
		
		kafkaDispatcher.send("ECOMMERCE_NEW_ORDER", key,value);
					
		kafkaDispatcher.send("ECOMMERCE_SEND_EMAIL",key,email);
	 }

	}
	
}
