package typora.uppic.common;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * @Description @读取外部配置文件
 * @Date 2024/3/25 7:44
 * @Created by 76574
 */
public class ReadProperties {

    /**
     * 获取配置信息,先判断当前jar下有没有application.properties,如果没有,那么从resources目录下读取默认配置
     * @return 配置信息
     */
    public ResourceBundle getProperties() throws IOException {
//        获取jar包路径
        URL url = this.getClass().getProtectionDomain().getCodeSource().getLocation();
        System.out.println("urlpath:"+url.getPath());
        String jarAllPath = url.getPath();
        String jarLocation= jarAllPath.substring(0,jarAllPath.indexOf("/uppic/target/")+"/uppic/target/".length());

        jarLocation += "application.properties";
        File file = new File(jarLocation);
        ResourceBundle resourceBundle;
        if (file.exists()&&file.isFile()){
            System.out.println("读取到外部配置");
            InputStream inputStream = new FileInputStream(jarLocation);
            resourceBundle = new PropertyResourceBundle(inputStream);
        }else {
            System.out.println("读取到本地配置");
            resourceBundle = ResourceBundle.getBundle("application");
        }
        System.out.println("读取到的server地址为:"+resourceBundle.getString("uppic.serverurl"));
        return resourceBundle;
    }

    /**
     * 读取指定前缀的值
     * @param bundle
     * @param prefix
     * @return
     */
    public Map<String,String> readBundleValueByPrefix(ResourceBundle bundle,String prefix){
        return null;
    }


    public static void main(String[] args) {
        ReadProperties readProperties = new ReadProperties();
        try {
            readProperties.getProperties();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
