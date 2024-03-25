package com.jerry;

import com.jerry.typora.plugin.SaveCookie;
import okhttp3.*;

import java.io.IOException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String filePath = "/E:/codeSpace/learn/uppic/target/uppic-1.0-SNAPSHOT.jar";
        System.out.println(filePath.substring(0,filePath.lastIndexOf("/uppic/target/")+"/uppic/target/".length()));
        System.out.println(STR."\{filePath.length()} \{filePath.lastIndexOf("\\\\")}");
        String fileName = filePath.substring(filePath.lastIndexOf("\\\\")+2,filePath.length()-1);
        System.out.println(fileName);

    }
}