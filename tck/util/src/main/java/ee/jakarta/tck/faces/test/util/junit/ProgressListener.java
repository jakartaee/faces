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
package ee.jakarta.tck.faces.test.util.junit;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.platform.engine.TestExecutionResult;
import org.junit.platform.engine.support.descriptor.ClassSource;
import org.junit.platform.engine.support.descriptor.MethodSource;
import org.junit.platform.launcher.TestExecutionListener;
import org.junit.platform.launcher.TestIdentifier;
import org.junit.platform.launcher.TestPlan;

/**
 * One line per test event, tagged with the GlassFish pool slot the
 * failsafe-fork JVM is leasing and the per-JVM progress counter (with
 * {@code reuseForks=false} that maps 1:1 to "test N of M within this IT
 * class"). On failure the full stack trace is printed inline so the build
 * log is sufficient even when {@code target/failsafe-reports} is incomplete
 * (e.g. build aborted before the surefire writer ran).
 *
 * <p>Output format (white brackets, bold colour for the status word, slot,
 * module and progress):
 * <pre>
 *   [RUNNING][SLOT1][faces20-api-application-misc][1/35] SecretIT#secretRenderPassthroughTest()
 *   [SKIPPED][SLOT3][faces22-ajax-inputs][4/95] Issue3833IT#test  reason: ignored at the request by the myfaces community
 *   [FAILED][SLOT3][faces20-api-component-html-input][5/123] HtmlCommandButtonIT#actionTest  exception: org.opentest4j.AssertionFailedError: expected: <foo> but was: <bar>
 * </pre>
 */
public class ProgressListener implements TestExecutionListener {

    private static final String WHITE       = "\u001B[37m";
    private static final String BOLD_GREEN  = "\u001B[1;32m";
    private static final String BOLD_YELLOW = "\u001B[1;33m";
    private static final String BOLD_RED    = "\u001B[1;31m";
    private static final String RESET       = "\u001B[0m";

    private final AtomicInteger progress = new AtomicInteger();
    private int total;
    private String module;

    @Override
    public void testPlanExecutionStarted(TestPlan testPlan) {
        total = (int) testPlan.countTestIdentifiers(TestIdentifier::isTest);
        // The root pom passes ${project.build.finalName} as a system property to
        // every failsafe fork (see pom.xml maven-failsafe-plugin config). The
        // "test-" prefix is repo convention, not information — strip it so the
        // tag stays compact. Vendor distributions that don't pass finalName get
        // no module segment (graceful degradation, same as the slot bracket).
        var finalName = System.getProperty("finalName");
        if (finalName != null && finalName.startsWith("test-")) {
            module = finalName.substring("test-".length());
        } else {
            module = finalName;
        }
    }

    @Override
    public void executionStarted(TestIdentifier id) {
        if (id.isTest()) {
            System.out.println(tag(BOLD_GREEN, "RUNNING", progress.incrementAndGet()) + " " + label(id));
        }
    }

    @Override
    public void executionSkipped(TestIdentifier id, String reason) {
        if (id.isTest()) {
            System.out.println(tag(BOLD_YELLOW, "SKIPPED", progress.incrementAndGet()) + " " + label(id) + "  reason: " + reason);
        }
    }

    @Override
    public void executionFinished(TestIdentifier id, TestExecutionResult result) {
        if (!id.isTest()) {
            return;
        }
        if (result.getStatus() == TestExecutionResult.Status.SUCCESSFUL) {
            return;
        }

        var out = new StringBuilder(tag(BOLD_RED, "FAILED", progress.get())).append(' ').append(label(id));
        result.getThrowable().ifPresent(t -> out.append("  exception: ").append(t.toString()));
        System.out.print(out);
    }

    private String tag(String color, String status, int n) {
        var slot = System.getProperty("gf.pool.slot");
        var sb = new StringBuilder().append(WHITE).append('[').append(color).append(status).append(WHITE).append(']');
        if (slot != null) {
            sb.append('[').append(color).append("SLOT").append(slot).append(WHITE).append(']');
        }
        if (module != null) {
            sb.append('[').append(color).append(module).append(WHITE).append(']');
        }
        if (total > 0) {
            sb.append('[').append(color).append(n).append('/').append(total).append(WHITE).append(']');
        }
        return sb.append(RESET).toString();
    }

    private static String label(TestIdentifier id) {
    	var className = id.getSource().flatMap(s -> {
            if (s instanceof ClassSource cs) return Optional.of(simple(cs.getClassName()));
            if (s instanceof MethodSource ms) return Optional.of(simple(ms.getClassName()));
            return Optional.empty();
        });
        return className.map(c -> c + "#" + id.getDisplayName()).orElse(id.getDisplayName());
    }

    private static String simple(String fqn) {
        int dot = fqn.lastIndexOf('.');
        return dot < 0 ? fqn : fqn.substring(dot + 1);
    }
}
