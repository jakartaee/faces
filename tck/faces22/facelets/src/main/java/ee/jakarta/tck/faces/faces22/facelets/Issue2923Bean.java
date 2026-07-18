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

package ee.jakarta.tck.faces.faces22.facelets;

import java.util.HashMap;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.convert.EnumConverter;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Issue2923Bean {

    private final Map<String, Issue2923Enum> choices;
    private Issue2923Enum actualChoice;
    private final EnumConverter converter = new EnumConverter(Issue2923Enum.class);

    public Issue2923Bean() {
        choices = new HashMap<>(2);
        choices.put("First choice", Issue2923Enum.CHOICE_ONE);
        choices.put("Second choice", Issue2923Enum.CHOICE_TWO);
    }

    public EnumConverter getConverter() {
        return converter;
    }

    public Issue2923Enum getActualChoice() {
        return actualChoice;
    }

    public void setActualChoice(Issue2923Enum actualChoice) {
        this.actualChoice = actualChoice;
    }

    public Map<String, Issue2923Enum> getChoices() {
        return choices;
    }
}
