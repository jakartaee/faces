/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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
package jakarta.faces.component;

/**
 * <p class="changed_added_4_0">
 * <strong>Doctype</strong> is an interface that must be implemented by any {@link UIComponent} that represents a document type declaration.
 * </p>
 *
 * @since 4.0
 */
public interface Doctype {

    /**
     * Returns the name of the first element in the document, never <code>null</code>. For example, <code>"html"</code>.
     *
     * @return The name of the first element in the document, never <code>null</code>.
     */
    String getRootElement();

    /**
     * Returns the public identifier of the document, or <code>null</code> if there is none. For example, <code>"-//W3C//DTD XHTML 1.1//EN"</code>.
     *
     * @return The public identifier of the document, or <code>null</code> if there is none.
     */
    String getPublic();

    /**
     * Returns the system identifier of the document, or <code>null</code> if there is none. For example,
     * <code>"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"</code>.
     *
     * @return The system identifier of the document, or <code>null</code> if there is none.
     */
    String getSystem();

}
