import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

/**
 * Reads pebble_block_medium.png and generates light and dark variants
 * by shifting the color palette.
 *
 * Light: warmer, brighter (sandy/limestone for birch, desert, beach biomes)
 * Dark: cooler, darker (slate/basalt for taiga, dark forest biomes)
 */
public class GeneratePebbleVariants {
    public static void main(String[] args) throws Exception {
        String basePath = "src/main/resources/assets/tempered/textures/block/";
        BufferedImage medium = ImageIO.read(new File(basePath + "pebble_block_medium.png"));

        int w = medium.getWidth();
        int h = medium.getHeight();

        // Print unique colors found in medium
        java.util.Set<String> colors = new java.util.TreeSet<>();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = medium.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a > 0) {
                    colors.add(String.format("#%06X", argb & 0xFFFFFF));
                }
            }
        }
        System.out.println("Colors found in medium texture: " + colors);

        // Generate light variant (warmer, brighter)
        BufferedImage light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = medium.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) {
                    light.setRGB(x, y, 0); // keep transparent
                } else {
                    light.setRGB(x, y, shiftColor(argb, 0.35f, 25f, 0.08f));
                }
            }
        }
        ImageIO.write(light, "PNG", new File(basePath + "pebble_block_light.png"));
        System.out.println("Generated pebble_block_light.png");

        // Generate dark variant (cooler, darker)
        BufferedImage dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = medium.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) {
                    dark.setRGB(x, y, 0); // keep transparent
                } else {
                    dark.setRGB(x, y, shiftColor(argb, -0.30f, -15f, -0.05f));
                }
            }
        }
        ImageIO.write(dark, "PNG", new File(basePath + "pebble_block_dark.png"));
        System.out.println("Generated pebble_block_dark.png");
    }

    /**
     * Shift a color in HSB space.
     * @param argb original color
     * @param brightnessShift how much to shift brightness (-1 to 1)
     * @param hueShiftDeg how much to shift hue in degrees (positive = warmer/yellow, negative = cooler/blue)
     * @param satShift how much to shift saturation (-1 to 1)
     */
    static int shiftColor(int argb, float brightnessShift, float hueShiftDeg, float satShift) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        float[] hsb = Color.RGBtoHSB(r, g, b, null);

        // Shift hue (0-1 range, where 0.08 ~ orange/warm, 0.6 ~ blue/cool)
        hsb[0] += hueShiftDeg / 360f;
        if (hsb[0] < 0) hsb[0] += 1f;
        if (hsb[0] > 1) hsb[0] -= 1f;

        // Shift saturation
        hsb[1] = Math.max(0, Math.min(1, hsb[1] + satShift));

        // Shift brightness
        hsb[2] = Math.max(0, Math.min(1, hsb[2] + brightnessShift));

        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return (a << 24) | (rgb & 0xFFFFFF);
    }
}
