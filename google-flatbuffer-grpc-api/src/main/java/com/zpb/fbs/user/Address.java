// automatically generated by the FlatBuffers compiler, do not modify

package com.zpb.fbs.user;

import java.nio.*;
import java.lang.*;
import java.util.*;
import com.google.flatbuffers.*;

/**
 * 地址信息
 */
@SuppressWarnings("unused")
public final class Address extends Table {
  public static void ValidateVersion() { Constants.FLATBUFFERS_2_0_0(); }
  public static Address getRootAsAddress(ByteBuffer _bb) { return getRootAsAddress(_bb, new Address()); }
  public static Address getRootAsAddress(ByteBuffer _bb, Address obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__assign(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public void __init(int _i, ByteBuffer _bb) { __reset(_i, _bb); }
  public Address __assign(int _i, ByteBuffer _bb) { __init(_i, _bb); return this; }

  /**
   * 国家
   */
  public String coutry() { int o = __offset(4); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer coutryAsByteBuffer() { return __vector_as_bytebuffer(4, 1); }
  public ByteBuffer coutryInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 4, 1); }
  /**
   * 省份
   */
  public String province() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer provinceAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public ByteBuffer provinceInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 6, 1); }
  /**
   * 城市
   */
  public String city() { int o = __offset(8); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer cityAsByteBuffer() { return __vector_as_bytebuffer(8, 1); }
  public ByteBuffer cityInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 8, 1); }
  /**
   * 县区
   */
  public String region() { int o = __offset(10); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer regionAsByteBuffer() { return __vector_as_bytebuffer(10, 1); }
  public ByteBuffer regionInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 10, 1); }
  /**
   * 乡镇、街道
   */
  public String street() { int o = __offset(12); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer streetAsByteBuffer() { return __vector_as_bytebuffer(12, 1); }
  public ByteBuffer streetInByteBuffer(ByteBuffer _bb) { return __vector_in_bytebuffer(_bb, 12, 1); }
  /**
   * 编号
   */
  public long no() { int o = __offset(14); return o != 0 ? bb.getLong(o + bb_pos) : 0L; }
  public boolean mutateNo(long no) { int o = __offset(14); if (o != 0) { bb.putLong(o + bb_pos, no); return true; } else { return false; } }

  public static int createAddress(FlatBufferBuilder builder,
      int coutryOffset,
      int provinceOffset,
      int cityOffset,
      int regionOffset,
      int streetOffset,
      long no) {
    builder.startTable(6);
    Address.addNo(builder, no);
    Address.addStreet(builder, streetOffset);
    Address.addRegion(builder, regionOffset);
    Address.addCity(builder, cityOffset);
    Address.addProvince(builder, provinceOffset);
    Address.addCoutry(builder, coutryOffset);
    return Address.endAddress(builder);
  }

  public static void startAddress(FlatBufferBuilder builder) { builder.startTable(6); }
  public static void addCoutry(FlatBufferBuilder builder, int coutryOffset) { builder.addOffset(0, coutryOffset, 0); }
  public static void addProvince(FlatBufferBuilder builder, int provinceOffset) { builder.addOffset(1, provinceOffset, 0); }
  public static void addCity(FlatBufferBuilder builder, int cityOffset) { builder.addOffset(2, cityOffset, 0); }
  public static void addRegion(FlatBufferBuilder builder, int regionOffset) { builder.addOffset(3, regionOffset, 0); }
  public static void addStreet(FlatBufferBuilder builder, int streetOffset) { builder.addOffset(4, streetOffset, 0); }
  public static void addNo(FlatBufferBuilder builder, long no) { builder.addLong(5, no, 0L); }
  public static int endAddress(FlatBufferBuilder builder) {
    int o = builder.endTable();
    return o;
  }

  public static final class Vector extends BaseVector {
    public Vector __assign(int _vector, int _element_size, ByteBuffer _bb) { __reset(_vector, _element_size, _bb); return this; }

    public Address get(int j) { return get(new Address(), j); }
    public Address get(Address obj, int j) {  return obj.__assign(__indirect(__element(j), bb), bb); }
  }
}
