package org.elvis.wang.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by wangzhiqun on 2018/2/11.
 */
public class NioServer {
    //通道管理器
    private Selector selector;

    public void initServer(int port) throws IOException {
        //获取一个ServerSocket通道连接
        ServerSocketChannel serverChanenl = ServerSocketChannel.open();
        //设置通道为非阻塞
        serverChanenl.configureBlocking(false);
        //将该通道对应的ServerSocket绑定到port端口
        serverChanenl.socket().bind(new InetSocketAddress(666));
        // 获得一个通道管理器
        this.selector = Selector.open();
        // 将通道管理器和该通道绑定，并为该通道注册SelectionKey.OP_ACCEPT事件,注册该事件后，
        // 当该事件到达时，selector.select()会返回，如果该事件没到达selector.select()会一直阻塞。
        serverChanenl.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println("服务端启动成功！");
        while(true){
            //当注册时间到达时，方法返回  都在该方法一致阻塞
            selector.select();
            Iterator<?> ite  = this.selector.selectedKeys().iterator();
            while (ite.hasNext()){
                SelectionKey key = (SelectionKey) ite.next();
                ite.remove();
                handler(key);
            }

        }
    }

    public void handler(SelectionKey key){
        //客户端接收连接事件
        if(key.isAcceptable()){
            handlerAccept(key);
            //获得可读事件
        }else if(key.isReadable()){
            handlerRead(key);
        }

    }

    public void handlerAccept(SelectionKey key){

    }
    public void handlerRead(SelectionKey key){

    }


}
