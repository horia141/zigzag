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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-4-22")
public class Generation implements org.apache.thrift.TBase<Generation, Generation._Fields>, java.io.Serializable, Cloneable, Comparable<Generation> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("Generation");

  private static final org.apache.thrift.protocol.TField ID_FIELD_DESC = new org.apache.thrift.protocol.TField("id", org.apache.thrift.protocol.TType.I64, (short)1);
  private static final org.apache.thrift.protocol.TField DATETIME_STARTED_TS_FIELD_DESC = new org.apache.thrift.protocol.TField("datetime_started_ts", org.apache.thrift.protocol.TType.I32, (short)2);
  private static final org.apache.thrift.protocol.TField DATETIME_ENDED_TS_FIELD_DESC = new org.apache.thrift.protocol.TField("datetime_ended_ts", org.apache.thrift.protocol.TType.I32, (short)3);
  private static final org.apache.thrift.protocol.TField ARTIFACT_SOURCES_FIELD_DESC = new org.apache.thrift.protocol.TField("artifact_sources", org.apache.thrift.protocol.TType.MAP, (short)4);
  private static final org.apache.thrift.protocol.TField SCREEN_CONFIGS_FIELD_DESC = new org.apache.thrift.protocol.TField("screen_configs", org.apache.thrift.protocol.TType.MAP, (short)5);
  private static final org.apache.thrift.protocol.TField ARTIFACTS_FIELD_DESC = new org.apache.thrift.protocol.TField("artifacts", org.apache.thrift.protocol.TType.LIST, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new GenerationStandardSchemeFactory());
    schemes.put(TupleScheme.class, new GenerationTupleSchemeFactory());
  }

  public long id; // required
  public int datetime_started_ts; // required
  public int datetime_ended_ts; // required
  public Map<Long,ArtifactSource> artifact_sources; // required
  public Map<Long,ScreenConfig> screen_configs; // required
  public List<Artifact> artifacts; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    ID((short)1, "id"),
    DATETIME_STARTED_TS((short)2, "datetime_started_ts"),
    DATETIME_ENDED_TS((short)3, "datetime_ended_ts"),
    ARTIFACT_SOURCES((short)4, "artifact_sources"),
    SCREEN_CONFIGS((short)5, "screen_configs"),
    ARTIFACTS((short)6, "artifacts");

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
        case 2: // DATETIME_STARTED_TS
          return DATETIME_STARTED_TS;
        case 3: // DATETIME_ENDED_TS
          return DATETIME_ENDED_TS;
        case 4: // ARTIFACT_SOURCES
          return ARTIFACT_SOURCES;
        case 5: // SCREEN_CONFIGS
          return SCREEN_CONFIGS;
        case 6: // ARTIFACTS
          return ARTIFACTS;
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
  private static final int __DATETIME_STARTED_TS_ISSET_ID = 1;
  private static final int __DATETIME_ENDED_TS_ISSET_ID = 2;
  private byte __isset_bitfield = 0;
  private static final _Fields optionals[] = {_Fields.ARTIFACTS};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.ID, new org.apache.thrift.meta_data.FieldMetaData("id", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64        , "EntityId")));
    tmpMap.put(_Fields.DATETIME_STARTED_TS, new org.apache.thrift.meta_data.FieldMetaData("datetime_started_ts", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.DATETIME_ENDED_TS, new org.apache.thrift.meta_data.FieldMetaData("datetime_ended_ts", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
    tmpMap.put(_Fields.ARTIFACT_SOURCES, new org.apache.thrift.meta_data.FieldMetaData("artifact_sources", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64            , "EntityId"), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ArtifactSource.class))));
    tmpMap.put(_Fields.SCREEN_CONFIGS, new org.apache.thrift.meta_data.FieldMetaData("screen_configs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64            , "EntityId"), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, ScreenConfig.class))));
    tmpMap.put(_Fields.ARTIFACTS, new org.apache.thrift.meta_data.FieldMetaData("artifacts", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, Artifact.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(Generation.class, metaDataMap);
  }

  public Generation() {
  }

  public Generation(
    long id,
    int datetime_started_ts,
    int datetime_ended_ts,
    Map<Long,ArtifactSource> artifact_sources,
    Map<Long,ScreenConfig> screen_configs)
  {
    this();
    this.id = id;
    setIdIsSet(true);
    this.datetime_started_ts = datetime_started_ts;
    setDatetime_started_tsIsSet(true);
    this.datetime_ended_ts = datetime_ended_ts;
    setDatetime_ended_tsIsSet(true);
    this.artifact_sources = artifact_sources;
    this.screen_configs = screen_configs;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public Generation(Generation other) {
    __isset_bitfield = other.__isset_bitfield;
    this.id = other.id;
    this.datetime_started_ts = other.datetime_started_ts;
    this.datetime_ended_ts = other.datetime_ended_ts;
    if (other.isSetArtifact_sources()) {
      Map<Long,ArtifactSource> __this__artifact_sources = new HashMap<Long,ArtifactSource>(other.artifact_sources.size());
      for (Map.Entry<Long, ArtifactSource> other_element : other.artifact_sources.entrySet()) {

        Long other_element_key = other_element.getKey();
        ArtifactSource other_element_value = other_element.getValue();

        Long __this__artifact_sources_copy_key = other_element_key;

        ArtifactSource __this__artifact_sources_copy_value = new ArtifactSource(other_element_value);

        __this__artifact_sources.put(__this__artifact_sources_copy_key, __this__artifact_sources_copy_value);
      }
      this.artifact_sources = __this__artifact_sources;
    }
    if (other.isSetScreen_configs()) {
      Map<Long,ScreenConfig> __this__screen_configs = new HashMap<Long,ScreenConfig>(other.screen_configs.size());
      for (Map.Entry<Long, ScreenConfig> other_element : other.screen_configs.entrySet()) {

        Long other_element_key = other_element.getKey();
        ScreenConfig other_element_value = other_element.getValue();

        Long __this__screen_configs_copy_key = other_element_key;

        ScreenConfig __this__screen_configs_copy_value = new ScreenConfig(other_element_value);

        __this__screen_configs.put(__this__screen_configs_copy_key, __this__screen_configs_copy_value);
      }
      this.screen_configs = __this__screen_configs;
    }
    if (other.isSetArtifacts()) {
      List<Artifact> __this__artifacts = new ArrayList<Artifact>(other.artifacts.size());
      for (Artifact other_element : other.artifacts) {
        __this__artifacts.add(new Artifact(other_element));
      }
      this.artifacts = __this__artifacts;
    }
  }

  public Generation deepCopy() {
    return new Generation(this);
  }

  @Override
  public void clear() {
    setIdIsSet(false);
    this.id = 0;
    setDatetime_started_tsIsSet(false);
    this.datetime_started_ts = 0;
    setDatetime_ended_tsIsSet(false);
    this.datetime_ended_ts = 0;
    this.artifact_sources = null;
    this.screen_configs = null;
    this.artifacts = null;
  }

  public long getId() {
    return this.id;
  }

  public Generation setId(long id) {
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

  public int getDatetime_started_ts() {
    return this.datetime_started_ts;
  }

  public Generation setDatetime_started_ts(int datetime_started_ts) {
    this.datetime_started_ts = datetime_started_ts;
    setDatetime_started_tsIsSet(true);
    return this;
  }

  public void unsetDatetime_started_ts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATETIME_STARTED_TS_ISSET_ID);
  }

  /** Returns true if field datetime_started_ts is set (has been assigned a value) and false otherwise */
  public boolean isSetDatetime_started_ts() {
    return EncodingUtils.testBit(__isset_bitfield, __DATETIME_STARTED_TS_ISSET_ID);
  }

  public void setDatetime_started_tsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATETIME_STARTED_TS_ISSET_ID, value);
  }

  public int getDatetime_ended_ts() {
    return this.datetime_ended_ts;
  }

  public Generation setDatetime_ended_ts(int datetime_ended_ts) {
    this.datetime_ended_ts = datetime_ended_ts;
    setDatetime_ended_tsIsSet(true);
    return this;
  }

  public void unsetDatetime_ended_ts() {
    __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __DATETIME_ENDED_TS_ISSET_ID);
  }

  /** Returns true if field datetime_ended_ts is set (has been assigned a value) and false otherwise */
  public boolean isSetDatetime_ended_ts() {
    return EncodingUtils.testBit(__isset_bitfield, __DATETIME_ENDED_TS_ISSET_ID);
  }

  public void setDatetime_ended_tsIsSet(boolean value) {
    __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __DATETIME_ENDED_TS_ISSET_ID, value);
  }

  public int getArtifact_sourcesSize() {
    return (this.artifact_sources == null) ? 0 : this.artifact_sources.size();
  }

  public void putToArtifact_sources(long key, ArtifactSource val) {
    if (this.artifact_sources == null) {
      this.artifact_sources = new HashMap<Long,ArtifactSource>();
    }
    this.artifact_sources.put(key, val);
  }

  public Map<Long,ArtifactSource> getArtifact_sources() {
    return this.artifact_sources;
  }

  public Generation setArtifact_sources(Map<Long,ArtifactSource> artifact_sources) {
    this.artifact_sources = artifact_sources;
    return this;
  }

  public void unsetArtifact_sources() {
    this.artifact_sources = null;
  }

  /** Returns true if field artifact_sources is set (has been assigned a value) and false otherwise */
  public boolean isSetArtifact_sources() {
    return this.artifact_sources != null;
  }

  public void setArtifact_sourcesIsSet(boolean value) {
    if (!value) {
      this.artifact_sources = null;
    }
  }

  public int getScreen_configsSize() {
    return (this.screen_configs == null) ? 0 : this.screen_configs.size();
  }

  public void putToScreen_configs(long key, ScreenConfig val) {
    if (this.screen_configs == null) {
      this.screen_configs = new HashMap<Long,ScreenConfig>();
    }
    this.screen_configs.put(key, val);
  }

  public Map<Long,ScreenConfig> getScreen_configs() {
    return this.screen_configs;
  }

  public Generation setScreen_configs(Map<Long,ScreenConfig> screen_configs) {
    this.screen_configs = screen_configs;
    return this;
  }

  public void unsetScreen_configs() {
    this.screen_configs = null;
  }

  /** Returns true if field screen_configs is set (has been assigned a value) and false otherwise */
  public boolean isSetScreen_configs() {
    return this.screen_configs != null;
  }

  public void setScreen_configsIsSet(boolean value) {
    if (!value) {
      this.screen_configs = null;
    }
  }

  public int getArtifactsSize() {
    return (this.artifacts == null) ? 0 : this.artifacts.size();
  }

  public java.util.Iterator<Artifact> getArtifactsIterator() {
    return (this.artifacts == null) ? null : this.artifacts.iterator();
  }

  public void addToArtifacts(Artifact elem) {
    if (this.artifacts == null) {
      this.artifacts = new ArrayList<Artifact>();
    }
    this.artifacts.add(elem);
  }

  public List<Artifact> getArtifacts() {
    return this.artifacts;
  }

  public Generation setArtifacts(List<Artifact> artifacts) {
    this.artifacts = artifacts;
    return this;
  }

  public void unsetArtifacts() {
    this.artifacts = null;
  }

  /** Returns true if field artifacts is set (has been assigned a value) and false otherwise */
  public boolean isSetArtifacts() {
    return this.artifacts != null;
  }

  public void setArtifactsIsSet(boolean value) {
    if (!value) {
      this.artifacts = null;
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

    case DATETIME_STARTED_TS:
      if (value == null) {
        unsetDatetime_started_ts();
      } else {
        setDatetime_started_ts((Integer)value);
      }
      break;

    case DATETIME_ENDED_TS:
      if (value == null) {
        unsetDatetime_ended_ts();
      } else {
        setDatetime_ended_ts((Integer)value);
      }
      break;

    case ARTIFACT_SOURCES:
      if (value == null) {
        unsetArtifact_sources();
      } else {
        setArtifact_sources((Map<Long,ArtifactSource>)value);
      }
      break;

    case SCREEN_CONFIGS:
      if (value == null) {
        unsetScreen_configs();
      } else {
        setScreen_configs((Map<Long,ScreenConfig>)value);
      }
      break;

    case ARTIFACTS:
      if (value == null) {
        unsetArtifacts();
      } else {
        setArtifacts((List<Artifact>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case ID:
      return Long.valueOf(getId());

    case DATETIME_STARTED_TS:
      return Integer.valueOf(getDatetime_started_ts());

    case DATETIME_ENDED_TS:
      return Integer.valueOf(getDatetime_ended_ts());

    case ARTIFACT_SOURCES:
      return getArtifact_sources();

    case SCREEN_CONFIGS:
      return getScreen_configs();

    case ARTIFACTS:
      return getArtifacts();

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
    case DATETIME_STARTED_TS:
      return isSetDatetime_started_ts();
    case DATETIME_ENDED_TS:
      return isSetDatetime_ended_ts();
    case ARTIFACT_SOURCES:
      return isSetArtifact_sources();
    case SCREEN_CONFIGS:
      return isSetScreen_configs();
    case ARTIFACTS:
      return isSetArtifacts();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof Generation)
      return this.equals((Generation)that);
    return false;
  }

  public boolean equals(Generation that) {
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

    boolean this_present_datetime_started_ts = true;
    boolean that_present_datetime_started_ts = true;
    if (this_present_datetime_started_ts || that_present_datetime_started_ts) {
      if (!(this_present_datetime_started_ts && that_present_datetime_started_ts))
        return false;
      if (this.datetime_started_ts != that.datetime_started_ts)
        return false;
    }

    boolean this_present_datetime_ended_ts = true;
    boolean that_present_datetime_ended_ts = true;
    if (this_present_datetime_ended_ts || that_present_datetime_ended_ts) {
      if (!(this_present_datetime_ended_ts && that_present_datetime_ended_ts))
        return false;
      if (this.datetime_ended_ts != that.datetime_ended_ts)
        return false;
    }

    boolean this_present_artifact_sources = true && this.isSetArtifact_sources();
    boolean that_present_artifact_sources = true && that.isSetArtifact_sources();
    if (this_present_artifact_sources || that_present_artifact_sources) {
      if (!(this_present_artifact_sources && that_present_artifact_sources))
        return false;
      if (!this.artifact_sources.equals(that.artifact_sources))
        return false;
    }

    boolean this_present_screen_configs = true && this.isSetScreen_configs();
    boolean that_present_screen_configs = true && that.isSetScreen_configs();
    if (this_present_screen_configs || that_present_screen_configs) {
      if (!(this_present_screen_configs && that_present_screen_configs))
        return false;
      if (!this.screen_configs.equals(that.screen_configs))
        return false;
    }

    boolean this_present_artifacts = true && this.isSetArtifacts();
    boolean that_present_artifacts = true && that.isSetArtifacts();
    if (this_present_artifacts || that_present_artifacts) {
      if (!(this_present_artifacts && that_present_artifacts))
        return false;
      if (!this.artifacts.equals(that.artifacts))
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

    boolean present_datetime_started_ts = true;
    list.add(present_datetime_started_ts);
    if (present_datetime_started_ts)
      list.add(datetime_started_ts);

    boolean present_datetime_ended_ts = true;
    list.add(present_datetime_ended_ts);
    if (present_datetime_ended_ts)
      list.add(datetime_ended_ts);

    boolean present_artifact_sources = true && (isSetArtifact_sources());
    list.add(present_artifact_sources);
    if (present_artifact_sources)
      list.add(artifact_sources);

    boolean present_screen_configs = true && (isSetScreen_configs());
    list.add(present_screen_configs);
    if (present_screen_configs)
      list.add(screen_configs);

    boolean present_artifacts = true && (isSetArtifacts());
    list.add(present_artifacts);
    if (present_artifacts)
      list.add(artifacts);

    return list.hashCode();
  }

  @Override
  public int compareTo(Generation other) {
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
    lastComparison = Boolean.valueOf(isSetDatetime_started_ts()).compareTo(other.isSetDatetime_started_ts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDatetime_started_ts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.datetime_started_ts, other.datetime_started_ts);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetDatetime_ended_ts()).compareTo(other.isSetDatetime_ended_ts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDatetime_ended_ts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.datetime_ended_ts, other.datetime_ended_ts);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArtifact_sources()).compareTo(other.isSetArtifact_sources());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArtifact_sources()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.artifact_sources, other.artifact_sources);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetScreen_configs()).compareTo(other.isSetScreen_configs());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetScreen_configs()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.screen_configs, other.screen_configs);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetArtifacts()).compareTo(other.isSetArtifacts());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetArtifacts()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.artifacts, other.artifacts);
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
    StringBuilder sb = new StringBuilder("Generation(");
    boolean first = true;

    sb.append("id:");
    sb.append(this.id);
    first = false;
    if (!first) sb.append(", ");
    sb.append("datetime_started_ts:");
    sb.append(this.datetime_started_ts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("datetime_ended_ts:");
    sb.append(this.datetime_ended_ts);
    first = false;
    if (!first) sb.append(", ");
    sb.append("artifact_sources:");
    if (this.artifact_sources == null) {
      sb.append("null");
    } else {
      sb.append(this.artifact_sources);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("screen_configs:");
    if (this.screen_configs == null) {
      sb.append("null");
    } else {
      sb.append(this.screen_configs);
    }
    first = false;
    if (isSetArtifacts()) {
      if (!first) sb.append(", ");
      sb.append("artifacts:");
      if (this.artifacts == null) {
        sb.append("null");
      } else {
        sb.append(this.artifacts);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    // alas, we cannot check 'id' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'datetime_started_ts' because it's a primitive and you chose the non-beans generator.
    // alas, we cannot check 'datetime_ended_ts' because it's a primitive and you chose the non-beans generator.
    if (artifact_sources == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'artifact_sources' was not present! Struct: " + toString());
    }
    if (screen_configs == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'screen_configs' was not present! Struct: " + toString());
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

  private static class GenerationStandardSchemeFactory implements SchemeFactory {
    public GenerationStandardScheme getScheme() {
      return new GenerationStandardScheme();
    }
  }

  private static class GenerationStandardScheme extends StandardScheme<Generation> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, Generation struct) throws org.apache.thrift.TException {
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
          case 2: // DATETIME_STARTED_TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.datetime_started_ts = iprot.readI32();
              struct.setDatetime_started_tsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // DATETIME_ENDED_TS
            if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
              struct.datetime_ended_ts = iprot.readI32();
              struct.setDatetime_ended_tsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ARTIFACT_SOURCES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map34 = iprot.readMapBegin();
                struct.artifact_sources = new HashMap<Long,ArtifactSource>(2*_map34.size);
                long _key35;
                ArtifactSource _val36;
                for (int _i37 = 0; _i37 < _map34.size; ++_i37)
                {
                  _key35 = iprot.readI64();
                  _val36 = new ArtifactSource();
                  _val36.read(iprot);
                  struct.artifact_sources.put(_key35, _val36);
                }
                iprot.readMapEnd();
              }
              struct.setArtifact_sourcesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SCREEN_CONFIGS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map38 = iprot.readMapBegin();
                struct.screen_configs = new HashMap<Long,ScreenConfig>(2*_map38.size);
                long _key39;
                ScreenConfig _val40;
                for (int _i41 = 0; _i41 < _map38.size; ++_i41)
                {
                  _key39 = iprot.readI64();
                  _val40 = new ScreenConfig();
                  _val40.read(iprot);
                  struct.screen_configs.put(_key39, _val40);
                }
                iprot.readMapEnd();
              }
              struct.setScreen_configsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // ARTIFACTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list42 = iprot.readListBegin();
                struct.artifacts = new ArrayList<Artifact>(_list42.size);
                Artifact _elem43;
                for (int _i44 = 0; _i44 < _list42.size; ++_i44)
                {
                  _elem43 = new Artifact();
                  _elem43.read(iprot);
                  struct.artifacts.add(_elem43);
                }
                iprot.readListEnd();
              }
              struct.setArtifactsIsSet(true);
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
      if (!struct.isSetDatetime_started_ts()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'datetime_started_ts' was not found in serialized data! Struct: " + toString());
      }
      if (!struct.isSetDatetime_ended_ts()) {
        throw new org.apache.thrift.protocol.TProtocolException("Required field 'datetime_ended_ts' was not found in serialized data! Struct: " + toString());
      }
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, Generation struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      oprot.writeFieldBegin(ID_FIELD_DESC);
      oprot.writeI64(struct.id);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DATETIME_STARTED_TS_FIELD_DESC);
      oprot.writeI32(struct.datetime_started_ts);
      oprot.writeFieldEnd();
      oprot.writeFieldBegin(DATETIME_ENDED_TS_FIELD_DESC);
      oprot.writeI32(struct.datetime_ended_ts);
      oprot.writeFieldEnd();
      if (struct.artifact_sources != null) {
        oprot.writeFieldBegin(ARTIFACT_SOURCES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, struct.artifact_sources.size()));
          for (Map.Entry<Long, ArtifactSource> _iter45 : struct.artifact_sources.entrySet())
          {
            oprot.writeI64(_iter45.getKey());
            _iter45.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.screen_configs != null) {
        oprot.writeFieldBegin(SCREEN_CONFIGS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, struct.screen_configs.size()));
          for (Map.Entry<Long, ScreenConfig> _iter46 : struct.screen_configs.entrySet())
          {
            oprot.writeI64(_iter46.getKey());
            _iter46.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.artifacts != null) {
        if (struct.isSetArtifacts()) {
          oprot.writeFieldBegin(ARTIFACTS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.artifacts.size()));
            for (Artifact _iter47 : struct.artifacts)
            {
              _iter47.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class GenerationTupleSchemeFactory implements SchemeFactory {
    public GenerationTupleScheme getScheme() {
      return new GenerationTupleScheme();
    }
  }

  private static class GenerationTupleScheme extends TupleScheme<Generation> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, Generation struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeI64(struct.id);
      oprot.writeI32(struct.datetime_started_ts);
      oprot.writeI32(struct.datetime_ended_ts);
      {
        oprot.writeI32(struct.artifact_sources.size());
        for (Map.Entry<Long, ArtifactSource> _iter48 : struct.artifact_sources.entrySet())
        {
          oprot.writeI64(_iter48.getKey());
          _iter48.getValue().write(oprot);
        }
      }
      {
        oprot.writeI32(struct.screen_configs.size());
        for (Map.Entry<Long, ScreenConfig> _iter49 : struct.screen_configs.entrySet())
        {
          oprot.writeI64(_iter49.getKey());
          _iter49.getValue().write(oprot);
        }
      }
      BitSet optionals = new BitSet();
      if (struct.isSetArtifacts()) {
        optionals.set(0);
      }
      oprot.writeBitSet(optionals, 1);
      if (struct.isSetArtifacts()) {
        {
          oprot.writeI32(struct.artifacts.size());
          for (Artifact _iter50 : struct.artifacts)
          {
            _iter50.write(oprot);
          }
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, Generation struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.id = iprot.readI64();
      struct.setIdIsSet(true);
      struct.datetime_started_ts = iprot.readI32();
      struct.setDatetime_started_tsIsSet(true);
      struct.datetime_ended_ts = iprot.readI32();
      struct.setDatetime_ended_tsIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map51 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.artifact_sources = new HashMap<Long,ArtifactSource>(2*_map51.size);
        long _key52;
        ArtifactSource _val53;
        for (int _i54 = 0; _i54 < _map51.size; ++_i54)
        {
          _key52 = iprot.readI64();
          _val53 = new ArtifactSource();
          _val53.read(iprot);
          struct.artifact_sources.put(_key52, _val53);
        }
      }
      struct.setArtifact_sourcesIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map55 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.screen_configs = new HashMap<Long,ScreenConfig>(2*_map55.size);
        long _key56;
        ScreenConfig _val57;
        for (int _i58 = 0; _i58 < _map55.size; ++_i58)
        {
          _key56 = iprot.readI64();
          _val57 = new ScreenConfig();
          _val57.read(iprot);
          struct.screen_configs.put(_key56, _val57);
        }
      }
      struct.setScreen_configsIsSet(true);
      BitSet incoming = iprot.readBitSet(1);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list59 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.artifacts = new ArrayList<Artifact>(_list59.size);
          Artifact _elem60;
          for (int _i61 = 0; _i61 < _list59.size; ++_i61)
          {
            _elem60 = new Artifact();
            _elem60.read(iprot);
            struct.artifacts.add(_elem60);
          }
        }
        struct.setArtifactsIsSet(true);
      }
    }
  }

}

