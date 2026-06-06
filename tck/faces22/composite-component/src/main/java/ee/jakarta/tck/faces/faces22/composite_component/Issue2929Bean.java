/*
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.component.UIComponent;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2929Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private UIComponent nonComposite;

    public UIComponent getNonComposite() {
        return nonComposite;
    }

    public void setNonComposite(UIComponent boundComponent) {
        this.nonComposite = boundComponent;
    }

    private UIComponent composite;

    public UIComponent getComposite() {
        return composite;
    }

    public void setComposite(UIComponent composite) {
        this.composite = composite;
    }
}
