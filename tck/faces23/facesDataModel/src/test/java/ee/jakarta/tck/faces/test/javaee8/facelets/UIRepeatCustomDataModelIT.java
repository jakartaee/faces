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

package ee.jakarta.tck.faces.test.javaee8.facelets;

import static java.util.regex.Pattern.matches;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.model.FacesDataModel;

public class UIRepeatCustomDataModelIT extends ITBase {

    /**
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see FacesDataModel
     * @see https://github.com/jakartaee/faces/issues/1078
     */
    @Test
    public void testExactClassMatch() throws Exception {

        // In this test a backing bean will return an object of type Child11.
        // There's a DataModel registered for exactly this class, which should
        // be picked up. 
        //
        // The (small) challenge is that there are also DataModels
        // registered for super classes of Child11 (e.g. Child1), which can also
        // handle a Child11, but these should NOT be picked up and the exact match
        // should be preferred.
        HtmlPage page = getPage("uirepeatCustomDataModel11.xhtml");
        System.out.println(page.asXml());
        assertTrue(matches("(?s).*START.*11-member 1.*11-member 2.*END.*", page.asXml()));
    }

    /**
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see FacesDataModel
     * @see https://github.com/jakartaee/faces/issues/1078
     */
    @Test
    public void testClosestSuperClassMatch() throws Exception {

        // In this test a backing bean will return an object of type Child111.
        // There's NO DataModel registered for exactly this class. However, there
        // is a DataModel registered for several super classes, which can all
        // handle a Child111.
        // The challenge here is that the DataModel for the closest super class
        // should be chosen, which in this test is the DataModel that handles
        // a Child11.
        HtmlPage page = getPage("uirepeatCustomDataModel111.xhtml");
        assertTrue(matches("(?s).*START.*111-member 1.*111-member 2.*END.*", page.asXml()));
    }
}
