package org.elvis.wang.server;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wangzhiqun on 2018/2/11.
 * 通过CMD telnet 127.0.0.1 666   来模拟client 端
 */
public class BioServer {

    public static void main(String[] args) throws IOException {
        ExecutorService handleThreadPool = Executors.newCachedThreadPool();

        //创建socket 服务，监听666端口
        ServerSocket server = new ServerSocket(666);
        System.out.println("服务器启动! ");
        while (true){
            //获取一个套字节（阻塞 accept）
            final Socket socket = server.accept();
            System.out.println("来了一个新客户端! ");

            //多线程来提高IOc处理效率  一个客户端一个socket 多个socket
            //多线程的创建和维护也是一种消耗，尤其是多线程被CPU挂起唤醒即频繁的进行上线问切换是非常消耗系统资源的

           handleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });

        }
    }

    public static void handler(Socket socket){
         try{
             byte[] bytes = new byte[1024];
             InputStream inputStream = socket.getInputStream();

             while(true){
                 //BIO 阻塞到写
                 int read = inputStream.read(bytes);
                 if(read!=-1){
                     System.out.println(new String(bytes, 0, read));
                 }else{
                     break;
                 }
             }

         }catch (Exception e ){
             e.printStackTrace();
         }finally {
             try {
                 System.out.println("socket关闭");
                 socket.close();
             } catch (IOException e) {
                 e.printStackTrace();
             }
         }
    }
}
