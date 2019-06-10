package com.latnok.atmapp

import android.app.Activity

import org.junit.Test
import org.junit.Assert.assertFalse
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue

class Pinvalidatortest {

    @Test
    fun pinvalidator_correctpin_returnstrue() {
        assertFalse(MainActivity.isvalid("2222"))
        assertFalse(Transaction.isvalid("2222"))
    }

    @Test
    fun pinvalidator_wrongpin_returnstrue() {
        assertFalse(MainActivity.isvalid(""))
        assertFalse(Transaction.isvalid(""))
    }

    @Test
    fun pinvalidator_null_returnstrue() {
        assertFalse(MainActivity.isvalid(null))
        assertFalse(Transaction.isvalid(null))
    }

}
