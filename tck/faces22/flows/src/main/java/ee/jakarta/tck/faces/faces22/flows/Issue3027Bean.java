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
package ee.jakarta.tck.faces.faces22.flows;

import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("outcomeParameterBean")
@ViewScoped
public class Issue3027Bean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchTermA = "Laurel & Hardy";
    private String searchTermB = "Laurel & Hardy";
    private String searchTermC = "Laurel & Hardy";
    private String searchTermD = "Laurel & Hardy";
    private String searchTermE = "Laurel & Hardy";

    public String startSearchWithUrlEncode() {
        String queryUrlParameter = URLEncoder.encode(searchTermA, StandardCharsets.UTF_8);
        return "/outcomeParameterResults.xhtml?query=" + queryUrlParameter + "&otherParameter=someValue&faces-redirect=true";
    }

    public String startSearchWithoutUrlEncode() {
        return "/outcomeParameterResults.xhtml?query=" + searchTermB + "&otherParameter=someValue&faces-redirect=true";
    }

    public void startSearchViaExternalContext() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        String contextPath = externalContext.getRequestContextPath();
        String redirectTarget = contextPath
                + "/outcomeParameterResults.xhtml?query="
                + URLEncoder.encode(searchTermC, StandardCharsets.UTF_8)
                + "&otherParameter=someValue";
        externalContext.redirect(redirectTarget);
    }

    public String getSearchTermA() {
        return searchTermA;
    }

    public void setSearchTermA(String searchTermA) {
        this.searchTermA = searchTermA;
    }

    public String getSearchTermB() {
        return searchTermB;
    }

    public void setSearchTermB(String searchTermB) {
        this.searchTermB = searchTermB;
    }

    public String getSearchTermC() {
        return searchTermC;
    }

    public void setSearchTermC(String searchTermC) {
        this.searchTermC = searchTermC;
    }

    public String getSearchTermD() {
        return searchTermD;
    }

    public void setSearchTermD(String searchTermD) {
        this.searchTermD = searchTermD;
    }

    public String getSearchTermE() {
        return searchTermE;
    }

    public void setSearchTermE(String searchTermE) {
        this.searchTermE = searchTermE;
    }
}
