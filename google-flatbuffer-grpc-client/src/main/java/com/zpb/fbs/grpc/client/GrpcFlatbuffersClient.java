package com.zpb.fbs.grpc.client;

import com.google.flatbuffers.FlatBufferBuilder;
import com.zpb.fbs.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.nio.ByteBuffer;

/**
 * @author       pengbo.zhao
 * @description  flatbuffers-client
 * @createDate   2021/12/21 10:32
 * @updateDate   2021/12/21 10:32
 * @version      1.0
 */

public class GrpcFlatbuffersClient {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // forAddress: 使用目标地址和端口号创建通道
        // usePlaintext: 使用到服务器的纯文本连接
        // directExecutor: 直接在传输线程中执行应用程序代码
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 18257)
                .usePlaintext().directExecutor().build();

        // 创建调用者对象
        UserGrpc.UserBlockingStub userBlockingStub = UserGrpc.newBlockingStub(channel);

        // 对于基于类型，可以直接将类型添加到buffer中，对于 string 类型，需要单独添加
        // 1.创建缓冲器
        FlatBufferBuilder flatBufferBuilder = new FlatBufferBuilder(1024);

        // 2.设置偏移量
        // 2.1 id-long类型偏移量
        long idOffset = System.currentTimeMillis();

        // 2.2 name-string类型偏移量
        int offsetName = flatBufferBuilder.createString("张三");

        // 2.2 age-long类型偏移量
        long ageOffset = 10;

        // 2.3 salary-double类型偏移量
        double salaryOffset = 2000.0d;

        // 2.4 创建时间-long类型偏移量
        long createTimeOffset = System.currentTimeMillis();

        // 2.5 更新时间-long类型偏移量
        long updateTimeOffset = System.currentTimeMillis();

        // 2.6 级别-枚举类型偏移量
        byte seniorOffset = Level.SENIOR;

        // 2.7 address(地址)-引用类型-对象类型-偏移量 注意：这个类型为table时，可以使用下列方式
        int countryOffset = flatBufferBuilder.createString("中国");
        int provinceOffset = flatBufferBuilder.createString("河南省");
        int cityOffset = flatBufferBuilder.createString("郑州市");
        int regionOffset = flatBufferBuilder.createString("二七区");
        int streetOffset = flatBufferBuilder.createString("二七街道");
        long noOffset = 450000;
        int addressOffset = Address.createAddress(flatBufferBuilder, countryOffset, provinceOffset, cityOffset, regionOffset, streetOffset, noOffset);

        // 引用类型-基本类型-偏移量，注意：这个类型为 struct 时，需在添加属性时使用，不能分开写，否则会报异常
        //  否则会报异常：java.lang.AssertionError: FlatBuffers: struct must be serialized inline.

        // 添加-用户
        User.startUser(flatBufferBuilder);

        // 添加基本类型-偏移量
        User.addAge(flatBufferBuilder,idOffset);
        User.addName(flatBufferBuilder,offsetName);
        User.addAge(flatBufferBuilder,ageOffset);
        User.addSalary(flatBufferBuilder,salaryOffset);
        User.addCreateTime(flatBufferBuilder,createTimeOffset);
        User.addUpdateTime(flatBufferBuilder,updateTimeOffset);

        // 添加-枚举类型-偏移量
        User.addLevel(flatBufferBuilder,seniorOffset);

        // 添加-引用类型-table-偏移量
        User.addAddress(flatBufferBuilder,addressOffset);

        // 添加-引用类型-struct-偏移量
        User.addPhone(flatBufferBuilder, Phone.createPhone(flatBufferBuilder, 1312345678));

        // 设置-unions-偏移量
        User.addAbility(flatBufferBuilder,Ability.Reading);

        // 结束添加
        int endUserOffset = User.endUser(flatBufferBuilder);

        // 完成
        User.finishUserBuffer(flatBufferBuilder,endUserOffset);

        // 序列化
        ByteBuffer byteBuffer = flatBufferBuilder.dataBuffer();
        User userRequest = User.getRootAsUser(byteBuffer);

        // 发起调用
        UserResponse userResponse = userBlockingStub.save(userRequest);

        System.err.println("服务端响应结果: ");
        System.err.println("\t code:"+ userResponse.baseResponse().code());
        System.err.println("\t data:"+ userResponse.baseResponse().data());
        System.err.println("\t message:"+ userResponse.baseResponse().message());

        System.err.println("客户端rt:" +(System.currentTimeMillis() - startTime));

    }
}
