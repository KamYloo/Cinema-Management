package org.example.utils;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.graphics.Theme;

public class ColorThemes {
    public static Theme getButtonTheme() {
        return SimpleTheme.makeTheme(
                true,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLUE,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLUE,
                TextColor.ANSI.BLACK,
                TextColor.ANSI.WHITE,
                TextColor.ANSI.BLACK
        );
    }
}
