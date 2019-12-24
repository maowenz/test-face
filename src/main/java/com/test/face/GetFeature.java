package com.test.face;

import com.sensetime.ad.core.StBodyAttributes;
import com.sensetime.ad.core.StBodyTrack;
import com.sensetime.ad.core.StFaceAttribute;
import com.sensetime.ad.core.StFaceDetect;
import com.sensetime.ad.core.StFaceException;
import com.sensetime.ad.core.StFaceImage;
import com.sensetime.ad.core.StFaceLiveness;
import com.sensetime.ad.core.StFaceTrack;
import com.sensetime.ad.core.StFaceVerify;
import com.sensetime.ad.sdk.StAttributeResult;
import com.sensetime.ad.sdk.StFace;
import com.sensetime.ad.sdk.StFaceFeature;
import com.sensetime.ad.sdk.StFaceOrientation;
import com.sensetime.ad.sdk.StImageFormat;
import com.sensetime.ad.sdk.StPointF;
import com.test.face.constants.FaceConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

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


    public static StFaceFeature testFace(String picture) {

        init();

        StFaceFeature feature = null;

        try {
            System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
            Mat frame = Imgcodecs.imread(picture, 1);
            int width = frame.cols();
            int height = frame.rows();
            byte[] data = new byte[width * height * 3];//BGR888
            frame.get(0, 0, data);

            List<StFaceFeature> features = new ArrayList<StFaceFeature>();

            //detect



            StFace[] faces = stFaceTrack.detectBasicInfo(data, StImageFormat.ST_PIX_FMT_BGR888, width, height, StFaceOrientation.ST_FACE_UP);
            if (faces != null) {
                for (StFace face : faces) {
                    //feature
                    feature = stFaceVerify.getFeature(data, StImageFormat.ST_PIX_FMT_NV21, width, height, face);
                    String featureString = null;
                    featureString = new String(feature.getByteFeature());
                    System.out.println("feature:" + featureString);

                }
            }
        } catch (StFaceException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }

        postTest();

        return feature;
    }

    public static int init() {

        try {
            stFaceTrack = new StFaceTrack(detectDetectModel, alignModel, poseModel, 0);
            stFaceAttribute = new StFaceAttribute(attrModel, alignModel);
            stFaceVerify = new StFaceVerify(alignModel, verifyModel);
            stFaceLiveness = new StFaceLiveness(liveMode, alignModel);
        } catch (StFaceException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void postTest() {
        stFaceTrack.release();
        stFaceAttribute.release();
        stFaceLiveness.release();
        stFaceVerify.release();
    }


}
