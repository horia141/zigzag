/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zigzag.log_analyzer.protos;

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
public class AnalysisResult implements org.apache.thrift.TBase<AnalysisResult, AnalysisResult._Fields>, java.io.Serializable, Cloneable, Comparable<AnalysisResult> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AnalysisResult");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DATETIME_RAN_TS_FIELD_DESC = new org.apache.thrift.protocol.TField("datetime_ran_ts", org.apache.thrift.protocol.TType.I64, (short)2);
  private static final org.apache.thrift.protocol.TField MONTH_FIELD_DESC = new org.apache.thrift.protocol.TField("month", org.apache.thrift.protocol.TType.STRUCT, (short)3);
  private static final org.apache.thrift.protocol.TField DAY_FIELD_DESC = new org.apache.thrift.protocol.TField("day", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AnalysisResultStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AnalysisResultTupleSchemeFactory());
  }

  public long id; // required
  public long datetime_ran_ts; // required
  public SystemConsumption month; // required
  public SystemConsumption day; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    DATETIME_RAN_TS((short)2, "datetime_ran_ts"),
    MONTH((short)3, "month"),
    DAY((short)4, "day");

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
        case 1: // ID
          return ID;
        case 2: // DATETIME_RAN_TS
          return DATETIME_RAN_TS;
        case 3: // MONTH
          return MONTH;
        case 4: // DAY
          return DAY;
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
  private static final int __ID_ISSET_ID = 0;
  private static final int __DATETIME_RAN_TS_ISSET_ID = 1;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.DATETIME_RAN_TS, new org.apache.thrift.meta_data.FieldMetaData("datetime_ran_ts", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64)));
    tmpMap.put(_Fields.MONTH, new org.apache.thrift.meta_data.FieldMetaData("month", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SystemConsumption.class)));
    tmpMap.put(_Fields.DAY, new org.apache.thrift.meta_data.FieldMetaData("day", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, SystemConsumption.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AnalysisResult.class, metaDataMap);
  }

  public AnalysisResult() {
  }

  public AnalysisResult(
    long id,
    long datetime_ran_ts,
    SystemConsumption month,
    SystemConsumption day)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.datetime_ran_ts = datetime_ran_ts;
    setDatetime_ran_tsIsSet(true);
    this.month = month;
    this.day = day;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AnalysisResult(AnalysisResult other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.datetime_ran_ts = other.datetime_ran_ts;
    if (other.isSetMonth()) {
      this.month = new SystemConsumption(other.month);
    }
    if (other.isSetDay()) {
      this.day = new SystemConsumption(other.day);
    }
  }

  public AnalysisResult deepCopy() {
    return new AnalysisResult(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setDatetime_ran_tsIsSet(false);
    this.datetime_ran_ts = 0;
    this.month = null;
    this.day = null;
  }

  public long getId() {
    return this.id;
  }

  public AnalysisResult setId(long id) {
    this.id = id;
    setIdIsSet(true);
    return this;
  }

  public void unsetId() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ID_ISSET_ID);
  }

  /** Returns true if field id is set (has been assigned a value) and false otherwise */
  public boolean isSetId() {
    return EncodingUtils.testBit(__isset_bitfield, __ID_ISSET_ID);
  }

  public void setIdIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ID_ISSET_ID, value);
  }

  public long getDatetime_ran_ts() {
    return this.datetime_ran_ts;
  }

  public AnalysisResult setDatetime_ran_ts(long datetime_ran_ts) {
    this.datetime_ran_ts = datetime_ran_ts;
    setDatetime_ran_tsIsSet(true);
    return this;
  }

  public void unsetDatetime_ran_ts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATETIME_RAN_TS_ISSET_ID);
  }

  /** Returns true if field datetime_ran_ts is set (has been assigned a value) and false otherwise */
  public boolean isSetDatetime_ran_ts() {
    return EncodingUtils.testBit(__isset_bitfield, __DATETIME_RAN_TS_ISSET_ID);
  }

  public void setDatetime_ran_tsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATETIME_RAN_TS_ISSET_ID, value);
  }

  public SystemConsumption getMonth() {
    return this.month;
  }

  public AnalysisResult setMonth(SystemConsumption month) {
    this.month = month;
    return this;
  }

  public void unsetMonth() {
    this.month = null;
  }

  /** Returns true if field month is set (has been assigned a value) and false otherwise */
  public boolean isSetMonth() {
    return this.month != null;
  }

  public void setMonthIsSet(boolean value) {
    if (!value) {
      this.month = null;
    }
  }

  public SystemConsumption getDay() {
    return this.day;
  }

  public AnalysisResult setDay(SystemConsumption day) {
    this.day = day;
    return this;
  }

  public void unsetDay() {
    this.day = null;
  }

  /** Returns true if field day is set (has been assigned a value) and false otherwise */
  public boolean isSetDay() {
    return this.day != null;
  }

  public void setDayIsSet(boolean value) {
    if (!value) {
      this.day = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case ID:
      if (value == null) {
        unsetId();
      } else {
        setId((Long)value);
      }
      break;

    case DATETIME_RAN_TS:
      if (value == null) {
        unsetDatetime_ran_ts();
      } else {
        setDatetime_ran_ts((Long)value);
      }
      break;

    case MONTH:
      if (value == null) {
        unsetMonth();
      } else {
        setMonth((SystemConsumption)value);
      }
      break;

    case DAY:
      if (value == null) {
        unsetDay();
      } else {
        setDay((SystemConsumption)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case DATETIME_RAN_TS:
      return Long.valueOf(getDatetime_ran_ts());

    case MONTH:
      return getMonth();

    case DAY:
      return getDay();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case ID:
      return isSetId();
    case DATETIME_RAN_TS:
      return isSetDatetime_ran_ts();
    case MONTH:
      return isSetMonth();
    case DAY:
      return isSetDay();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AnalysisResult)
      return this.equals((AnalysisResult)that);
    return false;
  }

  public boolean equals(AnalysisResult that) {
    if (that == null)
      return false;

    boolean this_present_id = true;
    boolean that_present_id = true;
    if (this_present_id || that_present_id) {
      if (!(this_present_id && that_present_id))
        return false;
      if (this.id != that.id)
        return false;
    }

    boolean this_present_datetime_ran_ts = true;
    boolean that_present_datetime_ran_ts = true;
    if (this_present_datetime_ran_ts || that_present_datetime_ran_ts) {
      if (!(this_present_datetime_ran_ts && that_present_datetime_ran_ts))
        return false;
      if (this.datetime_ran_ts != that.datetime_ran_ts)
        return false;
    }

    boolean this_present_month = true && this.isSetMonth();
    boolean that_present_month = true && that.isSetMonth();
    if (this_present_month || that_present_month) {
      if (!(this_present_month && that_present_month))
        return false;
      if (!this.month.equals(that.month))
        return false;
    }

    boolean this_present_day = true && this.isSetDay();
    boolean that_present_day = true && that.isSetDay();
    if (this_present_day || that_present_day) {
      if (!(this_present_day && that_present_day))
        return false;
      if (!this.day.equals(that.day))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_id = true;
    list.add(present_id);
    if (present_id)
      list.add(id);

    boolean present_datetime_ran_ts = true;
    list.add(present_datetime_ran_ts);
    if (present_datetime_ran_ts)
      list.add(datetime_ran_ts);

    boolean present_month = true && (isSetMonth());
    list.add(present_month);
    if (present_month)
      list.add(month);

    boolean present_day = true && (isSetDay());
    list.add(present_day);
    if (present_day)
      list.add(day);

    return list.hashCode();
  }

  @Override
  public int compareTo(AnalysisResult other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetId()).compareTo(other.isSetId());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetId()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.id, other.id);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDatetime_ran_ts()).compareTo(other.isSetDatetime_ran_ts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDatetime_ran_ts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.datetime_ran_ts, other.datetime_ran_ts);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetMonth()).compareTo(other.isSetMonth());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetMonth()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.month, other.month);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDay()).compareTo(other.isSetDay());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDay()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.day, other.day);
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
    StringBuilder sb = new StringBuilder("AnalysisResult(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("datetime_ran_ts:");
    sb.append(this.datetime_ran_ts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("month:");
    if (this.month == null) {
      sb.append("null");
    } else {
      sb.append(this.month);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("day:");
    if (this.day == null) {
      sb.append("null");
    } else {
      sb.append(this.day);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'datetime_ran_ts' because it's a primitive and you chose the non-beans generator.
    if (month == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'month' was not present! Struct: " + toString());
    }
    if (day == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'day' was not present! Struct: " + toString());
    }
    // check for sub-struct validity
    if (month != null) {
      month.validate();
    }
    if (day != null) {
      day.validate();
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

  private static class AnalysisResultStandardSchemeFactory implements SchemeFactory {
    public AnalysisResultStandardScheme getScheme() {
      return new AnalysisResultStandardScheme();
    }
  }

  private static class AnalysisResultStandardScheme extends StandardScheme<AnalysisResult> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AnalysisResult struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // ID
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.id = iprot.readI64();
              struct.setIdIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DATETIME_RAN_TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.datetime_ran_ts = iprot.readI64();
              struct.setDatetime_ran_tsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // MONTH
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.month = new SystemConsumption();
              struct.month.read(iprot);
              struct.setMonthIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // DAY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.day = new SystemConsumption();
              struct.day.read(iprot);
              struct.setDayIsSet(true);
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
      if (!struct.isSetId()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'id' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetDatetime_ran_ts()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'datetime_ran_ts' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, AnalysisResult struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DATETIME_RAN_TS_FIELD_DESC);
      oprot.writeI64(struct.datetime_ran_ts);
      oprot.writeFieldEnd();
      if (struct.month != null) {
        oprot.writeFieldBegin(MONTH_FIELD_DESC);
        struct.month.write(oprot);
        oprot.writeFieldEnd();
      }
      if (struct.day != null) {
        oprot.writeFieldBegin(DAY_FIELD_DESC);
        struct.day.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AnalysisResultTupleSchemeFactory implements SchemeFactory {
    public AnalysisResultTupleScheme getScheme() {
      return new AnalysisResultTupleScheme();
    }
  }

  private static class AnalysisResultTupleScheme extends TupleScheme<AnalysisResult> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AnalysisResult struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.id);
      oprot.writeI64(struct.datetime_ran_ts);
      struct.month.write(oprot);
      struct.day.write(oprot);
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AnalysisResult struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI64();
      struct.setIdIsSet(true);
      struct.datetime_ran_ts = iprot.readI64();
      struct.setDatetime_ran_tsIsSet(true);
      struct.month = new SystemConsumption();
      struct.month.read(iprot);
      struct.setMonthIsSet(true);
      struct.day = new SystemConsumption();
      struct.day.read(iprot);
      struct.setDayIsSet(true);
    }
  }

}

