<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="bean.Lieu" %>
<%@ page import="manager.LieuManager" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Lieu</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body style="padding: 12px"  id="employeeTableBody">
    <%
        List<Lieu> lieuList = (List<Lieu>)request.getAttribute("lieuList");
        String errMsg = (String) request.getAttribute("errorMessage");
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
        <form class="d-flex">
            <input class="form-control mr-sm-2" type="search" placeholder="Search" id="searchValue" aria-label="Search">
            <button class="btn btn-outline-primary my-2 my-sm-0" type="button" onclick="search()">Search</button>
        </form>
    </nav>
    <% if (errMsg != null) { %>
        <div class="alert alert-danger" role="alert">
            <%= errMsg %>
        </div>
    <% } %>

    <h1 style="margin: 12px 0"><b>Place Management</b></h1>

    <div class="row">
        <div class="col-sm-8 table-responsive" >
            <table class="table align-middle table-bordered table-striped ">
                <caption>List of Places</caption>
                <thead class="table-dark">
                  <tr>
                    <th scope="col">#</th>
                    <th scope="col">Designation</th>
                    <th scope="col">Province</th>
                    <th scope="col">Actions</th>
                  </tr>
                </thead>
                <tbody>
                        <% for (Lieu li : lieuList) { %>
                            <tr>
                                <td><%= li.getCodeLieu()%></td>
                                <td><%= li.getDesignation()%></td>
                                <td><%= li.getProvince()%></td>
                                <td style="display: flex; justify-content: space-evenly; align-items: center;" >
                                    <button type="button" class="btn btn-primary" style="width: 80px" onclick="setValues('<%= li.getCodeLieu() %>','<%= li.getDesignation()%>','<%= li.getProvince()%>')">Edit</button>
                                    <button type="button" class="btn btn-outline-danger" data-bs-toggle="modal" data-bs-target="#exampleModal" onclick="confirmDelete('<%= li.getCodeLieu() %>'); showAdd()" style="width: 80px">Delete</button>
                                </td>
                            </tr>
                        <% } %>
                </tbody>
            </table>
        </div>
        <div class="col">
            <h3>Operations</h3>
            <form>
                  <input type="hidden" value="" id="liCode"/>
                  <div class="form-group">
                    <label for="inputNom">Designation</label>
                    <input type="text" class="form-control" id="inputDesignation" placeholder="Ex: Fihary Soft">
                  </div>
                <div class="form-group" style="margin-top: 20px">
                  <label for="inputPoste">Province</label>
                  <input type="text" class="form-control" id="inputProvince" placeholder="Ex: Fianarantsoa">
                </div>
                <div class="form-group" style="margin-top: 20px">
                    <button  type="submit" class="btn btn-primary" id="addBtn" onclick="ajouter()">Add</button>
                </div>
                <div class="form-group" style="margin-top: 20px; display: flex;" >
                    <button  type="submit" class="btn btn-primary" id="saveBtn" style="display: none" onclick="edit()">Save</button>
                    <button  type="submit" class="btn btn-secondary" id="cancelBtn" style="display: none; margin-left: 24px;" onclick="cancel()">Cancel</button>
                </div>
              </form>
        </div>
    </div>
                
<!--     MODAL CONFIRMATION DELETE 
    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Delete Place</h5>
              <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" onclick="$('#exampleModal').modal('hide');"></button>
            </div>
            <div class="modal-body">
                Are you sure you want to delete this record?<br/><span style="color:tomato;">This action is irreversible</span>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary" data-bs-dismiss="modal" onclick="$('#exampleModal').modal('hide');">Go Back</button>
              <button type="button" class="btn btn-outline-danger" onclick="del(getLiCode())"> Delete Anyway</button>
            </div>
          </div>
        </div>
      </div>-->

<script>
    
    function getLiCode(){
//        alert(document.getElementById("liCode").value)
        return document.getElementById("liCode").value;
    }
    
    function showAdd(){
        document.getElementById("cancelBtn").style.display = 'none';
        document.getElementById("saveBtn").style.display = 'none';
        document.getElementById("addBtn").style.display = 'block';
        //document.getElementById("inputNom").value = '';
        //document.getElementById("inputPrenom").value = '';
        //document.getElementById("inputPoste").value = '';
    }
    function search() {
        showAdd()
        
        // Get the search value from the input field
        var searchValue = document.getElementById("searchValue").value;

        // Make an AJAX request to the servlet
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/webapp/lieu?codeLieu=" + searchValue, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Update the table with the search results
                document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
            }
        };

        xhr.send();
    }
    
    function del(codeLiToDelete) {
        showAdd()
        //alert(codeEmpToDelete)

        // Make an AJAX request to the servlet
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/webapp/lieu?codeLiToDelete=" + codeLiToDelete, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Update the table with the search results
                document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
            }
        };

        xhr.send();
    }
    
    function edit(){
        newDesignation = document.getElementById("inputDesignation").value;
        newProvince = document.getElementById("inputProvince").value;
        codeLiToEdit = getLiCode();
//        alert(codeLiToEdit);
        
        // Make an AJAX request to the servlet
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/webapp/lieu?codeLiToEdit=" + codeLiToEdit +"&newDesignation="+newDesignation+"&newProvince="+newProvince, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Update the table with the search results
                document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
            }
        };

        xhr.send();
        
        console.log(xhr.responseText);
        
    }
    
    function setValues(liCode, designation, province){
        document.getElementById("liCode").value = liCode;
        document.getElementById("inputDesignation").value = designation;
        document.getElementById("inputProvince").value = province;
        document.getElementById("cancelBtn").style.display = 'block';
        document.getElementById("saveBtn").style.display = 'block';
        document.getElementById("addBtn").style.display = 'none';
    }
    
    function cancel(){
        document.getElementById("cancelBtn").style.display = 'none';
        document.getElementById("saveBtn").style.display = 'none';
        document.getElementById("addBtn").style.display = 'block';
        document.getElementById("inputDesignation").value = '';
        document.getElementById("inputProvince").value = '';
    }
    
    
    
    function ajouter() {
        designation = document.getElementById("inputDesignation").value;
        province = document.getElementById("inputProvince").value;
        
        codeLiToAdd = "L"+Math.floor(Math.random()*10000);

        // Make an AJAX request to the servlet
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "/webapp/lieu?codeLiToAdd=" + codeLiToAdd +"&designation="+designation+"&province="+province, true);

        xhr.onreadystatechange = function () {
            if (xhr.readyState == 4 && xhr.status == 200) {
                // Update the table with the search results
                document.getElementById("employeeTableBody").innerHTML = xhr.responseText;
            }
        };

        xhr.send();
        showAdd()
    }
    function confirmDelete(codeLiToDelete) {
        var confirmation = confirm("Are you sure you want to delete this record?\nThis action is irreversible.");

        if (confirmation) {
            del(codeLiToDelete);
        }
    }
</script>

    <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-B4gt1jrGC7Jh4AgTPSdUtOBvfO8sh+WyVn+30C6JZumkkF5faFm9tF81iIMkQs6J" crossorigin="anonymous"></script>

</body>
</html>
