package com.test.face;




import com.sensetime.ad.sdk.StBody;
import com.sensetime.ad.sdk.StLibrary;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Logger;

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
        String testImage = "/root/body/4.png";
        //LibraryUtil.addLibraryPath("./system/");
        String license = readLicense("/home/data/sense-face/sense-time-license.lic");
        String signedCode = StLibrary.generateActivationCode("SenseInsight", license,retcode);
        retcode[0] = StLibrary.checkActivationCode("SenseInsight", license, signedCode);

        StLibrary.stEnvInit(license, signedCode, "SenseInsight", "/home/data/body/senu.kep", "/home/data/body/classifier.kep", retcode);
        System.out.println("环境初始化，result：" + retcode[0]);

        long classifier = StLibrary.stClassifierCreate("{\"model\":\"/home/data/body/models/KM_Classifier_Ped_Filter_ppl_1.2.1.model\"}",retcode);
        System.out.println("创建人体质量句柄，result：" + retcode[0]);

        long reidHandle = StLibrary.stReIdInitHandle("{\"model\":\"/home/data/body/models/KM_Senu_ppl_1.4.5.model\"}",retcode);
        System.out.println("创建特征提取句柄，result：" + retcode[0]);

        long bodyHandler = StLibrary.stCommonBodyTrackingCompactCreate(bodydetect, bodyaligment, retcode);
        System.out.println("创建人体检测句柄，result：" + retcode[0]);

        int width[] = new int[1];
        int height[] = new int[1];
        long cur2 = System.currentTimeMillis();
        System.out.println("cur2:"+cur2);
        byte[] image = StLibrary.stGetBgrDataFromImage(testImage, width, height);
        System.out.println("image length:"+image.length);
        StBody[] bodies = null;
        do{
            System.out.println("开始人体识别");
            bodies = StLibrary.stCommonBodyTrackingCompactTrack(bodyHandler, image, 5, width[0], height[0], 1, retcode);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (bodies == null || bodies.length == 0);
        System.out.println("开始质量分检测");
        float quality = StLibrary.stGetQuality(classifier, image, 5, width[0], height[0], bodies[0], retcode);
        System.out.println("人体质量："+quality);
        long cur = System.currentTimeMillis();
        System.out.println("开始特征值提取");
        byte feature1[] = StLibrary.stReIdProcessImage(reidHandle, image, 5, width[0], height[0], bodies[0], retcode);
        System.out.println("zqt time = " + (System.currentTimeMillis() - cur));
        System.out.println("zqt time1 = " + (System.currentTimeMillis() - cur2));

        image = StLibrary.stGetBgrDataFromImage("/root/body/1.png", width, height);
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
        System.out.println("最终结果：score：" + score);
    }

}
