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

package ee.jakarta.tck.faces.test.javaee8.importConstants;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.faces.component.UIImportConstants;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class Spec1424IT extends BaseITNG {

  /**
   * @see UIImportConstants
     * @see https://github.com/jakartaee/faces/issues/1424
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("spec1424.xhtml");

      assertEquals("jakarta.faces.PARTIAL_STATE_SAVING", page.findElement(By.id("result")).getText());
      assertEquals("{ACCEPT=ACCEPT, COMPLETE=COMPLETE, REJECT=REJECT}", page.findElement(By.id("results")).getText());
    }
}
