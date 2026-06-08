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

package ee.jakarta.tck.faces.faces23.facelet_cache_factory;

import jakarta.faces.view.facelets.FaceletCache;
import jakarta.faces.view.facelets.FaceletCacheFactory;

public class Issue2113FaceletCacheFactory extends FaceletCacheFactory {

    private boolean oneArgCtorCalled = false;
    private FaceletCache cache;
    private FaceletCacheFactory wrapped;

    public Issue2113FaceletCacheFactory() {
        super();
        oneArgCtorCalled = false;
    }

    public Issue2113FaceletCacheFactory(FaceletCacheFactory wrapped) {
        oneArgCtorCalled = true;
        this.wrapped = wrapped;
    }

    public boolean isOneArgCtorCalled() {
        return oneArgCtorCalled;
    }

    @Override
    public FaceletCacheFactory getWrapped() {
        return wrapped;
    }

    @Override
    public FaceletCache getFaceletCache() {
        if (cache == null) {
            cache = getWrapped().getFaceletCache();
        }

        return cache;
    }
}
