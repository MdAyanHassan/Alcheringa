package com.alcheringa.alcheringa2022;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alcheringa.alcheringa2022.Model.merchModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MerchFragment extends Fragment implements onItemClick {
    final String TAG = "MerchFragment";

    RecyclerView recyclerView;
    List<merchModel> merchModelList;
    MerchItemsAdapter merch_Items_adapter;
    FirebaseAuth firebaseAuth;
    ImageView cart, account;
    FirebaseFirestore firestore;
    TextView cartCountIcon;
    LoaderView loaderView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_merch,container,false);
        loaderView = view.findViewById(R.id.dots_progress);
        loaderView.setVisibility(View.VISIBLE);
        recyclerView=view.findViewById(R.id.merch_recyclerview);
        cartCountIcon = view.findViewById(R.id.cart_count);

        firebaseAuth=FirebaseAuth.getInstance();
        firestore= FirebaseFirestore.getInstance();
        merchModelList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        merch_Items_adapter =new MerchItemsAdapter(merchModelList,getContext(),this);

        cart=view.findViewById(R.id.cart);
        cart.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));
        cartCountIcon.setOnClickListener(v -> startActivity(new Intent(getActivity(), CartActivity.class)));

        account=view.findViewById(R.id.account);
        account.setOnClickListener(v -> startActivity(new Intent(getActivity(), Account.class)));


        populate_merch();
        setCartCountIcon();

        return view;

    }

    @Override
    public void onResume(){
        setCartCountIcon();
        ((BottomNavigationView)getActivity().findViewById(R.id.bottomNavigationView)).getMenu().findItem(R.id.merch).setChecked(true);
        MainActivity.index=R.id.merch;
        super.onResume();

    }

    public void setCartCountIcon(){
        int cartCount = Utility.calculateCartQuantity(getContext());
        if(cartCount != 0){
            cartCountIcon.setVisibility(View.VISIBLE);
            cartCountIcon.setText(String.format("%d", cartCount));
        }else{
            cartCountIcon.setVisibility(View.GONE);
        }
    }


    private void populate_merch() {
        firestore.collection("Merch").get().addOnCompleteListener(task -> {
            for(DocumentSnapshot documentSnapshot : task.getResult()){
                ArrayList<String> obj= (ArrayList<String>) documentSnapshot.get("Images");

                merchModelList.add(new merchModel(
                        documentSnapshot.getString("Name"),
                        documentSnapshot.getString("Type"),
                        documentSnapshot.getString("Price"),
                        documentSnapshot.getString("Description"),
                        documentSnapshot.getString("Image"),
                        documentSnapshot.getBoolean("Available"),
                        documentSnapshot.getBoolean("Small"),
                        documentSnapshot.getBoolean("Medium"),
                        documentSnapshot.getBoolean("Large"),
                        documentSnapshot.getBoolean("ExtraLarge"),
                        documentSnapshot.getBoolean("XXLarge"),
                        obj,
                        documentSnapshot.getString("Video"),
                        documentSnapshot.getString("Small_Description"),
                        documentSnapshot.getString("background")
                ));
            }
            recyclerView.setAdapter(merch_Items_adapter);
            loaderView.setVisibility(View.GONE);
        });
    }

    @Override
    public void OnAnyClick(int position) {

    }

    @Override
    public void Onclick(int position) {
        //Toast.makeText(getContext(), "Item clicked: " + merch_modelList.get(position).getName(), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Item clicked: " + merchModelList.get(position).getName());
        Intent intent=new Intent(getActivity(), MerchDescriptionActivity.class);
        intent.putExtra("item", merchModelList.get(position));
        startActivity(intent);
    }

    @Override
    public void OnIncrementClick(int position) {

    }

    @Override
    public void OnDecrementClick(int position) {

    }

}
