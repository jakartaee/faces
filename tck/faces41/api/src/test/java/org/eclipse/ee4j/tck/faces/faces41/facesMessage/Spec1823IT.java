/*
 * Copyright (c) 2024 Contributors to Eclipse Foundation.
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
package org.eclipse.ee4j.tck.faces.faces41.facesMessage;

import static jakarta.faces.application.FacesMessage.SEVERITY_INFO;
import static jakarta.faces.application.FacesMessage.SEVERITY_WARN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import jakarta.faces.application.FacesMessage;

import org.junit.jupiter.api.Test;

class Spec1823IT {

    private static final FacesMessage MESSAGE1A = new FacesMessage(SEVERITY_INFO, "summary1", "detail1");
    private static final FacesMessage MESSAGE1B = new FacesMessage(SEVERITY_INFO, "summary1", "detail1");
    private static final FacesMessage MESSAGE2A = new FacesMessage(SEVERITY_INFO, "summary1", "detail2");
    private static final FacesMessage MESSAGE2B = new FacesMessage(SEVERITY_INFO, "summary1", "detail2");
    private static final FacesMessage MESSAGE3A = new FacesMessage(SEVERITY_INFO, "summary2", "detail1");
    private static final FacesMessage MESSAGE3B = new FacesMessage(SEVERITY_INFO, "summary2", "detail1");
    private static final FacesMessage MESSAGE4A = new FacesMessage(SEVERITY_INFO, "summary2", "detail2");
    private static final FacesMessage MESSAGE4B = new FacesMessage(SEVERITY_INFO, "summary2", "detail2");
    private static final FacesMessage MESSAGE5A = new FacesMessage(SEVERITY_WARN, "summary1", "detail1");
    private static final FacesMessage MESSAGE5B = new FacesMessage(SEVERITY_WARN, "summary1", "detail1");
    private static final FacesMessage MESSAGE6A = new FacesMessage(SEVERITY_WARN, "summary1", "detail2");
    private static final FacesMessage MESSAGE6B = new FacesMessage(SEVERITY_WARN, "summary1", "detail2");
    private static final FacesMessage MESSAGE7A = new FacesMessage(SEVERITY_WARN, "summary2", "detail1");
    private static final FacesMessage MESSAGE7B = new FacesMessage(SEVERITY_WARN, "summary2", "detail1");
    private static final FacesMessage MESSAGE8A = new FacesMessage(SEVERITY_WARN, "summary2", "detail2");
    private static final FacesMessage MESSAGE8B = new FacesMessage(SEVERITY_WARN, "summary2", "detail2");

    public static class CustomFacesMessage extends FacesMessage {
        private static final long serialVersionUID = 1L;

        public CustomFacesMessage(Severity severity, String summary, String detail) {
            super(severity, summary, detail);
        }
    }

  /**
   * @see FacesMessage
     * @see https://github.com/jakartaee/faces/issues/1823
   */
  @Test
  void equals() {
        assertEquals(MESSAGE1A, MESSAGE1A);
        assertEquals(MESSAGE1A, MESSAGE1B);
        assertNotEquals(MESSAGE1A, MESSAGE2A);
        assertNotEquals(MESSAGE1A, MESSAGE2B);
        assertNotEquals(MESSAGE1A, MESSAGE3A);
        assertNotEquals(MESSAGE1A, MESSAGE3B);
        assertNotEquals(MESSAGE1A, MESSAGE4A);
        assertNotEquals(MESSAGE1A, MESSAGE4B);
        assertNotEquals(MESSAGE1A, MESSAGE5A);
        assertNotEquals(MESSAGE1A, MESSAGE5B);
        assertNotEquals(MESSAGE1A, MESSAGE6A);
        assertNotEquals(MESSAGE1A, MESSAGE6B);
        assertNotEquals(MESSAGE1A, MESSAGE7A);
        assertNotEquals(MESSAGE1A, MESSAGE7B);
        assertNotEquals(MESSAGE1A, MESSAGE8A);
        assertNotEquals(MESSAGE1A, MESSAGE8B);
        assertEquals(MESSAGE1A, new FacesMessage(SEVERITY_INFO, "summary1", "detail1"));
        assertNotEquals(MESSAGE1A, new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1"));
        assertEquals(new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1"), new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1"));
        assertNotEquals(new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1"), new CustomFacesMessage(SEVERITY_WARN, "summary1", "detail1"));
    }

  /**
   * @see FacesMessage
     * @see https://github.com/jakartaee/faces/issues/1823
   */
  @Test
  void testHashCode() {
        assertEquals(MESSAGE1A.hashCode(), MESSAGE1A.hashCode());
        assertEquals(MESSAGE1A.hashCode(), MESSAGE1B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE2A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE2B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE3A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE3B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE4A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE4B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE5A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE5B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE6A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE6B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE7A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE7B.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE8A.hashCode());
        assertNotEquals(MESSAGE1A.hashCode(), MESSAGE8B.hashCode());
        assertEquals(MESSAGE1A.hashCode(), new FacesMessage(SEVERITY_INFO, "summary1", "detail1").hashCode());
        assertNotEquals(MESSAGE1A, new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1").hashCode());
        assertEquals(new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1").hashCode(), new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1").hashCode());
        assertNotEquals(new CustomFacesMessage(SEVERITY_INFO, "summary1", "detail1").hashCode(), new CustomFacesMessage(SEVERITY_WARN, "summary1", "detail1").hashCode());
    }
}
