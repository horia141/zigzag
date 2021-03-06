#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py:new_style
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
from ttypes import *
from thrift.Thrift import TProcessor
from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None


class Iface(object):
  def fetch_url(self, page_url):
    """
    Parameters:
     - page_url
    """
    pass

  def fetch_url_mimetype(self, page_url):
    """
    Parameters:
     - page_url
    """
    pass

  def fetch_photo(self, photo_url):
    """
    Parameters:
     - photo_url
    """
    pass


class Client(Iface):
  def __init__(self, iprot, oprot=None):
    self._iprot = self._oprot = iprot
    if oprot is not None:
      self._oprot = oprot
    self._seqid = 0

  def fetch_url(self, page_url):
    """
    Parameters:
     - page_url
    """
    self.send_fetch_url(page_url)
    return self.recv_fetch_url()

  def send_fetch_url(self, page_url):
    self._oprot.writeMessageBegin('fetch_url', TMessageType.CALL, self._seqid)
    args = fetch_url_args()
    args.page_url = page_url
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_fetch_url(self):
    iprot = self._iprot
    (fname, mtype, rseqid) = iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(iprot)
      iprot.readMessageEnd()
      raise x
    result = fetch_url_result()
    result.read(iprot)
    iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    if result.io is not None:
      raise result.io
    raise TApplicationException(TApplicationException.MISSING_RESULT, "fetch_url failed: unknown result");

  def fetch_url_mimetype(self, page_url):
    """
    Parameters:
     - page_url
    """
    self.send_fetch_url_mimetype(page_url)
    return self.recv_fetch_url_mimetype()

  def send_fetch_url_mimetype(self, page_url):
    self._oprot.writeMessageBegin('fetch_url_mimetype', TMessageType.CALL, self._seqid)
    args = fetch_url_mimetype_args()
    args.page_url = page_url
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_fetch_url_mimetype(self):
    iprot = self._iprot
    (fname, mtype, rseqid) = iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(iprot)
      iprot.readMessageEnd()
      raise x
    result = fetch_url_mimetype_result()
    result.read(iprot)
    iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    if result.io is not None:
      raise result.io
    raise TApplicationException(TApplicationException.MISSING_RESULT, "fetch_url_mimetype failed: unknown result");

  def fetch_photo(self, photo_url):
    """
    Parameters:
     - photo_url
    """
    self.send_fetch_photo(photo_url)
    return self.recv_fetch_photo()

  def send_fetch_photo(self, photo_url):
    self._oprot.writeMessageBegin('fetch_photo', TMessageType.CALL, self._seqid)
    args = fetch_photo_args()
    args.photo_url = photo_url
    args.write(self._oprot)
    self._oprot.writeMessageEnd()
    self._oprot.trans.flush()

  def recv_fetch_photo(self):
    iprot = self._iprot
    (fname, mtype, rseqid) = iprot.readMessageBegin()
    if mtype == TMessageType.EXCEPTION:
      x = TApplicationException()
      x.read(iprot)
      iprot.readMessageEnd()
      raise x
    result = fetch_photo_result()
    result.read(iprot)
    iprot.readMessageEnd()
    if result.success is not None:
      return result.success
    if result.io is not None:
      raise result.io
    if result.big is not None:
      raise result.big
    raise TApplicationException(TApplicationException.MISSING_RESULT, "fetch_photo failed: unknown result");


