package cz.avocado.postal.util;

import cz.avocado.postal.model.Parcel;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

@RunWith(JUnit4.class)
public class SanitizerTest {

    @Test
    public void sanitize_nullInput_returnsEmptyString() {
        String result = ParcelUtils.sanitizeInput(null);
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        Assert.assertTrue(result.isBlank());
    }

    @Test
    public void sanitize_whitespaceOnly_returnsEmptyString() {
        String result = ParcelUtils.sanitizeInput("           ");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isEmpty());
        Assert.assertTrue(result.isBlank());
    }

    @Test
    public void sanitize_trailingLeading_returnsTrimmedResult() {
        String result = ParcelUtils.sanitizeInput("  lxar abc         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc", result);
    }

    @Test
    public void sanitize_internalSpacesTruncated_returnsCorrectResult() {
        String result = ParcelUtils.sanitizeInput("  lxar    \r  abc         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc", result);
    }

    @Test
    public void sanitize_internalSpacesTruncated_returnsCorrectResult_2() {
        String result = ParcelUtils.sanitizeInput("  lxar    \r  abc      \rdef         ");
        Assert.assertNotNull(result);
        Assert.assertEquals("lxar abc def", result);
    }

    @Test
    public void process_nullParam_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput(null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_emptyParam_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_blankParam_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("   \r            ");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_singleValue_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("abc");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_tooManyValues_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("abc def a fa as");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectDataTypeForWeightValue_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("abc 12543");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectDataTypeForParcelNumber_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("2.2 def");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectParcelChars_emptyOptionalReturned() {
        Optional<Parcel> result = ParcelUtils.procesInput("2.2 4563");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectParcelChars_emptyOptionalReturned_2() {
        Optional<Parcel> result = ParcelUtils.procesInput("2.2 45636445798");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectParcelDataType_emptyOptionalReturned_3() {
        Optional<Parcel> result = ParcelUtils.procesInput("2.2 456.36445798");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_correctInput_okayResult() {
        Optional<Parcel> result = ParcelUtils.procesInput("2.2 57486");
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals((float) 2.2, (float) result.get().getPackageWeight(), 0);
        Assert.assertEquals(57486, (int) result.get().getPackageNumber());
    }

    @Test
    public void process_correctInputAcceptsIntegerWeight_okayResult() {
        Optional<Parcel> result = ParcelUtils.procesInput("2 57486");
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals((float) 2.0, (float) result.get().getPackageWeight(), 0);
        Assert.assertEquals(57486, (int) result.get().getPackageNumber());
    }

}
