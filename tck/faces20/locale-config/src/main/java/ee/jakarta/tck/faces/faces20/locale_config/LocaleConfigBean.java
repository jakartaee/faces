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
package ee.jakarta.tck.faces.faces20.locale_config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class LocaleConfigBean {

    private Application application() {
        return FacesContext.getCurrentInstance().getApplication();
    }

    public String getDefaultLocale() {
        Locale locale = application().getDefaultLocale();
        return locale == null ? "" : locale.toString();
    }

    public String getSupportedLocales() {
        List<String> locales = new ArrayList<>();
        for (Iterator<Locale> it = application().getSupportedLocales(); it.hasNext();) {
            locales.add(it.next().toString());
        }
        Collections.sort(locales);
        return String.join(",", locales);
    }
}
