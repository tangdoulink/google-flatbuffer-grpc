package com.zpb.fbs.grpc.server.rpc;

import com.google.flatbuffers.FlatBufferBuilder;
import com.zpb.fbs.user.BaseResponse;
import com.zpb.fbs.user.User;
import com.zpb.fbs.user.UserGrpc;
import com.zpb.fbs.user.UserResponse;
import io.grpc.stub.StreamObserver;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author       pengbo.zhao
 * @description  user-grpc
 * @createDate   2021/12/28 15:14
 * @updateDate   2021/12/28 15:14
 * @version      1.0
 */
public class UserGrpcService extends UserGrpc.UserImplBase {

    List<User> userList = new ArrayList<>();

    @Override
    public void save(User request, StreamObserver<UserResponse> responseObserver) {
        long start = System.currentTimeMillis();
        userList.add(request);
        System.err.println("客户端请求数据是: "+request);

        // 创建缓冲构造器
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(256);

        // 创建offset
        int dataOffset = flatBufferBuilder.createString("成功");
        int messageOffset = flatBufferBuilder.createString("ok");
        int baseResponse = BaseResponse.createBaseResponse(flatBufferBuilder, 200, dataOffset, messageOffset);

        // 组装参数
        UserResponse.startUserResponse(flatBufferBuilder);
        UserResponse.addStatus(flatBufferBuilder,true);
        UserResponse.addBaseResponse(flatBufferBuilder,baseResponse);
        int endUserResponseOffset = UserResponse.endUserResponse(flatBufferBuilder);

        // 缓冲区完成
        flatBufferBuilder.finish(endUserResponseOffset);

        // 序列化
        ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();
        UserResponse rootAsUserResponse = UserResponse.getRootAsUserResponse(byteBuffer);

        responseObserver.onNext(rootAsUserResponse);
        responseObserver.onCompleted();
        System.err.println("服务端rt:"+ (System.currentTimeMillis() - start));

    }

    @Override
    public void update(User request, StreamObserver<UserResponse> responseObserver) {
        super.update(request, responseObserver);
    }

    @Override
    public void query(User request, StreamObserver<UserResponse> responseObserver) {
        super.query(request, responseObserver);
    }
}