class Processor(Iface, TProcessor):
  def __init__(self, handler):
    self._handler = handler
    self._processMap = {}
    self._processMap["fetch_url"] = Processor.process_fetch_url
    self._processMap["fetch_url_mimetype"] = Processor.process_fetch_url_mimetype
    self._processMap["fetch_photo"] = Processor.process_fetch_photo

  def process(self, iprot, oprot):
    (name, type, seqid) = iprot.readMessageBegin()
    if name not in self._processMap:
      iprot.skip(TType.STRUCT)
      iprot.readMessageEnd()
      x = TApplicationException(TApplicationException.UNKNOWN_METHOD, 'Unknown function %s' % (name))
      oprot.writeMessageBegin(name, TMessageType.EXCEPTION, seqid)
      x.write(oprot)
      oprot.writeMessageEnd()
      oprot.trans.flush()
      return
    else:
      self._processMap[name](self, seqid, iprot, oprot)
    return True

  def process_fetch_url(self, seqid, iprot, oprot):
    args = fetch_url_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = fetch_url_result()
    try:
      result.success = self._handler.fetch_url(args.page_url)
    except IOError, io:
      result.io = io
    oprot.writeMessageBegin("fetch_url", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_fetch_url_mimetype(self, seqid, iprot, oprot):
    args = fetch_url_mimetype_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = fetch_url_mimetype_result()
    try:
      result.success = self._handler.fetch_url_mimetype(args.page_url)
    except IOError, io:
      result.io = io
    oprot.writeMessageBegin("fetch_url_mimetype", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()

  def process_fetch_photo(self, seqid, iprot, oprot):
    args = fetch_photo_args()
    args.read(iprot)
    iprot.readMessageEnd()
    result = fetch_photo_result()
    try:
      result.success = self._handler.fetch_photo(args.photo_url)
    except IOError, io:
      result.io = io
    except PhotoTooBigError, big:
      result.big = big
    oprot.writeMessageBegin("fetch_photo", TMessageType.REPLY, seqid)
    result.write(oprot)
    oprot.writeMessageEnd()
    oprot.trans.flush()


# HELPER FUNCTIONS AND STRUCTURES

class fetch_url_args(object):
  """
  Attributes:
   - page_url
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'page_url', None, None, ), # 1
  )

  def __init__(self, page_url=None,):
    self.page_url = page_url

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.page_url = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_url_args')
    if self.page_url is not None:
      oprot.writeFieldBegin('page_url', TType.STRING, 1)
      oprot.writeString(self.page_url)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.page_url)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class fetch_url_result(object):
  """
  Attributes:
   - success
   - io
  """

  thrift_spec = (
    (0, TType.STRUCT, 'success', (Response, Response.thrift_spec), None, ), # 0
    (1, TType.STRUCT, 'io', (IOError, IOError.thrift_spec), None, ), # 1
  )

  def __init__(self, success=None, io=None,):
    self.success = success
    self.io = io

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.STRUCT:
          self.success = Response()
          self.success.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 1:
        if ftype == TType.STRUCT:
          self.io = IOError()
          self.io.read(iprot)
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_url_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.STRUCT, 0)
      self.success.write(oprot)
      oprot.writeFieldEnd()
    if self.io is not None:
      oprot.writeFieldBegin('io', TType.STRUCT, 1)
      self.io.write(oprot)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.success)
    value = (value * 31) ^ hash(self.io)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class fetch_url_mimetype_args(object):
  """
  Attributes:
   - page_url
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'page_url', None, None, ), # 1
  )

  def __init__(self, page_url=None,):
    self.page_url = page_url

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.page_url = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_url_mimetype_args')
    if self.page_url is not None:
      oprot.writeFieldBegin('page_url', TType.STRING, 1)
      oprot.writeString(self.page_url)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.page_url)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class fetch_url_mimetype_result(object):
  """
  Attributes:
   - success
   - io
  """

  thrift_spec = (
    (0, TType.STRUCT, 'success', (Response, Response.thrift_spec), None, ), # 0
    (1, TType.STRUCT, 'io', (IOError, IOError.thrift_spec), None, ), # 1
  )

  def __init__(self, success=None, io=None,):
    self.success = success
    self.io = io

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.STRUCT:
          self.success = Response()
          self.success.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 1:
        if ftype == TType.STRUCT:
          self.io = IOError()
          self.io.read(iprot)
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_url_mimetype_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.STRUCT, 0)
      self.success.write(oprot)
      oprot.writeFieldEnd()
    if self.io is not None:
      oprot.writeFieldBegin('io', TType.STRUCT, 1)
      self.io.write(oprot)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.success)
    value = (value * 31) ^ hash(self.io)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class fetch_photo_args(object):
  """
  Attributes:
   - photo_url
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRING, 'photo_url', None, None, ), # 1
  )

  def __init__(self, photo_url=None,):
    self.photo_url = photo_url

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 1:
        if ftype == TType.STRING:
          self.photo_url = iprot.readString();
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_photo_args')
    if self.photo_url is not None:
      oprot.writeFieldBegin('photo_url', TType.STRING, 1)
      oprot.writeString(self.photo_url)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.photo_url)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)

class fetch_photo_result(object):
  """
  Attributes:
   - success
   - io
   - big
  """

  thrift_spec = (
    (0, TType.STRUCT, 'success', (Response, Response.thrift_spec), None, ), # 0
    (1, TType.STRUCT, 'io', (IOError, IOError.thrift_spec), None, ), # 1
    (2, TType.STRUCT, 'big', (PhotoTooBigError, PhotoTooBigError.thrift_spec), None, ), # 2
  )

  def __init__(self, success=None, io=None, big=None,):
    self.success = success
    self.io = io
    self.big = big

  def read(self, iprot):
    if iprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and isinstance(iprot.trans, TTransport.CReadableTransport) and self.thrift_spec is not None and fastbinary is not None:
      fastbinary.decode_binary(self, iprot.trans, (self.__class__, self.thrift_spec))
      return
    iprot.readStructBegin()
    while True:
      (fname, ftype, fid) = iprot.readFieldBegin()
      if ftype == TType.STOP:
        break
      if fid == 0:
        if ftype == TType.STRUCT:
          self.success = Response()
          self.success.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 1:
        if ftype == TType.STRUCT:
          self.io = IOError()
          self.io.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.STRUCT:
          self.big = PhotoTooBigError()
          self.big.read(iprot)
        else:
          iprot.skip(ftype)
      else:
        iprot.skip(ftype)
      iprot.readFieldEnd()
    iprot.readStructEnd()

  def write(self, oprot):
    if oprot.__class__ == TBinaryProtocol.TBinaryProtocolAccelerated and self.thrift_spec is not None and fastbinary is not None:
      oprot.trans.write(fastbinary.encode_binary(self, (self.__class__, self.thrift_spec)))
      return
    oprot.writeStructBegin('fetch_photo_result')
    if self.success is not None:
      oprot.writeFieldBegin('success', TType.STRUCT, 0)
      self.success.write(oprot)
      oprot.writeFieldEnd()
    if self.io is not None:
      oprot.writeFieldBegin('io', TType.STRUCT, 1)
      self.io.write(oprot)
      oprot.writeFieldEnd()
    if self.big is not None:
      oprot.writeFieldBegin('big', TType.STRUCT, 2)
      self.big.write(oprot)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.success)
    value = (value * 31) ^ hash(self.io)
    value = (value * 31) ^ hash(self.big)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
