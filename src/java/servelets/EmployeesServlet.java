package servelets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import java.io.IOException;

import bean.Employee;
import manager.EmployeeManager;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "Employees", urlPatterns = {"/employees"})
public class EmployeesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        String jspPage = "/WEB-INF/pages/employees.jsp";

        // Extract codeEmp from the URL 
        String codeEmp = request.getParameter("codeEmp");
        String codeEmpToDelete = request.getParameter("codeEmpToDelete");
        String codeEmpToAdd = request.getParameter("codeEmpToAdd");
        String nom = request.getParameter("nom");
        String prenom = request.getParameter("prenom");
        String poste = request.getParameter("poste");
        String codeEmpToEdit = request.getParameter("codeEmpToEdit");
        String newNom = request.getParameter("newNom");
        String newPrenom = request.getParameter("newPrenom");
        String newPoste = request.getParameter("newPoste");
        
        
        List<Employee> employeeList = new ArrayList<>();
        
        if(codeEmpToEdit != null && !codeEmpToEdit.isEmpty() && newNom != null && !newNom.isEmpty() && newPrenom != null && !newPrenom.isEmpty() && newPoste != null && !newPoste.isEmpty() ){
            EmployeeManager em = new EmployeeManager();
            em.editEmployee(codeEmpToEdit, newNom, newPrenom, newPoste);
            employeeList = em.getAllData();
        }
        else if(codeEmpToAdd != null && !codeEmpToAdd.isEmpty() && nom != null && !nom.isEmpty() && prenom != null && !prenom.isEmpty() && poste != null && !poste.isEmpty() ){
            EmployeeManager em = new EmployeeManager();
            em.addEmployee(codeEmpToAdd, nom, prenom, poste);
            employeeList = em.getAllData();
        }
        else if (codeEmp != null && !codeEmp.isEmpty()) {
            // Handle the search request
            if (codeEmp.matches("E\\d+")) {
                EmployeeManager em = new EmployeeManager();
                Employee employee = em.getEmployeeByCode(codeEmp);

                if(employee != null) {
                    employeeList.add(employee); 
                }
            }else{
                EmployeeManager em = new EmployeeManager();
                employeeList = em.getEmployeeByName(codeEmp);
            }
        }
        else if(codeEmpToDelete != null && !codeEmpToDelete.isEmpty()) {
            EmployeeManager em = new EmployeeManager();
            em.deleteEmployee(codeEmpToDelete);
            employeeList = em.getAllData();
        }
        else {
            // Retrieve all employees
            EmployeeManager em = new EmployeeManager();
            employeeList = em.getAllData(); 
        }

        // Set employees in the request attribute
        request.setAttribute("employeeList", employeeList);

        // Use RequestDispatcher to forward the request to the JSP page  
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspPage);
        dispatcher.forward(request, response);

    }

}