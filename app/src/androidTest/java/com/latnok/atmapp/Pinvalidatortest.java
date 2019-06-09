package com.latnok.atmapp;

import org.junit.Test;
import static  org.junit.Assert.assertFalse;
import static  org.junit.Assert.assertTrue;

public class Pinvalidatortest {

    @Test
    public void pinvalidator_correctpin_returnstrue(){
        assertFalse(Transaction.isvalid("2222"));
    }


}
