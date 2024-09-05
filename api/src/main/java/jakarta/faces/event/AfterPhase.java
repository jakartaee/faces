/*
 * Copyright (c) 2024 Contributors to the Eclipse Foundation
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

import static jakarta.faces.event.PhaseId.ANY_PHASE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.enterprise.util.AnnotationLiteral;
import jakarta.inject.Qualifier;

/**
 * <p class="changed_added_5_0">
 * This qualifier allows you to observe after phase events via CDI.
 * The CDI event must be fired synchronously after invocation of all associated phase listeners.
 * </p>
 * @since 5.0
 * @see BeforePhase
 */
@Qualifier
@Target(PARAMETER)
@Retention(RUNTIME)
@Documented
public @interface AfterPhase {

    /**
     * <p>
     * The phase ID on which the after phase event needs to be observed. Defaults to {@link PhaseId#ANY_PHASE}.
     * </p>
     */
    PhaseId value() default ANY_PHASE;

    /**
     * <p>
     * Supports inline instantiation of the {@link AfterPhase} qualifier.
     * </p>
     */
    public static final class Literal extends AnnotationLiteral<AfterPhase> implements AfterPhase {

        private static final long serialVersionUID = 1L;

        /**
         * Default instance of the {@link AfterPhase} qualifier.
         */
        public static final Literal INSTANCE = of(ANY_PHASE);

        private final PhaseId value;

        public static Literal of(PhaseId value) {
            return new Literal(value);
        }

        private Literal(PhaseId value) {
            this.value = value;
        }

        @Override
        public PhaseId value() {
            return value;
        }
    }
}