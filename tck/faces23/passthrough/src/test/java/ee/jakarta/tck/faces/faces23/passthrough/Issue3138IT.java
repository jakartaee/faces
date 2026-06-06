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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

public class Issue3138IT extends BaseITNG {

    /**
     * An HTML5 pass-through {@code select} with {@code f:selectItems} over a Map
     * must render the Map values as the option values.
     *
     * @see jakarta.faces.component.html.HtmlSelectOneListbox
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3138
     */
    @Test
    void selectOnePassthroughMapValues() throws Exception {
        WebPage page = getPage("issue3138-selectonepassthrough.xhtml");

        assertNotNull(page.findElement(By.cssSelector("#ko option[value='aaaaaaaaaa']")));
        assertNotNull(page.findElement(By.cssSelector("#ko option[value='bbbbbbbbbb']")));
        assertNotNull(page.findElement(By.cssSelector("#ko option[value='cccccccccc']")));
        assertNotNull(page.findElement(By.cssSelector("#ko option[value='dddddddddd']")));
    }
}
