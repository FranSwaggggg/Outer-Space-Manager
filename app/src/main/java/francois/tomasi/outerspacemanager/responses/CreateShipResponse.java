package francois.tomasi.outerspacemanager.responses;

public class CreateShipResponse {
    private String internalCode;

    public CreateShipResponse(String internalCode) {
        this.internalCode = internalCode;
    }

    public String getInternalCode() { return internalCode; }
}
