package com.myblog.version3.controller.Management;

import com.myblog.version3.entity.Form.Form;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;

@RestController
@RequestMapping("/test")
@Api("表单上传测试接口")
public class test {


    @PostMapping(value = "/upload")
    public String upload(@Valid Form message) throws IOException {
        MultipartFile file = message.getImage();
        InputStream stream = file.getInputStream();
        BufferedInputStream inputStream = new BufferedInputStream(stream);
        String path = "D:\\test";
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流保存到本地文件
        File tempFile = new File(path,message.getTitle()+".jpg");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
        while ((len = inputStream.read(bs ,0,bs.length))!= -1){
            outputStream.write(bs ,0 ,len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return message.getTitle();
    }
}
