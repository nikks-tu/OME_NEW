package com.example.ordermadeeasy.adapters;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.ordermadeeasy.MainActivity;
import com.example.ordermadeeasy.R;
import com.example.ordermadeeasy.activity.SearchProductActivity;
import com.example.ordermadeeasy.api_interface.CompanyFavouriteDataInterface;
import com.example.ordermadeeasy.app.Constants;
import com.example.ordermadeeasy.domain.PlaceOrderDO;
import com.example.ordermadeeasy.holders.SearchCompanyRCVHolder;
import com.example.ordermadeeasy.object_models.CompanyListObjectModel;
import com.example.ordermadeeasy.post_parameters.CompanyFavouritePostParameters;
import com.example.ordermadeeasy.utilities.CartDatabase;
import com.example.ordermadeeasy.views.MApplication;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class SearchCompanyAdapter extends RecyclerView.Adapter<SearchCompanyRCVHolder> {// Recyclerview will extend to

    private ArrayList<CompanyListObjectModel> arrayList;
    private AppCompatActivity activity;
    private SearchCompanyRCVHolder listHolder;
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
    String companyId = "";
    boolean status;
    Boolean productStatus =false;
    CartDatabase cartDatabase;
    String accessToken="";
    Context context;
    String userId;
    final Handler handler = new Handler();
    int tempCartSize;
    public SearchCompanyAdapter(Context context, AppCompatActivity activity, ArrayList<CompanyListObjectModel> arrayList, EventListener listener) {
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
    public void onBindViewHolder(SearchCompanyRCVHolder holder, int position) {


        CompanyListObjectModel model = arrayList.get(position);
        companyId = String.valueOf(model.getCompany_id());
        placeOrderDO = new PlaceOrderDO();
        userId = MApplication.getString(context, Constants.UserID);
        status = model.getFavourite();
        holder.tv_company_name.setText(model.getCompany_name());
        holder.tv_state_desc.setText(model.getState_name()+", "+ model.getCity_name());
        holder.tv_address_name.setText(model.getCompany_addr());
        holder.tv_view_product.setEnabled(true);
        if(arrayList.get(position).getFavourite())
        {
            holder.rb_favourite.setChecked(true);
        }
        else {
            holder.rb_favourite.setChecked(false);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
       /* if(!model.getImageUrl().equals(""))
        {
            Picasso.with(context).load(model.getImageUrl()).into(holder.iv_product_image);
        }
        else*/
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        //holder.rb_favourite.setTag(position);
        //holder.rb_favourite.setChecked(position == selectedPosition);
        placeOrderDO.status_id = 1;

        holder.tv_view_product.setVisibility(View.GONE);
        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setEnabled(false);
            tempCartSize = Integer.parseInt(MApplication.getString(context, Constants.CartSize));
            if(tempCartSize>0)
            {
                int tempCompanyId = Integer.parseInt(MApplication.getString(context, Constants.CompanyID));
                if(tempCompanyId== model.getCompany_id())
                {
                    MApplication.setString(context, Constants.CompanyID, String.valueOf(model.getCompany_id()));
                    MApplication.setString(context, Constants.SelectedCompany, model.getCompany_name());
                    loadFragments(SearchProductActivity.newInstance(""));
                }
                else {
                    Toast.makeText(context, "You cannot choose different company! Please clear cart.", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                MApplication.setString(context, Constants.CompanyID, String.valueOf(model.getCompany_id()));
                MApplication.setString(context, Constants.SelectedCompany, model.getCompany_name());
                loadFragments(SearchProductActivity.newInstance(""));
            }

        });

        holder.rb_favourite.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(model.getFavourite())
            {
                companyId = String.valueOf(model.getCompany_id());
                status = false;
                serviceCall();
            }
            else {
                companyId = String.valueOf(model.getCompany_id());
                status = true;
                serviceCall();
            }
        });
    }


    public  void loadFragments(Fragment fragment)
    {
        ((MainActivity)activity).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment)
                .commit();
        FragmentTransaction ft =  ((MainActivity)activity).getSupportFragmentManager().beginTransaction();
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
       /* Bundle bundle = new Bundle();
        bundle.putSerializable("product", model);*/
        ft.replace(R.id.fragment_container, fragment);
        ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public SearchCompanyRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as view holder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_company_search, viewGroup, false);
        listHolder = new SearchCompanyRCVHolder(mainGroup);
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


    private void serviceCall() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                //.baseUrl("http://182.18.177.27/TUUserManagement/api/user/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CompanyFavouriteDataInterface service = retrofit.create(CompanyFavouriteDataInterface.class);

        Call<JsonElement> call = service.getStringScalar(Integer.parseInt(userId), accessToken, new CompanyFavouritePostParameters(userId, companyId, status));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    JsonObject jsonObject = response.body().getAsJsonObject();
                    JsonObject infoObject = jsonObject.get("info").getAsJsonObject();
                    int errorCode = infoObject.get("ErrorCode").getAsInt();
                    String errorMsg = infoObject.get("ErrorMessage").getAsString();
                    if(errorCode==0)
                    {
                        //Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show();
                    }
                }else {

                }

            }
            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                //Toast.makeText(context, "Unable to login!", Toast.LENGTH_SHORT).show();
                Toast.makeText(context, R.string.error_connecting_error, Toast.LENGTH_SHORT).show();
            }
        });

    }



}