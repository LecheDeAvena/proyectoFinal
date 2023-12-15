package main.java.utils;

import java.util.Map;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;

public class ManejoDatos {

	CollectionReference reference;
	
	static Firestore db;
	
	public static boolean guardarPersona(String collection, String doc, Map<String,Object> data) {
		
		db=FirestoreClient.getFirestore();
		
		try {
			
			DocumentReference docRef=db.collection(collection).document(doc);
			
			ApiFuture<WriteResult> result = docRef.set(data);
			
			System.out.println("yipie");
			
			return true;
			
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Error al guardar");
		}
		
		return false;
	}
}
