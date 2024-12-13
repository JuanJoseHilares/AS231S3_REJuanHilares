<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tabla de Prendas</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <link rel="icon" href="icon/icon.ico" type="image/x-icon">
</head>
<body>
<div class="container mt-5 text-center">
    <h1>Tabla Prendas</h1>
    <div class="container mt-3">
        <form action="garments" method="get" class="row g-3">
            <div class="col-md-3">
                <input type="text" name="code" class="form-control" placeholder="Código" pattern="^PCJM\d{3}$" title="El código debe seguir el formato PCJM001 (con tres dígitos)">
            </div>
            <div class="col-md-3">
                <input type="text" name="name" class="form-control" placeholder="Nombre" pattern="^[A-Za-záéíóúÁÉÍÓÚ\s]+$" title="El nombre solo puede contener letras y espacios">
            </div>
            <div class="col-md-3">
                <input type="text" name="brand" class="form-control" placeholder="Marca">
            </div>
            <div class="col-md-3">
                <button type="submit" class="btn btn-primary w-100">Buscar</button>
            </div>
        </form>
    </div>
    <c:if test="${!empty garments}">
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">ID</th>
                <th scope="col">Código</th>
                <th scope="col">Nombre</th>
                <th scope="col">Marca</th>
                <th scope="col">Categoría</th>
                <th scope="col">Precio</th>
                <th scope="col">Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${garments}" var="garment">
                <tr>
                    <td>${garment.id}</td>
                    <td>${garment.code}</td>
                    <td>${garment.name}</td>
                    <td>${garment.brand}</td>
                    <td>${garment.category}</td>
                    <td>
                        S/<fmt:formatNumber value="${garment.price}" pattern="#,##0.00" />
                    </td>
                    <td>
                        <!-- Mostrar botón de eliminar si es activo -->
                        <c:if test="${!inactive}">
                            <button type="button" class="btn btn-info" data-bs-toggle="modal" data-bs-target="#createGarmentModal" onclick="loadGarmentData('${garment.id}', '${garment.code}', '${garment.name}', '${garment.brand}', '${garment.category}', '${garment.price}')">Editar</button>
                            <form action="garments" method="post" style="display: inline;" onsubmit="confirmDelete(event)">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${garment.id}">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </form>
                        </c:if>

                        <!-- Mostrar botón de restaurar si es inactivo -->
                        <c:if test="${inactive}">
                            <form action="garments" method="post" style="display: inline;" onsubmit="confirmRestore(event)">
                                <input type="hidden" name="action" value="restore">
                                <input type="hidden" name="id" value="${garment.id}">
                                <button type="submit" class="btn btn-warning">Restaurar</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty garments}">
        <div class="alert alert-danger" role="alert">
            No hay datos disponibles.
        </div>
    </c:if>
</div>

<!-- Modal para crear una nueva prenda -->
<div class="modal fade" id="createGarmentModal" tabindex="-1" aria-labelledby="createGarmentModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createGarmentModalLabel">Crear una nueva prenda</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="createGarmentForm" action="garments" method="post">
                    <input type="hidden" name="action" value="create"> <!-- Se cambiará dinámicamente por 'update' -->
                    <input type="hidden" name="id" value=""> <!-- Se actualizará dinámicamente con el ID de la prenda -->

                    <div class="mb-3">
                        <label for="code" class="form-label">Código</label>
                        <input type="text" class="form-control" id="code" name="code" required pattern="^PCJM\d{3}$" title="El código debe seguir el formato PCJM001 (con tres dígitos)" />
                    </div>
                    <div class="mb-3">
                        <label for="name" class="form-label">Nombre</label>
                        <input type="text" class="form-control" id="name" name="name" required pattern="^[A-Za-záéíóúÁÉÍÓÚ\s]+$" title="El nombre solo puede contener letras y espacios" />
                    </div>
                    <div class="mb-3">
                        <label for="brand" class="form-label">Marca</label>
                        <input type="text" class="form-control" id="brand" name="brand" required />
                    </div>
                    <div class="mb-3">
                        <label for="category" class="form-label">Categoría</label>
                        <select class="form-select" id="category" name="category" required>
                            <option value="Damas">Damas</option>
                            <option value="Caballeros">Caballeros</option>
                            <option value="Deportivas">Deportivas</option>
                            <option value="Accesorios">Accesorios</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label for="price" class="form-label">Precio</label>
                        <input type="text" class="form-control" id="price" name="price" required pattern="^\d+(\.\d{2})?$" title="El precio debe ser un número con dos decimales, como 10.00" />
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
                <button type="button" class="btn btn-primary" id="createGarmentButton" onclick="submitForm()">Crear</button>
            </div>
        </div>
    </div>
</div>

