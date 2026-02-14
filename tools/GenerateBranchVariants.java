import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

/**
 * Reads branch_block_medium.png and generates light and dark variants.
 * Light: lighter, slightly warmer wood tones
 * Dark: darker, slightly cooler wood tones
 */
public class GenerateBranchVariants {
    public static void main(String[] args) throws Exception {
        String basePath = "src/main/resources/assets/tempered/textures/block/";
        BufferedImage medium = ImageIO.read(new File(basePath + "branch_block_medium.png"));

        int w = medium.getWidth();
        int h = medium.getHeight();

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
        System.out.println("Colors found in medium branch texture: " + colors);

        // Light variant (birch-like, lighter warmer wood)
        BufferedImage light = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = medium.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) {
                    light.setRGB(x, y, 0);
                } else {
                    light.setRGB(x, y, shiftColor(argb, 0.30f, 15f, -0.05f));
                }
            }
        }
        ImageIO.write(light, "PNG", new File(basePath + "branch_block_light.png"));
        System.out.println("Generated branch_block_light.png");

        // Dark variant (spruce-like, darker cooler wood)
        BufferedImage dark = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = medium.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) {
                    dark.setRGB(x, y, 0);
                } else {
                    dark.setRGB(x, y, shiftColor(argb, -0.25f, -10f, 0.05f));
                }
            }
        }
        ImageIO.write(dark, "PNG", new File(basePath + "branch_block_dark.png"));
        System.out.println("Generated branch_block_dark.png");
    }

    static int shiftColor(int argb, float brightnessShift, float hueShiftDeg, float satShift) {
        int a = (argb >> 24) & 0xFF;
        int r = (argb >> 16) & 0xFF;
        int g = (argb >> 8) & 0xFF;
        int b = argb & 0xFF;

        float[] hsb = Color.RGBtoHSB(r, g, b, null);

        hsb[0] += hueShiftDeg / 360f;
        if (hsb[0] < 0) hsb[0] += 1f;
        if (hsb[0] > 1) hsb[0] -= 1f;

        hsb[1] = Math.max(0, Math.min(1, hsb[1] + satShift));
        hsb[2] = Math.max(0, Math.min(1, hsb[2] + brightnessShift));

        int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
        return (a << 24) | (rgb & 0xFFFFFF);
    }
}
