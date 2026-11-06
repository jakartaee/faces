/*
 * Copyright (c) 2024, 2026 Contributors to the Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;

/**
 * The per-row state of a stateful descendant of {@link UIData}. It serializes only the fields which are not at their default.
 */
class SavedState implements Serializable {

    private static final long serialVersionUID = 2L;
    private static final ObjectStreamField[] serialPersistentFields = {};
    private static final int SUBMITTED_VALUE = 1;
    private static final int VALUE = 2;
    private static final int LOCAL_VALUE_SET = 4;
    private static final int INVALID = 8;
    private static final int SUBMITTED = 16;

    private Object submittedValue;
    private boolean submitted;

    Object getSubmittedValue() {
        return submittedValue;
    }

    void setSubmittedValue(Object submittedValue) {
        this.submittedValue = submittedValue;
    }

    private boolean valid = true;

    boolean isValid() {
        return valid;
    }

    void setValid(boolean valid) {
        this.valid = valid;
    }

    private Object value;

    Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    private boolean localValueSet;

    boolean isLocalValueSet() {
        return localValueSet;
    }

    public void setLocalValueSet(boolean localValueSet) {
        this.localValueSet = localValueSet;
    }

    public boolean getSubmitted() {
        return submitted;
    }

    public void setSubmitted(boolean submitted) {
        this.submitted = submitted;
    }

    public boolean hasDeltaState() {
        return submittedValue != null || value != null || localValueSet || !valid || submitted;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        int fields = (submittedValue != null ? SUBMITTED_VALUE : 0) | (value != null ? VALUE : 0) | (localValueSet ? LOCAL_VALUE_SET : 0)
            | (valid ? 0 : INVALID) | (submitted ? SUBMITTED : 0);
        out.writeByte(fields);
        if (submittedValue != null) {
            out.writeObject(submittedValue);
        }
        if (value != null) {
            out.writeObject(value);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        int fields = in.readByte();
        submittedValue = (fields & SUBMITTED_VALUE) != 0 ? in.readObject() : null;
        value = (fields & VALUE) != 0 ? in.readObject() : null;
        localValueSet = (fields & LOCAL_VALUE_SET) != 0;
        valid = (fields & INVALID) == 0;
        submitted = (fields & SUBMITTED) != 0;
    }

    @Override
    public String toString() {
        return "submittedValue: " + submittedValue + " value: " + value + " localValueSet: " + localValueSet;
    }

}
