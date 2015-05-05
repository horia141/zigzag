/**
 * Autogenerated by Thrift Compiler (0.9.2)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package com.zigzag.common.defines;

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
public class definesConstants {

  public static final String TIME_FORMAT = "%B %d, %Y %I:%M:%S %p %Z";

  public static final int MAX_ARTIFACTS_PER_GENERATION = 32;

  public static final Set<String> WEBPAGE_MIMETYPES = new HashSet<String>();
  static {
    WEBPAGE_MIMETYPES.add("application/xhtml+xml");
    WEBPAGE_MIMETYPES.add("text/html");
    WEBPAGE_MIMETYPES.add("text/plain");
  }

  public static final Set<String> PHOTO_MIMETYPES = new HashSet<String>();
  static {
    PHOTO_MIMETYPES.add("image/gif");
    PHOTO_MIMETYPES.add("image/jpeg");
    PHOTO_MIMETYPES.add("image/png");
  }

  public static final Map<String,String> PHOTO_MIMETYPES_TO_EXTENSION = new HashMap<String,String>();
  static {
    PHOTO_MIMETYPES_TO_EXTENSION.put("image/gif", "gif");
    PHOTO_MIMETYPES_TO_EXTENSION.put("image/jpeg", "jpg");
    PHOTO_MIMETYPES_TO_EXTENSION.put("image/png", "png");
    PHOTO_MIMETYPES_TO_EXTENSION.put("video/mp4", "mp4");
  }

  public static final Map<Long,com.zigzag.common.model.ArtifactSource> ARTIFACT_SOURCES = new HashMap<Long,com.zigzag.common.model.ArtifactSource>();
  static {
    com.zigzag.common.model.ArtifactSource tmp0 = new com.zigzag.common.model.ArtifactSource();
    tmp0.setId(1L);
    tmp0.setName("Reddit");
    tmp0.setStart_page_uri("http://reddit.com/r/%s");
    Set<String> tmp1 = new HashSet<String>();
    tmp1.add("pics");
    tmp1.add("comics");
    tmp1.add("fffffffuuuuuuuuuuuu");
    tmp1.add("ragecomics");
    tmp1.add("lolcats");
    tmp1.add("AdviceAnimals");
    tmp1.add("Demotivational");
    tmp1.add("memes");
    tmp1.add("images");
    tmp1.add("aww");
    tmp1.add("cats");
    tmp1.add("foxes");
    tmp1.add("dogpictures");
    tmp1.add("sloths");
    tmp1.add("gifs");
    tmp1.add("reactiongifs");

    tmp0.setSubdomains(tmp1);

    ARTIFACT_SOURCES.put(1L, tmp0);
    com.zigzag.common.model.ArtifactSource tmp2 = new com.zigzag.common.model.ArtifactSource();
    tmp2.setId(2L);
    tmp2.setName("Imgur");
    tmp2.setStart_page_uri("http://imgur.com");

    ARTIFACT_SOURCES.put(2L, tmp2);
    com.zigzag.common.model.ArtifactSource tmp3 = new com.zigzag.common.model.ArtifactSource();
    tmp3.setId(3L);
    tmp3.setName("9GAG");
    tmp3.setStart_page_uri("http://9gag.com/%s");
    Set<String> tmp4 = new HashSet<String>();
    tmp4.add("hot");
    tmp4.add("trending");
    tmp4.add("gif");

    tmp3.setSubdomains(tmp4);

    ARTIFACT_SOURCES.put(3L, tmp3);
  }

  public static final Map<Long,com.zigzag.common.model.ScreenConfig> VIDEO_SCREEN_CONFIG = new HashMap<Long,com.zigzag.common.model.ScreenConfig>();
  static {
    com.zigzag.common.model.ScreenConfig tmp5 = new com.zigzag.common.model.ScreenConfig();
    tmp5.setId(1L);
    tmp5.setName("480");
    tmp5.setWidth(480);

    VIDEO_SCREEN_CONFIG.put(1L, tmp5);
  }

  public static final Map<Long,com.zigzag.common.model.ScreenConfig> IMAGE_SCREEN_CONFIG = new HashMap<Long,com.zigzag.common.model.ScreenConfig>();
  static {
    com.zigzag.common.model.ScreenConfig tmp6 = new com.zigzag.common.model.ScreenConfig();
    tmp6.setId(2L);
    tmp6.setName("800");
    tmp6.setWidth(800);

    IMAGE_SCREEN_CONFIG.put(2L, tmp6);
  }

  public static final int IMAGE_SAVE_JPEG_OPTIONS_QUALITY = 50;

  public static final boolean IMAGE_SAVE_JPEG_OPTIONS_OPTIMIZE = true;

  public static final boolean IMAGE_SAVE_JPEG_OPTIONS_PROGRESSIVE = true;

  public static final String VIDEO_SAVE_BITRATE = "512k";

  public static final int DEFAULT_TIME_BETWEEN_FRAMES_MS = 75;

  public static final int PHOTO_MAX_WIDTH = 2048;

  public static final int PHOTO_MAX_HEIGHT = 2048;

  public static final long BANDWIDTH_ALERT_BYTES_PER_MONTH = 1073741824L;

}
