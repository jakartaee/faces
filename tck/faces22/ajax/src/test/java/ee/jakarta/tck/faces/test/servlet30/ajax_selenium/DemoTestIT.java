package ee.jakarta.tck.faces.test.servlet30.ajax_selenium;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;


class DemoTestIT extends BaseITNG {

  @Test
  void test1()  {
      assertTrue(true);
    }

  @Test
  void runMainPage()  {
        int statusCode = getStatusCode("index.xhtml");
        assertEquals(200, statusCode);
    }

}
