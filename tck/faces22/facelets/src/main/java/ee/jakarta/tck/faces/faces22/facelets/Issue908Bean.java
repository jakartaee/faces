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

package ee.jakarta.tck.faces.faces22.facelets;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue908Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private Boolean box1;
    private Boolean box2;

    public Boolean getBox1() {
        return box1;
    }

    public void setBox1(Boolean box1) {
        this.box1 = box1;
    }

    public Boolean getBox2() {
        return box2;
    }

    public void setBox2(Boolean box2) {
        this.box2 = box2;
    }

    public void valueChange(ValueChangeEvent event) {
        FacesContext.getCurrentInstance().renderResponse();
    }
}
