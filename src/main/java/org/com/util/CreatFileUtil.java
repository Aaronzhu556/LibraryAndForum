package org.com.util;


import org.com.Entity.Article;
import org.com.Mapper.ArticleMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CreatFileUtil {



    public static final void creatFile(List<Article> articles) throws IOException {

        String path = "D:\\graduateproject2file\\";
        File file = new File(path);
        File[] files = file.listFiles();
        if (files.length!=0){ //删除原有文件
            for (File file1:files) file1.delete();
        }
        for (int i=0;i<articles.size();i++){
            File file_temp = new File(path+articles.get(i).getArticle_id()+".txt");

            try{
                file_temp.createNewFile();
                FileWriter fileWriter = new FileWriter(file_temp);
                fileWriter.write(articles.get(i).getArticle_content());
                fileWriter.flush();
                fileWriter.close();
            }catch (Exception e){
                System.out.println("写文件发生错误");
            }
        }




    }
}
