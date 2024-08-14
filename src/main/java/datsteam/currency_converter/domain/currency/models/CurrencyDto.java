package datsteam.currency_converter.domain.currency.models;

import datsteam.currency_converter.domain.currency.enums.CurrencyEnum;

public class CurrencyDto {
    private CurrencyEnum currencyIn;
    private CurrencyEnum currencyOut;
    private Float value;
    private Float count;
    private Float sum;

    public CurrencyDto() {
    }

    public CurrencyDto(CurrencyEnum currencyIn, CurrencyEnum currencyOut, Float value) {
        this.currencyIn = currencyIn;
        this.currencyOut = currencyOut;
        this.value = value;
    }

    public CurrencyEnum getCurrencyIn() {
        return this.currencyIn;
    }

    public CurrencyEnum getCurrencyOut() {
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

    public void setCurrencyIn(CurrencyEnum currencyIn) {
        this.currencyIn = currencyIn;
    }

    public void setCurrencyOut(CurrencyEnum currencyOut) {
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
