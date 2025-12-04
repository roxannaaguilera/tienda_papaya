//funciones generales:


function checkout_paso_4(json){
    let html = Mustache.render(plantilla_checkout_4, json);
    $("#contenedor").html(html);
    
    // ============================================
    // CALCULAR TOTAL DEL PEDIDO
    // ============================================
    
    // 1. Calcular subtotal de productos
    let subtotalProductos = 0;
    $(".precio-producto").each(function(){
        let precio = parseFloat($(this).attr("data-precio"));
        if (!isNaN(precio)) {
            subtotalProductos += precio;
        }
    });
    
    // 2. Obtener costo de envío
    let costoEnvioStr = $("#costo_envio_display").attr("data-costo");
    let costoEnvio = parseFloat(costoEnvioStr) || 0;
    
    // 3. Calcular total
    let total = subtotalProductos + costoEnvio;
    
    // 4. Mostrar los valores calculados
    $("#subtotal_productos").text(subtotalProductos.toFixed(2) + " €");
    $("#subtotal_envio").text(costoEnvio.toFixed(2) + " €");
    $("#total_pedido").text(total.toFixed(2) + " €");
    
    console.log("Cálculo del pedido:", {
        subtotal: subtotalProductos,
        envio: costoEnvio,
        total: total
    });
    
    // ============================================
    // CONFIRMAR PEDIDO
    // ============================================
    $("#boton_confirmar_pedido").click(function(){
        // Opcional: Deshabilitar botón mientras procesa
        $(this).prop('disabled', true).text('PROCESANDO...');
        
        $.post("pedidosREST/paso4").done(function(res){
            alert(res);
            if(res == "ok"){
                alert("¡Pedido confirmado con éxito! Total: " + total.toFixed(2) + " €");
                obtenerAlimentos();
            } else {
                // Re-habilitar el botón si hubo error
                $("#boton_confirmar_pedido").prop('disabled', false).html(`
                    <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                        <polyline points="20 6 9 17 4 12"></polyline>
                    </svg>
                    CONFIRMAR PEDIDO
                `);
            }
        }).fail(function(){
            alert("Error al procesar el pedido");
            $("#boton_confirmar_pedido").prop('disabled', false).html(`
                <svg width="22" height="22" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2.5">
                    <polyline points="20 6 9 17 4 12"></polyline>
                </svg>
                CONFIRMAR PEDIDO
            `);
        });
    });
}
// ============================================
// VALIDACIÓN DE TARJETAS EN TIEMPO REAL
// ============================================

// Algoritmo de Luhn para validar números de tarjeta
function validarNumeroTarjeta(numero) {
    numero = numero.replace(/[\s-]/g, '');
    
    if (!/^\d+$/.test(numero)) {
        return false;
    }
    
    if (numero.length < 13 || numero.length > 19) {
        return false;
    }
    
    let suma = 0;
    let alternar = false;
    
    for (let i = numero.length - 1; i >= 0; i--) {
        let digito = parseInt(numero.charAt(i), 10);
        
        if (alternar) {
            digito *= 2;
            if (digito > 9) {
                digito -= 9;
            }
        }
        
        suma += digito;
        alternar = !alternar;
    }
    
    return (suma % 10 === 0);
}

// Identificar el tipo de tarjeta
function identificarTipoTarjeta(numero) {
    numero = numero.replace(/[\s-]/g, '');
    
    if (/^4/.test(numero)) {
        return { tipo: 'visa', nombre: 'VISA' };
    }
    
    if (/^5[1-5]/.test(numero) || /^2(22[1-9]|2[3-9]|[3-6]|7[01]|720)/.test(numero)) {
        return { tipo: 'mastercard', nombre: 'MasterCard' };
    }
    
    if (/^3[47]/.test(numero)) {
        return { tipo: 'amex', nombre: 'American Express' };
    }
    
    if (/^(6011|65|64[4-9]|622)/.test(numero)) {
        return { tipo: 'discover', nombre: 'Discover' };
    }
    
    return null;
}

