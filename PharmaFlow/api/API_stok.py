from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
db_stok = db.collection('stok')

API_stok = Blueprint('API_stok', __name__)

@API_stok.route('/create', methods=['POST'])
def create():
    try:
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total = request.get_json()['total']
        db_stok.document(obat_id).set({
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total": int(total)
        })
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Error Occured: {e}"
        
@API_stok.route('/read')
def read():
    try:
        all_docs = {}
        for doc in db_stok.stream():
        	all_docs[doc.id] = doc.to_dict()
        
        return jsonify(all_docs), 200
    except Exception as e:
        return f"An Error Occured: {e}"
    
@API_stok.route('/update', methods=['PATCH'])
def update():
    try:
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total = request.get_json()['total']
        db_stok.document(obat_id).update({
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total": total
        })
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Eror Occured: {e}"

@API_stok.route('/delete', methods=['DELETE'])
def delete():
    try:
        obat_id = request.get_json()['obat_id']
        db_stok.document(obat_id).delete()
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Error Occured: {e}"
