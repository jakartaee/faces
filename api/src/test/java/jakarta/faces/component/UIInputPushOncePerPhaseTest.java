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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import jakarta.faces.application.Application;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * An input component is pushed as the current component exactly once per request processing lifecycle phase, which covers the <code>rendered</code> check, the
 * processing of its facets and children and its own decode, validation or model update.
 */
public class UIInputPushOncePerPhaseTest {

    private FacesContext context;
    private int pushCount;

    @BeforeEach
    public void setUp() {
        context = mock(FacesContext.class);
        when(context.getAttributes()).thenReturn(new HashMap<>());
        when(context.getApplication()).thenReturn(mock(Application.class));
        when(context.getExternalContext()).thenReturn(mock(ExternalContext.class));
        pushCount = 0;
    }

    private UIInput input() {
        UIInput input = new UIInput() {

            @Override
            public void pushComponentToEL(FacesContext context, UIComponent component) {
                if (component == null || component == this) {
                    pushCount++;
                }
                super.pushComponentToEL(context, component);
            }

        };
        input.setRendererType(null);
        return input;
    }

    @Test
    public void processDecodesPushesOnce() {
        input().processDecodes(context);
        assertEquals(1, pushCount);
    }

    @Test
    public void immediateProcessDecodesPushesOnce() {
        UIInput input = input();
        input.setImmediate(true);
        input.processDecodes(context);
        assertEquals(1, pushCount);
    }

    @Test
    public void processValidatorsPushesOnce() {
        input().processValidators(context);
        assertEquals(1, pushCount);
    }

    @Test
    public void processUpdatesPushesOnce() {
        input().processUpdates(context);
        assertEquals(1, pushCount);
    }

    @Test
    public void viewParameterProcessValidatorsPushesOnce() {
        UIViewParameter viewParameter = new UIViewParameter() {

            @Override
            public void pushComponentToEL(FacesContext context, UIComponent component) {
                if (component == null || component == this) {
                    pushCount++;
                }
                super.pushComponentToEL(context, component);
            }

        };
        viewParameter.setId("viewParameter");
        viewParameter.setRequired(true);
        viewParameter.setRequiredMessage("required");
        viewParameter.processValidators(context);
        assertEquals(1, pushCount);
    }

    @Test
    public void viewParameterWithSubmittedValueProcessValidatorsPushesOnce() {
        UIViewParameter viewParameter = new UIViewParameter() {

            @Override
            public void pushComponentToEL(FacesContext context, UIComponent component) {
                if (component == null || component == this) {
                    pushCount++;
                }
                super.pushComponentToEL(context, component);
            }

            @Override
            public void validate(FacesContext context) {
                // Not under test, and a real conversion needs the RenderKitFactory.
            }

        };
        viewParameter.setSubmittedValue("value");
        viewParameter.processValidators(context);
        assertEquals(1, pushCount);
    }

}
