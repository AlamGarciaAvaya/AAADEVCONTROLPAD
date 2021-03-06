package service.AAADEVCONTROLPAD.Servlets;



import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import service.AAADEVCONTROLPAD.Arrays;

/**
 *
 * @author umansilla
 */
@WebServlet(name = "Grabaciones", urlPatterns = {"/Grabaciones/*"})
public class Grabaciones extends HttpServlet {

    private static final long serialVersionUID = 1L;
//    private final Logger logger;
    String userHomeDir = null;
    String osName = null;
    String windows = "WINDOWS";

    @Override
    public void init() throws ServletException {
        this.userHomeDir = System.getProperty("user.home");
        this.osName = System.getProperty("os.name");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * 
     * Método doGet es usado para ser solicitado por el Front end y enviar los archivos existentes en la ruta solicitada
     * la cual se ha establecido /Grabaciones/web/Watson/
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	setAccessControlHeaders(response);
        PrintWriter out = response.getWriter();
        String reviewLocationOnHttpServer = null;
//        File audioFile = null;
        String reqURIForReview = request.getRequestURL().toString();
        String[] recordNameArray = reqURIForReview.split("\\/");
        @SuppressWarnings("unused")
		String recFileName = recordNameArray[recordNameArray.length - 1];
        final String str[] = reqURIForReview.split("/");
        final int lengthUrlPre = reqURIForReview.indexOf(str[4]);
        final int lengthUrlPost = str[4].length();
        final int completeUrl = lengthUrlPre + lengthUrlPost;
        final String folderPath = reqURIForReview.substring(completeUrl, reqURIForReview.lastIndexOf('/'));
        reviewLocationOnHttpServer = userHomeDir + folderPath;
        JSONObject grabaciones = Arrays.main(reviewLocationOnHttpServer);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(200);
        out.println(grabaciones);

    }

    //AUTORIZAR CROSS DOMAIN
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8085");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With");
    }

}
