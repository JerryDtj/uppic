package typora.uppic.common;

/**
 * @Description 返回实体类
 * @Date 2024/3/25 18:47
 * @Created by 76574
 */
public class ApiResult {
    /**
     * http状态码
     * 默认 9999 未连接
     */
    private Integer code;
    /**
     * 是否成功
     * 默认否
     */
    private Boolean IsSuccess;

    /**
     * 文件网盘路径
     */
    private String fileUrl;

    /**
     * 错误消息
     */
    private String errorMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Boolean getSuccess() {
        return IsSuccess;
    }

    public void setSuccess(Boolean success) {
        IsSuccess = success;
    }

    public ApiResult(int code, boolean isSuccess, String fileUrl, String errorMsg) {
        this.code = code;
        IsSuccess = isSuccess;
        this.fileUrl = fileUrl;
        this.errorMsg = errorMsg;
    }


    public static ApiResult success(String fileUrl){
        return new ApiResult(200,true,fileUrl,null);
    }

    public static ApiResult fail(Integer code,String errorMsg){
        return new ApiResult(code,false,null,errorMsg);
    }

    public static ApiResult init(){
        return new ApiResult(9999,false,"","初始化");
    }

}
