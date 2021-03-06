/*
 * Minecraft Dev for IntelliJ
 *
 * https://minecraftdev.org
 *
 * Copyright (c) 2016 minecraft-dev
 *
 * MIT License
 */

package com.demonwav.mcdev.platform.canary.gradle;

import com.demonwav.mcdev.buildsystem.gradle.AbstractDataService;
import com.demonwav.mcdev.platform.canary.CanaryModuleType;

import org.jetbrains.annotations.NotNull;

public class CanaryDataService extends AbstractDataService {

    public CanaryDataService() {
        this(CanaryModuleType.getInstance());
    }

    public CanaryDataService(@NotNull CanaryModuleType type) {
        super(type);
    }

}
