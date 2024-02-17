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

import java.util.Locale;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletRequest;

@Named
@RequestScoped
public class Issue5160Bean {

    @Inject
    private HttpServletRequest request;

    private Locale locale;

    @PostConstruct
    public void init() {
        locale = parseLocale(request.getHeader("Accept-Language"));
    }

    private Locale parseLocale(String languageTag) {
        String[] chunks = languageTag.split("[_-]");
        switch (chunks.length) {
            case 1: return new Locale(chunks[0]);
            case 2: return new Locale(chunks[0], chunks[1]);
            default: return new Locale(chunks[0], chunks[1], chunks[2]);
        }
    }

    public Locale getLocale() {
        return locale;
    }

}
