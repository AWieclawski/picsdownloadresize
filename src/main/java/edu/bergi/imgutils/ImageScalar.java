package edu.bergi.imgutils;


import org.imgscalr.Scalr;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

public class ImageScalar {
    public static BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, targetWidth);
    }

    public static BufferedImage resizeImageQW(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Dimension newMaxSize = new Dimension(targetWidth, targetHeight);
        return Scalr.resize(originalImage, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, newMaxSize.width, newMaxSize.height, Scalr.OP_ANTIALIAS);
    }
}
