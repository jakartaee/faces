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

package ee.jakarta.tck.faces.faces20.view_param;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Counts how often the preRenderView listener of the target view has run, so that a postback on the source view can be shown not to have triggered it.
 */
@Named
@SessionScoped
public class Issue3209Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String firstId;
    private String secondId;
    private int targetListenerCount;

    public void loadTarget() {
        targetListenerCount++;
    }

    public void justAnAction() {
        // NOOP, the postback itself is what is under test.
    }

    public String getFirstId() {
        return firstId;
    }

    public void setFirstId(String firstId) {
        this.firstId = firstId;
    }

    public String getSecondId() {
        return secondId;
    }

    public void setSecondId(String secondId) {
        this.secondId = secondId;
    }

    public int getTargetListenerCount() {
        return targetListenerCount;
    }

}
