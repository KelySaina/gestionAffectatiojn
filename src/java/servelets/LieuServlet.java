package servelets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import bean.Lieu;
import manager.LieuManager;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Lieu", urlPatterns = {"/lieu"})
public class LieuServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String jspPage = "/WEB-INF/pages/lieu.jsp";

        // Extract codeEmp from the URL 
        String codeLieu = request.getParameter("codeLieu");
        String codeLiToDelete = request.getParameter("codeLiToDelete");
        String codeLiToAdd = request.getParameter("codeLiToAdd");
        String designation = request.getParameter("designation");
        String province = request.getParameter("province");
        String codeLiToEdit = request.getParameter("codeLiToEdit");
        String newDesignation = request.getParameter("newDesignation");
        String newProvince = request.getParameter("newProvince");
        
        
        List<Lieu> lieuList = new ArrayList<>();
        
        if(codeLiToEdit != null && !codeLiToEdit.isEmpty() && newDesignation != null && !newDesignation.isEmpty() && newProvince != null && !newProvince.isEmpty() ){
            LieuManager li = new LieuManager();
            li.updateLieuByCode(codeLiToEdit, newDesignation, newProvince);
            lieuList = li.getAllData();
        }
        else if(codeLiToAdd != null && !codeLiToAdd.isEmpty() && designation != null && !designation.isEmpty() && province != null && !province.isEmpty()){
            LieuManager li = new LieuManager();
            li.addLieu(codeLiToAdd, designation, province);
            lieuList = li.getAllData();
        }
        else if (codeLieu != null && !codeLieu.isEmpty()) {
            // Handle the search request
            if (codeLieu.matches("L\\d+")) {
                LieuManager li = new LieuManager();
                Lieu lieu = li.getLieuByCode(codeLieu);

                if(lieu != null) {
                    lieuList.add(lieu); 
                }
            }else{
                LieuManager li = new LieuManager();
                lieuList = li.getAllData();
            }
        }
        else if(codeLiToDelete != null && !codeLiToDelete.isEmpty()) {
            LieuManager li = new LieuManager();
            li.deleteLieu(codeLiToDelete);
            lieuList = li.getAllData();
        }
        else {
            // Retrieve all employees
            LieuManager li = new LieuManager();
            lieuList = li.getAllData(); 
        }

        // Set employees in the request attribute
        request.setAttribute("lieuList", lieuList);

        // Use RequestDispatcher to forward the request to the JSP page  
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);

    }

}