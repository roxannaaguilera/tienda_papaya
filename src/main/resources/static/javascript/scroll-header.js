// ============================================
// SCRIPT PARA CAMBIAR EL HEADER AL HACER SCROLL
// ============================================

(function() {
    'use strict';
    
    const header = document.querySelector('.header-moderno');
    
    if (!header) {
        console.warn('No se encontró el header con clase .header-moderno');
        return;
    }
    
    // Función para manejar el scroll
    function handleScroll() {
        if (window.scrollY > 50) {
            header.classList.add('scrolled');
        } else {
            header.classList.remove('scrolled');
        }
    }
    
    // Event listener para el scroll
    window.addEventListener('scroll', handleScroll);
    
    // Ejecutar una vez al cargar para estado inicial
    handleScroll();
    
    // ==========================================
    // FUNCIÓN PARA CAMBIAR MONEDA
    // ==========================================
    
    window.cambiarMoneda = function(moneda) {
        console.log('Moneda cambiada a: ' + moneda);
        
        // Guardar en localStorage
        localStorage.setItem('moneda_preferida', moneda);
        
        // Actualizar símbolo de moneda en la página
        const simbolos = {
            'USD': '$',
            'EUR': '€',
            'MXN': '$',
            'ARS': '$'
        };
        
        const simbolo = simbolos[moneda] || '$';
        
        // Mostrar notificación
        mostrarNotificacion(`Moneda cambiada a ${moneda} ${simbolo}`);
        
        // Aquí puedes agregar lógica para actualizar precios
        actualizarPrecios(moneda);
    };
    
    // ==========================================
    // FUNCIÓN PARA ACTUALIZAR PRECIOS
    // ==========================================
    
    function actualizarPrecios(moneda) {
        // Tasas de cambio (esto debería venir de una API en producción)
        const tasas = {
            'USD': 1,
            'EUR': 0.85,
            'MXN': 20,
            'ARS': 350
        };
        
        // Seleccionar todos los elementos de precio
        const precios = document.querySelectorAll('.precio');
        
        precios.forEach(function(elemento) {
            const precioBase = parseFloat(elemento.dataset.precioBase || elemento.textContent);
            const nuevoPrecio = (precioBase * tasas[moneda]).toFixed(2);
            elemento.textContent = nuevoPrecio;
        });
    }
    
    // ==========================================
    // SISTEMA DE NOTIFICACIONES
    // ==========================================
    
    function mostrarNotificacion(mensaje, tipo = 'info') {
        // Crear elemento de notificación
        const notificacion = document.createElement('div');
        notificacion.className = `notificacion notificacion-${tipo}`;
        notificacion.textContent = mensaje;
        
        // Estilos inline (puedes moverlos a CSS)
        notificacion.style.cssText = `
            position: fixed;
            top: 100px;
            right: 20px;
            background: #fed455;
            color: #2d2d2d;
            padding: 15px 25px;
            border-radius: 50px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2);
            z-index: 10000;
            animation: slideInRight 0.3s ease;
            font-weight: 600;
        `;
        
        // Agregar al body
        document.body.appendChild(notificacion);
        
        // Remover después de 3 segundos
        setTimeout(function() {
            notificacion.style.animation = 'slideOutRight 0.3s ease';
            setTimeout(function() {
                notificacion.remove();
            }, 300);
        }, 3000);
    }
    
    // Agregar animaciones CSS dinámicamente
    if (!document.getElementById('notificacion-styles')) {
        const style = document.createElement('style');
        style.id = 'notificacion-styles';
        style.textContent = `
            @keyframes slideInRight {
                from {
                    transform: translateX(400px);
                    opacity: 0;
                }
                to {
                    transform: translateX(0);
                    opacity: 1;
                }
            }
            
            @keyframes slideOutRight {
                from {
                    transform: translateX(0);
                    opacity: 1;
                }
                to {
                    transform: translateX(400px);
                    opacity: 0;
                }
            }
        `;
        document.head.appendChild(style);
    }
    
    // ==========================================
    // CARGAR MONEDA GUARDADA
    // ==========================================
    
    const monedaGuardada = localStorage.getItem('moneda_preferida');
    if (monedaGuardada) {
        actualizarPrecios(monedaGuardada);
    }
    
    console.log('✅ Script de scroll del header cargado correctamente');
    
})();

