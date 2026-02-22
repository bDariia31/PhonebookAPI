package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.AuthRequestDto;
import com.phonebook.dto.ErrorDto;
import com.phonebook.dto.TokenDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class LoginTests extends TestBase
{
    @Test
    public void loginSuccessTest() throws IOException {
        //body
        AuthRequestDto authRequestDto=AuthRequestDto.builder()
                .username(email)
                .password(password)
                .build();
        //преобразовываем java ->json
        RequestBody requestBody=RequestBody.create(gson.toJson(authRequestDto),JSON);
        Request request=new Request.Builder()
                .url(baseUrl+loginPath)
                .post(requestBody)
                .build();

        // отправляем запрос
        Response response=client.newCall(request).execute();
        // делаем assert
        Assert.assertTrue(response.isSuccessful());

        //gson превращаем в string
        TokenDto tokenDto = gson.fromJson(response.body().string(), TokenDto.class);
        System.out.println(tokenDto.getToken());


    }

    @Test
    public void loginErrorTestWrongPassword() throws IOException {
        AuthRequestDto authRequestDto = AuthRequestDto.builder()
                .username(email)
                .password("test")
                .build();

        RequestBody body=RequestBody.create(gson.toJson(authRequestDto),JSON);
        Request request=new Request.Builder()
                .url(baseUrl+loginPath)
                .post(body)
                .build();

        // assert status code
        Response response=client.newCall(request).execute();
        softAssert.assertEquals(response.code(),401);

        // assert status code message
        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
        softAssert.assertEquals(errorDto.getError(),"Unauthorized");

        // assert error message
        softAssert.assertEquals(errorDto.getMessage(),"Login or Password incorrect");
        softAssert.assertAll();


    }
}
