/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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
package jakarta.faces.context;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import jakarta.faces.component.UIComponent;

/**
 * <p class="changed_added_5_0">
 * Provides a simple implementation of {@link PartialResponseWriter} that can be subclassed by developers wishing to
 * provide specialized behavior to an existing {@link PartialResponseWriter} instance. The default implementation of
 * all methods is to call through to the wrapped {@link PartialResponseWriter} obtained via {@link #getWrapped()}.
 * </p>
 *
 * <p>
 * Usage: extend this class and push the implementation being wrapped to the constructor and use {@link #getWrapped} to
 * access the instance being getWrapped().
 * </p>
 *
 * @since 5.0
 */
public abstract class PartialResponseWriterWrapper extends PartialResponseWriter {

    private PartialResponseWriter wrapped;

    /**
     * <p>
     * If this response writer has been decorated, the implementation doing the decorating should push the implementation
     * being wrapped to this constructor. The {@link #getWrapped()} will then return the implementation being wrapped.
     * </p>
     *
     * @param wrapped The implementation being getWrapped().
     */
    public PartialResponseWriterWrapper(PartialResponseWriter wrapped) {
        super(wrapped.getWrapped());
        this.wrapped = wrapped;
    }

    @Override
    public PartialResponseWriter getWrapped() {
        return wrapped;
    }

