"""
Generate improved 16x16 item textures for the Tempered mod.
Uses struct + zlib to create valid PNGs without PIL dependency.

Usage:
    python tools/generate_item_textures.py [texture_name]

    texture_name: pebble, plant_fiber, strange_branch, primitive_axe, or all
    Defaults to 'all' if not specified.
"""
import struct
import zlib
import os
import sys

SIZE = 16
TRANSPARENT = (0, 0, 0, 0)

OUT_DIR = os.path.join(
    os.path.dirname(os.path.dirname(os.path.abspath(__file__))),
    'src', 'main', 'resources', 'assets', 'tempered', 'textures', 'item'
)


# ---------------------------------------------------------------------------
# PNG writer (pure struct + zlib)
# ---------------------------------------------------------------------------

def _png_chunk(chunk_type, data):
    """Build a PNG chunk: length + type + data + CRC32."""
    body = chunk_type + data
    crc = zlib.crc32(body) & 0xFFFFFFFF
    return struct.pack('>I', len(data)) + body + struct.pack('>I', crc)


def make_png(pixels):
    """
    Convert a flat list of 256 (r, g, b, a) tuples into a valid 16x16 RGBA PNG.
    Row-major order: pixels[0..15] = top row, pixels[240..255] = bottom row.
    """
    # IHDR: width=16, height=16, bit_depth=8, color_type=6 (RGBA)
    ihdr = struct.pack('>IIBBBBB', SIZE, SIZE, 8, 6, 0, 0, 0)

    # IDAT: filter byte 0x00 (None) before each row, then RGBA bytes
    raw = b''
    for row in range(SIZE):
        raw += b'\x00'
        for col in range(SIZE):
            r, g, b, a = pixels[row * SIZE + col]
            raw += bytes([r, g, b, a])

    idat = zlib.compress(raw, 9)

    # Assemble: signature + IHDR + IDAT + IEND
    png = b'\x89PNG\r\n\x1a\n'
    png += _png_chunk(b'IHDR', ihdr)
    png += _png_chunk(b'IDAT', idat)
    png += _png_chunk(b'IEND', b'')
    return png


def save_png(path, pixels):
    """Write a pixel list as a PNG file."""
    data = make_png(pixels)
    with open(path, 'wb') as f:
        f.write(data)
    print(f'  Saved ({len(data)} bytes): {path}')


# ---------------------------------------------------------------------------
# Canvas helpers
# ---------------------------------------------------------------------------

def new_canvas():
    """16x16 transparent canvas."""
    return [TRANSPARENT] * (SIZE * SIZE)


def px(canvas, x, y, rgba):
    """Set pixel at (x, y) if in bounds."""
    if 0 <= x < SIZE and 0 <= y < SIZE:
        canvas[y * SIZE + x] = rgba


def rgb(r, g, b):
    """Fully opaque RGBA tuple."""
    return (r, g, b, 255)


# ---------------------------------------------------------------------------
# Texture generators (added incrementally)
# ---------------------------------------------------------------------------

# -- placeholder: generate_pebble will be added in next commit --
# -- placeholder: generate_plant_fiber will be added later --
# -- placeholder: generate_strange_branch will be added later --
# -- placeholder: generate_primitive_axe will be added later --


GENERATORS = {
    # Will be populated as each texture is implemented:
    # 'pebble': generate_pebble,
    # 'plant_fiber': generate_plant_fiber,
    # 'strange_branch': generate_strange_branch,
    # 'primitive_axe': generate_primitive_axe,
}


def main():
    os.makedirs(OUT_DIR, exist_ok=True)

    target = sys.argv[1] if len(sys.argv) > 1 else 'all'

    if target == 'all':
        for name, gen in GENERATORS.items():
            gen(OUT_DIR)
    elif target in GENERATORS:
        GENERATORS[target](OUT_DIR)
    else:
        print(f'Unknown texture: {target}')
        print(f'Available: {", ".join(GENERATORS.keys())} or all')
        sys.exit(1)

    print('Done.')


if __name__ == '__main__':
    main()
