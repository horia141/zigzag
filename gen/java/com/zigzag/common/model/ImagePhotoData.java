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
@Generated(value = "Autogenerated by Thrift Compiler (0.9.2)", date = "2015-7-7")
public class ImagePhotoData implements org.apache.thrift.TBase<ImagePhotoData, ImagePhotoData._Fields>, java.io.Serializable, Cloneable, Comparable<ImagePhotoData> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ImagePhotoData");

  private static final org.apache.thrift.protocol.TField TILES_FIELD_DESC = new org.apache.thrift.protocol.TField("tiles", org.apache.thrift.protocol.TType.LIST, (short)1);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new ImagePhotoDataStandardSchemeFactory());
    schemes.put(TupleScheme.class, new ImagePhotoDataTupleSchemeFactory());
  }

  public List<TileData> tiles; // required

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    TILES((short)1, "tiles");

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
        case 1: // TILES
          return TILES;
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
    tmpMap.put(_Fields.TILES, new org.apache.thrift.meta_data.FieldMetaData("tiles", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TileData.class))));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ImagePhotoData.class, metaDataMap);
  }

  public ImagePhotoData() {
  }

  public ImagePhotoData(
    List<TileData> tiles)
  {
    this();
    this.tiles = tiles;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public ImagePhotoData(ImagePhotoData other) {
    if (other.isSetTiles()) {
      List<TileData> __this__tiles = new ArrayList<TileData>(other.tiles.size());
      for (TileData other_element : other.tiles) {
        __this__tiles.add(new TileData(other_element));
      }
      this.tiles = __this__tiles;
    }
  }

  public ImagePhotoData deepCopy() {
    return new ImagePhotoData(this);
  }

  @Override
  public void clear() {
    this.tiles = null;
  }

  public int getTilesSize() {
    return (this.tiles == null) ? 0 : this.tiles.size();
  }

  public java.util.Iterator<TileData> getTilesIterator() {
    return (this.tiles == null) ? null : this.tiles.iterator();
  }

  public void addToTiles(TileData elem) {
    if (this.tiles == null) {
      this.tiles = new ArrayList<TileData>();
    }
    this.tiles.add(elem);
  }

  public List<TileData> getTiles() {
    return this.tiles;
  }

  public ImagePhotoData setTiles(List<TileData> tiles) {
    this.tiles = tiles;
    return this;
  }

  public void unsetTiles() {
    this.tiles = null;
  }

  /** Returns true if field tiles is set (has been assigned a value) and false otherwise */
  public boolean isSetTiles() {
    return this.tiles != null;
  }

  public void setTilesIsSet(boolean value) {
    if (!value) {
      this.tiles = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case TILES:
      if (value == null) {
        unsetTiles();
      } else {
        setTiles((List<TileData>)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case TILES:
      return getTiles();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case TILES:
      return isSetTiles();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof ImagePhotoData)
      return this.equals((ImagePhotoData)that);
    return false;
  }

  public boolean equals(ImagePhotoData that) {
    if (that == null)
      return false;

    boolean this_present_tiles = true && this.isSetTiles();
    boolean that_present_tiles = true && that.isSetTiles();
    if (this_present_tiles || that_present_tiles) {
      if (!(this_present_tiles && that_present_tiles))
        return false;
      if (!this.tiles.equals(that.tiles))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_tiles = true && (isSetTiles());
    list.add(present_tiles);
    if (present_tiles)
      list.add(tiles);

    return list.hashCode();
  }

  @Override
  public int compareTo(ImagePhotoData other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetTiles()).compareTo(other.isSetTiles());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTiles()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tiles, other.tiles);
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
    StringBuilder sb = new StringBuilder("ImagePhotoData(");
    boolean first = true;

    sb.append("tiles:");
    if (this.tiles == null) {
      sb.append("null");
    } else {
      sb.append(this.tiles);
    }
    first = false;
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (tiles == null) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'tiles' was not present! Struct: " + toString());
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

  private static class ImagePhotoDataStandardSchemeFactory implements SchemeFactory {
    public ImagePhotoDataStandardScheme getScheme() {
      return new ImagePhotoDataStandardScheme();
    }
  }

  private static class ImagePhotoDataStandardScheme extends StandardScheme<ImagePhotoData> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, ImagePhotoData struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // TILES
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list0 = iprot.readListBegin();
                struct.tiles = new ArrayList<TileData>(_list0.size);
                TileData _elem1;
                for (int _i2 = 0; _i2 < _list0.size; ++_i2)
                {
                  _elem1 = new TileData();
                  _elem1.read(iprot);
                  struct.tiles.add(_elem1);
                }
                iprot.readListEnd();
              }
              struct.setTilesIsSet(true);
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

    public void write(org.apache.thrift.protocol.TProtocol oprot, ImagePhotoData struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.tiles != null) {
        oprot.writeFieldBegin(TILES_FIELD_DESC);
        {
          oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.tiles.size()));
          for (TileData _iter3 : struct.tiles)
          {
            _iter3.write(oprot);
          }
          oprot.writeListEnd();
        }
        oprot.writeFieldEnd();
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class ImagePhotoDataTupleSchemeFactory implements SchemeFactory {
    public ImagePhotoDataTupleScheme getScheme() {
      return new ImagePhotoDataTupleScheme();
    }
  }

  private static class ImagePhotoDataTupleScheme extends TupleScheme<ImagePhotoData> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, ImagePhotoData struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      {
        oprot.writeI32(struct.tiles.size());
        for (TileData _iter4 : struct.tiles)
        {
          _iter4.write(oprot);
        }
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, ImagePhotoData struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      {
        org.apache.thrift.protocol.TList _list5 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
        struct.tiles = new ArrayList<TileData>(_list5.size);
        TileData _elem6;
        for (int _i7 = 0; _i7 < _list5.size; ++_i7)
        {
          _elem6 = new TileData();
          _elem6.read(iprot);
          struct.tiles.add(_elem6);
        }
      }
      struct.setTilesIsSet(true);
    }
  }

}

