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

import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jakarta.faces.FacesException;
import jakarta.faces.context.FacesContext;

/**
 * The relation of an iterating component, such as {@link UIData} or <code>ui:repeat</code>, with the iterating components it is nested in and the iterating
 * components nested in its rows.
 * <p>
 * Before the owner leaves a row, {@link #park} takes each nested iterating component off its row, so that the nested component saves the state of its own row
 * while its clientId still identifies that row. When the owner enters that row again, {@link #unpark} puts each nested component back on its row, so that it
 * restores the state of that row. A nested component therefore never saves the state of one row as the state of another row.
 * <p>
 * The <code>ui:repeat</code> component is not part of the API. It is recognized by the same class name check as
 * {@link PackageUtils#isNestedInIterator(FacesContext, UIComponent)}, and moved through its public <code>int getRowIndex()</code> and
 * <code>void setRowIndex(int)</code>. One without those methods is not moved.
 */
final class NestedIterators {

    private static final String REPEAT_CLASS_NAME = "UIRepeat";

    /**
     * The <code>getRowIndex()</code> and <code>setRowIndex(int)</code> handles of a <code>ui:repeat</code> class, or {@code null} for any other class.
     */
    private static final ClassValue<MethodHandle[]> REPEAT_ROW_INDEX_HANDLES = new ClassValue<>() {

        @Override
        protected MethodHandle[] computeValue(Class<?> type) {
            if (!type.getName().contains(REPEAT_CLASS_NAME)) {
                return null;
            }
            try {
                MethodHandles.Lookup lookup = MethodHandles.publicLookup();
                return new MethodHandle[] { lookup.findVirtual(type, "getRowIndex", methodType(int.class)),
                    lookup.findVirtual(type, "setRowIndex", methodType(void.class, int.class)) };
            }
            catch (NoSuchMethodException | IllegalAccessException e) {
                return null;
            }
        }

    };

    private final UIComponent owner;
    private Boolean nested;
    private Map<String, Map<UIComponent, Integer>> parked;

    /**
     * Creates the nested iterators of the given iterating component.
     *
     * @param owner the iterating component.
     */
    NestedIterators(UIComponent owner) {
        this.owner = owner;
    }

    /**
     * Returns whether the given component iterates over its children once per row, and can be moved between rows by {@link #park} and {@link #unpark}.
     *
     * @param component the component.
     * @return whether the given component is a {@link UIData} or a <code>ui:repeat</code> with public row index accessors.
     */
    static boolean isIterator(UIComponent component) {
        return component instanceof UIData || REPEAT_ROW_INDEX_HANDLES.get(component.getClass()) != null;
    }

    /**
     * Returns whether any of the given nested iterating components is on a row, so that {@link #park} has something to do.
     *
     * @param iterators the outermost iterating components nested in the rows of the owner.
     * @return whether any of them is on a row.
     */
    static boolean isAnyOnRow(List<UIComponent> iterators) {
        for (UIComponent component : iterators) {
            if (getRowIndex(component) != -1) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns whether the owner is nested in an iterating component. The result is computed once per owner.
     *
     * @param context the faces context.
     * @return whether the owner is nested in an iterating component.
     */
    boolean isNested(FacesContext context) {
        if (nested == null) {
            nested = PackageUtils.isNestedInIterator(context, owner);
        }
        return nested;
    }

    /**
     * Takes each of the given nested iterating components which is on a row off that row, and remembers that row under the given row key of the owner.
     *
     * @param rowKey the key of the row of the owner which is being left.
     * @param iterators the outermost iterating components nested in the rows of the owner.
     */
    void park(String rowKey, List<UIComponent> iterators) {
        Map<UIComponent, Integer> positions = null;
        for (UIComponent component : iterators) {
            int rowIndex = getRowIndex(component);
            if (rowIndex != -1) {
                if (positions == null) {
                    positions = new IdentityHashMap<>();
                }
                positions.put(component, rowIndex);
                setRowIndex(component, -1);
            }
        }
        if (positions != null) {
            if (parked == null) {
                parked = new HashMap<>();
            }
            parked.put(rowKey, positions);
        }
    }

    /**
     * Returns whether any nested iterating component is parked, so that {@link #unpark} has something to do.
     *
     * @return whether any nested iterating component is parked.
     */
    boolean hasParked() {
        return parked != null;
    }

    /**
     * Puts each nested iterating component which was parked under the given row key of the owner back on its row.
     *
     * @param rowKey the key of the row of the owner which is being entered.
     */
    void unpark(String rowKey) {
        if (parked == null) {
            return;
        }
        Map<UIComponent, Integer> positions = parked.remove(rowKey);
        if (parked.isEmpty()) {
            parked = null;
        }
        if (positions != null) {
            for (Entry<UIComponent, Integer> position : positions.entrySet()) {
                setRowIndex(position.getKey(), position.getValue());
            }
        }
    }

    private static int getRowIndex(UIComponent component) {
        if (component instanceof UIData) {
            return ((UIData) component).getRowIndex();
        }
        try {
            return (int) REPEAT_ROW_INDEX_HANDLES.get(component.getClass())[0].invoke(component);
        }
        catch (RuntimeException | Error e) {
            throw e;
        }
        catch (Throwable e) {
            throw new FacesException(e);
        }
    }

    private static void setRowIndex(UIComponent component, int rowIndex) {
        if (component instanceof UIData) {
            ((UIData) component).setRowIndex(rowIndex);
            return;
        }
        try {
            REPEAT_ROW_INDEX_HANDLES.get(component.getClass())[1].invoke(component, rowIndex);
        }
        catch (RuntimeException | Error e) {
            throw e;
        }
        catch (Throwable e) {
            throw new FacesException(e);
        }
    }

}
