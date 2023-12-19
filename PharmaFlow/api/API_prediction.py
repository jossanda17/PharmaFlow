from flask import Blueprint, request, jsonify
from firebase_admin import firestore
import numpy as np
import datetime

db = firestore.client()
db_penjualan = db.collection('penjualan')

API_prediction = Blueprint('API_prediction', __name__)

def generate_daily_list(start_year, start_month, start_day):
    start = datetime.datetime(start_year,start_month,start_day)
    start = datetime.datetime.timestamp(start)
    end = datetime.datetime.now()
    end = datetime.datetime.timestamp(end)

    list=[]

    for day in range(int(start), int(end), 86400):
        list.append([datetime.datetime.fromtimestamp(day).strftime("%Y-%m-%d"), 0])

    return list

def sample_data(obat_id, data, list):
    import json

    with open('obat/' + obat_id + '.json') as jsonf:
        json_loads = json.loads(jsonf.read())
        
        for i in list:
            for j in json_loads['data'].items():
                if i[0] == j[0]:
                    i[1] = int(j[1])

            for j in data.items():
                if i[0] == j[0]:
                    i[1] = int(j[1])

    return list

def forcast(list, obat_id):
    sales = []
    split_time = 450
    window_size = 8
    batch_size = 8

    for row in list:
        sales.append(float(row[1]))

    series = np.array(sales)
    from keras import models
    model = models.load_model("model/" + obat_id + '.h5')

    forecast_series = series[split_time-window_size:-1]
    forecast = model_forecast(model, forecast_series, window_size, batch_size)
    results = forecast.squeeze()

    return results

def model_forecast(model, series, window_size, batch_size):
    import tensorflow as tf
    dataset = tf.data.Dataset.from_tensor_slices(series)    
    dataset = dataset.window(window_size, shift=1, drop_remainder=True)
    dataset = dataset.flat_map(lambda w: w.batch(window_size))
    dataset = dataset.batch(batch_size).prefetch(1)

    forecast = model.predict(dataset)

    return forecast

@API_prediction.get('/predict')
def prediction():
    obat_id = request.get_json()['obat_id']
    data = request.get_json()['data']

    list = generate_daily_list(2022,7,29)
    list = sample_data(obat_id, data, list)
    results = forcast(list, obat_id)
    
    return jsonify({
        "obat_id": obat_id,
        "predict": int(results[-1])
    }), 200