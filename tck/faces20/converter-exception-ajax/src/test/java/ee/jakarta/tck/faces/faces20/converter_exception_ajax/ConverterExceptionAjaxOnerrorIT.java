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

package ee.jakarta.tck.faces.faces20.converter_exception_ajax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.util.selenium.WebPage;

/**
 * An unchecked exception thrown from within a converter is surfaced to the client: through the ajax
 * {@code onerror} callback's {@code errorName} for an ajax submit, and through the (Development-stage)
 * error page for a non-ajax submit.
 */
public class ConverterExceptionAjaxOnerrorIT extends BaseITNG {

    /**
     * A converter throwing on an ajax submit reaches the {@code faces.ajax} onerror handler, whose
     * {@code errorName} names the thrown exception.
     *
     * @see jakarta.faces.convert.Converter#getAsObject(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent, String)
     * @see jakarta.faces.context.ExceptionHandler
     */
    @Test
    void testConverterThrowsNPEViaAjax() {
        WebPage page = getPage("converterExceptionAjaxOnerror.xhtml");
        page.guardAjax(page.findElement(By.id("ajaxForm:button1"))::click);
        assertTrue(page.findElement(By.id("ajaxResponseOutput")).getText().contains("NullPointerException"));
    }

    /**
     * A converter throwing on a non-ajax submit surfaces the exception on the error page.
     *
     * @see jakarta.faces.convert.Converter#getAsObject(jakarta.faces.context.FacesContext, jakarta.faces.component.UIComponent, String)
     * @see jakarta.faces.context.ExceptionHandler
     */
    @Test
    void testConverterThrowsNPEViaNonAjax() {
        WebPage page = getPage("converterExceptionAjaxOnerror.xhtml");
        page.guardHttp(page.findElement(By.id("nonAjaxForm:button2"))::click);
        assertTrue(page.containsSource("NullPointerException"));
    }
}
