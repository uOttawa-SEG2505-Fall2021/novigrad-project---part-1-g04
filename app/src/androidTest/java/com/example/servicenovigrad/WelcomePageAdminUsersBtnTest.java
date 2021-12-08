package com.example.servicenovigrad;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class WelcomePageAdminUsersBtnTest {

    @Rule
    public ActivityTestRule<WelcomePageAdmin> mActivityTestRule = new ActivityTestRule<WelcomePageAdmin>(WelcomePageAdmin.class);
    private WelcomePageAdmin mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void launchOfServiceActivityOnButtonClick() {
        assertNotNull(mActivity.findViewById(R.id.usersButton));
        onView(withId(R.id.usersButton)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}