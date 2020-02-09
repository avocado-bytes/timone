package cz.avocado.postal.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class UtilsTest {

    @Test
    public void sanitize_nullInput_returnsEmptyString() {
        String result = Utils.sanitizeInput(null);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        Assert.assertTrue(result.isBlank());
    }

    @Test
    public void sanitize_whitespaceOnly_returnsEmptyString() {
        String result = Utils.sanitizeInput("           ");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        Assert.assertTrue(result.isBlank());
    }

    @Test
    public void sanitize_trailingLeading_returnsTrimmedResult() {
        String result = Utils.sanitizeInput("  lxar abc         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc", result);
    }

    @Test
    public void sanitize_internalSpacesTruncated_returnsCorrectResult() {
        String result = Utils.sanitizeInput("  lxar    \r  abc         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc", result);
    }

    @Test
    public void sanitize_internalSpacesTruncated_returnsCorrectResult_2() {
        String result = Utils.sanitizeInput("  lxar    \r  abc      \rdef         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc def", result);
    }

}
