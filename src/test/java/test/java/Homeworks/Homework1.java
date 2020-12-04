package Homeworks;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.ConfigurationReader;
import utilities.ExcelUtil;

import java.math.BigDecimal;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class Homework1 {

    //Optional homeworks
    //Homework-1
    //1-Create csv file from mackaroo website, which includes name,gender,phone
    //2-Download csv file
    //3-Using testng data provider and apache poi create data driven posting from spartan

    @BeforeClass
    public void beforeClass(){
        baseURI= ConfigurationReader.get("spartan_api_url");
    }

    @DataProvider
    public Object[][] spartanTest(){

        ExcelUtil spartan = new ExcelUtil("src/test/resources/MOCK_DATA.xlsx","spartan");

        String [][] dataArray = spartan.getDataArrayWithoutFirstRow();

        return dataArray;
    }

    @Test(dataProvider = "spartanTest")
    public void test1(String name, String gender, String phone){

        BigDecimal d = new BigDecimal(phone);
        long phoneLong = Long.parseLong(d.toPlainString());

        SpartanPost spartanPost = new SpartanPost(name, gender, phoneLong);

        Response response = given().log().all()
                    .accept(ContentType.JSON)
                    .and()
                    .contentType(ContentType.JSON)
                    .and()
                    .body(spartanPost)
                    .when()
                    .post("/api/spartans");

        SpartanResponse spartanResponse = response.body().as(SpartanResponse.class);
        String actualName = spartanResponse.getData().getName();
        String expectedName = spartanPost.getName();
        assertEquals(actualName, expectedName);



    }


}
