package com.example.demo.config;

import com.google.gson.Gson;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class MappingConfig {
    private Map<String, TableMapping> tables;

    public Map<String, TableMapping> getTables() {
        return tables;
    }

    public static class TableMapping {
        private String targetTable;
        private Map<String, String> columns;

        public String getTargetTable() {
            return targetTable;
        }

        public Map<String, String> getColumns() {
            return columns;
        }
    }

    public static MappingConfig loadFromFile(String filePath) throws IOException {
        Gson gson = new Gson();
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, MappingConfig.class);
        }
    }
}

