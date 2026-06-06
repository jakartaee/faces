/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.passthrough;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The {@code jsf:} namespace on HTML5 elements (fieldset, meter, label, datalist, output, keygen)
 * renders the corresponding real HTML element with its id and pass-through attributes preserved.
 */
class Issue2609IT extends BaseITNG {

    /**
     * The {@code jsf:} namespace on HTML5 {@code fieldset} elements renders real fieldset elements
     * keeping their id and pass-through attributes (disabled, form, name).
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void fieldset() throws Exception {
        WebPage page = getPage("issue2609-fieldset.xhtml");

        String fieldset1 = page.findElement(By.id("fieldset1")).getAttribute("outerHTML");
        assertTrue(fieldset1.contains("<fieldset"), "fieldset1 is rendered as a fieldset element");
        assertTrue(fieldset1.contains("id=\"fieldset1\""), "fieldset1 keeps its id");

        String fieldset2 = page.findElement(By.id("fieldset2")).getAttribute("outerHTML");
        assertTrue(fieldset2.contains("<fieldset"), "fieldset2 is rendered as a fieldset element");
        assertTrue(fieldset2.contains("id=\"fieldset2\""), "fieldset2 keeps its id");
        assertTrue(fieldset2.contains("disabled=\"disabled\""), "fieldset2 keeps its disabled attribute");

        String fieldset3 = page.findElement(By.id("fieldset3")).getAttribute("outerHTML");
        assertTrue(fieldset3.contains("<fieldset"), "fieldset3 is rendered as a fieldset element");
        assertTrue(fieldset3.contains("id=\"fieldset3\""), "fieldset3 keeps its id");
        assertTrue(fieldset3.contains("form=\"form\""), "fieldset3 keeps its form attribute");
        assertTrue(fieldset3.contains("name=\"myfieldset\""), "fieldset3 keeps its name attribute");
    }

    /**
     * The {@code jsf:} namespace on HTML5 {@code meter} elements renders real meter elements
     * keeping their id and pass-through attributes (min, max, value), with EL-bound min/max resolved.
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void meter() throws Exception {
        WebPage page = getPage("issue2609-meter.xhtml");

        String meter1 = page.findElement(By.id("meter1")).getAttribute("outerHTML");
        assertTrue(meter1.contains("<meter"), "meter1 is rendered as a meter element");
        assertTrue(meter1.contains("id=\"meter1\""), "meter1 keeps its id");
        assertTrue(meter1.contains("min=\"200\""), "meter1 keeps its min attribute");
        assertTrue(meter1.contains("max=\"500\""), "meter1 keeps its max attribute");
        assertTrue(meter1.contains("value=\"350\""), "meter1 keeps its value attribute");

        String meter2 = page.findElement(By.id("meter2")).getAttribute("outerHTML");
        assertTrue(meter2.contains("<meter"), "meter2 is rendered as a meter element");
        assertTrue(meter2.contains("id=\"meter2\""), "meter2 keeps its id");
        assertTrue(meter2.contains("min=\"100\""), "meter2 resolves its EL-bound min attribute");
        assertTrue(meter2.contains("max=\"500\""), "meter2 resolves its EL-bound max attribute");
        assertTrue(meter2.contains("value=\"350\""), "meter2 keeps its value attribute");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code label} element renders a real label element
     * keeping its id and pass-through attributes (form, for).
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void label() throws Exception {
        WebPage page = getPage("issue2609-label.xhtml");

        String label1 = page.findElement(By.id("label1")).getAttribute("outerHTML");
        assertTrue(label1.contains("<label"), "label1 is rendered as a label element");
        assertTrue(label1.contains("id=\"label1\""), "label1 keeps its id");
        assertTrue(label1.contains("form=\"form\""), "label1 keeps its form attribute");
        assertTrue(label1.contains("for=\"input1\""), "label1 keeps its for attribute");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code datalist} element renders a real datalist element
     * keeping its id, and its {@code jsf:}-marked options render as real option elements with id and value.
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void datalist() throws Exception {
        WebPage page = getPage("issue2609-datalist.xhtml");

        String colors = page.findElement(By.id("colors")).getAttribute("outerHTML");
        assertTrue(colors.contains("<datalist"), "colors is rendered as a datalist element");
        assertTrue(colors.contains("id=\"colors\""), "colors keeps its id");

        String source = page.getSource();
        assertTrue(source.contains("<option id=\"r\" value=\"red\""), "red option is rendered with id and value");
        assertTrue(source.contains("<option id=\"b\" value=\"blue\""), "blue option is rendered with id and value");
        assertTrue(source.contains("<option id=\"g\" value=\"green\""), "green option is rendered with id and value");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code output} element renders a real output element
     * keeping its id.
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void output() throws Exception {
        WebPage page = getPage("issue2609-output.xhtml");

        String output1 = page.findElement(By.id("output1")).getAttribute("outerHTML");
        assertTrue(output1.contains("<output"), "output1 is rendered as an output element");
        assertTrue(output1.contains("id=\"output1\""), "output1 keeps its id");
    }

    /**
     * The {@code jsf:} namespace on an HTML5 {@code keygen} element renders a real keygen element
     * keeping its id.
     *
     * @see jakarta.faces.component.html.HtmlOutputText
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2609
     */
    @Test
    void keygen() throws Exception {
        WebPage page = getPage("issue2609-keygen.xhtml");

        String keygen1 = page.findElement(By.id("keygen1")).getAttribute("outerHTML");
        assertTrue(keygen1.contains("<keygen"), "keygen1 is rendered as a keygen element");
        assertTrue(keygen1.contains("id=\"keygen1\""), "keygen1 keeps its id");
    }
}
