package com.example.bit603_mitchell_travis_5080526_as3.viewModel;

/*
RESPONSE TIME TESTING FOR TESTING
//TESTING RESPONSE TIME: 1910ms
//TESTING RESPONSE TIME: 452ms
//TESTING RESPONSE TIME: 398ms
//TESTING RESPONSE TIME: 347ms

//the speed of the network the user is using can affect
//the time taken to access data in the cloud
//The location of the server where the data is being retrieved
//from can also affect performance.. A server closer to the user
//will improve the performance
//the device being used can also affect performance.. cpu and ram
//can affect time taken to process the response
 */

import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.bit603_mitchell_travis_5080526_as3.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import android.content.Context;

public class UserViewModel extends ViewModel {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<GoogleSignInAccount> userAccountLiveData = new MutableLiveData<>();

    public void checkUserSignedIn(Context context){
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(context);
        Log.d("USER_SIGN_IN_DEBUG",String.valueOf(account));
        userAccountLiveData.setValue(account);
    }

    public LiveData<GoogleSignInAccount> getUserAccountLiveData(){
        return userAccountLiveData;
    }

    public void setUserLiveData(User user){
        userLiveData.setValue(user);
    }

    public LiveData<User> getUserLiveData(){
        return userLiveData;
    }

    public void saveUser(User user){
        final long startTime = SystemClock.elapsedRealtime();
        db.collection("users")
                .document(user.getUserId())
                .get()
                .addOnSuccessListener(
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot document) {
                                long endTime = SystemClock.elapsedRealtime();
                                long responseTime = endTime - startTime;
                                Log.d("Response_Time",String.valueOf(responseTime));
                                //TESTING RESPONSE TIME: 1910ms
                                //TESTING RESPONSE TIME: 452ms
                                //TESTING RESPONSE TIME: 398ms
                                //TESTING RESPONSE TIME: 347ms
                                if(document.exists()){
                                    Log.d("USER_FIRESTORE","User already present in database");
                                }else{
                                    db.collection("users")
                                            .document(user.getUserId())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("USER_FIRESTORE","USER ADDED TO DATABASE");
                                                    userLiveData.setValue(user); // update LiveData on success
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("USER_FIRESTORE","Unsuccessfull adding new user");
                                                }
                                            });


                                }

                            }
                        }
                );
    }

}
