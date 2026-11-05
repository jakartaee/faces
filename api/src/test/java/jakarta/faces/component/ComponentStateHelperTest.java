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

import static java.util.Comparator.comparing;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.el.ValueExpression;
import jakarta.faces.component.html.HtmlOutputText;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;

import org.junit.jupiter.api.Test;

import com.example.faces.NonStandardComponent;

/**
 * Restoring full state runs no setters, so the helper re-records the attributes a standard component's generated setters would have tracked. Which components
 * those are is decided by the component's own package, the same test {@code HtmlComponentUtils.handleAttribute} and {@code RenderKitUtils.getAttributeIfSet}
 * apply on the write and read sides.
 */
class ComponentStateHelperTest {

    @Test
    void restoringAComponentFromOutsideTheStandardPackageDoesNotStartTrackingItsAttributes() {
        FacesContext context = mock(FacesContext.class);

        NonStandardComponent saved = new NonStandardComponent();
        saved.setRendered(false);
        Object state = saved.getStateHelper().saveState(context);

        NonStandardComponent restored = new NonStandardComponent();
        restored.getStateHelper().restoreState(context, state);

        assertNull(restored.getAttributes().get(PackageUtils.ATTRIBUTES_THAT_ARE_SET));
    }

    @Test
    void restoringAStandardComponentTracksTheAttributesItsSettersWouldHave() {
        FacesContext context = mock(FacesContext.class);

        HtmlPanelGroup saved = new HtmlPanelGroup();
        saved.setStyleClass("cell");
        Object state = saved.getStateHelper().saveState(context);

        HtmlPanelGroup restored = new HtmlPanelGroup();
        restored.getStateHelper().restoreState(context, state);

        assertTrackedAttributes(restored, "styleClass");
    }

    /**
     * Rendering emits the tracked attributes in the order of this list, so restoring full state must record them where the setters would have. Otherwise the
     * same component renders its attributes in one order when the view builds it and another when a postback restores it.
     * <p>
     * The saved entries are replayed in the iteration order of the state map, so the tracked list may be rebuilt from the individual properties before the
     * saved copy of the list itself is merged in. This moves that copy last to exercise that path, which is otherwise reached or not depending on where the key
     * happens to hash.
     */
    @Test
    void restoringFullStateRecordsTheAttributesInTheOrderTheSettersWouldHave() {
        FacesContext context = mock(FacesContext.class);

        HtmlOutputText built = new HtmlOutputText();
        built.setTitle("t");
        built.setDir("ltr");
        built.setStyle("color:#000");
        built.setLang("en");

        HtmlOutputText restored = new HtmlOutputText();
        restored.getStateHelper().restoreState(context, trackedListLast(built.getStateHelper().saveState(context)));

        assertEquals(trackedAttributes(built), trackedAttributes(restored));
    }

    /**
     * The delta of an attribute set after the build carries the attribute only: the restore records it in the attributesThatAreSet list again.
     */
    @Test
    void anAttributeSetAfterTheBuildSavesNoAttributesThatAreSet() {
        FacesContext context = mock(FacesContext.class);

        HtmlOutputText saved = new HtmlOutputText();
        saved.markInitialState();
        saved.setStyleClass("changed");
        Object[] delta = (Object[]) saved.getStateHelper().saveState(context);

        assertFalse(Arrays.asList(delta).contains(UIComponent.PropertyKeysPrivate.attributesThatAreSet), Arrays.deepToString(delta));

        HtmlOutputText restored = new HtmlOutputText();
        restored.markInitialState();
        restored.getStateHelper().restoreState(context, delta);

        assertEquals("changed", restored.getStyleClass());
        assertTrackedAttributes(restored, "styleClass");
    }

    /**
     * An attribute bound after the build, following another one set after the build, is recorded once in the attributesThatAreSet list, before and after a
     * restore. A name recorded twice renders its HTML attribute twice.
     */
    @Test
    void anAttributeBoundAfterAnAttributeSetAfterTheBuildIsRecordedOnce() {
        FacesContext context = mock(FacesContext.class);

        HtmlOutputText saved = new HtmlOutputText();
        saved.markInitialState();
        saved.setStyleClass("changed");
        saved.setValueExpression("title", nonLiteral());
        assertTrackedAttributes(saved, "styleClass", "title");

        HtmlOutputText restored = new HtmlOutputText();
        restored.markInitialState();
        restored.getStateHelper().restoreState(context, saved.getStateHelper().saveState(context));

        assertTrackedAttributes(restored, "styleClass", "title");
    }

    /**
     * An attribute set and an attribute bound after the build, and both cleared again, are recorded neither in the attributesThatAreSet list nor in it after a
     * restore.
     */
    @Test
    void attributesClearedAfterTheBuildAreNotRecorded() {
        FacesContext context = mock(FacesContext.class);

        HtmlOutputText saved = new HtmlOutputText();
        saved.markInitialState();
        saved.setStyleClass("changed");
        saved.setValueExpression("title", nonLiteral());
        saved.setStyleClass(null);
        saved.setValueExpression("title", null);
        assertTrackedAttributes(saved);

        HtmlOutputText restored = new HtmlOutputText();
        restored.markInitialState();
        restored.getStateHelper().restoreState(context, saved.getStateHelper().saveState(context));

        assertTrackedAttributes(restored);
    }

    /**
     * Only a standard component records each restored property in the attributesThatAreSet list again, so the list created after the build of any other
     * component is part of its delta.
     */
    @Test
    void theAttributesThatAreSetOfANonStandardComponentAreSaved() {
        FacesContext context = mock(FacesContext.class);

        NonStandardComponent saved = new NonStandardComponent();
        saved.markInitialState();
        saved.getAttributes().put(PackageUtils.ATTRIBUTES_THAT_ARE_SET, new ArrayList<>(List.of("title")));
        Object[] delta = (Object[]) saved.getStateHelper().saveState(context);

        assertNotNull(delta);
        assertTrue(Arrays.asList(delta).contains(UIComponent.PropertyKeysPrivate.attributesThatAreSet), Arrays.deepToString(delta));
    }

    private static ValueExpression nonLiteral() {
        ValueExpression expression = mock(ValueExpression.class);
        when(expression.isLiteralText()).thenReturn(false);
        return expression;
    }

    /**
     * Returns the saved state with the {@code attributesThatAreSet} entry moved to the end, so restoring replays every property before it.
     */
    private static Object[] trackedListLast(Object state) {
        Object[] saved = (Object[]) state;
        List<Object[]> entries = new ArrayList<>();

        for (int i = 0; i < (saved.length - 1) / 2; i++) {
            entries.add(new Object[] { saved[i * 2], saved[i * 2 + 1] });
        }

        entries.sort(comparing(entry -> UIComponent.PropertyKeysPrivate.attributesThatAreSet.equals(entry[0])));

        Object[] reordered = new Object[saved.length];
        for (int i = 0; i < entries.size(); i++) {
            reordered[i * 2] = entries.get(i)[0];
            reordered[i * 2 + 1] = entries.get(i)[1];
        }
        reordered[saved.length - 1] = saved[saved.length - 1];
        return reordered;
    }

    private static void assertTrackedAttributes(UIComponent component, String... expected) {
        assertEquals(List.of(expected), trackedAttributes(component));
    }

    @SuppressWarnings("unchecked")
    private static List<String> trackedAttributes(UIComponent component) {
        List<String> tracked = (List<String>) component.getAttributes().get(PackageUtils.ATTRIBUTES_THAT_ARE_SET);
        return tracked == null ? List.of() : tracked;
    }

}
