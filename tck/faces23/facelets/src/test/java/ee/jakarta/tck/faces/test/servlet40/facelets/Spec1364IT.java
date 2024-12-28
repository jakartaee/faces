/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2020 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet40.facelets;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.regex.Pattern;

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Spec1364IT extends ITBase {

  /**
   * @see UIData
     * @see Map
     * @see https://github.com/jakartaee/faces/issues/1364
   */
  @Test
  void dataTableMap() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/datatableMap.xhtml");
        assertTrue(Pattern.matches("(?s).*START.*Amsterdam.*821702.*Rotterdam.*624799.*Den Haag.*514782.*END.*", page.asXml()));
    }

  /**
   * @see com.sun.faces.facelets.component.UIRepeat
     * @see Map
     * @see https://github.com/jakartaee/faces/issues/1364
   */
  @Test
  void uIRepeatMap() throws Exception {
        HtmlPage page = webClient.getPage(webUrl + "faces/uirepeatMap.xhtml");
        assertTrue(Pattern.matches("(?s).*START.*Amsterdam-821702.*Rotterdam-624799.*Den Haag-514782.*END.*", page.asXml()));
    }

}
