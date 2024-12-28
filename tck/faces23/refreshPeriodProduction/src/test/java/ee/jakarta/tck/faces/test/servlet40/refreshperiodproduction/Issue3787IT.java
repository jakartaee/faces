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

package ee.jakarta.tck.faces.test.servlet40.refreshperiodproduction;

import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.application.ProjectStage;
import jakarta.faces.application.ViewHandler;

public class Issue3787IT extends ITBase  {

    /**
     * @see ViewHandler#FACELETS_REFRESH_PERIOD_PARAM_NAME
     * @see ProjectStage#Production
     * @see https://github.com/eclipse-ee4j/mojarra/issues/3791
     */
    @Test
    public void testGetRefreshPeriod() throws Exception {
        HtmlPage page = getPage("index.xhtml");
        assertTrue(page.getBody().asNormalizedText().indexOf("-1") != -1);
    }
}
