package com.function;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.logging.Logger;

public class WatchFunction {
    @FunctionName("WatchMongoDB")
    public void run(
        @TimerTrigger(name = "timerInfo", schedule = "*/30 * * * * *") String timerInfo,
        final ExecutionContext context) {

        Logger logger = context.getLogger();
        logger.info("MongoDB Watch Function triggered");

        String connectionString = System.getenv("MONGO_CONN_STRING");
        String dbName = System.getenv("MONGO_DB_NAME");
        String collName = System.getenv("MONGO_COLLECTION_NAME");

        try (MongoClient mongoClient = MongoClients.create(connectionString)) {
            MongoDatabase db = mongoClient.getDatabase(dbName);
            MongoCollection<Document> coll = db.getCollection(collName);

            // Watch change stream
            coll.watch().forEach(change -> {
                logger.info("Change detected: " + change);
            });
        }
    }
}
