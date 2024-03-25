package typora.uppic.common;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

/**
 * @Description 文件上传类,暂时只支持流浪网盘
 * @Todo 更多网盘支持,网盘上传数据结果落库(h2)
 * @Date 2024/3/25 18:51
 * @Created by 76574
 */
public class sendFile {

    private static Logger logger = LoggerFactory.getLogger(sendFile.class);

    /**
     * 发送文件到网盘
     * 先从外部读取配置文件,如果没有那么读取默认配置文件
     * @param fileUrl 文件本地路径
     * @return ApiResult
     */
    public ApiResult toNetWorkDisk(String fileUrl) throws IOException {
        ApiResult result = ApiResult.init();

        //初始化配置文件
        ReadProperties readProperties = new ReadProperties();
        ResourceBundle bundle = readProperties.getProperties();
        String serverUrl = bundle.getString("uppic.haveTo.serverUrl");
        String fileNameParamter = bundle.getString("uppic.haveTo.fileNameParamter");
        Boolean reNameFile = Boolean.valueOf(bundle.getString("uppic.ReNameFile"));
        logger.debug("serverUrl:{}",serverUrl);

        //判断文件是否存在
        File file = new File(fileUrl);
        if (!file.exists()){
            return ApiResult.fail(404,"文件不存在");
        }

        //文件存在,开始上传
        String fileName;
        if (reNameFile){
            fileName = String.valueOf(System.currentTimeMillis());
        }else {
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length()-1);
        }
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("image/png"));

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(fileNameParamter,fileName,fileBody)
                .build();



        return result;
    }

    public static void main(String[] args) throws IOException {
        ReadProperties readProperties = new ReadProperties();
        ResourceBundle bundle = readProperties.getProperties();
        String s[] = bundle.getStringArray("uppic.have.map");
        System.out.println(s.length);
        for (String key:bundle.keySet()){
            System.out.println(key);
        }
    }
}
