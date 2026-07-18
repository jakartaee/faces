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

package ee.jakarta.tck.faces.faces23.passthrough;

import java.io.Serializable;
import java.util.List;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class Spec1111Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String OUTCOME = "spec1111-outcome";

    private String text = "text1";
    private String email = "anybody@example.com";
    private Integer number = 10;
    private boolean checkbox;
    private List<String> list = List.of("1", "2", "3", "4", "5", "6", "7");
    private String selectOne = "2";
    private String selectOneSize2 = "3";
    private List<String> selectMany = List.of("4", "6");
    private String longText = "Long text";
    private String lastAction;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public boolean isCheckbox() {
        return checkbox;
    }

    public void setCheckbox(boolean checkbox) {
        this.checkbox = checkbox;
    }

    public List<String> getList() {
        return list;
    }

    public String getSelectOne() {
        return selectOne;
    }

    public void setSelectOne(String selectOne) {
        this.selectOne = selectOne;
    }

    public String getSelectOneSize2() {
        return selectOneSize2;
    }

    public void setSelectOneSize2(String selectOneSize2) {
        this.selectOneSize2 = selectOneSize2;
    }

    public List<String> getSelectMany() {
        return selectMany;
    }

    public void setSelectMany(List<String> selectMany) {
        this.selectMany = selectMany;
    }

    public String getLongText() {
        return longText;
    }

    public void setLongText(String longText) {
        this.longText = longText;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getOutcome() {
        return OUTCOME;
    }

    public String action1() {
        lastAction = "action1";
        return null;
    }

    public String action2() {
        lastAction = "action2";
        return null;
    }
}
