package com.oocl.ita.demo.exceptions;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BadRequestExceptionTest {
    @Test
    public void should_test_constructor() {
        //Given //When
        BadRequestException badRequestException = new BadRequestException();

        //Then
        Assert.assertNotNull(badRequestException);

    }
}