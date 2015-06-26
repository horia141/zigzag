from PIL import Image

def is_video(photo):
    try:
        photo.seek(1)
    except EOFError:
        return False
    photo.seek(0)
    return True


def resize_to_width(image, desired_width):
    (width, height) = image.size
    aspect_ratio = float(height) / float(width)
    desired_height = int(aspect_ratio * desired_width)
    desired_height = desired_height + desired_height % 2 # Always a multiple of 2.
    image_resized = image.resize((desired_width, desired_height), Image.ANTIALIAS)
    return (image_resized, desired_height)
