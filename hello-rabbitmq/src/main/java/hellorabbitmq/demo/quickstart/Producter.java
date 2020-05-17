package hellorabbitmq.demo.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author songtao
 * @create 2020-05-2020/5/17-18:22
 */
public class Producter {
    public static void main(String[] args) throws IOException, TimeoutException {
         //1创建连接工厂,并进行配置
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.95.7");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        //2.通过工厂创建连接
        Connection connection = connectionFactory.newConnection();
        //3.通过connection获取channel
        Channel channel = connection.createChannel();
        //4.通过channel发送数据
        for (int i=0;i < 5 ;i++) {
            String msg = "hello RabbitMQ1";
            channel.basicPublish("", "test001", null, msg.getBytes());
        }

        //5.关闭连接
        channel.close();
        connection.close();
    }
}
