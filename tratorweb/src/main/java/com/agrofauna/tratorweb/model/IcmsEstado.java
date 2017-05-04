package com.agrofauna.tratorweb.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="icms_estado")
@NamedQueries({
	@NamedQuery(name="IcmsEstado.findAll", query="SELECT r FROM IcmsEstado r"),
	@NamedQuery(name="IcmsEstado.pegaCalculo", query="SELECT r FROM IcmsEstado r WHERE r.estadoOrigem.idEstado=:idEstadoOrigem AND r.estadoDestino.idEstado=:idEstadoDestino")
})
public class IcmsEstado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_icms_estado")
	private long idIcmsEstado;
	
	@Temporal(TemporalType.DATE)
	@Column(name="dt_cadastro")
	private Date dtCadastro;
	
	@Column(name="nr_alicota")
	private double nrAlicota;
	
	@Column(name="id_usuario")
	private int idUsuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_icms_reducao")
	private IcmsReducao icmsReducao;
		
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_origem")
	private Estado estadoOrigem;

	//bi-directional many-to-one association to 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="id_estado_destino")	
	private Estado estadoDestino;
		
	@OneToMany(mappedBy="icmsEstado", fetch = FetchType.LAZY)
	private List<Pedido> pedidos = new ArrayList<>();
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public long getIdIcmsEstado() {
		return idIcmsEstado;
	}

	public void setIdIcmsEstado(long idIcmsEstado) {
		this.idIcmsEstado = idIcmsEstado;
	}

	public Date getDtCadastro() {
		return dtCadastro;
	}

	public void setDtCadastro(Date dtCadastro) {
		this.dtCadastro = dtCadastro;
	}

	public double getNrAlicota() {
		return nrAlicota;
	}

	public void setNrAlicota(double nrAlicota) {
		this.nrAlicota = nrAlicota;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public IcmsReducao getIcmsReducao() {
		return icmsReducao;
	}

	public void setIcmsReducao(IcmsReducao icmsReducao) {
		this.icmsReducao = icmsReducao;
	}
	
	public Estado getEstadoOrigem() {
		return estadoOrigem;
	}

	public void setEstadoOrigem(Estado estadoOrigem) {
		this.estadoOrigem = estadoOrigem;
	}

	public Estado getEstadoDestino() {
		return estadoDestino;
	}

	public void setEstadoDestino(Estado estadoDestino) {
		this.estadoDestino = estadoDestino;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idIcmsEstado ^ (idIcmsEstado >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IcmsEstado other = (IcmsEstado) obj;
		if (idIcmsEstado != other.idIcmsEstado)
			return false;
		return true;
	}
}
