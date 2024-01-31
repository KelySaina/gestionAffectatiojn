package servelets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import bean.Affecter;
import bean.AffecterId;
import bean.Employee;
import bean.Lieu;
import manager.AffecterManager;

@WebServlet(name = "Affecter", urlPatterns = { "/affecter" })
public class AffecterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String jspPage = "/WEB-INF/pages/affecter.jsp";

        String codeEmpToDel = request.getParameter("codeEmpToDel");
        String codeLiToDel = request.getParameter("codeLiToDel");
        String codeEmpToAdd = request.getParameter("codeEmpToAdd");
        String codeLiToAdd = request.getParameter("codeLiToAdd");

        List<Affecter> affList = new ArrayList<>();
        AffecterManager aff = new AffecterManager();

        if ((codeEmpToDel != null && !codeEmpToDel.isEmpty()) || (codeLiToDel != null && !codeLiToDel.isEmpty())) {
            aff.deleteAffecter(codeEmpToDel, codeLiToDel);
        } else if ((codeEmpToAdd != null && !codeEmpToAdd.isEmpty()) || (codeLiToAdd != null && !codeLiToAdd.isEmpty())) {
            Date d = new Date();
            Employee e = new Employee(); // You need to set values for e and l here
            Lieu l = new Lieu(); // You need to set values for e and l here
            aff.addAffecter(new AffecterId(codeEmpToAdd, codeLiToAdd), d, e, l);
        }

        affList = aff.getAllData();
        request.setAttribute("affList", affList);

        // Use RequestDispatcher to forward the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);
    }
}
