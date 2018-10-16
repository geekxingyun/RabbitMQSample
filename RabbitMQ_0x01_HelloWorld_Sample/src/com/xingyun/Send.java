package com.xingyun;

import java.util.concurrent.TimeoutException;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/***
 * @author ����
 * ������
 * **/
public class Send {
	  //������Ϣ���е�����
    private final static String QUEUE_NAME="hello";
    public static void main(String[] args) throws java.io.IOException, TimeoutException{
         //����һ����RabbitMQ Server ������
        ConnectionFactory factory = new ConnectionFactory();
        //������������ӵ����ػ����ϵĴ��� - ����Ǳ���������
        //������������ӵ���һ̨�����ϵĴ�������ֻ���ڴ�ָ�������ƻ�IP��ַ��
        factory.setHost("localhost");
        //���ӳ�����׽������ӣ���Ϊ���Ǵ���Э��汾Э�̺������֤�ȡ� 
        Connection connection = factory.newConnection();
        //���������Ǵ���һ��Channel �������Ǵ󲿷�������������APIפ���ĵط���
        Channel channel = connection.createChannel();
        //Ҫ�뷢�ͳ�ȥ�����Ǳ�������һ��������ִ�з���,��ô���ǿ��Խ���Ϣ�����������У�
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //����һ���������ݵȵ� - ֻ�е���������ʱ�Żᱻ������ 
        //��Ϣ������һ���ֽ����飬��������Ա����κ���ϲ���ĵط���
        String message = "Hello World!";
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");
        //������ǹر���Щ���Ӷ���
        channel.close();
        connection.close();
    }
}
