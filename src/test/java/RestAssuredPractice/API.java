package RestAssuredPractice;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.json.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

public class API {
    String id;

    @Test(priority = 1)

    public void gettingAllStudents(){


        RequestSpecification res=RestAssured.given();
        res.baseUri("http://localhost:3000/students").contentType(ContentType.JSON);
        Response response=res.get();
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        System.out.println("**********************************AllUsers list *********************");
    }
    @Test(priority=2)
    public void createUser(){
        JSONObject jsonData = new JSONObject();
        jsonData.put("name", "Khaja");

        // Use JSONArray for skill
        JSONArray skills = new JSONArray();
        skills.put("QA");
        skills.put("Manual");
        jsonData.put("skill", skills);

        jsonData.put("department", "Mech");
        System.out.println(jsonData.toString());

        RequestSpecification res=RestAssured.given();
        res.baseUri("http://localhost:3000").basePath("/students")
                .contentType(ContentType.JSON).headers("contentType","application/json")
                .body(jsonData.toJSONString());
        Response response=res.post();
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        System.out.println("**********************************New User *********************");
        int statuscode=response.getStatusCode();
        id = response.jsonPath().getString("id");
        System.out.println("******************************"+id);
        Assert.assertEquals(statuscode,201,"status failed");


    }
    @Test(priority=3)
    public void getCreateUser(){
        System.out.println(id);
        RequestSpecification res=RestAssured.given();
        res.baseUri("http://localhost:3000").basePath("/students/"+id)
                .contentType(ContentType.JSON).headers("contentType","application/json");
        Response response=res.get();
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        System.out.println("**********************************Created User*********************");
        Assert.assertEquals(response.getStatusCode(),200,"getting user status failed");
        String namefield=response.jsonPath().getString("name");
        boolean status=false;
        if(namefield.equalsIgnoreCase("khaja")){
            status=true;
        }
        Assert.assertTrue(status);
        String headers=response.getHeader("Content-Type");
        System.out.println("********************"+headers);
        boolean flag=false;
        if(headers.equals("application/json")){
            flag=true;
        }
        Assert.assertTrue(flag);



    }
    @Test(priority = 4)
    public void updateUser(){
        JSONObject jsonData=new JSONObject();
        jsonData.put("name", "Khaja");

        // Use JSONArray for skill
        JSONArray skills = new JSONArray();
        skills.put("CAD");
        skills.put("SOLID");
        jsonData.put("skill", skills);

        jsonData.put("department", "Mech");
        RequestSpecification res=RestAssured.given();
        res.baseUri("http://localhost:3000").basePath("/students/"+id)
                .contentType(ContentType.JSON).headers("contentType","application/json")
                .body(jsonData.toJSONString());
        Response response=res.put();
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());
        response.prettyPrint();
        System.out.println("**********************************updated User*********************");


    }
    @Test(priority=5)
    public void getDetailsusingQuery(){
        RequestSpecification res = RestAssured.given();
        res.baseUri("http://localhost:3000").basePath("/students/" + id)
                .queryParam("department","Mech")
                .contentType(ContentType.JSON).headers("contentType", "application/json");
        Response response=res.get();
        response.asPrettyString();
        System.out.println(response.getStatusCode());
        System.out.println(response.getHeaders());
        System.out.println(response.getTime());
        System.out.println(response.getStatusLine());

        System.out.println("**********************************QueryParams*********************");


    }
@Test(priority=6)
        public void deletuser() {
    RequestSpecification res = RestAssured.given();
    res.baseUri("http://localhost:3000").basePath("/students/" + id)
            .contentType(ContentType.JSON).headers("contentType", "application/json");

    Response response = res.delete();
    response.asPrettyString();
    System.out.println(response.getStatusCode());
    System.out.println(response.getHeaders());
    System.out.println(response.getTime());
    System.out.println(response.getStatusLine());

    System.out.println("**********************************Delete User*********************");
}

}
