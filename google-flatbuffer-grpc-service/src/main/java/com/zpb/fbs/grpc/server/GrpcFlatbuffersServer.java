package com.zpb.fbs.grpc.server;

import com.zpb.fbs.grpc.server.rpc.UserGrpcService;
import io.grpc.Server;

/**
 * @author       pengbo.zhao
 * @description  grpc-flatbuffers-server
 * @createDate   2021/12/21 10:35
 * @updateDate   2021/12/21 10:35
 * @version      1.0
 */

public class GrpcFlatbuffersServer {

    public static void main(String[] args) throws Exception {

        // 启动服务
        Server server = GrpcUtil.startGrpcService(18257,new UserGrpcService());
        GrpcUtil.blockUntilShutdown(server);

    }

}
