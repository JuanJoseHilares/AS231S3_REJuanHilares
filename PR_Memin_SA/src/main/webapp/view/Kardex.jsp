<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Tabla Kardex</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
    <link rel="icon" href="icon/icon.ico" type="image/x-icon">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body>
<div class="container mt-5 text-center">
    <h1>Tabla Kardex</h1>
    <div class="container mt-3">
        <form action="kardex" method="get" class="row g-3">
            <input type="hidden" name="action" value="searchByOptionalParams">

            <div class="col-md-3">
                <input type="date" class="form-control" id="searchRegistrationDate" name="registrationDate" placeholder="Fecha de Registro (opcional)">
            </div>

            <div class="col-md-3">
                <input type="text" class="form-control" id="searchBrand" name="brand" placeholder="Marca (opcional)">
            </div>

            <div class="col-md-3">
                <input type="text" class="form-control" id="searchMotion" name="motion" placeholder="Movimiento (opcional)">
            </div>

            <div class="col-md-3">
                <button type="submit" class="btn btn-primary w-100">Buscar</button>
            </div>
        </form>
    </div>
    <!-- Mostrar la tabla de Kardex solo si hay datos -->
    <c:if test="${!empty kardexList}">
        <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
                <th scope="col">Nombre del Producto</th>
                <th scope="col">Fecha de Registro</th>
                <th scope="col">Mes</th>
                <th scope="col">Movimiento</th>
                <th scope="col">Cantidad</th>
                <th scope="col">Costo Unitario</th>
                <th scope="col">Costo Total</th>
                <th scope="col">Acciones</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${kardexList}" var="kardex">
                <tr>
                    <td>${kardex.productName}</td>
                    <td><fmt:formatDate value="${kardex.registration_date}" pattern="dd/MM/yy" /></td>
                    <td>${kardex.month}</td>
                    <td>${kardex.motion}</td>
                    <td>${kardex.amount}</td>
                    <td>
                        S/<fmt:formatNumber value="${kardex.unit_cost}" pattern="#,##0.00" />
                    </td>
                    <td>
                        S/<fmt:formatNumber value="${kardex.total_cost}" pattern="#,##0.00" />
                    </td>
                    <td>
                        <!-- Mostrar botón de eliminar si es activo -->
                        <c:if test="${!inactive}">
                            <button type="button" class="btn btn-info" onclick="openUpdateModal(${kardex.id}, '${kardex.productName}', '${kardex.registration_date}', '${kardex.month}', '${kardex.motion}', ${kardex.amount}, ${kardex.unit_cost}, ${kardex.total_cost})">
                                Actualizar
                            </button>
                            <form action="kardex" method="post" style="display: inline;" onsubmit="confirmDeleteKardex(event)">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="id" value="${kardex.id}">
                                <button type="submit" class="btn btn-danger">Eliminar</button>
                            </form>
                        </c:if>

                        <!-- Mostrar botón de restaurar si es inactivo -->
                        <c:if test="${inactive}">
                            <form action="kardex" method="post" style="display: inline;" onsubmit="confirmRestoreKardex(event)">
                                <input type="hidden" name="action" value="recover">
                                <input type="hidden" name="id" value="${kardex.id}">
                                <button type="submit" class="btn btn-warning">Restaurar</button>
                            </form>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <!-- Si no hay datos disponibles -->
    <c:if test="${empty kardexList}">
        <div class="alert alert-danger" role="alert">
            No hay datos disponibles en el Kardex.
        </div>
    </c:if>
</div>

<!-- Modal para Crear Kardex -->
<div class="modal fade" id="createKardexModal" tabindex="-1" aria-labelledby="createKardexModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="createKardexModalLabel">Crear Nuevo Kardex</h5>
            </div>
            <div class="modal-body">
                <form id="kardexForm" action="kardex" method="post" onsubmit="return handleKardexSubmit(event)">
                    <input type="hidden" name="action" value="add">

                    <div class="mb-3">
                        <label for="productSelect" class="form-label">Seleccionar Producto</label>
                        <select class="form-select" id="productSelect" name="productSelect" required onchange="updateUnitCost()">
                            <option value="" disabled selected>Seleccione un producto</option>
                            <c:forEach items="${activeGarments}" var="garment">
                                <option value="${garment.id}" data-price="${garment.price}">${garment.name}</option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="products_id" id="products_id" value="">
                    </div>

                    <div class="mb-3">
                        <label for="registrationDate" class="form-label">Fecha de Registro</label>
                        <input type="date" class="form-control" id="registrationDate" name="registrationDate" required onchange="updateMonth()">
                    </div>

                    <div class="mb-3">
                        <label for="month" class="form-label">Mes</label>
                        <input type="text" class="form-control" id="month" name="month" required readonly>
                    </div>

                    <div class="mb-3">
                        <label for="motion" class="form-label">Movimiento</label>
                        <select class="form-select" id="motion" name="motion" required>
                            <option value="" disabled selected>Seleccione una opción</option>
                            <option value="Compra">Compra</option>
                            <option value="Venta">Venta</option>
                        </select>
                    </div>

                    <div class="mb-3">
                        <label for="amount" class="form-label">Cantidad</label>
                        <input type="number" class="form-control" id="amount" name="amount" required min="1">
                        <div class="invalid-feedback">
                            La cantidad debe ser mayor que 0.
                        </div>
                    </div>

                    <div class="mb-3">
                        <label for="unitCost" class="form-label">Costo Unitario</label>
                        <input type="number" class="form-control" id="unitCost" name="unitCost" step="0.01" readonly>
                    </div>

                    <div class="mb-3">
                        <label for="totalCost" class="form-label">Costo Total</label>
                        <input type="number" class="form-control" id="totalCost" name="totalCost" step="0.01" readonly>
                    </div>

                    <button type="submit" class="btn btn-primary">Crear Kardex</button>
                </form>
            </div>
        </div>
    </div>
