package io.github.byilya.themeswitch.themesettings;

import com.intellij.ide.ui.laf.UIThemeLookAndFeelInfo;
import com.intellij.openapi.ui.ComboBox;
import kotlin.sequences.Sequence;

import java.awt.*;
import java.util.function.Consumer;

import javax.swing.*;

@SuppressWarnings("UnstableApiUsage")
class ThemeSettingsPanel {
  private static final String PANEL_TITLE = "Choose preferred themes for Light & Dark states";
  private static final JLabel LIGHT_THEME_LABEL = new JLabel("Light theme:");
  private static final JLabel DARK_THEME_LABEL = new JLabel("Dark theme:");

  private final ThemesManager themesManager = new ThemesManager();
  private final ThemeSwitchHelpers themeSwitchHelpers = new ThemeSwitchHelpers();

  private void formThemesSelectionList(
          JComboBox<String> themesComboBox,
          String installedThemeId,
          boolean isDark,
          Consumer<UIThemeLookAndFeelInfo> onChangeSelected
  ) {
    Sequence<UIThemeLookAndFeelInfo> allThemes = this.themesManager.getInstalledThemes();
    allThemes.iterator().forEachRemaining((UIThemeLookAndFeelInfo theme) -> themesComboBox.addItem(theme.getName()));

    int themeIndexToSelect = this.themeSwitchHelpers.getInstalledThemeIndexById(installedThemeId, isDark);
    System.out.println(installedThemeId + " " + themeIndexToSelect + " " + isDark);

    themesComboBox.setSelectedIndex(themeIndexToSelect);
    themesComboBox.addActionListener(e -> onChangeSelected.accept(
            this.themeSwitchHelpers.findThemeByIndex(themesComboBox.getSelectedIndex())
    ));
  }

  private void resetThemesSelectionList(
          JComboBox<String> comboBox,
          String previousInstalledThemeId,
          boolean isDark
  ) {
    int themeIndexToSelect = this.themeSwitchHelpers.getInstalledThemeIndexById(previousInstalledThemeId, isDark);
    comboBox.setSelectedIndex(themeIndexToSelect);
  }

  private final ComboBox<String> lightThemesComboBox = new ComboBox<>();
  private final ComboBox<String> darkThemesComboBox = new ComboBox<>();

  protected ThemeSettingsPanel(
          String installedLightThemeId,
          String installedDarkThemeId,
          Consumer<UIThemeLookAndFeelInfo> onChangeLightTheme,
          Consumer<UIThemeLookAndFeelInfo> onChangeDarkTheme
  ) {
    formThemesSelectionList(this.lightThemesComboBox, installedLightThemeId, false, onChangeLightTheme);
    formThemesSelectionList(this.darkThemesComboBox, installedDarkThemeId, true, onChangeDarkTheme);
  }

  protected void resetSelectedThemes(String previousInstalledLightThemeId, String previousInstalledDarkThemeId) {
    resetThemesSelectionList(this.lightThemesComboBox, previousInstalledLightThemeId, false);
    resetThemesSelectionList(this.darkThemesComboBox, previousInstalledDarkThemeId, true);
  }

  protected JComponent getComponent() {
    JPanel themeSettingsPanel = new JPanel(new BorderLayout());

    JPanel contentPane = new JPanel();
    contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
    contentPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    contentPane.setAlignmentX(JComponent.LEFT_ALIGNMENT);
    contentPane.setAlignmentY(JComponent.TOP_ALIGNMENT);

    JPanel titlePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    titlePane.add(new JLabel(PANEL_TITLE));
    contentPane.add(titlePane);

    JPanel lightThemePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    lightThemePane.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    lightThemePane.add(LIGHT_THEME_LABEL);
    lightThemePane.add(this.lightThemesComboBox);
    contentPane.add(lightThemePane);

    JPanel darkThemePane = new JPanel(new FlowLayout(FlowLayout.LEFT));
    darkThemePane.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
    darkThemePane.add(DARK_THEME_LABEL);
    darkThemePane.add(this.darkThemesComboBox);
    contentPane.add(darkThemePane);

    contentPane.add(new JPanel());
    
    themeSettingsPanel.add(contentPane, BorderLayout.NORTH);

    return themeSettingsPanel;
  }
}
