/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.cdibeanvalidator.cdibeanvalidator;

import static org.junit.jupiter.api.Assertions.assertFalse;

import jakarta.inject.Inject;
import jakarta.validation.ConstraintValidator;

import org.junit.jupiter.api.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue3014IT extends ITBase {

  /**
   * @see Inject
     * @see ConstraintValidator
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3018
   */
  @Test
  void validatorInjection() throws Exception {
        HtmlPage page = webClient.getPage(webUrl);
        HtmlSubmitInput button = (HtmlSubmitInput) page.getElementById("button");
        page = button.click();

      assertFalse(page.asXml().contains("my message"));
    }
}
