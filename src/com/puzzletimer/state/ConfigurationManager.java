package com.puzzletimer.state;

import com.puzzletimer.models.ConfigurationEntry;

import java.util.ArrayList;
import java.util.HashMap;

public class ConfigurationManager {
    private ArrayList<Listener> listeners;
    private HashMap<String, ConfigurationEntry> entryMap;
    public ConfigurationManager(ConfigurationEntry[] entries) {
        this.listeners = new ArrayList<Listener>();

        this.entryMap = new HashMap<String, ConfigurationEntry>();
        for (ConfigurationEntry entry : entries) {
            this.entryMap.put(entry.getKey(), entry);
        }
    }

    public String getConfiguration(String key) {
        return this.entryMap.get(key).getValue();
    }

    public void setConfiguration(String key, String value) {
        this.entryMap.put(key, new ConfigurationEntry(key, value));
        for (Listener listener : this.listeners) {
            listener.configurationEntryUpdated(key, value);
        }
    }

    public void addListener(Listener listener) {
        this.listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        this.listeners.remove(listener);
    }

    public static class Listener {
        public void configurationEntryUpdated(String key, String value) {
        }
    }
}
