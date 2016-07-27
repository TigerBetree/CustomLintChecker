package com.custom.lint.detectors;

import com.android.tools.lint.client.api.JavaParser;
import com.android.tools.lint.detector.api.Category;
import com.android.tools.lint.detector.api.Detector;
import com.android.tools.lint.detector.api.Implementation;
import com.android.tools.lint.detector.api.Issue;
import com.android.tools.lint.detector.api.JavaContext;
import com.android.tools.lint.detector.api.Location;
import com.android.tools.lint.detector.api.Scope;
import com.android.tools.lint.detector.api.Severity;

import java.util.Collections;
import java.util.List;

import lombok.ast.AstVisitor;
import lombok.ast.ForwardingAstVisitor;
import lombok.ast.MethodInvocation;
import lombok.ast.Node;

/**
 * Created by liuhu on 2016/7/15.
 */
public class UseTextUtilsDetector extends Detector implements Detector.JavaScanner {

    private static final String ISSUE_ID = "UseTextUtils";

    private static final String ISSUE_DESCRIPTION = "Avoid use \"\".equals(str)/str.equals(\"\").";
    private static final String ISSUE_EXPLANATION = "please use TextUtils.isEmpty().";

    public static final Issue ISSUE = Issue.create(
            ISSUE_ID,
            ISSUE_DESCRIPTION,
            ISSUE_EXPLANATION,
            Category.SECURITY, 5, Severity.ERROR,
            new Implementation(UseTextUtilsDetector.class, Scope.JAVA_FILE_SCOPE));

    @Override
    public List<Class<? extends Node>> getApplicableNodeTypes() {
        return Collections.<Class<? extends Node>>singletonList(MethodInvocation.class);
    }

    private static final String CHECK_STRING1 = ".equals(\"\")";
    private static final String CHECK_STRING2 = "\"\".equals(";

    @Override
    public AstVisitor createJavaVisitor(final JavaContext context) {
        return new ForwardingAstVisitor() {
            @Override
            public boolean visitMethodInvocation(MethodInvocation node) {
                Location location = context.getLocation(node);

                if (node.toString().contains(CHECK_STRING1)
                        || node.toString().contains(CHECK_STRING2)) {
                    context.report(ISSUE, node, location, ISSUE_EXPLANATION);
                    return true;
                }

                JavaParser.ResolvedNode resolve = context.resolve(node);
                if (resolve instanceof JavaParser.ResolvedMethod) {
                    JavaParser.ResolvedMethod method = (JavaParser.ResolvedMethod) resolve;
                    JavaParser.ResolvedClass containingClass = method.getContainingClass();
                    if (containingClass.matches(CHECK_STRING1)
                            || containingClass.matches(CHECK_STRING1)) {
                        context.report(ISSUE, node, location, ISSUE_EXPLANATION);
                        return true;
                    }
                }

                return super.visitMethodInvocation(node);
            }
        };
    }
}
