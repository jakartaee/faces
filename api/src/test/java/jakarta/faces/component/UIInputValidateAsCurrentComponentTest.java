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
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Validation of an input component must run while that component is the current component, so that an expression referring to <code>#{component}</code>, for
 * example in a <code>requiredMessage</code>, <code>label</code> or validator attribute, resolves to the input component itself and not to its parent.
 */
public class UIInputValidateAsCurrentComponentTest {

    private FacesContext context;
    private UIPanel parent;
    private List<UIComponent> currentComponentsSeenByValidation;

    @BeforeEach
    public void setUp() {
        context = mock(FacesContext.class);
        when(context.getAttributes()).thenReturn(new HashMap<>());
        when(context.getApplication()).thenReturn(mock(Application.class));
        parent = new UIPanel();
        parent.setId("parent");
        currentComponentsSeenByValidation = new ArrayList<>();
        parent.pushComponentToEL(context, null);
    }

    private void recordCurrentComponent() {
        currentComponentsSeenByValidation.add(UIComponent.getCurrentComponent(context));
    }

    @Test
    public void immediateProcessDecodesValidatesWithComponentAsCurrent() {
        UIInput input = new UIInput() {

            @Override
            public void validate(FacesContext context) {
                recordCurrentComponent();
            }

        };
        input.setRendererType(null);
        input.setImmediate(true);
        parent.getChildren().add(input);

        input.processDecodes(context);

        assertEquals(List.of(input), currentComponentsSeenByValidation);
        assertSame(parent, UIComponent.getCurrentComponent(context));
    }

    @Test
    public void viewParameterRequiredCheckEvaluatesRequiredMessageWithComponentAsCurrent() {
        UIViewParameter viewParameter = new UIViewParameter() {

            @Override
            public String getRequiredMessage() {
                recordCurrentComponent();
                return "required";
            }

        };
        viewParameter.setId("viewParameter");
        viewParameter.setRequired(true);
        parent.getChildren().add(viewParameter);

        viewParameter.processValidators(context);

        assertEquals(List.of(viewParameter), currentComponentsSeenByValidation);
        assertSame(parent, UIComponent.getCurrentComponent(context));
    }

}
