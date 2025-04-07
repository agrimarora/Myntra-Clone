package com.example.myntra.data

import android.adservices.ondevicepersonalization.UserData
import android.annotation.SuppressLint
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.myntra.comman.ResultState
import com.example.myntra.comman.USER_COLLECTION
import com.example.myntra.comman.model.CatergoryModel
import com.example.myntra.comman.model.ProductModel
import com.example.myntra.comman.model.SliderImage
import com.example.myntra.domain.model.UserDataParent
import com.example.myntra.domain.model.Userdata
import com.example.myntra.domain.repo.Repo
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

import javax.inject.Inject

class RepoImpl @Inject constructor(var firebaseAuth: FirebaseAuth, var firebaseFirestore: FirebaseFirestore) : Repo {
    @SuppressLint("SuspiciousIndentation")
    override fun registeruserwithemailandpassword(userdata: Userdata): Flow<ResultState<String>> =
        callbackFlow {

            trySend(ResultState.Loading)

            userdata.email?.let {
                userdata.password?.let { it1 ->
                    firebaseAuth.createUserWithEmailAndPassword(it, it1)
                        .addOnCompleteListener {
                            Log.d("Loading", "${ResultState.Loading}.toString()")
                            if (it.isSuccessful) {
                                firebaseFirestore.collection(USER_COLLECTION)
                                    .document(it.result.user?.uid.toString()).set(userdata)
                                    .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            trySend(ResultState.Succes("User Registered Successfully"))

                                        } else {
                                            if (it.exception != null) {
                                                trySend(ResultState.error(it.exception!!.localizedMessage.toString()))
                                            }

                                        }
                                    }


                            } else {

                                Log.d("error", "${it.exception!!.localizedMessage} ")
                                if (it.exception != null)
                                    trySend(ResultState.error(it.exception!!.localizedMessage.toString()))
                            }


                        }
                }
            }
            awaitClose {
                close()

            }


        }
    fun loginwithgoogle(userdata: Userdata): Flow<ResultState<String>> = callbackFlow {

    }

    override fun loginuserwithemailandpassword(userdata: Userdata): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            userdata.email?.let {
                userdata.password?.let { it1 ->
                    firebaseAuth.signInWithEmailAndPassword(it, it1)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                trySend(ResultState.Succes("User Logged In Successfully"))
                            } else {
                                Log.d("error", "${it.exception!!.localizedMessage} ")
                                if (it.exception != null)
                                    trySend(ResultState.error(it.exception!!.localizedMessage.toString()))

                            }
                        }
                }
            }
            awaitClose {
                close()

            }

        }


    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun getuserbyUID(UID: String): Flow<ResultState<UserDataParent>> = callbackFlow {
        trySend(ResultState.Loading)
        firebaseFirestore.collection(USER_COLLECTION).document(UID).get().addOnCompleteListener {
            if (it.isSuccessful) {
                val data = it.result.toObject(Userdata::class.java)!!
                val UserData = UserDataParent(it.result.id, data)
                if (data != null) {
                    trySend(ResultState.Succes(UserData))
                }

            } else {
                if (it.exception != null)
                    trySend(ResultState.error(it.exception!!.localizedMessage.toString()))
                else {
                    if (it.exception != null) {
                        trySend(ResultState.error(it.exception!!.localizedMessage.toString()))
                    }
                }
            }
        }
        awaitClose {
            close()
        }
    }


    override fun updateUserData(userDataParent: UserDataParent): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val user = firebaseAuth.currentUser
            user!!.updateEmail(userDataParent.Userdata!!.email!!)
            userDataParent.nodeID?.let {
                userDataParent.Userdata?.let { it1 ->
                    firebaseFirestore.collection(USER_COLLECTION).document(it)
                        .set(it1).addOnCompleteListener {
                            if (it.isSuccessful) {
                                trySend(ResultState.Succes("User Data Updated Successfully"))
                            } else {
                                if (it.exception != null) {
                                    trySend(ResultState.error(it.exception?.localizedMessage.toString()))
                                }
                            }
                        }
                }
            }
        }

    override suspend fun addToCart(product: ProductModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser

        if (user == null) {
            trySend(ResultState.error("User not authenticated"))
            close()
            return@callbackFlow
        }

        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user.uid}/cart")

        // Check if the product already exists based on productId
        cartRef.whereEqualTo("id", product.id) // Changed condition to use productId
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // If the product is not in cart, add it with a unique ID
                    val newDocRef = cartRef.document() // Firestore will generate a unique ID
                    newDocRef.set(product)
                        .addOnSuccessListener {
                            newDocRef.set(product)
                                .addOnSuccessListener {
                                    trySend(ResultState.Succes("Product Added to Cart"))
                                }
                                .addOnFailureListener { e ->
                                    trySend(ResultState.error(e.localizedMessage ?: "Failed to add product"))
                                }

                            trySend(ResultState.Succes("Product Added to Cart"))
                        }
                        .addOnFailureListener { e ->
                            trySend(ResultState.error(e.localizedMessage ?: "Failed to add product"))
                        }
                } else {
                    // Product already exists
                    trySend(ResultState.error("Product already in cart"))
                }
            }
            .addOnFailureListener { e ->
                trySend(ResultState.error(e.localizedMessage ?: "Failed to check cart"))
            }

        awaitClose { close() }
    }
    override suspend fun addTowhislist(product: ProductModel): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser

        if (user == null) {
            trySend(ResultState.error("User not authenticated"))
            close()
            return@callbackFlow
        }

        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user.uid}/wishlist")

        // Check if the product already exists based on productId
        cartRef.whereEqualTo("id", product.id) // Changed condition to use productId
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    // If the product is not in cart, add it with a unique ID
                    val newDocRef = cartRef.document() // Firestore will generate a unique ID
                    newDocRef.set(product)
                        .addOnSuccessListener {
                            newDocRef.set(product)
                                .addOnSuccessListener {
                                    trySend(ResultState.Succes("Product Added to wishlist"))
                                }
                                .addOnFailureListener { e ->
                                    trySend(ResultState.error(e.localizedMessage ?: "Failed to add product"))
                                }

                            trySend(ResultState.Succes("Product Added to Wishlist"))
                        }
                        .addOnFailureListener { e ->
                            trySend(ResultState.error(e.localizedMessage ?: "Failed to add product"))
                        }
                } else {
                    // Product already exists
                    trySend(ResultState.error("Product already in wishlist"))
                }
            }
            .addOnFailureListener { e ->
                trySend(ResultState.error(e.localizedMessage ?: "Failed to check cart"))
            }

        awaitClose { close() }
    }







    override suspend fun getAllCategories(): Flow<ResultState<List<CatergoryModel>>> = callbackFlow {
        firebaseFirestore.collection("Admin/Category/Category").get()
            .addOnSuccessListener { querySnapshot ->
                try {
                    val categoryList = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(CatergoryModel::class.java)
                    }
                    trySend(ResultState.Succes(categoryList))
                } catch (e: Exception) {
                    trySend(ResultState.error("Data parsing error: ${e.message}"))
                }
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.error("Firestore error: ${exception.message}"))
            }

        awaitClose {
            close()
            Log.d("Test", "Flow closed")
        }
    }
    override suspend fun getSliderpics(): Flow<ResultState<List<SliderImage>>> = callbackFlow {
        firebaseFirestore.collection("/SliderPictures/pic1/pics").get()
            .addOnSuccessListener { querySnapshot ->
                try {
                    val ImageList = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(SliderImage::class.java)
                    }
                    Log.d( "getSliderpics: ","${ImageList}")
                    trySend(ResultState.Succes(ImageList))
                } catch (e: Exception) {
                    trySend(ResultState.error("Data parsing error: ${e.message}"))
                }
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.error("Firestore error: ${exception.message}"))
            }

        awaitClose {
            close()
            Log.d("Test", "Flow closed")
        }
    }
  override suspend fun getproducts(): Flow<ResultState<List<ProductModel>>> = callbackFlow {
        firebaseFirestore.collection("/Admin/Product/Products").get()
            .addOnSuccessListener { querySnapshot ->
                try {
                    val ProductList = querySnapshot.documents.mapNotNull { document ->
                        document.toObject(ProductModel::class.java)
                    }

                    trySend(ResultState.Succes(ProductList))
                } catch (e: Exception) {
                    trySend(ResultState.error("Data parsing error: ${e.message}"))
                }
            }
            .addOnFailureListener { exception ->
                trySend(ResultState.error("Firestore error: ${exception.message}"))
            }

        awaitClose {
            close()
            Log.d("Test", "Flow closed")
        }
    }
    override fun getallcartproduct():Flow<ResultState<List<ProductModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser
        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user?.uid}/cart")
        cartRef.get().addOnSuccessListener {querySnapshot->
            try {
                val ProductList = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(ProductModel::class.java)
                }
                Log.d( "getallcartproduct","carproduct=${ProductList}")

                trySend(ResultState.Succes(ProductList))
            } catch (e: Exception) {
                trySend(ResultState.error("Data parsing error: ${e.message}"))
            }
        }
            .addOnFailureListener { exception ->
                trySend(ResultState.error("Firestore error: ${exception.message}"))
            }
        awaitClose{
            close()
        }
        }
    override fun getallwishlistproduct():Flow<ResultState<List<ProductModel>>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser
        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user?.uid}/wishlist")
        cartRef.get().addOnSuccessListener {querySnapshot->
            try {
                val ProductList = querySnapshot.documents.mapNotNull { document ->
                    document.toObject(ProductModel::class.java)
                }
                Log.d( "getallcartproduct","carproduct=${ProductList}")

                trySend(ResultState.Succes(ProductList))
            } catch (e: Exception) {
                trySend(ResultState.error("Data parsing error: ${e.message}"))
            }
        }
            .addOnFailureListener { exception ->
                trySend(ResultState.error("Firestore error: ${exception.message}"))
            }
        awaitClose{
            close()
        }
    }


    override suspend fun removeFromCart(productId: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser

        if (user == null) {
            trySend(ResultState.error("User not authenticated"))
            close()
            return@callbackFlow
        }

        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user.uid}/cart")

        // Find the product in the cart by productId
        cartRef.whereEqualTo("id", productId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // If the product is found, delete it
                    val docId = documents.documents[0].id // Get the document ID of the product
                    cartRef.document(docId)
                        .delete()
                        .addOnSuccessListener {
                            trySend(ResultState.Succes("Product removed from cart"))
                        }
                        .addOnFailureListener { e ->
                            trySend(ResultState.error(e.localizedMessage ?: "Failed to remove product"))
                        }
                } else {
                    // Product not found in the cart
                    trySend(ResultState.error("Product not found in cart"))
                }
            }
            .addOnFailureListener { e ->
                trySend(ResultState.error(e.localizedMessage ?: "Failed to find product in cart"))
            }

        awaitClose { close() }
    }
    override suspend fun removeFromWishlist(productId: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val user = firebaseAuth.currentUser

        if (user == null) {
            trySend(ResultState.error("User not authenticated"))
            close()
            return@callbackFlow
        }

        val cartRef = firebaseFirestore.collection("${USER_COLLECTION}/${user.uid}/wishlist")

        // Find the product in the cart by productId
        cartRef.whereEqualTo("id", productId)
            .get()
            .addOnSuccessListener { documents ->
                if (!documents.isEmpty) {
                    // If the product is found, delete it
                    val docId = documents.documents[0].id // Get the document ID of the product
                    cartRef.document(docId)
                        .delete()
                        .addOnSuccessListener {
                            trySend(ResultState.Succes("Product removed from wishlist"))
                        }
                        .addOnFailureListener { e ->
                            trySend(ResultState.error(e.localizedMessage ?: "Failed to remove product"))
                        }
                } else {
                    // Product not found in the cart
                    trySend(ResultState.error("Product not found in wishlist"))
                }
            }
            .addOnFailureListener { e ->
                trySend(ResultState.error(e.localizedMessage ?: "Failed to find product in cart"))
            }

        awaitClose { close() }
    }

}
