// automatically generated by the FlatBuffers compiler, do not modify

package com.zpb.fbs.user;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

/**
 * 游泳
 */
@SuppressWarnings("unused")
public final class Swimming extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static Swimming getRootAsSwimming(ByteBuffer _bb) { return getRootAsSwimming(_bb, new Swimming()); }
  public static Swimming getRootAsSwimming(ByteBuffer _bb, Swimming obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Swimming __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  public String swimming() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer swimmingAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer swimmingInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }

  public static int createSwimming(FlatBufferBuilder builder,
      int swimmingOffset) {
    builder.startTable(1);
    Swimming.addSwimming(builder, swimmingOffset);
    return Swimming.endSwimming(builder);
  }

  public static void startSwimming(FlatBufferBuilder builder) { builder.startTable(1); }
  public static void addSwimming(FlatBufferBuilder builder, int swimmingOffset) { builder.addOffset(0, swimmingOffset, 0); }
  public static int endSwimming(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Swimming get(int j) { return get(new Swimming(), j); }
    public Swimming get(Swimming obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}

