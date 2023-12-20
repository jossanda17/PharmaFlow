import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore
from google.cloud.firestore_v1.base_query import FieldFilter

db = firestore.client()
db_penjualan = db.collection('penjualan')

API_penjualan = Blueprint('API_penjualan', __name__)

@API_penjualan.route('/create', methods=['POST'])
def create():
    try:
        id = uuid.uuid4()
        day = request.get_json()['day']
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total_obat_amount = request.get_json()['total_obat_amount']
        db_penjualan.document(id.hex).set({
            "day": day,
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total_obat_amount": total_obat_amount
        })
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Error Occured: {e}"
        
@API_penjualan.route('/read')
def read():
    try:
        try: 
            obat_id = request.args['obat_id'] 
        except KeyError: 
            obat_id = None
        all_docs = {}
        if obat_id == None:
            for doc in db_penjualan.stream():
                all_docs[doc.id] = doc.to_dict()
        else:
            for doc in db_penjualan.where(filter=FieldFilter("obat_id", "==", obat_id)).stream():
                all_docs[doc.id] = doc.to_dict()

        return jsonify(all_docs), 200
    except Exception as e:
        return f"An Error Occured: {e}"
    
@API_penjualan.route('/update', methods=['PATCH'])
def update():
    try:
        id = request.get_json()['id']
        day = request.get_json()['day']
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total_obat_amount = request.get_json()['total_obat_amount']
        db_penjualan.document(id).update({
            "day": day,
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total_obat_amount": total_obat_amount
        })
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Eror Occured: {e}"

@API_penjualan.route('/delete', methods=['DELETE'])
def delete():
    try:
        id = request.get_json()['id']
        db_penjualan.document(id).delete()
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Error Occured: {e}"
