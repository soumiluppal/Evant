package com.cs307.evant.evant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.*;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(value = Parameterized.class)
public class SignupTest {
    private String email;
    private String nm;
    private String pw;
    private String pw2;
    private String fmessage;
    private String fvar;
    private Boolean f;

    public SignupTest(String username, String password, String repass, String name){
        this.email = username;
        this.pw = password;
        this.pw2 = repass;
        this.nm = name;
    }

    @Parameters
    public static Collection data(){
        Object[][] data = new Object[][] {{"name@example.com", "12345", "12345", "name"}, {"name", "", "", ""}, {"name@", "", "", ""}, {"@gmail.com", "", "", ""}, {"", "", "", ""}, {"name@example.com", "12345", "123456", "name"}, };
        return Arrays.asList(data);
    }

    @Test
    public void inputTest(){
        f = false;
        fvar = "Error";

        if(!pw.equals(pw2))
        {
            fmessage = "Passwords Do not Match";
            fvar = pw2;
            f = true;

        }

        if(pw2.length() <= 0)
        {
            fmessage = "Please confirm your password";
            f = true;
        }



        if(pw.length() <= 0)
        {

            fmessage = "Please Fill In a Password";
            f = true;
        }

        if(nm.length() <= 0)
        {
            fmessage = "Please enter your name";
            f = true;
        }

        if(email.length() <= 0)
        {
            fmessage = "Please Fill In a User Name";
            f = true;
        }else {

            if (!email.matches(".+@.+\\..+")) {
                fmessage = "Please enter a valid email address";
                fvar = email;
                f = true;
            }
        }

        if(f){
            fail(fvar + ": " + fmessage);
        }

    }
}