package hellorabbitmq.demo.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author songtao
 * @create 2020-05-2020/5/17-18:22
 */
public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //1创建连接工厂,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.95.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2.通过工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection获取channel
        Channel channel = connection.createChannel();
        //4.声明（创建）一个队列
        String queueName = "test001";
        channel.queueDeclare(queueName, true, false, false, null);

        //5.创建consumer
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6.设置channel
        channel.basicConsume(queueName, true, queueingConsumer);

        while (true) {
            //7.获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            byte[] msg = delivery.getBody();
            String result = new String(msg);
            System.out.println("消费段："+result);
        }


    }
}
