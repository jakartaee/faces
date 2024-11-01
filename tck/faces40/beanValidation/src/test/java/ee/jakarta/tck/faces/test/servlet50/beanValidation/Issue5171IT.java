/*
 * Copyright (c) 2022, 2024 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.beanValidation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@RunWith(Arquillian.class)
public class Issue5171IT extends ITBase {

    /**
     * @see https://github.com/eclipse-ee4j/mojarra/issues/5171
     */
    @Test
    public void test() throws Exception {
        HtmlPage page = getPage("issue5171.xhtml");
        page = page.getElementById("form:submit").click();
        String simpleInputMessage = page.getElementById("form:simpleInputMessage").asNormalizedText();
        String compositeInputMessage = page.getElementById("form:compositeInputMessage").asNormalizedText();
        assertTrue("simple input must trigger bean validation and show message", simpleInputMessage.endsWith("must not be empty"));
        assertTrue("composite input must trigger bean validation and show message", compositeInputMessage.endsWith("must not be empty"));

        HtmlTextInput simpleInput = (HtmlTextInput) page.getElementById("form:simpleInput");
        HtmlTextInput compositeInput = (HtmlTextInput) page.getElementById("form:composite:input");
        simpleInput.setValueAttribute("not empty");
        compositeInput.setValueAttribute("not empty");
        page = page.getElementById("form:submit").click();
        simpleInputMessage = page.getElementById("form:simpleInputMessage").asNormalizedText();
        compositeInputMessage = page.getElementById("form:compositeInputMessage").asNormalizedText();
        assertEquals("simple input must pass bean validation and clear out message", "", simpleInputMessage);
        assertEquals("composite input must pass bean validation and clear out message", "", compositeInputMessage);
    }
}
