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
package ee.jakarta.tck.faces.faces20.locale_config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.application.Application;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class LocaleConfigIT extends BaseITNG {

    /**
     * Verifies that the {@code locale-config} declared in faces-config is reflected by
     * {@code Application.getDefaultLocale} and {@code Application.getSupportedLocales}.
     *
     * @see Application#getDefaultLocale()
     * @see Application#getSupportedLocales()
     */
    @Test
    void testLocaleConfig() {
        WebPage page = getPage("localeConfig.xhtml");

        assertEquals("en_US", page.findElement(By.id("form:defaultLocale")).getText(),
                "Default locale must come from faces-config locale-config");
        assertEquals("de_DE,en_US,fr_FR,ps_PS", page.findElement(By.id("form:supportedLocales")).getText(),
                "Supported locales must come from faces-config locale-config");
    }
}
