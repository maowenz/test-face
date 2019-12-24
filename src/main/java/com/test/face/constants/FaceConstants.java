package com.test.face.constants;


import java.util.ResourceBundle;

/**
 *
 * 读取face-config.properties配置文件的内容
 * @author wzm
 */
public class FaceConstants {


    protected static ResourceBundle resourceBundle = ResourceBundle.getBundle("face-config");

    /**
     * [resource]
     * 授权和激活文件
     */
    public static String license_file_name = resourceBundle.getString("license_file_name");

    public static String activation_code_file_name = resourceBundle.getString("activation_code_file_name");


    /**
     * 模型文件
     * detect_track_name
     * headpose_name
     * alignment_name
     * verify_name
     * attribute_name
     * liveness_name
     * detect_detect_name
     */

    public static String detect_track_name = resourceBundle.getString("detect_track_name");

    public static String headpose_name = resourceBundle.getString("headpose_name");

    public static String alignment_name = resourceBundle.getString("alignment_name");

    public static String verify_name = resourceBundle.getString("verify_name");

    public static String attribute_name = resourceBundle.getString("attribute_name");

    public static String liveness_name = resourceBundle.getString("liveness_name");

    public static String detect_detect_name = resourceBundle.getString("detect_detect_name");



}
