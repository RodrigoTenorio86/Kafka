package br.com.alura.ecommerce.producer;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class KafkaDispatcher implements Closeable{

	
	private KafkaProducer<String, String> producer;

	KafkaDispatcher() {
		this.producer = new KafkaProducer<>(properties());
	}



	void send(String topic, String key, String value) throws InterruptedException, ExecutionException {
		var record = new ProducerRecord<>(topic, key, value);
		Callback callback=(data, ex) ->{
			
			if(ex != null) {
				ex.printStackTrace();
				return;
			}
			System.out.println("Sucesso enviando "+data.topic()+ "::partition "+data.partition()+"/offset "+data.offset()+"/"+data.timestamp());
		};
		
		producer.send(record,callback).get();
		
	}
	
	
	
	
	private static Properties properties() {
		var properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());
			
		return properties;
		
	}



	@Override
	public void close()  {
		producer.close();
		
	}
	
	

}
