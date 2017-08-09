package org.framework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * 流相关工具类
 *
 * @author liujie
 */
public final class StreamUtils {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(StreamUtils.class);

    /**
     * 从流中得到字符串
     *
     * @param inputStream
     * @return
     */
    public static String getString(InputStream inputStream) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(
                inputStream, "utf-8"));
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sb.toString();
    }

}
