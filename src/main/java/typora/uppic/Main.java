package typora.uppic;

import okhttp3.HttpUrl;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import typora.uppic.common.ApiResult;
import typora.uppic.service.SendFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.http.HttpResponse;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) throws IOException {
        logger.debug("收到请求,入参为:{} ",args);
        SendFile sendFile = new SendFile();
        boolean justOne = true;
        for (String url: args){
            url = url.replaceAll("\\\\","/"); 
            ApiResult result = sendFile.toNetWorkDisk(url);
            if (result.getSuccess()){
                if (justOne){
                    System.out.println("Upload Success:");
                    justOne = false;
                }
                System.out.println(result.getFileUrl());
            }else {
                System.out.println("Upload failed");
            }
        }
    }

}