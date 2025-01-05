package photoWrite;

import java.sql.*;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;

class PhotoWrite {

    public static void main(String argv[]) {
        Connection con = null;
        PreparedStatement pstmt = null;
        InputStream fin = null;
        String url = "jdbc:mysql://localhost:3306/dobuy?serverTimezone=Asia/Taipei";
        String userid = "root";


        String passwd = "123456789";

        String photos = "src/main/resources/static/Gp_Photos"; // 測試用圖片資料夾
        String update = "UPDATE Goods SET gpPhotos? = ? WHERE goodsNo = ?"; // 動態設置欄位

        try {
            con = DriverManager.getConnection(url, userid, passwd);

            // 處理 goodsNo1 的照片 (101.jpg~110.jpg)
            updatePhotos(con, update, photos, 1, 101, 110);

            // 處理 goodsNo2 的照片 (201.jpg~206.jpg)
            updatePhotos(con, update, photos, 2, 201, 206);

            // 處理 goodsNo3 的照片 (301.jpg~305.jpg)
            updatePhotos(con, update, photos, 3, 301, 305);
            
            // 處理 goodsNo4 的照片 (401.jpg~404.jpg)
            updatePhotos(con, update, photos, 4, 401, 405);
            
            // 處理 goodsNo5 的照片 (501.jpg~503.jpg)
            updatePhotos(con, update, photos, 5, 501, 503);
            
            // 處理 goodsNo6 的照片 (601.jpg~604.jpg)
            updatePhotos(con, update, photos, 6, 601, 604);
            
            // 處理 goodsNo7 的照片 (701.jpg~705.jpg)
            updatePhotos(con, update, photos, 7, 701, 705);
            
            // 處理 goodsNo8 的照片 (801.jpg~805.jpg)
            updatePhotos(con, update, photos, 8, 801, 805);
            
            // 處理 goodsNo9 的照片 (901.jpg~907.jpg)
            updatePhotos(con, update, photos, 9, 901, 907);
            
            // 處理 goodsNo10 的照片 (1001.jpg~1006.jpg)
            updatePhotos(con, update, photos, 10, 1001, 1006);
            
            // 處理 goodsNo11 的照片 (1101.jpg~1103.jpg)
            updatePhotos(con, update, photos, 11, 1101, 1103);
            
            // 處理 goodsNo12 的照片 (1201.jpg~1203.jpg)
            updatePhotos(con, update, photos, 12, 1201, 1203);
            
            // 處理 goodsNo13 的照片 (1301.jpg~1302.jpg)
            updatePhotos(con, update, photos, 13, 1301, 1302);
            
            // 處理 goodsNo14 的照片 (1401.jpg~1403.jpg)
            updatePhotos(con, update, photos, 14, 1401, 1403);
            
            // 處理 goodsNo15 的照片 (1501.jpg~1506.jpg)
            updatePhotos(con, update, photos, 15, 1501, 1506);
            
            // 處理 goodsNo16 的照片 (1601.jpg~1607.jpg)
            updatePhotos(con, update, photos, 16, 1601, 1607);
            
            // 處理 goodsNo17 的照片 (1701.jpg~1706.jpg)
            updatePhotos(con, update, photos, 17, 1701, 1706);
            
            // 處理 goodsNo18 的照片 (1801.jpg~1804.jpg)
            updatePhotos(con, update, photos, 18, 1801, 1804);
            
            // 處理 goodsNo19 的照片 (1901.jpg~1903.jpg)
            updatePhotos(con, update, photos, 19, 1901, 1903);
            
            // 處理 goodsNo20 的照片 (2001.jpg~2004.jpg)
            updatePhotos(con, update, photos, 20, 2001, 2004);

            System.out.println("加入圖片-更新成功.........");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void updatePhotos(Connection con, String update, String photosPath, int goodsNo, int start, int end) throws Exception {
        PreparedStatement pstmt = null;
        InputStream fin = null;

        try {
            File[] photoFiles = new File(photosPath).listFiles();

            if (photoFiles != null) {
                // 篩選符合範圍的檔案，例如 101.jpg~110.jpg 或 301.jpg~305.jpg
                File[] filteredFiles = Arrays.stream(photoFiles)
                        .filter(f -> {
                            String name = f.getName();
                            try {
                                int fileNum = Integer.parseInt(name.substring(0, name.indexOf('.')));
                                return fileNum >= start && fileNum <= end;
                            } catch (NumberFormatException e) {
                                return false;
                            }
                        })
                        .sorted(Comparator.comparingInt(f -> {
                            String name = f.getName();
                            return Integer.parseInt(name.substring(0, name.indexOf('.')));
                        }))
                        .toArray(File[]::new);

                for (int i = 0; i < filteredFiles.length; i++) {
                    File f = filteredFiles[i];
                    fin = new FileInputStream(f);

                    pstmt = con.prepareStatement(update);
                    pstmt.setInt(1, i + 1); // 動態欄位: gpPhotos1 ~ gpPhotos10
                    pstmt.setBinaryStream(2, fin); // 設定 LONGBLOB 的二進位資料
                    pstmt.setInt(3, goodsNo); // 商品編號

                    pstmt.executeUpdate();
                    System.out.print("更新資料庫...");
                    System.out.println("goodsNo" + goodsNo + ": " + f.toString());

                    fin.close(); // 關閉檔案輸入流
                }
            }
        } finally {
            if (pstmt != null) pstmt.close();
        }
    }
}
