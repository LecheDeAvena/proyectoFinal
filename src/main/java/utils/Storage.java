package main.java.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


public class Storage {

	public static Firestore db;
	
	public static void conectarFirebase() {
		try {
			FileInputStream sa= new FileInputStream("proyectofinal-28d01-firebase-adminsdk-83qf5-c67d98750e.json");

			FirebaseOptions options = FirebaseOptions.builder()
			    .setCredentials(GoogleCredentials.fromStream(sa))
			    .setDatabaseUrl("https://<DATABASE_NAME>.firebaseio.com/")
			    .build();

			FirebaseApp.initializeApp(options);
			db=FirestoreClient.getFirestore();
			System.out.println("Yipie");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.print("Error: " + e.getMessage());
		}
	}
}
