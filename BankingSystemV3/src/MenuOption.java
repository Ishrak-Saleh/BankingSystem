enum MenuOption {
    CHECK_BALANCE(1),
    DEPOSIT(2),
    WITHDRAW(3),
    TRANSFER(4),
    DELETE_ACCOUNT(5),
    VIEW_INFO(6),
    LOGOUT(7);

    private final int value;

    MenuOption(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
