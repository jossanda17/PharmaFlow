from flask import Flask
from firebase_admin import credentials, initialize_app

cred = credentials.Certificate("api/key.json")
default_app = initialize_app(cred)

def create_app():
    app = Flask(__name__)
    app.config['SECRET_KEY'] = '12345rtfescdvf'

    from .API_penjualan import API_penjualan
    from .API_stok import API_stok
    from .API_prediction import API_prediction

    app.register_blueprint(API_penjualan, url_prefix='/penjualan')
    app.register_blueprint(API_stok, url_prefix='/stok')
    app.register_blueprint(API_prediction, url_prefix='/prediction')

    return app