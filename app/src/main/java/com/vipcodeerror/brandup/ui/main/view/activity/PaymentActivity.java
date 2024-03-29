package com.vipcodeerror.brandup.ui.main.view.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.razorpay.Checkout;
import com.razorpay.PaymentData;
import com.razorpay.PaymentResultWithDataListener;
import com.vipcodeerror.brandup.R;
import com.vipcodeerror.brandup.data.api.ApiHelper;
import com.vipcodeerror.brandup.data.api.ApiServiceImpl;
import com.vipcodeerror.brandup.data.model.ApiResponse;
import com.vipcodeerror.brandup.data.model.OrderIdResponse;
import com.vipcodeerror.brandup.ui.base.ViewModelFactory;
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel;
import com.vipcodeerror.brandup.util.Resource;
import com.vipcodeerror.brandup.util.SharedPreferenceUtil;
import com.vipcodeerror.brandup.util.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class PaymentActivity extends AppCompatActivity implements PaymentResultWithDataListener {

    MainViewModel mainViewModel;
    SharedPreferenceUtil sharedPreferenceUtil;
    String price = "";
    String planType = "";
    String planName = "";
    String bCardLimit = "";
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = setupViewModel();
        sharedPreferenceUtil = new SharedPreferenceUtil(this);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Wait .... ");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);

        price = getIntent().getStringExtra("amt");
        planType = getIntent().getStringExtra("plan_type");
        planName = getIntent().getStringExtra("plan_name");
        bCardLimit = getIntent().getStringExtra("buss_limit");

        createOrderID(price, sharedPreferenceUtil.getValueString("token"));
    }

    @Override
    public void onPaymentSuccess(String s, PaymentData paymentData) {
        verifyTransaction(paymentData.getSignature(),
                paymentData.getPaymentId(),
                paymentData.getOrderId(), sharedPreferenceUtil.getValueString("user_id"), planType,
                sharedPreferenceUtil.getValueString("token"));

    }

    @Override
    public void onPaymentError(int i, String s, PaymentData paymentData) {
        finish();
    }


    public void startRazorPayMent(String order_id) throws JSONException {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_Pl4Z9TGsFx1M4J");
        checkout.setImage(R.mipmap.ic_launcher_round);

        new Thread(new Runnable() {
            @Override
            public void run() {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(2);

                int amt = Integer.parseInt(price);

                try {
                    JSONObject options = new JSONObject();
                    options.put("name", "Brand Up");
                    options.put("description", "Upgrade Plan to " + planType);
                    //You can omit the image option to fetch the image from dashboard
                    options.put("image", "https://s3.amazonaws.com/zp-mobile/images/rzp.png");
                    options.put("currency", "INR");
                    options.put("amount", Float.parseFloat(df.format(amt)) * 100);
                    options.put("order_id", order_id);//from response of step 3.

                    JSONObject preFill = new JSONObject();
                    preFill.put("email", "test@razorpay.com");
                    preFill.put("contact", "8237798961");

                    options.put("prefill", preFill);

                    checkout.open(PaymentActivity.this, options);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private MainViewModel setupViewModel() {
        return  ViewModelProviders.of(
                this,
                new ViewModelFactory(new ApiHelper(new ApiServiceImpl()))
        ).get(MainViewModel.class);
    }

    private void createOrderID(String amt,String token) {
        mainViewModel.createOrderIdData(amt, token).observe(this, new Observer<Resource<OrderIdResponse>>() {
            @Override
            public void onChanged(Resource<OrderIdResponse> orderIdResponseResource) {
                if (orderIdResponseResource.getStatus() == Status.SUCCESS){
                    if(orderIdResponseResource.getData() != null) {
                        OrderIdResponse orderIdModel = orderIdResponseResource.getData();
                        try {
                            if (orderIdModel != null){
                                progressDialog.show();
                                startRazorPayMent(orderIdModel.getOrderId());

                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }else if(orderIdResponseResource.getStatus() == Status.ERROR){

                }else if(orderIdResponseResource.getStatus() == Status.LOADING){

                }
            }
        });
    }

    private void verifyTransaction(String paymentSignature,
                                    String razorpayPaymentId,
                                    String razorpayOrderId, String userId, String planType, String token) {
        mainViewModel.verifyTransactionData(paymentSignature, razorpayPaymentId, razorpayOrderId, userId, planType, token).observe(this, new Observer<Resource<ApiResponse>>() {
            @Override
            public void onChanged(Resource<ApiResponse> apiResponseResource) {
                if (apiResponseResource.getStatus() == Status.SUCCESS){
                    if(apiResponseResource.getData() != null) {
                        progressDialog.hide();
                        ApiResponse  apiResponse = apiResponseResource.getData();
                        Toast.makeText(PaymentActivity.this, apiResponse.getToken(), Toast.LENGTH_SHORT).show();
                        sharedPreferenceUtil.save("plan_id", planType);
                        sharedPreferenceUtil.save("plan_name", planName);
                        sharedPreferenceUtil.save("business_card_limit", bCardLimit);
                        sharedPreferenceUtil.save("show_message", true);
                        // restart Acitivty
                        Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                    }
                }else if(apiResponseResource.getStatus() == Status.ERROR){

                }else if(apiResponseResource.getStatus() == Status.LOADING){

                }
            }
        });
    }
}
