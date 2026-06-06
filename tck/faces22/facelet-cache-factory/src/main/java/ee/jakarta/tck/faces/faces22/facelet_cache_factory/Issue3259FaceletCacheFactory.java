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

import jakarta.faces.view.facelets.FaceletCache;
import jakarta.faces.view.facelets.FaceletCacheFactory;

/**
 * Custom {@link FaceletCacheFactory} decorating the default one. The decorating constructor sets a detectable marker
 * proving the factory was invoked via the {@code facelet-cache-factory} registration.
 */
public class Issue3259FaceletCacheFactory extends FaceletCacheFactory {

    private final boolean decoratingConstructorCalled;

    public Issue3259FaceletCacheFactory() {
        this.decoratingConstructorCalled = false;
    }

    public Issue3259FaceletCacheFactory(FaceletCacheFactory wrapped) {
        super(wrapped);
        this.decoratingConstructorCalled = true;
    }

    public boolean isDecoratingConstructorCalled() {
        return decoratingConstructorCalled;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public FaceletCache getFaceletCache() {
        return getWrapped().getFaceletCache();
    }
}
