# Mongo Watcher Java - Azure Function

This project demonstrates how to use **Azure Functions with Java** to watch a MongoDB (or Cosmos DB for MongoDB vCore) collection for change stream events.

## Prerequisites
- Java 17+
- Maven 3.8+
- Azure Functions Core Tools
- Azure CLI
- A MongoDB or Cosmos DB for MongoDB vCore instance with [enabled changestreams](https://learn.microsoft.com/en-us/azure/cosmos-db/mongodb/vcore/change-streams)
- VS Code (optional, with the Azure Functions extension)

## Clone the Repository
```bash
git clone https://github.com/sai-sambhu/mongo-watcher-function-java.git
cd mongo-watcher-function-java
```
## Run Locally
1. Update `local.settings.json` in the project root:
    ```json
    {
    "IsEncrypted": false,
    "Values": {
        "AzureWebJobsStorage": "UseDevelopmentStorage=true",
        "FUNCTIONS_WORKER_RUNTIME": "java",
        "MONGO_CONN_STRING": "<connection_string>",
        "MONGO_DB_NAME": "<db_name>",
        "MONGO_COLLECTION_NAME": "<collection_name>"
    }
    }
    ```

1. Build and run the function locally:
    ```bash
    mvn clean package
    mvn azure-functions:run
    ```

1. Trigger changes in your MongoDB collection (insert/update/delete) and you should see logs like:
    ```lua
    Change detected: ChangeStreamDocument{ operationType=insert, ... }
    ```
## Deploy to Azure
1. Login to Azure
    ```bash
    az login
    ```
1. Deploy the function:
    ```bash
    mvn azure-functions:deploy
    ```
1. In the **Azure Portal**, go to your **Function App** → **Configuration** → **Application settings**, and add:

    - Names: `MONGO_CONN_STRING`, `MONGO_DB_NAME`, `MONGO_COLLECTION_NAME`
    - Value: Your MongoDB connection string, Database Name, Collection Name
1. Save and restart the Function App.

## Create an Azure Function App
You can create a new Azure Function App in multiple ways:

- Azure Portal:
[Create Function App in Portal](https://learn.microsoft.com/en-gb/azure/azure-functions/functions-create-function-app-portal)

- Visual Studio Code:
[Create Function App in VS Code](https://learn.microsoft.com/en-gb/azure/azure-functions/how-to-create-function-vs-code)

- Azure CLI:
    ```bash
    az functionapp create \
    --resource-group <RESOURCE_GROUP> \
    --consumption-plan-location <REGION> \
    --runtime java \
    --functions-version 4 \
    --name <FUNCTION_APP_NAME> \
    --storage-account <STORAGE_ACCOUNT_NAME>
    ```

## Notes

- local.settings.json is only used locally. Values are not deployed to Azure.
- In Azure, configure MONGO_CONN_STRING in Application Settings of the Function App.
