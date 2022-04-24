/*
 * Copyright (c) 1997, 2018 Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2022 Contributors to Eclipse Foundation.
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

package ee.jakarta.tck.faces.test.servlet40.facelets;

import static jakarta.faces.component.visit.VisitContext.createVisitContext;
import static jakarta.faces.component.visit.VisitResult.ACCEPT;
import static java.util.Arrays.asList;
import static java.util.Collections.emptySet;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CollectionBean {

    private Integer value;
    
    public void performVisitTree() {
        FacesContext context = FacesContext.getCurrentInstance();
        context.getViewRoot().visitTree(createVisitContext(context, null, emptySet()), (visitContext, target) -> ACCEPT);
    }

    public Collection<Integer> getCollection() {
        return new TestCollection<>(asList(1, 2, 3));
    }
    
    public Integer getValue() {
        return value;
    }
    
    public void setValue(Integer value) {
        this.value = value;
    }

    private static class TestCollection<E> extends AbstractCollection<E> {

        private final Collection<E> collection;

        public TestCollection(Collection<E> collection) {
            this.collection = collection;
        }

        @Override
        public Iterator<E> iterator() {
            return collection.iterator();
        }

        @Override
        public int size() {
            return collection.size();
        }
    }

}
