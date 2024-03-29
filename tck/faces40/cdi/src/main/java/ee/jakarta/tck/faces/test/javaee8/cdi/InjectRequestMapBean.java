/*
 * Copyright (c) 2021, 2022 Contributors to Eclipse Foundation.
 * Copyright (c) 2017, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.javaee8.cdi;

import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.spi.CDI;
import jakarta.enterprise.util.TypeLiteral;
import jakarta.faces.annotation.RequestMap;
import jakarta.inject.Named;

@Named
@RequestScoped
public class InjectRequestMapBean {

    public String getRequestValue() {
        Map<String, Object> requestMap = CDI.current().select(new TypeLiteral<Map<String, Object>>() {}, RequestMap.Literal.INSTANCE).get();

        return (String) requestMap.get("fooAttribute");
    }

}
