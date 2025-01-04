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

package ee.jakarta.tck.faces.test.servlet40.refreshperiodexplicit;

import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.faces.application.ProjectStage;
import jakarta.faces.application.ViewHandler;

import org.junit.jupiter.api.Test;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

public class Issue3787IT extends BaseITNG {

    /**
     * @see ViewHandler#FACELETS_REFRESH_PERIOD_PARAM_NAME
     * @see ProjectStage#Development
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3791
     */
    @Test
    public void testGetRefreshPeriod() throws Exception {
        WebPage page = getPage("index.xhtml");
        assertTrue(page.getPageSource().contains("30000"));
    }
}
