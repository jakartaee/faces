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

package ee.jakarta.tck.faces.faces23.flash;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2140Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String flashValue;

    private String value;

    public Issue2140Bean() {
        Object current = FacesContext.getCurrentInstance().getExternalContext().getFlash().get("flashValue");
        if (current != null) {
            this.flashValue = current.toString();
        }
    }

    public String getFlashValue() {
        return this.flashValue;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("flashValue", value);
    }
}
