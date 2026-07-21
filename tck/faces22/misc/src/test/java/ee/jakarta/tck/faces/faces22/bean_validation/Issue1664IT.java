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

package ee.jakarta.tck.faces.faces22.bean_validation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.convert.EnumConverter;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue1664IT extends BaseITNG {

    private static final String SIMPLE_VALUE_REGEX = "(?s).*Simple\\s+value\\s+is\\s+VALUE1.*";

    /**
     * A by-type converter installed for an enum value must keep being applied to
     * that value after a non-ajax postback, so the rendered value remains correct.
     *
     * @see EnumConverter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1664
     */
    @Test
    void testConverterInstallation() throws Exception {
        WebPage page = getPage("issue1664.xhtml");
        assertTrue(page.getSource().matches(SIMPLE_VALUE_REGEX));

        WebElement button = page.findElement(By.id("button"));
        page.guardHttp(button::click);

        assertTrue(page.getSource().matches(SIMPLE_VALUE_REGEX));
    }
}
