package com.fresco;

import java.util.ArrayList;
import java.util.List;

public class ManejoExcepcionesSilenciosas {

	private record Cliente(int id, String nombre) {
	};

	public class NoEncontradoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public NoEncontradoException(String msg) {
			super(msg);
		}
	}

	public class IdIncorrectoException extends RuntimeException {
		private static final long serialVersionUID = 1L;

		public IdIncorrectoException(String msg) {
			super(msg);
		}
	}

	public void actualizarRegistro(List<Cliente> listaClientes, Cliente cliente) {
		if (cliente.id > 100) {
			throw new IdIncorrectoException("El id del cliente es incorrecto");
		}
		if (listaClientes.indexOf(cliente) == -1) {
			throw new NoEncontradoException("El id del cliente no existe");
		}
	}

	public static void main(String[] args) {
		var app = new ManejoExcepcionesSilenciosas();
		var listaClientes = new ArrayList<Cliente>();
		listaClientes.add(new Cliente(1, "Cliente 1"));
		listaClientes.add(new Cliente(2, "Cliente 2"));

		app.actualizarRegistro(listaClientes, new Cliente(200, "Cliente 200"));

		try {
			app.actualizarRegistro(listaClientes, new Cliente(10, "Cliente 10"));			
		} catch (NoEncontradoException | IdIncorrectoException e) {
			e.printStackTrace();
		}
	}
}


