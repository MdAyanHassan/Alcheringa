package com.alcheringa.alcheringa2022;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.alcheringa.alcheringa2022.Database.DBHandler;
import com.alcheringa.alcheringa2022.Model.cartModel;
import com.alcheringa.alcheringa2022.services.Retrofit_Class;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.razorpay.Checkout;
import com.razorpay.Order;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.squareup.okhttp.ResponseBody;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OrderSummaryActivity extends AppCompatActivity implements PaymentResultWithDataListener {
    private static final String TAG = "OrderSummaryActivityLog";
    DBHandler dbHandler;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button Pay;
    TextView name,address,total_price,total,order_total;
    LoaderView loaderView;
    ArrayList<cartModel> arrayList;
    String user_phone;
    String user_name;
    String user_road;
    String user_house;
    String user_state;
    String user_city;
    String user_pin_code;
    int shipping_charges;
    String Email;
    long amount;
    TextView shipping;
    private static Retrofit.Builder builder;
    public static  Retrofit retrofit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);
        builder=new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/u/0/d/e/1FAIpQLSfugQ32uHJp8XNA5-EwrGGcJgJeXwqzEOMaAKuyMBsC3jGFXg/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit=builder.build();
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
        loaderView = findViewById(R.id.dots_progress);
        shipping = findViewById(R.id.shipping_charge);

        Intent intent = getIntent();

        user_phone = intent.getStringExtra("phone");
        user_name = intent.getStringExtra("name");
        user_road = intent.getStringExtra("road");
        user_house = intent.getStringExtra("house");
        user_state = intent.getStringExtra("state");
        user_city = intent.getStringExtra("city");
        user_pin_code = intent.getStringExtra("pincode");
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Email = sharedPreferences.getString("email", "");

        name.setText(user_name);
        address.setText(String.format("%s, %s\n%s, %s - %s\n%s",
                user_house, user_road, user_city, user_state, user_pin_code, user_phone)
        );

        arrayList = dbHandler.readCourses();

        shipping_charges = 0;
        getShippingCharges();
        amount = calculate_amount();

        OrderSummaryAdapter adapter = new OrderSummaryAdapter(this, arrayList);
        ListView listView = findViewById(R.id.items_list_view);
        listView.setAdapter(adapter);
        setListViewHeightBasedOnItems(listView);

        Pay.setOnClickListener(v -> {
            Pay.setEnabled(false);
            Thread thread = new Thread(() -> {
                try {
                    OrderSummaryActivity.this.startPayment((int) amount);
                } catch (Exception e) {
                    Pay.setEnabled(true);
                    e.printStackTrace();
                }
            });
            thread.start();
        });

        ImageButton backBtn = findViewById(R.id.back_button);
        backBtn.setOnClickListener(v -> finish());

        loaderView.setVisibility(View.GONE);
    }

    private void getShippingCharges() {
        firebaseFirestore.collection("Constants").document("Merch").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    shipping_charges = Integer.parseInt(document.getString("shipping"));
                    shipping.setText(String.format("₹%s.00", document.getString("shipping")));
                }else{
                    Log.d(TAG, "Error getting documents: Document does not exist");
                }
            }else {
                Log.d(TAG, "Error getting documents: ", task.getException());
            }
            Log.d(TAG, "The obtained shipping amount is: "+ shipping_charges);
            amount = calculate_amount();
        });
    }


    private long calculate_amount() {
        long amt=0;
        int  cnt=0;
        for(int i=0;i<arrayList.size();i++){
            amt=amt+Long.parseLong(arrayList.get(i).getPrice())*Long.parseLong(arrayList.get(i).getCount());
            cnt=cnt+Integer.parseInt(arrayList.get(i).getCount());
        }

        //final int shipping_cost = 45;
        shipping_charges=cnt*shipping_charges;
        if(user_pin_code.equals("781039")){
            shipping_charges=0;
        }
        int total_and_shipping = shipping_charges + (int) amt;
        String amount = MessageFormat.format("₹{0}.", amt);
        shipping.setText(String.format("₹%s.00", shipping_charges+""));
        total.setText(String.format(Locale.getDefault(),"₹%d.00", total_and_shipping)); //total
        order_total.setText(String.format("₹%d.", total_and_shipping)); //bottom order total

        total_price.setText(String.format("%s00", amount));  //total MRP

        return amt;

    }



    private void startPayment(int total_price){
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_live_0MqrfaJ3rgG7Bh", "MLVrcuKucdYi9qCSho3ACUB7");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (total_price+shipping_charges)*100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");

            Order order = razorpay.Orders.create(orderRequest);
            Log.d(TAG, order.get("id"));
           // checkoutOrder(order.get("id"), total_price);
            AddOrderToFirebase(arrayList,order.get("id"));

        } catch (RazorpayException | JSONException e) {
            Pay.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Error: Could not proceed to payment page",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }

    public void checkoutOrder(String order_id, int total_price) {

        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_live_0MqrfaJ3rgG7Bh");

        checkout.setImage(R.drawable.ic_alcher_logo_top_nav);

        final Activity activity = this;

        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        String shared_email = sharedPreferences.getString("email", "");

        try {
            JSONObject options = new JSONObject();

            options.put("name", "Alcheringa 2022");
            options.put("description", "Alcheringa Merch Order");
            options.put("order_id", order_id);
            options.put("theme.color", "#ee6337");
            options.put("currency", "INR");
            options.put("amount", (total_price+shipping_charges)*100);//pass amount in currency subunits
            options.put("prefill.name", user_name);
            options.put("prefill.contact","+91"+user_phone);
            options.put("prefill.email",shared_email);
            options.put("send_sms_hash",true);
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 3);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Pay.setEnabled(true);
            Log.e(TAG, "Error in starting Razorpay Checkout", e);
        }
    }

    private void AddOrderToFirebase(ArrayList<cartModel> order_list,String PaymentId) {
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
            map.put("image",order_list.get(i).getImage());
            map.put("Timestamp",new Date());
            list.add(map);
            //total_price += Integer.parseInt(order_list.get(i).getPrice());
        }
        data.put("orders",list);
        data.put("Name",user_name);
        data.put("Phone",user_phone);
        data.put("House_No",user_house);
        data.put("Area",user_road);
        data.put("Order ID",PaymentId);
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
                                        AddToExcel(arrayList,PaymentId);
                                        Toast.makeText(getApplicationContext(), "Your order is placed", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(getApplicationContext(), "Some Error Occurred orders", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Some Error Occurred users", Toast.LENGTH_SHORT).show();

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
        AddOrderToFirebase(arrayList,paymentData.getPaymentId());
        clear_cart();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    private void AddToExcel(ArrayList<cartModel> order_list,String PaymentId) {

        for(int i=0;i<order_list.size();i++){
            Map<String,Object> data=new HashMap<>();
            data.put("entry.131168542",order_list.get(i).getName());
            data.put("entry.1664498189",order_list.get(i).getPrice());
            data.put("entry.2091239120",order_list.get(i).getSize());
            data.put("entry.363590504",order_list.get(i).getType());
            data.put("entry.2040691413",new Date()+"");
            data.put("entry.1576164204",user_phone);
            data.put("entry.1382179014",user_house);
            data.put("entry.1495324823",user_road);
            data.put("entry.2048595423",user_state);
            data.put("entry.1655477142",user_city);
            data.put("entry.848668946",user_pin_code);
            data.put("entry.822567484",PaymentId);
            data.put("entry.1277791907",""+amount);
            data.put("entry.1392578640",order_list.get(i).getCount());
            data.put("entry.559020023",user_name);
            data.put("entry.1216607284",Email);
            Volley(data);
            //total_price += Integer.parseInt(order_list.get(i).getPrice());
        };
        clear_cart();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();

    }

    private void Volley(Map<String, Object> map) {
        Retrofit_Class retrofit_class=retrofit.create(Retrofit_Class.class);
        Call<ResponseBody> call=retrofit_class.DataToExcel(map);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Toast.makeText(OrderSummaryActivity.this, ""+response.toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
                //Toast.makeText(OrderSummaryActivity.this, "error_occured", Toast.LENGTH_SHORT).show();
                Log.d("post",call.toString());
            }
        });


    }

    private void clear_cart() {
        DBHandler dbHandler =new DBHandler(getApplicationContext());
        dbHandler.Delete_all();
    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        Pay.setEnabled(true);
        Log.d(TAG, "onPaymentFailure");
        Toast.makeText(getApplicationContext(), "Payment Failed", Toast.LENGTH_SHORT).show();
    }
}



