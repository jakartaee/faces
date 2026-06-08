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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.faces.view.ViewDeclarationLanguage;
import jakarta.faces.view.ViewDeclarationLanguageFactory;

public class Issue2113ViewDeclarationLanguageFactory extends ViewDeclarationLanguageFactory {

    private ViewDeclarationLanguageFactory toWrap;
    private Map<String, Issue2113ViewDeclarationLanguage> vdlImpls;

    public Issue2113ViewDeclarationLanguageFactory(ViewDeclarationLanguageFactory toWrap) {
        this.toWrap = toWrap;
        vdlImpls = new ConcurrentHashMap<>();
    }

    @Override
    public ViewDeclarationLanguageFactory getWrapped() {
        return toWrap;
    }

    @Override
    public ViewDeclarationLanguage getViewDeclarationLanguage(String string) {
        Issue2113ViewDeclarationLanguage result = vdlImpls.get(string);

        if (null == result) {
            result = new Issue2113ViewDeclarationLanguage(getWrapped().getViewDeclarationLanguage(string));
            vdlImpls.put(string, result);
        }

        return result;
    }
}
