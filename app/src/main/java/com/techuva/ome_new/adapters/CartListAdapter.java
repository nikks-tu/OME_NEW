package com.techuva.ome_new.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;
import com.techuva.ome_new.R;
import com.techuva.ome_new.api_interface.PlaceOrderDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.holders.CartListRCVHolder;
import com.techuva.ome_new.object_models.GetDealerResultObject;
import com.techuva.ome_new.object_models.QuatityList;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class CartListAdapter extends RecyclerView.Adapter<CartListRCVHolder> {// Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<PlaceOrderDO> arrayList;
    private ArrayList<GetDealerResultObject> dealersList;
    private ArrayList<GetDealerResultObject> dealersListHavingProduct;
    private Context context;
    private CartListRCVHolder listHolder;
    private CompoundButton lastCheckedRB = null;
    private String UserName = "";
    private int selectedPosition = -1;
    private EventListener listener;
    private ArrayList<QuatityList> quantityList;
    private QuatityList quatityListDO;
    private QuantityAdapter quantityAdapter;
    List<Integer> list;
    DealersAdapter dealersAdapter;
    private Activity activity;
    private boolean delerselection = false;
    private boolean qunityenable = false;
    int dealerID;
    private int productId = 0;
    String dealerName = "";
    private int orderId;
    private int companyId;
    int totalAmount;
    int price;
    String userID = "";
    String userType = "";
    String accessToken = "";
    int newQty;
    public Dialog dialog;
    private AnimationDrawable animationDrawable;

    public CartListAdapter(Activity activity, Context context, ArrayList<GetDealerResultObject> dealersList, ArrayList<PlaceOrderDO> arrayList, EventListener listener) {
        this.activity = activity;
        this.context = context;
        this.dealersList = dealersList;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();

    }


    @Override
    public void onBindViewHolder(CartListRCVHolder holder, int position) {
        PlaceOrderDO model = arrayList.get(position);
        Activity activity = (Activity) context;
        list = new ArrayList<>();
        holder.tv_product_name.setText(model.getProduct_description());
        holder.tv_qty_new.setText(String.valueOf(model.getQuantity()));
        newQty = model.getQuantity();
        userType = MApplication.getString(context, Constants.UserType);

        if(userType.equals(Constants.Shopper))
        {
            holder.tv_product_desc.setText("Supplier : "+model.getCompany_Name());
        }
        else if(userType.equals(Constants.Consumer))
        {
            holder.tv_product_desc.setText("Company : "+model.getCompany_Name());
        }
        holder.tv_dealer_txt.setText(context.getResources().getString(R.string.rs)+" "+model.getPrice());
        accessToken = "Bearer " + MApplication.getString(activity, Constants.AccessToken);
        userID = MApplication.getString(context, Constants.UserID);
        userType = MApplication.getString(context, Constants.UserType);
        dealersListHavingProduct = new ArrayList<>();

        if (list.size() > 0) {
            for (int i = 0; i < dealersList.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (dealersList.get(i).getDealerId().equals(list.get(j))) {
                        dealersListHavingProduct.add(dealersList.get(i));
                    }
                }
            }
        }

        if (dealersListHavingProduct.size() == 1) {
            holder.iv_dealers.setVisibility(View.GONE);
        } else {
            holder.iv_dealers.setVisibility(View.VISIBLE);
        }

        holder.iv_dec_qty.setOnClickListener(v -> {
            if(newQty>1)
            {
                newQty = arrayList.get(position).getQuantity();
                newQty = newQty-1;
                holder.tv_qty_new.setText(String.valueOf(newQty));
                if(userType.equals(Constants.Consumer))
                {
                    orderId = arrayList.get(position).getOrder_id();
                    productId = Integer.parseInt(arrayList.get(position).getProduct_id());
                    companyId = Integer.parseInt(arrayList.get(position).getCompany_id());
                    price = arrayList.get(position).getPrice();
                    totalAmount = newQty*price;
                    CartDatabase.init(context);
                    CartDatabase.updateCheckStatus(arrayList.get(position).getOrder_id(), "true", newQty);
                    modifyProductForConsumer(orderId, newQty, productId, companyId, totalAmount);
                }
                else {
                    orderId = arrayList.get(position).getOrder_id();
                    price = arrayList.get(position).getPrice();
                    totalAmount = newQty*price;
                    CartDatabase.init(context);
                    CartDatabase.updateCheckStatusShopper(arrayList.get(position).getOrder_id(), "true", String.valueOf(newQty));
                    modifyProductForShopper(newQty, arrayList.get(position).getOrder_id(), totalAmount);
                }
            }
        });


        holder.iv_inc_qty.setOnClickListener(v -> {
            newQty = arrayList.get(position).getQuantity();
            newQty = newQty+1;
            holder.tv_qty_new.setText(String.valueOf(newQty));
            if(userType.equals(Constants.Consumer))
            {

                orderId = arrayList.get(position).getOrder_id();
                productId = Integer.parseInt(arrayList.get(position).getProduct_id());
                companyId = Integer.parseInt(arrayList.get(position).getCompany_id());
                price = arrayList.get(position).getPrice();
                totalAmount = newQty*price;
                CartDatabase.init(context);
                CartDatabase.updateCheckStatus(arrayList.get(position).getOrder_id(), "true", newQty);
                modifyProductForConsumer(orderId, newQty, productId, companyId, totalAmount);
            }
            else {
                orderId = arrayList.get(position).getOrder_id();
                price = arrayList.get(position).getPrice();
                totalAmount = newQty*price; CartDatabase.init(context);
                CartDatabase.updateCheckStatusShopper(arrayList.get(position).getOrder_id(), "true", String.valueOf(newQty));
                modifyProductForShopper(newQty, arrayList.get(position).getOrder_id(), totalAmount);
            }
        });

        holder.iv_qty.setOnClickListener(v -> {
            if (holder.lv_qty_list.getSelectedItem() == null) { // user selected nothing...
                holder.lv_qty_list.performClick();
            }
        });

        if (!model.getImageUrl().equals("")) {
            Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
        } else {
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        }
        quantityList = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            if (i % 5 == 0) {
                quatityListDO = new QuatityList();
                quatityListDO.setQuantity(i);
                quantityList.add(quatityListDO);
            }
        }

        qunityenable = false;
        delerselection = false;
        quantityAdapter = new QuantityAdapter(activity, R.layout.item_qty, R.id.tv_qty, quantityList);
        holder.lv_qty_list.setAdapter(quantityAdapter);
        int pos = indexOf(quantityAdapter, model.getQuantity());
        for (int i = 0; i < quantityList.size(); i++) {
            if (model.getQuantity() == quantityList.get(i).getQuantity()) {
                pos = i;
            }
        }

        qunityenable = true;

        holder.iv_delete_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomDialog(arrayList.get(position).getOrder_id());
            //getProductToDelete(orderId);
            }
        });
    }

    private void getProductToDelete(int productId) {
        JsonObject jsonObject = new JsonObject();
        JsonArray productArray = new JsonArray();
        productArray.add(productId);
        jsonObject.add("order_ids", productArray);
        if(userType.equals(Constants.Shopper))
        {
            deleteProductForShopper(jsonObject);
        }
        else
        {
            deleteProductForConsumer(jsonObject);
        }
    }


    private void modifyProductForShopper(int qty, int orderId, int totalAmount) {

        JsonArray productArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("quantity", qty);
        jsonObject.addProperty("order_id", orderId);
        jsonObject.addProperty("total_amount", totalAmount);

        productArray.add(jsonObject);
        showLoaderNew();
        modifyProductForShopper(productArray);
    }

    private void modifyProductForConsumer(int orderId, int qty, int productId, int companyId, int totalAmount) {

        JsonArray productArray = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("order_id", orderId);
        jsonObject.addProperty("company_id", companyId);
        jsonObject.addProperty("product_id", productId);
        jsonObject.addProperty("quantity", qty);
        jsonObject.addProperty("total_amount", totalAmount);
        productArray.add(jsonObject);
        showLoaderNew();
        modifyProductForConsumer(productArray);

    }

    @Override
    public CartListRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_cart_list, viewGroup, false);
        listHolder = new CartListRCVHolder(mainGroup);
        return listHolder;

    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public String getSelectedItem() {
        if (selectedPosition != -1) {
            // Toast.makeText(context, "Selected Item : " + arrayList.get(selectedPosition).getCompanyName(), Toast.LENGTH_SHORT).show();
            return String.valueOf(selectedPosition);
        }
        return "";
    }


    public interface EventListener {
        void onEvent(Boolean data);

        void onDelete(Boolean data);
    }

    private int indexOf(final QuantityAdapter adapter, Object value) {
        for (int index = 0, count = adapter.getCount(); index < count; ++index) {
            if (adapter.getItem(index).equals(value)) {
                return index;
            }
        }
        return -1;
    }


    private void showCustomDialog(int orderId) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);
        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, viewGroup, false);
        //Now we need an AlertDialog.Builder object
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(context);
        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView tv_alertText = dialogView.findViewById(R.id.tv_alertText);
        TextView button_yes = dialogView.findViewById(R.id.button_yes);
        TextView button_no = dialogView.findViewById(R.id.button_no);
        tv_alertText.setText(context.getResources().getString(R.string.delete_product));
        androidx.appcompat.app.AlertDialog alertDialog = builder.create();
        alertDialog.show();
        button_yes.setOnClickListener(v -> {
            getProductToDelete(orderId);
            alertDialog.dismiss();
        });

        button_no.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        //finally creating the alert dialog and displaying it

    }

    private void deleteProductForConsumer(JsonObject jsonObject) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<PlaceOrderMainObject> call = service.getStringScalar(1, jsonArray);
        Call<JsonElement> call = service.deleteConsumerProductFromCart(Integer.parseInt(userID), accessToken, jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode == 0) {
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        //notifyDataSetChanged();
                        //listener.onEvent(true);
                        listener.onDelete(true);
                    } else {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }


    private void deleteProductForShopper(JsonObject jsonObject) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);

        //Call<PlaceOrderMainObject> call = service.getStringScalar(1, jsonArray);
        Call<JsonElement> call = service.deleteShopperProductFromCart(Integer.parseInt(userID), accessToken, jsonObject);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode == 0) {
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        //notifyDataSetChanged();
                        //listener.onEvent(true);
                        CartDatabase.init(context);
                        listener.onDelete(true);
                    } else {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void modifyProductForShopper(JsonArray jsonArray) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);
        Call<JsonElement> call = service.updateShopperProductInCart(Integer.parseInt(userID), accessToken, jsonArray);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideLoader();
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode == 0) {
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        //notifyDataSetChanged();
                        listener.onEvent(true);
                    } else {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }
    private void modifyProductForConsumer(JsonArray jsonArray) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PlaceOrderDataInterface service = retrofit.create(PlaceOrderDataInterface.class);
        Call<JsonElement> call = service.updateConsumerProductInCart(Integer.parseInt(userID), accessToken, jsonArray);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideLoader();
                if (response.body() != null) {
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if (errorCode == 0) {
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                        listener.onEvent(true);
                    } else {
                        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    //hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    public void showLoaderNew() {
        // mActivity.runOnUiThread(new get.Runloader(context.getResources().getString(R.string.loading)));
        activity.runOnUiThread(new Runloader(context.getResources().getString(R.string.loading)));
    }


    class Runloader implements Runnable {
        private String strrMsg;

        public Runloader(String strMsg) {
            this.strrMsg = strMsg;
        }

        @SuppressWarnings("ResourceType")
        @Override
        public void run() {
            try {
                if (dialog == null)
                {
                    dialog = new Dialog(context,R.style.Theme_AppCompat_Light_DarkActionBar);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.loading);
                dialog.setCancelable(false);

                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    dialog=null;
                }
                assert dialog != null;
                dialog.show();

                ImageView imgeView = dialog
                        .findViewById(R.id.imgeView);
                TextView tvLoading = dialog
                        .findViewById(R.id.tvLoading);
                if (!strrMsg.equalsIgnoreCase(""))
                    tvLoading.setText(strrMsg);

                imgeView.setBackgroundResource(R.drawable.frame);

                animationDrawable = (AnimationDrawable) imgeView
                        .getBackground();
                imgeView.post(() -> {
                    if (animationDrawable != null)
                        animationDrawable.start();
                });
            } catch (Exception e)
            {
                Log.println(1, "catch", ""+e);

            }
        }
    }

    public void hideLoader() {
        activity.runOnUiThread(() -> {
            try
            {
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}