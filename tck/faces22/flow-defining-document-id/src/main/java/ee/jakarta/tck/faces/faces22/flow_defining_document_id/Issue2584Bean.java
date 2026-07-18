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

package ee.jakarta.tck.faces.faces22.flow_defining_document_id;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

/**
 * Both actions return the same flow id. Which of the two identically named flows is actually
 * entered is decided by the to-flow-document-id of the matching navigation case.
 */
@Named
@RequestScoped
public class Issue2584Bean {

    private static final String FLOW_ID = "bounded-task-flow";

    public String flow01() {
        return FLOW_ID;
    }

    public String flow02() {
        return FLOW_ID;
    }
}
