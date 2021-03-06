import json
import os
import os.path
import shutil

from PIL import Image

import generators


class _Profile(object):
    def __init__(self, out_path_mod, rescale_factor):
        self.out_path_mod = out_path_mod
        self.rescale_factor = rescale_factor

    def modify_path(self, path):
        if self.out_path_mod == '1x':
            (root_path, ext_path) = os.path.splitext(path)
            return '%s%s' % (root_path, '.png')
        else:
            (root_path, ext_path) = os.path.splitext(path)
            return '%s@%s%s' % (root_path, self.out_path_mod, '.png')


_PROFILES = {
    '1x': _Profile('1x', 29.0 / 60.0),
    '2x': _Profile('2x', 40.0 / 60.0),
    '3x': _Profile('3x', 60.0 / 60.0)
    }


_MODIFIER_FOR_USAGE = {
    'appicon': 'appiconset',
    'launcher': 'launchimage',
    'asset': 'imageset'
    }


class _PngHandler(generators._Handler):
    def __init__(self):
        super(_PngHandler, self).__init__('.png')

    def __call__(self, output_dir_path, g_name, g_config, path):
        (g_name_base, g_name_ext) = os.path.splitext(g_name)
        dir_path = os.path.join(output_dir_path,
            '%s.%s' % (g_name_base, _MODIFIER_FOR_USAGE[g_config['usage']]))
        base_out_path = os.path.join(dir_path, g_name)
        os.mkdir(dir_path)

        metadata = {
            'images': [],
            'info': {
                'version': 1,
                'author': 'xcode' # Should be the assets pipeline.
                }
            }

        for p_name, profile in _PROFILES.iteritems():
            out_path = profile.modify_path(base_out_path)
            image = Image.open(path)
            desired_width = int(image.size[0] * profile.rescale_factor)
            image_resized = generators._resize_to_width(image, desired_width)
            image_resized[0].save(out_path)
            metadata['images'].append({
                    'idiom': 'universal',
                    'scale': profile.out_path_mod,
                    'filename': os.path.basename(out_path)
                    })

        with open(os.path.join(dir_path, 'Contents.json'), 'w') as contents_file:
            contents_file.write(json.dumps(metadata))


class _GifHandler(generators._Handler):
    def __init__(self):
        super(_GifHandler, self).__init__('.gif')

    def __call__(self, output_dir_path, g_name, g_config, path):
        (g_name_base, g_name_ext) = os.path.splitext(g_name)        
        dir_path_pattern = os.path.join(output_dir_path,
            '%s%%02d.imageset' % g_name_base)
        g_name_pattern = '%s%%02d%s' % (g_name_base, g_name_ext)
        base_out_path_pattern = os.path.join(dir_path_pattern, g_name_pattern)

        fr = 0
        image = Image.open(path)
        while True:
            try:
                image.seek(fr)
            except EOFError:
                break

            dir_path = dir_path_pattern % fr
            os.mkdir(dir_path)
            metadata = {
                'images': [],
                'info': {
                    'version': 1,
                    'author': 'xcode' # Should be the assets pipeline.
                    }
                }

            for g_name, profile in _PROFILES.iteritems():
                out_path = profile.modify_path(base_out_path_pattern % (fr, fr))
                frame = image.copy()
                desired_width = int(frame.size[0] * profile.rescale_factor)
                frame_resized = generators._resize_to_width(image, desired_width)
                frame_resized[0].save(out_path)
                metadata['images'].append({
                        'idiom': 'universal',
                        'scale': profile.out_path_mod,
                        'filename': os.path.basename(out_path)
                        })

                with open(os.path.join(dir_path, 'Contents.json'), 'w') as contents_file:
                    contents_file.write(json.dumps(metadata))
            
            fr = fr + 1


_HANDLERS = {
    '.png': _PngHandler(),
    '.gif': _GifHandler()
}


class Generator(generators.BaseGenerator):
    def __init__(self, output_dir_path):
        super(Generator, self).__init__(output_dir_path)

    def __enter__(self):
        # Recreate the directory for graphics assets. In iOS, this directory is completely under
        # the application's control, so we can just recreate it without issues at this point.
        generators._recreate_directory(self.output_dir_path)

        return self

    def generate(self, g_name, g_config):
        path = g_config['path']
        path_type = os.path.splitext(path)[1]
        _HANDLERS[path_type](self.output_dir_path, g_name, g_config, path)
