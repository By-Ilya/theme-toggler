package io.theme.toggler.themetoggler;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ChangeThemeStatusBarWidgetFactory implements StatusBarWidgetFactory {
    private static final String THEME_TOGGLER_ID = "theme-toggler";
    private static final String THEME_TOGGLER_DISPLAY_NAME = "Theme toggler";

    @Override
    public @NonNls @NotNull String getId() {
        return THEME_TOGGLER_ID;
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return THEME_TOGGLER_DISPLAY_NAME;
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new ChangeThemeWidget();
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
        widget.dispose();
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return false;
    }
}
