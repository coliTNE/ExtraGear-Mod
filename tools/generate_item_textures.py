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

def generate_pebble(out_dir):
    """Small rounded pebble with 3-tone shading and surface speckle."""
    c = new_canvas()

    H = rgb(210, 205, 200)   # highlight (top-left lit)
    B = rgb(160, 155, 150)   # base (body)
    S = rgb(105, 100, 95)    # shadow (bottom-right)
    K = rgb(130, 125, 120)   # speckle (surface detail)
    D = rgb(85, 80, 75)      # dark edge accent

    # Silhouette: small oval ~7x5px, centered in canvas
    # Center of 16x16 is at (7.5, 7.5), so oval spans rows 5-9, cols 4-10
    shape = {
        5:  range(5, 10),       # narrow top (5 wide)
        6:  range(4, 11),       # wider (7 wide)
        7:  range(4, 11),       # widest row
        8:  range(4, 11),       # wider
        9:  range(5, 10),       # narrow bottom (5 wide)
    }

    # Fill all with base
    for y, xs in shape.items():
        for x in xs:
            px(c, x, y, B)

    # Highlight: top edge + top-left (light from top-left)
    for x in range(5, 10):
        px(c, x, 5, H)
    px(c, 4, 6, H)
    px(c, 5, 6, H)
    px(c, 4, 7, H)

    # Shadow: bottom edge + bottom-right
    for x in range(6, 10):
        px(c, x, 9, S)
    px(c, 10, 8, S)
    px(c, 10, 7, S)

    # Dark accent on bottom-right corner
    px(c, 9, 9, D)

    # Surface speckle
    for (sx, sy) in [(7, 6), (9, 7), (6, 8)]:
        px(c, sx, sy, K)

    save_png(os.path.join(out_dir, 'pebble.png'), c)


# -- placeholder: generate_plant_fiber will be added later --
# -- placeholder: generate_strange_branch will be added later --
# -- placeholder: generate_primitive_axe will be added later --


GENERATORS = {
    'pebble': generate_pebble,
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
