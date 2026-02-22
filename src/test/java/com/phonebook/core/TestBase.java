package com.phonebook.core;

import com.google.gson.Gson;
import com.phonebook.utils.PropertiesLoader;
import lombok.Setter;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import org.testng.asserts.SoftAssert;

public class TestBase
{
    protected Gson gson=new Gson();
    protected OkHttpClient client=new OkHttpClient();

    public static final MediaType JSON=MediaType.get("application/json;charset=utf-8");

    protected SoftAssert softAssert=new SoftAssert();

    public static final String baseUrl= PropertiesLoader.loadProperty("url");
    public static final String loginPath=PropertiesLoader.loadProperty("log.in");
    public static final String contactPath=PropertiesLoader.loadProperty("contact.controller");

    public static final  String email=PropertiesLoader.loadProperty("valid.email");
    public static final String password=PropertiesLoader.loadProperty("valid.password");

    public static final String TOKEN=PropertiesLoader.loadProperty("token");
    public static final String AUTH=PropertiesLoader.loadProperty("auth");

}
