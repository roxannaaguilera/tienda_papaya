// ============================================
// FUNCIONALIDAD DE MODALES LOGIN Y REGISTRO
// ============================================

(function() {
    'use strict';

    // Función para cerrar modal clickeando fuera
    window.addEventListener('click', function(event) {
        const modalLogin = document.getElementById('modal_login');
        const modalRegistro = document.getElementById('modal_registro');
        const modalCarrito = document.getElementById('modal_carrito');

        if (event.target === modalLogin) {
            $('#modal_login').removeClass('show');
            setTimeout(function(){
                $("#contenedor").html("");
            }, 300);
        }
        if (event.target === modalRegistro) {
            $('#modal_registro').removeClass('show');
            setTimeout(function(){
                $("#contenedor").html("");
            }, 300);
        }
        if (event.target === modalCarrito) {
            $('#modal_carrito').removeClass('show');
            setTimeout(function(){
                obtenerAlimentos();
            }, 350);
        }
    });

	// Cerrar modal con tecla ESC
	document.addEventListener('keydown', function(event) {
	    if (event.key === 'Escape') {
	        const modalCarrito = document.getElementById('modal_carrito');
	        
	        // Si el modal del carrito existe y está visible
	        if (modalCarrito && modalCarrito.classList.contains('show')) {
	            $('#modal_carrito').removeClass('show');
	            
	            // AGREGAR ESTA LÍNEA para quitar el focus
	            document.activeElement.blur();
	            
	            setTimeout(function(){
	                obtenerAlimentos();
	            }, 350);
	        } else {
	            // Para otros modales
	            $('.modal').removeClass('show');
	            
	            // AGREGAR ESTA LÍNEA para quitar el focus
	            document.activeElement.blur();
	            
	            setTimeout(function(){
	                $("#contenedor").html("");
	            }, 300);
	        }
	    }
	});

    // ============================================
    // EVENT LISTENERS PARA BOTONES DE CERRAR (X)
    // ============================================

    // Cerrar modal Login con la X
    $(document).on('click', '#close_login', function() {
        $('#modal_login').removeClass('show');
        setTimeout(function(){
            $("#contenedor").html("");
        }, 300);
    });

    // Cerrar modal Registro con la X
    $(document).on('click', '#close_registro', function() {
        $('#modal_registro').removeClass('show');
        setTimeout(function(){
            $("#contenedor").html("");
        }, 300);
    });

    // Cerrar modal Carrito con la X
    $(document).on('click', '#close_carrito', function() {
        $('#modal_carrito').removeClass('show');
        setTimeout(function(){
            obtenerAlimentos();
        }, 350);
    });

    // ============================================
    // NAVEGACIÓN ENTRE MODALES
    // ============================================

    // Abrir modal de Registro desde el link en Login
    $(document).on('click', '#link_abrir_registro', function(e) {
        e.preventDefault();
        $('#modal_login').removeClass('show');
        setTimeout(function(){
            mostrarRegistro();
        }, 300);
    });

    // Abrir modal de Login desde el link en Registro
    $(document).on('click', '#link_abrir_login', function(e) {
        e.preventDefault();
        $('#modal_registro').removeClass('show');
        setTimeout(function(){
            mostrarLogin();
        }, 300);
    });

    console.log('✅ Sistema de modales cargado correctamente');

})();