<div class="text-center mt-3">
    <a href="kardex" class="btn btn-primary">Ver kardex</a>
    <c:if test="${!inactive}">
        <button type="button" class="btn btn-light" onclick="location.href='garments?action=sortByName'">Ordenar por Nombre (A-Z)</button>
        <button type="button" class="btn btn-danger" onclick="location.href='garments?action=pdf'">Descargar PDF</button>
        <button type="button" class="btn btn-warning" onclick="location.href='garments?action=xls'">Descargar XLS</button>
        <button type="button" class="btn btn-success" onclick="location.href='garments?action=csv'">Descargar CSV</button>
        <button type="button" class="btn btn-primary" onclick="location.href='garments?action=inactive'">Ver datos eliminados</button>
        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#createGarmentModal" onclick="resetModalForCreate()">Crear una nueva prenda</button>
    </c:if>

    <c:if test="${inactive}">
        <button type="button" class="btn btn-info" onclick="location.href='garments'">Ver datos activos</button>
    </c:if>

    <a href="index.jsp" class="btn btn-secondary">Volver a inicio</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>
</body>
</html>
<script>
    function confirmDelete(event) {
        event.preventDefault();  // Evita que el formulario se envíe automáticamente

        Swal.fire({
            title: '¿Estás seguro?',
            text: 'Este dato será eliminado',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const form = event.target;  // Obtiene el formulario
                Swal.fire({
                    title: 'Eliminado!',
                    text: 'El dato ha sido eliminado correctamente.',
                    icon: 'success'
                }).then(() => {
                    // Enviar el formulario después de mostrar el mensaje de éxito
                    form.submit();
                });
            }
        });
    }

    function confirmRestore(event) {
        event.preventDefault();

        Swal.fire({
            title: '¿Estás seguro?',
            text: 'Este dato será restaurado',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, restaurar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const form = event.target;
                Swal.fire({
                    title: 'Restaurado!',
                    text: 'El dato ha sido restaurado correctamente.',
                    icon: 'success'
                }).then(() => {
                    // Enviar el formulario después de mostrar el mensaje de éxito
                    form.submit();
                });
            }
        });
    }

    function submitForm() {
        var form = document.getElementById("createGarmentForm");

        if (form.checkValidity()) {
            // Si la acción es 'update', mostrar el mensaje de actualización exitosa
            if (form.elements["action"].value === "update") {
                Swal.fire({
                    title: '¡Actualización exitosa!',
                    text: 'Los datos han sido actualizados',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        form.submit();
                    }
                });
            } else {
                Swal.fire({
                    title: '¡Creación exitosa!',
                    text: 'La prenda ha sido creada',
                    icon: 'success',
                    confirmButtonText: 'Aceptar'
                }).then((result) => {
                    if (result.isConfirmed) {
                        form.submit();
                    }
                });
            }
        } else {
            form.reportValidity();
        }
    }

    function loadGarmentData(id, code, name, brand, category, price) {
        // Asegúrate de que el precio se convierta a número y se formatee correctamente
        let formattedPrice = formatPrice(price);

        // Establecer los valores del formulario en el modal
        document.getElementById("createGarmentForm").action = "garments"; // Asegurarse de que la acción sea para actualizar
        document.getElementById("createGarmentForm").elements["action"].value = "update";
        document.getElementById("createGarmentForm").elements["id"].value = id;
        document.getElementById("code").value = code;
        document.getElementById("name").value = name;
        document.getElementById("brand").value = brand;
        document.getElementById("category").value = category;
        document.getElementById("price").value = formattedPrice; // Pasa el precio formateado al campo

        // Cambiar el título del modal a "Actualizar Prenda"
        document.getElementById("createGarmentModalLabel").textContent = "Actualizar Prenda";

        // Cambiar el texto del botón a "Actualizar"
        document.getElementById("createGarmentButton").textContent = "Actualizar";
    }

    function formatPrice(price) {
        // Convertir el precio de cadena a número y asegurarse de que tenga dos decimales
        let number = parseFloat(price).toFixed(2); // Asegura que tenga dos decimales
        return number;
    }



    // Función para resetear el título y el botón cuando se abre el modal para crear una nueva prenda
    function resetModalForCreate() {
        // Cambiar el título del modal a "Crear una nueva prenda"
        document.getElementById("createGarmentModalLabel").textContent = "Crear una nueva prenda";

        // Cambiar el texto del botón a "Crear"
        document.getElementById("createGarmentButton").textContent = "Crear";

        // Limpiar los campos del formulario
        document.getElementById("createGarmentForm").reset();

        // Asegurarse de que la acción del formulario sea "create"
        document.getElementById("createGarmentForm").elements["action"].value = "create";
        // Limpiar el id de la prenda
        document.getElementById("createGarmentForm").elements["id"].value = "";
    }

    // Llamar a esta función cuando el modal se abra para crear una nueva prenda
    document.getElementById("createGarmentModal").addEventListener('show.bs.modal', resetModalForCreate);
</script>