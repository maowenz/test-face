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

        int activate = GetActivationCode.activate(FaceConstants.license_file_name, FaceConstants.activation_code_file_name);
        if(activate != 0) {
            System.err.println("激活失败");
        }


        //String feature = "QpwAAAAAAAAMAgAAJXM3PbIivz0aMge9P3+qvQOqwr0p9Rs927QtPBOwMb0xM0m+0HPNPXf3hT1T/3s7D7STPWqINT1O1WS+kMs8vvlWPL16iwa+xFHJPfY92DtUgo898SQsu8r3Wr0yNFQ+fv3IvZpm3LxN67M9+KegOoIrwrzpebQ9jMwUPs2EJb0eW9S8dWDzvVOKBTy3YWo9OYvePZCitL02eRG8ofhwPPEdIjva+aE8LEwZvvoeF72Eq5u7p8wePvY+O77PJme84JfzPSIROL2V7Zq9BRqYvLsWxL31+O49ZnmcvTsTFj20obA94tf+u15lQb0lo3G9X+snvZrGVj2MCrm9U0xhvT6uAL40oUi9Gl7fvcqYob1kXRs9IY06PY7KXT2LnY49AToAvijA5z2USFk9cuOrO8UdND68RrY8zlPQvaGStD1TS+w9er4OPVCYtr0I0m2+9+Dbvf6xv70eQCQ9s6PFOz627T0Aita9BRGAPOlxybzTVy++kJ3mPW0txLtYsaS7p/bhvZbs7j3LfwY+oIIfvqv0F7yaR547RwT7PQZ/QrgGbz294i8Ovr4oNL0GoC09bdcEvZDmSL0JmGu9jNt5vBZj4j0Z1tc8ew8NPYDwBr65lds8UP5ZuxF1Nr26gKu9ZkkCPcNPVb18bNC95Q7oPCfEijyBdNc9pgCWPRML7D0=";
        String feature = "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";


        try {
            StFaceVerify stFaceVerify = new StFaceVerify(alignModel, verifyModel);
            StFaceFeature feature1 = GetFeature.testFace(args[0]);
            //StFaceFeature feature2 = GetFeature.testFace(args[1]);
            StFaceFeature feature2 = StFaceFeature.createStFaceFeature(feature.getBytes());
            if(null != feature1) {
                float score = stFaceVerify.compareFeature(feature1, feature2);
                System.out.println("compare score: " + score);
            }
            System.out.println("get feature is null.feature1:"+feature1);
            stFaceVerify.release();
        } catch (StFaceException e) {
            e.printStackTrace();
        }

    }

}
