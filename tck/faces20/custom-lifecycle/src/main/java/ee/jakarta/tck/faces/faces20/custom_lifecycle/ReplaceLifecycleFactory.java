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

package ee.jakarta.tck.faces.faces20.custom_lifecycle;

import java.util.Iterator;

import jakarta.faces.lifecycle.Lifecycle;
import jakarta.faces.lifecycle.LifecycleFactory;

/**
 * Replaces the {@link LifecycleFactory}, registering an additional {@link ReplaceLifecycle} under
 * {@link ReplaceLifecycle#LIFECYCLE_ID} that the application selects via the
 * {@code jakarta.faces.LIFECYCLE_ID} context parameter.
 */
public class ReplaceLifecycleFactory extends LifecycleFactory {

    public ReplaceLifecycleFactory(LifecycleFactory wrapped) {
        super(wrapped);
        addLifecycle(ReplaceLifecycle.LIFECYCLE_ID, new ReplaceLifecycle(getLifecycle(DEFAULT_LIFECYCLE)));
    }

    @Override
    public void addLifecycle(String lifecycleId, Lifecycle lifecycle) {
        getWrapped().addLifecycle(lifecycleId, lifecycle);
    }

    @Override
    public Lifecycle getLifecycle(String lifecycleId) {
        return getWrapped().getLifecycle(lifecycleId);
    }

    @Override
    public Iterator<String> getLifecycleIds() {
        return getWrapped().getLifecycleIds();
    }
}
