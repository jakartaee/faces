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

package ee.jakarta.tck.faces.faces22.iteration;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

/**
 * Backs a page whose c:forEach attaches an f:convertNumber to each row and binds the converter's {@code pattern} to a
 * per-row value ({@code pattern="#{row.pattern}"}). Every row holds the same numeric value but a different pattern, so
 * each unrolled converter must format with its own row's pattern - guarding that a converter is per-cell configured and
 * never shared/cached across cells (the tag-converter path is distinct from the attribute-less by-type path).
 */
@Named
@ViewScoped
public class ForEachConverterAttributeBean implements Serializable {

    private static final long serialVersionUID = 1L;

    public static class Row implements Serializable {

        private static final long serialVersionUID = 1L;

        private final int value;
        private final String pattern;

        public Row(int value, String pattern) {
            this.value = value;
            this.pattern = pattern;
        }

        public int getValue() {
            return value;
        }

        public String getPattern() {
            return pattern;
        }
    }

    private final List<Row> rows = List.of(new Row(1234, "#,##0"), new Row(1234, "0000000"), new Row(1234, "#"));

    public List<Row> getRows() {
        return rows;
    }

    public String submit() {
        return null;
    }
}
