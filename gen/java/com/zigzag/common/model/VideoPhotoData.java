/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zigzag.common.model;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-6-7")
public class VideoPhotoData implements org.apache.thrift.TBase<VideoPhotoData, VideoPhotoData._Fields>, java.io.Serializable, Cloneable, Comparable<VideoPhotoData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("VideoPhotoData");

  private static final org.apache.thrift.protocol.TField FIRST_FRAME_FIELD_DESC = new org.apache.thrift.protocol.TField("first_frame", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField VIDEO_FIELD_DESC = new org.apache.thrift.protocol.TField("video", org.apache.thrift.protocol.TType.STRUCT, (short)2);
  private static final org.apache.thrift.protocol.TField FRAMES_PER_SEC_FIELD_DESC = new org.apache.thrift.protocol.TField("frames_per_sec", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField TIME_BETWEEN_FRAMES_MS_FIELD_DESC = new org.apache.thrift.protocol.TField("time_between_frames_ms", org.apache.thrift.protocol.TType.I32, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new VideoPhotoDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new VideoPhotoDataTupleSchemeFactory());
  }

  public TileData first_frame; // required
  public TileData video; // required
  public int frames_per_sec; // required
  public int time_between_frames_ms; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    FIRST_FRAME((short)1, "first_frame"),
    VIDEO((short)2, "video"),
    FRAMES_PER_SEC((short)3, "frames_per_sec"),
    TIME_BETWEEN_FRAMES_MS((short)4, "time_between_frames_ms");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // FIRST_FRAME
          return FIRST_FRAME;
        case 2: // VIDEO
          return VIDEO;
        case 3: // FRAMES_PER_SEC
          return FRAMES_PER_SEC;
        case 4: // TIME_BETWEEN_FRAMES_MS
          return TIME_BETWEEN_FRAMES_MS;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final int __FRAMES_PER_SEC_ISSET_ID = 0;
  private static final int __TIME_BETWEEN_FRAMES_MS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.FIRST_FRAME, new org.apache.thrift.meta_data.FieldMetaData("first_frame", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TileData.class)));
    tmpMap.put(_Fields.VIDEO, new org.apache.thrift.meta_data.FieldMetaData("video", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TileData.class)));
    tmpMap.put(_Fields.FRAMES_PER_SEC, new org.apache.thrift.meta_data.FieldMetaData("frames_per_sec", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.TIME_BETWEEN_FRAMES_MS, new org.apache.thrift.meta_data.FieldMetaData("time_between_frames_ms", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(VideoPhotoData.class, metaDataMap);
  }

  public VideoPhotoData() {
  }

  public VideoPhotoData(
    TileData first_frame,
    TileData video,
    int frames_per_sec,
    int time_between_frames_ms)
  {
    this();
    this.first_frame = first_frame;
    this.video = video;
    this.frames_per_sec = frames_per_sec;
    setFrames_per_secIsSet(true);
    this.time_between_frames_ms = time_between_frames_ms;
    setTime_between_frames_msIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public VideoPhotoData(VideoPhotoData other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetFirst_frame()) {
      this.first_frame = new TileData(other.first_frame);
    }
    if (other.isSetVideo()) {
      this.video = new TileData(other.video);
    }
    this.frames_per_sec = other.frames_per_sec;
    this.time_between_frames_ms = other.time_between_frames_ms;
  }

  public VideoPhotoData deepCopy() {
    return new VideoPhotoData(this);
  }

  @Override
  public void clear() {
    this.first_frame = null;
    this.video = null;
    setFrames_per_secIsSet(false);
    this.frames_per_sec = 0;
    setTime_between_frames_msIsSet(false);
    this.time_between_frames_ms = 0;
  }

  public TileData getFirst_frame() {
    return this.first_frame;
  }

  public VideoPhotoData setFirst_frame(TileData first_frame) {
    this.first_frame = first_frame;
    return this;
  }

  public void unsetFirst_frame() {
    this.first_frame = null;
  }

  /** Returns true if field first_frame is set (has been assigned a value) and false otherwise */
  public boolean isSetFirst_frame() {
    return this.first_frame != null;
  }

  public void setFirst_frameIsSet(boolean value) {
    if (!value) {
      this.first_frame = null;
    }
  }

  public TileData getVideo() {
    return this.video;
  }

  public VideoPhotoData setVideo(TileData video) {
    this.video = video;
    return this;
  }

  public void unsetVideo() {
    this.video = null;
  }

  /** Returns true if field video is set (has been assigned a value) and false otherwise */
  public boolean isSetVideo() {
    return this.video != null;
  }

  public void setVideoIsSet(boolean value) {
    if (!value) {
      this.video = null;
    }
  }

  public int getFrames_per_sec() {
    return this.frames_per_sec;
  }

  public VideoPhotoData setFrames_per_sec(int frames_per_sec) {
    this.frames_per_sec = frames_per_sec;
    setFrames_per_secIsSet(true);
    return this;
  }

  public void unsetFrames_per_sec() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __FRAMES_PER_SEC_ISSET_ID);
  }

  /** Returns true if field frames_per_sec is set (has been assigned a value) and false otherwise */
  public boolean isSetFrames_per_sec() {
    return EncodingUtils.testBit(__isset_bitfield, __FRAMES_PER_SEC_ISSET_ID);
  }

  public void setFrames_per_secIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __FRAMES_PER_SEC_ISSET_ID, value);
  }

  public int getTime_between_frames_ms() {
    return this.time_between_frames_ms;
  }

  public VideoPhotoData setTime_between_frames_ms(int time_between_frames_ms) {
    this.time_between_frames_ms = time_between_frames_ms;
    setTime_between_frames_msIsSet(true);
    return this;
  }

  public void unsetTime_between_frames_ms() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __TIME_BETWEEN_FRAMES_MS_ISSET_ID);
  }

  /** Returns true if field time_between_frames_ms is set (has been assigned a value) and false otherwise */
  public boolean isSetTime_between_frames_ms() {
    return EncodingUtils.testBit(__isset_bitfield, __TIME_BETWEEN_FRAMES_MS_ISSET_ID);
  }

  public void setTime_between_frames_msIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __TIME_BETWEEN_FRAMES_MS_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case FIRST_FRAME:
      if (value == null) {
        unsetFirst_frame();
      } else {
        setFirst_frame((TileData)value);
      }
      break;

    case VIDEO:
      if (value == null) {
        unsetVideo();
      } else {
        setVideo((TileData)value);
      }
      break;

    case FRAMES_PER_SEC:
      if (value == null) {
        unsetFrames_per_sec();
      } else {
        setFrames_per_sec((Integer)value);
      }
      break;

    case TIME_BETWEEN_FRAMES_MS:
      if (value == null) {
        unsetTime_between_frames_ms();
      } else {
        setTime_between_frames_ms((Integer)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case FIRST_FRAME:
      return getFirst_frame();

    case VIDEO:
      return getVideo();

    case FRAMES_PER_SEC:
      return Integer.valueOf(getFrames_per_sec());

    case TIME_BETWEEN_FRAMES_MS:
      return Integer.valueOf(getTime_between_frames_ms());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case FIRST_FRAME:
      return isSetFirst_frame();
    case VIDEO:
      return isSetVideo();
    case FRAMES_PER_SEC:
      return isSetFrames_per_sec();
    case TIME_BETWEEN_FRAMES_MS:
      return isSetTime_between_frames_ms();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof VideoPhotoData)
      return this.equals((VideoPhotoData)that);
    return false;
  }

  public boolean equals(VideoPhotoData that) {
    if (that == null)
      return false;

    boolean this_present_first_frame = true && this.isSetFirst_frame();
    boolean that_present_first_frame = true && that.isSetFirst_frame();
    if (this_present_first_frame || that_present_first_frame) {
      if (!(this_present_first_frame && that_present_first_frame))
        return false;
      if (!this.first_frame.equals(that.first_frame))
        return false;
    }

    boolean this_present_video = true && this.isSetVideo();
    boolean that_present_video = true && that.isSetVideo();
    if (this_present_video || that_present_video) {
      if (!(this_present_video && that_present_video))
        return false;
      if (!this.video.equals(that.video))
        return false;
    }

    boolean this_present_frames_per_sec = true;
    boolean that_present_frames_per_sec = true;
    if (this_present_frames_per_sec || that_present_frames_per_sec) {
      if (!(this_present_frames_per_sec && that_present_frames_per_sec))
        return false;
      if (this.frames_per_sec != that.frames_per_sec)
        return false;
    }

    boolean this_present_time_between_frames_ms = true;
    boolean that_present_time_between_frames_ms = true;
    if (this_present_time_between_frames_ms || that_present_time_between_frames_ms) {
      if (!(this_present_time_between_frames_ms && that_present_time_between_frames_ms))
        return false;
      if (this.time_between_frames_ms != that.time_between_frames_ms)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_first_frame = true && (isSetFirst_frame());
    list.add(present_first_frame);
    if (present_first_frame)
      list.add(first_frame);

    boolean present_video = true && (isSetVideo());
    list.add(present_video);
    if (present_video)
      list.add(video);

    boolean present_frames_per_sec = true;
    list.add(present_frames_per_sec);
    if (present_frames_per_sec)
      list.add(frames_per_sec);

    boolean present_time_between_frames_ms = true;
    list.add(present_time_between_frames_ms);
    if (present_time_between_frames_ms)
      list.add(time_between_frames_ms);

    return list.hashCode();
  }

  @Override
  public int compareTo(VideoPhotoData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetFirst_frame()).compareTo(other.isSetFirst_frame());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFirst_frame()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.first_frame, other.first_frame);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetVideo()).compareTo(other.isSetVideo());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetVideo()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.video, other.video);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetFrames_per_sec()).compareTo(other.isSetFrames_per_sec());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetFrames_per_sec()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.frames_per_sec, other.frames_per_sec);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTime_between_frames_ms()).compareTo(other.isSetTime_between_frames_ms());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTime_between_frames_ms()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.time_between_frames_ms, other.time_between_frames_ms);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("VideoPhotoData(");
    boolean first = true;

    sb.append("first_frame:");
    if (this.first_frame == null) {
      sb.append("null");
    } else {
      sb.append(this.first_frame);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("video:");
    if (this.video == null) {
      sb.append("null");
    } else {
      sb.append(this.video);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("frames_per_sec:");
    sb.append(this.frames_per_sec);
    first = false;
    if (!first) sb.append(", ");
    sb.append("time_between_frames_ms:");
    sb.append(this.time_between_frames_ms);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (first_frame == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'first_frame' was not present! Struct: " + toString());
    }
    if (video == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'video' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'frames_per_sec' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'time_between_frames_ms' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
    if (first_frame != null) {
      first_frame.validate();
    }
    if (video != null) {
      video.validate();
    }
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
      __isset_bitfield = 0;
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class VideoPhotoDataStandardSchemeFactory implements SchemeFactory {
    public VideoPhotoDataStandardScheme getScheme() {
      return new VideoPhotoDataStandardScheme();
    }
  }

  private static class VideoPhotoDataStandardScheme extends StandardScheme<VideoPhotoData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, VideoPhotoData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // FIRST_FRAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.first_frame = new TileData();
              struct.first_frame.read(iprot);
              struct.setFirst_frameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // VIDEO
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.video = new TileData();
              struct.video.read(iprot);
              struct.setVideoIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // FRAMES_PER_SEC
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.frames_per_sec = iprot.readI32();
              struct.setFrames_per_secIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // TIME_BETWEEN_FRAMES_MS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.time_between_frames_ms = iprot.readI32();
              struct.setTime_between_frames_msIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();

      // check for required fields of primitive type, which can't be checked in the validate method
      if (!struct.isSetFrames_per_sec()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'frames_per_sec' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetTime_between_frames_ms()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'time_between_frames_ms' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, VideoPhotoData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.first_frame != null) {
        oprot.writeFieldBegin(FIRST_FRAME_FIELD_DESC);
        struct.first_frame.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.video != null) {
        oprot.writeFieldBegin(VIDEO_FIELD_DESC);
        struct.video.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(FRAMES_PER_SEC_FIELD_DESC);
      oprot.writeI32(struct.frames_per_sec);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(TIME_BETWEEN_FRAMES_MS_FIELD_DESC);
      oprot.writeI32(struct.time_between_frames_ms);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class VideoPhotoDataTupleSchemeFactory implements SchemeFactory {
    public VideoPhotoDataTupleScheme getScheme() {
      return new VideoPhotoDataTupleScheme();
    }
  }

  private static class VideoPhotoDataTupleScheme extends TupleScheme<VideoPhotoData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, VideoPhotoData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.first_frame.write(oprot);
      struct.video.write(oprot);
      oprot.writeI32(struct.frames_per_sec);
      oprot.writeI32(struct.time_between_frames_ms);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, VideoPhotoData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.first_frame = new TileData();
      struct.first_frame.read(iprot);
      struct.setFirst_frameIsSet(true);
      struct.video = new TileData();
      struct.video.read(iprot);
      struct.setVideoIsSet(true);
      struct.frames_per_sec = iprot.readI32();
      struct.setFrames_per_secIsSet(true);
      struct.time_between_frames_ms = iprot.readI32();
      struct.setTime_between_frames_msIsSet(true);
    }
  }

}

