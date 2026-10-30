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

import static jakarta.faces.model.FacesDataModel.DATA_MODEL_CLASSES_MAP_NAME;

import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec2184Bean {

    @Inject
    private BeanManager beanManager;

    /**
     * Returns the entries of the bean named {@link jakarta.faces.model.FacesDataModel#DATA_MODEL_CLASSES_MAP_NAME} which belong to this module, as
     * <code>WrappedClass=DataModelClass</code> in iteration order.
     *
     * @return the entries of this module in iteration order
     */
    public List<String> getEntries() {
        Bean<?> bean = beanManager.resolve(beanManager.getBeans(DATA_MODEL_CLASSES_MAP_NAME));
        Map<?, ?> dataModelClasses = (Map<?, ?>) beanManager.getReference(bean, Map.class, beanManager.createCreationalContext(bean));
        Package modulePackage = Spec2184Bean.class.getPackage();

        return dataModelClasses.entrySet().stream()
            .filter(entry -> ((Class<?>) entry.getKey()).getPackage() == modulePackage)
            .map(entry -> ((Class<?>) entry.getKey()).getSimpleName() + "=" + ((Class<?>) entry.getValue()).getSimpleName())
            .toList();
    }

}
