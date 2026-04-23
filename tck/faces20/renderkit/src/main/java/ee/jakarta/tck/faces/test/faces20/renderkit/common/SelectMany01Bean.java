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

package ee.jakarta.tck.faces.test.faces20.renderkit.common;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArraySet;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.FacesException;
import jakarta.faces.model.SelectItem;
import jakarta.inject.Named;

@Named("select01")
@SessionScoped
public class SelectMany01Bean implements Serializable {

    private static final long serialVersionUID = -8823380871067856327L;

    private final Collection<SelectItem> possibleValues;

    private Set<String> setValues;
    private SortedSet<String> sortedSetValues;
    private List<String> listValues;
    private Collection<String> collectionValues;
    private String[] arrayValues;

    private SortedSet<String> initialSortedSetValues;
    private Collection<String> initialCollectionValues;
    private Set<String> initialSetValues;
    private List<String> initialListValues;

    private Collection<String> collectionFromHintValues;
    private Collection<String> collectionFromHintValues2;

    private Object someValues;

    private List<Integer> listIntegers;
    private List<BigInteger> listBigIntegers;
    private List<Short> listShorts;
    private List<Long> listLongs;
    private List<Float> listFloats;
    private List<BigDecimal> listBigDecimals;

    public SelectMany01Bean() {
        Set<SelectItem> items = new LinkedHashSet<>();
        items.add(new SelectItem("Bilbo"));
        items.add(new SelectItem("Frodo"));
        items.add(new SelectItem("Merry"));
        items.add(new SelectItem("Pippin"));
        possibleValues = Collections.unmodifiableSet(items);

        initialSortedSetValues = new TreeSet<>(Collections.reverseOrder());
        initialSortedSetValues.add("Pippin");
        initialSortedSetValues.add("Frodo");

        initialCollectionValues = new LinkedHashSet<>(2);
        initialCollectionValues.add("Bilbo");
        initialCollectionValues.add("Merry");

        initialSetValues = new CopyOnWriteArraySet<>();
        initialSetValues.add("Frodo");

        initialListValues = new Vector<>();
        initialListValues.add("Bilbo");
        initialListValues.add("Pippin");
        initialListValues.add("Merry");

        populateNumberLists();
    }

    public Collection<SelectItem> getPossibleValues() {
        return possibleValues;
    }

    public Set<String> getSetValues() {
        return setValues;
    }

    public void setSetValues(Set<String> setValues) {
        if (!(setValues instanceof HashSet)) {
            throw new FacesException("[setSetValues] Error: Expected value to be HashSet");
        }
        this.setValues = setValues;
    }

    public List<String> getListValues() {
        return listValues;
    }

    public void setListValues(List<String> listValues) {
        if (!(listValues instanceof ArrayList)) {
            throw new FacesException("[setListValues] Error: Expected value to be ArrayList");
        }
        this.listValues = listValues;
    }

    public String[] getArrayValues() {
        return arrayValues;
    }

    public void setArrayValues(String[] arrayValues) {
        this.arrayValues = arrayValues;
    }

    public SortedSet<String> getSortedSetValues() {
        return sortedSetValues;
    }

    public void setSortedSetValues(SortedSet<String> sortedSetValues) {
        if (!(sortedSetValues instanceof TreeSet)) {
            throw new FacesException("[setSortedSetValues] Error: Expected value to be TreeSet");
        }
        if (((TreeSet<String>) sortedSetValues).comparator() != null) {
            throw new FacesException("[setSortedSetValues] Error: Expected null comparator");
        }
        this.sortedSetValues = sortedSetValues;
    }

    public Collection<String> getCollectionValues() {
        return collectionValues;
    }

    public void setCollectionValues(Collection<String> collectionValues) {
        if (!(collectionValues instanceof ArrayList)) {
            throw new FacesException("[setCollectionValues] Error: Expected value to be ArrayList");
        }
        this.collectionValues = collectionValues;
    }

    public SortedSet<String> getInitialSortedSetValues() {
        return initialSortedSetValues;
    }

