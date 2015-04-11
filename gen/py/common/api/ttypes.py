#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py:new_style
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import common.model.ttypes


from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class NextGenResponse(object):
  """
  Attributes:
   - generation
   - bandwidth_alert
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'generation', (common.model.ttypes.Generation, common.model.ttypes.Generation.thrift_spec), None, ), # 1
    (2, TType.BOOL, 'bandwidth_alert', None, None, ), # 2
  )

  def __init__(self, generation=None, bandwidth_alert=None,):
    self.generation = generation
    self.bandwidth_alert = bandwidth_alert

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
        if ftype == TType.STRUCT:
          self.generation = common.model.ttypes.Generation()
          self.generation.read(iprot)
        else:
          iprot.skip(ftype)
      elif fid == 2:
        if ftype == TType.BOOL:
          self.bandwidth_alert = iprot.readBool();
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
    oprot.writeStructBegin('NextGenResponse')
    if self.generation is not None:
      oprot.writeFieldBegin('generation', TType.STRUCT, 1)
      self.generation.write(oprot)
      oprot.writeFieldEnd()
    if self.bandwidth_alert is not None:
      oprot.writeFieldBegin('bandwidth_alert', TType.BOOL, 2)
      oprot.writeBool(self.bandwidth_alert)
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    if self.generation is None:
      raise TProtocol.TProtocolException(message='Required field generation is unset!')
    if self.bandwidth_alert is None:
      raise TProtocol.TProtocolException(message='Required field bandwidth_alert is unset!')
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.generation)
    value = (value * 31) ^ hash(self.bandwidth_alert)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
