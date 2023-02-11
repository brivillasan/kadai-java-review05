package jp.co.kiramex.dbSample.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Review05 {

    public static void main(String[] args) {
        // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        PreparedStatement spstmt = null;    // 検索用プリペアードステートメントオブジェクト
        ResultSet rs = null;

        try {
            // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/kadaidb?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "L67kn@22sss64311");

            // 4. DBとやりとりする窓口（PreparedStatementオブジェクト）の作成
            // 検索用SQLおよび検索用PreparedStatementオブジェクトを取得
            String selectSql = "SELECT * FROM  person where id = ?";
            spstmt = con.prepareStatement(selectSql);

            // idを入力
            System.out.print("検索キーワードを入力してください > ");
            int input = keyInNum();

         // PreparedStatementオブジェクトの?に値をセット  // ← 追記
            spstmt.setInt(1, input);  // ← 追記

            rs = spstmt.executeQuery();


            // 7. 結果を表示する
            while (rs.next()) {
                // name列の値を取得
                String name = rs.getString("name");
                // Population列の値を取得 ← 追記
                int age = rs.getInt("age");

                // 取得した値を表示
                System.out.println(name);
                System.out.println(age);
            }
        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        
            // TODO 自動生成された catch ブロック
          
        } finally {
            // 8. 接続を閉じる
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if (spstmt != null) {    // ← 修正
                try {
                    spstmt.close();    // ← 修正
                } catch (SQLException e) {
                    System.err.println("PreparedStatementを閉じるときにエラーが発生しました。");    // ← 修正
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }
    }

    private static int keyInNum()  {
        int result = 0;
        try {
            BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
            result = Integer.parseInt(keyIn.readLine());
        } catch (NumberFormatException | IOException e) {
        }
        return result;
    }

}