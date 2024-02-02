<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="bean.Affecter" %>
<%@ page import="bean.Employee" %>
<%@ page import="bean.Lieu" %>
<%@ page import="manager.AffecterManager" %>
<%@ page import="manager.EmployeeManager" %>
<%@ page import="manager.LieuManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Affectation</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body style="padding: 12px"  id="employeeTableBody">
    <%
        List<Affecter> affList = (List<Affecter>)request.getAttribute("affList");
        Employee e = new Employee();
        Lieu l = new Lieu();
        AffecterManager am = new AffecterManager();
        EmployeeManager em = new EmployeeManager();
        LieuManager lm = new LieuManager();
    %>

    <nav class="navbar navbar-light bg-light justify-content-between">
        <div class="dropdown">
            <ul class="nav">
                <li class="nav-item">
                  <a class="nav-link" href="/webapp/employees">Employees</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/webapp/lieu">Places</a>
                </li>
                <li class="nav-item">
                  <a class="nav-link" href="/webapp/affecter">Assignments</a>
                </li>
            </ul>
        </div>
    </nav>

    <h1 style="margin: 12px 0; display: flex; align-items: center; justify-content: space-between"><span><b>Affectation Management</b></span> <span> <button  type="button" class="btn btn-success" id="assignBtn" onclick="$('#exampleModal').modal('show')"> + New Assignment</button></span></h1>
    <h6>Liste of Assignments</h6>
    <div class="container">
        <div class="row row-cols-1 row-cols-md-2 row-cols-lg-3 g-3">
            <% for (Affecter aff : affList) { %>
                <div class="col">
                    <div class="card">
                        <div class="card-body">
                            <% e = am.getEmpDetails(aff.getId().getCodeEmp()); %>
                            <h5 class="card-title"><b><%= e.getNom() %></b> <%= e.getPrenom() %> | <%= e.getPoste() %></h5>
                            <% l = am.getLieuDetails(aff.getId().getCodeLieu()); %>
                            <p class="card-text"><%= l.getDesignation() %>, <%= l.getProvince() %></p>
                            <% SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); %>
                            <% String formattedDate = dateFormat.format(aff.getDate()); %>
                            <p class="card-text"><%= formattedDate %></p>
                            <a href="#" class="btn btn-primary"
                                onclick="confirmDelete('<%= aff.getId().getCodeEmp() %>', '<%= aff.getId().getCodeLieu() %>')">Unassing</a>
                        </div>
                    </div>
                </div>
            <% } %>
        </div>
    </div>

        
        <div class="modal fade" id="exampleModal" tabindex="-1">
            <div class="modal-dialog">
              <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title">Assign an Employee to a Place</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="$('#exampleModal').modal('hide')"></button>
                </div>
                <div class="modal-body">
                    <%
                        List<Employee> listEmployeeNotAssigned = em.getEmployeesNotInAffecterTable();
                    %>
                    <label for="selectEmployee">Select an Employee</label>
                    <select class="form-select" aria-label="Default select Employee" id="selectEmployee">
                        <% for (Employee employeeNotAssigned : listEmployeeNotAssigned) { %>
                        <option value='<%= employeeNotAssigned.getCodeEmp()%>'>
                            <%= '#'+employeeNotAssigned.getCodeEmp() + " - " + employeeNotAssigned.getNom() + " " + employeeNotAssigned.getPrenom() + " [ " + employeeNotAssigned.getPoste() + " ]"%>
                        </option>
                        <% } %>
                    </select>
                    <p></p>
                    <%
                      List<Lieu> listLieu = lm.getAllData();
                    %>
                    <label for="selectPlace">Select a Place</label>
                    <select class="form-select" aria-label="Default select Place" id="selectPlace">
                        <% for (Lieu li : listLieu) { %>
                        <option value="<%= li.getCodeLieu() %>">
                            <%= '#'+li.getCodeLieu() + " - " + li.getDesignation()+ ", " + li.getProvince()%>
                        </option>
                        <% } %>
                    </select>
                    <p></p>
                    <label for="selectPlace">Pick a date</label>
                    <input class="form-select" type="date" id="dateToAdd"  />
                    <p></p>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal" onclick="$('#exampleModal').modal('hide')">Close</button>
                    <button type="button" class="btn btn-primary" onclick="ajouter()">Assing</button>
                </div>
              </div>
            </div>
         </div>
                    
        <script>
            function del(codeEmpToDel, codeLiToDel) {
                //showAdd()
                //alert(idToDelete)

                // Make an AJAX request to the servlet
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "/webapp/affecter?codeEmpToDel=" + codeEmpToDel + "&codeLiToDel=" + codeLiToDel, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // Update the table with the search results
                        document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
                    }
                };

                xhr.send();
            }
            function confirmDelete(codeEmpToDel, codeLiToDel) {
                var confirmation = confirm("Are you sure you want to unassign this Employee?\nThis action is irreversible.");

                if (confirmation) {
                    del(codeEmpToDel, codeLiToDel);
                }
            }
            function ajouter() {
                var currentTimestamp = new Date().getTime();
                var currentTime = new Date(currentTimestamp);
                var hours = currentTime.getHours();
                var minutes = currentTime.getMinutes();
                var seconds = currentTime.getSeconds();

                // Create a formatted time string
                var formattedTime = hours + ':' + (minutes < 10 ? '0' : '') + minutes + ':' + (seconds < 10 ? '0' : '') + seconds;
                
                codeEmpToAdd = document.getElementById("selectEmployee").value;
                codeLiToAdd = document.getElementById("selectPlace").value;
                dateToAdd = document.getElementById("dateToAdd").value+" "+formattedTime;
                //alert(dateToAdd)
                // Make an AJAX request to the servlet
                var xhr = new XMLHttpRequest();
                xhr.open("GET", "/webapp/affecter?codeEmpToAdd=" + codeEmpToAdd +"&codeLiToAdd="+codeLiToAdd+"&dateToAdd="+dateToAdd, true);

                xhr.onreadystatechange = function () {
                    if (xhr.readyState == 4 && xhr.status == 200) {
                        // Update the table with the search results
                        document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
                    }
                };

                xhr.send();
            }
        </script>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+WyVn+30C6JZumkkF5faFm9tF81iIMkQs6J" crossorigin="anonymous"></script>

</body>
</html>
