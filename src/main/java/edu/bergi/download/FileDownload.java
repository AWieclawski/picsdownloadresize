package edu.bergi.download;

import edu.bergi.imgutils.ImageScalar;
import org.apache.commons.io.FileUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileDownload {

    public static void downloadWithImageIOAndScale(String fileURL, String localFilename, int imgWidth, int imgHeight) throws IOException {
        URL url = new URL(fileURL);
        BufferedImage originalImage = ImageIO.read(url.openStream());
        BufferedImage newResizedImage = ImageScalar.resizeImageQW(originalImage, imgWidth, imgHeight);
        Path targetPath = Paths.get(localFilename);
        String fileExtension = localFilename.substring(localFilename.lastIndexOf(".") + 1);
        ImageIO.write(newResizedImage, fileExtension, targetPath.toFile());
    }


    public static void downloadWithApacheCommons(String url, String localFilename) {

        int CONNECT_TIMEOUT = 10000;
        int READ_TIMEOUT = 10000;
        try {
            FileUtils.copyURLToFile(new URL(url), new File(localFilename), CONNECT_TIMEOUT, READ_TIMEOUT);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
