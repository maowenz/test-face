package com.test.face;

import com.sensetime.ad.core.StFaceException;
import com.sensetime.ad.core.StFaceVerify;
import com.sensetime.ad.sdk.StFaceFeature;
import com.test.face.constants.FaceConstants;

/**
 * @author wzm
 * @date 2019年12月24日 16:56
 */
public class CompareFeature {

    private static String verifyModel = FaceConstants.verify_name;

    private static String alignModel = FaceConstants.alignment_name;

    public static void main(String[] args) {


        try {
            StFaceVerify stFaceVerify = new StFaceVerify(alignModel, verifyModel);
            StFaceFeature feature1 = GetFeature.testFace(args[0]);
            StFaceFeature feature2 = GetFeature.testFace(args[1]);
            if(null != feature1 && null != feature2) {
                float score = stFaceVerify.compareFeature(feature1, feature2);
                System.out.println("compare score: " + score);
            }
            System.out.println("get feature is null.feature1:"+feature1+"feature2:"+feature2);
            stFaceVerify.release();
        } catch (StFaceException e) {
            e.printStackTrace();
        }

    }

}
