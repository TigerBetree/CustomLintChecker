package com.custom.lint.registry;

import com.android.tools.lint.client.api.IssueRegistry;
import com.android.tools.lint.detector.api.Issue;
import com.custom.lint.detectors.EnumDetector;
import com.custom.lint.detectors.LogDetector;
import com.custom.lint.detectors.UseSparseArrayDetector;
import com.custom.lint.detectors.UseTextUtilsDetector;

import java.util.Arrays;
import java.util.List;

/**
 * The list of issues that will be checked when running <code>lint</code>.
 */
@SuppressWarnings("unused")
public class CustomIssueRegistry extends IssueRegistry {

    private List<Issue> mIssues = Arrays.asList(
            LogDetector.ISSUE,
            EnumDetector.ISSUE,
            UseSparseArrayDetector.ISSUE,
            UseTextUtilsDetector.ISSUE

//            ,MinSdkDetector.ISSUE
//            ,TodoDetector.ISSUE
    );

    public CustomIssueRegistry() {
    }

    @Override
    public List<Issue> getIssues() {
        return mIssues;
    }
}
