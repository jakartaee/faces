/*
 * Copyright (c) 2026 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.faces20.state_serialization_disabled;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec1127Bean {

    public void putNonSerializableDataInState(ActionEvent event) {
        event.getComponent().getAttributes().put("nonSerializable", new NotReallySerializable());
    }

    /**
     * Declares Serializable so that it may be put into the component attribute map, but refuses to serialize, which is what distinguishes a runtime which
     * serializes the server side state from one which does not.
     */
    private static class NotReallySerializable implements Serializable {

        private static final long serialVersionUID = 1L;

        private void writeObject(ObjectOutputStream out) throws IOException {
            throw new NotSerializableException("This class is not really serializable.");
        }

        private void readObject(ObjectInputStream in) throws IOException {
            throw new NotSerializableException("This class is not really serializable.");
        }

    }

}
