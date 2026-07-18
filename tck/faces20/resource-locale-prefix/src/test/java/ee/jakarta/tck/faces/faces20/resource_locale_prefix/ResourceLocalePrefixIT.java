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

package ee.jakarta.tck.faces.faces20.resource_locale_prefix;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;

/**
 * Localized resources are resolved through a locale prefix that is derived from the request's
 * {@code Accept-Language} intersected with the configured supported locales, falling back to the
 * default locale. The prefix surfaces on the resource URL as its {@code loc} query parameter, and
 * unsupported languages fall back to the default ({@code en}).
 */
public class ResourceLocalePrefixIT extends BaseITNG {

    private String body(String page, String language) {
        return getResponseBody(page, Map.of("Accept-Language", language));
    }

    private static void assertContains(String body, String... needles) {
        for (String needle : needles) {
            assertTrue(body.contains(needle), () -> "expected response to contain: " + needle + "\n" + body);
        }
    }

    /**
     * A non-library localized resource carries the requested locale as its {@code loc} prefix.
     *
     * @see jakarta.faces.application.ResourceHandler#LOCALE_PREFIX
     * @see jakarta.faces.application.ResourceHandler#createResource(String)
     */
    @Test
    void testResource1() {
        assertContains(body("resourceLocalePrefix1.xhtml", "en"), "jakarta.faces.resource/resource1.gif", "loc=en");
        assertContains(body("resourceLocalePrefix1.xhtml", "de"), "jakarta.faces.resource/resource1.gif", "loc=de");
        assertContains(body("resourceLocalePrefix1.xhtml", "fr"), "jakarta.faces.resource/resource1.gif", "loc=fr");
        assertContains(body("resourceLocalePrefix1.xhtml", "ja"), "jakarta.faces.resource/resource1.gif", "loc=en");
    }

    /**
     * A library localized resource carries both its {@code ln} library and its {@code loc} prefix.
     *
     * @see jakarta.faces.application.ResourceHandler#LOCALE_PREFIX
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     */
    @Test
    void testResource2() {
        assertContains(body("resourceLocalePrefix2.xhtml", "en"), "jakarta.faces.resource/resource2.gif", "ln=resource2", "loc=en");
        assertContains(body("resourceLocalePrefix2.xhtml", "de"), "jakarta.faces.resource/resource2.gif", "ln=resource2", "loc=de");
        assertContains(body("resourceLocalePrefix2.xhtml", "fr"), "jakarta.faces.resource/resource2.gif", "ln=resource2", "loc=fr");
        assertContains(body("resourceLocalePrefix2.xhtml", "ja"), "jakarta.faces.resource/resource2.gif", "ln=resource2", "loc=en");
    }

    /**
     * A non-localized non-library resource resolves regardless of the requested locale.
     *
     * @see jakarta.faces.application.ResourceHandler#LOCALE_PREFIX
     * @see jakarta.faces.application.ResourceHandler#createResource(String)
     */
    @Test
    void testResource3() {
        for (String language : new String[] { "en", "de", "fr", "ja" }) {
            assertContains(body("resourceLocalePrefix3.xhtml", language), "jakarta.faces.resource/resource3.gif");
        }
    }

    /**
     * A non-localized library resource resolves with its {@code ln} library regardless of the requested locale.
     *
     * @see jakarta.faces.application.ResourceHandler#LOCALE_PREFIX
     * @see jakarta.faces.application.ResourceHandler#createResource(String, String)
     */
    @Test
    void testResource4() {
        for (String language : new String[] { "en", "de", "fr", "ja" }) {
            assertContains(body("resourceLocalePrefix4.xhtml", language), "jakarta.faces.resource/resource4.gif", "ln=resource4");
        }
    }
}
