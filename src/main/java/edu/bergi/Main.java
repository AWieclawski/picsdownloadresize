package edu.bergi;

import edu.bergi.download.FileDownload;
import edu.bergi.imgutils.ImageScalar;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Main {

    private static String url;
    private static String localFilename;

    private static final String BASE_DIR = "/home/user/pics";
    private static final String RESIZED_DIR = "/home/user/smallpics";

    private static final String SOURCE_URL = "https://huggingface.co/datasets/remoteuser/someset/resolve/main/";

    private static final int CHAPTERS = 24;

    private static final int PICS = 100;

    private static final int IMG_WIDTH = 1200;
    private static final int IMG_HEIGHT = Integer.MAX_VALUE;

    public static void main(String[] args) {

        for (int r = 0; r < CHAPTERS; r++) {
            int i = 1;
            while (i <= PICS) {
                String tmpDir = String.format("%s/R%03d", BASE_DIR, r);
                if (createDirectory(tmpDir)) {
                    localFilename = String.format("%s/%03d.jpg", tmpDir, i);
                    url = String.format("%s/R%d/%d.png",SOURCE_URL, r, i);
                    if (fileDownload()) {
                        log.info(" + Download {} file / chapter {}. OK", i, r);
                        tmpDir = String.format("%s/R%03d", RESIZED_DIR, r);
                        if (resizeProceed(localFilename, tmpDir, i)) {
                            log.info(" + + Resize {} file / chapter {}. OK", i, r);
                        } else {
                            log.warn(" - - Resize {} file / chapter {}. Failed!", i, r);
                        }
                    } else {
                        log.warn(" - Download {} file / chapter {}. Failed!", i, r);
                    }
                }
                i++;
            }
        }

    }

    private static boolean createDirectory(String dir) {
        try {
            Path path = Paths.get(dir);
            Files.createDirectories(path);
            return true;
        } catch (Exception e) {
            log.error("Make directory [{}] error!\n {} | {}", dir, e.getCause(), e.getMessage(), e);
        }
        return false;
    }

    private static boolean fileDownload() {
        try {
//            FileDownload.downloadWithJavaIO(url, localFilename);
//            FileDownload.downloadWithJava7IO(url, localFilename);
            FileDownload.downloadWithJavaNIO(url, localFilename);
            return true;
        } catch (Exception e) {
            log.error("Url download [{}] error!\n {} | {}", url, e.getCause(), e.getMessage(), e);
        }
        return false;
    }

    private static boolean resizeProceed(String source, String outputDir, int fileNumber) {
        if (createDirectory(outputDir)) {
            Path sourcePath = Paths.get(source);
            try (InputStream is = new FileInputStream(sourcePath.toFile())) {
                BufferedImage originalImage = ImageIO.read(is);
                BufferedImage newResizedImage = ImageScalar.resizeImageQW(originalImage, IMG_WIDTH, IMG_HEIGHT);
                String outFile = String.format("%s/%03d.jpg", outputDir, fileNumber);
                Path targetPath = Paths.get(outFile);
                String fileExtension = outFile.substring(outFile.lastIndexOf(".") + 1);
                ImageIO.write(newResizedImage, fileExtension, targetPath.toFile());
                return true;
            } catch (Exception e) {
                log.error("Resize [{}] error!\n {} | {}", source, e.getCause(), e.getMessage(), e);
            }
        }
        return false;
    }
}