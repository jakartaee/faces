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

package ee.jakarta.tck.faces.faces20.renderkit.converterbinding;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * The {@code binding} attribute of {@code f:converter} uses the bean-provided converter instance,
 * both a standard converter for output formatting and a custom converter for input conversion.
 */
class ConverterBindingIT extends BaseITNG {

    /**
     * A bound {@link jakarta.faces.convert.NumberConverter} formats the output value as currency.
     *
     * @see jakarta.faces.convert.NumberConverter
     * @see jakarta.faces.convert.Converter
     */
    @Test
    void boundStandardConverterFormatsOutput() {
        WebPage page = getPage("converterbinding/converterBinding.xhtml");
        assertEquals("$123.45", page.findElement(By.id("form:currency")).getText(), "bound currency converter output");
    }

    /**
     * A bound custom converter converts the submitted input value.
     *
     * @see jakarta.faces.convert.Converter
     * @see jakarta.faces.component.UIInput#setConverter(jakarta.faces.convert.Converter)
     */
    @Test
    void boundCustomConverterConvertsInput() {
        WebPage page = getPage("converterbinding/converterBinding.xhtml");
        page.findElement(By.id("form:input")).sendKeys("abc");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals("ABC", page.findElement(By.id("form:echo")).getText(), "bound custom converter input");
    }
}
