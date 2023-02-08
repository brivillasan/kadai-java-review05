package jp.co.kiramex.dbSample.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConnectSample01 {

    public static void main(String[] args) {
        // TODO 自動生成されたメソッド・スタブ
     // 3. データベース接続と結果取得のための変数宣言
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
     // 1. ドライバのクラスをJava上で読み込む
            Class.forName("com.mysql.cj.jdbc.Driver");
     // 2. DBと接続する
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost/world?useSSL=false&allowPublicKeyRetrieval=true",
                    "root",
                    "L67kn@22sss64311"//自分のパスワード
                );
      // 4. DBとやりとりする窓口（Statementオブジェクト）の作成
            stmt = con.createStatement();
            // 5, 6. Select文の実行と結果を格納／代入
            String sql = "SELECT * FROM country LIMIT 50";
            rs = stmt.executeQuery(sql);
            // 7. 結果を表示する
            while( rs.next() ){
                // Name列の値を取得
                String name = rs.getString("Name");
                // 取得した値を表示
                System.out.println(name);
            }
//rs.next()メソッドとは、表の中に「次の行のデータ」があるかどうかを指し示すための処理なのです。
//別の言い方をすると、DBから取り出してきたデータは何行であっても、複数行まとめての処理はできず、
//1行ずつ処理することが必須となっています。
//今回は50行のデータとなるため、最後（50行目）までカーソルが指し示されたあとにrs.next()で
//51行目を指し示そうとするも、51行目がない状態なのでfalseが戻り、while文の処理が終了
//します。


        } catch (ClassNotFoundException e) {
            System.err.println("JDBCドライバのロードに失敗しました。");
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
        }catch (SQLException e) {
            System.err.println("データベースに異常が発生しました。");
            e.printStackTrace();
        }  finally {
            if( rs != null ){
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.err.println("ResultSetを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            if( stmt != null ){
                try {
                    stmt.close();
                } catch (SQLException e) {
                    System.err.println("Statementを閉じるときにエラーが発生しました。");
                    e.printStackTrace();
                }
            }
            // 8. 接続を閉じる
            if( con != null ){
                try {
                    con.close();
                } catch (SQLException e) {
                    System.err.println("データベース切断時にエラーが発生しました。");
                    e.printStackTrace();
                }
            }
        }

    }

}
