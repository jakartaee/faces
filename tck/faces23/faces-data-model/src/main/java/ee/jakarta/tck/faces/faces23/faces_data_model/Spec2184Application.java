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
import jakarta.faces.application.ApplicationWrapper;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.DataModel;

/**
 * Adapts a {@link Spec2184Value} into a {@link Spec2184Model}, and delegates every other value.
 */
public class Spec2184Application extends ApplicationWrapper {

    public Spec2184Application(Application wrapped) {
        super(wrapped);
    }

    @Override
    @SuppressWarnings("unchecked") // The caller chooses the row data type.
    public <T> DataModel<T> createDataModel(FacesContext context, Object value) {
        if (value instanceof Spec2184Value) {
            Spec2184Model model = new Spec2184Model();
            model.setWrappedData(value);
            return (DataModel<T>) model;
        }

        return super.createDataModel(context, value);
    }

}
