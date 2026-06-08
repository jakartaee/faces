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

package ee.jakarta.tck.faces.faces22.view_param;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * When multiple f:viewParam values are stored in view scope, an h:link with includeViewParams="true" renders
 * all of them as request query parameters in the generated URL.
 */
class Issue3084IT extends BaseITNG {

    /**
     * Sets two view parameters into view scope (via ajax command links), performs a postback to retain them, then
     * verifies the h:link with includeViewParams="true" renders both as query parameters, as mandated by
     * {@link jakarta.faces.component.UIViewParameter}.
     *
     * @see jakarta.faces.component.UIViewParameter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3084
     */
    @Test
    void testMultipleViewParameters() {
        WebPage page = getPage("issue3084.xhtml");

        page.guardAjax(() -> page.findElement(By.id("form:set1")).click());
        page.guardAjax(() -> page.findElement(By.id("form:set2")).click());
        page.guardHttp(() -> page.findElement(By.id("form:postback")).click());

        String linkUrl = page.findElement(By.id("form:link")).getAttribute("href");
        assertTrue(linkUrl.contains("param1=value") && linkUrl.contains("param2=value"),
                "h:link with includeViewParams renders both view parameters as query parameters: " + linkUrl);
    }
}
