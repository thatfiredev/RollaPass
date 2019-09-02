package io.github.rosariopfernandes.rollapass;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.closeSoftKeyboard;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    @Rule
    public ActivityTestRule<MainActivity> activityTestRule=
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void canAddAccount() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.editTextWebsite)).perform(typeText("android.com"));
        onView(withId(R.id.editTextUsername)).perform(typeText("rosariopfernandes"));
        onView(withId(R.id.editTextPassword)).perform(typeText("espresso"));
        closeSoftKeyboard();
        onView(withId(R.id.fab)).perform(click());

        //onView(withText("android.com")).check(matches(isDisplayed()));
        //onData(withItemContent(""))
    }
}
