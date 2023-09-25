package com.shebao.test.test.designpattern.模板_策略test;

import java.awt.image.BufferedImage;

public interface ImageProcessingStrategy {
    BufferedImage processImage(BufferedImage bufferedImage);
}
