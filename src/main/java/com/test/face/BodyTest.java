package com.test.face;




import com.sensetime.ad.sdk.StBody;
import com.sensetime.ad.sdk.StLibrary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author wzm
 * @date 2020年08月12日 7:54 下午
 */
public class BodyTest {

    private static String readLicense(String path) {
        String license = null;

        File file = new File(path);
        try {
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte buffer[] = new byte[1024];
            int n;
            while ((n = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, n);
            }
            fis.close();
            bos.flush();
            bos.close();
            license = new String(bos.toByteArray());
        } catch (IOException e) {
        }
        return license;
    }


    public static void main(String[] args) {
        int retcode[] = new int[1];
        String bodydetect = "/home/data/body/models/M_Detect_Body_Hunter_1.7.0.model";
        String bodyaligment = "/home/data/body/models/M_Detect_Body_Keypoints_5.5.0.model";
        String testImage = "./4.jpeg";
        //LibraryUtil.addLibraryPath("./system/");
        String license = readLicense("/home/data/sense-face/sense-time-license.lic");
        String signedCode = StLibrary.generateActivationCode("SenseInsight", license,retcode);
        retcode[0] = StLibrary.checkActivationCode("SenseInsight", license, signedCode);

        StLibrary.stEnvInit(license, signedCode, "SenseInsight", "/home/data/body/senu.kep", "/home/data/body/classifier.kep", retcode);
        long classifier = StLibrary.stClassifierCreate("{\"model\":\"/home/data/body/models/KM_Classifier_Ped_Filter_ppl_1.2.1.model\"}",retcode);
        //Assert.assertTrue("Create env failed! errorcode  = " + retcode[0], retcode[0] == 0);
        long reidHandle = StLibrary.stReIdInitHandle("{\"model\":\"/home/data/body/models/KM_Senu_ppl_1.4.5.model\"}",retcode);
//        Assert.assertTrue("Create reidHandle failed! errorcode  = " + retcode[0], reidHandle != 0);
        long bodyHandler = StLibrary.stCommonBodyTrackingCompactCreate(bodydetect, bodyaligment, retcode);
        int width[] = new int[1];
        int height[] = new int[1];
        long cur2 = System.currentTimeMillis();
        byte[] image = StLibrary.stGetBgrDataFromImage(testImage, width, height);
        StBody[] bodies = null;
        do{
            bodies = StLibrary.stCommonBodyTrackingCompactTrack(bodyHandler, image, 5, width[0], height[0], 1, retcode);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (bodies == null || bodies.length == 0);
        float quality = StLibrary.stGetQuality(classifier, image, 5, width[0], height[0], bodies[0], retcode);
        long cur = System.currentTimeMillis();
        byte feature1[] = StLibrary.stReIdProcessImage(reidHandle, image, 5, width[0], height[0], bodies[0], retcode);
        System.out.println("zqt time = " + (System.currentTimeMillis() - cur));
        System.out.println("zqt time1 = " + (System.currentTimeMillis() - cur2));

        image = StLibrary.stGetBgrDataFromImage("./1.jpg", width, height);
        do{
            bodies = StLibrary.stCommonBodyTrackingCompactTrack(bodyHandler, image, 5, width[0], height[0], 1, retcode);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (bodies == null || bodies.length == 0);
        quality = StLibrary.stGetQuality(classifier, image, 5, width[0], height[0], bodies[0], retcode);
        byte feature2[] = StLibrary.stReIdProcessImage(reidHandle, image, 5, width[0], height[0], bodies[0], retcode);
        float score = StLibrary.stReIdCompare(feature1, feature2);
    }

}
