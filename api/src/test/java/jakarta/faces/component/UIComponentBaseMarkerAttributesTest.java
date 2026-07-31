/*
 * Copyright (c) Contributors to Eclipse Foundation.
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

package jakarta.faces.component;

import static jakarta.faces.component.PackageUtils.FACET_NAME;
import static jakarta.faces.component.PackageUtils.MARK_DELETED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

/**
 * Facelets scopes a facet by putting its name on the parent component's attributes for the duration of the facet body
 * and removing it again afterwards, and it flags components for deletion the same way. Both markers are answered from a
 * field rather than from the state map, so the attributes map has to keep reading like a map while the marker never
 * becomes part of the component's state.
 */
class UIComponentBaseMarkerAttributesTest {

    @Test
    void facetNameMarkerReadsBackThroughTheAttributesMap() {
        Map<String, Object> attributes = new UIPanel().getAttributes();
        attributes.put(FACET_NAME, "header");

        assertEquals("header", attributes.get(FACET_NAME));
        assertTrue(attributes.containsKey(FACET_NAME));

        attributes.remove(FACET_NAME);

        assertNull(attributes.get(FACET_NAME));
        assertFalse(attributes.containsKey(FACET_NAME));
    }

    @Test
    void buildTimeMarkersStayOutOfTheStateBackedAttributes() {
        Map<String, Object> attributes = new UIPanel().getAttributes();
        attributes.put(FACET_NAME, "header");
        attributes.put(MARK_DELETED, Boolean.TRUE);
        attributes.put("data-role", "banner");

        assertEquals(Set.of("data-role"), attributes.keySet(),
                "only the plain attribute is state-backed, so only it can be saved");
    }
}
