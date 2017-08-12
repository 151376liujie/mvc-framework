package org.framework.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.framework.web.FileRequest;
import org.framework.web.FormRequest;
import org.framework.web.WebRequestParameterBinder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传工具类
 * Author: jonny
 * Time: 2017-08-10 23:07.
 */
public class FileUploadHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUploadHandler.class);

    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化文件上传组件
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext) {

        if (LOGGER.isInfoEnabled()) {
            LOGGER.info("file upload handler preparing to init.");
        }

        File tempDir = (File) servletContext.getAttribute(ServletContext.TEMPDIR);
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, tempDir));
        long fileSizeMax = ConfigUtils.getUploadFileSizeMax();
        if (fileSizeMax != 0) {
            servletFileUpload.setFileSizeMax(fileSizeMax * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否是文件上传请求
     *
     * @param request
     * @return
     */
    public static boolean isMultiPartRequest(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    public static WebRequestParameterBinder createWebRequestParameterBinder(HttpServletRequest request) throws IOException {

        List<FormRequest> formRequests = new ArrayList<>();
        List<FileRequest> fileRequests = new ArrayList<>();

        try {
            Map<String, List<FileItem>> fileItems = servletFileUpload.parseParameterMap(request);
            if (fileItems != null && !fileItems.isEmpty()) {
                fileItems.entrySet().forEach(fileItemEntry -> {
                    String fieldName = fileItemEntry.getKey();
                    List<FileItem> fileItemList = fileItemEntry.getValue();

                    if (CollectionUtils.isNotEmpty(fileItemList)) {
                        fileItemList.forEach(fileItem -> {
                            if (fileItem.isFormField()) {
                                try {
                                    formRequests.add(new FormRequest(fieldName, fileItem.getString("utf-8")));
                                } catch (UnsupportedEncodingException e) {
                                    LOGGER.error(e.getMessage(), e);
                                }
                            } else {
                                String fileName = null;
                                fileName = new String(fileItem.getName().getBytes(), StandardCharsets.UTF_8);
                                if (StringUtils.isNotEmpty(fileName)) {
                                    FileRequest fileRequest = new FileRequest();
                                    try {
                                        fileRequest.setInputStream(fileItem.getInputStream());
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    fileRequest.setContentType(fileItem.getContentType());
                                    fileRequest.setSize(fileItem.getSize());
                                    fileRequest.setFileName(FilenameUtils.getName(fileName));
                                    fileRequest.setFieldName(fileItem.getFieldName());
                                    fileRequests.add(fileRequest);
                                }
                            }
                        });
                    }
                });
            }
        } catch (FileUploadException e) {
            LOGGER.error(e.getMessage(), e);
            throw new RuntimeException(e);
        }
        return WebRequestParameterBinder.of(fileRequests, formRequests);
    }

    public static void uploadFile(String basePath, FileRequest fileRequest) {
        if (fileRequest != null) {
            String filePath = basePath + fileRequest.getFileName();
            try {
                Files.copy(fileRequest.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
                throw new RuntimeException(e);
            }
        }
    }


    public static void uploadFile(String basePath, List<FileRequest> fileRequests) {
        if (CollectionUtils.isNotEmpty(fileRequests)) {
            fileRequests.stream().forEach(fileRequest -> {
                String filePath = basePath + fileRequest.getFileName();
                try {
                    Files.copy(fileRequest.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new RuntimeException(e);
                }
            });
        }
    }
}
