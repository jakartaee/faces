/*
 * Copyright (c) 2026 Contributors to the Eclipse Foundation.
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

package jakarta.faces.application;

import static java.util.Collections.emptyMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.Test;

import jakarta.faces.FacesException;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;

/**
 * Verifies that the fragment-aware {@code getBookmarkableURL}/{@code getRedirectURL} overloads added in 5.0 keep
 * honoring an implementation that overrides only the deprecated fragment-less overload. Deprecation does not remove a
 * method, so a {@link ViewHandler} or {@link ViewHandlerWrapper} that customizes the deprecated overload must continue
 * to take effect even though the framework now invokes the new overload. When a fragment is actually requested the call
 * still routes to the new overload so the fragment is preserved.
 */
class ViewHandlerBookmarkableBridgeTest {

    @Test
    void baseHandlerBridgesNewOverloadToDeprecatedOverride() {
        ViewHandler handler = new LegacyViewHandler();
        assertEquals("legacy-bookmark:/page", handler.getBookmarkableURL(null, "/page", emptyMap(), null, false));
        assertEquals("legacy-redirect:/page", handler.getRedirectURL(null, "/page", emptyMap(), null, false));
    }

    @Test
    void baseHandlerDefaultStillFallsBackToActionURL() {
        ViewHandler handler = new StubViewHandler();
        assertEquals("action:/page", handler.getBookmarkableURL(null, "/page", emptyMap(), null, false));
        assertEquals("action:/page", handler.getRedirectURL(null, "/page", emptyMap(), null, false));
    }

    @Test
    void wrapperBridgesNewOverloadToDeprecatedOverrideWhenNoFragment() {
        ViewHandler handler = new LegacyViewHandlerWrapper(new StubViewHandler());
        assertEquals("wrapper-legacy-bookmark:/page", handler.getBookmarkableURL(null, "/page", emptyMap(), null, false));
        assertEquals("wrapper-legacy-bookmark:/page", handler.getBookmarkableURL(null, "/page", emptyMap(), "", false));
        assertEquals("wrapper-legacy-redirect:/page", handler.getRedirectURL(null, "/page", emptyMap(), null, false));
    }

    @Test
    void wrapperRoutesToWrappedNewOverloadWhenFragmentPresent() {
        ViewHandler handler = new LegacyViewHandlerWrapper(new NewViewHandler());
        assertEquals("new-bookmark:/page#frag", handler.getBookmarkableURL(null, "/page", emptyMap(), "frag", false));
        assertEquals("new-redirect:/page#frag", handler.getRedirectURL(null, "/page", emptyMap(), "frag", false));
    }

    // Stubs ----------------------------------------------------------------------------------------------------------

    private static class StubViewHandler extends ViewHandler {

        @Override
        public String getActionURL(FacesContext context, String viewId) {
            return "action:" + viewId;
        }

        @Override
        public UIViewRoot restoreView(FacesContext context, String viewId) {
            return null;
        }

        @Override
        public UIViewRoot createView(FacesContext context, String viewId) {
            return null;
        }

        @Override
        public void renderView(FacesContext context, UIViewRoot viewToRender) throws IOException, FacesException {
            // NOOP
        }

        @Override
        public Locale calculateLocale(FacesContext context) {
            return null;
        }

        @Override
        public String calculateRenderKitId(FacesContext context) {
            return null;
        }

        @Override
        public String getResourceURL(FacesContext context, String path) {
            return null;
        }

        @Override
        public String getWebsocketURL(FacesContext context, String channel) {
            return null;
        }

        @Override
        public void writeState(FacesContext context) throws IOException {
            // NOOP
        }
    }

    private static class LegacyViewHandler extends StubViewHandler {

        @Override
        @SuppressWarnings("deprecation")
        public String getBookmarkableURL(FacesContext context, String viewId, Map<String, List<String>> parameters, boolean includeViewParams) {
            return "legacy-bookmark:" + viewId;
        }

        @Override
        @SuppressWarnings("deprecation")
        public String getRedirectURL(FacesContext context, String viewId, Map<String, List<String>> parameters, boolean includeViewParams) {
            return "legacy-redirect:" + viewId;
        }
    }

    private static class NewViewHandler extends StubViewHandler {

        @Override
        public String getBookmarkableURL(FacesContext context, String viewId, Map<String, List<String>> parameters, String fragment, boolean includeViewParams) {
            return "new-bookmark:" + viewId + "#" + fragment;
        }

        @Override
        public String getRedirectURL(FacesContext context, String viewId, Map<String, List<String>> parameters, String fragment, boolean includeViewParams) {
            return "new-redirect:" + viewId + "#" + fragment;
        }
    }

    private static class LegacyViewHandlerWrapper extends ViewHandlerWrapper {

        LegacyViewHandlerWrapper(ViewHandler wrapped) {
            super(wrapped);
        }

        @Override
        @SuppressWarnings("deprecation")
        public String getBookmarkableURL(FacesContext context, String viewId, Map<String, List<String>> parameters, boolean includeViewParams) {
            return "wrapper-legacy-bookmark:" + viewId;
        }

        @Override
        @SuppressWarnings("deprecation")
        public String getRedirectURL(FacesContext context, String viewId, Map<String, List<String>> parameters, boolean includeViewParams) {
            return "wrapper-legacy-redirect:" + viewId;
        }
    }
}
