/*
 * Copyright (c) 2009, 2020 Oracle and/or its affiliates. All rights reserved.
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

/*
 * $Id$
 */
package com.sun.ts.tests.jsf.spec.ajax.tagwrapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Formatter;

import org.htmlunit.html.HtmlInput;
import org.htmlunit.html.HtmlPage;
import org.htmlunit.html.HtmlSpan;
import com.sun.javatest.Status;
import com.sun.ts.tests.jsf.common.client.BaseHtmlUnitClient;

public class URLClient extends BaseHtmlUnitClient {

    private static final String CONTEXT_ROOT = "/jsf_ajax_tagwrapper_web";

    private static final String SPAN = "span";

    private static final String NL = System.getProperty("line.seperator", "\n");

    public static void main(String[] args) {
        URLClient theTests = new URLClient();
        Status s = theTests.run(args, new PrintWriter(System.out, true), new PrintWriter(System.err, true));
        s.exit();
    }

    public Status run(String[] args, PrintWriter out, PrintWriter err) {
        return super.run(args, out, err);
    }

    /*
     * @class.setup_props: webServerHost; webServerPort; ts_home;
     */

    // ---------------------------------------------------------- private
    // methods
    /**
     * Test for a the give @String "expectedValue" to match the value of the named @HtmlSpan "element "spanID".
     * 
     * @param page - @HtmlPage that contains @HtmlSpan element.
     * @param expectedValue - The expected result.
     * @param formatter - used to gather test result output.
     */
    private void validateSpanTag(HtmlPage page, String spanId, String expectedValue) throws Fault {
        StringBuilder messages = new StringBuilder(128);
        Formatter formatter = new Formatter(messages);

        HtmlSpan output = (HtmlSpan) getElementOfTypeIncludingId(page, SPAN, spanId);

        if (!validateExistence(spanId, SPAN, output, formatter)) {
            handleTestStatus(messages);
            return;
        }
        validateElementValue(output, expectedValue, formatter);

    }// End validateSpanTag
} // END URLClient
