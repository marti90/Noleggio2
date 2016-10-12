package dao;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

import model.Macchina;
import utility.DataSource;

public class MacchinaDAO {
	
	//legge la Macchina dalla tabella MACCHINA attraverso l'id, e ritorna l'oggetto che ha letto
	public Macchina leggiMacchinaConId(int id) {
		
		Macchina m = null;
		String sql = "SELECT * FROM MACCHINA WHERE ID_MACCHINA = ?";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			con = DataSource.getInstance().getConnection();
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			if(res.next()){
				int id_m = res.getInt(1);
				String modello = res.getString(2);
				String targa = res.getString(3);
				
				m = new Macchina(id_m, modello, targa);
			}
		} catch (SQLException | IOException | PropertyVetoException e) {
			e.printStackTrace();
		} finally{
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		return m;
	}

	//Inserisce la macchina nella tabella MACCHINA del Database
	public int inserisciMacchina(String modello, String targa) {
		
		int id_macchina = 0;
		String sql = "INSERT INTO MACCHINA (MODELLO, TARGA) VALUES (?,?)";
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet res = null;
		
		try {
			con = DataSource.getInstance().getConnection();
			pst = con.prepareStatement(sql, new String[]{"ID_MACCHINA"});
			pst.setString(1, modello);
			pst.setString(2, targa);
			pst.executeUpdate();
			
			res = pst.getGeneratedKeys();
			if(res!=null && res.next()){
				id_macchina = res.getInt(1);
			}			
		} catch (SQLException | IOException | PropertyVetoException e) {
			e.printStackTrace();
		} finally{
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		
		return id_macchina;
	}
	
	//aggiorna il modello della macchina all'interno della tabella MACCHINA
	public boolean aggiornaMacchina(String modello, String targa) {
		
		boolean flag = false;
		Connection con = null;
		String sql = "UPDATE MACCHINA SET MODELLO=? WHERE TARGA=?";
		PreparedStatement pst = null;
			
		try {
			con = DataSource.getInstance().getConnection();
			pst = con.prepareStatement(sql);
			pst.setString(1, modello);
			pst.setString(2, targa);
			
			int res = pst.executeUpdate();
			if(res!=0){
				flag = true;
			}
		} catch (SQLException | IOException | PropertyVetoException e) {
			e.printStackTrace();
		} finally{
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}
		return flag;
	}
	
	//cancella la macchina all'interno della tabella MACCHINA
	public boolean cancellaMacchinaConId(int id) {
		
		boolean flag = false;
		Connection con = null;
		String sql = "DELETE FROM MACCHINA WHERE ID_MACCHINA=?";
		PreparedStatement pst = null;
		
		try {
			con = DataSource.getInstance().getConnection();
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			
			int res = pst.executeUpdate();
			if(res!=0){
				flag = true;
			}
		} catch (SQLException | IOException | PropertyVetoException e) {
			e.printStackTrace();
		} finally{
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}	
		return flag;
	}
	
	//legge tutte le macchine assegnate ad una persona
	public Map<Integer, Macchina> getTutteLeMacchineDiUnaPersona(int id){
		
		Macchina m = null;
		Connection con = null;
		String sql = "SELECT MACCHINA.ID_MACCHINA, MACCHINA.MODELLO, MACCHINA.TARGA "
				+ "FROM MACCHINA, PERSONA_MACCHINA "
				+ "WHERE PERSONA_MACCHINA .ID_MACCHINA = MACCHINA.ID_MACCHINA "
				+ "AND PERSONA_MACCHINA .ID_PERSONA = ?";
		PreparedStatement pst = null;
		ResultSet res = null;
		Map<Integer, Macchina> macchinePerPersona = new TreeMap<Integer, Macchina>();
		try {
			con = DataSource.getInstance().getConnection();
			pst = con.prepareStatement(sql);
			pst.setInt(1, id);
			
			res = pst.executeQuery();
			while(res.next()){
					int id_m = res.getInt(1);
					String modello = res.getString(2);
					String targa = res.getString(3);
					
					m = new Macchina(id_m, modello, targa);
					macchinePerPersona.put(m.getId_macchina(), m);
			}
		} catch (SQLException | IOException | PropertyVetoException e) {
			e.printStackTrace();
		} finally{
			if (res != null) try { res.close(); } catch (SQLException e) {e.printStackTrace();}
			if (pst != null) try { pst.close(); } catch (SQLException e) {e.printStackTrace();}
			if (con != null) try { con.close(); } catch (SQLException e) {e.printStackTrace();}
		}	
		return macchinePerPersona;
	}
	
}