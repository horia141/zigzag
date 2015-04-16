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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-4-16")
public class ArtifactSource implements org.apache.thrift.TBase<ArtifactSource, ArtifactSource._Fields>, java.io.Serializable, Cloneable, Comparable<ArtifactSource> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ArtifactSource");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("name", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField START_PAGE_URI_FIELD_DESC = new org.apache.thrift.protocol.TField("start_page_uri", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField SUBDOMAINS_FIELD_DESC = new org.apache.thrift.protocol.TField("subdomains", org.apache.thrift.protocol.TType.SET, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ArtifactSourceStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ArtifactSourceTupleSchemeFactory());
  }

  public long id; // required
  public String name; // required
  public String start_page_uri; // required
  public Set<String> subdomains; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    NAME((short)2, "name"),
    START_PAGE_URI((short)3, "start_page_uri"),
    SUBDOMAINS((short)4, "subdomains");

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
        case 2: // NAME
          return NAME;
        case 3: // START_PAGE_URI
          return START_PAGE_URI;
        case 4: // SUBDOMAINS
          return SUBDOMAINS;
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
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.SUBDOMAINS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "EntityId")));
    tmpMap.put(_Fields.NAME, new org.apache.thrift.meta_data.FieldMetaData("name", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.START_PAGE_URI, new org.apache.thrift.meta_data.FieldMetaData("start_page_uri", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SUBDOMAINS, new org.apache.thrift.meta_data.FieldMetaData("subdomains", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ArtifactSource.class, metaDataMap);
  }

  public ArtifactSource() {
  }

  public ArtifactSource(
    long id,
    String name,
    String start_page_uri)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.name = name;
    this.start_page_uri = start_page_uri;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ArtifactSource(ArtifactSource other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    if (other.isSetName()) {
      this.name = other.name;
    }
    if (other.isSetStart_page_uri()) {
      this.start_page_uri = other.start_page_uri;
    }
    if (other.isSetSubdomains()) {
      Set<String> __this__subdomains = new HashSet<String>(other.subdomains);
      this.subdomains = __this__subdomains;
    }
  }

  public ArtifactSource deepCopy() {
    return new ArtifactSource(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    this.name = null;
    this.start_page_uri = null;
    this.subdomains = null;
  }

  public long getId() {
    return this.id;
  }

  public ArtifactSource setId(long id) {
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

  public String getName() {
    return this.name;
  }

  public ArtifactSource setName(String name) {
    this.name = name;
    return this;
  }

  public void unsetName() {
    this.name = null;
  }

  /** Returns true if field name is set (has been assigned a value) and false otherwise */
  public boolean isSetName() {
    return this.name != null;
  }

  public void setNameIsSet(boolean value) {
    if (!value) {
      this.name = null;
    }
  }

  public String getStart_page_uri() {
    return this.start_page_uri;
  }

  public ArtifactSource setStart_page_uri(String start_page_uri) {
    this.start_page_uri = start_page_uri;
    return this;
  }

  public void unsetStart_page_uri() {
    this.start_page_uri = null;
  }

  /** Returns true if field start_page_uri is set (has been assigned a value) and false otherwise */
  public boolean isSetStart_page_uri() {
    return this.start_page_uri != null;
  }

  public void setStart_page_uriIsSet(boolean value) {
    if (!value) {
      this.start_page_uri = null;
    }
  }

  public int getSubdomainsSize() {
    return (this.subdomains == null) ? 0 : this.subdomains.size();
  }

  public java.util.Iterator<String> getSubdomainsIterator() {
    return (this.subdomains == null) ? null : this.subdomains.iterator();
  }

  public void addToSubdomains(String elem) {
    if (this.subdomains == null) {
      this.subdomains = new HashSet<String>();
    }
    this.subdomains.add(elem);
  }

  public Set<String> getSubdomains() {
    return this.subdomains;
  }

  public ArtifactSource setSubdomains(Set<String> subdomains) {
    this.subdomains = subdomains;
    return this;
  }

  public void unsetSubdomains() {
    this.subdomains = null;
  }

  /** Returns true if field subdomains is set (has been assigned a value) and false otherwise */
  public boolean isSetSubdomains() {
    return this.subdomains != null;
  }

  public void setSubdomainsIsSet(boolean value) {
    if (!value) {
      this.subdomains = null;
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

    case NAME:
      if (value == null) {
        unsetName();
      } else {
        setName((String)value);
      }
      break;

    case START_PAGE_URI:
      if (value == null) {
        unsetStart_page_uri();
      } else {
        setStart_page_uri((String)value);
      }
      break;

    case SUBDOMAINS:
      if (value == null) {
        unsetSubdomains();
      } else {
        setSubdomains((Set<String>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case NAME:
      return getName();

    case START_PAGE_URI:
      return getStart_page_uri();

    case SUBDOMAINS:
      return getSubdomains();

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
    case NAME:
      return isSetName();
    case START_PAGE_URI:
      return isSetStart_page_uri();
    case SUBDOMAINS:
      return isSetSubdomains();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ArtifactSource)
      return this.equals((ArtifactSource)that);
    return false;
  }

  public boolean equals(ArtifactSource that) {
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

    boolean this_present_name = true && this.isSetName();
    boolean that_present_name = true && that.isSetName();
    if (this_present_name || that_present_name) {
      if (!(this_present_name && that_present_name))
        return false;
      if (!this.name.equals(that.name))
        return false;
    }

    boolean this_present_start_page_uri = true && this.isSetStart_page_uri();
    boolean that_present_start_page_uri = true && that.isSetStart_page_uri();
    if (this_present_start_page_uri || that_present_start_page_uri) {
      if (!(this_present_start_page_uri && that_present_start_page_uri))
        return false;
      if (!this.start_page_uri.equals(that.start_page_uri))
        return false;
    }

    boolean this_present_subdomains = true && this.isSetSubdomains();
    boolean that_present_subdomains = true && that.isSetSubdomains();
    if (this_present_subdomains || that_present_subdomains) {
      if (!(this_present_subdomains && that_present_subdomains))
        return false;
      if (!this.subdomains.equals(that.subdomains))
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

    boolean present_name = true && (isSetName());
    list.add(present_name);
    if (present_name)
      list.add(name);

    boolean present_start_page_uri = true && (isSetStart_page_uri());
    list.add(present_start_page_uri);
    if (present_start_page_uri)
      list.add(start_page_uri);

    boolean present_subdomains = true && (isSetSubdomains());
    list.add(present_subdomains);
    if (present_subdomains)
      list.add(subdomains);

    return list.hashCode();
  }

  @Override
  public int compareTo(ArtifactSource other) {
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
    lastComparison = Boolean.valueOf(isSetName()).compareTo(other.isSetName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.name, other.name);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetStart_page_uri()).compareTo(other.isSetStart_page_uri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetStart_page_uri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.start_page_uri, other.start_page_uri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSubdomains()).compareTo(other.isSetSubdomains());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubdomains()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subdomains, other.subdomains);
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
    StringBuilder sb = new StringBuilder("ArtifactSource(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("name:");
    if (this.name == null) {
      sb.append("null");
    } else {
      sb.append(this.name);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("start_page_uri:");
    if (this.start_page_uri == null) {
      sb.append("null");
    } else {
      sb.append(this.start_page_uri);
    }
    first = false;
    if (isSetSubdomains()) {
      if (!first) sb.append(", ");
      sb.append("subdomains:");
      if (this.subdomains == null) {
        sb.append("null");
      } else {
        sb.append(this.subdomains);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    if (name == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'name' was not present! Struct: " + toString());
    }
    if (start_page_uri == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'start_page_uri' was not present! Struct: " + toString());
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

  private static class ArtifactSourceStandardSchemeFactory implements SchemeFactory {
    public ArtifactSourceStandardScheme getScheme() {
      return new ArtifactSourceStandardScheme();
    }
  }

  private static class ArtifactSourceStandardScheme extends StandardScheme<ArtifactSource> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ArtifactSource struct) throws org.apache.thrift.TException {
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
          case 2: // NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.name = iprot.readString();
              struct.setNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // START_PAGE_URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.start_page_uri = iprot.readString();
              struct.setStart_page_uriIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // SUBDOMAINS
            if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
              {
                org.apache.thrift.protocol.TSet _set18 = iprot.readSetBegin();
                struct.subdomains = new HashSet<String>(2*_set18.size);
                String _elem19;
                for (int _i20 = 0; _i20 < _set18.size; ++_i20)
                {
                  _elem19 = iprot.readString();
                  struct.subdomains.add(_elem19);
                }
                iprot.readSetEnd();
              }
              struct.setSubdomainsIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, ArtifactSource struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      if (struct.name != null) {
        oprot.writeFieldBegin(NAME_FIELD_DESC);
        oprot.writeString(struct.name);
        oprot.writeFieldEnd();
      }
      if (struct.start_page_uri != null) {
        oprot.writeFieldBegin(START_PAGE_URI_FIELD_DESC);
        oprot.writeString(struct.start_page_uri);
        oprot.writeFieldEnd();
      }
      if (struct.subdomains != null) {
        if (struct.isSetSubdomains()) {
          oprot.writeFieldBegin(SUBDOMAINS_FIELD_DESC);
          {
            oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.subdomains.size()));
            for (String _iter21 : struct.subdomains)
            {
              oprot.writeString(_iter21);
            }
            oprot.writeSetEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ArtifactSourceTupleSchemeFactory implements SchemeFactory {
    public ArtifactSourceTupleScheme getScheme() {
      return new ArtifactSourceTupleScheme();
    }
  }

  private static class ArtifactSourceTupleScheme extends TupleScheme<ArtifactSource> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ArtifactSource struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.id);
      oprot.writeString(struct.name);
      oprot.writeString(struct.start_page_uri);
      BitSet optionals = new BitSet();
      if (struct.isSetSubdomains()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetSubdomains()) {
        {
          oprot.writeI32(struct.subdomains.size());
          for (String _iter22 : struct.subdomains)
          {
            oprot.writeString(_iter22);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ArtifactSource struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI64();
      struct.setIdIsSet(true);
      struct.name = iprot.readString();
      struct.setNameIsSet(true);
      struct.start_page_uri = iprot.readString();
      struct.setStart_page_uriIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TSet _set23 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
          struct.subdomains = new HashSet<String>(2*_set23.size);
          String _elem24;
          for (int _i25 = 0; _i25 < _set23.size; ++_i25)
          {
            _elem24 = iprot.readString();
            struct.subdomains.add(_elem24);
          }
        }
        struct.setSubdomainsIsSet(true);
      }
    }
  }

}

