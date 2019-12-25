package com.test.face;

import com.sensetime.ad.core.StFaceAttribute;
import com.sensetime.ad.core.StFaceException;
import com.sensetime.ad.core.StFaceLiveness;
import com.sensetime.ad.core.StFaceTrack;
import com.sensetime.ad.core.StFaceVerify;
import com.sensetime.ad.sdk.StFace;
import com.sensetime.ad.sdk.StFaceFeature;
import com.sensetime.ad.sdk.StFaceOrientation;
import com.sensetime.ad.sdk.StImageFormat;
import com.test.face.constants.FaceConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


/**
 * @author wzm
 * @date 2019年12月24日 16:03
 */
public class GetFeature {

    private static String detectDetectModel = FaceConstants.detect_detect_name;
    private static String alignModel = FaceConstants.alignment_name;
    private static String poseModel = FaceConstants.headpose_name;
    private static String attrModel = FaceConstants.attribute_name;
    private static String verifyModel = FaceConstants.verify_name;
    private static String liveMode = FaceConstants.liveness_name;

    private static StFaceTrack stFaceTrack;
    private static StFaceAttribute stFaceAttribute;
    private static StFaceVerify stFaceVerify;
    private static StFaceLiveness stFaceLiveness;

    public static void main(String[] args) {
        int activate = GetActivationCode.activate(FaceConstants.license_file_name, FaceConstants.activation_code_file_name);
        if(activate != 0) {
            System.out.println("激活失败");
        }



        testFace(args[0]);



    }


    static StFaceFeature testFace(String picture) {

        init();
        System.out.println("初始化成功");
        StFaceFeature feature = null;

        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat frame = Imgcodecs.imread(picture, 1);
            System.out.println("图片转换成功");
            int width = frame.cols();
            int height = frame.rows();
            byte[] data = new byte[width * height * 3];//BGR888
            frame.get(0, 0, data);
            //detect
            StFace[] faces = stFaceTrack.detectBasicInfo(data, StImageFormat.ST_PIX_FMT_BGR888, width, height, StFaceOrientation.ST_FACE_UP);
            if (faces != null) {
                System.out.println("人脸检测成功。" + faces.length);
//                for (StFace face : faces) {
                    //feature
                    feature = stFaceVerify.getFeature(data, StImageFormat.ST_PIX_FMT_BGR888, width, height, faces[0]);
                    String featureString;
                    featureString = new String(feature.getByteFeature());
                    System.out.println("feature:" + featureString);
//
//                }
            }
        } catch (StFaceException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        postTest();
        System.out.println("资源释放成功");

        return feature;
    }

    private static void init() {

        try {
            stFaceTrack = new StFaceTrack(detectDetectModel, alignModel, poseModel, 0);
            stFaceAttribute = new StFaceAttribute(attrModel, alignModel);
            stFaceVerify = new StFaceVerify(alignModel, verifyModel);
            stFaceLiveness = new StFaceLiveness(liveMode, alignModel);
        } catch (StFaceException e) {
            e.printStackTrace();
        }

    }

    private static void postTest() {
        stFaceTrack.release();
        stFaceAttribute.release();
        stFaceLiveness.release();
        stFaceVerify.release();
    }


}
