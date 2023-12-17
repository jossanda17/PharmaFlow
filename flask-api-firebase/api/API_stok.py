import uuid
from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
db_stok = db.collection('stok')

API_stok = Blueprint('API_stok', __name__)

@API_stok.route('/create', methods=['POST'])
def create():
    try:
        id = uuid.uuid4()
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total = request.get_json()['total']
        db_stok.document(id.hex).set({
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total": total
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
    
@API_stok.route('/update', methods=['POST'])
def update():
    try:
        id = request.get_json()['id']
        obat_id = request.get_json()['obat_id']
        obat_name = request.get_json()['obat_name']
        total = request.get_json()['total']
        db_stok.document(id).update({
            "obat_id": obat_id,
            "obat_name": obat_name,
            "total": total
        })
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Eror Occured: {e}"

@API_stok.route('/delete', methods=['POST'])
def delete():
    try:
        id = request.get_json()['id']
        db_stok.document(id).delete()
        return jsonify({
            "success": True
        }), 200
    except Exception as e:
        return f"An Error Occured: {e}"