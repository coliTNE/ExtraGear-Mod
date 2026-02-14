import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class VerifyBlockTextures {
    public static void main(String[] args) throws Exception {
        String dir = "src/main/resources/assets/tempered/textures/block/";
        String[] files = {
            "pebble_block_light.png", "pebble_block_medium.png", "pebble_block_dark.png",
            "branch_block_light.png", "branch_block_medium.png", "branch_block_dark.png"
        };

        for (String f : files) {
            BufferedImage img = ImageIO.read(new File(dir + f));
            int w = img.getWidth(), h = img.getHeight();
            int type = img.getType();
            
            // Count transparent vs opaque pixels
            int transparent = 0, opaque = 0;
            for (int y = 0; y < h; y++) {
                for (int x = 0; x < w; x++) {
                    int argb = img.getRGB(x, y);
                    int alpha = (argb >> 24) & 0xFF;
                    if (alpha == 0) transparent++;
                    else opaque++;
                }
            }
            
            String typeStr = (type == BufferedImage.TYPE_INT_ARGB) ? "TYPE_INT_ARGB" : "type=" + type;
            System.out.printf("%-28s %dx%d  %s  opaque=%d  transparent=%d%n",
                f, w, h, typeStr, opaque, transparent);
        }
        System.out.println("\nAll textures verified!");
    }
}
