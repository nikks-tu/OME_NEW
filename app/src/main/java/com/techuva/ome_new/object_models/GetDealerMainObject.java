package com.techuva.ome_new.object_models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetDealerMainObject  {

    @SerializedName("info")
    @Expose
    private GetDealerInfoObject info;
    @SerializedName("result")
    @Expose
    private List<GetDealerResultObject> result = null;

    public GetDealerInfoObject getInfo() {
        return info;
    }

    public void setInfo(GetDealerInfoObject info) {
        this.info = info;
    }

    public List<GetDealerResultObject> getResult() {
        return result;
    }

    public void setResult(List<GetDealerResultObject> result) {
        this.result = result;
    }

}