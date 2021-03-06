/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2016 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.platform.bungeecord;

import com.demonwav.mcdev.asset.PlatformAssets;
import com.demonwav.mcdev.insight.generation.ui.EventGenerationPanel;
import com.demonwav.mcdev.platform.AbstractModuleType;
import com.demonwav.mcdev.platform.PlatformType;
import com.demonwav.mcdev.platform.bungeecord.generation.BungeeCordEventGenerationPanel;
import com.demonwav.mcdev.platform.bungeecord.util.BungeeCordConstants;
import com.demonwav.mcdev.util.CommonColors;

import com.google.common.collect.ImmutableList;
import com.intellij.openapi.module.Module;
import com.intellij.psi.PsiClass;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.swing.Icon;

public class BungeeCordModuleType extends AbstractModuleType<BungeeCordModule> {

    private static final BungeeCordModuleType instance = new BungeeCordModuleType();

    private static final String ID = "BUNGEECORD_MODULE_TYPE";
    private static final List<String> IGNORED_ANNOTATIONS = ImmutableList.of(BungeeCordConstants.HANDLER_ANNOTATION);
    private static final List<String> LISTENER_ANNOTATIONS = ImmutableList.of(BungeeCordConstants.HANDLER_ANNOTATION);

    private BungeeCordModuleType() {
        super("net.md-5", "bungeecord-api");
        CommonColors.applyStandardColors(this.colorMap, BungeeCordConstants.CHAT_COLOR_CLASS);
    }

    @NotNull
    public static BungeeCordModuleType getInstance() {
        return instance;
    }

    @Override
    public PlatformType getPlatformType() {
        return PlatformType.BUNGEECORD;
    }

    @Override
    public Icon getIcon() {
        return PlatformAssets.BUNGEECORD_ICON;
    }

    @Override
    public String getId() {
        return ID;
    }

    @NotNull
    @Override
    public List<String> getIgnoredAnnotations() {
        return IGNORED_ANNOTATIONS;
    }

    @NotNull
    @Override
    public List<String> getListenerAnnotations() {
        return LISTENER_ANNOTATIONS;
    }

    @NotNull
    @Override
    public BungeeCordModule generateModule(Module module) {
        return new BungeeCordModule(module);
    }

    @Override
    public boolean isEventGenAvailable() {
        return true;
    }

    @NotNull
    @Override
    public EventGenerationPanel getEventGenerationPanel(@NotNull PsiClass chosenClass) {
        return new BungeeCordEventGenerationPanel(chosenClass);
    }
}
