package ee.jakarta.tck.faces.test.servlet50.ajax_selenium;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import org.eu.ingwar.tools.arquillian.extension.suite.annotations.ArquillianSuiteDeployment;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.spec.WebArchive;

/**
 * Given Arquilian has no single deployment testsuite
 * mechanism we have to rely on a third party library.
 * This improves the performance in a major area, namely
 * we are only deploying once and then run all tests
 * on the same deployment (cuts down by 95% the test time)
 */
@ArquillianSuiteDeployment
public class Deployments {
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return BaseITNG.createDeployment();
    }
}

