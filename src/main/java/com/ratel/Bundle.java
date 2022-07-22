package com.ratel;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.NotNull;

public class Bundle extends DynamicBundle {

    private static final String BUNDLE = "messages.bundle";

    public Bundle(@NotNull String pathToBundle) {
        super(pathToBundle);
    }


}
