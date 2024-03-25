package typora.uppic.common;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;

public class SendHttp {

    public static void main(String[] args) throws IOException {
        ReadProperties readProperties = new ReadProperties();
        ResourceBundle bundle = readProperties.getProperties();

        String url = bundle.getString("uppic.serverurl");
//        String filePath = "D:\\\\MyFiles\\\\Pictures\\\\Screenshots\\\\test.png";


        for (String filePath: args){
            System.out.println(filePath);

            String fileName = filePath.substring(filePath.lastIndexOf("\\\\")+2,filePath.length()-1);
            File file = new File(filePath);

            RequestBody fileBody = RequestBody.create(file,MediaType.parse("image/png"));

            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("filename",fileName,fileBody)
                    .build();

            HttpUrl httpUrl = HttpUrl.parse(url).newBuilder()
                    .addQueryParameter("filename",fileName)
                    .build();

            Request request = new Request.Builder()
                    .url(httpUrl)
                    .post(requestBody)
                    .build();

            OkHttpClient httpClient = new OkHttpClient().newBuilder()
//                .cookieJar(new SaveCookie())
                    .build();
            Response response = httpClient.newCall(request).execute();
            System.out.println(response.body().string());
        }


    }
}
