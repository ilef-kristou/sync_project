package com.example.demo.service;

import com.example.demo.DAO.ChangeLogDao;
import com.example.demo.config.MappingConfig;
import com.example.demo.model.ChangeLog;
import com.example.demo.util.RestClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncService {

    private final ChangeLogDao changeLogDao = new ChangeLogDao();
    private final RestClient restClient = new RestClient();
    private static final String TARGET_DB_URL = "jdbc:sqlserver://ILEFKRISTOU;databaseName=test2";
    private MappingConfig mappingConfig;

    public SyncService() {
        try {
            this.mappingConfig = MappingConfig.loadFromFile("C:\\Users\\ilefk\\Downloads\\projet\\demo\\src\\main\\resources\\com\\example\\demo\\mapping.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void synchronize() {
        try {
            List<ChangeLog> changeLogs = changeLogDao.getAllChangeLogs();

            for (ChangeLog changeLog : changeLogs) {
                MappingConfig.TableMapping tableMapping = mappingConfig.getTables().get(changeLog.getTableName());
                if (tableMapping != null) {
                    String targetTable = tableMapping.getTargetTable();
                    Map<String, String> columnMapping = tableMapping.getColumns();

                    switch (changeLog.getOperationType()) {
                        case "INSERT":
                            synchronizeInsert(changeLog, targetTable, columnMapping);
                            break;
                        //case "UPDATE":
                        //synchronizeUpdate(changeLog, targetTable, columnMapping);
                        //break;
                        //case "DELETE":
                        //synchronizeDelete(changeLog, targetTable);
                        // break;
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            ChangeLog op = new ChangeLog();
            op.setOperationType("Synchronisation");
            op.setTableName("Échec");
            op.setChangeTime(LocalDateTime.now());
            changeLogDao.addChangeLog(op);
        }
    }

    private void synchronizeInsert(ChangeLog changeLog, String targetTable, Map<String, String> columnMapping) throws IOException, InterruptedException {
        try {
            String newValueJson = changeLog.getNewValue();

            JsonObject newValueObject = JsonParser.parseString(newValueJson).getAsJsonObject();

            String targetRecordJson = mapJsonToTarget(newValueObject, columnMapping);

            String targetInsertUrl = TARGET_DB_URL + "/" + targetTable;
            restClient.post(targetInsertUrl, targetRecordJson);

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }
    }

    private String mapJsonToTarget(JsonObject sourceJson, Map<String, String> columnMapping) {
        Gson gson = new Gson();
        JsonObject targetJson = new JsonObject();

        for (Map.Entry<String, String> entry : columnMapping.entrySet()) {
            String sourceField = entry.getKey();
            String targetColumn = entry.getValue();

            if (sourceJson.has(sourceField)) {
                targetJson.add(targetColumn, sourceJson.get(sourceField));
            }
        }

        return gson.toJson(targetJson);
    }
}
