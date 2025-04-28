package com.jbatrina.BankingApplication.util;

import java.math.BigDecimal;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;

import org.javamoney.moneta.Money;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.Transient;

@Converter
public class MoneyToBigDecimalConverter implements AttributeConverter<Money, BigDecimal> {
	@Transient
    private static final CurrencyUnit baseCurrencyUnit = Monetary.getCurrency("PHP");
	@Transient
    private static final CurrencyConversion baseCurrencyConversion = MonetaryConversions.getConversion(baseCurrencyUnit);


    @Override
    public BigDecimal convertToDatabaseColumn(Money money) {
        if (money == null) {
            return null;
        }

        return money.with(baseCurrencyConversion).getNumberStripped();
    }

    @Override
    public Money convertToEntityAttribute(BigDecimal dbMoney) {
        if (dbMoney == null) {
            return null;
        }

        return Money.of(dbMoney, baseCurrencyUnit);
    }
}