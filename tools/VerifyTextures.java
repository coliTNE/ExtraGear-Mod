import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class VerifyTextures {
    public static void main(String[] args) throws Exception {
        String dir = "src/main/resources/assets/tempered/textures/block";
        String[] files = {
            "pebble_block_light.png", "pebble_block_medium.png", "pebble_block_dark.png",
            "branch_block_light.png", "branch_block_medium.png", "branch_block_dark.png"
        };
        for (String f : files) {
            BufferedImage img = ImageIO.read(new File(dir, f));
            int w = img.getWidth(), h = img.getHeight();
            int type = img.getType();
            int opaqueCount = 0, transparentCount = 0;
            for (int y = 0; y < h; y++)
                for (int x = 0; x < w; x++) {
                    int alpha = (img.getRGB(x, y) >> 24) & 0xFF;
                    if (alpha == 0) transparentCount++;
                    else opaqueCount++;
                }
            System.out.printf("%-28s %dx%d  type=%d  opaque=%3d  transparent=%3d%n",
                f, w, h, type, opaqueCount, transparentCount);
        }
    }
}
