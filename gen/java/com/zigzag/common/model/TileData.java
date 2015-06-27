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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-6-26")
public class TileData implements org.apache.thrift.TBase<TileData, TileData._Fields>, java.io.Serializable, Cloneable, Comparable<TileData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("TileData");

  private static final org.apache.thrift.protocol.TField WIDTH_FIELD_DESC = new org.apache.thrift.protocol.TField("width", org.apache.thrift.protocol.TType.I32, (short)1);
  private static final org.apache.thrift.protocol.TField HEIGHT_FIELD_DESC = new org.apache.thrift.protocol.TField("height", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField URI_PATH_FIELD_DESC = new org.apache.thrift.protocol.TField("uri_path", org.apache.thrift.protocol.TType.STRING, (short)3);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new TileDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new TileDataTupleSchemeFactory());
  }

  public int width; // required
  public int height; // required
  public String uri_path; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    WIDTH((short)1, "width"),
    HEIGHT((short)2, "height"),
    URI_PATH((short)3, "uri_path");

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
        case 1: // WIDTH
          return WIDTH;
        case 2: // HEIGHT
          return HEIGHT;
        case 3: // URI_PATH
          return URI_PATH;
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
  private static final int __WIDTH_ISSET_ID = 0;
  private static final int __HEIGHT_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.WIDTH, new org.apache.thrift.meta_data.FieldMetaData("width", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.HEIGHT, new org.apache.thrift.meta_data.FieldMetaData("height", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.URI_PATH, new org.apache.thrift.meta_data.FieldMetaData("uri_path", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(TileData.class, metaDataMap);
  }

  public TileData() {
  }

  public TileData(
    int width,
    int height,
    String uri_path)
  {
    this();
    this.width = width;
    setWidthIsSet(true);
    this.height = height;
    setHeightIsSet(true);
    this.uri_path = uri_path;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public TileData(TileData other) {
    __isset_bitfield = other.__isset_bitfield;
    this.width = other.width;
    this.height = other.height;
    if (other.isSetUri_path()) {
      this.uri_path = other.uri_path;
    }
  }

  public TileData deepCopy() {
    return new TileData(this);
  }

  @Override
  public void clear() {
    setWidthIsSet(false);
    this.width = 0;
    setHeightIsSet(false);
    this.height = 0;
    this.uri_path = null;
  }

  public int getWidth() {
    return this.width;
  }

  public TileData setWidth(int width) {
    this.width = width;
    setWidthIsSet(true);
    return this;
  }

  public void unsetWidth() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __WIDTH_ISSET_ID);
  }

  /** Returns true if field width is set (has been assigned a value) and false otherwise */
  public boolean isSetWidth() {
    return EncodingUtils.testBit(__isset_bitfield, __WIDTH_ISSET_ID);
  }

  public void setWidthIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __WIDTH_ISSET_ID, value);
  }

  public int getHeight() {
    return this.height;
  }

  public TileData setHeight(int height) {
    this.height = height;
    setHeightIsSet(true);
    return this;
  }

  public void unsetHeight() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __HEIGHT_ISSET_ID);
  }

  /** Returns true if field height is set (has been assigned a value) and false otherwise */
  public boolean isSetHeight() {
    return EncodingUtils.testBit(__isset_bitfield, __HEIGHT_ISSET_ID);
  }

  public void setHeightIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __HEIGHT_ISSET_ID, value);
  }

  public String getUri_path() {
    return this.uri_path;
  }

  public TileData setUri_path(String uri_path) {
    this.uri_path = uri_path;
    return this;
  }

  public void unsetUri_path() {
    this.uri_path = null;
  }

  /** Returns true if field uri_path is set (has been assigned a value) and false otherwise */
  public boolean isSetUri_path() {
    return this.uri_path != null;
  }

  public void setUri_pathIsSet(boolean value) {
    if (!value) {
      this.uri_path = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case WIDTH:
      if (value == null) {
        unsetWidth();
      } else {
        setWidth((Integer)value);
      }
      break;

    case HEIGHT:
      if (value == null) {
        unsetHeight();
      } else {
        setHeight((Integer)value);
      }
      break;

    case URI_PATH:
      if (value == null) {
        unsetUri_path();
      } else {
        setUri_path((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case WIDTH:
      return Integer.valueOf(getWidth());

    case HEIGHT:
      return Integer.valueOf(getHeight());

    case URI_PATH:
      return getUri_path();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case WIDTH:
      return isSetWidth();
    case HEIGHT:
      return isSetHeight();
    case URI_PATH:
      return isSetUri_path();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof TileData)
      return this.equals((TileData)that);
    return false;
  }

  public boolean equals(TileData that) {
    if (that == null)
      return false;

    boolean this_present_width = true;
    boolean that_present_width = true;
    if (this_present_width || that_present_width) {
      if (!(this_present_width && that_present_width))
        return false;
      if (this.width != that.width)
        return false;
    }

    boolean this_present_height = true;
    boolean that_present_height = true;
    if (this_present_height || that_present_height) {
      if (!(this_present_height && that_present_height))
        return false;
      if (this.height != that.height)
        return false;
    }

    boolean this_present_uri_path = true && this.isSetUri_path();
    boolean that_present_uri_path = true && that.isSetUri_path();
    if (this_present_uri_path || that_present_uri_path) {
      if (!(this_present_uri_path && that_present_uri_path))
        return false;
      if (!this.uri_path.equals(that.uri_path))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_width = true;
    list.add(present_width);
    if (present_width)
      list.add(width);

    boolean present_height = true;
    list.add(present_height);
    if (present_height)
      list.add(height);

    boolean present_uri_path = true && (isSetUri_path());
    list.add(present_uri_path);
    if (present_uri_path)
      list.add(uri_path);

    return list.hashCode();
  }

  @Override
  public int compareTo(TileData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetWidth()).compareTo(other.isSetWidth());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetWidth()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.width, other.width);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetHeight()).compareTo(other.isSetHeight());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetHeight()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.height, other.height);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetUri_path()).compareTo(other.isSetUri_path());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetUri_path()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.uri_path, other.uri_path);
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
    StringBuilder sb = new StringBuilder("TileData(");
    boolean first = true;

    sb.append("width:");
    sb.append(this.width);
    first = false;
    if (!first) sb.append(", ");
    sb.append("height:");
    sb.append(this.height);
    first = false;
    if (!first) sb.append(", ");
    sb.append("uri_path:");
    if (this.uri_path == null) {
      sb.append("null");
    } else {
      sb.append(this.uri_path);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'width' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'height' because it's a primitive and you chose the non-beans generator.
    if (uri_path == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'uri_path' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
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

  private static class TileDataStandardSchemeFactory implements SchemeFactory {
    public TileDataStandardScheme getScheme() {
      return new TileDataStandardScheme();
    }
  }

  private static class TileDataStandardScheme extends StandardScheme<TileData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, TileData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // WIDTH
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.width = iprot.readI32();
              struct.setWidthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // HEIGHT
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.height = iprot.readI32();
              struct.setHeightIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // URI_PATH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.uri_path = iprot.readString();
              struct.setUri_pathIsSet(true);
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
      if (!struct.isSetWidth()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'width' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetHeight()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'height' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, TileData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(WIDTH_FIELD_DESC);
      oprot.writeI32(struct.width);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(HEIGHT_FIELD_DESC);
      oprot.writeI32(struct.height);
      oprot.writeFieldEnd();
      if (struct.uri_path != null) {
        oprot.writeFieldBegin(URI_PATH_FIELD_DESC);
        oprot.writeString(struct.uri_path);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class TileDataTupleSchemeFactory implements SchemeFactory {
    public TileDataTupleScheme getScheme() {
      return new TileDataTupleScheme();
    }
  }

  private static class TileDataTupleScheme extends TupleScheme<TileData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, TileData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI32(struct.width);
      oprot.writeI32(struct.height);
      oprot.writeString(struct.uri_path);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, TileData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.width = iprot.readI32();
      struct.setWidthIsSet(true);
      struct.height = iprot.readI32();
      struct.setHeightIsSet(true);
      struct.uri_path = iprot.readString();
      struct.setUri_pathIsSet(true);
    }
  }

}

