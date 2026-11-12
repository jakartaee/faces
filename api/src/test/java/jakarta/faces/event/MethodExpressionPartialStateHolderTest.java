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

package jakarta.faces.event;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;

import jakarta.el.MethodExpression;
import jakarta.faces.component.StateHolder;
import jakarta.faces.component.UICommand;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.UIComponentBase;
import jakarta.faces.component.UIInput;
import jakarta.faces.context.FacesContext;
import jakarta.faces.validator.MethodExpressionValidator;
import jakarta.faces.validator.Validator;

import org.junit.jupiter.api.Test;

/**
 * {@link MethodExpressionActionListener}, {@link MethodExpressionValueChangeListener} and {@link MethodExpressionValidator} leave no state in a component whose
 * initial state is marked, and keep their full state whenever the component saves its full state.
 */
class MethodExpressionPartialStateHolderTest {

    private final FacesContext context = mock(FacesContext.class);

    @Test
    void markedCommandSavesNoActionListenerState() {
        UICommand command = new UICommand();
        command.addActionListener(new MethodExpressionActionListener(mock(MethodExpression.class), mock(MethodExpression.class)));
        command.markInitialState();

        assertNull(command.saveState(context));
    }

    @Test
    void markedInputSavesNoValueChangeListenerNorValidatorState() {
        UIInput input = new UIInput();
        input.addValueChangeListener(new MethodExpressionValueChangeListener(mock(MethodExpression.class), mock(MethodExpression.class)));
        input.addValidator(new MethodExpressionValidator(mock(MethodExpression.class)));
        input.markInitialState();

        assertNull(input.saveState(context));
    }

    @Test
    void clearedActionListenerSavesFullState() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        MethodExpressionActionListener listener = new MethodExpressionActionListener(oneArg, zeroArg);
        listener.markInitialState();
        listener.clearInitialState();

        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) listener.saveState(context));
    }

    @Test
    void clearedValueChangeListenerSavesFullState() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        MethodExpressionValueChangeListener listener = new MethodExpressionValueChangeListener(oneArg, zeroArg);
        listener.markInitialState();
        listener.clearInitialState();

        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) listener.saveState(context));
    }

    @Test
    void clearedValidatorSavesFullState() {
        MethodExpression expression = mock(MethodExpression.class);
        MethodExpressionValidator validator = new MethodExpressionValidator(expression);
        validator.markInitialState();
        validator.clearInitialState();

        assertArrayEquals(new Object[] { expression }, (Object[]) validator.saveState(context));
    }

    @Test
    void restoreNullStateKeepsExpressions() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        MethodExpressionActionListener listener = new MethodExpressionActionListener(oneArg, zeroArg);
        listener.markInitialState();
        listener.restoreState(context, null);
        assertNull(listener.saveState(context));
        listener.clearInitialState();

        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) listener.saveState(context));
    }

    @Test
    void actionListenerAddedAfterMarkingIsRestoredFromFullState() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        UICommand command = new UICommand();
        command.markInitialState();
        command.addActionListener(new MethodExpressionActionListener(oneArg, zeroArg));
        Object state = command.saveState(context);
        assertNotNull(state);

        UICommand restored = new UICommand();
        restored.restoreState(context, state);

        ActionListener[] listeners = restored.getActionListeners();
        assertEquals(1, listeners.length);
        MethodExpressionActionListener listener = assertInstanceOf(MethodExpressionActionListener.class, listeners[0]);
        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) listener.saveState(context));
    }

    @Test
    void valueChangeListenerAddedAfterMarkingIsRestoredFromFullState() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        UIInput input = new UIInput();
        input.markInitialState();
        input.addValueChangeListener(new MethodExpressionValueChangeListener(oneArg, zeroArg));
        Object state = input.saveState(context);
        assertNotNull(state);

        UIInput restored = new UIInput();
        restored.restoreState(context, state);

        ValueChangeListener<?>[] listeners = restored.getValueChangeListeners();
        assertEquals(1, listeners.length);
        MethodExpressionValueChangeListener listener = assertInstanceOf(MethodExpressionValueChangeListener.class, listeners[0]);
        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) listener.saveState(context));
    }

    @Test
    void transientActionListenerIsNotRestored() {
        UICommand command = new UICommand();
        MethodExpressionActionListener listener = new MethodExpressionActionListener(mock(MethodExpression.class), mock(MethodExpression.class));
        listener.setTransient(true);
        command.addActionListener(listener);
        Object state = command.saveState(context);

        UICommand restored = new UICommand();
        restored.restoreState(context, state);

        assertEquals(0, restored.getActionListeners().length);
    }

    @Test
    void clearedListenerRoundTripsThroughAttachedState() {
        MethodExpression oneArg = mock(MethodExpression.class);
        MethodExpression zeroArg = mock(MethodExpression.class);
        MethodExpressionActionListener listener = new MethodExpressionActionListener(oneArg, zeroArg);
        listener.markInitialState();
        listener.clearInitialState();

        Object restored = UIComponentBase.restoreAttachedState(context, UIComponentBase.saveAttachedState(context, listener));

        MethodExpressionActionListener restoredListener = assertInstanceOf(MethodExpressionActionListener.class, restored);
        assertArrayEquals(new Object[] { oneArg, zeroArg }, (Object[]) restoredListener.saveState(context));
    }

    @Test
    void markedValidatorKeepsItsExpressionWhenSiblingStateIsRestored() {
        MethodExpression expression = mock(MethodExpression.class);
        Object state = buildMarkedInput(expression, "changed").saveState(context);
        assertNotNull(state);

        UIInput rebuilt = buildMarkedInput(expression, "built");
        rebuilt.restoreState(context, state);

        Validator<?>[] validators = rebuilt.getValidators();
        MethodExpressionValidator validator = assertInstanceOf(MethodExpressionValidator.class, validators[0]);
        validator.clearInitialState();
        assertArrayEquals(new Object[] { expression }, (Object[]) validator.saveState(context));
        assertEquals("changed", ((StatefulValidator) validators[1]).config);
    }

    private static UIInput buildMarkedInput(MethodExpression expression, String siblingConfig) {
        UIInput input = new UIInput();
        input.addValidator(new MethodExpressionValidator(expression));
        StatefulValidator sibling = new StatefulValidator();
        input.addValidator(sibling);
        input.markInitialState();
        sibling.config = siblingConfig;
        return input;
    }

    /**
     * A validator implementing only {@link StateHolder}, which therefore always saves its state.
     */
    public static class StatefulValidator implements Validator<Object>, StateHolder {

        String config = "built";

        @Override
        public void validate(FacesContext context, UIComponent component, Object value) {
        }

        @Override
        public Object saveState(FacesContext context) {
            return config;
        }

        @Override
        public void restoreState(FacesContext context, Object state) {
            config = (String) state;
        }

        @Override
        public boolean isTransient() {
            return false;
        }

        @Override
        public void setTransient(boolean newTransientValue) {
        }

    }

}
