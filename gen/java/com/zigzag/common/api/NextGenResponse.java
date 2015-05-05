/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zigzag.common.api;

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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-5-5")
public class NextGenResponse implements org.apache.thrift.TBase<NextGenResponse, NextGenResponse._Fields>, java.io.Serializable, Cloneable, Comparable<NextGenResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NextGenResponse");

  private static final org.apache.thrift.protocol.TField GENERATION_FIELD_DESC = new org.apache.thrift.protocol.TField("generation", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField BANDWIDTH_ALERT_FIELD_DESC = new org.apache.thrift.protocol.TField("bandwidth_alert", org.apache.thrift.protocol.TType.BOOL, (short)2);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new NextGenResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new NextGenResponseTupleSchemeFactory());
  }

  public com.zigzag.common.model.Generation generation; // required
  public boolean bandwidth_alert; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GENERATION((short)1, "generation"),
    BANDWIDTH_ALERT((short)2, "bandwidth_alert");

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
        case 1: // GENERATION
          return GENERATION;
        case 2: // BANDWIDTH_ALERT
          return BANDWIDTH_ALERT;
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
  private static final int __BANDWIDTH_ALERT_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GENERATION, new org.apache.thrift.meta_data.FieldMetaData("generation", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.zigzag.common.model.Generation.class)));
    tmpMap.put(_Fields.BANDWIDTH_ALERT, new org.apache.thrift.meta_data.FieldMetaData("bandwidth_alert", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.BOOL)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NextGenResponse.class, metaDataMap);
  }

  public NextGenResponse() {
  }

  public NextGenResponse(
    com.zigzag.common.model.Generation generation,
    boolean bandwidth_alert)
  {
    this();
    this.generation = generation;
    this.bandwidth_alert = bandwidth_alert;
    setBandwidth_alertIsSet(true);
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NextGenResponse(NextGenResponse other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetGeneration()) {
      this.generation = new com.zigzag.common.model.Generation(other.generation);
    }
    this.bandwidth_alert = other.bandwidth_alert;
  }

  public NextGenResponse deepCopy() {
    return new NextGenResponse(this);
  }

  @Override
  public void clear() {
    this.generation = null;
    setBandwidth_alertIsSet(false);
    this.bandwidth_alert = false;
  }

  public com.zigzag.common.model.Generation getGeneration() {
    return this.generation;
  }

  public NextGenResponse setGeneration(com.zigzag.common.model.Generation generation) {
    this.generation = generation;
    return this;
  }

  public void unsetGeneration() {
    this.generation = null;
  }

  /** Returns true if field generation is set (has been assigned a value) and false otherwise */
  public boolean isSetGeneration() {
    return this.generation != null;
  }

  public void setGenerationIsSet(boolean value) {
    if (!value) {
      this.generation = null;
    }
  }

  public boolean isBandwidth_alert() {
    return this.bandwidth_alert;
  }

  public NextGenResponse setBandwidth_alert(boolean bandwidth_alert) {
    this.bandwidth_alert = bandwidth_alert;
    setBandwidth_alertIsSet(true);
    return this;
  }

  public void unsetBandwidth_alert() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __BANDWIDTH_ALERT_ISSET_ID);
  }

  /** Returns true if field bandwidth_alert is set (has been assigned a value) and false otherwise */
  public boolean isSetBandwidth_alert() {
    return EncodingUtils.testBit(__isset_bitfield, __BANDWIDTH_ALERT_ISSET_ID);
  }

  public void setBandwidth_alertIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __BANDWIDTH_ALERT_ISSET_ID, value);
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case GENERATION:
      if (value == null) {
        unsetGeneration();
      } else {
        setGeneration((com.zigzag.common.model.Generation)value);
      }
      break;

    case BANDWIDTH_ALERT:
      if (value == null) {
        unsetBandwidth_alert();
      } else {
        setBandwidth_alert((Boolean)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GENERATION:
      return getGeneration();

    case BANDWIDTH_ALERT:
      return Boolean.valueOf(isBandwidth_alert());

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case GENERATION:
      return isSetGeneration();
    case BANDWIDTH_ALERT:
      return isSetBandwidth_alert();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof NextGenResponse)
      return this.equals((NextGenResponse)that);
    return false;
  }

  public boolean equals(NextGenResponse that) {
    if (that == null)
      return false;

    boolean this_present_generation = true && this.isSetGeneration();
    boolean that_present_generation = true && that.isSetGeneration();
    if (this_present_generation || that_present_generation) {
      if (!(this_present_generation && that_present_generation))
        return false;
      if (!this.generation.equals(that.generation))
        return false;
    }

    boolean this_present_bandwidth_alert = true;
    boolean that_present_bandwidth_alert = true;
    if (this_present_bandwidth_alert || that_present_bandwidth_alert) {
      if (!(this_present_bandwidth_alert && that_present_bandwidth_alert))
        return false;
      if (this.bandwidth_alert != that.bandwidth_alert)
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_generation = true && (isSetGeneration());
    list.add(present_generation);
    if (present_generation)
      list.add(generation);

    boolean present_bandwidth_alert = true;
    list.add(present_bandwidth_alert);
    if (present_bandwidth_alert)
      list.add(bandwidth_alert);

    return list.hashCode();
  }

  @Override
  public int compareTo(NextGenResponse other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetGeneration()).compareTo(other.isSetGeneration());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetGeneration()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.generation, other.generation);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetBandwidth_alert()).compareTo(other.isSetBandwidth_alert());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetBandwidth_alert()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.bandwidth_alert, other.bandwidth_alert);
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
    StringBuilder sb = new StringBuilder("NextGenResponse(");
    boolean first = true;

    sb.append("generation:");
    if (this.generation == null) {
      sb.append("null");
    } else {
      sb.append(this.generation);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("bandwidth_alert:");
    sb.append(this.bandwidth_alert);
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (generation == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'generation' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'bandwidth_alert' because it's a primitive and you chose the non-beans generator.
    // check for sub-struct validity
    if (generation != null) {
      generation.validate();
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

  private static class NextGenResponseStandardSchemeFactory implements SchemeFactory {
    public NextGenResponseStandardScheme getScheme() {
      return new NextGenResponseStandardScheme();
    }
  }

  private static class NextGenResponseStandardScheme extends StandardScheme<NextGenResponse> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, NextGenResponse struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // GENERATION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.generation = new com.zigzag.common.model.Generation();
              struct.generation.read(iprot);
              struct.setGenerationIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // BANDWIDTH_ALERT
            if (schemeField.type == org.apache.thrift.protocol.TType.BOOL) {
              struct.bandwidth_alert = iprot.readBool();
              struct.setBandwidth_alertIsSet(true);
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
      if (!struct.isSetBandwidth_alert()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'bandwidth_alert' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, NextGenResponse struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.generation != null) {
        oprot.writeFieldBegin(GENERATION_FIELD_DESC);
        struct.generation.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(BANDWIDTH_ALERT_FIELD_DESC);
      oprot.writeBool(struct.bandwidth_alert);
      oprot.writeFieldEnd();
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class NextGenResponseTupleSchemeFactory implements SchemeFactory {
    public NextGenResponseTupleScheme getScheme() {
      return new NextGenResponseTupleScheme();
    }
  }

  private static class NextGenResponseTupleScheme extends TupleScheme<NextGenResponse> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, NextGenResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      struct.generation.write(oprot);
      oprot.writeBool(struct.bandwidth_alert);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NextGenResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.generation = new com.zigzag.common.model.Generation();
      struct.generation.read(iprot);
      struct.setGenerationIsSet(true);
      struct.bandwidth_alert = iprot.readBool();
      struct.setBandwidth_alertIsSet(true);
    }
  }

}

