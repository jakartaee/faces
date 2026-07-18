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

package ee.jakarta.tck.faces.faces22.phase_listener_default;

import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;

public class Spec1216ExceptionHandlerFactory extends ExceptionHandlerFactory {

    public Spec1216ExceptionHandlerFactory(ExceptionHandlerFactory parent) {
        super(parent);
    }

    @Override
    public ExceptionHandler getExceptionHandler() {
        return new Spec1216ExceptionHandler(getWrapped().getExceptionHandler());
    }
}
