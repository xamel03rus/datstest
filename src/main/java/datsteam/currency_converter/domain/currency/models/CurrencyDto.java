package datsteam.currency_converter.domain.currency.models;

import datsteam.currency_converter.domain.currency.enums.CurrencyInEnum;
import datsteam.currency_converter.domain.currency.enums.CurrencyOutEnum;

public class CurrencyDto {
    private CurrencyInEnum currencyIn;
    private CurrencyOutEnum currencyOut;
    private Float value;
    private Float count;
    private Float sum;

    public CurrencyDto() {
    }

    public CurrencyDto(CurrencyInEnum currencyIn, CurrencyOutEnum currencyOut, Float value) {
        this.currencyIn = currencyIn;
        this.currencyOut = currencyOut;
        this.value = value;
        this.count = count;
        this.sum = sum;
    }

    public CurrencyInEnum getCurrencyIn() {
        return this.currencyIn;
    }

    public CurrencyOutEnum getCurrencyOut() {
        return this.currencyOut;
    }

    public Float getValue() {
        return this.value;
    }

    public Float getCount() {
        return this.count;
    }

    public Float getSum()
    {
        return this.sum;
    }

    public void setCurrencyIn(CurrencyInEnum currencyIn) {
        this.currencyIn = currencyIn;
    }

    public void setCurrencyOut(CurrencyOutEnum currencyOut) {
        this.currencyOut = currencyOut;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public void setCount(Float count) {
        this.count = count;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }
}
