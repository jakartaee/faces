/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.faces50.facelets;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

@RunWith(Arquillian.class)
public class Spec1713IT extends ITBase {

    /**
     * @see com.sun.faces.facelets.component.UIRepeat
     * @see https://github.com/jakartaee/faces/issues/1713
     */
    @Test
    public void testSpec1713() throws Exception {
        HtmlPage page = getPage("spec1713.xhtml");
        assertOutput(page, "repeat1", "1 2 3");
        assertOutput(page, "repeat2", "-3 -2 -1 0 1 2 3");
        assertOutput(page, "repeat3", "3 2 1 0 -1 -2 -3");
        assertOutput(page, "repeat4", "-3 -1 1 3");
        assertOutput(page, "repeat5", "3");
        assertOutput(page, "repeat6", "3 2 1 0");
        assertOutput(page, "repeat7", "-3 -2 -1 0");
        assertOutput(page, "repeat8", "0");
        assertOutput(page, "repeat9", "0 1 2 3");
        assertOutput(page, "repeat10", "0 -1 -2 -3");
        assertOutput(page, "repeat11", "0");
        assertOutput(page, "repeat12", "");
        assertOutput(page, "repeat13", "C D E");
        assertOutput(page, "repeat14", "A B");
        assertOutput(page, "repeat15", "C D");
        assertOutput(page, "repeat16", "A C E");
        assertOutput(page, "repeat17", "B D");
        assertOutput(page, "repeat18", "B");
        
        assertFacesException("repeat19");
        assertFacesException("repeat20");
        assertFacesException("repeat21");
        assertFacesException("repeat22");
        assertFacesException("repeat23");
        assertFacesException("repeat24");
        assertFacesException("repeat25");
        assertFacesException("repeat26");
        assertFacesException("repeat27");
    }

    private void assertOutput(HtmlPage page, String clientId, String expected) {
        assertEquals(clientId, expected, page.getHtmlElementById(clientId).asNormalizedText());
    }

    private void assertFacesException(String clientId) throws Exception {
        assertThrows(FailingHttpStatusCodeException.class, () -> getPage("spec1713.xhtml?clientId=" + clientId));
    }
}
