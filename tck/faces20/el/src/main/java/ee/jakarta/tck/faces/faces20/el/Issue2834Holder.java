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
package ee.jakarta.tck.faces.faces20.el;

import java.io.Serializable;

public class Issue2834Holder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Issue2834Inner inner;
    private Object one;

    public Issue2834Inner getInner() {
        return inner;
    }

    public void setInner(Issue2834Inner inner) {
        this.inner = inner;
    }

    public Object getOne() {
        return one;
    }

    public void setOne(Object one) {
        this.one = one;
    }
}
