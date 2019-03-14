package com.oocl.ita.demo.po;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RecordTest {
    @Test
    public void should_test_setMethod() {
        //Given
        Record record = new Record();

        //When
        record.setId(1);
        record.setMoney("200");
        record.setType("income");

        //Then
        Assert.assertNotNull(record);
        Assert.assertEquals((Integer) 1, record.getId());
        Assert.assertEquals("200", record.getMoney());
        Assert.assertEquals("income", record.getType());
    }

}