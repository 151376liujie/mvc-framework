package org.framework.java.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流相关工具类
 * 
 * @author liujie
 * 
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
    public static String getString(InputStream inputStream) {
	StringBuilder sb = new StringBuilder();
	BufferedReader reader = new BufferedReader(new InputStreamReader(
		inputStream));
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
