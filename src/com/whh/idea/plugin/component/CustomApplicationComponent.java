package com.whh.idea.plugin.component;

import com.intellij.openapi.components.ApplicationComponent;
import com.whh.idea.plugin.config.UploadFileConfig;
import org.jetbrains.annotations.NotNull;

/**
 * CustomApplicationComponent
 * Created by xuzhuo on 2018/4/25.
 */
public class CustomApplicationComponent implements ApplicationComponent {
    public CustomApplicationComponent() {
    }

    @Override
    public void initComponent() {
        UploadFileConfig.initProperties();
    }

    @Override
    public void disposeComponent() {
        // TODO: insert component disposal logic here
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "CustomApplicationComponent";
    }
}
