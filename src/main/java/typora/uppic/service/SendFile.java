package typora.uppic.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import typora.uppic.common.ApiResult;
import typora.uppic.common.ReadProperties;

import java.io.*;
import java.util.Map;
import java.util.Properties;

/**
 * @Description 文件上传类,暂时只支持流浪网盘
 * @Todo 更多网盘支持,网盘上传数据结果落库(h2)
 * @Date 2024/3/25 18:51
 * @Created by 76574
 */
public class SendFile {

    private static Logger logger = LoggerFactory.getLogger(SendFile.class);

    /**
     * 发送文件到网盘
     * 先从外部读取配置文件,如果没有那么读取默认配置文件
     * @param fileUrl 文件本地路径
     * @return ApiResult
     */
    public ApiResult toNetWorkDisk(String fileUrl) throws IOException {
        //初始化配置文件
        ReadProperties readProperties = new ReadProperties();
        Properties properties = readProperties.getProperties();
        String serverUrl = properties.getProperty("uppic.notEmpty.serverUrl");
        String fileNameParamter = properties.getProperty("uppic.notEmpty.fileNameParamter");
        if (serverUrl.isBlank()||fileNameParamter.isBlank()){
            return ApiResult.init();
        }

        Boolean reNameFile = Boolean.valueOf(properties.getOrDefault("uppic.ReNameFile",false).toString()) ;
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
            fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1,fileUrl.length());
        }

            FileReader fileReader = new FileReader(file);
            BufferedReader reader = new BufferedReader(fileReader);
            StringBuilder builder = new StringBuilder();
            String temp = "";
            while ((temp = reader.readLine()) != null) {
                builder.append(temp + "\n");
            }
            reader.close();
            logger.debug("图片内容为:{}",builder);



        RequestBody fileBody = RequestBody.create(builder.toString(), MediaType.parse("application/octet-stream"));

        //准备塞入post入参
        Map<String,String> param = readProperties.readBundleValueByPrefix(properties,"uppic.post.param");
        logger.info("读取到{}个post入参",param.size());
        MultipartBody.Builder postBuilder = new MultipartBody.Builder();
        postBuilder.addFormDataPart(fileNameParamter,fileName,fileBody);
        for (Map.Entry<String, String> entrySet: param.entrySet()){
            postBuilder.addFormDataPart(entrySet.getKey(),entrySet.getValue());
        }
        RequestBody requestBody = postBuilder.setType(MultipartBody.FORM)
                .build();

        //准备请求url以及附带的get参数
        param = readProperties.readBundleValueByPrefix(properties,"uppic.get.param");
        logger.info("读取到{}个get入参",param.size());
        HttpUrl.Builder urlBuilder = HttpUrl.parse(serverUrl).newBuilder();
        urlBuilder.addQueryParameter(fileNameParamter,fileName);
        for (Map.Entry<String,String> entry:param.entrySet()){
            urlBuilder.addQueryParameter(entry.getKey(),entry.getValue());
        }
        HttpUrl url = urlBuilder.build();

        //设置请求
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        //发起请求
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        //todo cookie管理
        OkHttpClient httpClient = okHttpBuilder.build();
        Response response = httpClient.newCall(request).execute();
        logger.info("请求server发送成功,返回code为:{}",response.code());

        //封装状态码准备返回
        ApiResult result;
        if (response.isSuccessful()){
            String responseBodyString = response.body().string();
            logger.debug("请求响应数据为:{}",responseBodyString);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode responseJson = mapper.readTree(responseBodyString);
            String returnKey = properties.getProperty("uppic.returnkey");
            logger.info("读取到的返回值key为:{}",returnKey);
            if (returnKey.contains(".")){
                //多层级
                for (String key:returnKey.split("\\.")){
                    responseJson = responseJson.get(key);
                }
            }
            String netWorkfileUrl = responseJson.asText();
            result = ApiResult.success(netWorkfileUrl);
        }else {
            result = ApiResult.fail(response.code(),response.message());
        }
        return result;
    }

    public static void main(String[] args) throws IOException {
        SendFile sendFile = new SendFile();
        ApiResult apiResult = sendFile.toNetWorkDisk("/Users/dengtianjiao/Library/Application Support/typora-user-images/image-20240327114106772.png");
        System.out.println(apiResult.getFileUrl()+" "+apiResult.getCode());
    }
}
