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

package ee.jakarta.tck.faces.faces23.config_files_navigation;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2444IT extends BaseITNG {

    /**
     * The UTF-8 percent-encoding of the multibyte query param value "日א".
     */
    private static final String ENCODED_PARAM = "%E6%97%A5%D7%90";

    /**
     * A commandButton navigation outcome whose query string carries a multibyte value must end up
     * UTF-8 percent-encoded in the redirect URL.
     *
     * @see jakarta.faces.context.ExternalContext#encodeRedirectURL(String, java.util.Map)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2444
     */
    @Test
    void testQueryParamEncodingOnCommandButton() throws Exception {
        WebPage page = getPage("issue2444.xhtml");

        page.guardHttp(page.findElement(By.id("reload"))::click);

        String query = page.getCurrentUrl().split("\\?", 2)[1];
        assertTrue(query.contains(ENCODED_PARAM), "redirect query should contain UTF-8 encoded param, was: " + query);
    }

    /**
     * An h:button (outcome target) whose f:param value is a multibyte string must render that value
     * UTF-8 percent-encoded in the bookmarkable URL.
     *
     * @see jakarta.faces.context.ExternalContext#encodeBookmarkableURL(String, java.util.Map)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2444
     */
    @Test
    void testQueryParamEncodingOnOutcomeTargetButton() throws Exception {
        WebPage page = getPage("issue2444.xhtml");

        // The h:button renders its bookmarkable URL either as an inline onclick or a wired script
        // (implementation-agnostic per jakartaee/faces#2167); either way the encoded param is in the markup.
        assertTrue(page.containsSource(ENCODED_PARAM),
                "bookmarkable h:button URL should contain the UTF-8 encoded param " + ENCODED_PARAM);
    }
}
