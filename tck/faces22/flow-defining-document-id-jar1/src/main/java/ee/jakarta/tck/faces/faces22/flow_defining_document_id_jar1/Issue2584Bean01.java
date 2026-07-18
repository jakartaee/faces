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

package ee.jakarta.tck.faces.faces22.flow_defining_document_id_jar1;

import java.io.Serializable;

import jakarta.faces.flow.FlowScoped;
import jakarta.inject.Named;

/**
 * Scoped to the "bounded-task-flow" of defining document "flow01", which is a different flow than
 * the identically named one of defining document "flow02".
 */
@Named
@FlowScoped(value = "bounded-task-flow", definingDocumentId = "flow01")
public class Issue2584Bean01 implements Serializable {

    private static final long serialVersionUID = 1L;

    public String getName() {
        return "flow01 name";
    }

    public String getReturnValue() {
        return "/issue2584Return";
    }
}
