package org.framework.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 封装请求参数
 *
 * @author liujie
 */
public class WebRequestParameterBinder implements Serializable {

    private List<FileRequest> fileRequests = new ArrayList<>();

    private List<FormRequest> formRequests = new ArrayList<>();

    public WebRequestParameterBinder(List<FormRequest> formRequests) {
        if (formRequests != null) {
            this.formRequests = formRequests;
        }
    }

    public WebRequestParameterBinder(List<FileRequest> fileRequests, List<FormRequest> formRequests) {
        if (fileRequests != null) {
            this.fileRequests = fileRequests;
        }
        if (formRequests != null) {
            this.formRequests = formRequests;
        }
    }

    public static WebRequestParameterBinder of(List<FileRequest> fileRequests, List<FormRequest> formRequests) {
        return new WebRequestParameterBinder(fileRequests, formRequests);
    }

    /**
     * 根据指定名称获取参数值
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        if (this.getFormParameterMap().isEmpty()) {
            return null;
        }
        return this.getFormParameterMap().get(name);
    }

    public boolean isEmpty() {
        return this.fileRequests.isEmpty() && this.formRequests.isEmpty();
    }

    /**
     * 获取表单参数map
     *
     * @return
     */
    public Map<String, String> getFormParameterMap() {
        Map<String, String> map = new HashMap();
        if (this.formRequests != null && !this.formRequests.isEmpty()) {
            formRequests.stream().forEach(formItem -> {
                String fieldName = formItem.getFieldName();
                String fieldValue = formItem.getFieldValue();
                String val = fieldValue;
                if (map.containsKey(fieldName)) {
                    val = map.get(fieldName) + "," + fieldValue;
                }
                map.put(fieldName, val);
            });
        }
        return map;
    }

    /**
     * 获取上传文件参数map
     *
     * @return
     */
    public Map<String, List<FileRequest>> getFileParameterMap() {
        Map<String, List<FileRequest>> map = new HashMap<>();
        if (fileRequests != null && !fileRequests.isEmpty()) {
            fileRequests.stream().forEach(fileItem -> {
                String fieldName = fileItem.getFieldName();
                if (!map.containsKey(fieldName)) {
                    List<FileRequest> list = new ArrayList<>();
                    list.add(fileItem);
                    map.put(fieldName, list);
                } else {
                    map.get(fieldName).add(fileItem);
                }
            });
        }
        return map;
    }

    /**
     * 获取指定字段的所有上传文件
     *
     * @param fieldName
     * @return
     */
    public List<FileRequest> getFileList(String fieldName) {
        return getFileParameterMap().get(fieldName);
    }

    /**
     * 获取指定字段的单个上传文件
     *
     * @param fieldName
     * @return
     */
    public FileRequest getFile(String fieldName) {
        List<FileRequest> fileList = getFileList(fieldName);
        if (fileList != null && fileList.size() == 1) {
            return fileList.get(0);
        }
        return null;
    }

}
