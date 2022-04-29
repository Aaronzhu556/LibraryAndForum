package org.com.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
@Slf4j
public class PythonUtils {

    public final static void recommendBook() {
        // TODO Auto-generated method stub

        Process proc;
        try {
            log.info("开始进行推荐");
            // 执行py文件

            proc = Runtime.getRuntime().exec("D:\\Anaconda\\python3.8.exe D:\\PycharmFile\\speak\\recommend2.py");
            //proc = Runtime.getRuntime().exec("python D:\\PycharmFile\\PPT_program-master\\final2.py");

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
        //获取转化之后的ppt对象的json然后存储到数据库中
        log.info("推荐进程中");
    }


    public final static void recommendArticle() {
        // TODO Auto-generated method stub

        Process proc;
        try {
            log.info("开始进行推荐");
            // 执行py文件

            proc = Runtime.getRuntime().exec("D:\\Anaconda\\python3.8.exe D:\\PycharmFile\\speak\\recommend2.py");
            //proc = Runtime.getRuntime().exec("python D:\\PycharmFile\\PPT_program-master\\final2.py");

            //用输入输出流来截取结果
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = null;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
            }
            in.close();
            proc.waitFor();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //TODO
        //获取转化之后的ppt对象的json然后存储到数据库中
        log.info("推荐进程中");
    }

}
