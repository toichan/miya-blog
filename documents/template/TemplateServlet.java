package template;//フォルダの相対パス

import java.io.IOException; // IOExceptionのインポート
import java.net.URLEncoder; // URLエンコード用
import javax.servlet.ServletException; // サーブレット例外のインポート
import javax.servlet.annotation.WebServlet; // WebServletアノテーションのインポート
import javax.servlet.http.HttpServlet; // HttpServletクラスのインポート
import javax.servlet.http.HttpServletRequest; // HttpServletRequestのインポート
import javax.servlet.http.HttpServletResponse; // HttpServletResponseのインポート
import javax.servlet.http.HttpSession; // HttpSessionのインポート

@WebServlet("/template") // URLを指定
public class TemplateServlet extends HttpServlet {

    // 必要なければ、GetかPostのどちらかを削除する

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // JSPへフォワード
        request.getRequestDispatcher("/WEB-INF/template/template.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // リダイレクト
        response.sendRedirect(request.getContextPath() + "/template");
    }
}
