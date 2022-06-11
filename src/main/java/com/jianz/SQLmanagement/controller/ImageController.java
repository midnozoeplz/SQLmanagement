package com.jianz.SQLmanagement.controller;


import com.jianz.SQLmanagement.pojo.ResultBody;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.rmi.ServerException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Api(tags = "图片接口")
@RestController
public class ImageController {



    /**
     * @author Jianz
     * @Description 返回图片到后端
     * @Return
     * @Date 2022/3/1 10:19
     */
    @PostMapping("/upload")
    @Transactional
    public ResultBody upload(@RequestParam("uploadFile") MultipartFile zipFile) throws IOException, ServerException, NoSuchAlgorithmException, InvalidKeyException{
        /**
            需要修改的路径
         */
        String realPath = new String("D:\\Java\\IdeaProjects\\SQLmanagement\\productImg\\");
        // 将文件保存到指定路径下
        File dest = new File(new File(realPath).getAbsolutePath()+ "/" + zipFile.getOriginalFilename());
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
             // 保存文件
//            InputStream in = zipFile.getInputStream();
//            OutputStream out = new FileOutputStream(dest);
//            byte[] b = new byte[1024];
//            int len;
//            while((len =in.read(b))!= -1) {
//                out.write(b, 0, len);
//            }
//            in.close();
//            out.close();
            zipFile.transferTo(dest);

            System.out.println("图片传输成功！");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultBody.success("上传成功");
    }
}
