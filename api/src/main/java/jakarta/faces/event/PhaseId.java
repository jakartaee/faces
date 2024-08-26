/*
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

package jakarta.faces.event;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import jakarta.faces.FacesException;

/**
 * <p>
 * <span class="changed_modified_2_2 changed_modified_5_0">Enum</span> of the legal values that may be returned by the
 * <code>getPhaseId()</code> method of the {@link FacesEvent} interface.
 * </p>
 */

public enum PhaseId {

    /**
     * <p>
     * Identifier that indicates an interest in events, no matter which request processing phase is being performed.
     * </p>
     */
    ANY_PHASE,

    /**
     * <p>
     * Identifier that indicates an interest in events queued for the <em>Restore View</em> phase of the request processing
     * lifecycle.
     * </p>
     */
    RESTORE_VIEW,
    
    /**
     * <p>
     * Identifier that indicates an interest in events queued for the <em>Apply Request Values</em> phase of the request
     * processing lifecycle.
     * </p>
     */
    APPLY_REQUEST_VALUES,
    
    /**
     * <p>
     * Identifier that indicates an interest in events queued for the <em>Process Validations</em> phase of the request
     * processing lifecycle.
     * </p>
     */
    PROCESS_VALIDATIONS,
    
    /**
     * <p>
     * Identifier that indicates an interest in events queued for the <em>Update Model Values</em> phase of the request
     * processing lifecycle.
     * </p>
     */
    UPDATE_MODEL_VALUES,
    
    /**
     * <p>
     * Identifier that indicates an interest in events queued for the <em>Invoke Application</em> phase of the request
     * processing lifecycle.
     * </p>
     */
    INVOKE_APPLICATION,
    
    /**
     * <p>
     * Identifier for the <em>Render Response</em> phase of the request processing lifecycle.
     * </p>
     */
    RENDER_RESPONSE;

    /**
     * <p>
     * Return the ordinal value of this {@link PhaseId} instance.
     * </p>
     *
     * @return the ordinal
     */
    public int getOrdinal() {
        return ordinal();
    }

    /**
     * <p>
     * Return a String representation of this {@link PhaseId} instance.
     * </p>
     */
    @Override
    public String toString() {
        return name() + ' ' + getOrdinal();
    }

    /**
     * <p class="changed_added_2_2">
     * Return the name of this phase.
     * </p>
     *
     * @since 2.2
     *
     * @return the name
     */

    public String getName() {
        return this == ANY_PHASE ? "ANY" : name();
    }

    /**
     * <p class="changed_added_2_2">
     * Return a <code>PhaseId</code> representation of the argument <code>phase</code>.
     * </p>
     *
     * @param phase the String for which the corresponding <code>PhaseId</code> should be returned.
     *
     * @throws NullPointerException if argument <code>phase</code> is <code>null</code>.
     *
     * @throws FacesException if the <code>PhaseId</code> corresponding to the argument <code>phase</code> cannot be found.
     *
     * @since 2.2
     *
     * @return the phase id corresponding to the argument {@code phase}
     */

    public static PhaseId phaseIdValueOf(String phase) {
        Objects.requireNonNull(phase);

        final PhaseId result = VALUES_BY_NAME.get( phase.toUpperCase() );

        if ( result == null) {
            throw new FacesException("Not a valid phase [" + phase + "]");
        }

        return result;
    }

    // ------------------------------------------------------ Create Instances

    /**
     * <p>
     * List of valid {@link PhaseId} instances, in ascending order of their ordinal value.
     * </p>
     */
    public static final List<PhaseId> VALUES = List.of(values());

    /**
     * <p>
     * Valid {@link PhaseId} instances, mapped by their uppercase name
     * </p>
     */
    private static final Map<String,PhaseId> VALUES_BY_NAME = VALUES.stream().collect(toUnmodifiableMap(PhaseId::name, identity()));

}