// Obtener el icono SVG de la tarjeta
function obtenerIconoTarjeta(tipo) {
    const iconos = {
        visa: `<svg viewBox="0 0 48 32" width="32" height="20" style="display: block;">
            <rect width="48" height="32" rx="4" fill="#1434CB"/>
            <text x="24" y="20" fill="white" font-size="10" font-weight="bold" text-anchor="middle" font-family="Arial">VISA</text>
        </svg>`,
        
        mastercard: `<svg viewBox="0 0 48 32" width="32" height="20" style="display: block;">
            <rect width="48" height="32" rx="4" fill="#EB001B"/>
            <circle cx="18" cy="16" r="10" fill="#FF5F00" opacity="0.8"/>
            <circle cx="30" cy="16" r="10" fill="#F79E1B" opacity="0.8"/>
        </svg>`,
        
        amex: `<svg viewBox="0 0 48 32" width="32" height="20" style="display: block;">
            <rect width="48" height="32" rx="4" fill="#006FCF"/>
            <text x="24" y="20" fill="white" font-size="8" font-weight="bold" text-anchor="middle" font-family="Arial">AMEX</text>
        </svg>`,
        
        discover: `<svg viewBox="0 0 48 32" width="32" height="20" style="display: block;">
            <rect width="48" height="32" rx="4" fill="#FF6000"/>
            <circle cx="40" cy="16" r="8" fill="#F79E1B"/>
        </svg>`
    };
    
    return iconos[tipo] || '';
}

// Formatear número de tarjeta (espacios cada 4 dígitos)
function formatearNumeroTarjeta(valor) {
    valor = valor.replace(/\s/g, '');
    let formateo = '';
    
    for (let i = 0; i < valor.length; i++) {
        if (i > 0 && i % 4 === 0) {
            formateo += ' ';
        }
        formateo += valor[i];
    }
    
    return formateo;
}

// Formatear fecha de expiración (MM/AA)
function formatearFechaExpiracion(valor) {
    valor = valor.replace(/\D/g, '');
    
    if (valor.length >= 2) {
        return valor.slice(0, 2) + '/' + valor.slice(2, 4);
    }
    
    return valor;
}

// ============================================
// CHECKOUT PASO 3 CON VALIDACIÓN EN TIEMPO REAL
// ============================================

