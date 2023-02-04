package com.zpb.fbs.grpc.server;

import io.grpc.BindableService;
import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author       pengbo.zhao
 * @description  grpc-util
 * @createDate   2021/12/18 19:27
 * @updateDate   2021/12/18 19:27
 * @version      1.0
 */

public class GrpcUtil {

    /**
     * 启动grpc 服务
     * @param port 端口
     * @return Server
     * @throws IOException io
     */
    public static Server startGrpcService(int port,BindableService buildService) throws IOException {
        Server server = ServerBuilder.forPort(port).addService(buildService)
                .build().start();
        System.err.println("***************************************");
        System.err.println("[ grpc service starter......]");
        System.err.println("[ grpc service port:  " + port + " ]");
        System.err.println("***************************************");

        return server;
    }

    /**
     * jvm关闭时，会关闭grpc
     */
    public static void shutdownGrpc(Server server){
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                server.shutdown();
                System.err.println("grpc service shutdown......");
            }
        });
    }

    /**
     * 阻塞，直到服务关闭
     * @throws InterruptedException 打断异常
     */
    public static void blockUntilShutdown(Server server) throws InterruptedException {
        if(server != null ){
            server.awaitTermination();
        }
    }

}