    public void setInitialSortedSetValues(SortedSet<String> initialSortedSetValues) {
        if (!(initialSortedSetValues instanceof TreeSet)) {
            throw new FacesException("[setInitialSortedSetValues] Error: Expected value to be TreeSet");
        }
        this.initialSortedSetValues = initialSortedSetValues;
    }

    public Collection<String> getInitialCollectionValues() {
        return initialCollectionValues;
    }

    public void setInitialCollectionValues(Collection<String> initialCollectionValues) {
        if (!(initialCollectionValues instanceof LinkedHashSet)) {
            throw new FacesException("[initialCollectionValues] Error: Expected value to be LinkedHashSet");
        }
        this.initialCollectionValues = initialCollectionValues;
    }

    public Set<String> getInitialSetValues() {
        return initialSetValues;
    }

    public void setInitialSetValues(Set<String> initialSetValues) {
        if (!(initialSetValues instanceof CopyOnWriteArraySet)) {
            throw new FacesException("[initialSetValues] Error: Expected value to be CopyOnWriteArraySet");
        }
        this.initialSetValues = initialSetValues;
    }

    public List<String> getInitialListValues() {
        return initialListValues;
    }

    public void setInitialListValues(List<String> initialListValues) {
        if (!(initialListValues instanceof Vector)) {
            throw new FacesException("[initialListValues] Error: Expected value to be Vector");
        }
        this.initialListValues = initialListValues;
    }

    public Collection<String> getCollectionFromHintValues() {
        return collectionFromHintValues;
    }

    public void setCollectionFromHintValues(Collection<String> collectionFromHintValues) {
        if (!(collectionFromHintValues instanceof LinkedList)) {
            throw new FacesException("[collectionFromHintValues] Error: Expected value to be LinkedList");
        }
        this.collectionFromHintValues = collectionFromHintValues;
    }

    public Collection<String> getCollectionFromHintValues2() {
        return collectionFromHintValues2;
    }

    public void setCollectionFromHintValues2(Collection<String> collectionFromHintValues) {
        if (!(collectionFromHintValues instanceof LinkedList)) {
            throw new FacesException("[collectionFromHintValues2] Error: Expected value to be LinkedList");
        }
        this.collectionFromHintValues2 = collectionFromHintValues;
    }

    public Class<? extends Collection> getCollectionType() {
        return LinkedList.class;
    }

    public Object getSomeValues() {
        return someValues;
    }

    public void setSomeValues(Object someValues) {
        if (!someValues.getClass().isArray()) {
            throw new FacesException("[someValues] Error: Expected value to be an array type");
        }
        this.someValues = someValues;
    }

    public List<Integer> getListIntegers() {
        return listIntegers;
    }

    public void setListIntegers(List<Integer> listIntegers) {
        this.listIntegers = listIntegers;
    }

    public List<BigInteger> getListBigIntegers() {
        return listBigIntegers;
    }

    public void setListBigIntegers(List<BigInteger> listBigIntegers) {
        this.listBigIntegers = listBigIntegers;
    }

    public List<Short> getListShorts() {
        return listShorts;
    }

    public void setListShorts(List<Short> listShorts) {
        this.listShorts = listShorts;
    }

    public List<Long> getListLongs() {
        return listLongs;
    }

    public void setListLongs(List<Long> listLongs) {
        this.listLongs = listLongs;
    }

    public List<Float> getListFloats() {
        return listFloats;
    }

    public void setListFloats(List<Float> listFloats) {
        this.listFloats = listFloats;
    }

    public List<BigDecimal> getListBigDecimals() {
        return listBigDecimals;
    }

    public void setListBigDecimals(List<BigDecimal> listBigDecimals) {
        this.listBigDecimals = listBigDecimals;
    }

    private void populateNumberLists() {
        listIntegers = new ArrayList<>();
        listIntegers.add(123);

        listBigIntegers = new ArrayList<>();
        listBigIntegers.add(BigInteger.valueOf(123L));

        listShorts = new ArrayList<>();
        listShorts.add((short) 123);

        listLongs = new ArrayList<>();
        listLongs.add(123L);

        listFloats = new ArrayList<>();
        listFloats.add(123f);

        listBigDecimals = new ArrayList<>();
        listBigDecimals.add(BigDecimal.valueOf(123L));
    }
}
