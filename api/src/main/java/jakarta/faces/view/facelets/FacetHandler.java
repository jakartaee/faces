/*
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
 * Copyright 2005-2007 The Apache Software Foundation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jakarta.faces.view.facelets;

import static java.util.Collections.emptyMap;
import static java.util.Collections.unmodifiableMap;

import java.util.Map;

import jakarta.faces.component.TransientStateHelper;
import jakarta.faces.component.UIComponent;

/**
 * <p class="changed_added_2_0">
 * An interface that allows other code to identify FaceletHandlers that correspond to component facets.
 * </p>
 *
 * @since 2.0
 */
public interface FacetHandler {

    /**
     * <p class="changed_added_5_0">
     * The key under which the runtime stores the pass through attributes of the <code>&lt;f:facet&gt;</code> tag as a <code>Map</code> in the
     * {@link TransientStateHelper} of the facet component. The keys and values of this <code>Map</code> are the same as in
     * {@link UIComponent#getPassThroughAttributes()}.
     * </p>
     *
     * @since 5.0
     */
    String PASS_THROUGH_ATTRIBUTES_KEY = "jakarta.faces.FACET_PASSTHROUGH_ATTRIBUTES";

    /**
     * <p class="changed_added_5_0">
     * Returns the pass through attributes of the <code>&lt;f:facet&gt;</code> tag which created the given facet component. These are not the pass through
     * attributes of the facet component itself. A renderer writes them on the element which it starts around the facet, not on the markup of the facet
     * component.
     * </p>
     *
     * @param facet the facet component
     *
     * @return an unmodifiable <code>Map</code> of the pass through attributes stored under {@link #PASS_THROUGH_ATTRIBUTES_KEY}, or an empty <code>Map</code>
     * if there are none.
     *
     * @throws NullPointerException if <code>facet</code> is <code>null</code>
     *
     * @since 5.0
     */
    @SuppressWarnings("unchecked")
    static Map<String, Object> getPassThroughAttributes(UIComponent facet) {
        TransientStateHelper helper = facet.getTransientStateHelper(false);
        Object attributes = helper != null ? helper.getTransient(PASS_THROUGH_ATTRIBUTES_KEY) : null;
        return attributes != null ? unmodifiableMap((Map<String, Object>) attributes) : emptyMap();
    }

    /**
     * <p class="changed_added_2_0">
     * Returns the resolved literal String value of the facet name after evaluating EL.
     * </p>
     *
     * @param ctx the <code>FaceletContext</code> for this view execution
     *
     * @return the resolved literal String value of the facet name.
     */
    String getFacetName(FaceletContext ctx);

}
