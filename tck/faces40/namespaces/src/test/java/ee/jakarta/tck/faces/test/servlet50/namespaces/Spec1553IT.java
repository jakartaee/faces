/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.namespaces;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.Application;
import jakarta.faces.view.facelets.Facelet;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1553IT extends BaseITNG {

    /**
     * @see Facelet
     * @see Application#createComponent(String)
     * @see https://github.com/jakartaee/faces/issues/1553
     */
    @Test
    void test() throws Exception {
        WebPage page = getPage("spec1553IT.xhtml");

        assertEquals("Spec1553IT", page.getTitle(), "jakarta.faces.html h:head works");

        assertEquals("value", getValue(page.findElement(By.id("ui_sun"))), "http://java.sun.com/jsf/facelets works");
        assertEquals("value", getValue(page.findElement(By.id("f_sun"))), "http://java.sun.com/jsf/core works");
        assertEquals("value", getValue(page.findElement(By.id("h_sun"))), "http://java.sun.com/jsf/html works");
        assertEquals("value", getValue(page.findElement(By.id("cc_sun"))), "http://java.sun.com/jsf/composite works");
        assertEquals("value", getValue(page.findElement(By.id("c_sun"))), "http://java.sun.com/jsp/jstl/core works");
        assertEquals("value", getValue(page.findElement(By.id("fn_sun"))), "http://java.sun.com/jsp/jstl/functions works");

        assertEquals("id_jcp", page.findElement(By.id("jsf_jcp")).findElements(By.xpath(".//*")).iterator().next().getDomAttribute("id"),
                "http://xmlns.jcp.org/jsf works");
        assertEquals("value", getValue(page.findElement(By.id("ui_jcp"))), "http://xmlns.jcp.org/jsf/facelets works");
        assertEquals("value", getValue(page.findElement(By.id("f_jcp"))), "http://xmlns.jcp.org/jsf/core works");
        assertEquals("value", getValue(page.findElement(By.id("h_jcp"))), "http://xmlns.jcp.org/jsf/html works");
        assertEquals("email", page.findElement(By.id("p_jcp")).findElements(By.xpath(".//*")).iterator().next().getDomAttribute("type"),
                "http://xmlns.jcp.org/jsf/passthrough works");
        assertEquals("value", getValue(page.findElement(By.id("cc_jcp"))), "http://xmlns.jcp.org/jsf/composite works");
        assertEquals("value", getValue(page.findElement(By.id("comp_jcp"))), "http://xmlns.jcp.org/jsf/component works");
        assertEquals("value", getValue(page.findElement(By.id("c_jcp"))), "http://xmlns.jcp.org/jsp/jstl/core works");
        assertEquals("value", getValue(page.findElement(By.id("fn_jcp"))), "http://xmlns.jcp.org/jsp/jstl/functions works");

        assertEquals("id_jakarta", page.findElement(By.id("faces_jakarta")).findElements(By.xpath(".//*")).iterator().next().getDomAttribute("id"),
                "jakarta.faces works");
        assertEquals("value", getValue(page.findElement(By.id("ui_jakarta"))), "jakarta.faces.facelets works");
        assertEquals("value", getValue(page.findElement(By.id("f_jakarta"))), "jakarta.faces.core works");
        assertEquals("value", getValue(page.findElement(By.id("h_jakarta"))), "jakarta.faces.html works");
        assertEquals("email", page.findElement(By.id("p_jakarta")).findElements(By.xpath(".//*")).iterator().next().getDomAttribute("type"),
                "jakarta.faces.passthrough works");
        assertEquals("value", getValue(page.findElement(By.id("cc_jakarta"))), "jakarta.faces.composite works");
        assertEquals("value", getValue(page.findElement(By.id("comp_jakarta"))), "jakarta.faces.component works");
        assertEquals("value", getValue(page.findElement(By.id("c_jakarta"))), "jakarta.tags.core works");
        assertEquals("value", getValue(page.findElement(By.id("fn_jakarta"))), "jakarta.tags.functions works");

    }

    private static String getValue(WebElement element) {
        assertEquals(0, element.findElements(By.xpath(".//*")).size(), "This element has no children");
        return element.getText();
    }
}
