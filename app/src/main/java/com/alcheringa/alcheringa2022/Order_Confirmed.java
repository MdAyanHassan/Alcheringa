package com.alcheringa.alcheringa2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alcheringa.alcheringa2022.Database.DBHandler;
import com.alcheringa.alcheringa2022.Model.cartModel;
import com.alcheringa.alcheringa2022.services.Retrofit_Class;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Order_Confirmed extends AppCompatActivity implements PaymentResultWithDataListener {
    ConstraintLayout confirmview;
    ImageButton back_btn;
    DBHandler dbHandler;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    Button Pay;
    FrameLayout continue_shopping;
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
    private static Retrofit.Builder builder;
    public static  Retrofit retrofit;
    public static final String DATA = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static Random RANDOM = new Random();

    public static String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        dbHandler=new DBHandler(this);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseFirestore=FirebaseFirestore.getInstance();
        builder=new Retrofit.Builder()
                .baseUrl("https://docs.google.com/forms/d/e/1FAIpQLSd2uK87A7zPdbag7B9BCPqf5O_MyOw8CDz7mr7D4B0Q_h7ynA/")
                .addConverterFactory(GsonConverterFactory.create());
        retrofit=builder.build();
        Intent intent = getIntent();
        user_phone = intent.getStringExtra("phone");
        user_name = intent.getStringExtra("name");
        user_road = intent.getStringExtra("road");
        user_house = intent.getStringExtra("house");
        user_state = intent.getStringExtra("state");
        user_city = intent.getStringExtra("city");
        user_pin_code = intent.getStringExtra("pincode");
        shipping_charges = Integer.parseInt(intent.getStringExtra("shipping_charge"));
    //    Toast.makeText(this, ""+shipping_charges, Toast.LENGTH_SHORT).show();
        arrayList= (ArrayList<cartModel>) intent.getSerializableExtra("list");
        SharedPreferences sharedPreferences = getSharedPreferences("USER", MODE_PRIVATE);
        Email = sharedPreferences.getString("email", "");
        amount=calculate_amount();
        setContentView(R.layout.activity_order_confirmed);
        loaderView=findViewById(R.id.dots_progress);
        confirmview=findViewById(R.id.confirmview);
        Checkout.preload(getApplicationContext());
        startPayment(100);
        back_btn=findViewById(R.id.backbtn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });
        continue_shopping=findViewById(R.id.continue_shopping);
        continue_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

    }
    private long calculate_amount() {
        long amt=0;
        int  cnt=0;
        for(int i=0;i<arrayList.size();i++){
            amt=amt+Long.parseLong(arrayList.get(i).price)*Long.parseLong(arrayList.get(i).count);
            cnt=cnt+Integer.parseInt(arrayList.get(i).count);
        }

        //final int shipping_cost = 45;
        shipping_charges=cnt*shipping_charges;
        if(user_pin_code.equals("781039")){
            shipping_charges=0;
        }
        return shipping_charges + (int) amt;
    }

    private void startPayment(int total_price){
        try {
            RazorpayClient razorpay = new RazorpayClient("rzp_live_0MqrfaJ3rgG7Bh", "MLVrcuKucdYi9qCSho3ACUB7");

            JSONObject orderRequest = new JSONObject();
            orderRequest.put("amount", (total_price+shipping_charges)*100); // amount in the smallest currency unit
            orderRequest.put("currency", "INR");

             //Order order = razorpay.Orders.create(orderRequest);
            //Log.d("TAG", order.get("id"));
           //  checkoutOrder(order.get("id"), total_price);
            AddOrderToFirebase(arrayList,randomString(20));

        } catch (RazorpayException | JSONException e) {
            Pay.setEnabled(true);
            Toast.makeText(getApplicationContext(), "Error: Could not proceed to payment page",Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }

    }
    private void AddToExcel(ArrayList<cartModel> order_list, String PaymentId) {

        for(int i=0;i<order_list.size();i++){
            Map<String,Object> data=new HashMap<>();
            data.put("entry.1217211808", order_list.get(i).name);
            data.put("entry.365380202", order_list.get(i).price);
            data.put("entry.1596700490", order_list.get(i).size);
            data.put("entry.1406401370", order_list.get(i).type);
            data.put("entry.461981693",new Date()+"");
            data.put("entry.271845590",user_phone);
            data.put("entry.1853344447",user_house);
            data.put("entry.1389883145",user_road);
            data.put("entry.1369895676",user_state);
            data.put("entry.701619952",user_city);
            data.put("entry.1333764685",user_pin_code);
            data.put("entry.505659208",PaymentId);
            data.put("entry.1800146690",""+amount);
            data.put("entry.1596610928", order_list.get(i).count);
            data.put("entry.2032856044",user_name);
            data.put("entry.121322222",Email);
            Volley(data);
            //total_price += Integer.parseInt(order_list.get(i).getPrice());
        };
        clear_cart();
        Intent intent = new Intent(this, Order_Confirmed.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

    }
    private void AddOrderToFirebase(ArrayList<cartModel> order_list,String PaymentId) {
        //int total_price = 0;
        String email= Objects.requireNonNull(firebaseAuth.getCurrentUser()).getEmail();

        String id=firebaseFirestore.collection("USERS").document().getId();
        Map<String,Object> data=new HashMap<>();
        ArrayList<Map<String,Object>> list=new ArrayList<>();
        for(int i=0;i<order_list.size();i++){
            Map<String,Object> map=new HashMap<>();
            map.put("Name", order_list.get(i).name);
            map.put("Count", order_list.get(i).count);
            map.put("Price", order_list.get(i).price);
            map.put("Size", order_list.get(i).size);
            map.put("Type", order_list.get(i).type);
            map.put("isDelivered",false);
            map.put("image", order_list.get(i).image);
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
                                loaderView.setVisibility(View.GONE);
                                confirmview.setVisibility(View.VISIBLE);
                                Toast.makeText(getApplicationContext(), "Your order is placed", Toast.LENGTH_SHORT).show();

                            }
                            else{

                                Toast.makeText(getApplicationContext(), "Some error occurred while placing your orders, try again ! ", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                        });
            }
            else{
                Toast.makeText(getApplicationContext(), "Some error occurred while placing your orders, try again !", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();

            }
        });
    }
    private void clear_cart() {
        DBHandler dbHandler =new DBHandler(getApplicationContext());
        dbHandler.Delete_all();
    }
    private void Volley(Map<String, Object> map) {
        Retrofit_Class retrofit_class=retrofit.create(Retrofit_Class.class);
//        Call<ResponseBody> call=retrofit_class.DataToExcel(map);
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
//                Toast.makeText(getApplicationContext(), ""+response.toString(), Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<ResponseBody> call, Throwable t) {
//               // Toast.makeText(getApplicationContext(), "error occured code x16389", Toast.LENGTH_SHORT).show();
//                Log.d("post",call.toString());
//            }
//        });


    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
        super.onBackPressed();
    }
}