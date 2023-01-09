/**
 * Copyright Werner Punz
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Original code stemming 100% from me, hence relicense from EPL

package ee.jakarta.tck.faces.test.util.selenium;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.File;
import java.net.URL;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;

/**
 * Base class for tests,
 * which provides a certain infrastructure
 * can be used, but does not have to be
 */
@RunWith(SeleniumArquilianRunner.class)
public class BaseITNG {

    @ArquillianResource
    protected URL webUrl;

    private ExtendedWebDriver webDriver;

    static DriverPool driverPool = new DriverPool();


    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    @Before
    public void setUp() {
        webDriver = driverPool.getOrNewInstance();
    }


    @After
    public void tearDown() {
        driverPool.returnInstance(webDriver);
    }

    @AfterClass
    public static void afterAll() {
        driverPool.quitAll();
    }


    protected WebPage getPage(String page) {
        webDriver.get(webUrl.toString() + page);
        WebPage webPage = new WebPage(webDriver);
        webPage.waitForPageToLoad();
        return webPage;
    }

    /**
     * selenium does not automatically update
     * the page handles if a link is clicked
     * without ajax
     */
    protected void updatePage()
    {
        ExtendedWebDriver driver = getWebDriver();
        for(String winHandle : driver.getWindowHandles()){
            driver.switchTo().window(winHandle);
        }
    }
    protected int getStatusCode(String page) {
        webDriver.get(webUrl.toString() + page);
        WebPage webPage = new WebPage(webDriver);
        webPage.waitForPageToLoad();
        return webPage.getResponseStatus();
    }

    public ExtendedWebDriver getWebDriver() {
        return webDriver;
    }
}
