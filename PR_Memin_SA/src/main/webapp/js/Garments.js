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
    // Formatear el precio a S/30,00
    let formattedPrice = formatPrice(price);

    // Establecer los valores del formulario en el modal
    document.getElementById("createGarmentForm").action = "garments"; // Asegurarse de que la acción sea para actualizar
    document.getElementById("createGarmentForm").elements["action"].value = "update";
    document.getElementById("createGarmentForm").elements["id"].value = id;
    document.getElementById("code").value = code;
    document.getElementById("name").value = name;
    document.getElementById("brand").value = brand;
    document.getElementById("category").value = category;
    document.getElementById("price").value = formattedPrice;

    // Cambiar el título del modal a "Actualizar Prenda"
    document.getElementById("createGarmentModalLabel").textContent = "Actualizar Prenda";

    // Cambiar el texto del botón a "Actualizar"
    document.getElementById("createGarmentButton").textContent = "Actualizar";
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

function formatPrice(price) {
    // Convertir el precio de cadena a número y formatearlo como S/30,00
    let number = parseFloat(price).toFixed(2); // Asegura que tenga dos decimales
    let [integerPart, decimalPart] = number.split('.');

    // Formatear con coma como separador de decimales
    return `${integerPart}.${decimalPart}`;
}