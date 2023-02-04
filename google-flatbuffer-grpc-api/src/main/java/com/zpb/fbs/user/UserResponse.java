// automatically generated by the FlatBuffers compiler, do not modify

package com.zpb.fbs.user;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

/**
 * 用户响应
 */
@SuppressWarnings("unused")
public final class UserResponse extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static UserResponse getRootAsUserResponse(ByteBuffer _bb) { return getRootAsUserResponse(_bb, new UserResponse()); }
  public static UserResponse getRootAsUserResponse(ByteBuffer _bb, UserResponse obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public UserResponse __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public com.zpb.fbs.user.BaseResponse baseResponse() { return baseResponse(new com.zpb.fbs.user.BaseResponse()); }
  public com.zpb.fbs.user.BaseResponse baseResponse(com.zpb.fbs.user.BaseResponse obj) { int o = __offset(4); return o != 0 ? obj.__assign(__indirect(o + bb_pos), bb) : null; }
  public boolean status() { int o = __offset(6); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public boolean mutateStatus(boolean status) { int o = __offset(6); if (o != 0) { bb.put(o + bb_pos, (byte)(status ? 1 : 0)); return true; } else { return false; } }

  public static int createUserResponse(FlatBufferBuilder builder,
      int baseResponseOffset,
      boolean status) {
    builder.startTable(2);
    UserResponse.addBaseResponse(builder, baseResponseOffset);
    UserResponse.addStatus(builder, status);
    return UserResponse.endUserResponse(builder);
  }

  public static void startUserResponse(FlatBufferBuilder builder) { builder.startTable(2); }
  public static void addBaseResponse(FlatBufferBuilder builder, int baseResponseOffset) { builder.addOffset(0, baseResponseOffset, 0); }
  public static void addStatus(FlatBufferBuilder builder, boolean status) { builder.addBoolean(1, status, false); }
  public static int endUserResponse(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public UserResponse get(int j) { return get(new UserResponse(), j); }
    public UserResponse get(UserResponse obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}
