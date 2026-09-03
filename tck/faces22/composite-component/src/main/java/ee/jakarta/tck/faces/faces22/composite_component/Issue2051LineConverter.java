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

package ee.jakarta.tck.faces.faces22.composite_component;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

@FacesConverter(Issue2051LineConverter.CONVERTER_ID)
public class Issue2051LineConverter implements Converter<Issue2051Line> {

    public static final String CONVERTER_ID = "issue2051LineConverter";

    @Inject
    private Issue2051Bean bean;

    @Override
    public Issue2051Line getAsObject(FacesContext context, UIComponent component, String value) {
        return bean.findLine(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Issue2051Line value) {
        return value == null ? "" : value.getId();
    }

}
