package com.example.ocr.SeviceF;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.ocr.Utils.Base64Util;
import com.example.ocr.Utils.HttpUtil;

import java.io.ByteArrayOutputStream;
import java.net.URLEncoder;

/**
 * 通用文字识别（高精度版）
 */
public class AccurateBasic {

    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String accurateBasic(String filePaht,String toten) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(filePaht);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            byte[] datas = output.toByteArray();

//            String filePath = filePaht;
//            byte[] imgData = FileUtil.readFileByBytes(filePath);

            String imgStr = Base64Util.encode(datas);
            String imgParam = URLEncoder.encode(imgStr, "UTF-8");

            String param = "image=" + imgParam;

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = toten;
            String result = HttpUtil.post(url, accessToken, param);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}