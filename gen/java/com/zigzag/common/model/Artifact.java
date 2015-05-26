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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-5-26")
public class Artifact implements org.apache.thrift.TBase<Artifact, Artifact._Fields>, java.io.Serializable, Cloneable, Comparable<Artifact> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Artifact");

  private static final org.apache.thrift.protocol.TField PAGE_URI_FIELD_DESC = new org.apache.thrift.protocol.TField("page_uri", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TITLE_FIELD_DESC = new org.apache.thrift.protocol.TField("title", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField ARTIFACT_SOURCE_PK_FIELD_DESC = new org.apache.thrift.protocol.TField("artifact_source_pk", org.apache.thrift.protocol.TType.I64, (short)3);
  private static final org.apache.thrift.protocol.TField PHOTO_DESCRIPTIONS_FIELD_DESC = new org.apache.thrift.protocol.TField("photo_descriptions", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ArtifactStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ArtifactTupleSchemeFactory());
  }

  public String page_uri; // required
  public String title; // required
  public long artifact_source_pk; // required
  public List<PhotoDescription> photo_descriptions; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    PAGE_URI((short)1, "page_uri"),
    TITLE((short)2, "title"),
    ARTIFACT_SOURCE_PK((short)3, "artifact_source_pk"),
    PHOTO_DESCRIPTIONS((short)4, "photo_descriptions");

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
        case 1: // PAGE_URI
          return PAGE_URI;
        case 2: // TITLE
          return TITLE;
        case 3: // ARTIFACT_SOURCE_PK
          return ARTIFACT_SOURCE_PK;
        case 4: // PHOTO_DESCRIPTIONS
          return PHOTO_DESCRIPTIONS;
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
  private static final int __ARTIFACT_SOURCE_PK_ISSET_ID = 0;
  private byte __isset_bitfield = 0;
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.PAGE_URI, new org.apache.thrift.meta_data.FieldMetaData("page_uri", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TITLE, new org.apache.thrift.meta_data.FieldMetaData("title", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.ARTIFACT_SOURCE_PK, new org.apache.thrift.meta_data.FieldMetaData("artifact_source_pk", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "EntityId")));
    tmpMap.put(_Fields.PHOTO_DESCRIPTIONS, new org.apache.thrift.meta_data.FieldMetaData("photo_descriptions", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, PhotoDescription.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Artifact.class, metaDataMap);
  }

  public Artifact() {
  }

  public Artifact(
    String page_uri,
    String title,
    long artifact_source_pk,
    List<PhotoDescription> photo_descriptions)
  {
    this();
    this.page_uri = page_uri;
    this.title = title;
    this.artifact_source_pk = artifact_source_pk;
    setArtifact_source_pkIsSet(true);
    this.photo_descriptions = photo_descriptions;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Artifact(Artifact other) {
    __isset_bitfield = other.__isset_bitfield;
    if (other.isSetPage_uri()) {
      this.page_uri = other.page_uri;
    }
    if (other.isSetTitle()) {
      this.title = other.title;
    }
    this.artifact_source_pk = other.artifact_source_pk;
    if (other.isSetPhoto_descriptions()) {
      List<PhotoDescription> __this__photo_descriptions = new ArrayList<PhotoDescription>(other.photo_descriptions.size());
      for (PhotoDescription other_element : other.photo_descriptions) {
        __this__photo_descriptions.add(new PhotoDescription(other_element));
      }
      this.photo_descriptions = __this__photo_descriptions;
    }
  }

  public Artifact deepCopy() {
    return new Artifact(this);
  }

  @Override
  public void clear() {
    this.page_uri = null;
    this.title = null;
    setArtifact_source_pkIsSet(false);
    this.artifact_source_pk = 0;
    this.photo_descriptions = null;
  }

  public String getPage_uri() {
    return this.page_uri;
  }

  public Artifact setPage_uri(String page_uri) {
    this.page_uri = page_uri;
    return this;
  }

  public void unsetPage_uri() {
    this.page_uri = null;
  }

  /** Returns true if field page_uri is set (has been assigned a value) and false otherwise */
  public boolean isSetPage_uri() {
    return this.page_uri != null;
  }

  public void setPage_uriIsSet(boolean value) {
    if (!value) {
      this.page_uri = null;
    }
  }

  public String getTitle() {
    return this.title;
  }

  public Artifact setTitle(String title) {
    this.title = title;
    return this;
  }

  public void unsetTitle() {
    this.title = null;
  }

  /** Returns true if field title is set (has been assigned a value) and false otherwise */
  public boolean isSetTitle() {
    return this.title != null;
  }

  public void setTitleIsSet(boolean value) {
    if (!value) {
      this.title = null;
    }
  }

  public long getArtifact_source_pk() {
    return this.artifact_source_pk;
  }

  public Artifact setArtifact_source_pk(long artifact_source_pk) {
    this.artifact_source_pk = artifact_source_pk;
    setArtifact_source_pkIsSet(true);
    return this;
  }

  public void unsetArtifact_source_pk() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __ARTIFACT_SOURCE_PK_ISSET_ID);
  }

  /** Returns true if field artifact_source_pk is set (has been assigned a value) and false otherwise */
  public boolean isSetArtifact_source_pk() {
    return EncodingUtils.testBit(__isset_bitfield, __ARTIFACT_SOURCE_PK_ISSET_ID);
  }

  public void setArtifact_source_pkIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __ARTIFACT_SOURCE_PK_ISSET_ID, value);
  }

  public int getPhoto_descriptionsSize() {
    return (this.photo_descriptions == null) ? 0 : this.photo_descriptions.size();
  }

  public java.util.Iterator<PhotoDescription> getPhoto_descriptionsIterator() {
    return (this.photo_descriptions == null) ? null : this.photo_descriptions.iterator();
  }

  public void addToPhoto_descriptions(PhotoDescription elem) {
    if (this.photo_descriptions == null) {
      this.photo_descriptions = new ArrayList<PhotoDescription>();
    }
    this.photo_descriptions.add(elem);
  }

  public List<PhotoDescription> getPhoto_descriptions() {
    return this.photo_descriptions;
  }

  public Artifact setPhoto_descriptions(List<PhotoDescription> photo_descriptions) {
    this.photo_descriptions = photo_descriptions;
    return this;
  }

  public void unsetPhoto_descriptions() {
    this.photo_descriptions = null;
  }

  /** Returns true if field photo_descriptions is set (has been assigned a value) and false otherwise */
  public boolean isSetPhoto_descriptions() {
    return this.photo_descriptions != null;
  }

  public void setPhoto_descriptionsIsSet(boolean value) {
    if (!value) {
      this.photo_descriptions = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case PAGE_URI:
      if (value == null) {
        unsetPage_uri();
      } else {
        setPage_uri((String)value);
      }
      break;

    case TITLE:
      if (value == null) {
        unsetTitle();
      } else {
        setTitle((String)value);
      }
      break;

    case ARTIFACT_SOURCE_PK:
      if (value == null) {
        unsetArtifact_source_pk();
      } else {
        setArtifact_source_pk((Long)value);
      }
      break;

    case PHOTO_DESCRIPTIONS:
      if (value == null) {
        unsetPhoto_descriptions();
      } else {
        setPhoto_descriptions((List<PhotoDescription>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case PAGE_URI:
      return getPage_uri();

    case TITLE:
      return getTitle();

    case ARTIFACT_SOURCE_PK:
      return Long.valueOf(getArtifact_source_pk());

    case PHOTO_DESCRIPTIONS:
      return getPhoto_descriptions();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case PAGE_URI:
      return isSetPage_uri();
    case TITLE:
      return isSetTitle();
    case ARTIFACT_SOURCE_PK:
      return isSetArtifact_source_pk();
    case PHOTO_DESCRIPTIONS:
      return isSetPhoto_descriptions();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Artifact)
      return this.equals((Artifact)that);
    return false;
  }

  public boolean equals(Artifact that) {
    if (that == null)
      return false;

    boolean this_present_page_uri = true && this.isSetPage_uri();
    boolean that_present_page_uri = true && that.isSetPage_uri();
    if (this_present_page_uri || that_present_page_uri) {
      if (!(this_present_page_uri && that_present_page_uri))
        return false;
      if (!this.page_uri.equals(that.page_uri))
        return false;
    }

    boolean this_present_title = true && this.isSetTitle();
    boolean that_present_title = true && that.isSetTitle();
    if (this_present_title || that_present_title) {
      if (!(this_present_title && that_present_title))
        return false;
      if (!this.title.equals(that.title))
        return false;
    }

    boolean this_present_artifact_source_pk = true;
    boolean that_present_artifact_source_pk = true;
    if (this_present_artifact_source_pk || that_present_artifact_source_pk) {
      if (!(this_present_artifact_source_pk && that_present_artifact_source_pk))
        return false;
      if (this.artifact_source_pk != that.artifact_source_pk)
        return false;
    }

    boolean this_present_photo_descriptions = true && this.isSetPhoto_descriptions();
    boolean that_present_photo_descriptions = true && that.isSetPhoto_descriptions();
    if (this_present_photo_descriptions || that_present_photo_descriptions) {
      if (!(this_present_photo_descriptions && that_present_photo_descriptions))
        return false;
      if (!this.photo_descriptions.equals(that.photo_descriptions))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_page_uri = true && (isSetPage_uri());
    list.add(present_page_uri);
    if (present_page_uri)
      list.add(page_uri);

    boolean present_title = true && (isSetTitle());
    list.add(present_title);
    if (present_title)
      list.add(title);

    boolean present_artifact_source_pk = true;
    list.add(present_artifact_source_pk);
    if (present_artifact_source_pk)
      list.add(artifact_source_pk);

    boolean present_photo_descriptions = true && (isSetPhoto_descriptions());
    list.add(present_photo_descriptions);
    if (present_photo_descriptions)
      list.add(photo_descriptions);

    return list.hashCode();
  }

  @Override
  public int compareTo(Artifact other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetPage_uri()).compareTo(other.isSetPage_uri());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPage_uri()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.page_uri, other.page_uri);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTitle()).compareTo(other.isSetTitle());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTitle()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.title, other.title);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArtifact_source_pk()).compareTo(other.isSetArtifact_source_pk());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArtifact_source_pk()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.artifact_source_pk, other.artifact_source_pk);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetPhoto_descriptions()).compareTo(other.isSetPhoto_descriptions());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetPhoto_descriptions()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.photo_descriptions, other.photo_descriptions);
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
    StringBuilder sb = new StringBuilder("Artifact(");
    boolean first = true;

    sb.append("page_uri:");
    if (this.page_uri == null) {
      sb.append("null");
    } else {
      sb.append(this.page_uri);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("title:");
    if (this.title == null) {
      sb.append("null");
    } else {
      sb.append(this.title);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("artifact_source_pk:");
    sb.append(this.artifact_source_pk);
    first = false;
    if (!first) sb.append(", ");
    sb.append("photo_descriptions:");
    if (this.photo_descriptions == null) {
      sb.append("null");
    } else {
      sb.append(this.photo_descriptions);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (page_uri == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'page_uri' was not present! Struct: " + toString());
    }
    if (title == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'title' was not present! Struct: " + toString());
    }
    // alas, we cannot check 'artifact_source_pk' because it's a primitive and you chose the non-beans generator.
    if (photo_descriptions == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'photo_descriptions' was not present! Struct: " + toString());
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

  private static class ArtifactStandardSchemeFactory implements SchemeFactory {
    public ArtifactStandardScheme getScheme() {
      return new ArtifactStandardScheme();
    }
  }

  private static class ArtifactStandardScheme extends StandardScheme<Artifact> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Artifact struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // PAGE_URI
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.page_uri = iprot.readString();
              struct.setPage_uriIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TITLE
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.title = iprot.readString();
              struct.setTitleIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ARTIFACT_SOURCE_PK
            if (schemeField.type == org.apache.thrift.protocol.TType.I64) {
              struct.artifact_source_pk = iprot.readI64();
              struct.setArtifact_source_pkIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // PHOTO_DESCRIPTIONS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list16 = iprot.readListBegin();
                struct.photo_descriptions = new ArrayList<PhotoDescription>(_list16.size);
                PhotoDescription _elem17;
                for (int _i18 = 0; _i18 < _list16.size; ++_i18)
                {
                  _elem17 = new PhotoDescription();
                  _elem17.read(iprot);
                  struct.photo_descriptions.add(_elem17);
                }
                iprot.readListEnd();
              }
              struct.setPhoto_descriptionsIsSet(true);
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
      if (!struct.isSetArtifact_source_pk()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'artifact_source_pk' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Artifact struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.page_uri != null) {
        oprot.writeFieldBegin(PAGE_URI_FIELD_DESC);
        oprot.writeString(struct.page_uri);
        oprot.writeFieldEnd();
      }
      if (struct.title != null) {
        oprot.writeFieldBegin(TITLE_FIELD_DESC);
        oprot.writeString(struct.title);
        oprot.writeFieldEnd();
      }
      oprot.writeFieldBegin(ARTIFACT_SOURCE_PK_FIELD_DESC);
      oprot.writeI64(struct.artifact_source_pk);
      oprot.writeFieldEnd();
      if (struct.photo_descriptions != null) {
        oprot.writeFieldBegin(PHOTO_DESCRIPTIONS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.photo_descriptions.size()));
          for (PhotoDescription _iter19 : struct.photo_descriptions)
          {
            _iter19.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ArtifactTupleSchemeFactory implements SchemeFactory {
    public ArtifactTupleScheme getScheme() {
      return new ArtifactTupleScheme();
    }
  }

  private static class ArtifactTupleScheme extends TupleScheme<Artifact> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Artifact struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.page_uri);
      oprot.writeString(struct.title);
      oprot.writeI64(struct.artifact_source_pk);
      {
        oprot.writeI32(struct.photo_descriptions.size());
        for (PhotoDescription _iter20 : struct.photo_descriptions)
        {
          _iter20.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Artifact struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.page_uri = iprot.readString();
      struct.setPage_uriIsSet(true);
      struct.title = iprot.readString();
      struct.setTitleIsSet(true);
      struct.artifact_source_pk = iprot.readI64();
      struct.setArtifact_source_pkIsSet(true);
      {
        org.apache.thrift.protocol.TList _list21 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.photo_descriptions = new ArrayList<PhotoDescription>(_list21.size);
        PhotoDescription _elem22;
        for (int _i23 = 0; _i23 < _list21.size; ++_i23)
        {
          _elem22 = new PhotoDescription();
          _elem22.read(iprot);
          struct.photo_descriptions.add(_elem22);
        }
      }
      struct.setPhoto_descriptionsIsSet(true);
    }
  }

}

