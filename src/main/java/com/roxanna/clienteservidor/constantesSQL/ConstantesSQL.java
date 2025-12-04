package com.roxanna.clienteservidor.constantesSQL;

public class ConstantesSQL {
	
	public static final String SQL_OBTENER_ALIMENTOS_PARA_JSON = 
			"select a.id, a.nombre, a.descripcion, a.precio, a.cantidad, a.peso, a.categoria, r.nombre as nombre_rubro from tabla_alimentos as a, rubro as r  where a.rubro_id= r.id order by a.id desc";
	 
	public static final String SQL_OBTENER_PRODUCTOS_CARRITO = 
			"SELECT "
			+ "C.USUARIO_ID, TA.NOMBRE, TA.ID AS ALIMENTO_ID, TA.PRECIO, TA.DESCRIPCION, TA.CANTIDAD, TA.PESO, TA.CATEGORIA "
			+ "FROM CARRITO AS C, TABLA_ALIMENTOS AS TA "
			+ "WHERE "
			+ "USUARIO_ID = :usuario_id AND TA.ID= C.ALIMENTO_ID ";

	public static final String SQL_ELIMINAR_PRODUCTO_CARRITO=
			"DELETE FROM CARRITO WHERE ALIMENTO_ID = :alimento_id AND USUARIO_ID = :usuario_id";
	
	public static final String SQL_VACIAR_CARRITO = 
			"DELETE FROM CARRITO WHERE USUARIO_ID = :usuario_id";
			
}






