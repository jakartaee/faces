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

package ee.jakarta.tck.faces.faces23.facelets;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.html.HtmlDataTable;
import jakarta.inject.Named;

/**
 * Backs a data table bound via {@code binding} on a page that is requested more than once within one
 * session. Session scope is essential: on the second GET the binding property still holds the
 * component instance of the previous view, so building the fresh view must overwrite that instance
 * rather than reuse it. The row data is fixed so both renders are expected to be identical.
 */
@Named
@SessionScoped
public class Issue4146Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Issue4146Car> cars = List.of(
        new Issue4146Car("a1b2c3d4", 1998, "BMW", "Black"),
        new Issue4146Car("b2c3d4e5", 2003, "Mercedes", "White"),
        new Issue4146Car("c3d4e5f6", 2007, "Volvo", "Green"),
        new Issue4146Car("d4e5f6a7", 2011, "Audi", "Red"),
        new Issue4146Car("e5f6a7b8", 2015, "Renault", "Blue"),
        new Issue4146Car("f6a7b8c9", 2019, "Fiat", "Orange"));

    // The component instance of the previously rendered view survives here in between two GETs of
    // the same view -- the condition the test exercises.
    private transient HtmlDataTable carsDataTable;

    public List<Issue4146Car> getCars() {
        return cars;
    }

    public HtmlDataTable getCarsDataTable() {
        return carsDataTable;
    }

    public void setCarsDataTable(HtmlDataTable carsDataTable) {
        this.carsDataTable = carsDataTable;
    }
}
