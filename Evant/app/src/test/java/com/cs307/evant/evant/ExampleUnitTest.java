package com.cs307.evant.evant;

import org.junit.Test;
import org.junit.runner.*;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import java.util.regex.Pattern;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.*;



/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(value = Parameterized.class)
public class ExampleUnitTest {
    private String email;
    public ExampleUnitTest(String e){
        this.email = e;
    }

    @Rule
    public ErrorCollector collector = new ErrorCollector();

    @Parameters
    public static String[] data(){
        String[] data = new String[] {"name@example.com", "algkhagh@gmail", ""};
        return data;
    }

    @Test
    public void emailValidator_CorrectEmailSimple_ReturnsTrue(){
        try {
            assertEquals(true, email.matches(".+@.+\\..+"));
        }catch (Throwable e){
            collector.addError(e);
        }
    }
}