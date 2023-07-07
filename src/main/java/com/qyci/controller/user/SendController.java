package com.qyci.controller.user;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.entity.Condition;
import com.qyci.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.Random;

@Slf4j
@Controller
@RequestMapping("/send")
public class SendController {

    @ResponseBody
    @PostMapping(value = "/upload_file")
    public Object upload(@RequestParam(value = "file") MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        return JSON.toJSONString(1);
    }

    public ResponseResult

    @ResponseBody
    @PostMapping("/comment_upload_file")
    public Object commentUpload(@RequestParam(value = "file") MultipartFile file) throws IOException {
        // 按点分割
        String[] temps = file.getOriginalFilename().split("\\.");

        // 获取后缀
        String suf = temps[temps.length - 1];

        // 使用时间戳生成文件名
        String fileName = System.currentTimeMillis() + "." + suf;

        // 写入路径
        Path writePath = Path.of("C:\\inetpub\\wwwroot\\comment\\file\\", fileName);

        // 服务器路径
        String webPath = "http://10.3.109.11/comment/file/" + fileName;

//        System.out.println(writePath);


        boolean append = false;
        // 创建对应文件
        if (!Files.exists(writePath)) {
            Files.createFile(writePath);
        }
        // 文件流
        byte[] bytes = file.getBytes();
        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(writePath.toFile(), append))) {
            // 写入文件
            out.write(bytes);
            // 刷新缓存
            out.flush();
            // 设置返回格式
            String format = "{\"link\":\"%s\"}";
            // 生成返回结果
            return String.format(format, webPath);
        } catch (Exception e) {
            throw new IOException(e);
        }
    }











    /*
    以下是发送验证码部分
     */

    @Autowired
    private JavaMailSender sender;

    @ResponseBody
    @PostMapping("/email-code")
    public Object seadEmail(@RequestBody User user, ModelMap model) {// 发送验证码
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("1635276938@qq.com");           // 设置发件人邮箱（若配置默认邮箱则不用再设置）
        System.out.println(user.getEmail());
        message.setTo(user.getEmail());                 // 设置收件人邮箱
        message.setSubject("QYCI Verification Code");   // 设置邮件主题
        String check = getCheck();
        message.setText("QYCI 安全代码：" + check);    // 设置邮件文本内容
        message.setSentDate(new Date());                // 设置邮件发送时间
        Condition condition = MySession.getBean(Condition.class);
        condition.setCheck(check);
        model.put("condition", condition);
        sender.send(message);
        return JSON.toJSONString("code");
    }

    private static final char[] chars = new char[]{
            '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b',
            'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n',
            'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F',
            'G', 'H', 'I', 'J', 'K', 'L',
            'M', 'N', 'O', 'P', 'Q', 'R',
            'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z'
    };


    private static String getCheck() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        int checkSize = random.nextInt(4) + 7;
        for (int i = 0; i < checkSize; i++) {
            int index = random.nextInt(chars.length);
            sb.append(chars[index]);
        }
        return sb.toString();
    }
}
