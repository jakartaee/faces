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
package ee.jakarta.tck.faces.faces22.flows;

import java.io.Serializable;

import jakarta.faces.flow.FlowScoped;
import jakarta.inject.Named;

/**
 * Flow scoped bean of the intermediate flow which calls back into the flow that called it. Its
 * {@code called} flag doubles as the outbound parameter of that call and is verified again once the
 * recursively entered frame has returned.
 */
@Named
@FlowScoped("issue3684-nested-flow-call-f2")
public class Issue3684Flow2Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean called;

    public void markCalled() {
        called = true;
    }

    public boolean isCalled() {
        return called;
    }
}