    // -------------------------- Methods from jakarta.faces.context.PartialResponseWriter

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#getContentType()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#getContentType()
     */
    @Override
    public String getContentType() {
        return getWrapped().getContentType();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startDocument()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startDocument()
     */
    @Override
    public void startDocument() throws IOException {
        getWrapped().startDocument();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#getCharacterEncoding()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#getCharacterEncoding()
     */
    @Override
    public String getCharacterEncoding() {
        return getWrapped().getCharacterEncoding();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#flush()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#flush()
     */
    @Override
    public void flush() throws IOException {
        getWrapped().flush();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endDocument()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endDocument()
     */
    @Override
    public void endDocument() throws IOException {
        getWrapped().endDocument();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startElement(String, UIComponent)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startElement(String, UIComponent)
     */
    @Override
    public void startElement(String name, UIComponent component) throws IOException {
        getWrapped().startElement(name, component);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startInsertBefore(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startInsertBefore(String)
     */
    @Override
    public void startInsertBefore(String targetId) throws IOException {
        getWrapped().startInsertBefore(targetId);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startCDATA()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startCDATA()
     */
    @Override
    public void startCDATA() throws IOException {
        getWrapped().startCDATA();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endCDATA()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endCDATA()
     */
    @Override
    public void endCDATA() throws IOException {
        getWrapped().endCDATA();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startInsertAfter(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startInsertAfter(String)
     */
    @Override
    public void startInsertAfter(String targetId) throws IOException {
        getWrapped().startInsertAfter(targetId);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endElement(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endElement(String)
     */
    @Override
    public void endElement(String name) throws IOException {
        getWrapped().endElement(name);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startDocument()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startDocument()
     */
    @Override
    public void write(int c) throws IOException {
        getWrapped().write(c);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endInsert()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endInsert()
     */
    @Override
    public void endInsert() throws IOException {
        getWrapped().endInsert();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeAttribute(String, Object, String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeAttribute(String, Object, String)
     */
    @Override
    public void writeAttribute(String name, Object value, String property) throws IOException {
        getWrapped().writeAttribute(name, value, property);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeURIAttribute(String, Object, String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeURIAttribute(String, Object, String)
     */
    @Override
    public void writeURIAttribute(String name, Object value, String property) throws IOException {
        getWrapped().writeURIAttribute(name, value, property);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startUpdate(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startUpdate(String)
     */
    @Override
    public void startUpdate(String targetId) throws IOException {
        getWrapped().startUpdate(targetId);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#write(char[])} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#write(char[])
     */
    @Override
    public void write(char[] cbuf) throws IOException {
        getWrapped().write(cbuf);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeComment(Object)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeComment(Object)
     */
    @Override
    public void writeComment(Object comment) throws IOException {
        getWrapped().writeComment(comment);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endUpdate()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endUpdate()
     */
    @Override
    public void endUpdate() throws IOException {
        getWrapped().endUpdate();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeDoctype(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeDoctype(String)
     */
    @Override
    public void writeDoctype(String doctype) throws IOException {
        getWrapped().writeDoctype(doctype);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#updateAttributes(String, Map)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#updateAttributes(String, Map)
     */
    @Override
    public void updateAttributes(String targetId, Map<String, String> attributes) throws IOException {
        getWrapped().updateAttributes(targetId, attributes);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#write(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#write(String)
     */
    @Override
    public void write(String str) throws IOException {
        getWrapped().write(str);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writePreamble(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writePreamble(String)
     */
    @Override
    public void writePreamble(String preamble) throws IOException {
        getWrapped().writePreamble(preamble);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#write(String, int, int)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#write(String, int, int)
     */
    @Override
    public void write(String str, int off, int len) throws IOException {
        getWrapped().write(str, off, len);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeText(Object, String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeText(Object, String)
     */
    @Override
    public void writeText(Object text, String property) throws IOException {
        getWrapped().writeText(text, property);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#delete(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#delete(String)
     */
    @Override
    public void delete(String targetId) throws IOException {
        getWrapped().delete(targetId);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeText(Object, UIComponent, String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeText(Object, UIComponent, String)
     */
    @Override
    public void writeText(Object text, UIComponent component, String property) throws IOException {
        getWrapped().writeText(text, component, property);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#redirect(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#redirect(String)
     */
    @Override
    public void redirect(String url) throws IOException {
        getWrapped().redirect(url);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#writeText(char[], int, int)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#writeText(char[], int, int)
     */
    @Override
    public void writeText(char[] text, int off, int len) throws IOException {
        getWrapped().writeText(text, off, len);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#append(CharSequence)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#append(CharSequence)
     */
    @Override
    public Writer append(CharSequence csq) throws IOException {
        return getWrapped().append(csq);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#cloneWithWriter(Writer)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#cloneWithWriter(Writer)
     */
    @Override
    public ResponseWriter cloneWithWriter(Writer writer) {
        return getWrapped().cloneWithWriter(writer);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startEval()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startEval()
     */
    @Override
    public void startEval() throws IOException {
        getWrapped().startEval();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#close()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#close()
     */
    @Override
    public void close() throws IOException {
        getWrapped().close();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endEval()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endEval()
     */
    @Override
    public void endEval() throws IOException {
        getWrapped().endEval();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startExtension(Map)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startExtension(Map)
     */
    @Override
    public void startExtension(Map<String, String> attributes) throws IOException {
        getWrapped().startExtension(attributes);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#write(char[], int, int)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#write(char[], int, int)
     */
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        getWrapped().write(cbuf, off, len);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#append(CharSequence, int, int)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#append(CharSequence, int, int)
     */
    @Override
    public Writer append(CharSequence csq, int start, int end) throws IOException {
        return getWrapped().append(csq, start, end);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endExtension()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endExtension()
     */
    @Override
    public void endExtension() throws IOException {
        getWrapped().endExtension();
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#startError(String)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#startError(String)
     */
    @Override
    public void startError(String errorName) throws IOException {
        getWrapped().startError(errorName);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#append(char)} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#append(char)
     */
    @Override
    public Writer append(char c) throws IOException {
        return getWrapped().append(c);
    }

    /**
     * <p>
     * The default behavior of this method is to call {@link PartialResponseWriter#endError()} on the wrapped
     * {@link PartialResponseWriter} object obtained via {@link #getWrapped()}.
     * </p>
     *
     * @see PartialResponseWriter#endError()
     */
    @Override
    public void endError() throws IOException {
        getWrapped().endError();
    }
}
