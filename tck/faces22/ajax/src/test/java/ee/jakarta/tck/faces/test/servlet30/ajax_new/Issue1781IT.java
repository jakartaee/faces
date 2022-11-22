/*
 * Copyright (c) 2021 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.ajax_new;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;
import jakarta.faces.component.behavior.ClientBehaviorHolder;
import jakarta.faces.component.html.HtmlBody;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class Issue1781IT extends BaseITNG {

    /**
     * Test attaching a ClientBehaviorHolder to h:body. Note the current 2.1
     * spec does not allow using f:ajax outside of a form so this will throw
     * a script error which we are going to ignore.
     *
     * @see ClientBehaviorHolder
     * @see HtmlBody
     * @see https://github.com/eclipse-ee4j/mojarra/issues/1785
     */
    @Test
    public void testAjaxToOnBody() throws Exception {
        // we stick with the webclient for simple http checks
        // the selenium drivers cannot do it, that easily
        WebPage page = getPage("body.xhtml");

        //waitForCondition((webDriver) -> super.page.getResponseStatus() != -1, Duration.ofMillis(3000));
        assertEquals(page.getResponseStatus(), 200);
    }
}
