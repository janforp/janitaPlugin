package com.janita.plugin.autgencode.component;

import com.intellij.openapi.components.BaseComponent;
import org.jetbrains.annotations.NotNull;

/**
 * @author 李鹏军
 */
public class AutoCodeComponent implements BaseComponent {
    @Override
    public void initComponent() {
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        String tmp20 = "AutoCodeComponent";

        if (tmp20 == null) {
            throw new IllegalStateException(String.format("@NotNull method %s.%s must not return null", new Object[]{"com/platform/gen/component/AutoCodeComponent", "getComponentName"}));
        }
        return tmp20;
    }
}
