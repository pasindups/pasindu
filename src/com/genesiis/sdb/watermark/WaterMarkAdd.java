/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.genesiis.sdb.watermark;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;

/**
 *
 * @author pasindu
 */
public class WaterMarkAdd extends javax.swing.JFrame {

    static final Logger logger = Logger.getLogger("MyLog");
    FileHandler fh;
    String LOG_FILE_PATH = "log.txt";
    int fileCount = 0;
    int successFileCount = 0;
    int folderCount = 0;

    /**
     * Creates new form WaterMarkAdd
     */
    public WaterMarkAdd() {
        initComponents();

        try {
            fh = new FileHandler(LOG_FILE_PATH);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.info("Starting the watermark application");
        } catch (SecurityException e) {
            logger.warning("Log file error " + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.warning(e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkForFolders(String wtrMrkPath, String imgSrcDir) {
        File watermarkImageFile = new File(wtrMrkPath);
        File folder = new File(imgSrcDir);
        String[] directories = folder.list(new FilenameFilter() {
            @Override
            public boolean accept(File current, String name) {
                return new File(current, name).isDirectory();
            }
        });
        
        System.out.println("DIR "+directories.length);

        if (directories != null) {
            for (int i = 0; i < directories.length; i++) {
                File folderPath = new File(imgSrcDir + directories[i]);
                applyWatermark(folderPath, watermarkImageFile);
                setStatusLabel("Folder Path : " + folderPath.getPath(), 0);
                folderCount = folderCount + 1;
            }
        } else {
            setStatusLabel("Cannot verify the folders", 2);
        }
    }

    public void applyWatermark(File dir, File watermarkImageFile) {
        try {
            File[] files = dir.listFiles();
            if (files == null) {
                setStatusLabel("Cannot verify the folders", 2);
            } else {
                for (File file : files) {

                    String fileNameWithPathAndExt = file.getPath();
                    logger.info("File Path + " + fileNameWithPathAndExt);
                    fileCount = fileCount + 1;

                    String fileExt = fileNameWithPathAndExt.substring(fileNameWithPathAndExt.length()-4, fileNameWithPathAndExt.length());
                    System.out.println(fileExt);
                    
                    if (fileExt.equalsIgnoreCase(".jpg") || fileExt.equalsIgnoreCase(".png") || fileExt.equalsIgnoreCase(".gif")) {
                        File sourceImageFile = new File(fileNameWithPathAndExt);

                        System.out.println("Source Image File Path" + sourceImageFile);
                        
                        setStatusLabel("Source Image File Path" + sourceImageFile, 0);

                        addImageWatermark(watermarkImageFile, sourceImageFile, sourceImageFile, fileExt);
                    }
                }

            }
        } catch (Exception e) {
            setStatusLabel(e.getMessage(), 2);
            e.printStackTrace();
        }
    }

    public void addImageWatermark(File watermarkImageFile,
            File sourceImageFile, File destImageFile, String extention) {
        try {
            BufferedImage sourceImage = ImageIO.read(sourceImageFile);
            BufferedImage watermarkImage = ImageIO.read(watermarkImageFile);

            // initializes necessary graphic properties
            Graphics2D g2d = (Graphics2D) sourceImage.getGraphics();
            AlphaComposite alphaChannel = AlphaComposite.getInstance(
                    AlphaComposite.SRC_OVER, 0.3f);
            g2d.setComposite(alphaChannel);

            // calculates the coordinate where the image is painted
            int topLeftX = (sourceImage.getWidth() - watermarkImage.getWidth()) / 2;
            int topLeftY = (sourceImage.getHeight() - watermarkImage
                    .getHeight()) / 3;

            // paints the image watermark
            g2d.drawImage(watermarkImage, topLeftX, topLeftY, null);

            ImageIO.write(sourceImage, extention.substring(1), destImageFile);
            g2d.dispose();

            setStatusLabel("Watermark added to : " + sourceImageFile.getPath(), 1);
            System.out.println("Watermark added to : " + sourceImageFile.getPath());
            successFileCount = successFileCount + 1;

        } catch (Exception ex) {
            setStatusLabel(ex.getMessage(), 2);
            ex.printStackTrace();
        }
    }

    private void setStatusLabel(String msg, int type) {
        statusLabel.setText(msg);
        if (type == 0) {
            statusLabel.setForeground(Color.BLUE);
            logger.info(msg);
        }
        if (type == 1) {
            statusLabel.setForeground(Color.GREEN);
            logger.info(msg);
        }
        if (type == 2) {
            statusLabel.setForeground(Color.RED);
            logger.warning(msg);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        wtrImgPathTxt = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        srcDirPathTxt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add Water Mark for images");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Watermark");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        wtrImgPathTxt.setEditable(false);

        jLabel2.setText("Watermark Image Path : ");

        jLabel3.setText("Source Images Folder Path : ");

        jButton1.setText("Add Watermark");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Select the Watermark");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 667, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(68, 68, 68)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(wtrImgPathTxt)
                                .addGap(18, 18, 18)
                                .addComponent(jButton2))
                            .addComponent(srcDirPathTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(34, 34, 34))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(wtrImgPathTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(jButton2))
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(srcDirPathTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 34, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(685, 250));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String wtrMrkPath = wtrImgPathTxt.getText();
        String imgSrcDir = srcDirPathTxt.getText();
        checkForFolders(wtrMrkPath, imgSrcDir);

        setStatusLabel("Watermark added to " + successFileCount + " of " + fileCount + " images in " + folderCount + " folders", 1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        wtrImgPathTxt.setText(fileChooser.getSelectedFile().getPath());
    }//GEN-LAST:event_jButton2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WaterMarkAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WaterMarkAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WaterMarkAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WaterMarkAdd.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WaterMarkAdd().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField srcDirPathTxt;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JTextField wtrImgPathTxt;
    // End of variables declaration//GEN-END:variables
}