</div>



<!-- Botones para alternar entre datos activos e inactivos -->
<div class="text-center mt-3">
    <c:if test="${!inactive}">
        <a href="garments" class="btn btn-secondary">Volver a Prendas</a>
        <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#createKardexModal">
            Crear Nuevo Kardex
        </button>
        <a href="kardex?action=sortByProductName" class="btn btn-secondary">Ordenar por Nombre de Producto (A-Z)</a>
        <a href="kardex?action=download&format=pdf" class="btn btn-danger">Descargar PDF</a>
        <a href="kardex?action=download&format=xls" class="btn btn-warning">Descargar XLS</a>
        <a href="kardex?action=download&format=csv" class="btn btn-success">Descargar CSV</a>
        <button type="button" class="btn btn-warning" onclick="location.href='kardex?action=inactive'">Ver datos Inactivos</button>
    </c:if>
    <c:if test="${inactive}">
        <button type="button" class="btn btn-success" onclick="location.href='kardex'">Ver datos Activos</button>
    </c:if>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
        crossorigin="anonymous">
</script>

<script>
    function confirmDeleteKardex(event) {
        event.preventDefault();
        Swal.fire({
            title: '¿Estás seguro?',
            text: 'Este dato será eliminado',
            icon: 'warning',
            showCancelButton: true,
            confirmButtonText: 'Sí, eliminar',
            cancelButtonText: 'Cancelar'
        }).then((result) => {
            if (result.isConfirmed) {
                const form = event.target;
                Swal.fire({
                    title: 'Eliminado!',
                    text: 'El dato ha sido eliminado correctamente.',
                    icon: 'success'
                }).then(() => {
                    form.submit();
                });
            }
        });
    }

    function confirmRestoreKardex(event) {
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
                    form.submit();
                });
            }
        });
    }

    function handleKardexSubmit(event) {
        event.preventDefault(); // Evita el envío automático del formulario

        const form = event.target; // Obtiene el formulario

        Swal.fire({
            title: '¡Kardex creado!',
            text: 'El Kardex ha sido creado correctamente.',
            icon: 'success',
            confirmButtonText: 'Aceptar'
        }).then((result) => {
            if (result.isConfirmed) {
                form.submit(); // Envía el formulario después de mostrar el mensaje
            }
        });
    }

    function updateMonth() {
        const registrationDate = document.getElementById('registrationDate').value;
        const monthField = document.getElementById('month');

        if (registrationDate) {
            const date = new Date(registrationDate);
            const options = { month: 'long' }; // Formato de mes largo
            monthField.value = date.toLocaleDateString('es-ES', options); // Cambia 'es-ES' según el idioma deseado
        } else {
            monthField.value = '';
        }
    }

    document.getElementById('amount').addEventListener('input', calculateTotal);
    document.getElementById('unitCost').addEventListener('input', calculateTotal);

    function calculateTotal() {
        const amount = document.getElementById('amount').value;
        const unitCost = document.getElementById('unitCost').value;
        const totalCost = document.getElementById('totalCost');

        if (amount && unitCost) {
            totalCost.value = (amount * unitCost).toFixed(2);
        } else {
            totalCost.value = '';
        }
    }

    function updateUnitCost() {
        const productSelect = document.getElementById('productSelect');
        const unitCostField = document.getElementById('unitCost');
        const productsIdField = document.getElementById('products_id');

        const selectedOption = productSelect.options[productSelect.selectedIndex];
        const price = selectedOption.getAttribute('data-price');

        unitCostField.value = price;
        productsIdField.value = selectedOption.value; // Establecer el ID del producto seleccionado
    }
</script>
</body>
</html>