
package Homeworks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpartanResponse {

    @SerializedName("success")
    @Expose
    private String success;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public SpartanResponse() {
    }

    /**
     * 
     * @param data
     * @param success
     */
    public SpartanResponse(String success, Data data) {
        super();
        this.success = success;
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
