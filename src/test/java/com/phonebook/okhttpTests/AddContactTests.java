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


    // по API ошибка должна быть 401, но актуальный результат 403! БАГ
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

        String responseBody = response.body().string();
        System.out.println("STATUS: " + response.code());
        System.out.println("BODY: " + responseBody);

    }


//   // по API ошибка должна быть 409, тк это дубликат контакта, но актуальный результат 200! БАГ
    @Test
    public void addAllContactErrorExistingContactTest() throws IOException {

        ContactDto contactDto = ContactDto.builder()
                .name("Oliver")
                .lastName("Kan")
                .email("oliver@gmail.com")
                .phone("12345678901")
                .address("DülmenStraße 31")
                .description("goalkeeper")
                .build();

        RequestBody body = RequestBody.create(gson.toJson(contactDto), JSON);

        Request request1 = new Request.Builder()
                .url(baseUrl + contactPath)
                .post(body)
                .addHeader(AUTH, TOKEN)
                .build();

        Response response1 = client.newCall(request1).execute();
        response1.close();


        Request request2 = new Request.Builder()
                .url(baseUrl + contactPath)
                .post(RequestBody.create(gson.toJson(contactDto), JSON))
                .addHeader(AUTH, TOKEN)
                .build();

        Response response2 = client.newCall(request2).execute();

        Assert.assertEquals(response2.code(), 409);

        String responseBody = response2.body().string();
        System.out.println(responseBody);

        ErrorDto errorDto = gson.fromJson(responseBody, ErrorDto.class);

        Assert.assertEquals(errorDto.getMessage(), "Contact already exists");
    }
}
