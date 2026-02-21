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


def generate_plant_fiber(out_dir):
    """Three organic fiber strands with subtle curves and frayed ends."""
    c = new_canvas()

    # Palette: 3 green tones per strand + pale highlight
    G1 = rgb(105, 155, 50)    # strand 1 — warm green
    G2 = rgb(80, 135, 40)     # strand 2 — base green
    G3 = rgb(120, 150, 45)    # strand 3 — yellow-green
    GH = rgb(145, 185, 80)    # highlight (top tips)
    GD = rgb(55, 95, 25)      # dark (shadow, frayed ends)

    # Left strand (x~4-5, curves slightly right in middle)
    strand_l = [
        (4, 2, GH),  # tip highlight
        (4, 3, G1), (5, 3, G1),
        (5, 4, G1),
        (5, 5, G1), (6, 5, GD),
        (5, 6, G1),
        (5, 7, G1),
        (4, 8, G1),
        (4, 9, G1), (5, 9, GD),
        (4, 10, G1),
        (3, 11, G1),
        (3, 12, GD),  # frayed end left
        (5, 12, GD),  # frayed end right
    ]

    # Center strand (x~7-8, mostly straight)
    strand_c = [
        (7, 1, GH),  # tip highlight
        (7, 2, G2), (8, 2, G2),
        (7, 3, G2),
        (7, 4, G2), (8, 4, GD),
        (8, 5, G2),
        (8, 6, G2),
        (7, 7, G2),
        (7, 8, G2), (8, 8, GD),
        (7, 9, G2),
        (7, 10, G2),
        (8, 11, G2),
        (7, 12, GD),  # frayed
        (9, 12, GD),  # frayed
        (8, 13, GD),
    ]

    # Right strand (x~10-11, curves slightly left)
    strand_r = [
        (11, 3, GH),  # tip highlight
        (11, 4, G3), (10, 4, G3),
        (10, 5, G3),
        (10, 6, G3), (11, 6, GD),
        (10, 7, G3),
        (11, 8, G3),
        (11, 9, G3),
        (11, 10, G3), (10, 10, GD),
        (11, 11, G3),
        (11, 12, GD),  # frayed
        (12, 13, GD),  # frayed end
    ]

    for strand in (strand_l, strand_c, strand_r):
        for (x, y, color) in strand:
            px(c, x, y, color)

    save_png(os.path.join(out_dir, 'plant_fiber.png'), c)


def generate_strange_branch(out_dir):
    """Diagonal Y-shaped forked branch — slingshot handle, bottom-left to top-right."""
    c = new_canvas()

    # Palette: darker brown to match branch.png
    H = rgb(170, 140, 90)    # highlight (upper edge)
    B = rgb(125, 90, 50)     # base (body)
    S = rgb(80, 55, 30)      # shadow (lower edge)
    K = rgb(60, 40, 20)      # knot / dark accent at fork
    T = rgb(150, 120, 75)    # mid-tone (grain detail)

    # Trunk: diagonal from bottom-left to fork, shifted up for centering
    trunk = [
        (3, 13), (4, 12), (5, 11), (6, 10), (7, 9),
    ]
    for (x, y) in trunk:
        px(c, x, y, B)
        px(c, x, y - 1, H)
        px(c, x + 1, y, S)

    # Taper at base
    px(c, 3, 12, TRANSPARENT)

    # Fork junction at (8, 8) — knot
    px(c, 7, 8, H)
    px(c, 8, 8, K)
    px(c, 9, 8, S)

    # Right prong: continues diagonal up-right (longer)
    prong_r = [(9, 7), (10, 6), (10, 5), (11, 4)]
    for (x, y) in prong_r:
        px(c, x, y, B)
        px(c, x, y - 1, H)
    px(c, 11, 3, T)  # tip

    # Left prong: more vertical, shorter, closer to right
    prong_l = [(7, 7), (7, 6), (6, 5)]
    for (x, y) in prong_l:
        px(c, x, y, B)
        px(c, x - 1, y, H)
    px(c, 6, 4, T)  # tip

    # Wood grain on trunk
    px(c, 5, 11, T)

    save_png(os.path.join(out_dir, 'strange_branch.png'), c)


# -- placeholder: generate_primitive_axe will be added later --


def generate_primitive_axe(out_dir):
    """Primitive axe — generates base for manual retouching in LibreSprite."""
    c = new_canvas()

    SH = rgb(185, 180, 175)
    SB = rgb(140, 135, 130)
    SS = rgb(95, 90, 85)
    SE = rgb(70, 65, 60)

    WH = rgb(155, 120, 70)
    WB = rgb(115, 82, 42)

    FB = rgb(80, 130, 40)
    FD = rgb(50, 90, 22)

    # Handle (bottom-left to upper-center)
    handle = [(2, 14), (3, 13), (4, 12), (5, 11), (6, 10), (7, 9), (8, 8)]
    for (x, y) in handle:
        px(c, x, y, WB)
        px(c, x, y - 1, WH)
    px(c, 2, 13, TRANSPARENT)

    # Stone head (grows from handle, extends up-left)
    head = {
        3: range(3, 6),
        4: range(3, 7),
        5: range(4, 8),
        6: range(5, 9),
        7: range(6, 9),
    }
    for y, xs in head.items():
        for x in xs:
            px(c, x, y, SB)

    px(c, 3, 3, SE); px(c, 3, 4, SE); px(c, 4, 5, SE)
    for x in range(4, 6):
        px(c, x, 3, SH)
    px(c, 4, 4, SH)
    px(c, 8, 6, SS); px(c, 8, 7, SS); px(c, 7, 7, SS)

    # Fiber lashing
    px(c, 7, 8, FB); px(c, 8, 7, FD); px(c, 9, 7, FB); px(c, 9, 8, FD)

    save_png(os.path.join(out_dir, 'primitive_axe.png'), c)


GENERATORS = {
    'pebble': generate_pebble,
    'plant_fiber': generate_plant_fiber,
    'strange_branch': generate_strange_branch,
    'primitive_axe': generate_primitive_axe,
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
