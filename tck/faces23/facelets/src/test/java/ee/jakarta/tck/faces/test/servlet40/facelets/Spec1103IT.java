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

import java.util.Collection;
import java.util.regex.Pattern;

import jakarta.faces.component.UIData;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1103IT extends BaseITNG {

  /**
   * @see UIData
     * @see Iterable
     * @see https://github.com/jakartaee/faces/issues/1103
   */
  @Test
  void dataTableIterable() throws Exception {
        WebPage page = getPage("faces/datatableIterable.xhtml");
        assertTrue(Pattern.matches("(?s).*START.*0.*1.*2.*END.*", page.getPageSource()));
    }

  /**
   * @see com.sun.faces.facelets.component.UIRepeat
     * @see Iterable
     * @see https://github.com/jakartaee/faces/issues/1103
   */
  @Test
  void uIRepeatIterable() throws Exception {
        WebPage page = getPage("faces/uirepeatIterable.xhtml");
        assertTrue(Pattern.matches("(?s).*START.*0.*1.*2.*END.*", page.getPageSource()));
    }

  /**
   * @see com.sun.faces.facelets.component.UIRepeat
     * @see Collection
     * @see https://github.com/jakartaee/faces/issues/1103
   */
  @Test
  void uIRepeatCollection() throws Exception {
        WebPage page = getPage("faces/uirepeatCollection.xhtml");
        assertTrue(Pattern.matches("(?s).*START.*1.*2.*3.*END.*", page.getPageSource()));
    }
}
