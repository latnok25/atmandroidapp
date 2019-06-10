package com.latnok.atmapp

import android.support.test.rule.ActivityTestRule

import org.junit.Rule
import org.junit.Test

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.view.View
import org.hamcrest.Matchers.allOf

class transactiontest {

    @Rule
    var mainActivityTestRule = ActivityTestRule(Transaction::class.java)


    @Test
    fun buttonClick_goToSecondActivity() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m500))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m1000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m1000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m2000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m2000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m3000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m3000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m5000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m5000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m10000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m10000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_m20000() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m20000))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

    @Test
    fun buttonClick_my() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.myvalue))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }
}
