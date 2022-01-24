package com.example.alcheringa2022;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.alcheringa2022.Model.cartModel;
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

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class OrderSummaryActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private static final String TAG = "OrderSummaryActivityLog";
    DBHandler dbHandler;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button Pay;
    TextView name,address,total_price,total,order_total;
    ArrayList<cartModel> arrayList;
    String user_phone;
    String user_name;
    String user_road;
    String user_house;
    String user_state;
    String user_city;
    String user_pin_code;
    long amount;
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

        Intent intent = getIntent();

        user_phone = intent.getStringExtra("phone");
        user_name = intent.getStringExtra("name");
        user_road = intent.getStringExtra("road");
        user_house = intent.getStringExtra("house");
        user_state = intent.getStringExtra("state");
        user_city = intent.getStringExtra("city");
        user_pin_code = intent.getStringExtra("pincode");

        name.setText(user_name);
        address.setText(String.format("%s, %s\n%s, %s - %s\n%s",
                user_house, user_road, user_city, user_state, user_pin_code, user_phone)
        );

        arrayList = dbHandler.readCourses();

        amount = calculate_amount();

        OrderSummaryAdapter adapter = new OrderSummaryAdapter(this, arrayList);
        ListView listView = findViewById(R.id.items_list_view);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnItems(listView);

        Pay.setOnClickListener(v -> {
            Thread thread = new Thread(() -> {
                try {
                    OrderSummaryActivity.this.startPayment((int) amount);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            thread.start();
        });

        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(v -> finish());

        Toast.makeText(this,""+Utility.calculateCartQuantity(this),Toast.LENGTH_LONG).show();
    }



    private long calculate_amount() {
        long amt=0;
        for(int i=0;i<arrayList.size();i++){
            amt=amt+Long.parseLong(arrayList.get(i).getPrice())*Long.parseLong(arrayList.get(i).getCount());
        }

        final int shipping_cost = 45;
        int total_and_shipping = shipping_cost + (int) amt;
        String amount = MessageFormat.format("₹{0}.", amt);

        total.setText(String.format(Locale.getDefault(),"₹%d.00", total_and_shipping)); //total
        order_total.setText(String.format("₹%d.", total_and_shipping)); //bottom order total

        total_price.setText(String.format("%s00", amount));  //total MRP

        return amt;

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

    }

    public void checkoutOrder(String order_id, int total_price) {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_JR2iDD635lZNVE");

        checkout.setImage(R.drawable.alcheringa_logo);

        final Activity activity = this;

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Alcheringa 2022");
            options.put("description", "Alcheringa Merch Order");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("order_id", order_id);
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", total_price*100);//pass amount in currency subunits
            options.put("prefill.name", user_name);
            options.put("prefill.contact",user_phone);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 3);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    private void Add_Order(ArrayList<cartModel> order_list) {
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
        data.put("Name",user_name);
        data.put("Phone",user_phone);
        data.put("House_No",user_house);
        data.put("Area",user_road);
        data.put("State",user_state);
        data.put("City",user_city);
        data.put("Pincode",user_pin_code);
        data.put("Method","Online Payment");

        assert email != null;
        firebaseFirestore.collection("USERS").document(email).collection("ORDERS").
                document(id).set(data).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        firebaseFirestore.collection("ORDERS").document(id).set(data).
                                addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        Toast.makeText(getApplicationContext(), "Your order is placed", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Some Error Occurred Please try again", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Some Error Occurred Please try again", Toast.LENGTH_SHORT).show();

                    }
                });
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
        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_LONG).show();
    }
}



