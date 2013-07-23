package org.neomatrix369.apiworld.util;

import java.util.ResourceBundle;

/**
 * Enum singleton to load a resource bundle to care of all API strings.
 * @author helio frota http://www.heliofrota.com
 *
 */
public enum Keys {

    /** The instance. */
    INSTANCE;

    /** Static bundle. */
    private static ResourceBundle bundle;
    
    static {
       bundle = ResourceBundle.getBundle("Keys");
    }

    /**
     * Returns the value based on key.
     * @param key String
     * @return String
     */
    public String getKey(String key) {
        return bundle.getString(key);
    }

}
