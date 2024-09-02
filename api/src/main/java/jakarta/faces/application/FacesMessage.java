/*
 * Copyright (c) 2022, 2023 Contributors to Eclipse Foundation.
 * Copyright (c) 1997, 2020 Oracle and/or its affiliates. All rights reserved.
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

package jakarta.faces.application;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toUnmodifiableMap;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * <strong>FacesMessage</strong> represents a single validation (or other) message, which is typically associated with a
 * particular component in the view. A {@link jakarta.faces.application.FacesMessage} instance may be created based on a
 * specific <code>messageId</code>. The specification defines the set of <code>messageId</code>s for which there must be
 * {@link jakarta.faces.application.FacesMessage} instances.
 * </p>
 *
 * <p>
 * The implementation must take the following steps when creating <code>FacesMessage</code> instances given a
 * <code>messageId</code>:
 * </p>
 *
 * <blockquote>
 *
 * <p>
 * Call {@link Application#getMessageBundle}. If non-<code>null</code>, locate the named <code>ResourceBundle</code>,
 * using the <code>Locale</code> from the current {@link jakarta.faces.component.UIViewRoot} and see if it has a value
 * for the argument <code>messageId</code>. If it does, treat the value as the <code>summary</code> of the
 * <code>FacesMessage</code>. If it does not, or if {@link Application#getMessageBundle} returned <code>null</code>,
 * look in the <code>ResourceBundle</code> named by the value of the constant {@link #FACES_MESSAGES} and see if it has
 * a value for the argument <code>messageId</code>. If it does, treat the value as the <code>summary</code> of the
 * <code>FacesMessage</code>. If it does not, there is no initialization information for the <code>FacesMessage</code>
 * instance.
 * </p>
 *
 * <p>
 * In all cases, if a <code>ResourceBundle</code> hit is found for the <code>{messageId}</code>, look for further hits
 * under the key <code>{messageId}_detail</code>. Use this value, if present, as the <code>detail</code> for the
 * returned <code>FacesMessage</code>.
 * </p>
 *
 * <p>
 * Make sure to perform any parameter substitution required for the <code>summary</code> and <code>detail</code> of the
 * <code>FacesMessage</code>.
 * </p>
 *
 * </blockquote>
 *
 */
public class FacesMessage implements Serializable {

    // --------------------------------------------------------------- Constants

    private static final long serialVersionUID = -4161119404763764443L;

    /**
     * <p>
     * <code>ResourceBundle</code> identifier for messages whose message identifiers are defined in the Jakarta Faces
     * specification.
     * </p>
     */
    public static final String FACES_MESSAGES = "jakarta.faces.Messages";

    // ------------------------------------------------- Message Severity Levels

    /**
     * <p>
     * Message severity level indicating an informational message rather than an error.
     * </p>
     * @deprecated Use {@link Severity#INFO} instead because it has been converted to enum since 5.0.
     */
    @Deprecated(since = "5.0", forRemoval = true)
    public static final Severity SEVERITY_INFO = Severity.INFO;

    /**
     * <p>
     * Message severity level indicating that an error might have occurred.
     * </p>
     * @deprecated Use {@link Severity#WARN} instead because it has been converted to enum since 5.0.
     */
    @Deprecated(since = "5.0", forRemoval = true)
    public static final Severity SEVERITY_WARN = Severity.WARN;

    /**
     * <p>
     * Message severity level indicating that an error has occurred.
     * </p>
     * @deprecated Use {@link Severity#ERROR} instead because it has been converted to enum since 5.0.
     */
    @Deprecated(since = "5.0", forRemoval = true)
    public static final Severity SEVERITY_ERROR = Severity.ERROR;

    /**
     * <p>
     * Message severity level indicating that a serious error has occurred.
     * </p>
     * @deprecated Use {@link Severity#FATAL} instead because it has been converted to enum since 5.0.
     */
    @Deprecated(since = "5.0", forRemoval = true)
    public static final Severity SEVERITY_FATAL = Severity.FATAL;

    /**
     * <p>
     * Immutable <code>List</code> of valid {@link jakarta.faces.application.FacesMessage.Severity} instances, in ascending
     * order of their ordinal value.
     * </p>
     */
    public static final List<Severity> VALUES = List.of(Severity.values());

    /**
     * <p>
     * Immutable <code>Map</code> of valid {@link jakarta.faces.application.FacesMessage.Severity} instances, keyed by name.
     * </p>
     */
    public static final Map<String, Severity> VALUES_MAP = VALUES.stream().collect(toUnmodifiableMap(Severity::name, identity()));

    // ------------------------------------------------------ Instance Variables

    private transient Severity severity = Severity.INFO;
    private transient String summary = null;
    private transient String detail = null;
    private transient boolean rendered;

    // ------------------------------------------------------------ Constructors

    /**
     * <p>
     * Construct a new {@link jakarta.faces.application.FacesMessage} with no initial values. The severity is set to
     * Severity.INFO.
     * </p>
     */
    public FacesMessage() {
        super();
    }

    /**
     * <p>
     * Construct a new {@link jakarta.faces.application.FacesMessage} with just a summary. The detail is <code>null</code>,
     * the severity is set to <code>Severity.INFO</code>.
     * </p>
     *
     * @param summary the summary.
     */
    public FacesMessage(String summary) {
        super();
        setSummary(summary);
    }

    /**
     * <p>
     * Construct a new {@link jakarta.faces.application.FacesMessage} with the specified initial values. The severity is set
     * to Severity.INFO.
     * </p>
     *
     * @param summary Localized summary message text
     * @param detail Localized detail message text
     *
     * @throws IllegalArgumentException if the specified severity level is not one of the supported values
     */
    public FacesMessage(String summary, String detail) {
        super();
        setSummary(summary);
        setDetail(detail);
    }

