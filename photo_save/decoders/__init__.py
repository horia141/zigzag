"""Decoders transform images and videos to the standard formats used internally."""

def Error(Exception):
    pass


class Decoder(object):
    def decode(self, screen_config_key, screen_config, image_raw_data, unique_image_path_fn):
        raise NotImplementedError('Tried to invoke abstract method')
