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

package ee.jakarta.tck.faces.faces23.misc_additive_config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.ResourceHandler;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue4086IT extends BaseITNG {

    private static final String EN = "I won Wimbledon ...(resources/en/1_0/css/js/rafa.js/1_1.js)";
    private static final String FR = "J'ai gagne Roland Garros ...(resources/fr/1_0/css/js/rafa.js/1_2.js)";
    private static final String US = "I won US Open ...(resources/us/1_0/css/js/rafa.js/1_0.js)";
    private static final String AU = "I won Australian Open ...(resources/au/1_0/css/js/rafa.js/1_0.js)";

    /**
     * When {@code f:view locale} selects a locale that has a matching locale-prefixed resource
     * directory, the resource handler must resolve {@code h:outputScript} to the highest-versioned
     * resource under that locale's library path. Switching the active locale via a postback must
     * re-resolve the script to the new locale's variant. Each locale's script writes a distinctive
     * marker (carrying its own resource path) into {@code document.title}, so the resolved variant
     * is observable.
     *
     * @see ResourceHandler
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4086
     */
    @Test
    void testLocalizedResources() {
        // Default locale "en" resolves to the highest en variant (1_1).
        WebPage page = getPage("Issue4086.xhtml");
        assertEquals(EN, page.getTitle(), "default locale en resolves rafa.js to en/1_1");

        // Switch to "fr" -> highest fr variant (1_2).
        page.guardHttp(page.findElement(By.id("atp:rg"))::click);
        assertEquals(FR, page.getTitle(), "locale fr resolves rafa.js to fr/1_2");

        // Switch to "au" -> au variant (1_0).
        page.guardHttp(page.findElement(By.id("atp:au"))::click);
        assertEquals(AU, page.getTitle(), "locale au resolves rafa.js to au/1_0");

        // Switch to "us" -> us variant (1_0).
        page.guardHttp(page.findElement(By.id("atp:us"))::click);
        assertEquals(US, page.getTitle(), "locale us resolves rafa.js to us/1_0");

        // Back to "en" via postback -> highest en variant (1_1).
        page.guardHttp(page.findElement(By.id("atp:wo"))::click);
        assertEquals(EN, page.getTitle(), "locale en resolves rafa.js to en/1_1 after postback");
    }
}
