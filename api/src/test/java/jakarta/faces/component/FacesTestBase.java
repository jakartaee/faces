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

package jakarta.faces.component;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import jakarta.el.ELContext;
import jakarta.el.ELResolver;
import jakarta.el.ExpressionFactory;
import jakarta.el.StandardELContext;
import jakarta.faces.application.Application;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.application.ResourceHandler;
import jakarta.faces.application.ViewHandler;
import jakarta.faces.context.CurrentFacesContext;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.PartialViewContext;
import jakarta.faces.event.AbortProcessingException;
import jakarta.faces.event.SystemEvent;
import jakarta.faces.event.SystemEventListener;
import jakarta.faces.event.SystemEventListenerHolder;
import jakarta.faces.render.RenderKit;
import jakarta.faces.render.RenderKitFactory;
import jakarta.faces.render.Renderer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * Base for component tests which need a working {@link FacesContext} without an implementation.
 * <p>
 * The {@link FacesContext}, {@link Application}, {@link ExternalContext}, {@link ViewHandler} and {@link RenderKit} are Mockito mocks. The state a component
 * reads back during its lifecycle (view root, messages, response flags, event processing flag, partial request flags) is held in fields of this class. EL
 * expressions are parsed and evaluated by the EL implementation on the test classpath, and an unqualified name resolves to the attribute of that name in
 * {@link #requestMap}. The {@link ResourceHandler} is a partial mock which runs the concrete methods of the API. The render kit returns the renderers which a
 * test registered through {@link RenderKit#addRenderer(String, String, Renderer)}, and {@link FacesContext#getRenderKit()} returns it only while there is a
 * view root.
 * <p>
 * {@link Application#publishEvent} delivers the event to the listeners of the source, then to the application listeners subscribed for the source class, then
 * to those subscribed without a source class. It deliberately ignores {@link FacesContext#isProcessingEvents()}, so that a test catches a component which
 * publishes while event processing is suppressed.
 */
public abstract class FacesTestBase {

    protected FacesContext facesContext;
    protected Application application;
    protected ExternalContext externalContext;
    protected ViewHandler viewHandler;
    protected RenderKit renderKit;
    protected Map<String, Object> requestMap;
    protected Map<String, String> requestParameterMap;

    private final Map<String, Renderer<?>> renderers = new HashMap<>();
    private final Map<String, String> componentClasses = new HashMap<>();
    private final Map<String, List<FacesMessage>> messages = new LinkedHashMap<>();
    private final Map<Class<?>, Map<Class<?>, Set<SystemEventListener>>> systemEventListeners = new HashMap<>();
    private ExpressionFactory expressionFactory;
    private ELContext elContext;
    private UIViewRoot viewRoot;
    private boolean renderResponse;
    private boolean responseComplete;
    private boolean processingEvents;
    private boolean partialRequest;
    private boolean renderAll;

    @BeforeEach
    public void setUp() throws Exception {
        requestMap = new HashMap<>();
        requestParameterMap = new HashMap<>();
        processingEvents = true;
        expressionFactory = ExpressionFactory.newInstance();

        externalContext = mockExternalContext();
        viewHandler = mockViewHandler();
        renderKit = mockRenderKit();
        application = mockApplication();
        facesContext = mockFacesContext();

        StandardELContext standardELContext = new StandardELContext(expressionFactory);
        standardELContext.addELResolver(new RequestMapELResolver());
        standardELContext.putContext(FacesContext.class, facesContext);
        elContext = standardELContext;

        CurrentFacesContext.set(facesContext);
    }

    @AfterEach
    public void tearDown() throws Exception {
        CurrentFacesContext.set(null);
    }

    /**
     * Undoes {@link FacesContext#renderResponse()} and {@link FacesContext#responseComplete()}.
     */
    protected void resetResponseFlags() {
        renderResponse = false;
        responseComplete = false;
    }

    private ExternalContext mockExternalContext() {
        ExternalContext mock = mock(ExternalContext.class);
        Map<String, Object> sessionMap = new HashMap<>();
        Map<String, Object> applicationMap = new HashMap<>();
        when(mock.getRequestMap()).thenAnswer(invocation -> requestMap);
        when(mock.getRequestParameterMap()).thenAnswer(invocation -> requestParameterMap);
        when(mock.getSessionMap()).thenReturn(sessionMap);
        when(mock.getApplicationMap()).thenReturn(applicationMap);
        return mock;
    }

    private ViewHandler mockViewHandler() {
        ViewHandler mock = mock(ViewHandler.class);
        when(mock.createView(any(), any())).thenAnswer(invocation -> {
            UIViewRoot root = new UIViewRoot();
            root.setViewId(invocation.getArgument(1));
            root.setRenderKitId(RenderKitFactory.HTML_BASIC_RENDER_KIT);
            return root;
        });
        when(mock.calculateLocale(any())).thenReturn(Locale.getDefault());
        return mock;
    }

    private RenderKit mockRenderKit() {
        RenderKit mock = mock(RenderKit.class);
        when(mock.getRenderer(anyString(), anyString()))
            .thenAnswer(invocation -> renderers.get(invocation.getArgument(0) + "|" + invocation.getArgument(1)));
        doAnswer(invocation -> renderers.put(invocation.getArgument(0) + "|" + invocation.getArgument(1), invocation.getArgument(2)))
            .when(mock).addRenderer(anyString(), anyString(), any());
        return mock;
    }

    private Application mockApplication() {
        Application mock = mock(Application.class);
        ResourceHandler resourceHandler = mock(ResourceHandler.class, CALLS_REAL_METHODS);
        when(mock.getExpressionFactory()).thenAnswer(invocation -> expressionFactory);
        when(mock.getViewHandler()).thenReturn(viewHandler);
        when(mock.getResourceHandler()).thenReturn(resourceHandler);
        doAnswer(invocation -> componentClasses.put(invocation.getArgument(0), invocation.getArgument(1))).when(mock).addComponent(anyString(), anyString());
        when(mock.createComponent(anyString())).thenAnswer(
            invocation -> Class.forName(componentClasses.get(invocation.<String>getArgument(0)))
                .getDeclaredConstructor().newInstance()
        );
        doAnswer(invocation -> listenersFor(invocation.getArgument(0), invocation.getArgument(1)).add(invocation.getArgument(2)))
            .when(mock).subscribeToEvent(any(), any(), any());
        doAnswer(invocation -> listenersFor(invocation.getArgument(0), null).add(invocation.getArgument(1))).when(mock).subscribeToEvent(any(), any());
        doAnswer(invocation -> listenersFor(invocation.getArgument(0), invocation.getArgument(1)).remove(invocation.getArgument(2)))
            .when(mock).unsubscribeFromEvent(any(), any(), any());
        doAnswer(invocation -> listenersFor(invocation.getArgument(0), null).remove(invocation.getArgument(1))).when(mock).unsubscribeFromEvent(any(), any());
        doAnswer(invocation -> {
            publishEvent(invocation.getArgument(1), invocation.<Object>getArgument(2).getClass(), invocation.getArgument(2));
            return null;
        }).when(mock).publishEvent(any(), any(), any());
        doAnswer(invocation -> {
            publishEvent(invocation.getArgument(1), invocation.getArgument(2), invocation.getArgument(3));
            return null;
        }).when(mock).publishEvent(any(), any(), any(), any());
        return mock;
    }

    private PartialViewContext mockPartialViewContext() {
        PartialViewContext mock = mock(PartialViewContext.class);
        when(mock.isPartialRequest()).thenAnswer(invocation -> partialRequest);
        doAnswer(invocation -> partialRequest = invocation.getArgument(0)).when(mock).setPartialRequest(anyBoolean());
        when(mock.isRenderAll()).thenAnswer(invocation -> renderAll);
        doAnswer(invocation -> renderAll = invocation.getArgument(0)).when(mock).setRenderAll(anyBoolean());
        return mock;
    }

    private FacesContext mockFacesContext() {
        FacesContext mock = mock(FacesContext.class);
        Map<Object, Object> attributes = new HashMap<>();
        PartialViewContext partialViewContext = mockPartialViewContext();
        ExceptionHandler exceptionHandler = mock(ExceptionHandler.class);
        when(mock.getAttributes()).thenReturn(attributes);
        when(mock.getApplication()).thenReturn(application);
        when(mock.getExternalContext()).thenReturn(externalContext);
        when(mock.getELContext()).thenAnswer(invocation -> elContext);
        when(mock.getRenderKit()).thenAnswer(invocation -> viewRoot == null ? null : renderKit);
        when(mock.getPartialViewContext()).thenReturn(partialViewContext);
        when(mock.getExceptionHandler()).thenReturn(exceptionHandler);

        when(mock.getViewRoot()).thenAnswer(invocation -> viewRoot);
        doAnswer(invocation -> viewRoot = invocation.getArgument(0)).when(mock).setViewRoot(any());
        when(mock.isProcessingEvents()).thenAnswer(invocation -> processingEvents);
        doAnswer(invocation -> processingEvents = invocation.getArgument(0)).when(mock).setProcessingEvents(anyBoolean());

        when(mock.getRenderResponse()).thenAnswer(invocation -> renderResponse);
        doAnswer(invocation -> renderResponse = true).when(mock).renderResponse();
        when(mock.getResponseComplete()).thenAnswer(invocation -> responseComplete);
        doAnswer(invocation -> responseComplete = true).when(mock).responseComplete();

        doAnswer(invocation -> messages.computeIfAbsent(invocation.getArgument(0), clientId -> new ArrayList<>()).add(invocation.getArgument(1)))
            .when(mock).addMessage(any(), any());
        when(mock.getMessages()).thenAnswer(invocation -> messages.values().stream().flatMap(List::stream).iterator());
        return mock;
    }

    private Set<SystemEventListener> listenersFor(Class<?> systemEventClass, Class<?> sourceClass) {
        return systemEventListeners.computeIfAbsent(systemEventClass, eventClass -> new HashMap<>())
            .computeIfAbsent(sourceClass == null ? Void.class : sourceClass, key -> new LinkedHashSet<>());
    }

    private void publishEvent(Class<? extends SystemEvent> systemEventClass, Class<?> sourceBaseType, Object source) {
        SystemEvent event = null;

        try {
            if (source instanceof SystemEventListenerHolder holder) {
                event = processListeners(holder.getListenersForEventClass(systemEventClass), systemEventClass, source, event);
            }

            event = processListeners(listenersFor(systemEventClass, sourceBaseType), systemEventClass, source, event);
            processListeners(listenersFor(systemEventClass, null), systemEventClass, source, event);
        }
        catch (AbortProcessingException ignore) {
            // A listener may abort the delivery to the remaining listeners.
        }
    }

    private SystemEvent processListeners(
        Collection<SystemEventListener> listeners, Class<? extends SystemEvent> systemEventClass, Object source, SystemEvent event
    )
    {
        if (listeners == null) {
            return event;
        }

        SystemEvent result = event;

        for (SystemEventListener listener : List.copyOf(listeners)) {
            if (listener.isListenerForSource(source)) {
                if (result == null) {
                    result = createSystemEvent(systemEventClass, source);
                }

                if (result.isAppropriateListener(listener)) {
                    result.processListener(listener);
                }
            }
        }

        return result;
    }

    private SystemEvent createSystemEvent(Class<? extends SystemEvent> systemEventClass, Object source) {
        try {
            for (Constructor<?> constructor : systemEventClass.getConstructors()) {
                Class<?>[] parameterTypes = constructor.getParameterTypes();

                if (parameterTypes.length == 1 && parameterTypes[0].isInstance(source)) {
                    return (SystemEvent) constructor.newInstance(source);
                }

                if (parameterTypes.length == 2 && parameterTypes[0] == FacesContext.class && parameterTypes[1].isInstance(source)) {
                    return (SystemEvent) constructor.newInstance(facesContext, source);
                }
            }
        }
        catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }

        throw new IllegalArgumentException("No constructor of " + systemEventClass + " accepts " + source);
    }

    /**
     * Resolves an unqualified name to the attribute of that name in {@link FacesTestBase#requestMap}.
     */
    private final class RequestMapELResolver extends ELResolver {

        @Override
        public Object getValue(ELContext context, Object base, Object property) {
            if (base == null && property != null && requestMap.containsKey(property.toString())) {
                context.setPropertyResolved(true);
                return requestMap.get(property.toString());
            }
            return null;
        }

        @Override
        public Class<?> getType(ELContext context, Object base, Object property) {
            if (base == null && property != null && requestMap.containsKey(property.toString())) {
                context.setPropertyResolved(true);
                return Object.class;
            }
            return null;
        }

        @Override
        public void setValue(ELContext context, Object base, Object property, Object value) {
            if (base == null && property != null) {
                context.setPropertyResolved(true);
                requestMap.put(property.toString(), value);
            }
        }

        @Override
        public boolean isReadOnly(ELContext context, Object base, Object property) {
            return false;
        }

        @Override
        public Class<?> getCommonPropertyType(ELContext context, Object base) {
            return base == null ? String.class : null;
        }

    }

}
