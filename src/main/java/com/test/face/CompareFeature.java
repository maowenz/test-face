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


        String feature = "QpwAAAAAAAAMAgAAJXM3PbIivz0aMge9P3+qvQOqwr0p9Rs927QtPBOwMb0xM0m+0HPNPXf3hT1T/3s7D7STPWqINT1O1WS+kMs8vvlWPL16iwa+xFHJPfY92DtUgo898SQsu8r3Wr0yNFQ+fv3IvZpm3LxN67M9+KegOoIrwrzpebQ9jMwUPs2EJb0eW9S8dWDzvVOKBTy3YWo9OYvePZCitL02eRG8ofhwPPEdIjva+aE8LEwZvvoeF72Eq5u7p8wePvY+O77PJme84JfzPSIROL2V7Zq9BRqYvLsWxL31+O49ZnmcvTsTFj20obA94tf+u15lQb0lo3G9X+snvZrGVj2MCrm9U0xhvT6uAL40oUi9Gl7fvcqYob1kXRs9IY06PY7KXT2LnY49AToAvijA5z2USFk9cuOrO8UdND68RrY8zlPQvaGStD1TS+w9er4OPVCYtr0I0m2+9+Dbvf6xv70eQCQ9s6PFOz627T0Aita9BRGAPOlxybzTVy++kJ3mPW0txLtYsaS7p/bhvZbs7j3LfwY+oIIfvqv0F7yaR547RwT7PQZ/QrgGbz294i8Ovr4oNL0GoC09bdcEvZDmSL0JmGu9jNt5vBZj4j0Z1tc8ew8NPYDwBr65lds8UP5ZuxF1Nr26gKu9ZkkCPcNPVb18bNC95Q7oPCfEijyBdNc9pgCWPRML7D0=";


        try {
            StFaceVerify stFaceVerify = new StFaceVerify(alignModel, verifyModel);
            StFaceFeature feature1 = GetFeature.testFace(args[0]);
            //StFaceFeature feature2 = GetFeature.testFace(args[1]);
            if(null != feature1) {
                float score = stFaceVerify.compareFeature(feature1.getByteFeature(), feature.getBytes());
                System.out.println("compare score: " + score);
            }
            System.out.println("get feature is null.feature1:"+feature1);
            stFaceVerify.release();
        } catch (StFaceException e) {
            e.printStackTrace();
        }

    }

}
