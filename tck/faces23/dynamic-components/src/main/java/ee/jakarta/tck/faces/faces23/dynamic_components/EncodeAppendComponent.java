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

package ee.jakarta.tck.faces.faces23.dynamic_components;

import java.io.IOException;

import jakarta.faces.component.FacesComponent;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.context.FacesContext;

/**
 * During its own encode this component appends a sibling to its parent. The append therefore happens
 * while the parent is already iterating its children to encode them, so the parent's child-encode
 * loop must observe the newly added sibling and render it. The parent here is the {@code <h:body>},
 * whose renderer does not render its own children, so the children are walked by
 * {@link UIComponent#encodeAll}. Models the lazy-loading pattern where a component lazily adds a
 * sibling (e.g. a hidden form needed for an ajax request) to the body during its own render.
 */
@FacesComponent(value = "ee.jakarta.tck.faces.faces23.dynamic_components.EncodeAppendComponent")
public class EncodeAppendComponent extends UIComponentBase {

    public static final String SIBLING_ID = "encodeAppendedSibling";
    public static final String SIBLING_VALUE = "SIBLING_RENDERED_DURING_ENCODE";

    @Override
    public String getFamily() {
        return DynamicComponentBase.FAMILY;
    }

    @Override
    public void encodeBegin(FacesContext context) throws IOException {
        UIComponent parent = getParent();

        if (parent.findComponent(SIBLING_ID) == null) {
            HtmlOutputText sibling = new HtmlOutputText();
            sibling.setId(SIBLING_ID);
            sibling.setValue(SIBLING_VALUE);
            parent.getChildren().add(sibling);
        }
    }
}