// ==========================================
// FUNCIÓN PARA SCROLL DE PRODUCTOS (GLOBAL)
// ==========================================

window.scrollProductos = function(direction) {
    // Buscar el contenedor de productos más cercano al botón que se clickeó
    const container = event.target.closest('.container-productos-horizontal, .seccion-productos-horizontal')
                                 .querySelector('.productos-scroll-horizontal');
    
    if (!container) {
        console.warn('No se encontró el contenedor de productos');
        return;
    }
    
    const scrollAmount = 440; // 2 tarjetas (220px cada una)
    
    if (direction === 'left' || direction === -1) {
        container.scrollBy({ left: -scrollAmount, behavior: 'smooth' });
    } else if (direction === 'right' || direction === 1) {
        container.scrollBy({ left: scrollAmount, behavior: 'smooth' });
    }
};

// ==========================================
// INICIALIZAR SCROLL DE PRODUCTOS
// ==========================================

function inicializarScrollProductos() {
    // Seleccionar todos los contenedores de productos
    const contenedores = document.querySelectorAll('.productos-scroll-horizontal');
    
    contenedores.forEach(function(contenedor) {
        // Agregar event listeners para arrastre con mouse (opcional)
        let isDown = false;
        let startX;
        let scrollLeft;
        
        contenedor.addEventListener('mousedown', function(e) {
            isDown = true;
            contenedor.classList.add('active');
            startX = e.pageX - contenedor.offsetLeft;
            scrollLeft = contenedor.scrollLeft;
        });
        
        contenedor.addEventListener('mouseleave', function() {
            isDown = false;
            contenedor.classList.remove('active');
        });
        
        contenedor.addEventListener('mouseup', function() {
            isDown = false;
            contenedor.classList.remove('active');
        });
        
        contenedor.addEventListener('mousemove', function(e) {
            if (!isDown) return;
            e.preventDefault();
            const x = e.pageX - contenedor.offsetLeft;
            const walk = (x - startX) * 2; // Velocidad del scroll
            contenedor.scrollLeft = scrollLeft - walk;
        });
    });
    
    // Mostrar/ocultar botones según el scroll
    contenedores.forEach(function(contenedor) {
        const container = contenedor.closest('.container-productos-horizontal, .seccion-productos-horizontal');
        const btnLeft = container.querySelector('.scroll-btn-left');
        const btnRight = container.querySelector('.scroll-btn-right');
        
        function updateButtons() {
            if (btnLeft && btnRight) {
                // Ocultar botón izquierdo si está al inicio
                if (contenedor.scrollLeft <= 0) {
                    btnLeft.style.opacity = '0';
                    btnLeft.style.pointerEvents = 'none';
                } else {
                    btnLeft.style.opacity = '1';
                    btnLeft.style.pointerEvents = 'auto';
                }
                
                // Ocultar botón derecho si está al final
                if (contenedor.scrollLeft + contenedor.clientWidth >= contenedor.scrollWidth - 10) {
                    btnRight.style.opacity = '0';
                    btnRight.style.pointerEvents = 'none';
                } else {
                    btnRight.style.opacity = '1';
                    btnRight.style.pointerEvents = 'auto';
                }
            }
        }
        
        contenedor.addEventListener('scroll', updateButtons);
        updateButtons(); // Ejecutar al cargar
    });
}

// Inicializar cuando el DOM esté listo
if (document.readyState === 'loading') {
    document.addEventListener('DOMContentLoaded', inicializarScrollProductos);
} else {
    inicializarScrollProductos();
}

console.log('✅ Funcionalidad de scroll de productos cargada correctamente');