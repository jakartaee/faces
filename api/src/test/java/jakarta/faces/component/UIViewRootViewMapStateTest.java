/*
 * Copyright (c) Contributors to the Eclipse Foundation.
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
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import jakarta.faces.application.Application;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.context.CurrentFacesContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PostConstructViewMapEvent;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * {@link UIViewRoot} obtains the view map from {@link ViewHandler#createViewMap(FacesContext)}, and saves it as a {@link StateHolder}: its class and its own
 * saved state, never its entries. After a restore, the view map reconnects itself to the data store of the view scope.
 */
class UIViewRootViewMapStateTest {

    private final FacesContext context = mock(FacesContext.class);

    private final Application application = mock(Application.class);

    private final ViewHandler viewHandler = mock(ViewHandler.class);

    @BeforeEach
    void setCurrentInstance() {
        CurrentFacesContext.set(context);
    }

    @AfterEach
    void releaseCurrentInstance() {
        CurrentFacesContext.set(null);
    }

    @Test
    void getViewMapCreatesViewMapThroughViewHandler() {
        TestViewMap createdViewMap = viewHandlerCreating(new TestViewMap());
        UIViewRoot viewRoot = new UIViewRoot();

        assertSame(createdViewMap, viewRoot.getViewMap(true));
        assertSame(createdViewMap, viewRoot.getViewMap(true));
        verify(viewHandler, times(1)).createViewMap(context);
        verify(application).publishEvent(context, PostConstructViewMapEvent.class, UIViewRoot.class, viewRoot);
    }

    @Test
    void getViewMapRejectsViewMapWhichIsNotStateHolder() {
        viewHandlerCreating(new HashMap<>());
        UIViewRoot viewRoot = new UIViewRoot();

        assertThrows(IllegalStateException.class, () -> viewRoot.getViewMap(true));
        assertNull(viewRoot.getViewMap(false));
    }

    @Test
    void restoreViewScopeStateRestoresOnlyViewMap() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreViewScopeState(context, stateWithViewMap("id1"));

        assertInstanceOf(TestViewMap.class, viewRoot.getViewMap(false));
        assertNull(viewRoot.getRenderKitId());
    }

    @Test
    void restoreViewScopeStateRestoresViewMapAsStateHolder() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreViewScopeState(context, stateWithViewMap("id1"));

        TestViewMap viewMap = assertInstanceOf(TestViewMap.class, viewRoot.getViewMap(false));
        assertEquals("id1", viewMap.id);
    }

    @Test
    void restoreStateRestoresViewMapAsStateHolder() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreState(context, stateWithViewMap("id1"));

        TestViewMap viewMap = assertInstanceOf(TestViewMap.class, viewRoot.getViewMap(false));
        assertEquals("id1", viewMap.id);
    }

    @Test
    void restoreStateKeepsViewMapRestoredByRestoreViewScopeState() {
        Object state = stateWithViewMap("id1");
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreViewScopeState(context, state);
        TestViewMap viewMap = (TestViewMap) viewRoot.getViewMap(false);
        viewMap.id = "id2";

        viewRoot.restoreState(context, state);

        assertSame(viewMap, viewRoot.getViewMap(false));
        assertEquals("id2", viewMap.id);
    }

    @Test
    void saveStateSavesViewMapAsStateHolder() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreViewScopeState(context, stateWithViewMap("id1"));
        Object state = viewRoot.saveState(context);

        UIViewRoot restoredViewRoot = new UIViewRoot();
        restoredViewRoot.restoreState(context, state);

        TestViewMap viewMap = assertInstanceOf(TestViewMap.class, restoredViewRoot.getViewMap(false));
        assertEquals("id1", viewMap.id);
    }

    @Test
    void saveStateNeverSavesViewMapEntries() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreViewScopeState(context, stateWithViewMap("id1"));
        viewRoot.getViewMap(false).put("bean", "value");
        Object state = viewRoot.saveState(context);

        UIViewRoot restoredViewRoot = new UIViewRoot();
        restoredViewRoot.restoreState(context, state);

        assertTrue(restoredViewRoot.getViewMap(false).isEmpty());
    }

    @Test
    void noViewMapIsRestoredWhenNoneWasSaved() {
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.restoreState(context, new UIViewRoot().saveState(context));

        assertNull(viewRoot.getViewMap(false));
    }

    private <M extends Map<String, Object>> M viewHandlerCreating(M viewMap) {
        when(context.getApplication()).thenReturn(application);
        when(application.getViewHandler()).thenReturn(viewHandler);
        when(viewHandler.createViewMap(context)).thenReturn(viewMap);
        return viewMap;
    }

    /**
     * Returns the saved state of a view root with a render kit id and a view map with the given id.
     */
    private Object stateWithViewMap(String id) {
        TestViewMap viewMap = new TestViewMap();
        viewMap.id = id;
        viewHandlerCreating(viewMap);
        UIViewRoot viewRoot = new UIViewRoot();
        viewRoot.setRenderKitId("renderKitId");
        viewRoot.getViewMap(true);
        return viewRoot.saveState(context);
    }

    /**
     * A view map as the implementation supplies it: a {@link StateHolder} which saves only the id of its data store.
     */
    public static class TestViewMap extends HashMap<String, Object> implements StateHolder {

        private static final long serialVersionUID = 1L;

        String id;

        @Override
        public Object saveState(FacesContext context) {
            return id;
        }

        @Override
        public void restoreState(FacesContext context, Object state) {
            id = (String) state;
        }

        @Override
        public boolean isTransient() {
            return false;
        }

        @Override
        public void setTransient(boolean newTransientValue) {
            // A view map is never transient.
        }

    }

}
