package io.github.byilya.themeswitch.themesettings;

import javax.swing.*;

import com.intellij.ide.ui.laf.UIThemeLookAndFeelInfo;
import org.jetbrains.annotations.Nullable;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.util.NlsContexts.ConfigurableName;

@SuppressWarnings("UnstableApiUsage")
public class ThemeSettingsConfigurable implements Configurable {
  private static final String THEME_SETTINGS_DISPLAY_NAME = "Theme Switch settings";

  private final ThemeSwitchHelpers themeSwitchHelpers = new ThemeSwitchHelpers();

  private String updatingLightThemeId = ThemeSettingsState.getInstance().lightThemeId;
  private String updatingDarkThemeId = ThemeSettingsState.getInstance().darkThemeId;

  private ThemeSettingsPanel themeSettingsPanel;

  private void changeLightTheme(UIThemeLookAndFeelInfo updatingLightTheme) {
    this.updatingLightThemeId = updatingLightTheme.getId();
  }

  private void changeDarkTheme(UIThemeLookAndFeelInfo updatingDarkTheme) {
    this.updatingDarkThemeId = updatingDarkTheme.getId();
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

    this.themeSwitchHelpers.changeTheme(false);
  }

  @Override
  public @ConfigurableName String getDisplayName() {
    return THEME_SETTINGS_DISPLAY_NAME;
  }
}