package com.takuchan.milkypublisher.graphic;

import android.graphics.Bitmap;

import androidx.camera.core.ImageProxy;



public interface VisionImageProcessor{
    void processBitmap(Bitmap bitmap,GraphicOverlay graphicOverlay);
    void processByteBuffer(byte[] data,FrameMetadata frameMetadata,GraphicOverlay graphicOverlay);
    void processImageProxy (ImageProxy image, GraphicOverlay graphicOverlay);

    void stop();
}