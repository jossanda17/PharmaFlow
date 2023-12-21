# API SPEC

## Endpoint Stok


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



## Endpoint Penjualan


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


## Endpoint Prediction


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
