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

package ee.jakarta.tck.faces.faces23.config_files_navigation;

import java.io.Serializable;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2444Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String STRING_WITH_SPECIAL_CHARACTERS = "日א";

    public String submit() {
        return "issue2444?param=" + STRING_WITH_SPECIAL_CHARACTERS + "&faces-redirect=true";
    }

    public String getStringWithSpecialCharacters() {
        return STRING_WITH_SPECIAL_CHARACTERS;
    }
}
