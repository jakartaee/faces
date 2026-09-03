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

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.ExternalContextWrapper;

/**
 * Records the order in which the response content type is set and the response output is first obtained, so that a test can assert the content type was already
 * set by the time the runtime asked for the writer or stream.
 */
public class RecordingExternalContext extends ExternalContextWrapper {

    static final String GET_RESPONSE_OUTPUT_WRITER = "getResponseOutputWriter()";
    static final String GET_RESPONSE_OUTPUT_STREAM = "getResponseOutputStream()";
    static final String SET_RESPONSE_CONTENT_TYPE = "setResponseContentType(";

    private final ExternalContext wrapped;
    private final List<String> calls = new CopyOnWriteArrayList<>();

    public RecordingExternalContext(ExternalContext wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public void setResponseContentType(String contentType) {
        calls.add(SET_RESPONSE_CONTENT_TYPE + contentType + ")");
        super.setResponseContentType(contentType);
    }

    @Override
    public Writer getResponseOutputWriter() throws IOException {
        calls.add(GET_RESPONSE_OUTPUT_WRITER);
        return super.getResponseOutputWriter();
    }

    @Override
    public OutputStream getResponseOutputStream() throws IOException {
        calls.add(GET_RESPONSE_OUTPUT_STREAM);
        return super.getResponseOutputStream();
    }

    List<String> getCalls() {
        return calls;
    }

    @Override
    public ExternalContext getWrapped() {
        return wrapped;
    }

}
