package francois.tomasi.outerspacemanager.responses;

public class AttackUserResponse {
    private String code;
    private int attackTime;

    public AttackUserResponse(String code, int attackTime) {
        this.code = code;
        this.attackTime = attackTime;
    }

    public String getCode() { return code; }

    public int getAttackTime() { return attackTime; }
}
