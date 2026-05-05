/*
 * Copyright (c) 2009, 2026 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.test.faces20.renderkit.inputtext;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.html.HtmlInputText;
import jakarta.inject.Named;

@Named("Hello")
@SessionScoped
public class InputTextUIBean implements Serializable {

    private static final long serialVersionUID = -2564325672383456327L;

    private HtmlInputText greeting;

    public HtmlInputText getGreeting() {
        return greeting;
    }

    public void setGreeting(HtmlInputText greeting) {
        greeting.setStyleClass("text");
        greeting.setSize(10);
        greeting.setValue("hello");

        this.greeting = greeting;
    }
}
