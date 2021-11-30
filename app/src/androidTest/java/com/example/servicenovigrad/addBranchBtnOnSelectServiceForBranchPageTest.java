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

public class addBranchBtnOnSelectServiceForBranchPageTest {

    @Rule
    public ActivityTestRule<AddBranch> mActivityTestRule = new ActivityTestRule<AddBranch>(AddBranch.class);
    private AddBranch mActivity = null;

    @Before
    public void setUp() throws Exception {
        mActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void launchOfServiceActivityOnButtonClick() {
        assertNotNull(mActivity.findViewById(R.id.continue_button));
        onView(withId(R.id.continue_button)).perform(click());
    }

    @After
    public void tearDown() throws Exception {
        mActivity = null;
    }
}