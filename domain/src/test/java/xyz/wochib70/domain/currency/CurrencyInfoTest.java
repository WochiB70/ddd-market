package xyz.wochib70.domain.currency;

import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

import java.util.Objects;

class CurrencyInfoTest {

    @Test
    void currencyInfoTest() {
        CurrencyInfo info = new CurrencyInfo("name", "description");
        Assert.isTrue(Objects.equals(new CurrencyInfo("name", "description"), info), "CurrencyInfo对象相等");
    }


    @Test
    void createCurrencyInfoWithNullNameTest() {
        try {
            new CurrencyInfo(null, "description");
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建CurrencyInfo对象时，name不能为null");
        }
    }

    @Test
    void createCurrencyInfoWithBlankNameTest() {
        try {
            new CurrencyInfo("", "description");
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建CurrencyInfo对象时，name不能为空");
        }
    }

    @Test
    void createCurrencyInfoWithNameLengthGreaterThan20Test() {
        try {
            new CurrencyInfo("123456789012345678901", "description");
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建CurrencyInfo对象时，name长度不能超过20个字符");
        }
    }

    @Test
    void createCurrencyInfoWithDescriptionLengthGreaterThan50Test() {
        try {
            new CurrencyInfo("name", "description".repeat(10));
        } catch (Exception e) {
            Assert.isTrue(e instanceof IllegalArgumentException, "创建CurrencyInfo对象时，description长度不能超过50个字符");
        }
    }
}