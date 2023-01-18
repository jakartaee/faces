package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;


import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class DemoTestIT extends BaseITNG {

    @Test
    public void test1()  {
        assertEquals(true, true);
    }

    @Test
    public void runMainPage()  {
        int statusCode = getStatusCode("index.xhtml");
        System.out.println(statusCode);
        assertEquals(200, statusCode);
    }

}
