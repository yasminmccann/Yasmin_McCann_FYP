package com.example.fyp;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class User {

    public String name, age, email, gender;

    public User() {
    }


    public User(String name, String age, String email, String gender){
        this.name=name;
        this.age=age;
        this.gender = gender;
        this.email=email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    // add this method
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        if(name != null)
            result.put("name", name);
        if(age != null)
            result.put("age", age);
        if(email != null)
            result.put("email", email);
        if(gender != null)
            result.put("gender", gender);

        return result;
    }
}
