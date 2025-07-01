package controller.administer.post;

import java.io.IOException; // IOExceptionのインポート
import javax.servlet.RequestDispatcher; // RequestDispatcherのインポート
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート
import dao.PostsDao; // PostsDaoのインポート
import entity.Post; // Postエンティティのインポート

import java.util.ArrayList; // ArrayListのインポート
import java.util.List; // Listのインポート

@WebServlet("/administer/post/home") // このサーブレットのURLマッピング
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // messageとerrorの取得・セット
        String message = request.getParameter("message");
        if (message != null && !message.isEmpty()) {
            request.setAttribute("message", message);
        }
        String error = request.getParameter("error");
        if (error != null && !error.isEmpty()) {
            request.setAttribute("error", error);
        }

        // HttpSession session = request.getSession(false); // セッションを取得（存在しない場合はnull）
        // if (session == null || session.getAttribute("userId") == null) { //
        // セッションまたはuserId属性がなければ
        // String errorMessage = java.net.URLEncoder.encode("ログインしてください", "UTF-8");
        // response.sendRedirect(request.getContextPath() +
        // "/administer/account/login?error=" + errorMessage); // ログイン画面へリダイレクト（エラー付き）
        // return; // 以降の処理を中断
        // }

        try {
            PostsDao postsDao = new PostsDao(); // 投稿データアクセスオブジェクトを生成
            List<Post> posts = postsDao.getAll(); // 全投稿データを取得
            if (posts == null) {
                posts = new ArrayList<>(); // 投稿がなければ空リストをセット
            }
            request.setAttribute("posts", posts); // 投稿リストをリクエスト属性にセット
        } catch (Exception e) {
            throw new ServletException("投稿データの取得中にエラーが発生しました。", e); // 例外発生時はServletExceptionでラップしてスロー
        }
        String alertMessage = request.getParameter("message"); // リクエストパラメータからmessageを取得
        if (alertMessage != null && !alertMessage.isEmpty()) { // messageが存在し空でなければ
            request.setAttribute("alertMessage", alertMessage); // alertMessage属性としてセット
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/administer/post/home.jsp"); // JSPへのディスパッチャ取得
        dispatcher.forward(request, response); // JSPへフォワード
    }
}