package model;

public class PremiumMember extends Member {
    private final int limitBorrow;
    private final long fineRate;

    public PremiumMember(String id, String name, String phone, String email) {
        super(id, name, phone, email);
        this.limitBorrow = 3;
        this.fineRate = 3000;
    }

    @Override
    public String showMemberType() {
        return "Premium Member";
    }

    @Override
    public void showMemberInfo() {
        System.out.printf("| %-10s | %-12s | %-20s | %-12s | %-25s | %-10d | %-10d | %-12d | %-10d |\n",
                showMemberType(), super.getId(), super.getName(), super.getPhone(), super.getEmail(),
                super.getCurrentBorrowedCount(), super.getTotalBorrowing(), limitBorrow, fineRate);
    }

    @Override
    public int getLimitBorrow() {
        return limitBorrow;
    }

    @Override
    public long getFineRate() {
        return fineRate;
    }

    @Override
    public long calculateFine(long lateDay) {
        return fineRate * lateDay;
    }
}