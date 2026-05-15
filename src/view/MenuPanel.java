/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;

import controller.ControlLoginPanel;
import dao.VideoDAO;
import dao.SearchHistoryDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import model.Movie;
import model.Serie;
import model.User;
import model.Video;


/**
 *
 * @author ekpri
 */
public class MenuPanel extends javax.swing.JFrame {
    private Connection conn;
    private ControlLoginPanel cLogin;
    private javax.swing.JDialog historyDialog;
    private User user;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MenuPanel.class.getName());
    

    public MenuPanel(ControlLoginPanel cLogin, Connection conn, User user)
                                                           throws SQLException {
        initComponents();
        this.setLocationRelativeTo(null);
        this.cLogin = cLogin;
        this.conn = conn;
        this.user = user;
        lblUsername.setText(user.getName());
        
        VideoDAO dao = new VideoDAO(conn);
        loadVideos(dao.listVideos(user.getId()));
        
        //Build Icons in the Menu
        setScaledIcon(lblSearchIcon, "/resources/lupa.png");
        setScaledIcon(lblUserIcon, "/resources/profile.png");
        setScaledIcon(lblFavIcon, "/resources/star.png");
        
        //Set Icons as button
        asButton(lblFavIcon, () -> {
            new FavoritesPanel().setVisible(true);
            this.dispose();
        });
        
        asButton(lblUserIcon, () -> {
            new ProfilePanel().setVisible(true);
            this.dispose();
        });
        
        //Start the search field
        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent key) {
                String words = txtSearch.getText().trim();
                try {
                    VideoDAO videoDAO = new VideoDAO(conn);

                    //When search field is empty shows the history
                    if(words.isEmpty()) {
                        loadVideos(videoDAO.listVideos(user.getId()));
                        showHistory();
                    }else{
                        hideHistory();
                        //Trigger search and save when ENTER is pressed
                        if (key.getKeyCode() == KeyEvent.VK_ENTER) {
                            SearchHistoryDAO historyDAO = 
                                new SearchHistoryDAO(conn);
                            List<Video> videos = videoDAO.searchByTitle(words);

                            historyDAO.saveSearch(user.getId(), words);

                            //Return result of the search
                            if (videos.isEmpty()) {
                                JOptionPane.showMessageDialog(null,
                                    "Nenhum vídeo encontrado: " + words,"Busca",
                                    JOptionPane.INFORMATION_MESSAGE);
                            }else{
                                JOptionPane.showMessageDialog(null,
                                    "Resultados encontrados", "Busca", 
                                    JOptionPane.INFORMATION_MESSAGE);
                                loadVideos(videos);
                            }
                        }
                    }
                }catch(SQLException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Erro",
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        //Shows history when clicked in search field
        txtSearch.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (txtSearch.getText().trim().isEmpty()) {
                    showHistory();
                }
            }
        });
    }
    
    private void setScaledIcon(JLabel label, String resourcePath) {
        try {
            ImageIcon originalIcon = 
                new ImageIcon(getClass().getResource(resourcePath));

            int width = label.getWidth();
            int height = label.getHeight();

            Image scaledImg = originalIcon.getImage().getScaledInstance(
                    width, height, Image.SCALE_SMOOTH
            );
            label.setIcon(new ImageIcon(scaledImg));
        } catch (Exception e) {
            System.err.println("Erro ao cargegar ícone: " + resourcePath);
        }
    }
    
    private void asButton(JLabel label, Runnable action) {
        label.setCursor(new Cursor(Cursor.HAND_CURSOR));

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                action.run();
            }

            @Override
            public void mouseEntered(MouseEvent evt) {
                label.setOpaque(true);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                label.setOpaque(false);
                label.setBackground(null);
            }
        });
    }
    
    private void showHistory() {
        try {
            SearchHistoryDAO historyDAO = new SearchHistoryDAO(conn);
            List<String> history = historyDAO.getHistory(user.getId());
            
            if (history.isEmpty()) return;
            
            //Close dialog if it already exist
            if (historyDialog != null && historyDialog.isVisible()) {
                historyDialog.dispose();
            }
            
            historyDialog = new JDialog(this, false);
            historyDialog.setUndecorated(true);
            historyDialog.setLayout(new BorderLayout());
            historyDialog.setFocusableWindowState(false);
            
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            panel.setBackground(Color.WHITE);
            panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(0, 0, 2, 0)
            ));
            
            for (String word : history) {
                JPanel item = new JPanel(new BorderLayout());
                item.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
                item.setBackground(Color.WHITE);
                item.setMaximumSize(new Dimension(txtSearch.getWidth(), 40));
                
                JLabel lblWord = new JLabel(word);
                lblWord.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                
                item.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent ev) {
                        txtSearch.setText(word);
                        hideHistory();
                    }
                });
                
                item.add(lblWord, BorderLayout.WEST);
                panel.add(item);
                
                if (history.indexOf(word) < history.size() - 1) {
                    JSeparator sep = new JSeparator();
                    sep.setForeground(new Color(230, 230, 230));
                    sep.setMaximumSize(new Dimension(txtSearch.getWidth(), 1));
                    panel.add(sep);
                }
            }
            
            historyDialog.add(panel);
            historyDialog.pack();
            
            //Set history under search field
            Point searchPosition = txtSearch.getLocationOnScreen();
            int positionX = searchPosition.x;
            int positionY = searchPosition.y + txtSearch.getHeight();
            
            //Defines the size to go along with search field
            int width = txtSearch.getWidth();
            int heightItem = 41;
            int maxHeight = 232;

            int height = history.size() * heightItem;
            int finalHeight = Math.min(height + 2, maxHeight);

            //Apply all metrics to show
            historyDialog.setLocation(positionX, positionY);
            historyDialog.setSize(width, finalHeight);
            historyDialog.setVisible(true);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,e.getMessage(), "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hideHistory() {
        if (historyDialog != null && historyDialog.isVisible()) {
            historyDialog.dispose();
            historyDialog = null;
        }
    }
    
    private JPanel createVideo(Video video) {
        //Set card size
        JPanel card = new JPanel(new BorderLayout());
        card.setPreferredSize(new Dimension(300, 230));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        //Set image size 
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/"
                                                    + "default_thumbnail.png"));
        Image scaledImage = icon.getImage().getScaledInstance(300, 150, 
                                                            Image.SCALE_SMOOTH);
        
        //Set thumbnail
        JLabel thumb = new JLabel(new ImageIcon(scaledImage));
        thumb.setHorizontalAlignment(SwingConstants.LEFT);

        //Set informations
        JLabel title = new JLabel(video.getTitle());
        int time = video.getDuration();
        int hour = time/3600;
        int min = (time%3600)/60;
        int sec = time % 60;
        String strTime;
        
        if(hour <= 0) {
            strTime = String.format("%02d:%02d", min, sec);
        }else{
            strTime = String.format("%02d:%02d:%02d", hour, min, sec);
        }
        JLabel duration = new JLabel("Time: " + strTime);
        
        JPanel info = new JPanel(new GridLayout(2, 1, 0, 5));
        info.setBackground(Color.WHITE);
        info.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        info.add(title);
        info.add(duration);

        card.add(thumb, BorderLayout.CENTER);
        card.add(info, BorderLayout.SOUTH);
        
        //Open video info
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (video instanceof Movie) {
                    new MovieInfoPanel((Movie) video, user, conn).setVisible(true);
                } else if (video instanceof Serie) {
                    new SerieInfoPanel((Serie) video, user, conn).setVisible(true);
                }
            }
        });

        return card;
    }
    
    private void loadVideos(List<Video> videos) {
        JPanel grid = new JPanel(new GridLayout(0, 4, 20, 20));
        
        for(Video v : videos) {
            //Secure the scale of the card will be respect
            JPanel wrapper = 
            new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            wrapper.setOpaque(false);
            
            wrapper.add(createVideo(v));
            grid.add(wrapper);
        }
        
        scrollpnlMenu.setViewportView(grid);
        scrollpnlMenu.revalidate();
        scrollpnlMenu.repaint();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblUsername = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        scrollpnlMenu = new javax.swing.JScrollPane();
        lblUserIcon = new javax.swing.JLabel();
        lblSearchIcon = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        lblFavIcon = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblUsername.setText("LOGADO");
        lblUsername.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(scrollpnlMenu, javax.swing.GroupLayout.DEFAULT_SIZE, 1295, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 11, Short.MAX_VALUE)
                .addComponent(scrollpnlMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 618, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        lblUserIcon.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblFavIcon.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(lblUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(225, 225, 225)
                .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 435, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSearchIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblFavIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblUsername, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblUserIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblSearchIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblFavIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
//            logger.log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> new MenuPanel().setVisible(true));
//    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblFavIcon;
    private javax.swing.JLabel lblSearchIcon;
    private javax.swing.JLabel lblUserIcon;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JScrollPane scrollpnlMenu;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
