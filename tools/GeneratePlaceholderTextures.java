import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.File;

/**
 * Generates simple placeholder 16x16 textures for primitive tools.
 * Replace these with proper pixel art later.
 */
public class GeneratePlaceholderTextures {
    public static void main(String[] args) throws Exception {
        String base = "src/main/resources/assets/tempered/textures/item/";

        // Knife: brownish, like flint + wood
        generatePlaceholder(base + "primitive_knife.png", new Color(139, 119, 101));

        // Axe: darker brown, like stone + wood
        generatePlaceholder(base + "primitive_axe.png", new Color(120, 100, 80));

        // Sling: lighter brown, like rope/fiber
        generatePlaceholder(base + "primitive_sling.png", new Color(160, 140, 110));
    }

    static void generatePlaceholder(String path, Color color) throws Exception {
        BufferedImage img = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        for (int y = 0; y < 16; y++) {
            for (int x = 0; x < 16; x++) {
                img.setRGB(x, y, color.getRGB());
            }
        }
        ImageIO.write(img, "PNG", new File(path));
        System.out.println("Created " + path);
    }
}
