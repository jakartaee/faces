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
package ee.jakarta.tck.faces.faces20.appconfigresources.startupbehavior;

import jakarta.faces.validator.LengthValidator;

/**
 * Validator registered by an application configuration resource which is only reachable via
 * {@code jakarta.faces.CONFIG_FILES}.
 */
public class StartupBehaviorConfigValidator extends LengthValidator {
    // Behaviour is irrelevant, the test only asserts that it got registered.
}