    /**
     * <p>
     * Construct a new <code>FacesMessage</code> with the specified initial values.
     * </p>
     *
     * @param severity the severity
     * @param summary Localized summary message text
     * @param detail Localized detail message text
     *
     * @throws IllegalArgumentException if the specified severity level is not one of the supported values
     */
    public FacesMessage(Severity severity, String summary, String detail) {
        super();
        setSeverity(severity);
        setSummary(summary);
        setDetail(detail);
    }

    // ---------------------------------------------------------- Public Methods

    /**
     * <p>
     * Return the localized detail text. If no localized detail text has been defined for this message, return the localized
     * summary text instead.
     * </p>
     *
     * @return the localized detail text.
     */
    public String getDetail() {

        if (detail == null) {
            return summary;
        }

        return detail;
    }

    /**
     * <p>
     * Set the localized detail text.
     * </p>
     *
     * @param detail The new localized detail text
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * <p>
     * Return the severity level.
     * </p>
     *
     * @return the severity level.
     */
    public Severity getSeverity() {
        return severity;
    }

    /**
     * <p>
     * Set the severity level.
     * </p>
     *
     * @param severity The new severity level
     */
    public void setSeverity(Severity severity) {
        this.severity = severity;
    }

    /**
     * <p>
     * Return the localized summary text.
     * </p>
     *
     * @return the localized summary text.
     */
    public String getSummary() {
        return summary;
    }

    /**
     * <p>
     * Set the localized summary text.
     * </p>
     *
     * @param summary The new localized summary text
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * @return <code>true</code> if {@link #rendered()} has been called, otherwise <code>false</code>
     *
     * @since 2.0
     */
    public boolean isRendered() {
        return rendered;
    }

    /**
     * <p>
     * Marks this message as having been rendered to the client.
     * </p>
     *
     * @since 2.0
     */
    public void rendered() {
        rendered = true;
    }

    /**
     * @since 4.1
     */
    @Override
    public int hashCode() {
        return Objects.hash(severity, summary, detail);
    }

    /**
     * @since 4.1
     */
    @Override
    public boolean equals(Object object) {
        return (object == this) || (object != null && object.getClass() == getClass()
            && Objects.equals(severity, ((FacesMessage) object).severity)
            && Objects.equals(summary, ((FacesMessage) object).summary)
            && Objects.equals(detail, ((FacesMessage) object).detail));
    }

    /**
     * @since 4.1
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "["
            + "severity='" + severity + "', "
            + "summary='" + summary + "', "
            + "detail='" + detail + "']"
        ;
    }

    /**
     * <p>
     * Persist {@link jakarta.faces.application.FacesMessage} artifacts, including the non serializable
     * <code>Severity</code>.
     * </p>
     *
     * @param out The target stream to which the object will be written.
     *
     * @throws IOException Any of the usual Input/Output related exceptions.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeObject(severity);
        out.writeObject(summary);
        out.writeObject(detail);
        out.writeObject(rendered);
    }

    /**
     * <p>
     * Reconstruct {@link jakarta.faces.application.FacesMessage} from serialized artifacts.
     * </p>
     *
     * @param in The binary input of the object to be read
     *
     * @throws IOException Any of the usual Input/Output related exceptions.
     * @throws ClassNotFoundException Class of a serialized object cannot be found.
     *
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        severity = Severity.INFO;
        summary = null;
        detail = null;
        severity = (Severity) in.readObject();
        summary = (String) in.readObject();
        detail = (String) in.readObject();
        rendered = (Boolean) in.readObject();
    }

    /**
     * <p>
     * <span class="changed_modified_5_0">Enum</span> used to represent message severity levels.
     * </p>
     */
    public enum Severity {
        
        /**
         * <p class="changed_added_5_0">
         * Message severity level indicating an informational message rather than an error.
         * </p>
         * <p>
         * Historically, this was a constant class known as FacesMessage.SEVERITY_INFO.
         * </p>
         * @since 5.0
         */
        INFO,

        /**
         * <p class="changed_added_5_0">
         * Message severity level indicating a success message rather than an error.
         * </p>
         * @since 5.0
         */
        SUCCESS,

        /**
         * <p class="changed_added_5_0">
         * Message severity level indicating that an error might have occurred.
         * </p>
         * <p>
         * Historically, this was a constant class known as FacesMessage.SEVERITY_WARN.
         * </p>
         * @since 5.0
         */
        WARN,

        /**
         * <p class="changed_added_5_0">
         * Message severity level indicating that an error has occurred.
         * </p>
         * <p>
         * Historically, this was a constant class known as FacesMessage.SEVERITY_ERROR.
         * </p>
         * @since 5.0
         */
        ERROR,

        /**
         * <p class="changed_added_5_0">
         * Message severity level indicating that a serious error has occurred.
         * </p>
         * <p>
         * Historically, this was a constant class known as FacesMessage.SEVERITY_FATAL.
         * </p>
         * @since 5.0
         */
        FATAL;

        /**
         * <p>
         * Return the ordinal value of this {@link FacesMessage.Severity} instance.
         * </p>
         *
         * @return the ordinal.
         */
        public int getOrdinal() {
            return ordinal();
        }

        /**
         * <p>
         * Return a String representation of this {@link FacesMessage.Severity} instance.
         * </p>
         */
        @Override
        public String toString() {
            return name() + ' ' + getOrdinal();
        }
    }

}
