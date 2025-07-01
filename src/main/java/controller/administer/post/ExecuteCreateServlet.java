package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

import entity.Post; // Postエンティティのインポート
import modelUtil.Failure;
import dao.PostsDao; // PostsDaoのインポート
import dao.DaoException; // DaoExceptionのインポート
import java.time.LocalDateTime; // LocalDateTimeのインポート

@WebServlet("/administer/post/execute-create") // このサーブレットのURLマッピング
public class ExecuteCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8"); // 追加: 文字化け防止のためリクエストのエンコーディングをUTF-8に設定
        // messageとerrorの取得・セット
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("message", message);
        }
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }

        /*
         * HttpSession session = request.getSession(false); // 既存のセッションを取得（なければnull）
         * if (session == null || session.getAttribute("userId") == null) { //
         * セッションまたはuserId属性がなければ
         * String errorMessage = java.net.URLEncoder.encode("ログインしてください", "UTF-8");
         * response.sendRedirect(request.getContextPath() +
         * "/administer/account/login?error=" + errorMessage); // ログイン画面へリダイレクト（エラー付き）
         * return;
         * }
         */

        String title = request.getParameter("title"); // リクエストからタイトルを取得
        String text = request.getParameter("text"); // リクエストからテキストを取得
        // int userId = (Integer) session.getAttribute("userId"); // セッションからuserIdを取得
        int userId = 1; // テスト用に固定値を設定（実際のアプリケーションではセッションから取得する）

        Post post = null;
        try {
            post = new Post(0, LocalDateTime.now(), null, title, text, userId); // 新しい投稿オブジェクトを作成
        } catch (Failure e) {
            String errorMessage = java.net.URLEncoder.encode("投稿オブジェクトの作成中にエラーが発生しました。", "UTF-8");
            response.sendRedirect(request.getContextPath() + "/administer/post/home?error=" + errorMessage); // エラーメッセージと共にリダイレクト
            return;
        }
        PostsDao postsDao = new PostsDao(); // PostsDaoのインスタンスを生成

        try {
            postsDao.create(post); // 投稿をデータベースに作成
            response.sendRedirect(request.getContextPath() + "/administer/post/home?message=" +
                    java.net.URLEncoder.encode("投稿の作成が完了しました。", "UTF-8")); // 成功メッセージと共にリダイレクト
            return;
        } catch (DaoException e) {
            String errorMessage = java.net.URLEncoder.encode("投稿の作成中にエラーが発生しました。", "UTF-8"); // エラーメッセージをURLエンコード
            response.sendRedirect(request.getContextPath() + "/administer/post/home?error=" + errorMessage); // エラーメッセージと共にリダイレクト
        }
    }
}
