package com.techuva.ome_new.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.techuva.ome_new.R;
import com.techuva.ome_new.api_interface.CompanyFavouriteDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.CompanyListObjectModel;
import com.techuva.ome_new.post_parameters.CompanyFavouritePostParameters;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class GridFavCompanyAdapter extends ArrayAdapter<CompanyListObjectModel> {
    private final int resource;
    private Context mContext;
    ArrayList<CompanyListObjectModel> list;
    String companyId = "";
    boolean status;
    String accessToken="";
    Context context;
    private EventListener listener;
    String userId;

    public GridFavCompanyAdapter(@NonNull Context context, int resource, @NonNull ArrayList<CompanyListObjectModel> objects, EventListener listener) {
        super(context, resource, objects);
        this.resource = resource;
        this.mContext = context;
        this.list = objects;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CompanyListObjectModel getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @RequiresApi(api = Build.VERSION_CODES.M)
    public View getView(int position, View convertView, ViewGroup parent) {

        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();
        View row = convertView;
        RecordHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new RecordHolder();
            holder.cb_fav_btn = row.findViewById(R.id.cb_fav_btn);
            holder.tv_company_name = row.findViewById(R.id.tv_company_name);
            holder.tv_company_address = row.findViewById(R.id.tv_company_address);
            holder.tv_company_city_name = row.findViewById(R.id.tv_company_city_name);
            holder.tv_company_state_name = row.findViewById(R.id.tv_company_state_name);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }
        context = getContext();
        CompanyListObjectModel item = list.get(position);
        companyId = String.valueOf(item.getCompany_id());
        userId = MApplication.getString(context, Constants.UserID);
        status = item.getFavourite();
        Typeface faceLight = Typeface.createFromAsset(getContext().getAssets(),
                "fonts/Roboto-Regular.ttf");
        holder.tv_company_name.setText(String.valueOf(item.getCompany_name()));
        holder.tv_company_address.setText(String.valueOf(item.getCompany_addr()));
        holder.tv_company_city_name.setText(String.valueOf(item.getCity_name()));
        holder.tv_company_state_name.setText(String.valueOf(item.getState_name()));
        holder.tv_company_name.setTypeface(faceLight);
        holder.tv_company_address.setTypeface(faceLight);
        holder.tv_company_city_name.setTypeface(faceLight);
        holder.tv_company_state_name.setTypeface(faceLight);
        if(item.getFavourite())
        {
            holder.cb_fav_btn.setChecked(true);
        }
        else holder.cb_fav_btn.setChecked(false);
        holder.cb_fav_btn.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(item.getFavourite())
            {
                companyId = String.valueOf(item.getCompany_id());
                status = false;
                serviceCall();
            }
            else {
                companyId = String.valueOf(item.getCompany_id());
                status = true;
                serviceCall();
            }
        });
        return row;
    }
    static class RecordHolder {
        CheckBox cb_fav_btn;
        TextView tv_company_name;
        TextView tv_company_address;
        TextView tv_company_city_name;
        TextView tv_company_state_name;

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
                        listener.onRefresh(true);
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


    public interface EventListener {
        void onRefresh(Boolean data);

    }


}
