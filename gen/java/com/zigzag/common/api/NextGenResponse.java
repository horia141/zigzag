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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-4-10")
public class NextGenResponse implements org.apache.thrift.TBase<NextGenResponse, NextGenResponse._Fields>, java.io.Serializable, Cloneable, Comparable<NextGenResponse> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("NextGenResponse");

  private static final org.apache.thrift.protocol.TField GENERATION_FIELD_DESC = new org.apache.thrift.protocol.TField("generation", org.apache.thrift.protocol.TType.STRUCT, (short)1);
  private static final org.apache.thrift.protocol.TField SCREEN_CONFIGS_FIELD_DESC = new org.apache.thrift.protocol.TField("screen_configs", org.apache.thrift.protocol.TType.MAP, (short)2);
  private static final org.apache.thrift.protocol.TField ARTIFACT_SOURCES_FIELD_DESC = new org.apache.thrift.protocol.TField("artifact_sources", org.apache.thrift.protocol.TType.MAP, (short)3);
  private static final org.apache.thrift.protocol.TField ARTIFACTS_FIELD_DESC = new org.apache.thrift.protocol.TField("artifacts", org.apache.thrift.protocol.TType.LIST, (short)4);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new NextGenResponseStandardSchemeFactory());
    schemes.put(TupleScheme.class, new NextGenResponseTupleSchemeFactory());
  }

  public com.zigzag.common.model.Generation generation; // required
  public Map<Long,com.zigzag.common.model.ScreenConfig> screen_configs; // required
  public Map<Long,com.zigzag.common.model.ArtifactSource> artifact_sources; // required
  public List<com.zigzag.common.model.Artifact> artifacts; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    GENERATION((short)1, "generation"),
    SCREEN_CONFIGS((short)2, "screen_configs"),
    ARTIFACT_SOURCES((short)3, "artifact_sources"),
    ARTIFACTS((short)4, "artifacts");

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
        case 2: // SCREEN_CONFIGS
          return SCREEN_CONFIGS;
        case 3: // ARTIFACT_SOURCES
          return ARTIFACT_SOURCES;
        case 4: // ARTIFACTS
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
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.GENERATION, new org.apache.thrift.meta_data.FieldMetaData("generation", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.zigzag.common.model.Generation.class)));
    tmpMap.put(_Fields.SCREEN_CONFIGS, new org.apache.thrift.meta_data.FieldMetaData("screen_configs", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.zigzag.common.model.ScreenConfig.class))));
    tmpMap.put(_Fields.ARTIFACT_SOURCES, new org.apache.thrift.meta_data.FieldMetaData("artifact_sources", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.MapMetaData(org.apache.thrift.protocol.TType.MAP, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64), 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.zigzag.common.model.ArtifactSource.class))));
    tmpMap.put(_Fields.ARTIFACTS, new org.apache.thrift.meta_data.FieldMetaData("artifacts", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, com.zigzag.common.model.Artifact.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(NextGenResponse.class, metaDataMap);
  }

  public NextGenResponse() {
  }

  public NextGenResponse(
    com.zigzag.common.model.Generation generation,
    Map<Long,com.zigzag.common.model.ScreenConfig> screen_configs,
    Map<Long,com.zigzag.common.model.ArtifactSource> artifact_sources,
    List<com.zigzag.common.model.Artifact> artifacts)
  {
    this();
    this.generation = generation;
    this.screen_configs = screen_configs;
    this.artifact_sources = artifact_sources;
    this.artifacts = artifacts;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public NextGenResponse(NextGenResponse other) {
    if (other.isSetGeneration()) {
      this.generation = new com.zigzag.common.model.Generation(other.generation);
    }
    if (other.isSetScreen_configs()) {
      Map<Long,com.zigzag.common.model.ScreenConfig> __this__screen_configs = new HashMap<Long,com.zigzag.common.model.ScreenConfig>(other.screen_configs.size());
      for (Map.Entry<Long, com.zigzag.common.model.ScreenConfig> other_element : other.screen_configs.entrySet()) {

        Long other_element_key = other_element.getKey();
        com.zigzag.common.model.ScreenConfig other_element_value = other_element.getValue();

        Long __this__screen_configs_copy_key = other_element_key;

        com.zigzag.common.model.ScreenConfig __this__screen_configs_copy_value = new com.zigzag.common.model.ScreenConfig(other_element_value);

        __this__screen_configs.put(__this__screen_configs_copy_key, __this__screen_configs_copy_value);
      }
      this.screen_configs = __this__screen_configs;
    }
    if (other.isSetArtifact_sources()) {
      Map<Long,com.zigzag.common.model.ArtifactSource> __this__artifact_sources = new HashMap<Long,com.zigzag.common.model.ArtifactSource>(other.artifact_sources.size());
      for (Map.Entry<Long, com.zigzag.common.model.ArtifactSource> other_element : other.artifact_sources.entrySet()) {

        Long other_element_key = other_element.getKey();
        com.zigzag.common.model.ArtifactSource other_element_value = other_element.getValue();

        Long __this__artifact_sources_copy_key = other_element_key;

        com.zigzag.common.model.ArtifactSource __this__artifact_sources_copy_value = new com.zigzag.common.model.ArtifactSource(other_element_value);

        __this__artifact_sources.put(__this__artifact_sources_copy_key, __this__artifact_sources_copy_value);
      }
      this.artifact_sources = __this__artifact_sources;
    }
    if (other.isSetArtifacts()) {
      List<com.zigzag.common.model.Artifact> __this__artifacts = new ArrayList<com.zigzag.common.model.Artifact>(other.artifacts.size());
      for (com.zigzag.common.model.Artifact other_element : other.artifacts) {
        __this__artifacts.add(new com.zigzag.common.model.Artifact(other_element));
      }
      this.artifacts = __this__artifacts;
    }
  }

  public NextGenResponse deepCopy() {
    return new NextGenResponse(this);
  }

  @Override
  public void clear() {
    this.generation = null;
    this.screen_configs = null;
    this.artifact_sources = null;
    this.artifacts = null;
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

  public int getScreen_configsSize() {
    return (this.screen_configs == null) ? 0 : this.screen_configs.size();
  }

  public void putToScreen_configs(long key, com.zigzag.common.model.ScreenConfig val) {
    if (this.screen_configs == null) {
      this.screen_configs = new HashMap<Long,com.zigzag.common.model.ScreenConfig>();
    }
    this.screen_configs.put(key, val);
  }

  public Map<Long,com.zigzag.common.model.ScreenConfig> getScreen_configs() {
    return this.screen_configs;
  }

  public NextGenResponse setScreen_configs(Map<Long,com.zigzag.common.model.ScreenConfig> screen_configs) {
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

  public int getArtifact_sourcesSize() {
    return (this.artifact_sources == null) ? 0 : this.artifact_sources.size();
  }

  public void putToArtifact_sources(long key, com.zigzag.common.model.ArtifactSource val) {
    if (this.artifact_sources == null) {
      this.artifact_sources = new HashMap<Long,com.zigzag.common.model.ArtifactSource>();
    }
    this.artifact_sources.put(key, val);
  }

  public Map<Long,com.zigzag.common.model.ArtifactSource> getArtifact_sources() {
    return this.artifact_sources;
  }

  public NextGenResponse setArtifact_sources(Map<Long,com.zigzag.common.model.ArtifactSource> artifact_sources) {
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

  public int getArtifactsSize() {
    return (this.artifacts == null) ? 0 : this.artifacts.size();
  }

  public java.util.Iterator<com.zigzag.common.model.Artifact> getArtifactsIterator() {
    return (this.artifacts == null) ? null : this.artifacts.iterator();
  }

  public void addToArtifacts(com.zigzag.common.model.Artifact elem) {
    if (this.artifacts == null) {
      this.artifacts = new ArrayList<com.zigzag.common.model.Artifact>();
    }
    this.artifacts.add(elem);
  }

  public List<com.zigzag.common.model.Artifact> getArtifacts() {
    return this.artifacts;
  }

  public NextGenResponse setArtifacts(List<com.zigzag.common.model.Artifact> artifacts) {
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
    case GENERATION:
      if (value == null) {
        unsetGeneration();
      } else {
        setGeneration((com.zigzag.common.model.Generation)value);
      }
      break;

    case SCREEN_CONFIGS:
      if (value == null) {
        unsetScreen_configs();
      } else {
        setScreen_configs((Map<Long,com.zigzag.common.model.ScreenConfig>)value);
      }
      break;

    case ARTIFACT_SOURCES:
      if (value == null) {
        unsetArtifact_sources();
      } else {
        setArtifact_sources((Map<Long,com.zigzag.common.model.ArtifactSource>)value);
      }
      break;

    case ARTIFACTS:
      if (value == null) {
        unsetArtifacts();
      } else {
        setArtifacts((List<com.zigzag.common.model.Artifact>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case GENERATION:
      return getGeneration();

    case SCREEN_CONFIGS:
      return getScreen_configs();

    case ARTIFACT_SOURCES:
      return getArtifact_sources();

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
    case GENERATION:
      return isSetGeneration();
    case SCREEN_CONFIGS:
      return isSetScreen_configs();
    case ARTIFACT_SOURCES:
      return isSetArtifact_sources();
    case ARTIFACTS:
      return isSetArtifacts();
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

    boolean this_present_screen_configs = true && this.isSetScreen_configs();
    boolean that_present_screen_configs = true && that.isSetScreen_configs();
    if (this_present_screen_configs || that_present_screen_configs) {
      if (!(this_present_screen_configs && that_present_screen_configs))
        return false;
      if (!this.screen_configs.equals(that.screen_configs))
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

    boolean present_generation = true && (isSetGeneration());
    list.add(present_generation);
    if (present_generation)
      list.add(generation);

    boolean present_screen_configs = true && (isSetScreen_configs());
    list.add(present_screen_configs);
    if (present_screen_configs)
      list.add(screen_configs);

    boolean present_artifact_sources = true && (isSetArtifact_sources());
    list.add(present_artifact_sources);
    if (present_artifact_sources)
      list.add(artifact_sources);

    boolean present_artifacts = true && (isSetArtifacts());
    list.add(present_artifacts);
    if (present_artifacts)
      list.add(artifacts);

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
    sb.append("screen_configs:");
    if (this.screen_configs == null) {
      sb.append("null");
    } else {
      sb.append(this.screen_configs);
    }
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
    sb.append("artifacts:");
    if (this.artifacts == null) {
      sb.append("null");
    } else {
      sb.append(this.artifacts);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (generation == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'generation' was not present! Struct: " + toString());
    }
    if (screen_configs == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'screen_configs' was not present! Struct: " + toString());
    }
    if (artifact_sources == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'artifact_sources' was not present! Struct: " + toString());
    }
    if (artifacts == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'artifacts' was not present! Struct: " + toString());
    }
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
          case 2: // SCREEN_CONFIGS
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map0 = iprot.readMapBegin();
                struct.screen_configs = new HashMap<Long,com.zigzag.common.model.ScreenConfig>(2*_map0.size);
                long _key1;
                com.zigzag.common.model.ScreenConfig _val2;
                for (int _i3 = 0; _i3 < _map0.size; ++_i3)
                {
                  _key1 = iprot.readI64();
                  _val2 = new com.zigzag.common.model.ScreenConfig();
                  _val2.read(iprot);
                  struct.screen_configs.put(_key1, _val2);
                }
                iprot.readMapEnd();
              }
              struct.setScreen_configsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // ARTIFACT_SOURCES
            if (schemeField.type == org.apache.thrift.protocol.TType.MAP) {
              {
                org.apache.thrift.protocol.TMap _map4 = iprot.readMapBegin();
                struct.artifact_sources = new HashMap<Long,com.zigzag.common.model.ArtifactSource>(2*_map4.size);
                long _key5;
                com.zigzag.common.model.ArtifactSource _val6;
                for (int _i7 = 0; _i7 < _map4.size; ++_i7)
                {
                  _key5 = iprot.readI64();
                  _val6 = new com.zigzag.common.model.ArtifactSource();
                  _val6.read(iprot);
                  struct.artifact_sources.put(_key5, _val6);
                }
                iprot.readMapEnd();
              }
              struct.setArtifact_sourcesIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // ARTIFACTS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list8 = iprot.readListBegin();
                struct.artifacts = new ArrayList<com.zigzag.common.model.Artifact>(_list8.size);
                com.zigzag.common.model.Artifact _elem9;
                for (int _i10 = 0; _i10 < _list8.size; ++_i10)
                {
                  _elem9 = new com.zigzag.common.model.Artifact();
                  _elem9.read(iprot);
                  struct.artifacts.add(_elem9);
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
      if (struct.screen_configs != null) {
        oprot.writeFieldBegin(SCREEN_CONFIGS_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, struct.screen_configs.size()));
          for (Map.Entry<Long, com.zigzag.common.model.ScreenConfig> _iter11 : struct.screen_configs.entrySet())
          {
            oprot.writeI64(_iter11.getKey());
            _iter11.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.artifact_sources != null) {
        oprot.writeFieldBegin(ARTIFACT_SOURCES_FIELD_DESC);
        {
          oprot.writeMapBegin(new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, struct.artifact_sources.size()));
          for (Map.Entry<Long, com.zigzag.common.model.ArtifactSource> _iter12 : struct.artifact_sources.entrySet())
          {
            oprot.writeI64(_iter12.getKey());
            _iter12.getValue().write(oprot);
          }
          oprot.writeMapEnd();
        }
        oprot.writeFieldEnd();
      }
      if (struct.artifacts != null) {
        oprot.writeFieldBegin(ARTIFACTS_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.artifacts.size()));
          for (com.zigzag.common.model.Artifact _iter13 : struct.artifacts)
          {
            _iter13.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
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
      {
        oprot.writeI32(struct.screen_configs.size());
        for (Map.Entry<Long, com.zigzag.common.model.ScreenConfig> _iter14 : struct.screen_configs.entrySet())
        {
          oprot.writeI64(_iter14.getKey());
          _iter14.getValue().write(oprot);
        }
      }
      {
        oprot.writeI32(struct.artifact_sources.size());
        for (Map.Entry<Long, com.zigzag.common.model.ArtifactSource> _iter15 : struct.artifact_sources.entrySet())
        {
          oprot.writeI64(_iter15.getKey());
          _iter15.getValue().write(oprot);
        }
      }
      {
        oprot.writeI32(struct.artifacts.size());
        for (com.zigzag.common.model.Artifact _iter16 : struct.artifacts)
        {
          _iter16.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, NextGenResponse struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.generation = new com.zigzag.common.model.Generation();
      struct.generation.read(iprot);
      struct.setGenerationIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map17 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.screen_configs = new HashMap<Long,com.zigzag.common.model.ScreenConfig>(2*_map17.size);
        long _key18;
        com.zigzag.common.model.ScreenConfig _val19;
        for (int _i20 = 0; _i20 < _map17.size; ++_i20)
        {
          _key18 = iprot.readI64();
          _val19 = new com.zigzag.common.model.ScreenConfig();
          _val19.read(iprot);
          struct.screen_configs.put(_key18, _val19);
        }
      }
      struct.setScreen_configsIsSet(true);
      {
        org.apache.thrift.protocol.TMap _map21 = new org.apache.thrift.protocol.TMap(org.apache.thrift.protocol.TType.I64, org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.artifact_sources = new HashMap<Long,com.zigzag.common.model.ArtifactSource>(2*_map21.size);
        long _key22;
        com.zigzag.common.model.ArtifactSource _val23;
        for (int _i24 = 0; _i24 < _map21.size; ++_i24)
        {
          _key22 = iprot.readI64();
          _val23 = new com.zigzag.common.model.ArtifactSource();
          _val23.read(iprot);
          struct.artifact_sources.put(_key22, _val23);
        }
      }
      struct.setArtifact_sourcesIsSet(true);
      {
        org.apache.thrift.protocol.TList _list25 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.artifacts = new ArrayList<com.zigzag.common.model.Artifact>(_list25.size);
        com.zigzag.common.model.Artifact _elem26;
        for (int _i27 = 0; _i27 < _list25.size; ++_i27)
        {
          _elem26 = new com.zigzag.common.model.Artifact();
          _elem26.read(iprot);
          struct.artifacts.add(_elem26);
        }
      }
      struct.setArtifactsIsSet(true);
    }
  }

}