function checkout_paso_3(){
    $("#contenedor").html(plantilla_checkout_3);
    
    // ============================================
    // VALIDACIÓN EN TIEMPO REAL DEL NÚMERO DE TARJETA
    // ============================================
    $("#numero_tarjeta").on('input', function(){
        let valor = $(this).val();
        
        // Solo permitir números y espacios
        valor = valor.replace(/[^\d\s]/g, '');
        
        // Formatear con espacios
        valor = formatearNumeroTarjeta(valor);
        $(this).val(valor);
        
        // Limpiar espacios para validación
        let numeroLimpio = valor.replace(/\s/g, '');
        
        // Detectar tipo de tarjeta
        let tipoInfo = identificarTipoTarjeta(numeroLimpio);
        
        if (tipoInfo) {
            // Mostrar tipo detectado
            $("#tipo_detectado").text(tipoInfo.nombre).show();
            $("#tipo_detectado").css({
                'background': '#e3f2fd',
                'color': '#1976d2'
            });
            
            // Guardar tipo en campo oculto
            $("#tipo_tarjeta").val(tipoInfo.tipo);
            
            // Mostrar icono
            $("#icono_tarjeta").html(obtenerIconoTarjeta(tipoInfo.tipo)).show();
        } else {
            $("#tipo_detectado").hide();
            $("#tipo_tarjeta").val('');
            $("#icono_tarjeta").hide();
        }
        
        // Validar con algoritmo de Luhn cuando tenga suficientes dígitos
        if (numeroLimpio.length >= 13) {
            let esValido = validarNumeroTarjeta(numeroLimpio);
            
            if (esValido) {
                $(this).css('border-color', '#4caf50');
                $("#mensaje_validacion").text('✓ Número de tarjeta válido').css('color', '#4caf50');
            } else {
                $(this).css('border-color', '#f44336');
                $("#mensaje_validacion").text('✗ Número de tarjeta no válido').css('color', '#f44336');
            }
        } else if (numeroLimpio.length > 0) {
            $(this).css('border-color', '#e0e0e0');
            $("#mensaje_validacion").text('Introduce entre 13 y 19 dígitos').css('color', '#666');
        } else {
            $(this).css('border-color', '#e0e0e0');
            $("#mensaje_validacion").text('');
        }
    });
    
    // ============================================
    // FORMATEO AUTOMÁTICO DE FECHA
    // ============================================
    $("#fecha_expiracion").on('input', function(){
        let valor = $(this).val();
        valor = formatearFechaExpiracion(valor);
        $(this).val(valor);
    });
    
    // ============================================
    // SOLO NÚMEROS EN CVV
    // ============================================
    $("#cvv").on('input', function(){
        let valor = $(this).val();
        valor = valor.replace(/\D/g, '');
        $(this).val(valor);
    });
    
    // ============================================
    // VALIDACIÓN AL ENVIAR EL FORMULARIO
    // ============================================
    $("#aceptar_paso_3").submit(function(e){
        e.preventDefault();
        
        let tipo_tarjeta = $("#tipo_tarjeta").val();
        let numero = $("#numero_tarjeta").val();
        let titular = $("#titular_tarjeta").val();
        let fecha = $("#fecha_expiracion").val();
        let cvv = $("#cvv").val();
        
        // Validar número de tarjeta con Luhn
        let numeroLimpio = numero.replace(/\s/g, '');
        if (!validarNumeroTarjeta(numeroLimpio)) {
            alert("❌ El número de tarjeta no es válido. Por favor verifica.");
            $("#numero_tarjeta").focus();
            return false;
        }
        
        // Validar que se haya detectado el tipo
        if (!tipo_tarjeta) {
            alert("❌ No se pudo identificar el tipo de tarjeta.");
            $("#numero_tarjeta").focus();
            return false;
        }
        
        // Validar titular (mínimo 3 caracteres)
        if (titular.length < 3) {
            alert("❌ El nombre del titular debe tener al menos 3 caracteres.");
            $("#titular_tarjeta").focus();
            return false;
        }
        
        // Validar formato de fecha MM/AA
        if (!/^\d{2}\/\d{2}$/.test(fecha)) {
            alert("❌ Formato de fecha inválido. Use MM/AA");
            $("#fecha_expiracion").focus();
            return false;
        }
        
        // Validar CVV
        if (cvv.length < 3 || cvv.length > 4) {
            alert("❌ El CVV debe tener 3 o 4 dígitos.");
            $("#cvv").focus();
            return false;
        }
        
        console.log("✅ Datos de pago válidos:", {
            tipo: tipo_tarjeta,
            numero: numeroLimpio,
            titular: titular,
            fecha: fecha,
            cvv: cvv
        });
        
        // Enviar al backend
        $.post("pedidosREST/paso3", {
            tarjeta: tipo_tarjeta,
            numero: numeroLimpio, 
            titular: titular,
            fecha_expiracion: fecha,
            cvv: cvv
        }).done(function(res){
            console.log("Respuesta paso 3:", res);
            checkout_paso_4(res);
        }).fail(function(error){
            console.error("Error en paso 3:", error);
            alert("❌ Error al procesar el pago");
        });
        
        return false;
    });
}
function checkout_paso_2() {
    // Mostrar plantilla del paso 2
    $("#contenedor").html(plantilla_checkout_2);
    
    // Interceptar el submit del formulario
    $("#aceptar_paso_2").submit(function(e) {
        e.preventDefault(); // Prevenir comportamiento por defecto
        
        let metodo = $("#metodo_envio").val();
        
        // Validar que se haya seleccionado un método
        if (!metodo || metodo === "" || metodo === "0") {
            alert("Selecciona un método de envío");
            return false;
        }
        
        // Enviar al backend
        $.post("pedidosREST/paso2", {
            metodo_envio: metodo
        }).done(function(res) {
            console.log("Respuesta paso 2:", res);
            alert("Método de envío guardado correctamente");
            
            // Continuar al paso 3
            checkout_paso_3();
        }).fail(function(error) {
            console.error("Error en paso 2:", error);
            alert("Error al guardar el método de envío");
        });
        
        return false; // Prevenir submit
    });
}
function checkout_paso_1(){
	nombre = $("#campo_nombre").val()
	apellido = $("#campo_apellido").val()
	direccion = $("#campo_direccion").val()
	provincia = $("#campo_provincia").val()
	email = $("#campo_email").val()
	telefono = $("#campo_telefono").val()
	alert("mandar la info insertada a pedidosREST")
	$.post("pedidosREST/paso1",{
		nombre: nombre,
		apellidos: apellido,
		direccion: direccion,
		provincia: provincia,
		email: email,
		telefono: telefono
	}).done(function(res){
		alert(res)
		if(res == "ok"){
			alert("mostrar plantilla checkout2")
			checkout_paso_2()
		}
	})			
}//end checkout_paso_1
function checkout_paso_0(){
	// Cerrar el modal del carrito primero
	$('#modal_carrito').removeClass('show');
	
	// Agregar clase al body para ocultar hero
	$('body').addClass('checkout-activo');
	
	// Esperar a que se cierre y luego mostrar el checkout
	setTimeout(function(){
		$("#contenedor").html(plantilla_checkout_1);
		$("#aceptar_paso_1").submit(function(e){
			e.preventDefault();
			checkout_paso_1();
		});
	}, 350);
}//end checkout_paso_0

