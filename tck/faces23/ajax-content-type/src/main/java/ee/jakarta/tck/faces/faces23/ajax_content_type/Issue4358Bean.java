/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.ajax_content_type;

import static ee.jakarta.tck.faces.faces23.ajax_content_type.RecordingExternalContext.GET_RESPONSE_OUTPUT_STREAM;
import static ee.jakarta.tck.faces.faces23.ajax_content_type.RecordingExternalContext.GET_RESPONSE_OUTPUT_WRITER;

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue4358Bean {

    private static final String PARTIAL_CONTENT_TYPE = "text/xml";
    private static final String HTML_CONTENT_TYPE = "text/html";

    public List<String> getExternalContextCalls() {
        return calls(FacesContext.getCurrentInstance());
    }

    /**
     * Reports SUCCESS when the content type set most recently before the response output was first obtained is the one required for this kind of request:
     * text/xml for a partial response, text/html otherwise.
     */
    public String getResult() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<String> calls = calls(context);
        int firstOutput = firstResponseOutputCall(calls);

        if (firstOutput <= 0) {
            return "FAILURE: response output obtained at index " + firstOutput;
        }

        String whenObtained = calls.get(firstOutput - 1);
        String effective = context.getExternalContext().getResponseContentType();
        String expected = context.getPartialViewContext().isPartialRequest() ? PARTIAL_CONTENT_TYPE : HTML_CONTENT_TYPE;

        return effective != null && effective.contains(expected)
            ? "SUCCESS"
            : "FAILURE: expected " + expected + " but effective was " + effective + " and when obtained " + whenObtained;
    }

    private static int firstResponseOutputCall(List<String> calls) {
        int writer = calls.indexOf(GET_RESPONSE_OUTPUT_WRITER);
        int stream = calls.indexOf(GET_RESPONSE_OUTPUT_STREAM);

        if (writer < 0) {
            return stream;
        }

        return stream < 0 ? writer : Math.min(writer, stream);
    }

    private static List<String> calls(FacesContext context) {
        return ((RecordingExternalContext) context.getExternalContext()).getCalls();
    }

}
