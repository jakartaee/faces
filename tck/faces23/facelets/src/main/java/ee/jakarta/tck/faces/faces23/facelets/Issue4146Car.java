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

/**
 * Row of the bound data table of {@link Issue4146Bean}.
 */
public class Issue4146Car implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final int year;
    private final String brand;
    private final String color;

    public Issue4146Car(String id, int year, String brand, String color) {
        this.id = id;
        this.year = year;
        this.brand = brand;
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getBrand() {
        return brand;
    }

    public String getColor() {
        return color;
    }
}
