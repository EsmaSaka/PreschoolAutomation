package com.cybertektraining.units;


import com.cybertektraining.api.StudentAPI;
import com.cybertektraining.pojos.Address;
import com.cybertektraining.pojos.Company;
import com.cybertektraining.pojos.Contact;
import com.cybertektraining.pojos.Student;
import com.cybertektraining.utilities.FileUtilities;
import com.google.gson.Gson;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;

import static io.restassured.RestAssured.*;

public class StudentUnitTests {


    @Test
    public void test1() {
        try {
            String path = System.getProperty("user.dir") + "/student.json";
            System.out.println(path);
            Gson gson = new Gson();
            Student student = new Student();

            FileReader fileReader = new FileReader(path);

            Student student_from_json = gson.fromJson(fileReader, Student.class);

            System.out.println(student_from_json);

            Assert.assertEquals("01/01/1980", student_from_json.getBirthDate());
            Assert.assertEquals("Lex", student_from_json.getCompany().getAddress().getStreet());
            Assert.assertEquals("abc@gmail.com", student_from_json.getContact().getEmailAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        Address address = new Address("McLean", "Virginia", "7925 Jones Branch Dr #3300", 7925);
        Company company = new Company(address, "02/02/2020", "Cybertek", "SDET");
        Contact contact = new Contact("sdet@email.com", "454-323-2341", "7925 Jones Branch Dr #3300");
        Student student = new Student("123456", 11, "01/06/1990", company, contact,
                "LionKing", "Male", "11/26/2017",
                "Dzagoev", "MBA", "AP-45", "1111", "Math");

        Response response = StudentAPI.addStudent(student);

        Assert.assertEquals(200, response.getStatusCode());

        response.prettyPrint();
    }

    @Test
    public void test3() {
        //path to the student JSON file
        String path = System.getProperty("user.dir") + "/student.json";
        //deserialize JSON object into student POJO
        Student student = FileUtilities.getUserFromJSON(path);
        //POST new student
        Response response = StudentAPI.addStudent(student);
        //assert status code
        Assert.assertEquals(200, response.getStatusCode());
    }

    @Test
    public void test4(){
       //String path=System.getProperty("user.dir")+"/student.json";

        String jsonObj="{\n" +
                "  \"admissionNo\": \"1234\",\n" +
                "  \"batch\": 12,\n" +
                "  \"birthDate\": \"01/01/1890\",\n" +
                "  \"company\": {\n" +
                "    \"address\": {\n" +
                "      \"city\": \"McLean\",\n" +
                "      \"state\": \"Virginia\",\n" +
                "      \"street\": \"7925 Jones Branch Dr\",\n" +
                "      \"zipCode\": 22102\n" +
                "    },\n" +
                "    \"companyName\": \"Cybertek\",\n" +
                "    \"startDate\": \"02/02/2020\",\n" +
                "    \"title\": \"SDET\"\n" +
                "  },\n" +
                "  \"contact\": {\n" +
                "    \"emailAddress\": \"james_bond@gmail.com\",\n" +
                "    \"phone\": \"240-123-1231\",\n" +
                "    \"premanentAddress\": \"7925 Jones Branch Dr\"\n" +
                "  },\n" +
                "  \"firstName\": \"Eliff\",\n" +
                "  \"gender\": \"Males\",\n" +
                "  \"joinDate\": \"01/01/2021\",\n" +
                "  \"lastName\": \"Twister\",\n" +
                "  \"major\": \"CS\",\n" +
                "  \"password\": \"1234\",\n" +
                "  \"section\": \"101010\",\n" +
                "  \"subject\": \"Math\"\n" +
                "}";

        Response response=StudentAPI.addStudent(jsonObj);
        System.out.println(response.prettyPrint());
       // Assert.assertEquals(200,response.getStatusCode());



    }



}