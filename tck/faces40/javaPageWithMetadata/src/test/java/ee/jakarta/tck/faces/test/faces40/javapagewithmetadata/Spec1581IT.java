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

package ee.jakarta.tck.faces.test.faces40.javapagewithmetadata;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.annotation.View;
import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.faces.view.facelets.Facelet;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Spec1581IT extends BaseITNG {

  /**
   * @see Facelet
     * @see View
     * @see ViewDeclarationLanguage#getViewMetadata(jakarta.faces.context.FacesContext, String)
     * @see https://github.com/jakartaee/faces/issues/1581
   */
  @Test
  void test() throws Exception {
        WebPage page = getPage("hello.xhtml?id=foo");

        assertTrue(page.getPageSource().contains("Id is:foo"));
    }

}
