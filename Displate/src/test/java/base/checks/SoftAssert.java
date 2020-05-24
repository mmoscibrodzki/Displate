package base.checks;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class SoftAssert {

    private static final List<String> ERRORS = new ArrayList<>();

    private SoftAssert() {
    }

    private static void addError(AssertionError ae) {
        final StackTraceElement ste = findRoutCause(ae.getStackTrace());
        final StringBuilder msg = new StringBuilder(ae.getMessage().replace("<", "{").replace(">", "}"));

        msg.append("\n\tat ").append(ste.getClassName()).append(".").append(ste.getMethodName()).append("(").append(ste.getFileName())
                .append(":").append(ste.getLineNumber()).append(")");
        ERRORS.add(msg.toString());
    }

    private static StackTraceElement findRoutCause(StackTraceElement[] stackTrace) {
        boolean softAssert = false;
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (SoftAssert.class.getCanonicalName().equals(stackTraceElement.getClassName())) {
                softAssert = true;
            } else {
                if (softAssert) {
                    return stackTraceElement;
                }
            }
        }
        return null;
    }

    public static void assertTrue(String message, boolean condition) {
        try {
            Assert.assertTrue(message == null ? "assertion failed" : message, condition);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(null, condition);
    }

    public static void assertFalse(String message, boolean condition) {
        assertTrue(message, !condition);
    }

    public static void assertFalse(boolean condition) {
        assertTrue(null, !condition);
    }

    public static void assertEquals(String mesage, Object expected, Object actual) {
        try {
            Assert.assertEquals(mesage, expected, actual);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertNotEquals(String message, Object unExpected, Object actual) {
        try {
            Assert.assertNotEquals(message, unExpected, actual);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertNotEquals(Object unExpected, Object actual) {
        assertNotEquals(null, unExpected, actual);
    }

    public static void assertEquals(String mesage, long expected, long actual) {
        try {
            Assert.assertEquals(mesage, expected, actual);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertEquals(long expected, long actual) {
        assertEquals(null, expected, actual);
    }

    public static void assertNotEquals(String message, long unExpected, long actual) {
        try {
            Assert.assertNotEquals(message, unExpected, actual);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertNotEquals(long unExpected, long actual) {
        assertNotEquals(null, unExpected, actual);
    }

    public static void assertNotNull(String message, Object object) {
        try {
            Assert.assertNotNull(message, object);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertNotNull(Object object) {
        assertNotNull(null, object);
    }

    public static void assertNull(String message, Object object) {
        try {
            Assert.assertNull(message, object);
        } catch (AssertionError ae) {
            addError(ae);
        }
    }

    public static void assertNull(Object object) {
        assertNull(null, object);
    }

    public static void checkAssertions() {
        if (!ERRORS.isEmpty()) {
            final String errorMessage = "SoftAssertion\n" + StringUtils.join(ERRORS, "\n");
            clearAssertions();
            Assert.fail(errorMessage);
        }
    }

    public static void clearAssertions() {
        ERRORS.clear();
    }

}
