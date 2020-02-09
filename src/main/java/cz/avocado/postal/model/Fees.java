package cz.avocado.postal.model;

import java.util.Map;
import java.util.TreeMap;

public class Fees {

    private Map<Float, Float> feeMap = new TreeMap<>();

    public void addFee(float weightLimit, float fee) {
        this.feeMap.put(weightLimit, fee);
    }

    /**
     * This method returns fee applicable for the weight specified. If no fees are specified a negative value is returned.
     *
     * @param weight
     * @return
     */
    public Float getFee(float weight) {
        var feeKey = feeMap.keySet().stream().filter(it -> it < weight).reduce(Float::max).orElse((float) -1);
        if (feeKey < 0) {
            return feeKey;
        }
        if (!feeMap.containsKey(feeKey)) {
            return (float) -1;
        }
        return this.feeMap.get(feeKey);
    }

}
