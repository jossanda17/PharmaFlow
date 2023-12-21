from flask import Blueprint, request, jsonify
from firebase_admin import firestore

db = firestore.client()
db_stok = db.collection('stok')

def get_data_stok():
    all_docs = {}
    for doc in db_stok.stream():
        all_docs[doc.id] = doc.to_dict()

    return all_docs

def get_data_stok_by_obat_id(obat_id):
    all_docs = get_data_stok()
        
    docs = {}
    for doc in all_docs.items():
        if doc[1]['obat_id'] == obat_id:
            docs[doc[0]] = {
                "obat_id": doc[1]['obat_id'],
                "obat_name": doc[1]['obat_name'],
                "total": doc[1]['total']
            }
    
    return docs


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
        try: 
            obat_id = request.args['obat_id'] 
        except KeyError: 
            obat_id = None

        if obat_id == None:
            all_docs = get_data_stok()
        else:
            all_docs = get_data_stok_by_obat_id(obat_id)
        
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
