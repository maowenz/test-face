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
        String feature = "QpwAAAAAAAAMAgAAI3ezun0Clr35SCG+clvKPeT3d72kKKq9LKkdvT9+ojuuwbu8KJuCvf8K570Ktu28YF8SPgeQEL0SXNm9ajdwvXayPDw18v89/nHHPDjeYD22ycS93bdSOjo6qLxkBHO9flv4PTPgWT5lrBM+4QesvbiP9rfPqnY7c8ERvZay3DzCKhC8DATiPWIlmD06qb08eNASPIKz0bx2Ac29o8NXvIDLnr11n4K8j2pbPEFzhb0fzAC+CKDEvbyx07wQVaQ9IkDfPO2/JLwMAQs++pFevSIfpD1qESI9GckVPdDgyTxvX+o6cnvivYsxcbyQUEU+iakIvZoeSryQGxW9JCLKvZzvGT5SCJ88/Wgfvi+mI7w+nD2+h+U4PSlzJr7nEVu9oC+TPVjOFr05dRI8sZziPfFWGD3nAO862B+oOySdozzTtOG8JjicvmK1VT0pDoe9R0omvFl9LL4mDLM7bGYUPik6DL747eg91lsZvnFNQzs5+eK9fco3vVHTvj1OP/O9AYxUvnUWuLsqYJI9xKqBPYd+6rxgGjo8eA1zvZpjqL3DVre8y1pJvWGMnb30KwI+fgR1vVkSKr7uuLg8FVm5vRZVxb26iIC8S+6UvfcH0Dy3z1y8vc4KvkyCKzyEW5y9XQtpvcI2RD74aW+9xfyLvZYfVT1JRIS9Q9SDPO4Boj0=";


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
