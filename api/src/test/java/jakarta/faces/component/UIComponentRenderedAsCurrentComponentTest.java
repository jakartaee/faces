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

import static jakarta.faces.component.visit.VisitHint.SKIP_UNRENDERED;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.faces.application.Application;
import jakarta.faces.component.visit.VisitContext;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The <code>rendered</code> property of a component must be evaluated while that component is the current component, so that a <code>rendered</code> expression
 * referring to <code>#{component}</code> resolves to the component itself and not to its parent. When the component is not rendered, the parent must be the
 * current component again afterwards.
 */
public class UIComponentRenderedAsCurrentComponentTest {

    private FacesContext context;
    private UIPanel parent;
    private List<UIComponent> currentComponentsSeenByIsRendered;

    @FunctionalInterface
    private interface Phase<C extends UIComponent> {

        void process(C component) throws IOException;

    }

    @BeforeEach
    public void setUp() {
        context = mock(FacesContext.class);
        when(context.getAttributes()).thenReturn(new HashMap<>());
        when(context.getApplication()).thenReturn(mock(Application.class));
        when(context.getExternalContext()).thenReturn(mock(ExternalContext.class));
        parent = new UIPanel();
        parent.setId("parent");
        currentComponentsSeenByIsRendered = new ArrayList<>();
        parent.pushComponentToEL(context, null);
    }

    private boolean recordCurrentComponent() {
        currentComponentsSeenByIsRendered.add(UIComponent.getCurrentComponent(context));
        return false;
    }

    private <C extends UIComponent> void assertRenderedSeesItselfAsCurrent(C component, Phase<C> phase) throws IOException {
        parent.getChildren().add(component);
        phase.process(component);
        assertEquals(Set.of(component), new HashSet<>(currentComponentsSeenByIsRendered));
        assertSame(parent, UIComponent.getCurrentComponent(context));
    }

    private UIOutput output() {
        return new UIOutput() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
    }

    private UIInput input() {
        return new UIInput() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
    }

    private UIData data() {
        return new UIData() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
    }

    private VisitContext skipUnrenderedVisitContext() {
        VisitContext visitContext = mock(VisitContext.class);
        when(visitContext.getFacesContext()).thenReturn(context);
        when(visitContext.getHints()).thenReturn(EnumSet.of(SKIP_UNRENDERED));
        when(visitContext.getSubtreeIdsToVisit(any())).thenReturn(emptyList());
        return visitContext;
    }

    @Test
    public void processDecodesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> c.processDecodes(context));
    }

    @Test
    public void processValidatorsEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> c.processValidators(context));
    }

    @Test
    public void processUpdatesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> c.processUpdates(context));
    }

    @Test
    public void encodeAllEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> c.encodeAll(context));
    }

    @Test
    public void encodeBeginEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> {
            c.encodeBegin(context);
            c.encodeEnd(context);
        });
    }

    @Test
    public void visitTreeEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(output(), c -> c.visitTree(skipUnrenderedVisitContext(), (visitContext, target) -> null));
    }

    @Test
    public void inputProcessDecodesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(input(), c -> c.processDecodes(context));
    }

    @Test
    public void inputProcessValidatorsEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(input(), c -> c.processValidators(context));
    }

    @Test
    public void inputProcessUpdatesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(input(), c -> c.processUpdates(context));
    }

    @Test
    public void viewParameterProcessValidatorsEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        UIViewParameter viewParameter = new UIViewParameter() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
        assertRenderedSeesItselfAsCurrent(viewParameter, c -> c.processValidators(context));
    }

    @Test
    public void dataProcessDecodesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(data(), c -> c.processDecodes(context));
    }

    @Test
    public void dataProcessValidatorsEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(data(), c -> c.processValidators(context));
    }

    @Test
    public void dataProcessUpdatesEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(data(), c -> c.processUpdates(context));
    }

    @Test
    public void dataVisitTreeEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        assertRenderedSeesItselfAsCurrent(data(), c -> c.visitTree(skipUnrenderedVisitContext(), (visitContext, target) -> null));
    }

    @Test
    public void formVisitTreeEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        UIForm form = new UIForm() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
        assertRenderedSeesItselfAsCurrent(form, c -> c.visitTree(skipUnrenderedVisitContext(), (visitContext, target) -> null));
    }

    @Test
    public void namingContainerVisitTreeEvaluatesRenderedWithComponentAsCurrent() throws IOException {
        UINamingContainer namingContainer = new UINamingContainer() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
        assertRenderedSeesItselfAsCurrent(namingContainer, c -> c.visitTree(skipUnrenderedVisitContext(), (visitContext, target) -> null));
    }

    @Test
    public void dataEvaluatesColumnRenderedWithColumnAsCurrent() throws IOException {
        UIData data = new UIData();
        data.setId("data");
        data.setRendererType(null);
        UIColumn column = new UIColumn() {

            @Override
            public boolean isRendered() {
                return recordCurrentComponent();
            }

        };
        data.getChildren().add(column);
        parent.getChildren().add(data);

        data.processDecodes(context);

        assertEquals(List.of(column), currentComponentsSeenByIsRendered);
    }

    @Test
    public void dataEvaluatesColumnChildRenderedWithColumnChildAsCurrent() throws IOException {
        UIData data = new UIData();
        data.setId("data");
        data.setRendererType(null);
        data.setValue(List.of("row"));
        UIColumn column = new UIColumn();
        UIOutput columnChild = output();
        column.getChildren().add(columnChild);
        data.getChildren().add(column);
        parent.getChildren().add(data);

        data.processDecodes(context);

        assertEquals(List.of(columnChild), currentComponentsSeenByIsRendered);
        assertSame(parent, UIComponent.getCurrentComponent(context));
    }

    @Test
    public void dataVisitTreeRestoresCurrentComponentWhenRenderedThrows() {
        UIData data = new UIData() {

            @Override
            public boolean isRendered() {
                throw new IllegalStateException();
            }

        };
        parent.getChildren().add(data);

        assertThrows(IllegalStateException.class, () -> data.visitTree(skipUnrenderedVisitContext(), (visitContext, target) -> null));
        assertSame(parent, UIComponent.getCurrentComponent(context));
    }

}
