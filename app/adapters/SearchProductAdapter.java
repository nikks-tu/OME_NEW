package com.example.ordermadeeasy.adapters;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ordermadeeasy.MainActivity;
import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.activity.ProductDescFragment;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.holders.SearchProductRCVHolder;
import com.example.ordermadeeasy.object_models.GetDealerResultObject;
import com.example.ordermadeeasy.object_models.ProductStatusLineObject;
import com.example.ordermadeeasy.object_models.SearchProductResultObject;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchProductAdapter extends RecyclerView.Adapter<SearchProductRCVHolder> {// Recyclerview will extend to
    // recyclerview adapter

    private ArrayList<GetDealerResultObject> allDealersList;
    private ArrayList<SearchProductResultObject> arrayList;
    private ArrayList<ProductStatusLineObject> productInfo;
    private AppCompatActivity activity;
    private SearchProductRCVHolder listHolder;
    private CompoundButton lastCheckedRB = null;
    private String UserName = "";
    private int selectedPosition = -1;
    private EventListener listener;
    PlaceOrderDO placeOrderDO;
    String dealersList = "";
    StringBuilder sb;
    int dealerId = 0;
    int qty = 05;
    int totalDealers = 0;
    String dealerName = "";
    String retailerId = "";
    String productId = "";
    String userType;
    Boolean productStatus =false;
    CartDatabase cartDatabase;
    String accessToken="";
    Context context;
    final Handler handler = new Handler();
    public SearchProductAdapter(Context context, AppCompatActivity activity, ArrayList<SearchProductResultObject> arrayList, EventListener listener) {
        this.context = context;
        this.activity = activity;
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();
    }

    @Override
    public void onBindViewHolder(SearchProductRCVHolder holder, int position) {


        SearchProductResultObject model = arrayList.get(position);
        productId = String.valueOf(model.getProductId());
        placeOrderDO = new PlaceOrderDO();
        userType = MApplication.getString(context, Constants.UserType);
        holder.tv_product_name.setText(model.getProductDescription());
        if(userType.equals(Constants.Consumer))
        {
            holder.tv_product_desc.setText("Company : "+model.getCompanyName());
        }
        else {
            holder.tv_product_desc.setText("Supplier : "+model.getCompanyName());
        }
        dealersList = new String();
        sb = new StringBuilder();
       /* for (int i = 0; i < model.getProductData().size(); i++) {
            dealerName = model.getProductData().get(0).getDealerName();
            sb.append(model.getProductData().get(i).getDealerId());
            sb.append(",");
        }
        dealersList = sb.toString();*/
        retailerId = MApplication.getString(context, Constants.RetailerID);
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        //serviceCallforAllDealers();


      /*  placeOrderDO.setDealerNames(dealersList);
        if (model.getProductData().size() > 1) {
            totalDealers = model.getProductData().size() - 1;
            holder.tv_dealers_name.setText(dealerName + " +" + totalDealers + " more");
        } else {
            holder.tv_dealers_name.setText(dealerName);
        }*/
      if(userType.equals(Constants.Shopper))
      {
          holder.tv_dealers_name.setText(context.getResources().getString(R.string.rs)+" "+model.getShopperPrice());
      }
      else {
          holder.tv_dealers_name.setText(context.getResources().getString(R.string.rs)+" "+model.getConsumerPrice());
      }
            if(model.getImageUrl()!=null)
            {    if(!model.getImageUrl().equals(""))
            {
                Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
            }
            else
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
            }   else
                Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);

       /* placeOrderDO.dealerId = model.getProductData().get(0).getDealerId();
        placeOrderDO.dealersName = model.getProductData().get(0).getDealerName();
        placeOrderDO.productId = model.getProductId();
        placeOrderDO.quantity = qty;
        placeOrderDO.retailerId = Integer.parseInt(retailerId);
        placeOrderDO.productName = model.getProductName();
        placeOrderDO.productDesc = model.getDisplayName();*/
        //placeOrderDO.productDesc = model.getProductDesc();
        placeOrderDO.status_id = 1;
       // placeOrderDO.imageUrl = model.getImageUrl();
      /*  model.setDealer_names(dealersList);
       *//* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(allDealersList.size()>0)
                {
                    model.setDealerList(allDealersList);
                }
            }
        }, 2000);*//*
        // placeOrderDO.retailerId = Integer.parseInt(MApplication.getString(context, Constants.RetailerID));
        placeOrderDO.receivedQuantity = 0;
        StringBuilder sb1 = new StringBuilder();
        for (int i = 0; i < model.getProductData().size(); i++) {
            sb1.append(model.getProductData().get(i).getDealerId());
            sb1.append(",");
        }
        placeOrderDO.dealerNames = sb1.toString();
*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // Toast.makeText(context, "Click", Toast.LENGTH_SHORT).show();
                loadFragments(ProductDescFragment.newInstance(), model);

            }
        });

    }


    public  void loadFragments(Fragment fragment, SearchProductResultObject model)
    {
       /* ((MainActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();*/
        FragmentTransaction ft =  ((MainActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", model);
        fragment.setArguments(bundle);
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public SearchProductRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_product_search, viewGroup, false);
        listHolder = new SearchProductRCVHolder(mainGroup);
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

        void startLoader(Boolean data);

        void stopLoader(Boolean data);
    }

   /* private void serviceCallforProductStatus(SearchProductRCVHolder holder, final SearchProductResultObject model, final int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ProductStatusDataInterface service = retrofit.create(ProductStatusDataInterface.class);

        //Call<ProductStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new ProductStatusPostParameters(retailerId, productId));
        Call<ProductStatusMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), accessToken,new ProductStatusPostParameters(retailerId, String.valueOf(model.getProductId())));
        call.enqueue(new Callback<ProductStatusMainObject>() {
            @Override
            public void onResponse(Call<ProductStatusMainObject> call, Response<ProductStatusMainObject> response) {
                if (response.body() != null) {
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    productInfo = new ArrayList<>();
                    if (response.body().getInfo().getErrorCode() == 0) {
                        if (response.body().getResult() != null) {
                            productInfo.addAll(response.body().getResult().getValues());
                        }
                        CartDatabase.init(context);
                        if (CartDatabase.getCartListWithProductId(model.getProductId()).size() == 0) {

                                placeOrderDO.dealerId = model.getProductData().get(0).getDealerId();
                                placeOrderDO.productId = model.getProductId();
                                placeOrderDO.quantity = qty;
                                placeOrderDO.retailerId = Integer.parseInt(retailerId);
                                placeOrderDO.productName = model.getProductName();
                                placeOrderDO.productDesc = model.getDisplayName();
                                placeOrderDO.statusId = 1;
                                placeOrderDO.imageUrl = model.getImageUrl();
                                // placeOrderDO.retailerId = Integer.parseInt(MApplication.getString(context, Constants.RetailerID));
                                placeOrderDO.receivedQuantity = 0;
                                StringBuilder sb1 = new StringBuilder();
                                for (int i = 0; i < model.getProductData().size(); i++) {
                                    sb1.append(model.getProductData().get(i).getDealerId());
                                    sb1.append(",");
                                }
                                placeOrderDO.dealerNames = sb1.toString();

                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, arrayList.size());
                                notifyDataSetChanged();
                                listener.onEvent(true);
                                showCustomDialog(placeOrderDO, productInfo, holder);
                        } else {
                            Toast.makeText(context, "Product already added in cart", Toast.LENGTH_SHORT).show();
                        }
                        //Toast.makeText(context, "size" +productInfo.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        listener.stopLoader(true);
                        holder.tv_add_to_cart.setEnabled(true);
                        placeOrderDO.dealerId = model.getProductData().get(0).getDealerId();
                        placeOrderDO.productId = model.getProductId();
                        placeOrderDO.quantity = qty;
                        placeOrderDO.retailerId = Integer.parseInt(retailerId);
                        placeOrderDO.productName = model.getProductName();
                        placeOrderDO.productDesc = model.getDisplayName();
                        placeOrderDO.imageUrl = model.getImageUrl();
                        placeOrderDO.statusId = 1;
                        // placeOrderDO.retailerId = Integer.parseInt(MApplication.getString(context, Constants.RetailerID));
                        placeOrderDO.receivedQuantity = 0;
                        StringBuilder sb2 = new StringBuilder();
                        for (int i = 0; i < model.getProductData().size(); i++) {
                            sb2.append(model.getProductData().get(i).getDealerId());
                            sb2.append(",");
                        }
                        placeOrderDO.dealerNames = sb2.toString();
                        CartDatabase.addProductToCart(placeOrderDO);
                        Toast.makeText(context, "Product added to the cart", Toast.LENGTH_SHORT).show();
                        listener.onEvent(true);
                    }
                } else {
                }

            }

            @Override
            public void onFailure(Call<ProductStatusMainObject> call, Throwable t) {
                //hideloader();
                listener.stopLoader(true);
                holder.tv_add_to_cart.setEnabled(true);
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });

    }
*/
    private void showCustomDialog(PlaceOrderDO placeOrderDO, ArrayList<ProductStatusLineObject> productInfo, SearchProductRCVHolder holder) {
        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = activity.findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(context).inflate(R.layout.alert_dialog, viewGroup, false);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        TextView tv_alertText = dialogView.findViewById(R.id.tv_alertText);
        TextView button_yes = dialogView.findViewById(R.id.button_yes);
        TextView button_no = dialogView.findViewById(R.id.button_no);
        builder.setCancelable(false);
        if(productInfo.size()>0)
        {
            String date1 = productInfo.get(0).getOrderDate();
            /*Feb 25, 2019 7:54:08 PM*/
            SimpleDateFormat sdfIn = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
            SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
            Date date = null;
            try {
                date = sdfIn.parse(date1);
            } catch (ParseException e) {
                e.printStackTrace();
            }

           // Toast.makeText(context, "list size" +productInfo.size(), Toast.LENGTH_SHORT).show();
            tv_alertText.setText("You had already ordered the " + productInfo.get(0).getProductDesc()+" from dealer "+productInfo.get(0).getDealerName()+ " with quantity "+ placeOrderDO.getQuantity()+" on "+sdfOut.format(date)+". Do you want to place the order again?");
        }
        else {
            //tv_alertText.setText("Nothing");
            //Toast.makeText(searchContext, "Not Working", Toast.LENGTH_SHORT).show();
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        button_yes.setOnClickListener(v -> {
            CartDatabase.addProductToCart(placeOrderDO);
            listener.onEvent(true);
            Toast.makeText(context, "Product added to the cart", Toast.LENGTH_SHORT).show();
            holder.tv_add_to_cart.setEnabled(true);
            listener.stopLoader(true);
            alertDialog.dismiss();
        });

        button_no.setOnClickListener(v -> {
            holder.tv_add_to_cart.setEnabled(true);
            listener.stopLoader(true);
            alertDialog.dismiss();
        });

        //finally creating the alert dialog and displaying it

    }
  /*  private void serviceCallforAllDealers(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetDealersDataInterface service = retrofit.create(GetDealersDataInterface.class);

        //Call<GetDealerMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetDealerPostParameters(retailerId));
        Call<GetDealerMainObject> call = service.callWithSession(Integer.parseInt(retailerId), accessToken, new GetDealerPostParameters(retailerId));
        call.enqueue(new Callback<GetDealerMainObject>() {
            @Override
            public void onResponse(Call<GetDealerMainObject> call, Response<GetDealerMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
               // hideloader();
                if(response.body()!=null){
                 //   Toast.makeText(context,response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    allDealersList = new ArrayList<>();
                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().size();
                        //  Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                        for (int i =0; i< size; i++)
                        {
                            allDealersList.add(response.body().getResult().get(i));
                        }

                        // setAdapterToList(dealersList);
                    }
                    else
                    {
                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<GetDealerMainObject> call, Throwable t) {
                //hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }
*/
}