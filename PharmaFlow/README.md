# DEPLOYMENT STEPS AND API SPEC

## Deployment Steps

Deployment in App Engine:

1.  Configure your App Engine in GCP
2.  Open Google Cloud console and activate cloud shell
3.  From the cloud shell terminal clone this repository.
    ```sh
    https://github.com/jossanda17/PharmaFlow.git
    ```

4.  Move to the working directory
    ```sh
    cd PharmaFlow
    ```

5.  In the cloud shell click open editor to review the required files to deploy to App Engine

    - main.py = The API for your machine learning model to run in the cloud. in this project we use Flask.
    - requirements.txt = This file is used for specifying what python packages are required to run the project you are looking at
    - app.yaml = You configure your App Engine app's settings in the app.yaml file The app.yaml file contains information about your app's code, such as the runtime and the latest version identifier.

6.  return to the cloud shell terminal and run this code to authorizes gcloud and other SDK tools to access Google Cloud Platform using your user account credentials, or from an account of your choosing whose credentials are already available and Sets up a new or existing configuration.
    ```sh
    gcloud init
    ```

7.  still in Cloud Shell terminal, to deploy our ML model to App Engine run this code.
    ```sh
    gcloud app deploy
    ```

8.  After succesful deployment, you can access the given endpoint and test the prediction using postman

   
## API SPEC

### Endpoint Stok


POST | YOUR-API-URL/stok/create

request :
```json
{
    "obat_id": "200",
    "obat_name": "Heizen",
    "total": 30
}
```
response :
```json
{
    "success": true
}
```

GET |  YOUR-API-URL/stok/read?obat_id=112

request :

response :
```json
{
    "112": {
        "obat_id": "112",
        "obat_name": "Ibuprofen Etafen 400 mg",
        "total": 0
    }
}
```

PATCH |  YOUR-API-URL/stok/update

request :
```json
{
    "obat_id": "200",
    "obat_name": "Heizen",
    "total": 200
}
```
response :
```json
{
    "success": true
}
```

DELETE |  YOUR-API-URL/stok/delete

request :
```json
{
    "obat_id": "200"
}
```
response :
```json
{
    "success": true
}
```



### Endpoint Penjualan


POST |  YOUR-API-URL/penjualan/create

request :
```json
{
    "day": "2023-12-04",
    "obat_id": "112",
    "obat_name": "Ibuprofen Etafen 400 mg",
    "total_obat_amount": 60
}
```
response :
```json
{
    "success": true
}
```

GET |  YOUR-API-URL/penjualan/read?obat_id=112

request :

response :
```json
{
    "0055e5d10b4a492ebc2673280857878e": {
        "day": "2023-05-02",
        "obat_id": "112",
        "obat_name": "Ibuprofen Etafen 400 mg",
        "total_obat_amount": "266"
    },
    "00f85fe3d1f74a0b9e91a7c6dd9f55a4": {
        "day": "2022-10-17",
        "obat_id": "112",
        "obat_name": "Ibuprofen Etafen 400 mg",
        "total_obat_amount": "220"
    },
    { ... }
}
```

PATCH |  YOUR-API-URL/stok/update

request :
```json
{
    "id": "7afd127a78c445e4a65dc6a7da1ab00b",
    "day": "2023-12-04",
    "obat_id": "112",
    "obat_name": "Ibuprofen Etafen 400 mg",
    "total_obat_amount": 30
}
```
response :
```json
{
    "success": true
}
```

DELETE |  YOUR-API-URL/stok/delete

request :
```json
{
    "id": "7afd127a78c445e4a65dc6a7da1ab00b"
}
response :
{
    "success": true
}
```


### Endpoint Prediction


POST | YOUR-API-URL/prediction/predict

request :
```json
{
    "obat_id": "112"
}
```
response :
```json
{
    "data": {
        "2022-07-29": 70,
        "2022-07-30": 130,
        "2022-07-31": 155,
        "2022-08-01": 170,
        "2022-08-02": 110,

	       ...
        
        "2023-11-29": 141,
        "2023-11-30": 110,
        "2023-12-01": 70,
        "2023-12-02": 76,
        "2023-12-03": 129,
        "2023-12-04": 0,
        "2023-12-05": 0,
        "2023-12-06": 0,
        "2023-12-07": 0,
        "2023-12-08": 0,
        "2023-12-09": 0,
        "2023-12-10": 0,
        "2023-12-11": 0,
        "2023-12-12": 0,
        "2023-12-13": 0,
        "2023-12-14": 0,
        "2023-12-15": 0,
        "2023-12-16": 0,
        "2023-12-17": 0,
        "2023-12-18": 0,
        "2023-12-19": 0,
        "2023-12-20": 0
    },
    "obat_id": "112",
    "predict": 17
}
```
