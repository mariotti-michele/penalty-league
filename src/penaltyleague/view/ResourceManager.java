package penaltyleague.view;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ResourceManager {

    private static String referenceFolderAtRuntime = null;

    private static String getReferenceFolderAtRuntime() throws Exception{
        if (referenceFolderAtRuntime == null) {
            referenceFolderAtRuntime = new File(ResourceManager.class.getProtectionDomain().getCodeSource().getLocation()
                    .toURI()).getParentFile().getParentFile().getParentFile().getPath();
        }
        return referenceFolderAtRuntime;
    }

    public static BufferedImage loadImage(String fileName) throws Exception{
        String fs = File.separator;
        BufferedImage image = ImageIO.read(new File(getReferenceFolderAtRuntime() + fs + "resources"
                + fs + "images" + fs + fileName));
        return image;
    }

    public static File getSoundFile(String fileName) throws Exception{
        String fs = File.separator;
        String filePath = getReferenceFolderAtRuntime() + fs + "resources" + fs + "sounds" + fs + fileName;
        File file = new File(filePath);
        return file;
    }
}
