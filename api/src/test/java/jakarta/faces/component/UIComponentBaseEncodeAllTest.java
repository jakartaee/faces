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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.el.ELContext;
import jakarta.el.ValueExpression;
import jakarta.faces.application.Application;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.MockedStatic;

/**
 * The expression of the <code>rendered</code> attribute may call an expensive getter, so one {@link UIComponent#encodeAll(FacesContext)} evaluates it once,
 * with the component as the current component.
 */
public class UIComponentBaseEncodeAllTest {

    private final ValueExpression rendered = mock(ValueExpression.class);
    private FacesContext context;
    private MockedStatic<FacesContext> currentInstance;

    @BeforeEach
    public void setUp() {
        context = mock(FacesContext.class);
        when(context.getAttributes()).thenReturn(new HashMap<>());
        when(context.getApplication()).thenReturn(mock(Application.class));
        when(context.getExternalContext()).thenReturn(mock(ExternalContext.class));
        when(context.getELContext()).thenReturn(mock(ELContext.class));
        currentInstance = mockStatic(FacesContext.class);
        currentInstance.when(FacesContext::getCurrentInstance).thenReturn(context);
    }

    @AfterEach
    public void tearDown() {
        currentInstance.close();
    }

    private <C extends UIComponent> C bindRendered(C component) {
        component.setValueExpression("rendered", rendered);
        return component;
    }

    private void verifyEvaluations(int count) {
        verify(rendered, times(count)).getValue(any(ELContext.class));
    }

    @Test
    public void encodeAllEvaluatesRenderedOnceWithTheComponentAsCurrentComponent() throws IOException {
        UIOutput component = bindRendered(new UIOutput());
        component.setRendererType(null);
        List<UIComponent> currentComponents = new ArrayList<>();
        when(rendered.getValue(any(ELContext.class))).thenAnswer(invocation -> {
            currentComponents.add(UIComponent.getCurrentComponent(context));
            return true;
        });

        component.encodeAll(context);

        assertEquals(List.of(component), currentComponents);
    }

    @ParameterizedTest
    @ValueSource(booleans = { true, false })
    public void renderedIsEvaluatedAnewAfterEncodeAll(boolean value) throws IOException {
        UIOutput component = bindRendered(new UIOutput());
        component.setRendererType(null);
        when(rendered.getValue(any(ELContext.class))).thenReturn(value);

        component.encodeAll(context);
        component.isRendered();

        verifyEvaluations(2);
    }

    @Test
    public void everyEncodeAllEvaluatesRenderedAnew() throws IOException {
        UIOutput component = bindRendered(new UIOutput());
        component.setRendererType(null);
        when(rendered.getValue(any(ELContext.class))).thenReturn(true, false);

        component.encodeAll(context);
        component.encodeAll(context);

        verifyEvaluations(2);
    }

    @Test
    public void componentWhichDoesNotCallSuperEncodeEndKeepsNoCachedValue() throws IOException {
        UIOutput component = bindRendered(new UIOutput() {

            @Override
            public void encodeBegin(FacesContext context) {
            }

            @Override
            public void encodeEnd(FacesContext context) {
            }

        });
        component.setRendererType(null);
        when(rendered.getValue(any(ELContext.class))).thenReturn(true);

        component.encodeAll(context);
        component.isRendered();

        verifyEvaluations(2);
    }

    @Test
    public void encodeAllWhichThrowsKeepsNoCachedValue() {
        UIOutput component = bindRendered(new UIOutput() {

            @Override
            public void encodeBegin(FacesContext context) throws IOException {
                throw new IOException();
            }

        });
        component.setRendererType(null);
        when(rendered.getValue(any(ELContext.class))).thenReturn(true);

        assertThrows(IOException.class, () -> component.encodeAll(context));
        component.isRendered();

        verifyEvaluations(2);
    }

}
