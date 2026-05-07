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
package ee.jakarta.tck.faces.test.faces20.jstl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import ee.jakarta.tck.faces.test.util.selenium.BaseITNG;
import ee.jakarta.tck.faces.test.util.selenium.WebPage;

class JstlIT extends BaseITNG {

    @Test
    void jstlCoreCWOTagTest() {
        WebPage page = getPage("faces/cwo/cwo_facelet.xhtml");

        assertEquals("PASSED", page.findElement(By.id("test1")).getText(), "test1");
        assertEquals("PASSED", page.findElement(By.id("test2")).getText(), "test2");
        assertEquals("PASSED", page.findElement(By.id("test3")).getText(), "test3");
    }

    @Test
    void jstlFunctionContainsTagTest() {
        WebPage page = getPage("faces/fncontains/fncontains.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("false", page.findElement(By.id("case2")).getText(), "case2");
        assertEquals("true", page.findElement(By.id("case3")).getText(), "case3");
        assertEquals("true", page.findElement(By.id("case4")).getText(), "case4");
        assertEquals("false", page.findElement(By.id("case5")).getText(), "case5");
        assertEquals("true", page.findElement(By.id("case6")).getText(), "case6");
    }

    @Test
    void jstlFunctionContainsIgnoreCaseTagTest() {
        WebPage page = getPage("faces/fncontainsignore/fncontainsignore.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("false", page.findElement(By.id("case2")).getText(), "case2");
        assertEquals("true", page.findElement(By.id("case3")).getText(), "case3");
        assertEquals("true", page.findElement(By.id("case4")).getText(), "case4");
        assertEquals("false", page.findElement(By.id("case5")).getText(), "case5");
        assertEquals("true", page.findElement(By.id("case6")).getText(), "case6");
    }

    @Test
    void jstlFunctionEndsWithTagTest() {
        WebPage page = getPage("faces/fnendswith/fnendswith.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("false", page.findElement(By.id("case2")).getText(), "case2");
        assertEquals("false", page.findElement(By.id("case3")).getText(), "case3");
        assertEquals("true", page.findElement(By.id("case4")).getText(), "case4");
        assertEquals("true", page.findElement(By.id("case5")).getText(), "case5");
        assertEquals("false", page.findElement(By.id("case6")).getText(), "case6");
        assertEquals("true", page.findElement(By.id("case7")).getText(), "case7");
        assertEquals("false", page.findElement(By.id("case8")).getText(), "case8");
    }

    @Test
    void jstlFunctionescapeXmlTagTest() {
        WebPage page = getPage("faces/fnescapexml/fnescapexml.xhtml");

        // fn:escapeXml turns '><&\'"' into '&gt;&lt;&amp;&#039;&#034;'; h:outputText
        // then re-escapes it for HTML so the browser shows the literal entity text.
        assertEquals("&gt;&lt;&amp;&#039;&#034;",
            page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("", page.findElement(By.id("case2")).getText(), "case2");
    }

    @Test
    void jstlFunctionindexOfTagTest() {
        WebPage page = getPage("faces/fnindexof/fnindexof.xhtml");

        for (int i = 1; i <= 6; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionjoinTagTest() {
        WebPage page = getPage("faces/fnjoin/fnjoin.xhtml");

        for (int i = 1; i <= 4; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionlengthTagTest() {
        WebPage page = getPage("faces/fnlength/fnlength.xhtml");

        for (int i = 1; i <= 9; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionreplaceTagTest() {
        WebPage page = getPage("faces/fnreplace/fnreplace.xhtml");

        for (int i = 1; i <= 8; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionsplitTagTest() {
        WebPage page = getPage("faces/fnsplit/fnsplit.xhtml");

        for (int i = 1; i <= 5; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    // The test page is named fnendswith.xhtml in the old TCK sources, but it tests fn:startsWith.
    @Test
    void jstlFunctionStartsWithTagTest() {
        WebPage page = getPage("faces/fnstartswith/fnendswith.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("false", page.findElement(By.id("case2")).getText(), "case2");
        assertEquals("false", page.findElement(By.id("case3")).getText(), "case3");
        assertEquals("true", page.findElement(By.id("case4")).getText(), "case4");
        assertEquals("true", page.findElement(By.id("case5")).getText(), "case5");
        assertEquals("false", page.findElement(By.id("case6")).getText(), "case6");
        assertEquals("true", page.findElement(By.id("case7")).getText(), "case7");
        assertEquals("false", page.findElement(By.id("case8")).getText(), "case8");
    }

    @Test
    void jstlFunctionsubStringTagTest() {
        WebPage page = getPage("faces/fnsubstring/fnsubstring.xhtml");

        for (int i = 1; i <= 5; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionsubStringAfterTagTest() {
        WebPage page = getPage("faces/fnsubstringafter/fnsubstringafter.xhtml");

        for (int i = 1; i <= 5; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctionsubStringBeforeTagTest() {
        WebPage page = getPage("faces/fnsubstringbefore/fnsubstringbefore.xhtml");

        for (int i = 1; i <= 5; i++) {
            assertEquals("true", page.findElement(By.id("case" + i)).getText(), "case" + i);
        }
    }

    @Test
    void jstlFunctiontoLowerCaseTagTest() {
        WebPage page = getPage("faces/fntolowercase/fntolowercase.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("true", page.findElement(By.id("case2")).getText(), "case2");
    }

    @Test
    void jstlFunctiontoUpperCaseTagTest() {
        WebPage page = getPage("faces/fntouppercase/fntouppercase.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("true", page.findElement(By.id("case2")).getText(), "case2");
    }

    @Test
    void jstlFunctiontrimTagTest() {
        WebPage page = getPage("faces/fntrim/fntrim.xhtml");

        assertEquals("true", page.findElement(By.id("case1")).getText(), "case1");
        assertEquals("true", page.findElement(By.id("case2")).getText(), "case2");
    }

    @Test
    void jstlCoreForEachTagTest() {
        WebPage page = getPage("faces/foreachtag/foreachtag_facelet.xhtml");
        String body = page.getSource();

        // case 1: String array iteration
        assertAllPresent(body, List.of("Firstname: Geddy", "Firstname: Alex", "Firstname: Neil"));

        // case 2: ArrayList iteration
        assertAllPresent(body, List.of("Lastname: Lee", "Lastname: Lifeson", "Lastname: Peart"));

        // case 3: Vector iteration
        assertAllPresent(body, List.of("Album: Exit Stage Left", "Album: Hemispheres", "Album: Farewell To Kings"));

        // case 4: LinkedList iteration
        assertAllPresent(body, List.of("Song: Freewill", "Song: 2112", "Song: Subdivisions"));

        // case 5: HashSet iteration
        assertAllPresent(body, List.of("Released: 1971", "Released: 1972", "Released: 1973"));

        // case 6: TreeSet iteration
        assertAllPresent(body, List.of("Rating: 8", "Rating: 9", "Rating: 10"));

        // case 7: begin attribute
        assertAllPresent(body, List.of("Begin: Peart"));

        // case 8: end attribute
        assertAllPresent(body, List.of("End: Lee"));

        // case 9: step attribute
        assertAllPresent(body, List.of("Step: Lee", "Step: Peart"));

        // case 10: varStatus count
        assertAllPresent(body, List.of("VSCo: 1", "VSCo: 2", "VSCo: 3"));

        // case 11: varStatus current
        assertAllPresent(body, List.of("VSCu: Lee", "VSCu: Lifeson", "VSCu: Peart"));

        // case 12: varStatus index
        assertAllPresent(body, List.of("VSI: 0", "VSI: 1", "VSI: 2"));

        // case 13: varStatus last
        assertAllPresent(body, List.of("VSL: false", "VSL: true"));
    }

    @Test
    void jstlCoreIfTagTest() {
        WebPage page = getPage("faces/iftag/iftag_facelet.xhtml");

        assertEquals("true", page.findElement(By.id("test1")).getText(), "test1");
        assertEquals("false", page.findElement(By.id("test2")).getText(), "test2");
        assertEquals("true", page.findElement(By.id("test3")).getText(), "test3");
        assertEquals("false", page.findElement(By.id("test4")).getText(), "test4");
        assertEquals("true", page.findElement(By.id("test5")).getText(), "test5");
        assertEquals("false", page.findElement(By.id("test6")).getText(), "test6");
    }

    @Test
    void jstlCoreURITest() {
        WebPage page = getPage("faces/iftag/uri_test_facelet.xhtml");

        assertEquals("true", page.findElement(By.id("old_way")).getText(), "old_way");
        assertEquals("true", page.findElement(By.id("new_way")).getText(), "new_way");
    }

    private static void assertAllPresent(String body, List<String> expected) {
        for (String item : expected) {
            assertTrue(body.contains(item), "Expected body to contain: " + item);
        }
    }
}
