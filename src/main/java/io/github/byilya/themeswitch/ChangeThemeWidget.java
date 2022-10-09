package io.github.byilya.themeswitch;

import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ChangeThemeWidget implements StatusBarWidget {
    private static final String CHANGE_THEME_WIDGET_ID = "change-theme-widget";

    @Override
    public @NonNls @NotNull String ID() {
        return CHANGE_THEME_WIDGET_ID;
    }

    @Override
    public @Nullable WidgetPresentation getPresentation() {
        return new ChangeThemeWidgetPresentation();
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {}

    @Override
    public void dispose() {}
}
