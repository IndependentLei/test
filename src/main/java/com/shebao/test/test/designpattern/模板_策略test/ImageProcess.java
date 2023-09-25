package com.shebao.test.test.designpattern.模板_策略test;

import java.awt.image.BufferedImage;

public abstract class ImageProcess {
    public void processImage() {
        BufferedImage image = openImage();
        ImageProcessingStrategy strategy = createImageProcessingStrategy();
        BufferedImage processedImage = strategy.processImage(image);
        saveImage(processedImage);
    }

    protected BufferedImage openImage() {
        // 打开图片的具体实现
        return null;
    }

    protected abstract ImageProcessingStrategy createImageProcessingStrategy();

    protected void saveImage(BufferedImage image) {
        // 保存图片的具体实现
    }
}
