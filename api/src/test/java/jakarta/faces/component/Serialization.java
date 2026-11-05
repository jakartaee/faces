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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Java serialization round trips for tests.
 */
final class Serialization {

    private Serialization() {
    }

    /**
     * @param <T> the type of the object
     * @param object the object to serialize
     * @return a deserialized copy of the given object
     * @throws IOException if serializing or deserializing fails
     * @throws ClassNotFoundException if a serialized class cannot be loaded
     */
    @SuppressWarnings("unchecked")
    public static <T> T serializeAndDeserialize(T object) throws IOException, ClassNotFoundException {
        return (T) deserialize(serialize(object));
    }

    /**
     * @param object the object to serialize
     * @return the serialized bytes of the given object
     * @throws IOException if serializing fails
     */
    private static byte[] serialize(Object object) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (ObjectOutputStream out = new ObjectOutputStream(bytes)) {
            out.writeObject(object);
        }
        return bytes.toByteArray();
    }

    /**
     * @param serialized the serialized bytes of an object
     * @return the deserialized object
     * @throws IOException if deserializing fails
     * @throws ClassNotFoundException if a serialized class cannot be loaded
     */
    private static Object deserialize(byte[] serialized) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(serialized))) {
            return in.readObject();
        }
    }

}
