package com.augmentum.masterchef.util;

import java.util.HashMap;
import java.util.Map;

public class GameProps extends AbstractInitProps {

    public static final String LEVEL_ENABLED = "level.enabled";
    protected static final String PROPS_NAME = "config_game_props.xml";

    // protected static InitProps props = new InitProps();
    protected static Map<String, GameProps> props = new HashMap<String, GameProps>();

    public GameProps(String propsFileName) {
        super(propsFileName);
    }

    public static GameProps getInstance() {
        return getInstance(PROPS_NAME);
    }

    public static GameProps getInstance(String propsFileName) {
        GameProps init = props.get(propsFileName);
        if (init == null) {
            init = new GameProps(propsFileName);
            props.put(propsFileName, init);
        }

        return init;
    }
}
