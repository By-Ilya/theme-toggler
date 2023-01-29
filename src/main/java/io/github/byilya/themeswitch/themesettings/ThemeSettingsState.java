package io.github.byilya.themeswitch.themesettings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@State(
        name = "io.github.byilya.themeswitch.themesettings.ThemeSettingsState",
        storages = @Storage("ThemeSwitchPluginSettings.xml")
)
public class ThemeSettingsState implements PersistentStateComponent<ThemeSettingsState> {
    public String lightThemeId = ThemeHelpers.getThemeId(ThemeHelpers.getBaseLightTheme());
    public String darkThemeId = ThemeHelpers.getThemeId(ThemeHelpers.getBaseDarkTheme());
    public boolean isUnderDarculaState = ThemeHelpers.isIdeaUnderDarcula();

    public static ThemeSettingsState getInstance() {
        return ApplicationManager.getApplication().getService(ThemeSettingsState.class);
    }

    @Override
    public @Nullable ThemeSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull ThemeSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}
