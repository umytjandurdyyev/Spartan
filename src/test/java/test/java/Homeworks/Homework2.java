package test.java.Homeworks;


import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import test.java.utilities.ConfigurationReader;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Homework2 {
    //Homework-2
    //Create one mackaroo api for name,gender,phone
    //send get request to retrieve random info from that api
    //use those info to send post request to spartan

    //URL :  https://my.api.mockaroo.com/spartanapi.json?key=e1d509a0



    @Test
    public void  test1()  {


        Response response = given().log().all()
                .and()
                .accept(ContentType.JSON)
                .and()
                .queryParam("key","e1d509a0")
                .when().get(ConfigurationReader.get("spartan_mockaroo_api")+"/spartanapi.json");
                //.when().get("/spartanapi.json?key=e1d509a0");



                List<Map<String,Object>> responseList = response.body().as(List.class);




                for (Map<String, Object> responseMap : responseList) {

                    given().accept(ContentType.JSON)
                            .and()
                            .contentType(ContentType.JSON)
                            .and()
                            .body(responseMap)
                            .when().post(ConfigurationReader.get("spartan_api_url")+"/api/spartans")
                            .then()
                            .statusCode(201)
                            .and()
                            .contentType("application/json")
                            .and()
                            .body("data.name",equalTo(responseMap.get("name")),
                                    "data.gender",equalTo(responseMap.get("gender"))
                            //     ,"data.phone",equalTo(responseMap.get("phone"))
                                    );

 //    Long.parseLong(String.valueOf(responseMap.get("phone")));


                }






    }








}
