package com.myblog.version3.controller.User;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String[] args){
        File oldOne = new File("E:\\MyBLOGFileFolder\\02fbdaaa\\default\\98dc1ac3");
        File newOne = new File("E:\\MyBLOGFileFolder\\02fbdaaa\\2fasf13l\\98dc1ac3");
        try {
            newOne.mkdirs();
            FileUtils.copyDirectory(oldOne,newOne);
            FileUtils.deleteDirectory(newOne);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
