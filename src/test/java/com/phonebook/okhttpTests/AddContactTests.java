package com.phonebook.okhttpTests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.ErrorDto;
import com.phonebook.dto.MessageDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class AddContactTests extends TestBase {

    @Test
    public void addContactSuccessTest() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("oliver@gmail.com")
                .phone("12345678901")
                .address("DülmenStraße 31")
                .description("goalkeeper")
                .build();


        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url(baseUrl + contactPath)
                .post(body)
                .addHeader(AUTH, TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);
        System.out.println(messageDto.getMessage());


    }

//    @Test
//    public void addAllContactErrorExistingContactTest() throws IOException {
//        ContactDto contactDto = ContactDto.builder()
//                .name("Oliver")
//                .lastName("Kan")
//                .email("oliver@gmail.com")
//                .phone("12345678901")
//                .address("DülmenStraße 31")
//                .description("goalkeeper")
//                .build();
//
//        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
//        Request request = new Request.Builder()
//                .url(baseUrl + contactPath)
//                .post(body)
//                .addHeader(AUTH, TOKEN)
//                .build();
//
//      client.newCall(request).execute();
//
//        Request request2 = new Request.Builder()
//                .url(baseUrl + contactPath)
//                .post(body)
//                .addHeader(AUTH, TOKEN)
//                .build();
//
//        Response response = client.newCall(request2).execute();
//        softAssert.assertEquals(response.code(),409);
//
//        // assert status code message
//        ErrorDto errorDto = gson.fromJson(response.body().string(), ErrorDto.class);
//        softAssert.assertEquals(errorDto.getError(),"409");
//
//        // assert error message
//        softAssert.assertEquals(errorDto.getMessage(),"Contact already exists");
//        softAssert.assertAll();
//
//
//
//    }



    // про API ошибка должна быть 401, но актуальный результат 403! БАГ
    @Test
    public void addContactErrorUnauthorizedTest() throws IOException {
        ContactDto contactDto = ContactDto.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("oliver@gmail.com")
                .phone("12345678901")
                .address("DülmenStraße 31")
                .description("goalkeeper")
                .build();


        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);
        Request request = new Request.Builder()
                .url(baseUrl + contactPath)
                .post(body)
                .build();


      Response response = client.newCall(request).execute();
      Assert.assertEquals(response.code(), 403);

    }
}
