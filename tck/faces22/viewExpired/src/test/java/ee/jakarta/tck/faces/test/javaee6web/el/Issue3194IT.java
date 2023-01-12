/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee6web.el;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import jakarta.faces.application.ViewExpiredException;
import jakarta.faces.view.ViewScoped;

@RunWith(Arquillian.class)
@Ignore("Sloppy test using Mojarra specific assumptions. See https://github.com/jakartaee/faces/issues/1773")
public class Issue3194IT {

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
     * @see ViewScoped
     * @see ViewExpiredException
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3198
     */
    @Test
    public void testViewExpired() throws Exception {
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        HtmlPage page = webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        assertTrue(page.asXml().contains("1"));
        page = webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        assertTrue(page.asXml().contains("2"));
        page = webClient.getPage(webUrl + "faces/viewExpired.xhtml");
        assertTrue(page.asXml().contains("3"));
    }
}
