package cz.avocado.postal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@EqualsAndHashCode
public class Parcel {

    @NotNull(message = "Package weight cannot be null")
    @Positive(message = "Weight cannot be a negative value")
    private Float packageWeight;
    @NotNull(message = "Packge number cannot be null")
    private Integer packageNumber;
    private Float fee = -1f;

    public Parcel(Float packageWeight, Integer packageNumber) {
        this.packageWeight = packageWeight;
        this.packageNumber = packageNumber;
    }

    @Override
    public String toString() {
        if (this.fee > 0) {
            return String.format("%d %.3f %f", this.packageNumber, this.packageWeight, this.fee);
        }

        return String.format("%d %.3f", this.packageNumber, this.packageWeight);
    }
}
