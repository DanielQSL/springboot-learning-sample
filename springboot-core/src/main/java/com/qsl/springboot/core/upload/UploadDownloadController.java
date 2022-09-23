package com.qsl.springboot.core.upload;

import cn.hutool.core.util.StrUtil;
import com.qsl.project.base.exception.BusinessException;
import com.qsl.project.base.model.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.UUID;

/**
 * @author shuailong.qian
 * @date 2022/9/23
 */
@Slf4j
@RestController
public class UploadDownloadController {

    /**
     * 小文件最大大小: 2MB
     */
    private static final long FILE_MAX_SIZE = 2 * 1024 * 1024;

    @GetMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public CommonResponse<?> uploadFile(@RequestParam("id") Integer id,
                                        @RequestParam("file") MultipartFile file) {
        // 获取文件名
        String fileName = file.getOriginalFilename();

        if (StrUtil.isBlank(fileName)) {
            throw new BusinessException("文件名不能为空");
        }
        // 大文件判定
        if (file.getSize() > FILE_MAX_SIZE) {
            throw new BusinessException("文件过大，请使用大文件传输");
        }
        // 生成新文件名
        String suffixName = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : null;
        String newFileName = UUID.randomUUID().toString() + suffixName;

        // 获取文件输入流
        try (InputStream inputStream = file.getInputStream()) {
            // 处理输入流
        } catch (Exception e) {
            log.error("文件上传出错 id: {}", id, e);
        }
        return CommonResponse.success();
    }

    @GetMapping("/download")
    public CommonResponse<?> downloadFile(@RequestParam("id") Integer id, HttpServletResponse response) {
        // 此处获取输入流
        try (InputStream inputStream = null;
             OutputStream outputStream = response.getOutputStream()) {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode("文件名", "UTF-8"));
            IOUtils.copy(inputStream, outputStream);
        } catch (Exception e) {
            log.error("文件下载出错 id: {}", id, e);
            throw new BusinessException("文件下载出错");
        }
        return CommonResponse.success();
    }

}
