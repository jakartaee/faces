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
package ee.jakarta.tck.faces.faces50.misc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class Spec772Bean {

    public static class Category {

        private final String name;
        private final Map<String, String> products;

        public Category(String name, Map<String, String> products) {
            this.name = name;
            this.products = products;
        }

        public String getName() {
            return name;
        }

        public Map<String, String> getProducts() {
            return products;
        }

    }

    private final Map<String, String> products = new LinkedHashMap<>();
    private final Map<String, String> nullValueProducts = new LinkedHashMap<>();
    private final List<Category> categories = List.of(new Category("fruit", products));
    private final Map<String, Map<String, String>> categoryMap = new LinkedHashMap<>();
    private String nested = "a";
    private String nestedVar;
    private String directVar;
    private String group;

    public Spec772Bean() {
        products.put("Apple", "a");
        products.put(null, "n");
        products.put("Banana", null);
        categoryMap.put("fruit", products);
        categoryMap.put(null, Map.of("Cherry", "c"));
        nullValueProducts.put("Apple", "a");
        nullValueProducts.put("Banana", null);
    }

    public Map<String, String> getProducts() {
        return products;
    }

    public Map<String, String> getNullValueProducts() {
        return nullValueProducts;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public Map<String, Map<String, String>> getCategoryMap() {
        return categoryMap;
    }

    public String getNested() {
        return nested;
    }

    public void setNested(String nested) {
        this.nested = nested;
    }

    public String getNestedVar() {
        return nestedVar;
    }

    public void setNestedVar(String nestedVar) {
        this.nestedVar = nestedVar;
    }

    public String getDirectVar() {
        return directVar;
    }

    public void setDirectVar(String directVar) {
        this.directVar = directVar;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

}
