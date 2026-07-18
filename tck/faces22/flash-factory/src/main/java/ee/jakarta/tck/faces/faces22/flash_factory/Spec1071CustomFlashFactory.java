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
package ee.jakarta.tck.faces.faces22.flash_factory;

import jakarta.faces.context.Flash;
import jakarta.faces.context.FlashFactory;

public class Spec1071CustomFlashFactory extends FlashFactory {

    private final FlashFactory parent;

    public Spec1071CustomFlashFactory(FlashFactory parent) {
        this.parent = parent;
    }

    @Override
    public Flash getFlash(boolean create) {
        return new Spec1071CustomFlash(getWrapped().getFlash(create));
    }

    @Override
    public FlashFactory getWrapped() {
        return parent;
    }
}
