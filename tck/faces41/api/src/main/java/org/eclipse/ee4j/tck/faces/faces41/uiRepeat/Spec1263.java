/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces41.uiRepeat;

import java.io.Serializable;
import java.util.Arrays;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Spec1263 implements Serializable {

    private static final long serialVersionUID = 1L;

    private Cell[][] matrix = new Cell[2][2];
    private String output;

    @PostConstruct
    public void init() {
        for (int x = 0; x < matrix.length; x++) {
            for (int y = 0; y < matrix[x].length; y++) {
                matrix[x][y] = new Cell();
            }
        }
    }

    public void submit() {
        output = Arrays.deepToString(matrix);
    }

    public Cell[][] getMatrix() {
        return matrix;
    }

    public String getOutput() {
        return output;
    }

    public static class Cell implements Serializable {

        private static final long serialVersionUID = 1L;

        private String value;

        public String getValue() {
            return this.value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return value;
        }
    }
}
