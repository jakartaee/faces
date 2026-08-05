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

package ee.jakarta.tck.faces.faces23.ajax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

class Issue2725IT extends BaseITNG {

    private static final String SCRIPT_BODY = "var issue2725 = \"This has a";

    /**
     * A double quote inside the body of an inline script is written through unescaped on an initial render.
     *
     * @see jakarta.faces.context.ResponseWriter
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2725
     */
    @Test
    void quoteInScriptSurvivesInitialRender() {
        WebPage page = getPage("issue2725.xhtml");

        assertTrue(page.getSource().contains(SCRIPT_BODY),
                "The quote inside the script must be written through unescaped.");
    }

}
