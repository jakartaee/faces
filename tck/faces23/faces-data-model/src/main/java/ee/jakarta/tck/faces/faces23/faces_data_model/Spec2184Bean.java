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

import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.faces.model.DataModel;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec2184Bean {

    @Inject
    private FacesContext context;

    private final Child111 child111 = new Child111("test", "test2");

    private final Spec2184Value value = new Spec2184Value(List.of("decorated1", "decorated2"));

    /**
     * @return the simple class name of the DataModel which {@link Application#createDataModel} returns for a {@link Child111}
     */
    public String getMostSpecificModel() {
        return createDataModel(child111).getClass().getSimpleName();
    }

    /**
     * @return whether the DataModel which {@link Application#createDataModel} returns for a {@link Child111} wraps that instance
     */
    public boolean isMostSpecificModelWrapsValue() {
        return createDataModel(child111).getWrappedData() == child111;
    }

    /**
     * @return whether {@link Application#createDataModel} returns <code>null</code> for a type which no DataModel wraps
     */
    public boolean isUnregisteredModelNull() {
        return createDataModel(new Spec2184UnregisteredValue()) == null;
    }

    public Spec2184Value getValue() {
        return value;
    }

    private DataModel<?> createDataModel(Object value) {
        return context.getApplication().createDataModel(context, value);
    }

}
