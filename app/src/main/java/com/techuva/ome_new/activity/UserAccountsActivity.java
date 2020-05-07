package com.techuva.ome_new.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.techuva.ome_new.R;
import com.techuva.ome_new.adapters.AccountListRcvAdapter;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.object_models.AccountListResultObject;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.util.ArrayList;

public class UserAccountsActivity extends AppCompatActivity implements AccountListRcvAdapter.EventListener{

    Toolbar toolbar;
    TextView tv_btn_continue, tv_success_txt, tv_chooseAccounts, tv_chooseAccount2;
    LinearLayout ll_header;
    CardView cv_successMsg;
    RecyclerView rcv_user_accounts;
    Context mContext;
    int UserId;
    AccountListRcvAdapter adapter;
    Toast exitToast;
    Boolean doubleBackToExitPressedOnce = true;
    ArrayList<AccountListResultObject> accountList;
    ListView lv_data_list;
    String authorityKey ="";
    String grantType = "";
    int listCount =0;
    String userType ="";
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_accounts);
        init();
        Bundle bundle = getIntent().getExtras();
        // accountList = bundle.getParcelableArrayList(Constants.UserRoles);
        CartDatabase.init(mContext);
        listCount = CartDatabase.getUserRolesListCount();
        if(listCount>0){
            accountList = CartDatabase.getAllUserRoles();
            //Toast.makeText(mContext, "Hi Done"+listCount, Toast.LENGTH_SHORT).show();
            setDataAdapterforList(accountList);
        }
        exitToast = Toast.makeText(getApplicationContext(), "Press back again to exit app", Toast.LENGTH_SHORT);
        //serviceCall();
        setTypeface();
        onClickEvent();


    }

    private void setTypeface() {
        Typeface faceLight = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Light.ttf");
        Typeface faceMedium = Typeface.createFromAsset(getAssets(),
                "fonts/Roboto-Medium.ttf");
        tv_btn_continue.setTypeface(faceMedium);
        tv_success_txt.setTypeface(faceMedium);
        tv_chooseAccounts.setTypeface(faceLight);
        tv_chooseAccount2.setTypeface(faceLight);
    }

    private void init() {
        mContext = UserAccountsActivity.this;
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        accountList = new ArrayList<>();
        tv_btn_continue  = findViewById(R.id.tv_btn_continue);
        tv_chooseAccounts  = findViewById(R.id.tv_chooseAccounts);
        tv_success_txt  = findViewById(R.id.tv_success_txt);
        tv_chooseAccount2  = findViewById(R.id.tv_chooseAccount2);
        ll_header  = findViewById(R.id.ll_header);
        cv_successMsg  = findViewById(R.id.cv_successMsg);
        rcv_user_accounts  = findViewById(R.id.rcv_user_accounts);
        lv_data_list  = findViewById(R.id.lv_data_list);
        //UserId = Integer.parseInt(MApplication.getString(mContext, Constants.UserID));
        //authorityKey = Constants.AuthorizationKey;
        authorityKey = "Bearer "+ MApplication.getString(mContext, Constants.AccessToken);
        grantType = Constants.GrantType;
        userType = MApplication.getString(mContext, Constants.UserType);
      // Toast.makeText(mContext, "UserId"+ UserId, Toast.LENGTH_SHORT).show();
    }

    private void setDataAdapterforList(ArrayList<AccountListResultObject> accountList) {


        if(accountList.size()==1)
        {
            //Toast.makeText(mContext, "Single", Toast.LENGTH_SHORT).show();
            MApplication.setBoolean(mContext, Constants.SingleAccount, true);
        }
        else
        {
            MApplication.setBoolean(mContext, Constants.SingleAccount, false);
        }
        adapter = new AccountListRcvAdapter(mContext, accountList, UserAccountsActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcv_user_accounts.setLayoutManager(linearLayoutManager);
        rcv_user_accounts.setAdapter(adapter);

    }
    private void onClickEvent() {
        tv_btn_continue.setOnClickListener(view -> {
            //Get the selected position
             adapter.getSelectedItem();

            if(!adapter.getSelectedItem().equals(""))
            {
               /* int i =  accountList.get(Integer.parseInt(adapter.getSelectedItem())).getCompanyId();
                MApplication.setString(mContext, Constants.CompanyID, String.valueOf(i));
                MApplication.setBoolean(mContext, Constants.IsDefaultDeviceSaved, false);
                Intent intent = new Intent(mContext, UserDevicesListActivity.class);
                startActivity(intent);*/
            }
           else
               Toast.makeText(mContext, "Please select account to proceed", Toast.LENGTH_SHORT).show();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        if(userType.equals(Constants.UserType))
        {
            if(doubleBackToExitPressedOnce){
                // Do what ever you want
                exitToast.show();
                doubleBackToExitPressedOnce = false;
            } else{
                finishAffinity();
                finish();
                // Do exit app or back press here
                super.onBackPressed();
            }
        }

        else {
            finish();
           // loadFragments(Dashboard.newInstance(), "Home");
        }

    }


    @Override
    public void onEvent(Boolean data) {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDelete(Boolean data) {

    }
    public  void loadFragments(Fragment fragment, String tag)
    {
        fragmentManager =getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragment_container,fragment, tag).commit();
    }


}
