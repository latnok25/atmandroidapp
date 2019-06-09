package com.latnok.atmapp;

import android.app.Activity;

import org.junit.Test;
import static  org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static  org.junit.Assert.assertTrue;

public class Pinvalidatortest {

    @Test
    public void pinvalidator_correctpin_returnstrue(){
        assertFalse(MainActivity.isvalid("2222"));
        assertFalse(Transaction.isvalid("2222"));
    }

    @Test
    public void pinvalidator_wrongpin_returnstrue(){
        assertFalse(MainActivity.isvalid(""));
        assertFalse(Transaction.isvalid(""));
    }

    @Test
    public void pinvalidator_null_returnstrue(){
        assertFalse(MainActivity.isvalid(null));
        assertFalse(Transaction.isvalid(null));
    }

}
