package com.techuva.ome_new.adapters;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;
import com.techuva.ome_new.R;
import com.techuva.ome_new.api_interface.UpdateOrderDataInterface;
import com.techuva.ome_new.app.Constants;
import com.techuva.ome_new.domain.PlaceOrderDO;
import com.techuva.ome_new.holders.AllOrderRCVHolder;
import com.techuva.ome_new.object_models.AllOrdersResultObject;
import com.techuva.ome_new.object_models.OrderStatusResultObject;
import com.techuva.ome_new.object_models.UpdateOrderMainObject;
import com.techuva.ome_new.post_parameters.UpdateOrderPostParameters;
import com.techuva.ome_new.utilities.CartDatabase;
import com.techuva.ome_new.views.MApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class AllOrdersAdapter extends RecyclerView.Adapter<AllOrderRCVHolder>
{   // Recyclerview will extend to
    // recyclerview adapter
    private ArrayList<AllOrdersResultObject> arrayList;
    private Context context;
    private AllOrderRCVHolder listHolder;
    private int selectedPosition = -1;
    private OnItemClicked listener;
    private PlaceOrderDO placeOrderDO;
    String dealersList ="";
    StringBuilder sb;
    int dealerId = 0;
    String userId = "";
    int qty = 05;
    int totalDealers= 0;
    String dealerName="";
    CartDatabase cartDatabase;
    OrderStatusAdapter orderStatusAdapter;
    ArrayList<OrderStatusResultObject> statusList;
    ArrayList<OrderStatusResultObject> statusListFilter;
    AppCompatActivity activity;
    String demandOrderId, statusId, receivedQuantity;
    String accessToken ="";
    int pos;
    private EventListener eventListener;
    String userType;

    public AllOrdersAdapter(AppCompatActivity activity, Context context, ArrayList<OrderStatusResultObject> statusList, ArrayList<AllOrdersResultObject> arrayList, OnItemClicked listener, EventListener eventListener) {
       this.activity = activity;
        this.statusList = statusList;
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        this.eventListener = eventListener;
    }

    @Override
    public int getItemCount() {
        // return (null != arrayList ? arrayList.size() : 0);
        return arrayList.size();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(AllOrderRCVHolder holder, int position) {
        userId = MApplication.getString(context, Constants.UserID);
        userType = MApplication.getString(context, Constants.UserType);
            //holder.setIsRecyclable(false);
            placeOrderDO = new PlaceOrderDO();
            AllOrdersResultObject model = arrayList.get(position);
            holder.tv_product_name.setText( model.getCustomerName());
            holder.tv_product_desc.setText(model.getDisplay_name());
            holder.tv_dealers_name.setText(model.getDealerName());
            holder.txt_order_status.setVisibility(View.VISIBLE);
            holder.txt_order_status.setText(model.getStatusName());
            holder.tv_product_qty.setText(context.getResources().getString(R.string.qty)+" "+ model.getQuantity());

            demandOrderId = String.valueOf(model.getDemandOrderId());
            //statusId = String.valueOf(holder.spin_order_status.getSelectedItemId());
            receivedQuantity="";
        if(!model.getImage_url().equals("") && model.getImage_url()!=null)
        {
            Picasso.with(context).load(model.getImage_url()).into(holder.iv_product_image);
        }
        else
            Picasso.with(context).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        StringBuilder sb = new StringBuilder();
        sb.append("Bearer ");
        sb.append( MApplication.getString(context, Constants.AccessToken));
        accessToken = sb.toString();

            String date1 = model.getReceived_on();
        SimpleDateFormat sdfIn = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date date = null;
        try {
            date = sdfIn.parse(date1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        statusListFilter = new ArrayList<>();
        holder.tv_order_date.setText(sdfOut.format(date));

        //Picasso.with(activity).load("https://homepages.cae.wisc.edu/~ece533/images/airplane.png").into(holder.iv_product_image);
        holder.txt_order_status.setTextColor(context.getResources().getColor(R.color.white));
        holder.txt_order_status.setTag(model.getStatusId());

        setColoreToView(holder.txt_order_status,model.getStatusId());

        holder.txt_order_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos= holder.getAdapterPosition();
                statusListFilter = new ArrayList<>();
                if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("1"))
                {
                    for (int i=0; i<statusList.size(); i++)
                    {
                        if(statusList.get(i).getStatusId()==5)
                        {
                            statusListFilter.add(statusList.get(i));
                        }
                    }
                }
               // if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("6")||holder.txt_order_status.getTag().toString().equalsIgnoreCase("3"))
                if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("3"))
                {
                    for (int i=0; i<statusList.size(); i++)
                    {
                        if(statusList.get(i).getStatusId()== 6)
                        {
                            //partially dispatched will have partially received.

                            statusListFilter.add(statusList.get(i));
                        }
                    }
                }
                if(holder.txt_order_status.getTag().toString().equalsIgnoreCase("4"))
                {
                    for (int i=0; i<statusList.size(); i++)
                    {
                        if(statusList.get(i).getStatusId()==2)
                        {
                            statusListFilter.add(statusList.get(i));
                        }
                    }
                }
                if(statusListFilter.size()>0)
                {
                    showPopupMenu(holder.txt_order_status, statusListFilter,position);
                    if(model.getStatusId()==5)
                    {
                        holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    if(model.getStatusId()==6)
                    {
                        holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                    if(model.getStatusId()==2)
                    {
                        holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    }
                }

            }
        });

        if(model.getStatusId()==5)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(model.getStatusId()==6)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        if(model.getStatusId()==2)
        {
            holder.txt_order_status.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        }
        dealersList = new String();
    }

    @SuppressLint("NewApi")
    public void setColoreToView (View view, int id)
    {
        if(id== 1)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.pending_backgound));
        }
        else if(id==2)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.received_backgound));
        }
        else if(id==3)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_disp_backgound));
        }
        else if(id==4)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.dispatched_backgound));
        }
        else if(id==5)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.canceled_backgound));
        }
        else if(id==6)
        {
            view.setBackground(context.getResources().getDrawable(R.drawable.part_rece_backgound));
        }
    }

    @Override
    public AllOrderRCVHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // This method will inflate the custom layout and return as viewholder
        LayoutInflater mInflater = LayoutInflater.from(viewGroup.getContext());
        ViewGroup mainGroup = (ViewGroup) mInflater.inflate(R.layout.item_order_list, viewGroup, false);
        listHolder = new AllOrderRCVHolder(mainGroup);
        return listHolder;

    }


    private void showPopupMenu(final View view1, ArrayList<OrderStatusResultObject> list,int position)
    {
        final TextView b1 = (TextView) view1;
        PopupMenu menu = new PopupMenu(activity, view1);
        for(int y=0;y<list.size();y++)
        {
            menu.getMenu().add(list.get(y).getStatusDescription());
        }
        menu.show();
        menu.setOnMenuItemClickListener(item -> {

            if(b1.getText().toString().equalsIgnoreCase(item.getTitle().toString()))
            {
               Toast.makeText(activity,"You have selected the same status.",Toast.LENGTH_SHORT).show();
            }
            else
            {
                b1.setText(item.getTitle());
                int id=0;
                for (int i=0; i<statusList.size(); i++)
                {
                    if(b1.getText().toString().equalsIgnoreCase(statusList.get(i).getStatusDescription()) )
                    {
                        b1.setTag(statusList.get(i).getStatusId());
                        id=statusList.get(i).getStatusId();
                    }
                }
                arrayList.get(position).setStatusId(id);
                String orderId = String.valueOf(arrayList.get(position).getDemandOrderId());
                setColoreToView(b1,id);
                serviceCallforAllUpdatingStatus(orderId, userType);
               // eventListener.onSuccess(true);
            }
            return false;
        });
    }


    public interface OnItemClicked {
        void onItemClick(View view, int position);
    }

    public interface EventListener{
        void onSuccess(boolean data);
    }

    private void serviceCallforAllUpdatingStatus(String demandOrderId, String role) {

        ProgressDialog dialog = new ProgressDialog(activity);
        dialog.setCancelable(false);
        dialog.setMessage("Please wait..");
        dialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.LIVE_BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UpdateOrderDataInterface service = retrofit.create(UpdateOrderDataInterface.class);

        int demanid = arrayList.get(pos).getDemandOrderId();
        Call<UpdateOrderMainObject> call = service.getStringScalar(Integer.parseInt(userId),accessToken,  new UpdateOrderPostParameters(demandOrderId, role));
        call.enqueue(new Callback<UpdateOrderMainObject>() {
            @Override
            public void onResponse(Call<UpdateOrderMainObject> call, Response<UpdateOrderMainObject> response) {
                //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                //hideloader();

                dialog.dismiss();
                if (response.body() != null) {
                    //Toast.makeText(getBaseContext(),response.body().getInfo().getErrorMessage(),Toast.LENGTH_SHORT).show();

                    if (response.body().getInfo().getErrorCode() == 0) {
                        new Handler().postDelayed(
                                () -> {
                                    eventListener.onSuccess(true);
                                }, 100);
                    } else {
                    }
                } else {
                    //response.body() have your LoginResult fields and methods  (example you have to access error then try like this response.body().getError() )
                    //  Toast.makeText(getBaseContext(), "Data Error",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<UpdateOrderMainObject> call, Throwable t) {
                dialog.dismiss();
                Toast.makeText(context, "Error connecting server", Toast.LENGTH_SHORT).show();
            }

        });
    }

}