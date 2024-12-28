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

package ee.jakarta.tck.faces.test.faces40.javapages;

import jakarta.faces.annotation.View;
import jakarta.faces.view.facelets.Facelet;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Spec1581IT extends ITBase {

  /**
   * @see Facelet
     * @see View
     * @see https://github.com/jakartaee/faces/issues/1581
   */
  @Test
  void test() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "hello.xhtml");

        System.out.println(page.asXml());

        HtmlInput button = (HtmlInput) page.getElementById("form:button");
        page = button.click();

        System.out.println(page.asXml());

    }

}
