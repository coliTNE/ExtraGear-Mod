import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
 * Generates 16x16 cross-model textures for pebble and branch blocks.
 * Cross model uses two intersecting vertical planes, so objects are drawn
 * as side-view in the bottom portion of the texture with transparent background.
 */
public class GenerateCrossTextures {

    static final int SIZE = 16;
    static final int TRANSPARENT = 0x00000000;

    public static void main(String[] args) throws Exception {
        String outputDir = args.length > 0 ? args[0]
            : "src/main/resources/assets/tempered/textures/block";

        File dir = new File(outputDir);
        if (!dir.exists()) dir.mkdirs();

        // --- Pebble textures ---
        generatePebble(dir, "pebble_block_light.png",
            rgb(200, 195, 185), rgb(215, 210, 200), rgb(175, 170, 160));
        generatePebble(dir, "pebble_block_medium.png",
            rgb(140, 135, 130), rgb(160, 155, 150), rgb(120, 115, 110));
        generatePebble(dir, "pebble_block_dark.png",
            rgb(80, 75, 70), rgb(100, 95, 90), rgb(60, 55, 50));

        // --- Branch textures ---
        generateBranch(dir, "branch_block_light.png",
            rgb(210, 190, 150), rgb(225, 210, 175), rgb(185, 165, 125));
        generateBranch(dir, "branch_block_medium.png",
            rgb(150, 120, 70), rgb(170, 140, 90), rgb(130, 100, 50));
        generateBranch(dir, "branch_block_dark.png",
            rgb(80, 60, 40), rgb(100, 80, 60), rgb(60, 40, 25));

        System.out.println("All 6 cross-model textures generated in: " + dir.getAbsolutePath());
    }

    /**
     * Pebble texture: 2-3 small rounded stones scattered in the bottom 3 rows (rows 13-15).
     * Each stone is roughly 3x2 pixels with highlight on top and shadow on bottom.
     */
    static void generatePebble(File dir, String filename, int base, int highlight, int shadow)
            throws Exception {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        // Fill with transparent
        for (int y = 0; y < SIZE; y++)
            for (int x = 0; x < SIZE; x++)
                img.setRGB(x, y, TRANSPARENT);

        // Stone 1: left side, at x=2..4, rows 14-15
        // Top row (highlight)
        setPixel(img, 2, 14, highlight);
        setPixel(img, 3, 14, highlight);
        setPixel(img, 4, 14, base);
        // Bottom row (shadow)
        setPixel(img, 2, 15, base);
        setPixel(img, 3, 15, shadow);
        setPixel(img, 4, 15, shadow);

        // Stone 2: center, at x=7..10, rows 13-15 (slightly larger stone)
        // Top row
        setPixel(img, 8, 13, highlight);
        setPixel(img, 9, 13, highlight);
        // Middle row
        setPixel(img, 7, 14, highlight);
        setPixel(img, 8, 14, base);
        setPixel(img, 9, 14, base);
        setPixel(img, 10, 14, highlight);
        // Bottom row
        setPixel(img, 7, 15, shadow);
        setPixel(img, 8, 15, shadow);
        setPixel(img, 9, 15, shadow);
        setPixel(img, 10, 15, base);

        // Stone 3: right side, at x=12..14, rows 14-15
        // Top row
        setPixel(img, 12, 14, base);
        setPixel(img, 13, 14, highlight);
        setPixel(img, 14, 14, highlight);
        // Bottom row
        setPixel(img, 12, 15, shadow);
        setPixel(img, 13, 15, shadow);
        setPixel(img, 14, 15, base);

        ImageIO.write(img, "png", new File(dir, filename));
        System.out.println("  Generated: " + filename);
    }

    /**
     * Branch texture: a diagonal twig from approximately (3,15) to (12,10),
     * about 2px wide, with a small side twig branching off.
     * Highlight on upper edge, shadow on lower edge, base in center.
     */
    static void generateBranch(File dir, String filename, int base, int highlight, int shadow)
            throws Exception {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        // Fill with transparent
        for (int y = 0; y < SIZE; y++)
            for (int x = 0; x < SIZE; x++)
                img.setRGB(x, y, TRANSPARENT);

        // Main diagonal twig: from (3,15) going up-right to (12,10)
        // 2px wide diagonal line with highlight on top edge, shadow on bottom

        // Upper edge (highlight) - shifted 1px up from center
        int[][] upperEdge = {
            {3, 14}, {4, 14}, {5, 13}, {6, 13}, {7, 12}, {8, 12}, {9, 11}, {10, 11}, {11, 10}, {12, 9}
        };
        for (int[] p : upperEdge) setPixel(img, p[0], p[1], highlight);

        // Center line (base color) - the main body
        int[][] centerLine = {
            {3, 15}, {4, 15}, {5, 14}, {6, 14}, {7, 13}, {8, 13}, {9, 12}, {10, 12}, {11, 11}, {12, 10}
        };
        for (int[] p : centerLine) setPixel(img, p[0], p[1], base);

        // Lower edge (shadow) - shifted 1px down from center
        int[][] lowerEdge = {
            {5, 15}, {6, 15}, {7, 14}, {8, 14}, {9, 13}, {10, 13}, {11, 12}, {12, 11}
        };
        for (int[] p : lowerEdge) setPixel(img, p[0], p[1], shadow);

        // Small side twig branching upward from around (8,12) to (7,9)
        setPixel(img, 8, 11, highlight);
        setPixel(img, 7, 10, highlight);
        setPixel(img, 7, 9, base);

        setPixel(img, 9, 11, base);
        setPixel(img, 8, 10, base);

        // Tip of main branch: small detail at the upper-right end
        setPixel(img, 13, 10, highlight);
        setPixel(img, 13, 9, base);

        // Bottom-left base: slight thickening
        setPixel(img, 2, 15, shadow);

        ImageIO.write(img, "png", new File(dir, filename));
        System.out.println("  Generated: " + filename);
    }

    /** Set a pixel only if within bounds. */
    static void setPixel(BufferedImage img, int x, int y, int argb) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            img.setRGB(x, y, argb);
        }
    }

    /** Create a fully opaque ARGB int from RGB. */
    static int rgb(int r, int g, int b) {
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }
}
