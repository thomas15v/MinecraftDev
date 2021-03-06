/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2016 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.platform.canary.inspection;

import com.demonwav.mcdev.platform.canary.util.CanaryConstants;
import com.demonwav.mcdev.util.McPsiUtil;

import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiAnnotation;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.PsiModifierList;
import com.siyeh.ig.BaseInspection;
import com.siyeh.ig.BaseInspectionVisitor;
import com.siyeh.ig.InspectionGadgetsFix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CanaryListenerImplementedInspection extends BaseInspection {

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Canary HookHandler in class not implementing PluginListener";
    }

    @NotNull
    @Override
    protected String buildErrorString(Object... infos) {
        return "This class contains HookHandler methods but does not implement PluginListener.";
    }

    @Nullable
    @Override
    public String getStaticDescription() {
        return "All Canary HookHandler methods must reside in a class that implements PluginListener.";
    }

    @Nullable
    @Override
    protected InspectionGadgetsFix buildFix(Object... infos) {
        return new InspectionGadgetsFix() {
            @Override
            protected void doFix(Project project, ProblemDescriptor descriptor) {
                PsiClass psiClass = (PsiClass) infos[0];
                McPsiUtil.addImplements(psiClass, CanaryConstants.LISTENER_CLASS, project);
            }

            @Nls
            @NotNull
            @Override
            public String getName() {
                return "Implement PluginListener";
            }

            @Nls
            @NotNull
            @Override
            public String getFamilyName() {
                return getName();
            }
        };
    }

    @Override
    public BaseInspectionVisitor buildVisitor() {
        return new BaseInspectionVisitor() {
            @Override
            public void visitClass(PsiClass aClass) {
                final PsiMethod[] methods = aClass.getMethods();
                boolean isEventHandler = false;
                for (PsiMethod method : methods) {
                    PsiModifierList list = method.getModifierList();
                    PsiAnnotation annotation = list.findAnnotation(CanaryConstants.HANDLER_ANNOTATION);
                    if (annotation != null) {
                        isEventHandler = true;
                        break;
                    }
                }

                if (!isEventHandler) {
                    return;
                }

                final boolean inError = !McPsiUtil.extendsOrImplementsClass(aClass, CanaryConstants.LISTENER_CLASS);

                if (inError) {
                    registerClassError(aClass, aClass);
                }
            }
        };
    }
}
