package com.augmentum.masterchef.util;

import java.util.HashMap;
import java.util.Map;

public class ConfigProps extends AbstractInitProps {

    protected static final String PROPS_NAME = "config.xml";

    // protected static ConfigProps props = new ConfigProps();
    protected static Map<String, ConfigProps> props = new HashMap<String, ConfigProps>();


    public ConfigProps(String propsFileName) {
        super(propsFileName);
        
    }

    public static ConfigProps getInstance() {
        return getInstance(PROPS_NAME);
    }

    public static ConfigProps getInstance(String propsFileName) {
        ConfigProps init = props.get(propsFileName);
        if (init == null) {
            init = new ConfigProps(propsFileName);
            props.put(propsFileName, init);
        }
        return init;
    }
}
