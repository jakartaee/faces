/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces23.faces_data_model;

import jakarta.faces.application.Application;
import jakarta.faces.application.ApplicationFactory;

/**
 * Decorates the {@link Application} with {@link Spec2184Application}.
 */
public class Spec2184ApplicationFactory extends ApplicationFactory {

    private volatile Application application;

    public Spec2184ApplicationFactory(ApplicationFactory wrapped) {
        super(wrapped);
    }

    @Override
    public Application getApplication() {
        if (application == null) {
            application = new Spec2184Application(getWrapped().getApplication());
        }

        return application;
    }

    @Override
    public void setApplication(Application application) {
        getWrapped().setApplication(application);
        this.application = null;
    }

}
