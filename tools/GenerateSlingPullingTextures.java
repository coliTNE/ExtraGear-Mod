import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

/**
 * Generates placeholder pulling textures for the primitive sling.
 * Creates 3 stages by tinting the base texture progressively:
 *   pulling_0: slight warm tint (starting to wind)
 *   pulling_1: more saturated (mid-charge)
 *   pulling_2: bright highlight (fully charged, ready to release)
 * Replace these with proper pixel art later.
 */
public class GenerateSlingPullingTextures {
    public static void main(String[] args) throws Exception {
        String base = "src/main/resources/assets/tempered/textures/item/";
        BufferedImage sling = ImageIO.read(new File(base + "primitive_sling.png"));
        int w = sling.getWidth();
        int h = sling.getHeight();

        // Stage 0: slight brightness boost (starting to wind up)
        generateVariant(sling, w, h, base + "primitive_sling_pulling_0.png", 0.05f, 0f, 0.02f);

        // Stage 1: warmer, more saturated (mid-charge)
        generateVariant(sling, w, h, base + "primitive_sling_pulling_1.png", 0.12f, 5f, 0.05f);

        // Stage 2: bright and warm (fully charged, ready to release)
        generateVariant(sling, w, h, base + "primitive_sling_pulling_2.png", 0.20f, 10f, 0.08f);
    }

    static void generateVariant(BufferedImage src, int w, int h, String path,
                                float brightnessShift, float hueShiftDeg, float satShift) throws Exception {
        BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                int argb = src.getRGB(x, y);
                int a = (argb >> 24) & 0xFF;
                if (a == 0) {
                    out.setRGB(x, y, 0);
                } else {
                    out.setRGB(x, y, shiftColor(argb, brightnessShift, hueShiftDeg, satShift));
                }
            }
        }
        ImageIO.write(out, "PNG", new File(path));
        System.out.println("Created " + path);
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
