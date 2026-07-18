/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Holds the visibility of the conditionally included composite component. View scoped, so the c:if
 * build-time condition sees the value set by the previous ajax postback.
 */
@Named
@ViewScoped
public class Issue4244Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean itemVisible;

    public void show(AjaxBehaviorEvent event) {
        itemVisible = true;
    }

    public void hide(AjaxBehaviorEvent event) {
        itemVisible = false;
    }

    public boolean isItemVisible() {
        return itemVisible;
    }
}
