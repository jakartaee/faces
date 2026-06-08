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

package ee.jakarta.tck.faces.faces22.facelet_cache_factory;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FactoryFinder;
import jakarta.faces.view.facelets.FaceletCacheFactory;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue3259Bean {

    public String getResult() {
        FaceletCacheFactory factory = (FaceletCacheFactory) FactoryFinder.getFactory(FactoryFinder.FACELET_CACHE_FACTORY);

        boolean invoked = factory instanceof Issue3259FaceletCacheFactory
                && ((Issue3259FaceletCacheFactory) factory).isDecoratingConstructorCalled();

        return invoked ? "SUCCESS" : "FAILURE";
    }
}
