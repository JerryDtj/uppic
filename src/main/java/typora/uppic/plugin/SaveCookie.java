package com.jerry.typora.plugin;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 保存cookie的值,先暂时用map存起来
 * @Todo 后面考虑用h2存储或者写入到指定文件里面
 * @Date 2024/3/25 4:02
 * @Created by 76574
 */
public class SaveCookie implements CookieJar {
    private static final Logger logger = LoggerFactory.getLogger(SaveCookie.class);

    private final HashMap<String,List<Cookie>> cookieStore = new HashMap<>();

    /**
     * 该方法在发起请求时调用
     * @param httpUrl
     * @return
     */
    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        List<Cookie> result;
        logger.debug("host:{}",httpUrl.host());
        if (cookieStore.containsKey(httpUrl.host())){
            result = cookieStore.get(httpUrl.host());
        }else {
            //找不到cookie,重新请求,把cookie放入map中
            result = new ArrayList<>();
            OkHttpClient httpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(httpUrl)
                    .build();
            try {
                Response response = httpClient.newCall(request).execute();
                if (response.isSuccessful()){

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return result;
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        logger.info("host:{},cookies:{}",httpUrl.host(),list);
        cookieStore.put(httpUrl.host(),list);

    }
}
