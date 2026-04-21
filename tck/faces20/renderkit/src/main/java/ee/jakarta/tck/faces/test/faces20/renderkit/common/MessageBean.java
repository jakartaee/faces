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

import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;

@Named("Message")
@SessionScoped
public class MessageBean implements Serializable {

    private static final long serialVersionUID = -2156780871083890367L;

    private static final String INFO_SUMMARY = "INFO: Summary Message";
    private static final String INFO_DETAIL = "INFO: Detailed Message";
    private static final String WARN_SUMMARY = "WARN: Summary Message";
    private static final String WARN_DETAIL = "WARN: Detailed Message";
    private static final String ERROR_SUMMARY = "ERROR: Summary Message";
    private static final String ERROR_DETAIL = "ERROR: Detailed Message";
    private static final String FATAL_SUMMARY = "FATAL: Summary Message";
    private static final String FATAL_DETAIL = "FATAL: Detailed Message";

    private String severity;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        FacesContext context = FacesContext.getCurrentInstance();

        switch (severity) {
            case "INFO":
                context.addMessage(id, new FacesMessage(FacesMessage.Severity.INFO, INFO_SUMMARY, INFO_DETAIL));
                break;
            case "WARN":
                context.addMessage(id, new FacesMessage(FacesMessage.Severity.WARN, WARN_SUMMARY, WARN_DETAIL));
                break;
            case "ERROR":
                context.addMessage(id, new FacesMessage(FacesMessage.Severity.ERROR, ERROR_SUMMARY, ERROR_DETAIL));
                break;
            case "FATAL":
                context.addMessage(id, new FacesMessage(FacesMessage.Severity.FATAL, FATAL_SUMMARY, FATAL_DETAIL));
                break;
            case "MESSAGES_INFO":
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.INFO, INFO_SUMMARY + "_One ", INFO_DETAIL + "_One "));
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.INFO, INFO_SUMMARY + "_Two ", INFO_DETAIL + "_Two "));
                break;
            case "MESSAGES_WARN":
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.WARN, WARN_SUMMARY + "_One ", WARN_DETAIL + "_One "));
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.WARN, WARN_SUMMARY + "_Two ", WARN_DETAIL + "_Two "));
                break;
            case "MESSAGES_ERROR":
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.ERROR, ERROR_SUMMARY + "_One ", ERROR_DETAIL + "_One "));
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.ERROR, ERROR_SUMMARY + "_Two ", ERROR_DETAIL + "_Two "));
                break;
            case "MESSAGES_FATAL":
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.FATAL, FATAL_SUMMARY + "_One ", FATAL_DETAIL + "_One "));
                context.addMessage(null, new FacesMessage(FacesMessage.Severity.FATAL, FATAL_SUMMARY + "_Two ", FATAL_DETAIL + "_Two "));
                break;
            default:
                break;
        }

        this.severity = severity;
    }
}
