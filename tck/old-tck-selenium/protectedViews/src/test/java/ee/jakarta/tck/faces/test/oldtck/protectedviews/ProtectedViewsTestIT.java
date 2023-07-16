/*
 * Copyright (c) 2023 Contributors to the Eclipse Foundation.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

package ee.jakarta.tck.faces.test.oldtck.protectedviews;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class ProtectedViewsTestIT extends ITBase {

    @ArquillianResource
    private URL webUrl;
    private WebClient webClient;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        return create(ZipImporter.class, getProperty("finalName") + ".war")
                .importFrom(new File("target/" + getProperty("finalName") + ".war"))
                .as(WebArchive.class);
    }

    @Before
    public void setUp() {
        webClient = new WebClient();
    }

    @After
    public void tearDown() {
        webClient.close();
    }



  /**
   * @testName: viewProtectedViewNonAccessPointTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Validate that a that we can not gain access to a Protected
   *                 View from out side that views web-app.
   * 
   * @since 2.2
   */
  @Test
  public void viewProtectedViewNonAccessPointTest() throws Exception {
    webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);

    HtmlPage page = webClient.getPage(webUrl + "faces/views/protected.xhtml");

    HtmlAnchor anchor = (HtmlAnchor) page.getElementById("messOne");

    assertNull("Illegal Access of a Protected View!", anchor);

    assertTrue("Expected a ProtectedViewException when accessing a protected view", page.asNormalizedText().contains("jakarta.faces.application.ProtectedViewException"));

  } 

  /**
   * @testName: viewProtectedViewSameWebAppAccessTest
   * 
   * @assertion_ids: PENDING
   * 
   * @test_Strategy: Validate that we are able to gain access to a protected
   *                 view from inside the same web-app through a non-protected
   *                 view.
   * 
   * @since 2.2
   */
  @Test
  public void viewProtectedViewSameWebAppAccessTest() throws Exception {

    String expected = "This is a Protected View!";

    HtmlPage page = webClient.getPage(webUrl + "faces/views/public.xhtml");

    HtmlAnchor anchor = (HtmlAnchor) page.getElementById("form1:linkOne");
    assertNotNull("Anchor linkOne should not be null!", anchor);

    HtmlPage protectedPage = anchor.click();
    assertEquals(expected, protectedPage.getElementById("messOne").asNormalizedText());

  } 

}
