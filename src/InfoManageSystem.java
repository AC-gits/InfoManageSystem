public class InfoManageSystem {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoginForm().createAndShowLoginGUI();
        });
    }

    public static void showSignUpForm() {
        javax.swing.SwingUtilities.invokeLater(() -> {
            new LoadingScreen(() -> {
                new SignUpForm().createAndShowSignUpGUI();
            }).showLoadingScreen();
        });
    }
}
