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

package ee.jakarta.tck.faces.faces23.passthrough;

import java.time.LocalDate;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue3212Bean {

    public static class Entity {

        private final String name;
        private final LocalDate modifiedOn;

        public Entity(String name, LocalDate modifiedOn) {
            this.name = name;
            this.modifiedOn = modifiedOn;
        }

        public String getName() {
            return name;
        }

        public LocalDate getModifiedOn() {
            return modifiedOn;
        }
    }

    private final List<Entity> entities = List.of(
        new Entity("first", LocalDate.of(2026, 1, 1)),
        new Entity("second", LocalDate.of(2026, 2, 2)),
        new Entity("third", LocalDate.of(2026, 3, 3)));

    public List<Entity> getEntities() {
        return entities;
    }
}
