package main.java.utils;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.hibernate.annotations.common.util.impl.Log;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreException;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.annotations.Nullable;

public class ManejoDatos {

	CollectionReference reference;
	
	static Firestore db;
	
	public static boolean guardarDatos(String collection, String doc, Map<String,Object> data) {
		
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
	
	public static void sacarDatos(String collection, String doc) {
		
		db=FirestoreClient.getFirestore();
		
		try {
			DocumentReference docRef=db.collection(collection).document(doc);
			// asynchronously retrieve the document
			ApiFuture<DocumentSnapshot> future = docRef.get();
			// ...
			// future.get() blocks on response
			DocumentSnapshot document;
			document = future.get();
			
			if (document.exists()) {
			  System.out.println("Document data: " + document.getData());
			} else {
			  System.out.println("No such document!");
			}
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
