/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2023 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet30.compositeComponent;

import static org.junit.Assert.assertEquals;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.UIViewRoot;

@RunWith(Arquillian.class)
public class Issue5065IT extends ITBase {

    /**
     * @see UIViewRoot#findComponent(String)
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5065
     */
    @Test
    public void testFindComponentWhenNestedInComposite() throws Exception {
        HtmlPage page = getPage("issue5065.xhtml");

        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("form:submit");
        page = button.click();

        webClient.waitForBackgroundJavaScript(3000);

        String inlineInvokeApplication = page.getElementById("form:invokeApplication").asNormalizedText();
        String inlineRenderResponse = page.getElementById("form:renderResponse").asNormalizedText();
        assertEquals("same inline component is reused during render response", inlineInvokeApplication, inlineRenderResponse);

        String htmlWrapperInvokeApplication = page.getElementById("form:htmlWrapper:invokeApplication").asNormalizedText();
        String htmlWrapperRenderResponse = page.getElementById("form:htmlWrapper:renderResponse").asNormalizedText();
        assertEquals("same htmlwrapper component is reused during render response", htmlWrapperInvokeApplication, htmlWrapperRenderResponse);

        String componentWrapperInvokeApplication = page.getElementById("form:componentWrapper:invokeApplication").asNormalizedText();
        String componentWrapperRenderResponse = page.getElementById("form:componentWrapper:renderResponse").asNormalizedText();
        assertEquals("same componentWrapper component is reused during render response", componentWrapperInvokeApplication, componentWrapperRenderResponse);

    }
}
