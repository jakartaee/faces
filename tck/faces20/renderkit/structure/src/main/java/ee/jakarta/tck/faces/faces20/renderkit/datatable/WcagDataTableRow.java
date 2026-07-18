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
package ee.jakarta.tck.faces.faces20.renderkit.datatable;

public class WcagDataTableRow {

    private final String codePageId;
    private final String name;
    private final boolean ACP;
    private final boolean OEMCP;
    private final boolean winNT31;
    private final boolean winNT351;
    private final boolean win95;

    public WcagDataTableRow(String codePageId, String name, boolean acp, boolean oemcp,
            boolean winNT31, boolean winNT351, boolean win95) {
        this.codePageId = codePageId;
        this.name = name;
        this.ACP = acp;
        this.OEMCP = oemcp;
        this.winNT31 = winNT31;
        this.winNT351 = winNT351;
        this.win95 = win95;
    }

    public String getCodePageId() {
        return codePageId;
    }

    public String getName() {
        return name;
    }

    public boolean isACP() {
        return ACP;
    }

    public boolean isOEMCP() {
        return OEMCP;
    }

    public boolean isWinNT31() {
        return winNT31;
    }

    public boolean isWinNT351() {
        return winNT351;
    }

    public boolean isWin95() {
        return win95;
    }
}
