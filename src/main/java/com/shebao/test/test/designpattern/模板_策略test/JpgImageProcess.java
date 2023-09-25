package com.shebao.test.test.designpattern.模板_策略test;

public class JpgImageProcess extends ImageProcess{
    @Override
    protected ImageProcessingStrategy createImageProcessingStrategy() {
        return new JpgImageProcessingStrategy();
    }
}
