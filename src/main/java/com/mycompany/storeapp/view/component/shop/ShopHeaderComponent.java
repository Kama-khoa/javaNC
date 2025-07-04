/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.storeapp.view.component.shop;

/**
 *
 * @author Manh Hung
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ShopHeaderComponent extends JPanel {
    
    // Colors
    private static final Color HEADER_BACKGROUND = new Color(59, 130, 246);
    private static final Color TEXT_COLOR = Color.WHITE;
    private static final Color SECONDARY_TEXT_COLOR = new Color(219, 234, 254);
    private static final Color BUTTON_HOVER_COLOR = new Color(37, 99, 235);
    private static final Color SEARCH_BACKGROUND = new Color(255, 255, 255, 20);
    
    // Components
    private JLabel logoLabel;
    private JLabel titleLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JLabel timeLabel;
    private JLabel userLabel;
    private JButton cartToggleButton;
    private JButton logoutButton;
    
    // Event listeners
    private ActionListener logoutActionListener;
    private ActionListener toggleCartActionListener;
    
    // Timer for clock
    private Timer clockTimer;
    
    public ShopHeaderComponent() {
        initializeHeader();
        setupComponents();
        setupLayout();
        startClock();
    }
    
    private void initializeHeader() {
        setLayout(new BorderLayout());
        setBackground(HEADER_BACKGROUND);
        setBorder(new EmptyBorder(12, 20, 12, 20));
        setPreferredSize(new Dimension(0, 70));
    }
    
    private void setupComponents() {
        // Left panel - Logo and title
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        leftPanel.setOpaque(false);
        
        logoLabel = new JLabel("🏪");
        logoLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 28));
        logoLabel.setBorder(new EmptyBorder(0, 0, 0, 12));
        
        titleLabel = new JLabel("Hệ thống POS");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(TEXT_COLOR);
        
        leftPanel.add(logoLabel);
        leftPanel.add(titleLabel);
        
        // Center panel - Search
        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        
        searchField = new JTextField(25);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            new EmptyBorder(8, 12, 8, 12)
        ));
        searchField.setBackground(SEARCH_BACKGROUND);
        searchField.setForeground(TEXT_COLOR);
        searchField.setCaretColor(TEXT_COLOR);
        searchField.setOpaque(false);
        
        // Placeholder text
        searchField.setText("Tìm kiếm sản phẩm...");
        searchField.setForeground(SECONDARY_TEXT_COLOR);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (searchField.getText().equals("Tìm kiếm sản phẩm...")) {
                    searchField.setText("");
                    searchField.setForeground(TEXT_COLOR);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Tìm kiếm sản phẩm...");
                    searchField.setForeground(SECONDARY_TEXT_COLOR);
                }
            }
        });
        
        searchButton = createHeaderButton("🔍", "Tìm kiếm");
        searchButton.addActionListener(e -> handleSearch());
        
        centerPanel.add(searchField);
        centerPanel.add(Box.createHorizontalStrut(8));
        centerPanel.add(searchButton);
        
        // Right panel - User info and actions
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightPanel.setOpaque(false);
        
        // Time display
        timeLabel = new JLabel();
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        timeLabel.setForeground(SECONDARY_TEXT_COLOR);
        
        // User info
        userLabel = new JLabel("👤 Admin");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userLabel.setForeground(TEXT_COLOR);
        userLabel.setBorder(new EmptyBorder(0, 12, 0, 12));
        
        // Cart toggle button
        cartToggleButton = createHeaderButton("🛒", "Hiện/Ẩn giỏ hàng (F2)");
        cartToggleButton.addActionListener(e -> {
            if (toggleCartActionListener != null) {
                toggleCartActionListener.actionPerformed(e);
            }
        });
        
        // Logout button
        logoutButton = createHeaderButton("🚪", "Đăng xuất (Alt+Q)");
        logoutButton.addActionListener(e -> {
            if (logoutActionListener != null) {
                logoutActionListener.actionPerformed(e);
            }
        });
        
        rightPanel.add(timeLabel);
        rightPanel.add(userLabel);
        rightPanel.add(cartToggleButton);
        rightPanel.add(logoutButton);
        
        add(leftPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
    }
    
    private void setupLayout() {
        // Additional layout setup if needed
    }
    
    private JButton createHeaderButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        button.setForeground(TEXT_COLOR);
        button.setBackground(new Color(255, 255, 255, 20));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(255, 255, 255, 50), 1),
            new EmptyBorder(6, 10, 6, 10)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setToolTipText(tooltip);
        
        // Hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(255, 255, 255, 20));
            }
        });
        
        return button;
    }
    
    private void startClock() {
        clockTimer = new Timer(1000, e -> updateTime());
        clockTimer.start();
        updateTime(); // Initial update
    }
    
    private void updateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");
        timeLabel.setText(sdf.format(new Date()));
    }
    
    private void handleSearch() {
        String searchText = searchField.getText().trim();
        
        if (searchText.isEmpty() || searchText.equals("Tìm kiếm sản phẩm...")) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập từ khóa tìm kiếm!", 
                "Thông báo", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // TODO: Implement search functionality
        System.out.println("Searching for: " + searchText);
        JOptionPane.showMessageDialog(this, 
            "Tìm kiếm: " + searchText + "\n(Chức năng đang phát triển)", 
            "Tìm kiếm", 
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Public methods
    public void addLogoutActionListener(ActionListener listener) {
        this.logoutActionListener = listener;
    }
    
    public void addToggleCartActionListener(ActionListener listener) {
        this.toggleCartActionListener = listener;
    }
    
    public void setUserName(String userName) {
        userLabel.setText("👤 " + userName);
    }
    
    public String getSearchText() {
        String text = searchField.getText().trim();
        return text.equals("Tìm kiếm sản phẩm...") ? "" : text;
    }
    
    public void clearSearch() {
        searchField.setText("Tìm kiếm sản phẩm...");
        searchField.setForeground(SECONDARY_TEXT_COLOR);
    }
    
    public void focusSearchField() {
        searchField.requestFocus();
    }
    
    @Override
    public void removeNotify() {
        super.removeNotify();
        if (clockTimer != null) {
            clockTimer.stop();
        }
    }
}