package RestAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import java.util.Random;

public class BooksAPI {
    //Random ram=new Random();
    static String token;

    @Test(priority=1)
    public void Token() {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("clientName","restAssured");
        jsonObject.put("clientEmail","restAssurred1@gmail.com");
        RequestSpecification res = RestAssured.given();
        res.baseUri("https://simple-books-api.glitch.me")
                .basePath("api-clients").contentType(ContentType.JSON)
                .headers("Content-Type", "application/json")
                .body(jsonObject.toJSONString());

        Response response=res.post();
        token=response.jsonPath().getString("accessToken");
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        System.out.println(token);

    }

    @Test(priority=2)
    public void getBookDetails(){
        System.out.println(token);
        RequestSpecification res= RestAssured.given();
        res.baseUri("https://simple-books-api.glitch.me")
                .basePath("/orders/").contentType(ContentType.JSON)
                .headers("Content-Type","application/json").headers("Authorization","Bearer "+token);
        Response response=res.get();
        //OK star the execution
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
    }
}
