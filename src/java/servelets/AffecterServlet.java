package servelets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.text.ParseException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import bean.Affecter;
import bean.AffecterId;
import bean.Employee;
import bean.Lieu;
import manager.AffecterManager;

import java.text.SimpleDateFormat;

@WebServlet(name = "Affecter", urlPatterns = { "/affecter" })
public class AffecterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String jspPage = "/WEB-INF/pages/affecter.jsp";

        String codeEmpToDel = request.getParameter("codeEmpToDel");
        String codeLiToDel = request.getParameter("codeLiToDel");
        String codeEmpToAdd = request.getParameter("codeEmpToAdd");
        String codeLiToAdd = request.getParameter("codeLiToAdd");
        String dateToAdd = request.getParameter("dateToAdd");

        List<Affecter> affList = new ArrayList<>();
        AffecterManager aff = new AffecterManager();

        if ((codeEmpToDel != null && !codeEmpToDel.isEmpty()) || (codeLiToDel != null && !codeLiToDel.isEmpty())) {
            aff.deleteAffecter(codeEmpToDel, codeLiToDel);
        } else if ((codeEmpToAdd != null && !codeEmpToAdd.isEmpty()) || (codeLiToAdd != null && !codeLiToAdd.isEmpty()) || (dateToAdd != null && !dateToAdd.isEmpty())) {
            try {
                System.out.println(dateToAdd);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); // Adjust the pattern based on your date format
                Date d = dateFormat.parse(dateToAdd);

                // You need to set values for e and l here
                Employee e = new Employee();
                // Set values for e

                Lieu l = new Lieu();
                // Set values for l

                aff.addAffecter(new AffecterId(codeEmpToAdd, codeLiToAdd), d, e, l);
            } catch (ParseException e) {
                e.printStackTrace(); // Handle the parse exception appropriately
            }  catch (Exception e) {
                e.printStackTrace(); // Handle the exception appropriately
            }
        }

        affList = aff.getAllData();
        request.setAttribute("affList", affList);

        // Use RequestDispatcher to forward the request to the JSP page
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);
    }
}
