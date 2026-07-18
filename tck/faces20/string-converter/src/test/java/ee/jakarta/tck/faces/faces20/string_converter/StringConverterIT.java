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

package ee.jakarta.tck.faces.faces20.string_converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A {@code Converter} registered by-class for {@code java.lang.String} is applied automatically to
 * every String-valued component. Each round-trip prepends {@code String_} exactly once, through
 * {@code getAsObject} on submit; rendering does not apply it again, as the renderer deliberately
 * skips by-type converter lookup for values which are already a String.
 */
class StringConverterIT extends BaseITNG {

    /**
     * @see jakarta.faces.convert.Converter
     * @see jakarta.faces.application.Application#createConverter(Class)
     */
    @Test
    void testStringConverter() {
        WebPage page = getPage("stringConverter.xhtml");

        page.findElement(By.id("form:inputText")).clear();
        page.findElement(By.id("form:inputText")).sendKeys("newString");
        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals("String_newString", page.findElement(By.id("form:outputText")).getText(),
                "the by-class String converter must apply once per submit, through getAsObject");

        page.guardHttp(page.findElement(By.id("form:submit"))::click);
        assertEquals("String_String_newString", page.findElement(By.id("form:outputText")).getText(),
                "resubmitting the converted value must prepend String_ once more");
    }
}
