package typora.uppic.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.util.*;

/**
 * @Description @读取外部配置文件
 * @Date 2024/3/25 7:44
 * @Created by 76574
 */
public class ReadProperties {

    private Logger logger = LoggerFactory.getLogger(ReadProperties.class);

    /**
     * 获取配置信息,先判断当前jar下有没有application.properties,如果没有,那么从resources目录下读取默认配置
     * @return 配置信息
     */
    public Properties getProperties() throws IOException {
//        获取jar包路径
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        logger.debug("urlpath:{}",url.getPath());
        String jarAllPath = url.getPath();
        String jarLocation= jarAllPath.substring(0,jarAllPath.indexOf("/uppic/target/")+"/uppic/target/".length());
        logger.debug("jarLocation:{}",jarLocation);
        jarLocation += "application.properties";
        File file = new File(jarLocation);
        InputStream inputStream;
        if (file.exists()&&file.isFile()){
            logger.debug("读取到外部配置");
            inputStream = new FileInputStream(jarLocation);

        }else {
            logger.debug("读取到本地配置");
            inputStream = this.getClass().getClassLoader().getResourceAsStream("application.properties");
        }
        Properties properties = new Properties();
        properties.load(inputStream);
        logger.info("读取到的server地址为:{}",properties.getProperty("uppic.notEmpty.serverUrl"));
        return properties;
    }

    /**
     * 读取指定前缀的值
     * @param prop
     * @param prefix
     * @return
     */
    public Map<String,String> readBundleValueByPrefix(Properties prop,String prefix){
        Map<String,String> result = new HashMap<>();
        for (Object o:prop.keySet()){
            String key = o.toString();
            if (key.contains(prefix)){
                String prefixKey = key.replace(prefix+".","");
                logger.debug("prefixkey:{}",prefixKey);
                result.put(prefixKey,prop.getProperty(key));
            }
        }
        return result;
    }
}
