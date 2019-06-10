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

class BalanceTest {

    @Rule
    var mainActivityTestRule = ActivityTestRule(Balance::class.java)


    @Test
    fun buttonClick_goToSecondActivity() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.m500))).perform(click())
        onView(withId(R.id.rf)).check(matches(isDisplayed()))
    }

}
