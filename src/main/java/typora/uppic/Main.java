package typora.uppic;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;

public class Main {
//    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        System.out.println("Upload Success:");
//        logger.info("123,args.size:{}",args.length);
        for (String s:args){
            System.out.println("http://www.baidu.com/"+s);
//            logger.info("param:{}",s);
        }

//        String filePath = "/E:/codeSpace/learn/uppic/target/uppic-1.0-SNAPSHOT.jar";
//        System.out.println(filePath.substring(0,filePath.lastIndexOf("/uppic/target/")+"/uppic/target/".length()));
//        System.out.println(STR."\{filePath.length()} \{filePath.lastIndexOf("\\\\")}");
//        String fileName = filePath.substring(filePath.lastIndexOf("\\\\")+2,filePath.length()-1);
//        System.out.println(fileName);
//        System.out.println("Upload Success:"+System.lineSeparator()+"http://remote-image-1.png"+System.lineSeparator()+"http://remote-image-1.png");

    }

    public String test(String[] args){
        System.out.println(args);
        return "https://www.baidu.com/a.png";
    }
}