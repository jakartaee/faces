/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.composite;

import static java.lang.System.getProperty;
import static org.jboss.shrinkwrap.api.ShrinkWrap.create;
import static org.junit.Assert.assertEquals;

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
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

/**
 * Tests if composite component that use resourceBundleMap .properties
 * reflects locale changes.
 * 
 * @see https://github.com/eclipse-ee4j/mojarra/issues/5160
 * @see https://issues.apache.org/jira/browse/MYFACES-4491
 * 
 */
@RunWith(Arquillian.class)
public class LocalizedCompositeIT {

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
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.setJavaScriptTimeout(120000);
    }

    @Test
    public void testLocalizedCompositeEn() throws Exception {
        webClient.addRequestHeader("Accept-Language", "en-US, en;q=0.9, es-ES");
        HtmlPage page = webClient.getPage(webUrl + "localized-composite.xhtml");
        
        HtmlHeading1 h1 = (HtmlHeading1) page.getHtmlElementById("header");

        assertEquals("Application", h1.getTextContent());
        
        HtmlSubmitInput btn1 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn");
        assertEquals("My precious button", btn1.getAttribute("value"));
        HtmlSubmitInput btn2 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn1:btn");
        assertEquals("Button", btn2.getAttribute("value"));
    }

    @Test
    public void testLocalizedCompositeEs() throws Exception {
        webClient.addRequestHeader("Accept-Language", "es-ES");
        HtmlPage page = webClient.getPage(webUrl + "localized-composite.xhtml");
        
        HtmlHeading1 h1 = (HtmlHeading1) page.getHtmlElementById("header");

        assertEquals("Aplicación", h1.getTextContent());
        
        HtmlSubmitInput btn1 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn");
        assertEquals("Mi precioso botón", btn1.getAttribute("value"));
        HtmlSubmitInput btn2 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn1:btn");
        assertEquals("Botón", btn2.getAttribute("value"));
    }

    @Test
    public void testLocalizedCompositePtBrPb() throws Exception {
        webClient.addRequestHeader("Accept-Language", "pt-BR-PB");
        HtmlPage page = webClient.getPage(webUrl + "localized-composite.xhtml");
        
        HtmlHeading1 h1 = (HtmlHeading1) page.getHtmlElementById("header");

        assertEquals("Application", h1.getTextContent());
        
        HtmlSubmitInput btn1 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn");
        assertEquals("My precious button", btn1.getAttribute("value"));
        HtmlSubmitInput btn2 = (HtmlSubmitInput) page.getHtmlElementById("frm:btn1:btn");
        assertEquals("Pitoco", btn2.getAttribute("value"));
    }

    @After
    public void tearDown() {
        webClient.close();
    }

}
