package com.mr.clock.service;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;

/**
 * 摄像头服务
 * 
 * @author mingrisoft
 *
 */
public class CameraService {

    private static final Webcam WEBCAM = Webcam.getDefault();// 摄像头对象

    /**
     * 开启摄像头
     * 
     * @return 是否开启成功
     */
    public static boolean startCamera() {
        if (WEBCAM == null) {// 如果计算机没有连接摄像头
            return false;
        }
        // 摄像头采用默认的640*480宽高
        WEBCAM.setViewSize(new Dimension(640, 480));
        return WEBCAM.open();// 开启摄像头，返回开启是否成功
    }

    /**
     * 摄像头是否开启
     * 
     * @return
     */
    public static boolean cameraIsOpen() {
        if (WEBCAM == null) {// 如果计算机没有连接摄像头
            return false;
        }
        return WEBCAM.isOpen();
    }

    /**
     * 获取摄像头画面面板
     * 
     * @return
     */
    public static JPanel getCameraPanel() {
        // 摄像头画面面板
        WebcamPanel panel = new WebcamPanel(WEBCAM);
        panel.setMirrored(true);// 开启镜像
        return panel;
    }

    /**
     * 获取摄像头捕获的帧画面
     * 
     * @return 原始大小帧画面
     */
    public static BufferedImage getCameraFrame() {
        // 获取当前帧画面
        return WEBCAM.getImage();
    }

    /**
     * 释放摄像头资源
     */
    public static void releaseCamera() {
        if (WEBCAM != null) {
            WEBCAM.close();// 关闭摄像头
        }
    }
}
