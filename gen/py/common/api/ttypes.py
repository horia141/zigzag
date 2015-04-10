#
# Autogenerated by Thrift Compiler (0.9.2)
#
# DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
#
#  options string: py
#

from thrift.Thrift import TType, TMessageType, TException, TApplicationException
import common.model.ttypes


from thrift.transport import TTransport
from thrift.protocol import TBinaryProtocol, TProtocol
try:
  from thrift.protocol import fastbinary
except:
  fastbinary = None



class NextGenResponse:
  """
  Attributes:
   - generation
   - screen_configs
   - artifact_sources
   - artifacts
  """

  thrift_spec = (
    None, # 0
    (1, TType.STRUCT, 'generation', (common.model.ttypes.Generation, common.model.ttypes.Generation.thrift_spec), None, ), # 1
    (2, TType.MAP, 'screen_configs', (TType.I64,None,TType.STRUCT,(common.model.ttypes.ScreenConfig, common.model.ttypes.ScreenConfig.thrift_spec)), None, ), # 2
    (3, TType.MAP, 'artifact_sources', (TType.I64,None,TType.STRUCT,(common.model.ttypes.ArtifactSource, common.model.ttypes.ArtifactSource.thrift_spec)), None, ), # 3
    (4, TType.LIST, 'artifacts', (TType.STRUCT,(common.model.ttypes.Artifact, common.model.ttypes.Artifact.thrift_spec)), None, ), # 4
  )

  def __init__(self, generation=None, screen_configs=None, artifact_sources=None, artifacts=None,):
    self.generation = generation
    self.screen_configs = screen_configs
    self.artifact_sources = artifact_sources
    self.artifacts = artifacts

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
        if ftype == TType.MAP:
          self.screen_configs = {}
          (_ktype1, _vtype2, _size0 ) = iprot.readMapBegin()
          for _i4 in xrange(_size0):
            _key5 = iprot.readI64();
            _val6 = common.model.ttypes.ScreenConfig()
            _val6.read(iprot)
            self.screen_configs[_key5] = _val6
          iprot.readMapEnd()
        else:
          iprot.skip(ftype)
      elif fid == 3:
        if ftype == TType.MAP:
          self.artifact_sources = {}
          (_ktype8, _vtype9, _size7 ) = iprot.readMapBegin()
          for _i11 in xrange(_size7):
            _key12 = iprot.readI64();
            _val13 = common.model.ttypes.ArtifactSource()
            _val13.read(iprot)
            self.artifact_sources[_key12] = _val13
          iprot.readMapEnd()
        else:
          iprot.skip(ftype)
      elif fid == 4:
        if ftype == TType.LIST:
          self.artifacts = []
          (_etype17, _size14) = iprot.readListBegin()
          for _i18 in xrange(_size14):
            _elem19 = common.model.ttypes.Artifact()
            _elem19.read(iprot)
            self.artifacts.append(_elem19)
          iprot.readListEnd()
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
    if self.screen_configs is not None:
      oprot.writeFieldBegin('screen_configs', TType.MAP, 2)
      oprot.writeMapBegin(TType.I64, TType.STRUCT, len(self.screen_configs))
      for kiter20,viter21 in self.screen_configs.items():
        oprot.writeI64(kiter20)
        viter21.write(oprot)
      oprot.writeMapEnd()
      oprot.writeFieldEnd()
    if self.artifact_sources is not None:
      oprot.writeFieldBegin('artifact_sources', TType.MAP, 3)
      oprot.writeMapBegin(TType.I64, TType.STRUCT, len(self.artifact_sources))
      for kiter22,viter23 in self.artifact_sources.items():
        oprot.writeI64(kiter22)
        viter23.write(oprot)
      oprot.writeMapEnd()
      oprot.writeFieldEnd()
    if self.artifacts is not None:
      oprot.writeFieldBegin('artifacts', TType.LIST, 4)
      oprot.writeListBegin(TType.STRUCT, len(self.artifacts))
      for iter24 in self.artifacts:
        iter24.write(oprot)
      oprot.writeListEnd()
      oprot.writeFieldEnd()
    oprot.writeFieldStop()
    oprot.writeStructEnd()

  def validate(self):
    if self.generation is None:
      raise TProtocol.TProtocolException(message='Required field generation is unset!')
    if self.screen_configs is None:
      raise TProtocol.TProtocolException(message='Required field screen_configs is unset!')
    if self.artifact_sources is None:
      raise TProtocol.TProtocolException(message='Required field artifact_sources is unset!')
    if self.artifacts is None:
      raise TProtocol.TProtocolException(message='Required field artifacts is unset!')
    return


  def __hash__(self):
    value = 17
    value = (value * 31) ^ hash(self.generation)
    value = (value * 31) ^ hash(self.screen_configs)
    value = (value * 31) ^ hash(self.artifact_sources)
    value = (value * 31) ^ hash(self.artifacts)
    return value

  def __repr__(self):
    L = ['%s=%r' % (key, value)
      for key, value in self.__dict__.iteritems()]
    return '%s(%s)' % (self.__class__.__name__, ', '.join(L))

  def __eq__(self, other):
    return isinstance(other, self.__class__) and self.__dict__ == other.__dict__

  def __ne__(self, other):
    return not (self == other)
