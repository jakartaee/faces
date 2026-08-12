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

package ee.jakarta.tck.faces.faces22.composite_component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue2051Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final List<Issue2051Line> availableLines =
        List.of(new Issue2051Line("1", "First"), new Issue2051Line("2", "Second"), new Issue2051Line("3", "Third"));

    private List<Issue2051Line> selectedLines = new ArrayList<>();

    public List<Issue2051Line> getAvailableLines() {
        return availableLines;
    }

    public List<Issue2051Line> getSelectedLines() {
        return selectedLines;
    }

    public void setSelectedLines(List<Issue2051Line> selectedLines) {
        this.selectedLines = selectedLines;
    }

    public String getSelectedNames() {
        return selectedLines.stream().map(Issue2051Line::getName).reduce((left, right) -> left + " " + right).orElse("");
    }

    public Issue2051Line findLine(String id) {
        return availableLines.stream().filter(line -> line.getId().equals(id)).findFirst().orElse(null);
    }
}
