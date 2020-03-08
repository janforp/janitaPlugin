package com.janita.plugin.autgencode.component;

import com.intellij.openapi.components.BaseComponent;
import org.jetbrains.annotations.NotNull;

/**
 * 表数据
 *
 * @author zhucj
 * @since 202003
 */
public class AutoCodeComponent implements BaseComponent {

    @NotNull
    @Override
    public String getComponentName() {
        return "AutoCodeComponent";
    }
}
