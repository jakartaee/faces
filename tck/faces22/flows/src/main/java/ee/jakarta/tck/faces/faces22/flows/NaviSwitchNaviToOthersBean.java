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

@Named
@FlowScoped(value = "navi-start-from-switch-node")
public class NaviSwitchNaviToOthersBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String data = "0";

    public void setData(String choice) {
        data = choice;
    }

    public String getData() {
        return data;
    }

    public boolean isToMethodCall() {
        return data.equals("1");
    }

    public boolean isToView() {
        return data.equals("2");
    }

    public boolean isToSwitch() {
        return data.equals("3");
    }

    public boolean isToReturn() {
        return data.equals("4");
    }

    public boolean isToFlowCall() {
        return data.equals("5");
    }

    public boolean isCase1() {
        return true;
    }

    public boolean isCase2() {
        return false;
    }

    public String toLastPage() {
        return "DestinationView";
    }
}
