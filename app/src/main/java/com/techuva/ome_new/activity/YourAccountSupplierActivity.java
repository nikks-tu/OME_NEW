package com.techuva.ome_new.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.CityListAdapter;
import com.techuva.ome_new.adapters.StateListAdapter;
import com.techuva.ome_new.api_interface.CityListDataInterface;
import com.techuva.ome_new.api_interface.EditProfileDataInterface;
import com.techuva.ome_new.api_interface.MyProfileDataInterface;
import com.techuva.ome_new.api_interface.StateListDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.CityListMainObject;
import com.techuva.ome_new.object_models.CityNamesObject;
import com.techuva.ome_new.object_models.EditProfileMainObject;
import com.techuva.ome_new.object_models.MyProfileMainObject;
import com.techuva.ome_new.object_models.StateListMainObject;
import com.techuva.ome_new.object_models.StateListResultObject;
import com.techuva.ome_new.post_parameters.EditProfilePostParameters;
import com.techuva.ome_new.post_parameters.GetCityPostParameters;
import com.techuva.ome_new.post_parameters.GetStatesPostParameters;
import com.techuva.ome_new.post_parameters.MyProfilePostParameters;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class YourAccountSupplierActivity extends Fragment {
   public Dialog dialog;
    private AnimationDrawable animationDrawable;
    TextView tv_user_name, tv_user_type, tv_editProfile, tv_email, tv_mobile, tv_gst, tv_pan, tv_address, tv_update_profile;
    TextView tv_email_txt, tv_mb_txt, tv_gst_txt, tv_pan_txt, tv_add_txt;
    TextView tv_name, tv_email_edt_txt, tv_mb_edt_txt, tv_gst_txt_edt, tv_pan_edt, tv_state_edt, tv_city_edt;
    TextView tv_add_edt, tv_add_edt2, tv_pincode;
    String retailerId ="";
    LinearLayout ll_view_profile, ll_edit_profile;
    EditText edt_first_name, edt_middle_name, edt_last_name, edt_address1, edt_address2;
    EditText edt_email, edt_mobile, edt_gst, edt_pan_no, edt_pincode;
    Spinner spin_state, spin_city;
    Context context;
    String userId;
    String userName;
    String firstName;
    String middleName;
    String lastName;
    String emailId;
    String mobileNo;
    String password;
    String userTypeId;
    String isActive="true";
    String createdBy= "";
    String userType="";
    String userCode="";
    String address1;
    String address2;
    int cityId;
    int stateId;
    int countryId;
    int pinId;
    int areaCodeId;
    String panNumber;
    String gstNumber;
    String pageNumber = "1";
    String pagePerCount = "ALL";
    String searchKey = "";
    ArrayList<StateListResultObject> stateList;
    ArrayList<CityNamesObject> cityList;
    StateListAdapter stateListAdapter;
    CityListAdapter cityListAdapter;
    String accessToke="";
    Activity mActivity;
    String stateName="";
    String cityName="";
    Toolbar toolbar;

    public static YourAccountSupplierActivity newInstance() {

        Bundle args = new Bundle();

        YourAccountSupplierActivity fragment = new YourAccountSupplierActivity();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.activity_your_account, null, false);

        inititialize(contentView);
        serviceCallforMyProfile();

        tv_editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_editProfile.setVisibility(View.GONE);
                ll_view_profile.setVisibility(View.GONE);
                ll_edit_profile.setVisibility(View.VISIBLE);
            }
        });
        tv_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInputForEdit();
            }
        });

        spin_state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "HI " + stateListAdapter.getItem(position).getStateId(), Toast.LENGTH_SHORT).show();
                stateId = stateListAdapter.getItem(position).getStateId();
                serviceCallforGettingCityList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spin_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(context, "Hi" + stateListAdapter.getItem(position).getStateId(), Toast.LENGTH_SHORT).show();
                cityId = cityListAdapter.getItem(position).getCityId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return contentView;
    }



    private void getInputForEdit() {

        userId= MApplication.getString(getContext(), Constants.UserID);
        userName =MApplication.getString(getContext(), Constants.UserName);
        firstName = edt_first_name.getText().toString();
        middleName = edt_middle_name.getText().toString();
        lastName = edt_last_name.getText().toString();
        emailId = edt_email.getText().toString();
        mobileNo = edt_mobile.getText().toString();
        password= MApplication.getString(getContext(), Constants.Password);
        createdBy = MApplication.getString(getContext(), Constants.UserID);
        userType = MApplication.getString(getContext(), Constants.UserType);
        address1 = edt_address1.getText().toString();
        address2 = edt_address2.getText().toString();
        /*panNumber = edt_pan_no.getText().toString();
        gstNumber = edt_gst.getText().toString();*/
        try{
            pinId = Integer.parseInt(edt_pincode.getText().toString());
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
        }
      /*  String pincode = edt_pincode.getText().toString();
        if(!TextUtils.isEmpty(pincode))
        {
            pinId = Integer.parseInt(pincode) ;
        }*/

        if (firstName.length() > 0) {
            if (address1.length() > 0) {
                if (lastName.length() > 0) {
                    if (String.valueOf(pinId).length()==6 && pinId>0)
                    {
                        showLoaderNew();
                        serviceCallforEditProfile();
                    }
                    else Toast.makeText(context, "Please enter valid pincode", Toast.LENGTH_SHORT).show();

                }
                else Toast.makeText(context, "Please enter Last name", Toast.LENGTH_SHORT).show();

            }
            else Toast.makeText(context, "Please enter address", Toast.LENGTH_SHORT ).show();
        }
        else Toast.makeText(context, "Please enter First name", Toast.LENGTH_SHORT ).show();




    }

    private void inititialize(View view) {
        context = getActivity();
        toolbar =  getActivity().findViewById(R.id.toolbar);
        tv_user_name = view.findViewById(R.id.tv_user_name);
        tv_user_type = view.findViewById(R.id.tv_user_type);
        tv_editProfile = view.findViewById(R.id.tv_editProfile);
        tv_email = view.findViewById(R.id.tv_email);
        tv_mobile = view.findViewById(R.id.tv_mobile);
        tv_gst = view.findViewById(R.id.tv_gst);
        tv_pan = view.findViewById(R.id.tv_pan);
        tv_address = view.findViewById(R.id.tv_address);
        ll_view_profile = view.findViewById(R.id.ll_view_profile);
        ll_edit_profile = view.findViewById(R.id.ll_edit_profile);
        edt_first_name = view.findViewById(R.id.edt_first_name);
        edt_middle_name = view.findViewById(R.id.edt_middle_name);
        edt_last_name = view.findViewById(R.id.edt_last_name);
        edt_address1 = view.findViewById(R.id.edt_address1);
        edt_address2 = view.findViewById(R.id.edt_address2);
        edt_email = view.findViewById(R.id.edt_email);
        edt_mobile = view.findViewById(R.id.edt_mobile);
        edt_gst = view.findViewById(R.id.edt_gst);
        edt_pan_no = view.findViewById(R.id.edt_pan_no);
        edt_pincode = view.findViewById(R.id.edt_pincode);
        spin_state = view.findViewById(R.id.spin_state);
        spin_city = view.findViewById(R.id.spin_city);
        tv_update_profile = view.findViewById(R.id.tv_update_profile);
        tv_email_txt = view.findViewById(R.id.tv_email_txt);
        tv_mb_txt = view.findViewById(R.id.tv_mb_txt);
        tv_gst_txt = view.findViewById(R.id.tv_gst_txt);
        tv_pan_txt = view.findViewById(R.id.tv_pan_txt);
        tv_add_txt = view.findViewById(R.id.tv_add_txt);
        tv_name = view.findViewById(R.id.tv_name);
        tv_email_edt_txt = view.findViewById(R.id.tv_email_edt_txt);
        tv_mb_edt_txt = view.findViewById(R.id.tv_mb_edt_txt);
        tv_gst_txt_edt = view.findViewById(R.id.tv_gst_txt_edt);
        tv_pan_edt = view.findViewById(R.id.tv_pan_edt);
        tv_state_edt = view.findViewById(R.id.tv_state_edt);
        tv_city_edt = view.findViewById(R.id.tv_city_edt);
        tv_add_edt = view.findViewById(R.id.tv_add_edt);
        tv_add_edt2 = view.findViewById(R.id.tv_add_edt2);
        tv_pincode = view.findViewById(R.id.tv_pincode);
        Typeface faceLight = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        Typeface faceRegular = Typeface.createFromAsset(getResources().getAssets(),
                "fonts/Roboto-Regular.ttf");
        tv_user_name.setTypeface(faceRegular);
        tv_user_type.setTypeface(faceLight);
        tv_editProfile.setTypeface(faceRegular);
        tv_email.setTypeface(faceLight);
        tv_mobile.setTypeface(faceLight);
        tv_gst.setTypeface(faceLight);
        tv_pan.setTypeface(faceLight);
        tv_address.setTypeface(faceLight);
        tv_update_profile.setTypeface(faceRegular);
        tv_email_txt.setTypeface(faceRegular);
        tv_mb_txt.setTypeface(faceRegular);
        tv_gst_txt.setTypeface(faceRegular);
        tv_pan_txt.setTypeface(faceRegular);
        tv_add_txt.setTypeface(faceRegular);
        tv_name.setTypeface(faceRegular);
        tv_email_edt_txt.setTypeface(faceRegular);
        tv_mb_edt_txt.setTypeface(faceRegular);
        tv_gst_txt_edt.setTypeface(faceRegular);
        tv_pan_edt.setTypeface(faceRegular);
        tv_state_edt.setTypeface(faceRegular);
        tv_city_edt.setTypeface(faceRegular);
        tv_add_edt.setTypeface(faceRegular);
        tv_add_edt2.setTypeface(faceRegular);
        tv_pincode.setTypeface(faceRegular);
        edt_first_name.setTypeface(faceLight);
        edt_middle_name.setTypeface(faceLight);
        edt_last_name.setTypeface(faceLight);
        edt_address1.setTypeface(faceLight);
        edt_address2.setTypeface(faceLight);
        edt_email.setTypeface(faceLight);
        edt_mobile.setTypeface(faceLight);
        edt_gst.setTypeface(faceLight);
        edt_pan_no.setTypeface(faceLight);
        edt_pincode.setTypeface(faceLight);
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToke = sb.toString();
        retailerId = MApplication.getString(context, Constants.RetailerID);
        userId= MApplication.getString(getContext(), Constants.UserID);
        ll_view_profile.setVisibility(View.VISIBLE);
    }

    private void serviceCallforMyProfile(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyProfileDataInterface service = retrofit.create(MyProfileDataInterface.class);

        //Call<MyProfileMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new MyProfilePostParameters( retailerId, "", ""));
        Call<MyProfileMainObject> call = service.getStringScalar(Integer.parseInt(userId), accessToke, new MyProfilePostParameters( userId, "", ""));
        call.enqueue(new Callback<MyProfileMainObject>() {
            @Override
            public void onResponse(Call<MyProfileMainObject> call, Response<MyProfileMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
               if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo().getErrorCode()==0)
                    {

                        serviceCallforGettingStateList();

                        for (int i=0; i<response.body().getResult().size();i++){
                            String retailerName;
                            StringBuilder sb = new StringBuilder();
                            sb.append(response.body().getResult().get(i).getFirstName());
                            if(response.body().getResult().get(i).getMiddleName()!= null && !response.body().getResult().get(i).getMiddleName().isEmpty())
                            {
                                sb.append(" ");
                                sb.append(response.body().getResult().get(i).getMiddleName());
                                edt_middle_name.setText(response.body().getResult().get(i).getMiddleName());
                            }
                            else {
                                sb.append(" ");
                                edt_middle_name.setText("");
                            }
                            if(!response.body().getResult().get(i).getLastName().isEmpty())
                            {
                                sb.append(" ");
                                sb.append(response.body().getResult().get(i).getLastName());
                            }
                            retailerName = sb.toString();
                            tv_user_name.setText(retailerName);
                            tv_user_type.setText(response.body().getResult().get(i).getUserType());
                            //tv_editProfile.setText(response.body().getResult().get(i).getUserName());
                            tv_email.setText(response.body().getResult().get(i).getUserName());
                            tv_mobile.setText(response.body().getResult().get(i).getMobileNo());
                            tv_gst.setText(response.body().getResult().get(i).getGstNo());
                            tv_pan.setText(response.body().getResult().get(i).getPan());
                            edt_first_name.setText(response.body().getResult().get(i).getFirstName());

                            edt_last_name.setText(response.body().getResult().get(i).getLastName());
                            edt_email.setText(response.body().getResult().get(i).getUserName());
                            edt_mobile.setText(response.body().getResult().get(i).getMobileNo());
                           /* edt_gst.setText(response.body().getResult().get(i).getGstNo());
                            edt_pan_no.setText(response.body().getResult().get(i).getPan());
                           */ edt_address1.setText(response.body().getResult().get(i).getAddress1());
                            edt_address2.setText(response.body().getResult().get(i).getAddress2());
                            String address = response.body().getResult().get(i).getAddress2();
                            StringBuilder sb1  =  new StringBuilder();
                            sb1.append(response.body().getResult().get(i).getAddress1());
                            sb1.append(", ");
                            if(address!=null && !address.equals(""))
                            {
                                sb1.append(address);
                                sb1.append(", ");
                            }
                            sb1.append(response.body().getResult().get(i).getCityName());
                            sb1.append(", ");
                            sb1.append(response.body().getResult().get(i).getStateName());
                            sb1.append(", ");
                            sb1.append(response.body().getResult().get(i).getPin());
                            edt_pincode.setText(String.valueOf(response.body().getResult().get(i).getPin()));
                            edt_email.setText(response.body().getResult().get(i).getUserName());
                            tv_address.setText(sb1.toString());
                            userCode = response.body().getResult().get(i).getUserCode();
                            userTypeId = String.valueOf(response.body().getResult().get(i).getUserTypeId());
                            countryId = response.body().getResult().get(i).getCountryId();
                            isActive = String.valueOf(response.body().getResult().get(i).getIsActive());
                            areaCodeId = response.body().getResult().get(i).getArea_code_id();
                            stateName = response.body().getResult().get(i).getStateName();
                            cityName = response.body().getResult().get(i).getCityName();
                        }
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
            public void onFailure(Call<MyProfileMainObject> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }


    private void serviceCallforEditProfile(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EditProfileDataInterface service = retrofit.create(EditProfileDataInterface.class);

       /* Call<EditProfileMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new EditProfilePostParameters( userId, userName, firstName, middleName,
                lastName, emailId, mobileNo, password, userTypeId, isActive, createdBy, userType, userCode,
                address1, address2,  cityId, stateId, countryId, pinId, panNumber, gstNumber));*/
         Call<EditProfileMainObject> call = service.getStringScalar(Integer.parseInt(userId), accessToke,new EditProfilePostParameters(userId, firstName, middleName,
                lastName,address1, address2,  cityId, stateId, countryId, pinId));
        call.enqueue(new Callback<EditProfileMainObject>() {
            @Override
            public void onResponse(Call<EditProfileMainObject> call, Response<EditProfileMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                hideloader();
                if(response.body()!=null){
                     Toast.makeText(context,response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        Toast.makeText(context, response.body().getInfo().getErrorMessage(), Toast.LENGTH_SHORT).show();
                        ll_edit_profile.setVisibility(View.GONE);
                        ll_view_profile.setVisibility(View.VISIBLE);
                        tv_editProfile.setVisibility(View.VISIBLE);
                        serviceCallforMyProfile();
                    }
                    else
                    {
                        hideloader();
                        //Toast.makeText(getBaseContext(), "Data not found",Toast.LENGTH_SHORT).show();
                    }


                }else {
                    hideloader();
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<EditProfileMainObject> call, Throwable t) {
                hideloader();
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }


    private void serviceCallforGettingCityList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL_New)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CityListDataInterface service = retrofit.create(CityListDataInterface.class);

       // Call<CityListMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetCityPostParameters(String.valueOf(stateId)));
        Call<CityListMainObject> call = service.getStringScalar(Integer.parseInt(userId), accessToke, new GetCityPostParameters(String.valueOf(stateId)));
        call.enqueue(new Callback<CityListMainObject>() {
            @Override
            public void onResponse(Call<CityListMainObject> call, Response<CityListMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();
                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().getValues().size();
                        cityList = new ArrayList<>();
                        //  Toast.makeText(getBaseContext(), "Data came" +size, Toast.LENGTH_SHORT).show();
                        for (int i =0; i< size; i++)
                        {
                            cityList.add(response.body().getResult().getValues().get(i));
                        }

                        if(getActivity()!=null && cityList.size()>0)
                        {
                            cityListAdapter = new CityListAdapter(getActivity(), R.layout.item_state_list, R.id.tv_state_name, cityList);
                            spin_city.setAdapter(cityListAdapter);
                            if(!cityName.equals(""))
                            {
                                for(int k=0; k<cityList.size(); k++)
                                {
                                    if(cityList.get(k).getCityName().equals(cityName))
                                    {
                                        cityList.get(k).getCityId();
                                        spin_city.setSelection(k);
                                    }
                                }
                            }
                        }
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
            public void onFailure(Call<CityListMainObject> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });
    }


    private void serviceCallforGettingStateList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        StateListDataInterface service = retrofit.create(StateListDataInterface.class);

        //Call<StateListMainObject> call = service.getStringScalar(Integer.parseInt(retailerId), new GetStatesPostParameters(searchKey, pagePerCount, pageNumber));
        Call<StateListMainObject> call = service.getStringScalar(Integer.parseInt(userId),accessToke, new GetStatesPostParameters(searchKey, pagePerCount, pageNumber));
        call.enqueue(new Callback<StateListMainObject>() {
            @Override
            public void onResponse(Call<StateListMainObject> call, Response<StateListMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                if(response.body()!=null){
                    // Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if(response.body().getInfo().getErrorCode()==0)
                    {
                        int size = response.body().getResult().size();
                        stateList = new ArrayList<>();
                         for (int i =0; i< size; i++)
                        {
                            stateList.add(response.body().getResult().get(i));
                        }

                        if(getActivity()!=null)
                        {
                            stateListAdapter = new StateListAdapter(getActivity(), R.layout.item_state_list, R.id.tv_state_name, stateList);
                            spin_state.setAdapter(stateListAdapter);

                            if(!stateName.equals(""))
                            {
                                for(int j=0; j<stateList.size(); j++)
                                {
                                    if(stateList.get(j).getStateName().equals(stateName))
                                    {
                                        stateList.get(j).getStateId();
                                        spin_state.setSelection(j);
                                    }
                                }
                            }
                        }
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
            public void onFailure(Call<StateListMainObject> call, Throwable t) {
                Toast.makeText(context, "Error connecting server" , Toast.LENGTH_SHORT).show();
                //fl_main.setVisibility(View.GONE);
                //rl_serverError.setVisibility(View.VISIBLE);
            }

        });


    }



    public void showLoaderNew() {
        mActivity.runOnUiThread(new Runloader(getResources().getString(R.string.loading)));
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
                    dialog = new Dialog(context, R.style.Theme_AppCompat_Light_DarkActionBar);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.getWindow().setBackgroundDrawable(
                            new ColorDrawable(android.graphics.Color.TRANSPARENT));
                }
                dialog.setContentView(R.layout.loading);
                dialog.setCancelable(false);

                if (dialog != null && dialog.isShowing())
                {
                    dialog.dismiss();
                    dialog=null;
                }
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
                imgeView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (animationDrawable != null)
                            animationDrawable.start();
                    }
                });
            } catch (Exception e)
            {

            }
        }
    }

    public void hideloader() {
        mActivity.runOnUiThread(() -> {
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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
    }


   }
