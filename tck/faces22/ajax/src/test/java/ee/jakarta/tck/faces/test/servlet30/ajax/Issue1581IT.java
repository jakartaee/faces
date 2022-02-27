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

package ee.jakarta.tck.faces.test.servlet30.ajax; 

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlCheckBoxInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;

public class Issue1581IT extends ITBase {

    /**
     * This test verifies correct function of SelectManyCheckbox in a Composite
     * Component over Ajax. 
     */
    @Test
    public void testSelectManyCheckboxInComposite() throws Exception {
        HtmlPage page = getPage("issue1581.xhtml");
        
        final List<HtmlCheckBoxInput> checkBoxes = getCheckboxes(page);
        
        HtmlCheckBoxInput cbox1 = checkBoxes.get(0);
       
        
        // This will ensure JavaScript finishes before evaluating the page.
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("JAVASERVERFACES-1 false, JAVASERVERFACES-2 false, JAVASERVERFACES-3 false, JAVASERVERFACES-4 false")); 
        
        page = cbox1.click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("JAVASERVERFACES-1 true, JAVASERVERFACES-2 false, JAVASERVERFACES-3 false, JAVASERVERFACES-4 false")); 

        page = getCheckboxes(page).get(1).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("JAVASERVERFACES-1 true, JAVASERVERFACES-2 true, JAVASERVERFACES-3 false, JAVASERVERFACES-4 false")); 

        page = getCheckboxes(page).get(2).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("JAVASERVERFACES-1 true, JAVASERVERFACES-2 true, JAVASERVERFACES-3 true, JAVASERVERFACES-4 false")); 

        page = getCheckboxes(page).get(3).click();
        webClient.waitForBackgroundJavaScript(3000);
        assertTrue(page.asXml().contains("JAVASERVERFACES-1 true, JAVASERVERFACES-2 true, JAVASERVERFACES-3 true, JAVASERVERFACES-4 true")); 
    }
    
    private List<HtmlCheckBoxInput> getCheckboxes(HtmlPage page) {
        List<HtmlCheckBoxInput> checkBoxes = new ArrayList<>();
        
        DomNodeList<DomElement> elements = page.getElementsByTagName("input");
        for (Iterator<DomElement> it = elements.iterator(); it.hasNext();) {
            DomElement elem = it.next();
            if (elem instanceof HtmlCheckBoxInput) {
                checkBoxes.add((HtmlCheckBoxInput)elem);
            }
        }
        
        return checkBoxes;
    }

}
