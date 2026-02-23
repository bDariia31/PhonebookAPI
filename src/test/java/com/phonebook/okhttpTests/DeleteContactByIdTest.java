package com.phonebook.okhttpTests;

import com.phonebook.core.TestBase;
import com.phonebook.dto.ContactDto;
import com.phonebook.dto.MessageDto;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;

public class DeleteContactByIdTest extends TestBase {

    String id;

    @BeforeMethod
    public void precondition() throws IOException {

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

        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);

        // contact was added! ID: tdftj;gdzeuehqqwa" --> " tdftj;gdzeuehqqwa "
        String[] split = messageDto.getMessage().split(": ");
        id=split[1];

    }

    @Test
    public void deleteContactByIdSuccessTest() throws IOException {
        Request request=new Request.Builder()
                .url(baseUrl+contactPath+"/"+id)
                .delete()
                .addHeader(AUTH,TOKEN)
                .build();

        Response response = client.newCall(request).execute();
        Assert.assertTrue(response.isSuccessful());
        Assert.assertEquals(response.code(),200);

        //получаем какое сообщение при удалении выводит
        MessageDto messageDto = gson.fromJson(response.body().string(), MessageDto.class);
        System.out.println(messageDto.getMessage());//(Contact was deleted!)
        // делаем assert для сравнение сообщения которое должно вывести
        Assert.assertEquals(messageDto.getMessage(),"Contact was deleted!");
    }

}
