package com.xingyun;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/***
 * ������
 * ****/
public class Recv {
 private final static  String QUEUE_NAME="hello";
     
     public static void main(String[] args) throws IOException, TimeoutException {
         
          /**
           * �����뷢������ͬ; 
           * ���Ǵ�һ�����Ӻ�һ��ͨ�������������ǽ�Ҫʹ�õĶ��С� 
           * ��ע�⣬���뷢�ͷ������Ķ�����ƥ��
           * */
           ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost");
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();

            /**
             * ��ע�⣬����Ҳ�������������С� 
             * ��Ϊ���ǿ��ܻ��ڷ�����֮ǰ���������ߣ���������ϣ�������ǳ���ʹ����Ϣ֮ǰȷ�������Ѵ��ڡ�   
             * */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
            
            /**
             *  ���Ǽ������߷������������е���Ϣ���ݸ����ǡ�
             *   ���������첽������Ϣ����������Զ������ʽ�ṩ�ص�������Ϣ��������Ϣ��ֱ������׼����ʹ�����ǡ� 
             *  ����һ��DefaultConsumer��������á�
             * */

            Consumer consumer = new DefaultConsumer(channel) {
                  @Override
                  public void handleDelivery(String consumerTag, Envelope envelope,
                                             AMQP.BasicProperties properties, byte[] body)
                      throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + message + "'");
                  }
                };
            channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}
