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
@SuppressWarnings("UnstableApiUsage")
public class ThemeSettingsState implements PersistentStateComponent<ThemeSettingsState> {
    private final ThemesManager themesManager = new ThemesManager();

    public String lightThemeId = this.themesManager.getDefaultLightTheme().getId();
    public String darkThemeId = this.themesManager.getDefaultDarkTheme().getId();
    public boolean isUnderDarculaState = this.themesManager.isIdeaUnderDarcula();

    protected void switchIsIdeaUnderDarcula(boolean withChangingState) {
        if (withChangingState) {
            this.isUnderDarculaState = !this.isUnderDarculaState;
        }
    }

    @Nullable
    protected String getNextThemeIdToInstall(boolean withChangingState) {
        String nextThemId;

        if (withChangingState) {
            nextThemId = this.isUnderDarculaState ? this.lightThemeId : this.darkThemeId;
        } else {
            nextThemId = this.isUnderDarculaState ? this.darkThemeId : this.lightThemeId;
        }

        return nextThemId;
    }

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
