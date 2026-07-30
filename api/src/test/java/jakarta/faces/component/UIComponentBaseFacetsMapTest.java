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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * A component's facets are walked while the view is being built, and a listener reached from that walk may add a facet
 * to the very component being walked: relocating a component resource creates a facet on the view root from a
 * PostAddToViewEvent listener. The iterators therefore have to keep working across such a change.
 */
class UIComponentBaseFacetsMapTest {

    @Test
    void facetAddedDuringIterationDoesNotBreakTheWalk() {
        UIComponent parent = new UIPanel();
        parent.getFacets().put("header", new UIOutput());

        Iterator<UIComponent> iterator = parent.getFacetsAndChildren();
        // the walk has started; now a listener relocates a resource, creating a second facet
        parent.getFacets().put("footer", new UIOutput());

        int walked = 0;
        while (iterator.hasNext()) {
            iterator.next();
            walked++;
        }

        assertEquals(1, walked, "the walk in progress sees the facets it started with");
        assertEquals(2, parent.getFacetCount(), "and the added facet is there afterwards");
    }

    @Test
    void facetRemovedThroughIteratorIsDetached() {
        UIComponent parent = new UIPanel();
        UIComponent header = new UIOutput();
        parent.getFacets().put("header", header);

        Iterator<UIComponent> iterator = parent.getFacets().values().iterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, parent.getFacetCount(), "removing through the iterator removes the facet");
        assertTrue(header.getParent() == null, "and detaches it from its parent");
    }

    @Test
    void iteratorsSeeFacetsAddedBeforeTheyWereCreated() {
        UIComponent parent = new UIPanel();
        parent.getFacets().put("a", new UIOutput());
        assertEquals(1, count(parent.getFacets().values().iterator()));

        parent.getFacets().put("b", new UIOutput());
        assertEquals(2, count(parent.getFacets().values().iterator()), "a later walk sees the added facet");

        parent.getFacets().remove("a");
        assertEquals(1, count(parent.getFacets().values().iterator()), "and the removed one is gone");

        assertEquals(List.of("b"), List.copyOf(parent.getFacets().keySet()));
    }

    private static int count(Iterator<?> iterator) {
        int n = 0;
        while (iterator.hasNext()) {
            iterator.next();
            n++;
        }
        return n;
    }
}
