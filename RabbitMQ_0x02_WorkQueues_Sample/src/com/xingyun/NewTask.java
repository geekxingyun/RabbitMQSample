package com.xingyun;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/***
 * ������
 * ********/
public class NewTask {
	private static final String TASK_QUEUE_NAME = "task_queue";

	  public static void main(String[] argv) throws Exception {
		  
		//��������Ϣ���е�����  
	    ConnectionFactory factory = new ConnectionFactory();
	    factory.setHost("localhost");
	    Connection connection = factory.newConnection();
	    Channel channel = connection.createChannel();

	    //�ڶ�������Ϊtrue ȷ���ر�RabbitMQ������ʱִ�г־û�
	    channel.queueDeclare(TASK_QUEUE_NAME, true, false, false, null);

	    //�������з���������Ϣ
	    String message = getMessage(argv);

	    //����Ϣ���Ϊ�־��� - ͨ����MessageProperties��ʵ��BasicProperties������ΪֵPERSISTENT_TEXT_PLAIN��
	    channel.basicPublish("",
	    		TASK_QUEUE_NAME,//ָ����Ϣ���е�����
	    		MessageProperties.PERSISTENT_TEXT_PLAIN,//ָ����Ϣ�־û�
	        message.getBytes("UTF-8"));//ָ����Ϣ���ַ�����
	    //��ӡ�����߷��ͳɹ�����Ϣ
	    System.out.println(" [x] Sent '" + message + "'");

	    //�ر���Դ
	    channel.close();
	    connection.close();
	  }

	  
	  /***
	   * һЩ�����������в�����ȡ��Ϣ
	   * @param strings �������з���������Ϣ�ַ���
	   * */
	  private static String getMessage(String[] strings) {
	    if (strings.length < 1)
	      return "Hello World!";
	    return joinStrings(strings," ");
	  }

	  /**
	   * �ַ�������
	   * @param delimiter �ָ���
	   * */
	  private static String joinStrings(String[] strings, String delimiter) {
	    int length = strings.length;
	    if (length == 0) return "";
	    StringBuilder words = new StringBuilder(strings[0]);
	    for (int i = 1; i < length; i++) {
	      words.append(delimiter).append(strings[i]);
	    }
	    return words.toString();
	  }
}
