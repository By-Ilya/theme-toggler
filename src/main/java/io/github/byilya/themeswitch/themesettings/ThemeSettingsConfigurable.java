package io.github.byilya.themeswitch.themesettings;

import javax.swing.*;

import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;

public class ThemeSettingsConfigurable implements Configurable {
  private static final String THEME_SETTINGS_DISPLAY_NAME = "Theme Switch settings";

  private String updatingLightThemeId = ThemeSettingsState.getInstance().lightThemeId;
  private String updatingDarkThemeId = ThemeSettingsState.getInstance().darkThemeId;

  private ThemeSettingsPanel themeSettingsPanel;

  private void changeLightTheme(UIManager.LookAndFeelInfo updatingLightTheme) {
    this.updatingLightThemeId = updatingLightTheme.toString();
  }

  private void changeDarkTheme(UIManager.LookAndFeelInfo updatingDarkTheme) {
    this.updatingDarkThemeId = updatingDarkTheme.toString();
  }

  @Override
  public @Nullable JComponent createComponent() {
    ThemeSettingsState settingsState = ThemeSettingsState.getInstance();
    this.themeSettingsPanel = new ThemeSettingsPanel(
            settingsState.lightThemeId,
            settingsState.darkThemeId,
            this::changeLightTheme,
            this::changeDarkTheme
    );

    return this.themeSettingsPanel.getComponent();
  }

  @Override
  public boolean isModified() {
    ThemeSettingsState settingsState = ThemeSettingsState.getInstance();

    return !settingsState.lightThemeId.equals(this.updatingLightThemeId) ||
            !settingsState.darkThemeId.equals(this.updatingDarkThemeId);
  }

  @Override
  public void reset() {
    if (this.themeSettingsPanel != null) {
      ThemeSettingsState settingsState = ThemeSettingsState.getInstance();
      this.themeSettingsPanel.resetSelectedThemes(settingsState.lightThemeId, settingsState.darkThemeId);
    }
  }

  @Override
  public void apply() throws ConfigurationException {
    ThemeSettingsState settingsState = ThemeSettingsState.getInstance();
    settingsState.lightThemeId = this.updatingLightThemeId;
    settingsState.darkThemeId = this.updatingDarkThemeId;

    ThemeSwitchHelpers.changeTheme(false);
  }

  @Override
  public @ConfigurableName String getDisplayName() {
    return THEME_SETTINGS_DISPLAY_NAME;
  }
}