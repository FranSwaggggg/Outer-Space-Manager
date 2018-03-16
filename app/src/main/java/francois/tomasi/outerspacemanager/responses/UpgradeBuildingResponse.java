package francois.tomasi.outerspacemanager.responses;

public class UpgradeBuildingResponse {
    private String code;
    private String internalCode;
    private String status;
    private String message;

    public UpgradeBuildingResponse(String code, String internalCode, String status, String message) {
        this.code = code;
        this.internalCode = internalCode;
        this.status = status;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getInternalCode() {
        return internalCode;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "UpgradeBuildingResponse{" +
                "code='" + code + '\'' +
                "internalCode='" + internalCode + '\'' +
                "status='" + status + '\'' +
                "message='" + message + '\'' +
                '}';
    }
}
