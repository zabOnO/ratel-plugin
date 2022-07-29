package com.ratel;

import com.intellij.DynamicBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.PropertyKey;

import java.util.function.Supplier;

/**
 * @author bobo
 */
public class RatelBundle extends DynamicBundle {


    private static final @NonNls String BUNDLE = "messages.bundle";

    private static final RatelBundle RATEL_BUNDLE_INSTANCE = new RatelBundle();

    private RatelBundle() {
        super(BUNDLE);
    }

    public static @NotNull @Nls String message(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key,@NotNull Object ... params) {
        return RATEL_BUNDLE_INSTANCE.getMessage(key, params);
    }

    public static @NotNull Supplier<String> messagePointer(@NotNull @PropertyKey(resourceBundle = BUNDLE) String key,@NotNull Object  ... params) {
        return RATEL_BUNDLE_INSTANCE.getLazyMessage(key, params);
    }

}
