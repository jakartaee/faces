/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
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

package ee.jakarta.tck.faces.faces22.el_lambda;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue3032Bean3 implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private List<Issue3032Bean2> issue3032Bean2s;
    
    @PostConstruct
    private void create() {
        issue3032Bean2s = new CopyOnWriteArrayList<Issue3032Bean2>();
        issue3032Bean2s.add(new Issue3032Bean2("The Picture of Dorian Gray", "Philosophical Fiction"));
        issue3032Bean2s.add(new Issue3032Bean2("At Swim Two Birds", "Comedy"));
        issue3032Bean2s.add(new Issue3032Bean2("North and South", "Social Commentary"));
        issue3032Bean2s.add(new Issue3032Bean2("The Third Policeman", "Comedy"));
    }

    public List<Issue3032Bean2> getIssue3032Bean2s() {
        return issue3032Bean2s;
    }

}
