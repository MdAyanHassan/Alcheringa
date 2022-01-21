package com.example.alcheringa2022;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alcheringa2022.Model.Cart_model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class OrderSummaryActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private static final String TAG = "OrderSummaryActivityLog";
    DBHandler dbHandler;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button Pay;
    TextView name,address,total_price,total,order_total;
    ArrayList<Cart_model> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        Checkout.preload(getApplicationContext());

        dbHandler=new DBHandler(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        Pay=findViewById(R.id.pay_btn);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        total_price=findViewById(R.id.total_mrp);
        total=findViewById(R.id.total);
        order_total=findViewById(R.id.order_total);




        arrayList = dbHandler.readCourses();
        long amount = calculate_amount();
        OrderSummaryAdapter adapter = new OrderSummaryAdapter(this, arrayList);
        ListView listView = findViewById(R.id.items_list_view);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnItems(listView);
        //Pay.setOnClickListener(v -> Add_Order(arrayList));
        Pay.setOnClickListener(v -> {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OrderSummaryActivity.this.startPayment((int) amount);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            thread.start();
        });
    }

    private void Add_Order(ArrayList<Cart_model> order_list) {
        //int total_price = 0;
        String email= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        String id=firebaseFirestore.collection("USERS").document().getId();
        Map<String,Object> data=new HashMap<>();
        ArrayList<Map<String,Object>> list=new ArrayList<>();
        for(int i=0;i<order_list.size();i++){
            Map<String,Object> map=new HashMap<>();
            map.put("Name",order_list.get(i).getName());
            map.put("Count",order_list.get(i).getCount());
            map.put("Price",order_list.get(i).getPrice());
            map.put("Size",order_list.get(i).getSize());
            map.put("Type",order_list.get(i).getType());
            map.put("isDelivered",false);
            list.add(map);
            //total_price += Integer.parseInt(order_list.get(i).getPrice());
        }
        data.put("orders",list);
        data.put("Name","VIPIN JALUTHRIA");
        data.put("Phone","7011879379");
        data.put("House_No","6745 3rd floor");
        data.put("Area","Karol Bagh Dev Nagar");
        data.put("State","Delhi");
        data.put("City","New Delhi");
        data.put("Pincode","110005");
        data.put("Method","COD");

        assert email != null;
        firebaseFirestore.collection("USERS").document(email).collection("ORDERS").
                document(id).set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    firebaseFirestore.collection("ORDERS").document(id).set(data).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "Order added to Firebase", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Some Error Occurred Please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(getApplicationContext(), "Some Error Occurred Please try again", Toast.LENGTH_SHORT).show();

                }
            }
        });

        //int finalTotal_price = total_price;


    }

    private void startPayment(int total_price){
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_test_JR2iDD635lZNVE", "W0HOMo0KpOKar9kgugOGkZ5U");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", total_price*100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");

            Order order = razorpay.Orders.create(orderRequest);
            Log.d(TAG, order.get("id"));
            checkoutOrder(order.get("id"), total_price);


        } catch (RazorpayException | JSONException e) {
            // Handle Exception
            System.out.println(e.getMessage());
        }

        //checkoutOrder("order_IkQfm5cnZtq9ti");
    }

    private long calculate_amount() {
        long amt=0;
        for(int i=0;i<arrayList.size();i++){
            Toast.makeText(getApplicationContext(), ""+arrayList.get(i).getCount(), Toast.LENGTH_SHORT).show();
            amt=amt+Long.parseLong(arrayList.get(i).getPrice())*Long.parseLong(arrayList.get(i).getCount());
        }
        String total_amount ="₹"+ amt + ".00";
        name.setText("vipin");
        address.setText("vipin");
        total.setText(total_amount);
        order_total.setText(total_amount);

        total_price.setText(total_amount);

        return amt;

    }

    public void checkoutOrder(String order_id, int total_price) {


        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_JR2iDD635lZNVE");

        checkout.setImage(R.drawable.alcheringa_logo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Alcheringa 2022");
            options.put("description", "Merch Order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order_id);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", total_price*100);//pass amount in currency subunits
            options.put("prefill.email", "name@example.com");
            options.put("prefill.contact","9284823088");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }



    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    //RazorPay functions:

    @Override
    public void onPaymentSuccess(String razorpayPaymentID, PaymentData paymentData) {
        Log.d(TAG, "onPaymentSuccess razorpayPaymentID: " + razorpayPaymentID);
        Toast.makeText(getApplicationContext(), "Payment Successful!", Toast.LENGTH_LONG).show();
        Add_Order(arrayList);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Log.d(TAG, "onPaymentFailure");
    }
}



