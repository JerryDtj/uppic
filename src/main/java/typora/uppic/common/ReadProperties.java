package com.jerry.typora.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.Properties;

/**
 * @Description @读取外部配置文件
 * @Date 2024/3/25 7:44
 * @Created by 76574
 */
public class ReadProperties {

    /**
     * 获取Jar所在的目录地址
     * @return jar 目录所在地址
     */
    public String getJarPath(){
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        System.out.println("urlpath:"+url.getPath());
        String jarAllPath = url.getPath();
        String jarLocation= jarAllPath.substring(0,jarAllPath.lastIndexOf(+"/uppic/target/".length()));
        return jarLocation;
    }

    /**
     * 获取配置信息,先判断当前jar下有没有application.properties,如果没有,那么从resources目录下读取默认配置
     * @return 配置信息
     */
    public Properties getProperties(){
        String localtion = this.getJarPath();
        return null;
    }

    public static void main(String[] args) {
        ReadProperties readProperties = new ReadProperties();
        readProperties.getJarPath();
    }
}
