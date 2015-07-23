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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-7-23")
public class PhotoDescription implements org.apache.thrift.TBase<PhotoDescription, PhotoDescription._Fields>, java.io.Serializable, Cloneable, Comparable<PhotoDescription> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PhotoDescription");

  private static final org.apache.thrift.protocol.TField SUBTITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("subtitle", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField DESCRIPTION_FIELD_DESC = new org.apache.thrift.protocol.TField("description", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField SOURCE_URI_FIELD_DESC = new org.apache.thrift.protocol.TField("source_uri", org.apache.thrift.protocol.TType.STRING, (short)3);
  private static final org.apache.thrift.protocol.TField PHOTO_DATA_FIELD_DESC = new org.apache.thrift.protocol.TField("photo_data", org.apache.thrift.protocol.TType.STRUCT, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new PhotoDescriptionStandardSchemeFactory());
    schemes.put(TupleScheme.class, new PhotoDescriptionTupleSchemeFactory());
  }

  public String subtitle; // optional
  public String description; // optional
  public String source_uri; // required
  public PhotoData photo_data; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    SUBTITLE((short)1, "subtitle"),
    DESCRIPTION((short)2, "description"),
    SOURCE_URI((short)3, "source_uri"),
    PHOTO_DATA((short)4, "photo_data");

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
        case 1: // SUBTITLE
          return SUBTITLE;
        case 2: // DESCRIPTION
          return DESCRIPTION;
        case 3: // SOURCE_URI
          return SOURCE_URI;
        case 4: // PHOTO_DATA
          return PHOTO_DATA;
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
  private static final _Fields optionals[] = {_Fields.SUBTITLE,_Fields.DESCRIPTION};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.SUBTITLE, new org.apache.thrift.meta_data.FieldMetaData("subtitle", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.DESCRIPTION, new org.apache.thrift.meta_data.FieldMetaData("description", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SOURCE_URI, new org.apache.thrift.meta_data.FieldMetaData("source_uri", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.PHOTO_DATA, new org.apache.thrift.meta_data.FieldMetaData("photo_data", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PhotoData.class)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PhotoDescription.class, metaDataMap);
  }

  public PhotoDescription() {
  }

  public PhotoDescription(
    String source_uri,
    PhotoData photo_data)
  {
    this();
    this.source_uri = source_uri;
    this.photo_data = photo_data;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public PhotoDescription(PhotoDescription other) {
    if (other.isSetSubtitle()) {
      this.subtitle = other.subtitle;
    }
    if (other.isSetDescription()) {
      this.description = other.description;
    }
    if (other.isSetSource_uri()) {
      this.source_uri = other.source_uri;
    }
    if (other.isSetPhoto_data()) {
      this.photo_data = new PhotoData(other.photo_data);
    }
  }

  public PhotoDescription deepCopy() {
    return new PhotoDescription(this);
  }

  @Override
  public void clear() {
    this.subtitle = null;
    this.description = null;
    this.source_uri = null;
    this.photo_data = null;
  }

  public String getSubtitle() {
    return this.subtitle;
  }

  public PhotoDescription setSubtitle(String subtitle) {
    this.subtitle = subtitle;
    return this;
  }

  public void unsetSubtitle() {
    this.subtitle = null;
  }

  /** Returns true if field subtitle is set (has been assigned a value) and false otherwise */
  public boolean isSetSubtitle() {
    return this.subtitle != null;
  }

  public void setSubtitleIsSet(boolean value) {
    if (!value) {
      this.subtitle = null;
    }
  }

  public String getDescription() {
    return this.description;
  }

  public PhotoDescription setDescription(String description) {
    this.description = description;
    return this;
  }

  public void unsetDescription() {
    this.description = null;
  }

  /** Returns true if field description is set (has been assigned a value) and false otherwise */
  public boolean isSetDescription() {
    return this.description != null;
  }

  public void setDescriptionIsSet(boolean value) {
    if (!value) {
      this.description = null;
    }
  }

  public String getSource_uri() {
    return this.source_uri;
  }

  public PhotoDescription setSource_uri(String source_uri) {
    this.source_uri = source_uri;
    return this;
  }

  public void unsetSource_uri() {
    this.source_uri = null;
  }

  /** Returns true if field source_uri is set (has been assigned a value) and false otherwise */
  public boolean isSetSource_uri() {
    return this.source_uri != null;
  }

  public void setSource_uriIsSet(boolean value) {
    if (!value) {
      this.source_uri = null;
    }
  }

  public PhotoData getPhoto_data() {
    return this.photo_data;
  }

  public PhotoDescription setPhoto_data(PhotoData photo_data) {
    this.photo_data = photo_data;
    return this;
  }

  public void unsetPhoto_data() {
    this.photo_data = null;
  }

  /** Returns true if field photo_data is set (has been assigned a value) and false otherwise */
  public boolean isSetPhoto_data() {
    return this.photo_data != null;
  }

  public void setPhoto_dataIsSet(boolean value) {
    if (!value) {
      this.photo_data = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case SUBTITLE:
      if (value == null) {
        unsetSubtitle();
      } else {
        setSubtitle((String)value);
      }
      break;

    case DESCRIPTION:
      if (value == null) {
        unsetDescription();
      } else {
        setDescription((String)value);
      }
      break;

    case SOURCE_URI:
      if (value == null) {
        unsetSource_uri();
      } else {
        setSource_uri((String)value);
      }
      break;

    case PHOTO_DATA:
      if (value == null) {
        unsetPhoto_data();
      } else {
        setPhoto_data((PhotoData)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case SUBTITLE:
      return getSubtitle();

    case DESCRIPTION:
      return getDescription();

    case SOURCE_URI:
      return getSource_uri();

    case PHOTO_DATA:
      return getPhoto_data();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case SUBTITLE:
      return isSetSubtitle();
    case DESCRIPTION:
      return isSetDescription();
    case SOURCE_URI:
      return isSetSource_uri();
    case PHOTO_DATA:
      return isSetPhoto_data();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof PhotoDescription)
      return this.equals((PhotoDescription)that);
    return false;
  }

  public boolean equals(PhotoDescription that) {
    if (that == null)
      return false;

    boolean this_present_subtitle = true && this.isSetSubtitle();
    boolean that_present_subtitle = true && that.isSetSubtitle();
    if (this_present_subtitle || that_present_subtitle) {
      if (!(this_present_subtitle && that_present_subtitle))
        return false;
      if (!this.subtitle.equals(that.subtitle))
        return false;
    }

    boolean this_present_description = true && this.isSetDescription();
    boolean that_present_description = true && that.isSetDescription();
    if (this_present_description || that_present_description) {
      if (!(this_present_description && that_present_description))
        return false;
      if (!this.description.equals(that.description))
        return false;
    }

    boolean this_present_source_uri = true && this.isSetSource_uri();
    boolean that_present_source_uri = true && that.isSetSource_uri();
    if (this_present_source_uri || that_present_source_uri) {
      if (!(this_present_source_uri && that_present_source_uri))
        return false;
      if (!this.source_uri.equals(that.source_uri))
        return false;
    }

    boolean this_present_photo_data = true && this.isSetPhoto_data();
    boolean that_present_photo_data = true && that.isSetPhoto_data();
    if (this_present_photo_data || that_present_photo_data) {
      if (!(this_present_photo_data && that_present_photo_data))
        return false;
      if (!this.photo_data.equals(that.photo_data))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_subtitle = true && (isSetSubtitle());
    list.add(present_subtitle);
    if (present_subtitle)
      list.add(subtitle);

    boolean present_description = true && (isSetDescription());
    list.add(present_description);
    if (present_description)
      list.add(description);

    boolean present_source_uri = true && (isSetSource_uri());
    list.add(present_source_uri);
    if (present_source_uri)
      list.add(source_uri);

    boolean present_photo_data = true && (isSetPhoto_data());
    list.add(present_photo_data);
    if (present_photo_data)
      list.add(photo_data);

    return list.hashCode();
  }

  @Override
  public int compareTo(PhotoDescription other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetSubtitle()).compareTo(other.isSetSubtitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSubtitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.subtitle, other.subtitle);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDescription()).compareTo(other.isSetDescription());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDescription()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.description, other.description);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSource_uri()).compareTo(other.isSetSource_uri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSource_uri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.source_uri, other.source_uri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhoto_data()).compareTo(other.isSetPhoto_data());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhoto_data()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.photo_data, other.photo_data);
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
    StringBuilder sb = new StringBuilder("PhotoDescription(");
    boolean first = true;

    if (isSetSubtitle()) {
      sb.append("subtitle:");
      if (this.subtitle == null) {
        sb.append("null");
      } else {
        sb.append(this.subtitle);
      }
      first = false;
    }
    if (isSetDescription()) {
      if (!first) sb.append(", ");
      sb.append("description:");
      if (this.description == null) {
        sb.append("null");
      } else {
        sb.append(this.description);
      }
      first = false;
    }
    if (!first) sb.append(", ");
    sb.append("source_uri:");
    if (this.source_uri == null) {
      sb.append("null");
    } else {
      sb.append(this.source_uri);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("photo_data:");
    if (this.photo_data == null) {
      sb.append("null");
    } else {
      sb.append(this.photo_data);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (source_uri == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'source_uri' was not present! Struct: " + toString());
    }
    if (photo_data == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'photo_data' was not present! Struct: " + toString());
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
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class PhotoDescriptionStandardSchemeFactory implements SchemeFactory {
    public PhotoDescriptionStandardScheme getScheme() {
      return new PhotoDescriptionStandardScheme();
    }
  }

  private static class PhotoDescriptionStandardScheme extends StandardScheme<PhotoDescription> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, PhotoDescription struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // SUBTITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.subtitle = iprot.readString();
              struct.setSubtitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // DESCRIPTION
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.description = iprot.readString();
              struct.setDescriptionIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // SOURCE_URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.source_uri = iprot.readString();
              struct.setSource_uriIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PHOTO_DATA
            if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
              struct.photo_data = new PhotoData();
              struct.photo_data.read(iprot);
              struct.setPhoto_dataIsSet(true);
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
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, PhotoDescription struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.subtitle != null) {
        if (struct.isSetSubtitle()) {
          oprot.writeFieldBegin(SUBTITLE_FIELD_DESC);
          oprot.writeString(struct.subtitle);
          oprot.writeFieldEnd();
        }
      }
      if (struct.description != null) {
        if (struct.isSetDescription()) {
          oprot.writeFieldBegin(DESCRIPTION_FIELD_DESC);
          oprot.writeString(struct.description);
          oprot.writeFieldEnd();
        }
      }
      if (struct.source_uri != null) {
        oprot.writeFieldBegin(SOURCE_URI_FIELD_DESC);
        oprot.writeString(struct.source_uri);
        oprot.writeFieldEnd();
      }
      if (struct.photo_data != null) {
        oprot.writeFieldBegin(PHOTO_DATA_FIELD_DESC);
        struct.photo_data.write(oprot);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class PhotoDescriptionTupleSchemeFactory implements SchemeFactory {
    public PhotoDescriptionTupleScheme getScheme() {
      return new PhotoDescriptionTupleScheme();
    }
  }

  private static class PhotoDescriptionTupleScheme extends TupleScheme<PhotoDescription> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, PhotoDescription struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.source_uri);
      struct.photo_data.write(oprot);
      BitSet optionals = new BitSet();
      if (struct.isSetSubtitle()) {
        optionals.set(0);
      }
      if (struct.isSetDescription()) {
        optionals.set(1);
      }
      oprot.writeBitSet(optionals, 2);
      if (struct.isSetSubtitle()) {
        oprot.writeString(struct.subtitle);
      }
      if (struct.isSetDescription()) {
        oprot.writeString(struct.description);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, PhotoDescription struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.source_uri = iprot.readString();
      struct.setSource_uriIsSet(true);
      struct.photo_data = new PhotoData();
      struct.photo_data.read(iprot);
      struct.setPhoto_dataIsSet(true);
      BitSet incoming = iprot.readBitSet(2);
      if (incoming.get(0)) {
        struct.subtitle = iprot.readString();
        struct.setSubtitleIsSet(true);
      }
      if (incoming.get(1)) {
        struct.description = iprot.readString();
        struct.setDescriptionIsSet(true);
      }
    }
  }

}

