package com.lonewolf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import com.lonewolf.dto.Contactos;
import com.lonewolf.jdbc.ConectaDb;
import com.lonewolf.service.ServiceContactos;

public class ServiceContactosImpl implements ServiceContactos {
	
	private final ConectaDb db;
	private String mensaje;
	
	public ServiceContactosImpl() {
		this.db = new ConectaDb();
	}

	@Override
	public List<Contactos> contactosQry() {
		List<Contactos> lista = null;
		
		String sql = "SELECT id, nombres, celular, correo FROM contactos";
		
		Connection cn = db.getConnection();
		
		if (cn != null) {
			try {
				PreparedStatement ps = cn.prepareStatement(sql);
				
				ResultSet rs = ps.executeQuery();
				lista = new LinkedList<Contactos>();
				while (rs.next()) {
					Contactos contactos = new Contactos();
					contactos.setId(rs.getInt(1));
					contactos.setNombres(rs.getString(2));
					contactos.setCelular(rs.getInt(3));
					contactos.setCorreo(rs.getString(4));
					
					lista.add(contactos);
				}
				ps.close();
				
			} catch (SQLException e) {
				setMensaje("Problemas para listar: " + e.getMessage());
			} finally {
				try {
					cn.close();
				} catch (SQLException ex) {
					setMensaje(ex.getMessage());
				}
			}
		} else {
			setMensaje("Error en conexion: " + db.getMessage());
		}
		
		return lista;
	}

	@Override
	public void contactosIns(Contactos contactos) {
		String sql = "INSERT INTO contactos(nombres, celular, correo) VALUES(?,?,?)";
		
		Connection cn = db.getConnection();
		if (cn != null) {
			try {
				PreparedStatement ps = cn.prepareStatement(sql);
				
				ps.setString(1, contactos.getNombres());
				ps.setInt(2, contactos.getCelular());
				ps.setString(3, contactos.getCorreo());
				
				int exec = ps.executeUpdate();
				
				if (exec == 0) {
					throw new SQLException();
				}
				ps.close();
				
			} catch (SQLException e) {
				setMensaje("Problemas para insertar: " + e.getMessage());
			} finally {
				try {
					cn .close();
				} catch (SQLException ex) {
					setMensaje(ex.getMessage());
				}
			}
		} else {
			setMensaje("Error en conexion: " + db.getMessage());
		}
		
	}

	@Override
	public Contactos contactosFnd(Integer id) {
		Contactos contactos = null;
		
		String sql = "SELECT id, nombres, celular, correo FROM contactos WHERE id=?";
		
		Connection cn = db.getConnection();
		
		if (cn != null) {
			try {
				PreparedStatement ps = cn.prepareStatement(sql);
				
				ps.setInt(1, id);
				
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					contactos = new Contactos();
					
					contactos.setId(rs.getInt(1));
					contactos.setNombres(rs.getString(2));
					contactos.setCelular(rs.getInt(3));
					contactos.setCorreo(rs.getString(4));
				}
				ps.close();
				
			} catch (SQLException e) {
				setMensaje("Problemas para consultar: " + e.getMessage());
			} finally {
				try {
					cn.close();
				} catch (SQLException ex) {
					setMensaje(ex.getMessage());
				}
			}
		} else {
			setMensaje("Error en conexion: " + db.getMessage());
		}
		return contactos;
	}

	@Override
	public void contactosUpd(Contactos contactos) {
		String sql = "UPDATE contactos SET nombres=?, celular=?, correo=? WHERE id=?";
		
		Connection cn = db.getConnection();
		if (cn != null) {
			try {
				PreparedStatement ps = cn.prepareStatement(sql);
				
				ps.setString(1, contactos.getNombres());
				ps.setInt(2, contactos.getCelular());
				ps.setString(3, contactos.getCorreo());
				ps.setInt(4, contactos.getId());
				
				int exec = ps.executeUpdate();
				
				if (exec == 0) {
					throw new SQLException();
					
				}
				ps.close();
				
			} catch (SQLException e) {
				setMensaje("Problemas para actualizar: " + e.getMessage());
			} finally {
				try {
					cn .close();
				} catch (SQLException ex) {
					setMensaje(ex.getMessage());
				}
			}
		} else {
			setMensaje("Error en conexion: " + db.getMessage());
		}
		
	}

	@Override
	public void contactosDel(Integer id) {
		String sql = "DELETE FROM contactos WHERE id=?";
		
		Connection cn = db.getConnection();
		if (cn != null) {
			try {
				PreparedStatement ps = cn.prepareStatement(sql);

				ps.setInt(1, id);
				
				int exec = ps.executeUpdate();
				
				if (exec == 0) {
					throw new SQLException();
				}
				ps.close();
				
			} catch (SQLException e) {
				setMensaje("Problemas para eliminar: " + e.getMessage());
			} finally {
				try {
					cn .close();
				} catch (SQLException ex) {
					setMensaje(ex.getMessage());
				}
			}
		} else {
			setMensaje("Error en conexion: " + db.getMessage());
		}
		
	}

	@Override
	public String getMensaje() {
		return mensaje;
	}
	
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
}