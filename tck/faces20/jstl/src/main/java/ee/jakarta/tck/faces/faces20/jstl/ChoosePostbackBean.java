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
 * Backs the {@code c:when} tests of {@code cwo/choose-postback.xhtml}. Session scoped so the branch an action selected outlives the postback that selected it;
 * a branch no {@code c:when} matches selects the {@code c:otherwise}.
 */
@Named
@SessionScoped
public class ChoosePostbackBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String branch = "alpha";
    private int postbacks;

    public void setBranch(String branch) {
        this.branch = branch;
        postbacks++;
    }

    public String getBranch() {
        return branch;
    }

    public void submit() {
        postbacks++;
    }

    public int getPostbacks() {
        return postbacks;
    }

}
