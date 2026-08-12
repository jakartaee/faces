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

package ee.jakarta.tck.faces.faces23.ajax_content_type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;
import jakarta.faces.context.ExternalContext;

/**
 * The module registers an ExternalContextFactory so that the view can report the content type the response actually
 * carries, per kind of request. A factory applies to every view of its webapp, hence the dedicated module.
 *
 * <p>
 * The recorded call list the view also renders is diagnostic only. It is deliberately not asserted on: a wrapper does
 * not observe every path by which the runtime sets the content type, so the order of calls through the wrapper says
 * nothing about the response the client receives.
 */
class Issue4358IT extends BaseITNG {

    private static final String VIEW = "issue4358.xhtml";

    /**
     * An initial render carries text/html.
     *
     * @see ExternalContext#setResponseContentType(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4358
     */
    @Test
    void responseContentTypeIsHtmlOnInitialRender() {
        assertEquals("SUCCESS", getPage(VIEW).findElement(By.id("result")).getText());
    }

    /**
     * A partial response carries text/xml, as required for the ajax response format.
     *
     * @see ExternalContext#setResponseContentType(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4358
     */
    @Test
    void responseContentTypeIsXmlOnAjaxRequest() {
        WebPage page = getPage(VIEW);

        page.guardAjax(page.findElement(By.id("form:ajaxButton"))::click);

        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }

    /**
     * The same holds for an ajax request executing the whole view, which is the case the issue was reported for.
     *
     * @see ExternalContext#setResponseContentType(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4358
     */
    @Test
    void responseContentTypeIsXmlOnExecuteAllAjaxRequest() {
        WebPage page = getPage(VIEW);

        page.guardAjax(page.findElement(By.id("form:ajaxExecuteAllButton"))::click);

        assertEquals("SUCCESS", page.findElement(By.id("result")).getText(),
                "recorded calls: " + page.findElement(By.id("calls")).getText());
    }

    /**
     * A non ajax postback carries text/html again.
     *
     * @see ExternalContext#setResponseContentType(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/4358
     */
    @Test
    void responseContentTypeIsHtmlOnNonAjaxPostback() {
        WebPage page = getPage(VIEW);

        page.guardHttp(page.findElement(By.id("form:nonAjaxButton"))::click);

        assertEquals("SUCCESS", page.findElement(By.id("result")).getText());
    }
}
