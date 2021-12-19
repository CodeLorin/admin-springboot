package com.lorin.utils;

import com.arcsoft.face.*;
import com.arcsoft.face.enums.DetectMode;
import com.arcsoft.face.enums.DetectOrient;
import com.arcsoft.face.enums.ErrorInfo;
import com.arcsoft.face.toolkit.ImageInfo;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.arcsoft.face.toolkit.ImageFactory.getRGBData;


/**
 * @author lorin
 */
public class FaceEngineUtil {
    private static final double threshold = 0.8;
    private static final String appId = "AtjpQHXSfL56XWTsVF92nesPmfvmo7n4VnP6DemSKyrG";
    private static final String sdkKey = "ARtQbVnaWsExAtGPMi2uwky7PWVtdSePaYvMuQR86yZJ";
    private static final String libPath = "C:\\Users\\lcary\\Desktop\\web-backend\\src\\main\\resources\\lib\\WIN64";
    private static final String path = "C:\\Users\\lcary\\Desktop\\web-backend\\src\\main\\resources\\static\\file\\face\\";
    private static FaceEngine faceEngine;
    private static int errorCode;
    private static EngineConfiguration engineConfiguration;
    private static FunctionConfiguration functionConfiguration;

    public void setEngine() {
        faceEngine = new FaceEngine(libPath);
        errorCode = faceEngine.activeOnline(appId, sdkKey);
        //激活引擎
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("引擎激活失败");
        }
        ActiveFileInfo activeFileInfo = new ActiveFileInfo();
        errorCode = faceEngine.getActiveFileInfo(activeFileInfo);
        if (errorCode != ErrorInfo.MOK.getValue() && errorCode != ErrorInfo.MERR_ASF_ALREADY_ACTIVATED.getValue()) {
            System.out.println("获取激活文件信息失败");
        }
    }

    public void configEngine() {

        //引擎配置
        engineConfiguration = new EngineConfiguration();
        engineConfiguration.setDetectMode(DetectMode.ASF_DETECT_MODE_IMAGE);
        engineConfiguration.setDetectFaceOrientPriority(DetectOrient.ASF_OP_ALL_OUT);
        engineConfiguration.setDetectFaceMaxNum(10);
        engineConfiguration.setDetectFaceScaleVal(16);
        //功能配置
        functionConfiguration = new FunctionConfiguration();
        functionConfiguration.setSupportFace3dAngle(true);
        functionConfiguration.setSupportFaceDetect(true);
        functionConfiguration.setSupportFaceRecognition(true);
        functionConfiguration.setSupportLiveness(true);

        engineConfiguration.setFunctionConfiguration(functionConfiguration);
    }

    public void initEngine() {
        //初始化引擎
        errorCode = faceEngine.init(engineConfiguration);

        if (errorCode != ErrorInfo.MOK.getValue()) {
            System.out.println("初始化引擎失败");
        }
    }

    public float face(String img1, String img2) {
        ImageInfo
                imageInfo = getRGBData(new File(img1));
        List<FaceInfo> faceInfoList = new ArrayList<FaceInfo>();
        errorCode = FaceEngineUtil.faceEngine.detectFaces(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList);


        //特征提取
        FaceFeature faceFeature = new FaceFeature();
        errorCode = FaceEngineUtil.faceEngine.extractFaceFeature(imageInfo.getImageData(), imageInfo.getWidth(), imageInfo.getHeight(), imageInfo.getImageFormat(), faceInfoList.get(0), faceFeature);


        //人脸检测2
        ImageInfo imageInfo2 = getRGBData(new File(img2));
        List<FaceInfo> faceInfoList2 = new ArrayList<FaceInfo>();
        errorCode = FaceEngineUtil.faceEngine.detectFaces(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2);


        //特征提取2
        FaceFeature faceFeature2 = new FaceFeature();
        errorCode = FaceEngineUtil.faceEngine.extractFaceFeature(imageInfo2.getImageData(), imageInfo2.getWidth(), imageInfo2.getHeight(), imageInfo.getImageFormat(), faceInfoList2.get(0), faceFeature2);


        //特征比对
        FaceFeature targetFaceFeature = new FaceFeature();
        targetFaceFeature.setFeatureData(faceFeature.getFeatureData());
        FaceFeature sourceFaceFeature = new FaceFeature();
        sourceFaceFeature.setFeatureData(faceFeature2.getFeatureData());
        FaceSimilar faceSimilar = new FaceSimilar();

        errorCode = FaceEngineUtil.faceEngine.compareFaceFeature(targetFaceFeature, sourceFaceFeature, faceSimilar);
        return faceSimilar.getScore();


    }

    public void unInit() {
        //引擎卸载
        errorCode = FaceEngineUtil.faceEngine.unInit();
    }

    public String run(String imgUrl) {
        //从官网获取
        this.setEngine();
        this.configEngine();
        this.initEngine();
        imgUrl = "C:\\Users\\lcary\\Desktop\\web-backend\\src\\main\\resources\\static\\file\\temp\\" + imgUrl;
        File f = new File(path);
        File[] fa = f.listFiles();
        if (fa != null) {
            for (File fs : fa) {
                String img2 = path + fs.getName();
                float face = this.face(imgUrl, img2);
                System.out.println("相似率为: " + face);
                if (face > 0.8) {
                    System.out.println("识别成功,人脸数据: " + fs.getName());
                    return fs.getName();
                }
            }
            System.out.println("没有找到该用户或者识别失败");

        }
        this.unInit();
        return "false";

    }

    public static void main(String[] args) {

        FaceEngineUtil faceEngineUtil = new FaceEngineUtil();

        String run = faceEngineUtil.run("WIN_20211117_22_17_23_Pro.jpg");
        if (!"false".equals(run)) {
            System.out.println("识别成功");
            System.out.println(run);
        }
    }
}