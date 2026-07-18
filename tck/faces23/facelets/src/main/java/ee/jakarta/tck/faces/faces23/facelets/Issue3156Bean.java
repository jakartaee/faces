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
package ee.jakarta.tck.faces.faces23.facelets;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue3156Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final List<String> LIST = Arrays.asList("1", "2", "3");

    public List<String> getList1() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evaluated 1"));
        }
        return LIST;
    }

    public List<String> getList2() {
        if (FacesContext.getCurrentInstance().isPostback()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Evaluated 2"));
        }
        return LIST;
    }

    public String getCurrentDate() {
        return new Date().toString();
    }
}
