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

package ee.jakarta.tck.faces.faces20.view_param_el_redirect;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * A value expression in a faces-config redirect {@code redirect-param} is evaluated when a bookmarkable
 * link ({@code h:link}) computes its target URL, so the parameter appears with its evaluated value.
 */
public class Issue2888IT extends BaseITNG {

    /**
     * The bookmarkable link's URL carries the evaluated redirect parameter {@code viewparam=5}
     * (from {@code #{2 + 3}} in the navigation case).
     *
     * @see jakarta.faces.component.html.HtmlOutcomeTargetLink
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2888
     */
    @Test
    void testELViewParamInFacesConfig() {
        WebPage page = getPage("issue2888.xhtml");
        String href = page.findElement(By.id("link")).getAttribute("href");
        assertTrue(href.contains("viewparam=5"), () -> "expected viewparam=5 in href but was: " + href);
    }
}
