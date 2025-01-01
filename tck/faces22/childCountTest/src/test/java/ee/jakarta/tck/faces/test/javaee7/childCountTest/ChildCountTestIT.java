/*
 * Copyright (c) 1997, 2021 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee7.childCountTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.logging.Logger;

import jakarta.faces.component.UIComponent;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class ChildCountTestIT extends BaseITNG {

    Logger logger = Logger.getLogger(ChildCountTestIT.class.getName());

    /**
     * @see UIComponent#getChildCount()
     * @see https://github.com/eclipse-ee4j/mojarra/commit/6ca5e2d2300d0e9dc0d26ce3821346a4bcbffe2e
     */
    @Test
    void childCountTest() throws Exception {
        WebPage page = getPage("childCountTest");
        if (!page.getPageSource().contains("Test PASSED")) {
            logger.warning(page.getPageSource());
        }

        assertTrue(page.getPageSource().contains("Test PASSED"));
    }

}
