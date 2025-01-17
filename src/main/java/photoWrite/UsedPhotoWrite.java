package photoWrite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsedPhotoWrite {
	// 資料庫連接配置
    private static final String DB_URL = "jdbc:mysql://localhost:3306/dobuy";
    private static final String DB_USER = "root";

    private static final String DB_PASSWORD = "e1012125";


    public static void main(String[] args) {
        String baseFolder = "C:\\Users\\Winfred\\OneDrive\\Desktop\\柏翔的\\專案\\CIA103_WebApp\\eclipse_WTP_workspace4B_To-SpringBootMVC_1-5-8U\\Dobuy\\src\\main\\resources\\static\\UsedPictures"; // 根目錄

        File folder = new File(baseFolder);
        if (!folder.exists() || !folder.isDirectory()) {
            System.out.println("資料夾不存在: " + baseFolder);
            return;
        }

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            String sql = "INSERT INTO usedPic (usedNo, usedPicName, usedPics) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

                // 遍歷資料夾
                for (File subFolder : folder.listFiles()) {
                    if (!subFolder.isDirectory()) continue;

                    int usedNo;
                    try {
                        usedNo = Integer.parseInt(subFolder.getName());
                    } catch (NumberFormatException e) {
                        System.out.println("跳過無效資料夾名稱: " + subFolder.getName());
                        continue;
                    }

                    // 處理每個資料夾中的照片
                    for (File imageFile : subFolder.listFiles()) {
                        if (!imageFile.isFile()) continue;

                        String fileName = imageFile.getName();
                        if (!fileName.toLowerCase().matches(".*\\.(png|jpg|jpeg|bmp|gif)$")) continue;

                        // 讀取圖片資料
                        byte[] imageData = Files.readAllBytes(imageFile.toPath());

                        // 插入到資料庫
                        pstmt.setInt(1, usedNo);
                        pstmt.setString(2, fileName);
                        pstmt.setBytes(3, imageData);
                        pstmt.executeUpdate();

                        System.out.println("已插入: 資料夾 " + subFolder.getName() + " -> 照片 " + fileName);
                    }
                }

                System.out.println("所有照片已成功插入資料庫！");
            }

        } catch (SQLException e) {
            System.err.println("資料庫錯誤: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("文件讀取錯誤: " + e.getMessage());
        }
    }
}