function mostrarCarrito(){
	if(nombre_login == ""){
		alert("tienes que identificarte para comprar productos")
	}else{
		$.get("carritoREST/obtener", function(r){
			if(r.length == 0){
				alert("aun no has agregado nada al carrito")
			}else{
				let html = Mustache.render(plantilla_carrito, r )
				$("#contenedor").html(html)	
				
				// MODIFICAR ESTA LÍNEA - usar función anónima
				$("#realizar_pedido").click(function(){
					checkout_paso_0();
				});
				
				$(".enlace-borrar-producto-carrito").click(function(){	
					if ( ! confirm("¿estas seguro?")){
						return
					}				
				 	let idAlimento = $(this).attr("id-libro")	
					alert("mandar el id de libro: " + idAlimento+ " a carritoREST para "+
						"que lo borre del carrito")
					$.post("carritoREST/eliminar",{
						id: idAlimento
					}).done(function(res){
						alert(res)
						if(res == "ok"){
							actualizarContadorCarrito();
							mostrarCarrito()
						}
					})
				})
			}						
		})
	}
}


function comprar_producto(){
	if(nombre_login == ""){
		alert("tienes que identificarte para comprar productos")
	}else{
		var id_producto = $(this).attr("data-producto-id")
		alert("añadir producto de id: " + id_producto + " al carrito")
		//invocar a una operacion de CarritoREST para agregar 
		//el producto al carrito
		$.post("carritoREST/agregarProducto",{
			id: id_producto,
			cantidad: 1
		}).done(function(res){
			alert(res)
			
			actualizarContadorCarrito();
		})	
	}
}//end comprar_producto
	
function obtenerAlimentos(){
	
	$('body').removeClass('checkout-activo');
		
	$.get("alimentosREST/obtener", function(r){
		//codigo a ejecutar cuando reciba la respuesta del recurso indicado
		//alert("recibido: " + r );
		var alimentos = r //JSON.parse(r)
		console.log(alimentos)
		var info = Mustache.render( 
				plantilla_alimentos , { alimentos : "hola desde mustache", array_alimentos: alimentos } ) 
		$("#contenedor").html(info)
		$(".enlace_comprar_producto").click(comprar_producto)		
	})//end $.get
	$("#contenedor").html("cargando...");
}//end obtenerAlimentos

