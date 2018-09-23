package com.xingyun;

import java.io.IOException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Worker {

	private static final String TASK_QUEUE_NAME = "task_queue";

	public static void main(String[] argv) throws Exception {
		
		//����Ϣ���д�������
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		final Connection connection = factory.newConnection();
		final Channel channel = connection.createChannel();

		//ָ����Ϣ���е�����
		channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);
		System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

		//�ڴ���ȷ��ǰһ����Ϣ֮ǰ����Ҫ������Ա��������Ϣ���෴�����Ὣ�����͸���һ����Ȼ��æ�Ĺ���
		int prefetchCount = 1 ; 
		channel.basicQos(prefetchCount);
		//channel.basicQos(1);

		final Consumer consumer = new DefaultConsumer(channel) {
			@Override
			public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
					byte[] body) throws IOException {
				String message = new String(body, "UTF-8");

				System.out.println(" [x] Received '" + message + "'");
				try {
					doWork(message);
				} finally {
					System.out.println(" [x] Done");
					channel.basicAck(envelope.getDeliveryTag(), false);
				}
			}
		};
		//boolean autoAck = false;
		//channel.basicConsume(TASK_QUEUE_NAME, autoAck, consumer);
		channel.basicConsume(TASK_QUEUE_NAME, false, consumer);
	}

	/*****
	 * ���ǵļ�������ģ��ִ��ʱ��
	 * */
	private static void doWork(String task) {
		for (char ch : task.toCharArray()) {
			if (ch == '.') {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException _ignored) {
					Thread.currentThread().interrupt();
				}
			}
		}
	}
}
