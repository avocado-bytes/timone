package cz.avocado.postal.util;

import cz.avocado.postal.model.Pair;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Optional;

@RunWith(JUnit4.class)
public class FeeUtilsTest {

    @Test
    public void process_nullParam_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput(null);
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_emptyParam_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_blankParam_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("   \r            ");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_singleValue_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("abc");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_tooManyValues_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("abc def a fa as");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectDataTypeForWeightValue_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("abc 12543");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_incorrectDataTypeForFeeNumber_emptyOptionalReturned() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("2.2 def");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void process_correctInput_okayResult() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("2.2 57.4");
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals((float) 2.2, (float) result.get().getFirst(), 0);
        Assert.assertEquals((float) 57.4, (float) result.get().getSecond(), 0);
    }

    @Test
    public void process_correctInputAcceptsIntegerWeight_okayResult() {
        Optional<Pair<Float, Float>> result = FeeUtils.processInput("2 56");
        Assert.assertFalse(result.isEmpty());
        Assert.assertEquals((float) 2.0, (float) result.get().getFirst(), 0);
        Assert.assertEquals((float) 56.0, (float) result.get().getSecond(), 0);
    }

}
