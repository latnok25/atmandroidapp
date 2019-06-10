package com.latnok.atmapp

import android.app.Activity
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4


import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import android.support.test.rule.ActivityTestRule
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.view.View
import org.hamcrest.Matchers.allOf

@RunWith(AndroidJUnit4::class)
class MainActivitytest {


    @Rule
    var mainActivityTestRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun buttonClick_goToSecondActivity() {
        //onView(allOf(withId(R.id.edtextnumber),withText("2222222222")));
        onView(allOf<View>(withId(R.id.select))).perform(click())
        onView(withId(R.id.edtextnumber)).check(matches(isDisplayed()))
    }
}
