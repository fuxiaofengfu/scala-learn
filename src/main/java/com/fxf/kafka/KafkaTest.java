package com.fxf.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Collections;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class KafkaTest {

	public static void main(String[] args) {

		Properties properties = new Properties();
		properties.put("bootstrap.servers", "host8:9092,host9:9092,host10:9092");
		properties.put("key.serializer", StringSerializer.class.getName());
		properties.put("value.serializer", StringSerializer.class.getName());
		KafkaProducer kafkaProducer = new KafkaProducer(properties);
		new Thread() {
			@Override
			public void run() {
				int index = 1;
				while (true) {
					try {
						Thread.sleep(TimeUnit.SECONDS.toSeconds(1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ProducerRecord producerRecord = new ProducerRecord("fxf_Test_topic", "pactera1", "fffff" + index);
					kafkaProducer.send(producerRecord);
					index++;
				}
			}
		}.start();

		properties = new Properties();
		properties.put("bootstrap.servers", "host8:9092,host9:9092,host10:9092");
		properties.put("key.deserializer", StringDeserializer.class.getName());
		properties.put("value.deserializer", StringDeserializer.class.getName());
		properties.put("group.id", "fxf_group_consumer");
		//properties.put("enable.auto.commit", "false");
		properties.put("enable.auto.commit", "true");
		KafkaConsumer kafkaConsumer = new KafkaConsumer(properties);
		//kafkaConsumer.subscribe(Arrays.asList("fxf_Test_topic"));

		TopicPartition partition = new TopicPartition("fxf_Test_topic", 0);
		kafkaConsumer.assign(Collections.singletonList(partition));
		kafkaConsumer.seek(partition, 3756440);
		while (true) {
			try {
				Thread.sleep(TimeUnit.SECONDS.toSeconds(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ConsumerRecords poll = kafkaConsumer.poll(1000);
			Iterator<ConsumerRecord<String, String>> iterator = poll.iterator();
			while (iterator.hasNext()) {
				ConsumerRecord<String, String> next = iterator.next();
				System.out.println(next.key());
				System.out.println(next.value());
				System.out.println(next.offset());
				System.out.println(next.topic());
				System.out.println(next.partition());
			}
		}
	}

}
