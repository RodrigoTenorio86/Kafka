package br.com.alura.ecommerce;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class NewOrderMain {
	
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		var producer = new KafkaProducer<String,String>(properties());
		
		Callback callback=(data, ex) ->{
			
				if(ex != null) {
					ex.printStackTrace();
					return;
				}
				System.out.println("Sucesso enviando "+data.topic()+ "::partition "+data.partition()+"/offset "+data.offset()+"/"+data.timestamp());
			};
			
		var value = "132123,67523,7894589745";
		var record = new ProducerRecord<>("ECOMMERCE_NEW_ORDER", value,value);
		
		var email = "Obrigado voce por sua ordem! nos sao processo sua ordem!!!";
		var emailRecord = new ProducerRecord<>("ECOMMERCE_SEND_EMAIL",email,email);
			
		producer.send(record,callback).get();
		producer.send(emailRecord,callback).get();
	}


	
	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
			
		return properties;
		
	}

}
