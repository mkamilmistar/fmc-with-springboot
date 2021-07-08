package com.example.fcm.springbootfcm.service;

import com.example.fcm.springbootfcm.entity.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import com.google.protobuf.Api;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {

  private static final String COLLECTION_NAME = "products";

  public String saveProduct(Product product) throws ExecutionException, InterruptedException {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(product.getName()).set(product);

    return collectionApiFuture.get().getUpdateTime().toString();
  }

  public Product getProductDetails(String name) throws ExecutionException, InterruptedException {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    DocumentReference documentReference = dbFirestore.collection(COLLECTION_NAME).document(name);

    ApiFuture<DocumentSnapshot> future = documentReference.get();

    DocumentSnapshot document = future.get();

    Product product = new Product();
    if(document.exists()) {
      product = document.toObject(Product.class);
      return product;
    } else {
      return null;
    }
  }

  public String updateProduct(Product product) throws ExecutionException, InterruptedException {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(product.getName()).set(product);

    return collectionApiFuture.get().getUpdateTime().toString();
  }

  public String deleteProduct(String name) throws ExecutionException, InterruptedException {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(name).delete();

    return "Document with Product ID "+ name + " has been deleted successfully";
  }

  public List<Product> getListProductDetails() throws ExecutionException, InterruptedException {
    Firestore dbFirestore = FirestoreClient.getFirestore();

    Iterable<DocumentReference> documentReference = dbFirestore.collection(COLLECTION_NAME).listDocuments();
    Iterator<DocumentReference> iterator = documentReference.iterator();

    List<Product> productList = new ArrayList<>();
    new Product();
    Product product;

    while (iterator.hasNext()) {
      DocumentReference documentReferenceList = iterator.next();
      ApiFuture<DocumentSnapshot> future = documentReferenceList.get();
      DocumentSnapshot document = future.get();

      product = document.toObject(Product.class);
      productList.add(product);
    }

    return productList;
  }
}
