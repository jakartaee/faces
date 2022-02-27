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

import org.junit.Test;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import ee.jakarta.tck.faces.test.util.arquillian.ITBase;
import jakarta.faces.component.behavior.AjaxBehavior;

public class Issue2439IT extends ITBase {

    /**
     * @see AjaxBehavior#isDisabled()
     * @see https://github.com/eclipse-ee4j/mojarra/issues/2443
     */
    @Test
    public void testUpdateAttributeNamedValue() throws Exception {
        String expectedString1 = "<input id="+'"'+"form1:input1"+'"'+" type="+'"'+"text"+'"'+" name="+'"'+"form1:input1"+'"'+"/>";
        
        String expectedString2 = "<input id="+'"'+"form1:input2"+'"'+" type="+'"'+"text"+'"'+" name="+'"'+"form1:input2"+'"'+" onchange="+'"'+"faces.util.chain(this,event,'mojarra.ab(this,event,"+"\\"+"'valueChange\\"+"'"+",\\'@this\\',\\'@all\\')')"+'"'+"/>";

        String expectedString3 = "<input id="+'"'+"form1:input3"+'"'+" type="+'"'+"text"+'"'+" name="+'"'+"form1:input3"
+'"'+" onchange="+'"'+"faces.util.chain(this,event,'alert(\\'Hello, World!\\');')"+'"'+"/>";

        HtmlPage page = getPage("disabledBehaviors.xhtml");
        assertTrue(page.asXml().contains(expectedString1));
        assertTrue(page.asXml().contains(expectedString2));
        assertTrue(page.asXml().contains(expectedString3));
    }
}

