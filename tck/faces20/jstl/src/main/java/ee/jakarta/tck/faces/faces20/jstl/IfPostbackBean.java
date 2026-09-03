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

package ee.jakarta.tck.faces.faces20.jstl;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

/**
 * Backs the {@code c:if} test of {@code iftag/if-postback.xhtml}. Session scoped so the condition an action set outlives the postback that set it, and every
 * action counts itself so a postback is observable in the response even when it changes nothing else.
 */
@Named
@SessionScoped
public class IfPostbackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean shown = true;
    private int postbacks;

    public void setShown(boolean shown) {
        this.shown = shown;
        postbacks++;
    }

    public boolean isShown() {
        return shown;
    }

    public void submit() {
        postbacks++;
    }

    public int getPostbacks() {
        return postbacks;
    }

}
