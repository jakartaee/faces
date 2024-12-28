/*
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

class Issue5171IT extends ITBase {

  /**
   * @see https://github.com/eclipse-ee4j/mojarra/issues/5171
   */
  @Test
  void test() throws Exception {
        HtmlPage page = getPage("issue5171.xhtml");
        page = page.getElementById("form:submit").click();
        String simpleInputMessage = page.getElementById("form:simpleInputMessage").asNormalizedText();
        String compositeInputMessage = page.getElementById("form:compositeInputMessage").asNormalizedText();
        assertTrue(simpleInputMessage.endsWith("must not be empty"), "simple input must trigger bean validation and show message");
        assertTrue(compositeInputMessage.endsWith("must not be empty"), "composite input must trigger bean validation and show message");

        HtmlTextInput simpleInput = (HtmlTextInput) page.getElementById("form:simpleInput");
        HtmlTextInput compositeInput = (HtmlTextInput) page.getElementById("form:composite:input");
        simpleInput.setValueAttribute("not empty");
        compositeInput.setValueAttribute("not empty");
        page = page.getElementById("form:submit").click();
        simpleInputMessage = page.getElementById("form:simpleInputMessage").asNormalizedText();
        compositeInputMessage = page.getElementById("form:compositeInputMessage").asNormalizedText();
        assertEquals("", simpleInputMessage, "simple input must pass bean validation and clear out message");
        assertEquals("", compositeInputMessage, "composite input must pass bean validation and clear out message");
    }
}
