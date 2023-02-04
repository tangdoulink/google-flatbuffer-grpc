import com.google.flatbuffers.FlatBufferBuilder;
import com.zpb.fbs.example.Monster;
import com.zpb.fbs.example.MonsterRequest;
import com.zpb.fbs.example.MonsterSvcGrpc;
import com.zpb.fbs.grpc.server.GrpcUtil;
import com.zpb.fbs.grpc.server.rpc.MonsterSvcGrpcService;
import com.zpb.fbs.grpc.server.rpc.UserGrpcService;
import com.zpb.fbs.user.*;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * @author       pengbo.zhao
 * @description  grpc-flatbuffers-test
 * @createDate   2021/12/28 19:38
 * @updateDate   2021/12/28 19:38
 * @version      1.0
 */
@DisplayName("grpc-flatbuffer-test")
public class GrpcFlatbuffersTest {

    @Test
    @DisplayName("启动grpc服务")
    public void startGrpcUser() throws IOException, InterruptedException {
        Server server = GrpcUtil.startGrpcService(18257, new UserGrpcService());
        GrpcUtil.blockUntilShutdown(server);
    }

    @Test
    @DisplayName("grpc-flatbuffer-client-user")
    public void testUserGrpcFlatbufferClient(){
        long startTime = System.currentTimeMillis();

        // forAddress: 使用目标地址和端口号创建通道
        // usePlaintext: 使用到服务器的纯文本连接
        // directExecutor: 直接在传输线程中执行应用程序代码
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 18257)
                .usePlaintext().directExecutor().build();

        // 创建调用存根
        UserGrpc.UserBlockingStub userBlockingStub = UserGrpc.newBlockingStub(channel);

        // 获取创建者对象
        User userRequest = getUser();

        // 发起调用
        UserResponse userResponse = userBlockingStub.save(userRequest);

        System.err.println("服务端响应结果: ");
        System.err.println("\t code:"+ userResponse.baseResponse().code());
        System.err.println("\t data:"+ userResponse.baseResponse().data());
        System.err.println("\t message:"+ userResponse.baseResponse().message());
        System.err.println("客户端rt:" +(System.currentTimeMillis() - startTime));

    }

    @Test
    @DisplayName("启动grpc服务")
    public void startGrpcMonster() throws IOException, InterruptedException {
        Server server = GrpcUtil.startGrpcService(18257, new MonsterSvcGrpcService());
        GrpcUtil.blockUntilShutdown(server);
    }

    @Test
    @DisplayName("grpc-flatbuffer-client-monster")
    public void testMonsterGrpcFlatbufferClient(){
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 18257)
                .usePlaintext().directExecutor().build();

        // 创建调用者对象
        MonsterSvcGrpc.MonsterSvcBlockingStub stub = MonsterSvcGrpc.newBlockingStub(channel);

        // 创建 flatbuffers 对象
        FlatBufferBuilder builder = new FlatBufferBuilder();
        int offsetStr1 = builder.createString("big-monster");
        int offsetRequest = MonsterRequest.createMonsterRequest(builder, offsetStr1);
        builder.finish(offsetRequest);

        ByteBuffer buffer = builder.dataBuffer();
        MonsterRequest rootAsMonsterRequest = MonsterRequest.getRootAsMonsterRequest(buffer);
        Monster monster = stub.showMonster(rootAsMonsterRequest);

        System.err.println(monster.toString());
        System.err.println(monster.name());

    }

    private User getUser(){

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

        return User.getRootAsUser(byteBuffer);

    }
}
