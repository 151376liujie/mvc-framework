package org.framework.web;

import java.io.InputStream;

/**
 * 封装上传文件相关参数
 * Author: jonny
 * Time: 2017-08-10 13:43.
 */
public class FileRequest extends WebRequestBaseParameter {

    /**
     * 上传的文件名
     */
    private String fileName;
    /**
     * 文件大小,单位byte
     */
    private long size;
    /**
     * 文件输入流
     */
    private InputStream inputStream;

    private String contentType;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public void setFieldName(String fieldName) {
        super.setFieldName(fieldName);
    }
}
