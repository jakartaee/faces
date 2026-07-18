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

import java.util.ArrayList;
import java.util.List;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class WcagDataTableBean {

    private final List<WcagDataTableRow> codePageData = new ArrayList<>();

    public WcagDataTableBean() {
        codePageData.add(new WcagDataTableRow("1200", "Unicode (BMP of ISO 10646)", false, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1251", "Windows 3.1 Cyrillic", true, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1250", "Windows 3.1 Eastern European", true, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1252", "Windows 3.1 US (ANSI)", true, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1253", "Windows 3.1 Greek", true, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1254", "Windows 3.1 Turkish", true, false, true, true, true));
        codePageData.add(new WcagDataTableRow("1255", "Hebrew", true, false, false, false, true));
        codePageData.add(new WcagDataTableRow("1256", "Arabic", true, false, false, false, true));
        codePageData.add(new WcagDataTableRow("1257", "Baltic", true, false, false, false, true));
        codePageData.add(new WcagDataTableRow("1361", "Korean (Johab)", true, false, false, true, true));
        codePageData.add(new WcagDataTableRow("437", "MS-DOS United States", false, true, true, true, true));
        codePageData.add(new WcagDataTableRow("708", "Arabic (ASMO 708)", false, true, false, false, true));
        codePageData.add(new WcagDataTableRow("709", "Arabic (ASMO 449+, BCON V4)", false, true, false, false, true));
        codePageData.add(new WcagDataTableRow("710", "Arabic (Transparent Arabic)", false, true, false, false, true));
        codePageData.add(new WcagDataTableRow("720", "Arabic (Transparent ASMO)", false, true, false, false, true));
    }

    public List<WcagDataTableRow> getCodePageData() {
        return codePageData;
    }
}
