public enum MenuOption {
    LOGIN("1. Login"),
    REGISTER("2. Register"),
    EXIT("3. Exit");

    private final String description;

    MenuOption(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static MenuOption fromNumber(int choice) {
        switch (choice) {
            case 1:
                return LOGIN;
            case 2:
                return REGISTER;
            case 3:
                return EXIT;
            default:
                throw new IllegalArgumentException("Invalid menu option");
        }
    }
}