function mostrarLogin(){
	$("#contenedor").html(plantilla_login)
	$("#form_login").submit(function(e){
		e.preventDefault()
		var email = $("#email").val()
		var pass = $("#pass").val()
		        
		$.post("usuariosREST/login",{
			email: email,
			pass: pass
		}).done(function(res){
			var parte1 = res.split(",")[0]
			var parte2 = res.split(",")[1]
			if (parte1 == "ok"){
				alert("bienvenido " + parte2 + " ya puedes comprar")
				nombre_login = parte2
				
				
				$("#enlace_identificarme span").text( parte2)
				
				// Cerrar el modal
				$('#modal_login').removeClass('show');
				
				actualizarContadorCarrito();
				
				setTimeout(function(){
					obtenerAlimentos()
				}, 350);
				
				
			}else{
				alert(res)
			}
		})//end done		
	})//end submit
}//end mostrarLogin
function mostrarRegistro(){
	$("#contenedor").html(plantilla_registro)
	//vamos a interceptar el envio de formulario
	$("#form_registro").submit(function(e){
		e.preventDefault()
		//alert("se intenta enviar form")
		//recoger los datos del form y mandarselos a UsuariosREST
		var nombre = $("#nombre").val()
		var apellidos = $("#apellidos").val()
		var email = $("#email").val()
		var pass = $("#pass").val()
		var telefono = $("#telefono").val()
		var direccion = $("#direccion").val()
		$.post("usuariosREST/registrar",{
			nombre: nombre, 
			apellidos: apellidos, 
			email: email,
			pass: pass,
			telefono: telefono,
			direccion: direccion
		}).done(function(res){
			alert(res)
		})//end done
		obtenerAlimentos()
	})//end submit form
}//end mostrarRegistro

// Función para actualizar el contador del carrito
function actualizarContadorCarrito(){
	$.get("carritoREST/obtener", function(r){
		var cantidad = r.length;
		$(".badge").text(cantidad);
		
		// Mostrar/ocultar badge según cantidad
		if(cantidad > 0){
			$(".badge").show();
		} else {
			$(".badge").hide();
		}
	});
}


  function usarUbicacionActual() {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition(function(position) {
        const lat = position.coords.latitude;
        const lng = position.coords.longitude;

        // Call Google Geocoding API
        fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${lat},${lng}&key=AIzaSyBxiVv3t15eEk7i3hYWEyj9QWEvJkxDAm4`)
          .then(response => response.json())
          .then(data => {
            if (data.results && data.results.length > 0) {
              const address = data.results[0].formatted_address;
              document.getElementById("input-direccion").value = address;

              // Show location on map
              mostrarMapa(lat, lng, address);
            } else {
              alert("No se pudo obtener la dirección.");
            }
          })
          .catch(err => console.error(err));
      }, function(error) {
        alert("Error obteniendo ubicación: " + error.message);
      });
    } else {
      alert("Geolocalización no soportada en este navegador.");
    }
  }
  
  function mostrarMapa(lat, lng, address) {
      const location = { lat: lat, lng: lng };
      const map = new google.maps.Map(document.getElementById("map"), {
        zoom: 15,
        center: location
      });
      new google.maps.Marker({
        position: location,
        map: map,
        title: address
      });
    }
	
	function buscarDireccion() {
	  const address = document.getElementById("input-direccion").value;
	  fetch(`https://maps.googleapis.com/maps/api/geocode/json?address=${encodeURIComponent(address)}&key=AIzaSyBxiVv3t15eEk7i3hYWEyj9QWEvJkxDAm4`)
	    .then(res => res.json())
	    .then(data => {
	      if (data.results && data.results.length > 0) {
	        const loc = data.results[0].geometry.location;
	        mostrarMapa(loc.lat, loc.lng, address);
	      }
	    });
	}
	
	function initAutocomplete() {
	      const input = document.getElementById("input-direccion");
	      const autocomplete = new google.maps.places.Autocomplete(input, {
	        types: ['geocode'],
	        componentRestrictions: { country: "es" } // opcional: restringir a España
	      });

	      autocomplete.addListener("place_changed", function() {
	        const place = autocomplete.getPlace();
	        if (place.geometry) {
	          const lat = place.geometry.location.lat();
	          const lng = place.geometry.location.lng();
	          mostrarMapa(lat, lng, place.formatted_address);
	          enviarAlBackend(lat, lng, place.formatted_address);
	        }
	      });
	    }
		
	function enviarAlBackend(lat, lng, address) {
	      fetch('/session/location', {
	        method: 'POST',
	        headers: { 'Content-Type': 'application/json' },
	        body: JSON.stringify({ lat, lng, address })
	      });
	    }	

//atencion a eventos:

$("#enlace_productos").click(obtenerAlimentos)
$("#enlace_identificarme").click(mostrarLogin)
$("#enlace_registrarme").click(mostrarRegistro)
$("#enlace_carrito").click(mostrarCarrito)
