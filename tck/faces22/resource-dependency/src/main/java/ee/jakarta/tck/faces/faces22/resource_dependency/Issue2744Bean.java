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

package ee.jakarta.tck.faces.faces22.resource_dependency;

import java.io.Serializable;

import jakarta.faces.component.UIOutput;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Issue2744Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private int counter = 0;
    private UIOutput hello;

    public void setHello(UIOutput hello) {
        this.hello = hello;
    }

    public UIOutput getHello() {
        return this.hello;
    }

    public void add() {
        ++counter;
    }

    public int getCounter() {
        return counter;
    }
}
