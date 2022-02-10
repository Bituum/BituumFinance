package dev.bituum.tinkoffmservice.dto;

import dev.bituum.tinkoffmservice.model.LastPrice;
import dev.bituum.tinkoffmservice.model.Quotation;
import lombok.Getter;

@Getter
public class CleanLastPriceDto {
    private double lastPrice;

    public CleanLastPriceDto(LastPrice lastPrice) {
        this.lastPrice = convert(lastPrice.getPrice());
    }

    private Double convert(Quotation quotation){
        String floatPart = quotation.getNano() == null ? "0" : quotation.getNano();
        String price = quotation.getUnits() + "." + floatPart;
        return Double.parseDouble(price);
    }
}
