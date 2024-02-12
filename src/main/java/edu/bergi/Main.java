package edu.bergi;

import edu.bergi.download.FileDownload;
import lombok.extern.slf4j.Slf4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class Main {

    private static String url;
    private static String localFilename;

    private static final String RESIZED_DIR = "/home/localuser/pics/downloaded";

    private static final String SOURCE_URL = "https://huggingface.co/datasets/remoteuser/somedataset/resolve/main";

    private static final int CHAPTERS = 200;

    private static final int PICS = 100;

    private static final int IMG_WIDTH = 1200;
    private static final int IMG_HEIGHT = Integer.MAX_VALUE;


    public static void main(String[] args) {

        for (int r = 0; r < CHAPTERS; r++) {
            int i = 0;
            int fails = 0;
            String tmpDir = String.format("%s/R%03d", RESIZED_DIR, r);
            if (createDirectory(tmpDir)) {
                while (i <= PICS) {
                    localFilename = String.format("%s/%03d.jpg", tmpDir, i);
                    url = String.format("%s/R%d/%d.png", SOURCE_URL, r, i);
                    if (fileDownload()) {
                        log.info(" + Download {} file / chapter {}. OK", i, r);
                    } else {
                        log.warn(" - Download {} file / chapter {}. Failed! {}", i, r, ++fails);
                        if (fails > 3) {
                            i = PICS;
                        }
                    }
                    i++;
                }
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
            FileDownload.downloadWithImageIOAndScale(url, localFilename, IMG_WIDTH, IMG_HEIGHT);
            return true;
        } catch (Exception e) {
            log.warn("Url download [{}] error!\n {} | {}", url, e.getCause(), e.getMessage(), e);
        }
        return false;
    }

}