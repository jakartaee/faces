/*
 * Copyright (c) 2021 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet50.inputfile;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.html.HtmlInputFile;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlFileInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Spec1556IT extends ITBase {

  /**
   * @see HtmlInputFile#getAccept()
     * @see https://github.com/jakartaee/faces/issues/1556
   */
  @Test
  void renderingOfAcceptAttribute() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "spec1556IT.xhtml");

        HtmlFileInput inputFileWithoutAccept = page.getHtmlElementById("form:inputFileWithoutAccept");
        assertEquals("", inputFileWithoutAccept.getAttribute("accept"), "Unspecified 'accept' attribute on h:inputFile is NOT rendered");

        HtmlFileInput inputFileWithAccept = page.getHtmlElementById("form:inputFileWithAccept");
        assertEquals("image/*", inputFileWithAccept.getAttribute("accept"), "Specified 'accept' attribute on h:inputFile is rendered");

        // It's for Mojarra also explicitly tested on h:inputText because they share the same renderer.
        HtmlTextInput inputTextWithoutAccept = page.getHtmlElementById("form:inputTextWithoutAccept");
        assertEquals("", inputTextWithoutAccept.getAttribute("accept"), "Unspecified 'accept' attribute on h:inputText is NOT rendered");

        HtmlTextInput inputTextWithAccept = page.getHtmlElementById("form:inputTextWithAccept");
        assertEquals("", inputTextWithAccept.getAttribute("accept"), "Specified 'accept' attribute on h:inputText is NOT rendered");

        // NOTE: HtmlUnit doesn't support filtering files by accept attribute. So the upload part is not tested to keep it simple (it's nonetheless already tested in Spec1555IT).
    }

}
