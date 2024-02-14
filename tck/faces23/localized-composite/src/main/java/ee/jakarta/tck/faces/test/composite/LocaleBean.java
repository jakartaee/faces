/*
 * Copyright (c) 2022 Contributors to the Eclipse Foundation.
 * Copyright (c) 2017, 2021 Oracle and/or its affiliates. All rights reserved.
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
package ee.jakarta.tck.faces.test.composite;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.Locale;

@Named
@SessionScoped
public class LocaleBean implements Serializable {
    
    @Inject
    private HttpServletRequest request;
    
    private Locale locale;
    
    private String language;
    
    @PostConstruct
    public void init() {
        Locale requestLocale = request.getLocale();
        setLocale(requestLocale != null ? requestLocale.toString() : "en");
    }
    
    private void setLocale(String languageTag) {
        String[] chunks = languageTag.split("(-|_)");
        switch (chunks.length) {
            case 1:
                locale = new Locale(chunks[0]);
                break;
            case 2:
                locale = new Locale(chunks[0], chunks[1]);
                break;
            default:
                locale = new Locale(chunks[0], chunks[1], chunks[2]);
                break;
        }
        language = locale.toString();
        UIViewRoot root = FacesContext.getCurrentInstance().getViewRoot();
        if (root != null) {
            root.setLocale(locale);
        }
    }

    public Locale getLocale() {
        return locale;
    }

    public String getLanguage() {
        return language;
    }
    
}
