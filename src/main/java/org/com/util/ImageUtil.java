package org.com.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
/**
 * 上传图片
 */

public class ImageUtil {

    public final static String UploadPhoto(MultipartFile multipartFile, String filePath, String user_name){
        String filename="";
        int flag=0;
        try{
            String timestamp = String.valueOf(System.currentTimeMillis());
            String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf(".")+1);
            filename = user_name+timestamp+"."+prefix;
            File file = new File(filePath+filename);
            multipartFile.transferTo(file);
            flag=1;
        }catch (IOException e){
            e.printStackTrace();
            flag=0;
        }
        if (flag==1) return "/path/"+filename;
        else return "";
    }
}
