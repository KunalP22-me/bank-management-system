import java.awt.*;
import javax.swing.*;

public class BankInterface extends JFrame {

    private BankServer bank = new BankServer();
    private Accountholder currentUser;
    private JTextField userField = new JTextField(10);
    private JPasswordField passField = new JPasswordField(10);
    private JTextArea outputArea = new JTextArea(12, 30);
    private JComboBox<String> accountBox = new JComboBox<>();
    private JTextField amountField = new JTextField(10);
    private JLabel balanceLabel = new JLabel();

    public BankInterface() {
        setTitle("Bank Management System");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 800);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        UIManager.put("Label.font", new Font("Dialog", Font.PLAIN, 16));
        UIManager.put("Button.font", new Font("Dialog", Font.BOLD, 16));
        UIManager.put("TextField.font", new Font("Dialog", Font.PLAIN, 16));
        UIManager.put("PasswordField.font", new Font("Dialog", Font.PLAIN, 16));
        UIManager.put("TextArea.font", new Font("Monospaced", Font.PLAIN, 14));

        loginScreen();
        setVisible(true);
    }

    private void loginScreen() {
        getContentPane().removeAll();

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setBackground(new Color(230, 240, 250));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(60, 110, 60, 110));

        JLabel title = new JLabel("\u265F Welcome to KP Cantilever Bank"); // Symbol: ♟
        title.setFont(new Font("Dialog", Font.BOLD, 28));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setForeground(new Color(33, 37, 41));
        title.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel subTitle = new JLabel("Login or Register to continue");
        subTitle.setFont(new Font("Dialog", Font.PLAIN, 18));
        subTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        subTitle.setForeground(new Color(120, 120, 130));
        subTitle.setHorizontalAlignment(SwingConstants.CENTER);

        userField = new JTextField(15);
        userField.setMaximumSize(new Dimension(220, 36));
        userField.setHorizontalAlignment(JTextField.CENTER);

        passField = new JPasswordField(15);
        passField.setMaximumSize(new Dimension(220, 36));
        passField.setHorizontalAlignment(JTextField.CENTER);

        JLabel userLbl = new JLabel("\u263A Username:"); // Symbol: ☺
        userLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        userLbl.setHorizontalAlignment(SwingConstants.CENTER);
        JLabel passLbl = new JLabel("\u270E Password:"); // Symbol: ✎
        passLbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        passLbl.setHorizontalAlignment(SwingConstants.CENTER);

        JButton loginBtn = new JButton("\u2387 Login");          // ⎇
        JButton registerBtn = new JButton("\u271A Register");    // ✚
        styleButton(loginBtn, new Color(0, 123, 255));
        styleButton(registerBtn, new Color(40, 167, 69));
        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);

        loginPanel.add(title);
        loginPanel.add(Box.createVerticalStrut(6));
        loginPanel.add(subTitle);
        loginPanel.add(Box.createVerticalStrut(22));
        loginPanel.add(userLbl);
        loginPanel.add(userField);
        loginPanel.add(Box.createVerticalStrut(10));
        loginPanel.add(passLbl);
        loginPanel.add(passField);
        loginPanel.add(Box.createVerticalStrut(20));
        loginPanel.add(buttonPanel);

        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void styleButton(JButton button, Color bgColor) {
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("Dialog", Font.BOLD, 17));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(bgColor.darker(), 1, true),
            BorderFactory.createEmptyBorder(10, 28, 10, 28)
        ));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }

    private void dashboard() {
        getContentPane().removeAll();

        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setBackground(new Color(52, 152, 219));
        userPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel userLabel = new JLabel("\u263A Logged in as: " + currentUser.getUsername()); // ☺
        userLabel.setFont(new Font("Dialog", Font.BOLD, 16));
        userLabel.setForeground(Color.WHITE);
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel lastLoginLabel = new JLabel("\u23F2 Last login: " + currentUser.getLastLogin()); // ⏲
        lastLoginLabel.setFont(new Font("Dialog", Font.PLAIN, 13));
        lastLoginLabel.setForeground(Color.WHITE);
        lastLoginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel topInfo = new JPanel();
        topInfo.setOpaque(false);
        topInfo.setLayout(new BoxLayout(topInfo, BoxLayout.Y_AXIS));
        topInfo.add(userLabel);
        topInfo.add(Box.createVerticalStrut(4));
        topInfo.add(lastLoginLabel);

        userPanel.add(topInfo, BorderLayout.CENTER);

        JPanel accountPanel = new JPanel();
        accountPanel.setLayout(new BoxLayout(accountPanel, BoxLayout.Y_AXIS));
        accountPanel.setBackground(new Color(245, 245, 245));
        accountPanel.setBorder(BorderFactory.createEmptyBorder(14, 40, 14, 40));

        JPanel accRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        accRow.setOpaque(false);

        JLabel accLabel = new JLabel("\u25A1 Select Account:"); // □
        accLabel.setFont(new Font("Dialog", Font.PLAIN, 16));
        accLabel.setHorizontalAlignment(SwingConstants.CENTER);
        accRow.add(accLabel);

        accountBox = new JComboBox<>();
        for (AccountManage acc : currentUser.getAccounts()) {
            accountBox.addItem(acc.getName());
        }
        accountBox.setMaximumSize(new Dimension(140, 34));
        accountBox.setFont(new Font("Dialog", Font.PLAIN, 15));
        accRow.add(accountBox);

        JButton addAccBtn = new JButton("\u2795 Add Account");      // ➕
        JButton delAccBtn = new JButton("\u2716 Delete Account");   // ✖
        styleButton(addAccBtn, new Color(255, 193, 7));
        styleButton(delAccBtn, new Color(220, 53, 69));

        addAccBtn.addActionListener(e -> {
            String name = JOptionPane.showInputDialog("New account name:");
            if (name != null && !name.isEmpty()) {
                currentUser.addAccount(name);
                accountBox.addItem(name);
                bank.updateData();
                updateBalanceLabel(balanceLabel);
            }
        });

        delAccBtn.addActionListener(e -> {
            String selected = (String) accountBox.getSelectedItem();
            if (selected != null && !selected.equals("Main")) {
                int confirm = JOptionPane.showConfirmDialog(this, "Delete account '" + selected + "'?");
                if (confirm == JOptionPane.YES_OPTION) {
                    currentUser.deleteAccount(selected);
                    accountBox.removeItem(selected);
                    bank.updateData();
                    updateBalanceLabel(balanceLabel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Main account can't be deleted.");
            }
        });

        accRow.add(addAccBtn);
        accRow.add(delAccBtn);

        balanceLabel.setFont(new Font("Dialog", Font.BOLD, 18));
        balanceLabel.setForeground(new Color(50, 50, 50));
        balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);

        accountPanel.add(accRow);
        accountPanel.add(Box.createVerticalStrut(8));
        accountPanel.add(balanceLabel);

        accountBox.setSelectedIndex(0);
        updateBalanceLabel(balanceLabel);
        accountBox.addActionListener(e -> updateBalanceLabel(balanceLabel));

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 12));
        actionPanel.setBackground(new Color(245, 245, 245));
        amountField = new JTextField(12);
        amountField.setHorizontalAlignment(JTextField.CENTER);
        amountField.setFont(new Font("Monospaced", Font.PLAIN, 16));
        amountField.setMaximumSize(new Dimension(90, 34));

        JButton depositBtn = new JButton("\u2191 Deposit");    // ↑
        JButton withdrawBtn = new JButton("\u2193 Withdraw");  // ↓
        JButton historyBtn = new JButton("\u2630 History");    // ☰
        JButton logoutBtn = new JButton("\u21E6 Logout");      // ⇦
        JButton changePassBtn = new JButton("\u270E Change Password"); // ✎
        JButton transferBtn = new JButton("\u21C4 Transfer"); // ⇄ symbol
        
        styleButton(depositBtn, new Color(40, 167, 69));
        styleButton(withdrawBtn, new Color(220, 53, 69));
        styleButton(historyBtn, new Color(108, 117, 125));
        styleButton(logoutBtn, new Color(52, 58, 64));
        styleButton(changePassBtn, new Color(102, 16, 242));
        styleButton(transferBtn, new Color(23, 162, 184));
        
        depositBtn.addActionListener(e -> {
            doDeposit();
            updateBalanceLabel(balanceLabel);
        });
        withdrawBtn.addActionListener(e -> {
            doWithdraw();
            updateBalanceLabel(balanceLabel);
        });
        historyBtn.addActionListener(e -> showHistory());
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            loginScreen();
        });
        changePassBtn.addActionListener(e -> {
            String newPass = JOptionPane.showInputDialog(this, "Enter new password:");
            if (newPass != null && !newPass.isEmpty()) {
                currentUser.setPassword(newPass);
                bank.updateData();
                JOptionPane.showMessageDialog(this, "Password changed.");
            }
        });
        transferBtn.addActionListener(e -> transferMoney());
        
        JLabel amtLbl = new JLabel("\u20B9 Amount:"); // ₹
        amtLbl.setHorizontalAlignment(SwingConstants.CENTER);
        
        actionPanel.add(changePassBtn);
        actionPanel.add(amtLbl);
        actionPanel.add(amountField);
        actionPanel.add(depositBtn);
        actionPanel.add(withdrawBtn);
        actionPanel.add(historyBtn);
        actionPanel.add(logoutBtn);
        actionPanel.add(transferBtn);

        outputArea = new JTextArea(12, 40);
        outputArea.setEditable(false);
        outputArea.setBackground(new Color(255, 255, 240));
        outputArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        outputArea.setFont(new Font("Monospaced", Font.ITALIC, 14));
        outputArea.setForeground(new Color(70, 70, 80));
        outputArea.setAlignmentX(Component.CENTER_ALIGNMENT);

        JScrollPane scrollPane = new JScrollPane(outputArea);

        JPanel logPanel = new JPanel(new BorderLayout());
        logPanel.setBorder(BorderFactory.createTitledBorder("\u2630 Transaction Log")); // ☰
        logPanel.add(scrollPane, BorderLayout.CENTER);

        add(userPanel, BorderLayout.NORTH);
        add(accountPanel, BorderLayout.BEFORE_FIRST_LINE);
        add(actionPanel, BorderLayout.CENTER);
        add(logPanel, BorderLayout.SOUTH);

        revalidate();
        repaint();
        pack();
    }

    private void updateBalanceLabel(JLabel label) {
        AccountManage acc = selectedAccount();
        if (acc != null) {
            label.setText("\u20B9 Balance: ₹" + String.format("%.2f", acc.getBalance()));
        } else {
            label.setText("\u20B9 Balance: ₹0.00");
        }
    }

    private void login() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        currentUser = bank.login(user, pass);
        if (currentUser != null) {
            dashboard();
        } else {
            JOptionPane.showMessageDialog(this, "\u2716 Invalid credentials."); // ✖
        }
    }

    private void register() {
        String user = userField.getText().trim();
        String pass = new String(passField.getPassword()).trim();
        if (bank.register(user, pass)) {
            JOptionPane.showMessageDialog(this, "\u2714 Registration successful."); // ✔
        } else {
            JOptionPane.showMessageDialog(this, "\u26A0 User already exists."); // ⚠
        }
    }

    private AccountManage selectedAccount() {
        String selected = (String) accountBox.getSelectedItem();
        if (selected == null) return null;
        return currentUser.getAccount(selected);
    }

    private void doDeposit() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            AccountManage acc = selectedAccount();
            acc.deposit(amt);
            outputArea.append("\u2191 Deposited ₹" + amt + " to " + acc.getName() + "\n");
            bank.updateData();
        } catch (NumberFormatException e) {
            outputArea.append("\u2716 Invalid amount\n"); // ✖
        }
    }

    private void doWithdraw() {
        try {
            double amt = Double.parseDouble(amountField.getText());
            AccountManage acc = selectedAccount();
            if (acc.withdraw(amt)) {
                outputArea.append("\u2193 Withdrew ₹" + amt + " from " + acc.getName() + "\n");
            } else {
                outputArea.append("\u26A0 Insufficient funds.\n"); // ⚠
            }
            bank.updateData();
        } catch (NumberFormatException e) {
            outputArea.append("\u2716 Invalid amount\n"); // ✖
        }
    }

    private void showHistory() {
        AccountManage acc = selectedAccount();
        outputArea.append("--- " + acc.getName() + " History ---\n");
        for (String txn : acc.getTransactions()) {
            outputArea.append(txn + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankInterface());
    }

    private void transferMoney() {
    java.util.List<String> accNames = new java.util.ArrayList<>();
    for (AccountManage acc : currentUser.getAccounts()) {
        accNames.add(acc.getName());
    }

    JPanel panel = new JPanel(new GridLayout(4, 2, 5, 5));
    JComboBox<String> sourceBox = new JComboBox<>(accNames.toArray(new String[0]));
    JComboBox<String> destBox = new JComboBox<>(accNames.toArray(new String[0]));
    JTextField amountField = new JTextField();

    panel.add(new JLabel("Source Account:"));
    panel.add(sourceBox);
    panel.add(new JLabel("Destination Account:"));
    panel.add(destBox);
    panel.add(new JLabel("Amount to Transfer:"));
    panel.add(amountField);

    int result = JOptionPane.showConfirmDialog(this, panel, "Transfer Money", JOptionPane.OK_CANCEL_OPTION);

    if (result == JOptionPane.OK_OPTION) {
        String sourceName = (String) sourceBox.getSelectedItem();
        String destName = (String) destBox.getSelectedItem();

        if (sourceName.equals(destName)) {
            JOptionPane.showMessageDialog(this, "Source and destination accounts must be different.");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountField.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Amount must be positive.");
                return;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid amount entered.");
            return;
        }

        AccountManage src = currentUser.getAccount(sourceName);
        AccountManage dest = currentUser.getAccount(destName);

        if (src == null || dest == null) {
            JOptionPane.showMessageDialog(this, "Account not found.");
            return;
        }
        
        if (!src.withdraw(amount)) {
            JOptionPane.showMessageDialog(this, "Insufficient funds in source account.");
            return;
        }

        dest.deposit(amount);
        outputArea.append(String.format("⇄ Transferred ₹%.2f from %s to %s\n", amount, sourceName, destName));
        updateBalanceLabel(balanceLabel);
        bank.updateData();
    }
}
}