package com.zpb.fbs.grpc.server.rpc;

import com.google.flatbuffers.FlatBufferBuilder;
import com.zpb.fbs.example.*;
import com.zpb.fbs.user.BaseResponse;
import com.zpb.fbs.user.User;
import com.zpb.fbs.user.UserGrpc;
import com.zpb.fbs.user.UserResponse;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author       pengbo.zhao
 * @description  svc
 * @createDate   2021/12/21 10:40
 * @updateDate   2021/12/21 10:40
 * @version      1.0
 */

public class MonsterSvcGrpcService extends MonsterSvcGrpc.MonsterSvcImplBase {

    static Map<String, Monster> monsters = new HashMap<String, Monster>();

    static final String BIG_MONSTER_NAME = "big-monster";

    static Monster makeBigMonster() {
        FlatBufferBuilder builder = new FlatBufferBuilder(0);

        // Create some weapons for our Monster ('Sword' and 'Axe').
        int weaponOneName = builder.createString("Sword");
        short weaponOneDamage = 3;
        int weaponTwoName = builder.createString("Axe");
        short weaponTwoDamage = 5;

        // Use the `createWeapon()` helper function to create the weapons, since we set every field.
        int[] weaps = new int[2];
        weaps[0] = Weapon.createWeapon(builder, weaponOneName, weaponOneDamage);
        weaps[1] = Weapon.createWeapon(builder, weaponTwoName, weaponTwoDamage);

        // Serialize the FlatBuffer data.
        int name = builder.createString("Orc");
        byte[] treasure = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        int inv = Monster.createInventoryVector(builder, treasure);
        int weapons = Monster.createWeaponsVector(builder, weaps);
        int pos = Vec3.createVec3(builder, 1.0f, 2.0f, 3.0f);

        Monster.startMonster(builder);
        Monster.addPos(builder, pos);
        Monster.addName(builder, name);
        Monster.addColor(builder, Color.Red);
        Monster.addHp(builder, (short)300);
        Monster.addInventory(builder, inv);
        Monster.addWeapons(builder, weapons);
        Monster.addEquippedType(builder, Equipment.Weapon);
        Monster.addEquipped(builder, weaps[1]);
        int orc = Monster.endMonster(builder);

        // You could also call `Monster.finishMonsterBuffer(builder, orc);
        builder.finish(orc);

        // We now have a FlatBuffer that can be stored on disk or sent over a network.
        ByteBuffer buf = builder.dataBuffer();

        // Get access to the root:
        return Monster.getRootAsMonster(buf);
    }

    static {
        monsters.put(BIG_MONSTER_NAME, makeBigMonster());
    }

    @Override
    public void showMonster(MonsterRequest request, StreamObserver<Monster> responseObserver) {
        Monster monster = monsters.get(request.name());
        if (monster != null) {
            responseObserver.onNext(monster);
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND.asRuntimeException());
        }
    }

}
