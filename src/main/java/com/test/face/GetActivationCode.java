package com.test.face;

import com.sensetime.ad.sdk.StLibrary;
import com.test.face.constants.FaceConstants;
import com.test.face.utils.FileUtils;
import com.test.face.utils.TextUtils;

import java.io.File;

/**
 * @author wzm
 * @date 2019年12月24日 14:38
 */
public class GetActivationCode {

    public static void main(String[] args) {

        int isActivate = activate(FaceConstants.license_file_name, FaceConstants.activation_code_file_name);
        if(isActivate == 0) {
            System.out.println("激活成功。activation code file: " + FaceConstants.activation_code_file_name);
        }else{
            System.out.println("激活失败");
        }
    }

    public static int activate(String licensePath, String activateCodePath) {

        FileUtils.createFile(activateCodePath);

        String license = FileUtils.readFileContent(licensePath);
        if (TextUtils.isEmpty(license)) {
            return -1;
        }

        String actCode = FileUtils.readFileContent(activateCodePath);
        if (TextUtils.isEmpty(actCode)) {
            int[] retCode = new int[1];
            actCode = StLibrary.onlineActivite("SenseInsight", license, retCode);
            if (retCode[0] != 0) {
                System.out.println("get certify code failed.");
                return retCode[0];
            }

            if (!TextUtils.isEmpty(actCode)) {
                FileUtils.writeToFile(actCode, activateCodePath);
            }
        }

        int ret = StLibrary.addLic("SenseInsight", license, TextUtils.isEmpty(actCode) ? "" : actCode);
        return ret;
    }

}
