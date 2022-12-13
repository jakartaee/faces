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
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.lang.System.setProperty;
import static java.util.Arrays.stream;

@FunctionalInterface
interface FileApplication {
    void method(File file, String name);
}

/**
 * Base class for tests,
 * which provides a certain infrastructure
 * can be used, but does not have to be
 */
@RunWith(Arquillian.class)
public class BaseITNG {

    @ArquillianResource
    protected URL webUrl;

    private ExtendedWebDriver webDriver;

    static DriverPool driverPool = new DriverPool();


    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return getWebArchive();
    }


    static void applyFiles(WebArchive war, String dir, FileApplication application) {
        File[] webFiles = new File(dir).listFiles();

        stream(webFiles)
                .filter(file -> !file.getName().equals("WEB-INF"))
                .forEach(file -> {
                    final String rootPath = file.getParentFile().getAbsolutePath();
                    try {
                        Files.walkFileTree(Path.of(file.getAbsolutePath()), new SimpleFileVisitor<>() {
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                                File workFile = file.toFile();
                                if (!workFile.isDirectory()) {
                                    String resourceName = workFile.getAbsolutePath().substring(rootPath.length() + 1);
                                    application.method(workFile, resourceName);
                                }
                                return super.visitFile(file, attrs);
                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    public static WebArchive getWebArchive() {
        PomEquippedResolveStage mavenResolver = Maven.resolver().loadPomFromFile("pom.xml");
        //File[] libs = mavenResolver.importRuntimeAndTestDependencies().resolve().withTransitivity().asFile();

        setProperty("finalName", "extractedTck");

        final WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addPackage("ee.jakarta.tck.faces.test.servlet30.ajax");

        war.addAsManifestResource(new File("src/test/resources/context.xml"), "context.xml");
        war.addAsManifestResource(new File("src/test/resources/arquillian.xml"), "arquillian.xml");

        applyFiles(war, "src/main/webapp/", (File workFile, String resourceName) -> war.addAsWebResource(workFile, resourceName));
        applyFiles(war, "src/main/webapp/WEB-INF/", (File workFile, String resourceName) -> war.addAsWebInfResource(workFile, resourceName));

        return war;
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
