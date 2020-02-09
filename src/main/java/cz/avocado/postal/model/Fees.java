package cz.avocado.postal.model;

import java.util.Map;
import java.util.TreeMap;

public class Fees {

    private Map<Float, Float> feeMap = new TreeMap<>();

    public void addFee(float weightLimit, float fee) {
        this.feeMap.put(weightLimit, fee);
    }

    public Float getFee(float weight) {
        var feeKey = feeMap.keySet().stream().filter(it -> it < weight).reduce(Float::max).orElse((float) -1);
        if (feeKey < 0) {
            return feeKey;
        }
        return this.feeMap.get(feeKey);
    }

}
