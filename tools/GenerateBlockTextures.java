import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GenerateBlockTextures {

    static final int SIZE = 16;
    static final int TRANSPARENT = 0x00000000;

    public static void main(String[] args) throws IOException {
        String outDir = "src/main/resources/assets/tempered/textures/block/";
        new File(outDir).mkdirs();

        // Generate pebble textures
        generatePebbles(outDir + "pebble_block_light.png",
            rgb(200,195,185), rgb(220,215,205), rgb(170,165,155));
        generatePebbles(outDir + "pebble_block_medium.png",
            rgb(140,135,130), rgb(165,160,155), rgb(115,110,105));
        generatePebbles(outDir + "pebble_block_dark.png",
            rgb(80,75,70), rgb(105,100,95), rgb(55,50,45));

        // Generate branch textures
        generateBranch(outDir + "branch_block_light.png",
            rgb(210,190,150), rgb(230,215,180), rgb(180,160,120));
        generateBranch(outDir + "branch_block_medium.png",
            rgb(150,120,70), rgb(175,145,95), rgb(125,95,50));
        generateBranch(outDir + "branch_block_dark.png",
            rgb(80,60,40), rgb(105,85,65), rgb(55,35,20));

        System.out.println("All 6 block textures generated successfully!");
    }

    static int rgb(int r, int g, int b) {
        return 0xFF000000 | (r << 16) | (g << 8) | b;
    }

    // --- PEBBLE GENERATION ---

    static void generatePebbles(String path, int base, int highlight, int shadow) throws IOException {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        // Fill with fully transparent pixels
        for (int y = 0; y < SIZE; y++)
            for (int x = 0; x < SIZE; x++)
                img.setRGB(x, y, TRANSPARENT);

        // 4 scattered pebbles at spread-out positions, each with slightly different shape

        // Pebble 1: at (3,3), oval 3x2 horizontal
        drawPebble(img, 3, 3, new int[][]{
            {0,0},{1,0},{2,0},
            {0,1},{1,1},{2,1}
        }, base, highlight, shadow);

        // Pebble 2: at (10,6), oval 2x3 vertical
        drawPebble(img, 10, 6, new int[][]{
            {0,0},{1,0},
            {0,1},{1,1},
            {0,2},{1,2}
        }, base, highlight, shadow);

        // Pebble 3: at (4,11), irregular 3x2
        drawPebble(img, 4, 11, new int[][]{
            {1,0},{2,0},
            {0,1},{1,1},{2,1}
        }, base, highlight, shadow);

        // Pebble 4: at (12,2), small round 2x2
        drawPebble(img, 12, 2, new int[][]{
            {0,0},{1,0},
            {0,1},{1,1}
        }, base, highlight, shadow);

        ImageIO.write(img, "png", new File(path));
        System.out.println("  Created: " + path);
    }

    static void drawPebble(BufferedImage img, int ox, int oy, int[][] pixels,
                           int base, int highlight, int shadow) {
        // Find bounds for highlight/shadow placement
        int minX = Integer.MAX_VALUE, minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE, maxY = Integer.MIN_VALUE;
        for (int[] p : pixels) {
            if (p[0] < minX) minX = p[0];
            if (p[1] < minY) minY = p[1];
            if (p[0] > maxX) maxX = p[0];
            if (p[1] > maxY) maxY = p[1];
        }

        for (int[] p : pixels) {
            int px = ox + p[0];
            int py = oy + p[1];
            if (px < 0 || px >= SIZE || py < 0 || py >= SIZE) continue;

            int color;
            // Top-left corner gets highlight (light from top-left)
            if (p[0] == minX && p[1] == minY) {
                color = highlight;
            }
            // Bottom-right corner gets shadow
            else if (p[0] == maxX && p[1] == maxY) {
                color = shadow;
            }
            // Top row gets highlight
            else if (p[1] == minY && p[0] <= minX + 1) {
                color = highlight;
            }
            // Bottom row right side gets shadow
            else if (p[1] == maxY && p[0] >= maxX - 1) {
                color = shadow;
            }
            else {
                color = base;
            }
            img.setRGB(px, py, color);
        }
    }

    // --- BRANCH GENERATION ---

    static void generateBranch(String path, int base, int highlight, int shadow) throws IOException {
        BufferedImage img = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);

        // Fill with fully transparent pixels
        for (int y = 0; y < SIZE; y++)
            for (int x = 0; x < SIZE; x++)
                img.setRGB(x, y, TRANSPARENT);

        // Main branch: diagonal from bottom-left to top-right
        // Going from approximately (2,13) to (13,3) with a slight natural curve
        int[][] mainBranch = {
            {2, 13},
            {3, 12},
            {4, 11},
            {5, 10},
            {5, 9},   // slight curve - stays at x=5 for natural look
            {6, 8},
            {7, 7},
            {8, 6},
            {9, 6},   // slight horizontal for natural look
            {10, 5},
            {11, 4},
            {12, 3},
            {13, 3},  // tip extends horizontally
        };

        // Draw main branch with ~2px width
        // For a diagonal going up-right, highlight goes on top (y-1), shadow on bottom (y+1)
        for (int i = 0; i < mainBranch.length; i++) {
            int x = mainBranch[i][0], y = mainBranch[i][1];
            safeSet(img, x, y, base);

            // Add width: highlight on top side, shadow on bottom side
            safeSet(img, x, y - 1, highlight);
            safeSet(img, x, y + 1, shadow);
        }

        // Taper the tip (last 2 points) - remove extra width
        safeSet(img, 13, 2, TRANSPARENT);
        safeSet(img, 13, 4, TRANSPARENT);
        safeSet(img, 12, 2, TRANSPARENT);

        // Taper the base end - remove extra width
        safeSet(img, 2, 14, TRANSPARENT);
        safeSet(img, 2, 12, TRANSPARENT);

        // Side twig branching off from around (7,7) going down-right to (9,11)
        int[][] sideTwig = {
            {7, 8},
            {8, 9},
            {9, 10},
            {9, 11},
        };

        for (int i = 0; i < sideTwig.length; i++) {
            int x = sideTwig[i][0], y = sideTwig[i][1];
            safeSet(img, x, y, base);
            // Thinner twig - only highlight on one side for first 2 pixels
            if (i < 2) {
                safeSet(img, x - 1, y, highlight);
            }
        }

        // Small knot where side twig meets main branch
        safeSet(img, 7, 8, shadow);

        // Tiny second twig stub near (5,10) going left to (3,10)
        safeSet(img, 4, 10, base);
        safeSet(img, 3, 10, base);
        safeSet(img, 3, 9, highlight);

        ImageIO.write(img, "png", new File(path));
        System.out.println("  Created: " + path);
    }

    static void safeSet(BufferedImage img, int x, int y, int color) {
        if (x >= 0 && x < SIZE && y >= 0 && y < SIZE) {
            img.setRGB(x, y, color);
        }
    }
}
