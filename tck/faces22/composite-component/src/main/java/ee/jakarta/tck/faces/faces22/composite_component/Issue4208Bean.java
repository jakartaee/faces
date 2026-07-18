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
import java.util.HashSet;
import java.util.Set;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Tracks which of the toggleable composite components are currently visible. View scoped, so the
 * c:if build-time conditions see the accumulated state across postbacks.
 */
@Named
@ViewScoped
public class Issue4208Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final Set<Integer> visible = new HashSet<>();

    public void show(int index) {
        visible.add(index);
    }

    public void hide(int index) {
        visible.remove(index);
    }

    public boolean isVisible(int index) {
        return visible.contains(index);
    }
}
