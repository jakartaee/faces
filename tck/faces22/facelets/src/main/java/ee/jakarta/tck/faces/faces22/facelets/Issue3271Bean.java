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

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.inject.Named;

@Named
@SessionScoped
public class Issue3271Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Set<String> tags = new TreeSet<>(Arrays.asList("seed"));

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public static class StringToSetConverter implements Converter<Set<String>> {

        @Override
        public Set<String> getAsObject(FacesContext ctx, UIComponent component, String value) {
            if (value == null) {
                return null;
            }
            Set<String> tagSet = new TreeSet<>();
            for (String tag : value.split("\\s+")) {
                if (!tag.isEmpty()) {
                    tagSet.add(tag);
                }
            }
            return tagSet;
        }

        @Override
        public String getAsString(FacesContext ctx, UIComponent component, Set<String> value) {
            if (value == null) {
                return "";
            }
            StringBuilder builder = new StringBuilder();
            Collection<String> tags = value;
            for (String tag : tags) {
                builder.append(tag);
                builder.append(" ");
            }
            return builder.toString().trim();
        }
    }

    public String printTags() {
        if (tags.isEmpty()) {
            return "No tags";
        }
        StringBuilder builder = new StringBuilder();
        for (String tag : tags) {
            builder.append("'");
            builder.append(tag);
            builder.append("' ");
        }
        return builder.toString().trim();
    }

    public Converter<Set<String>> getTagsConverter() {
        return new StringToSetConverter();
    }
}
