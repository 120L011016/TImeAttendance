package com.mr.clock.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import javax.naming.ConfigurationException;

import com.arcsoft.face.EngineConfiguration;
import com.arcsoft.face.FaceEngine;
import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.face.FaceSimilar;
import com.arcsoft.face.FunctionConfiguration;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageFactory;
import com.arcsoft.face.toolkit.ImageInfo;
import com.mr.clock.session.Session;

/**
 * 人脸识别引擎服务
 * 
 * @author mingrisoft
 *
 */
public class FaceEngineService {
    private static String appId = null;
    private static String sdkKey = null;
    private static FaceEngine faceEngine = null;// 人脸识别引擎
    private static String ENGINE_PATH = "ArcFace/WIN64";// 算法库地址
    // 配置文件地址
    private static final String CONFIG_FILE = "src/com/mr/clock/config/ArcFace.properties";

    static {
        Properties pro = new Properties();// 配置文件解析类
        File config = new File(CONFIG_FILE);// 配置文件的文件对象
        try {
            if (!config.exists()) {// 如果配置文件不存在
                throw new FileNotFoundException("缺少文件：" + config.getAbsolutePath());
            }
            pro.load(new FileInputStream(config));// 加载配置文件
            appId = pro.getProperty("app_id");// 获取指定字段值
            sdkKey = pro.getProperty("sdk_key");
            if (appId == null || sdkKey == null) {// 如果配置文件中获取不到这俩字段
                throw new ConfigurationException("ArcFace.properties文件缺少配置信息");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        File path = new File(ENGINE_PATH);// 算法库文件夹
        faceEngine = new FaceEngine(path.getAbsolutePath());// 人脸识别引擎
        // 激活引擎，**首次激活需要联网**
        int errorCode = faceEngine.activeOnline(appId, sdkKey);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.err.println("ERROR: ArcFace引擎激活失败，请检查授权码是否填写错误，或重新联网激活。");
        }

        // 引擎配置
        EngineConfiguration engineConfiguration = new EngineConfiguration();
        // 单张图像模式
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        // 检测所有角度
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        // 检测最多人脸数
        engineConfiguration.setDetectFaceMaxNum(1);
        // 设置人脸相对于所在图片的长边的占比
        engineConfiguration.setDetectFaceScaleVal(16);
        // 功能配置
        FunctionConfiguration functionConfiguration = new FunctionConfiguration();
        // 支持人脸检测
        functionConfiguration.setSupportFaceDetect(true);
        // 支持人脸识别
        functionConfiguration.setSupportFaceRecognition(true);
        // 引擎使用此功能配置
        engineConfiguration.setFunctionConfiguration(functionConfiguration);
        // 初始化引擎
        errorCode = faceEngine.init(engineConfiguration);
        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.err.println("ERROR:ArcFace引擎初始化失败");
        }
    }

    /**
     * 获取一张人脸的面部特征
     * 
     * @param img 人脸照片
     * @return
     */
    public static FaceFeature getFaceFeature(BufferedImage img) {
        if (img == null) {
            throw new NullPointerException("人脸图像为null");
        }
        //创建一个和原图像一样大的临时图像，临时图像类型为普通BRG图像
        BufferedImage face = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_BGR);
        face.setData(img.getData());// 临时图像使用原图像中的数据
        // 采集图像信息
        ImageInfo imageInfo = ImageFactory.bufferedImage2ImageInfo(face);
        // 人脸信息列表
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        // 从图像信息中采集人脸信息
        faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),
                imageInfo.getImageFormat(), faceInfoList);

        if (faceInfoList.isEmpty()) {// 如果人脸信息是空的
            return null;
        }
        // 人脸特征
        FaceFeature faceFeature = new FaceFeature();
        // 从人脸信息中采集人脸特征
        faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(),
                imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);
        return faceFeature;// 采集之后的人脸特征
    }

    /**
     * 加载所有面部特征
     */
    public static void loadAllFaceFeature() {
        // 获取所有人脸图片对应的特征码集合
        Set<String> keys = Session.IMAGE_MAP.keySet();
        for (String code : keys) {// 遍历所有特征码
            // 取出一张人脸图片
            BufferedImage image = Session.IMAGE_MAP.get(code);
            // 获取该人脸图片的人脸特征对象
            FaceFeature faceFeature = getFaceFeature(image);
            // 将人脸特征对象保存至全局会话中
            Session.FACE_FEATURE_MAP.put(code, faceFeature);
        }
    }

    /**
     * 从人脸特征库中检测人脸
     * 
     * @param targetFaceFeature 目标人脸特征
     * @return 该人脸特征对应的特征码
     */
    public static String detectFace(FaceFeature targetFaceFeature) {
        if (targetFaceFeature == null) {
            return null;
        }
        // 获取所有人脸特征对应的特征码集合
        Set<String> keys = Session.FACE_FEATURE_MAP.keySet();
        float score = 0;// 匹配最高得分
        String resultCode = null;// 评分对应的特征码
        for (String code : keys) {// 遍历所有特征码
            // 取出一个人脸特征对象
            FaceFeature sourceFaceFeature = Session.FACE_FEATURE_MAP.get(code);
            // 特征比对对象
            FaceSimilar faceSimilar = new FaceSimilar();
            // 对比目标人脸特征和取出的人脸特征
            faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
            if (faceSimilar.getScore() > score) {// 如果得分大于当前最高得分
                score = faceSimilar.getScore();// 重新记录当前最高得分
                resultCode = code;// 记录最高得分的特征码
            }
        }
        if (score > 0.9) {// 如果最高得分大于0.9，则认为找到匹配人脸
            return resultCode;// 返回人脸对应的特征码
        }
        return null;
    }

    /**
     * 释放资源
     */
    public static void dispost() {
        faceEngine.unInit();// 引擎卸载
    }
}
