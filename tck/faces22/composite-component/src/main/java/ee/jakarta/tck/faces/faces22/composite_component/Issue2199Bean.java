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

package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.event.ComponentSystemEvent;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

/**
 * Target of the composite:attribute method-signature bindings under test. Each listener records
 * that it ran into the status property, which the pages render into an id'd output.
 */
@Named
@RequestScoped
public class Issue2199Bean {

    private String status;

    public void processAjaxBehavior(AjaxBehaviorEvent event) {
        status = "processAjaxBehavior called";
    }

    public void listener(ComponentSystemEvent event) {
        status = "listener called";
    }

    public void valueChange(ValueChangeEvent event) {
        status = "valueChange called";
    }

    public String getStatus() {
        return status;
    }
}
