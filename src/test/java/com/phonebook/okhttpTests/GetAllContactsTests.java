package com.phonebook.okhttpTests;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.phonebook.core.TestBase;
import com.phonebook.dto.AllContactDto;
import com.phonebook.dto.ContactDto;
import okhttp3.Request;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;

public class GetAllContactsTests extends TestBase {


    @Test
    public void getAllContactsSuccessTest() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + contactPath)
                .get()
                .addHeader(AUTH, TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());

        AllContactDto contactsDto = gson.fromJson(response.body().string(), AllContactDto.class);
        for (ContactDto contactDto : contactsDto.getContacts()) {
            System.out.println(contactDto.getId());
            System.out.println(contactDto.getName());
            System.out.println("------------------------------");
        }

    }

    @Test
    public void getContactErrorUnauthorizedTest() throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + contactPath)
                .get()
                .build(); // без AUTH и TOKEN

        Response response = client.newCall(request).execute();
        Assert.assertEquals(response.code(), 403);


    }


}
