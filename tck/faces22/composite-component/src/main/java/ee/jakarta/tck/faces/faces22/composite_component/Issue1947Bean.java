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
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

/**
 * Passed into the composites as a plain object-valued attribute. The bean is request scoped so that
 * the status reflects only the invocations made by the request under test: an unrelated submit must
 * render an empty status. removeGroup counts its invocations to pin that it runs exactly once.
 */
@Named
@RequestScoped
public class Issue1947Bean {

    private String status = "";
    private int removeGroupCount;
    private String groupToAdd = "select one";

    public void valueChange(ValueChangeEvent event) {
        status = "valueChange called";
    }

    public String removeGroup() {
        status = "removeGroup called (" + ++removeGroupCount + ")";
        return null;
    }

    public String getStatus() {
        return status;
    }

    public String getGroupToAdd() {
        return groupToAdd;
    }

    public void setGroupToAdd(String groupToAdd) {
        this.groupToAdd = groupToAdd;
    }
}
