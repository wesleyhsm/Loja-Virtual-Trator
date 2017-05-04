package com.agrofauna.tratorweb.controller;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named
@RequestScoped
public class RodapeBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private MapModel simpleModel = new DefaultMapModel();
	//-20.79185, -49.361535
	//-20.792056, -49.36191
	public RodapeBean(){		
        LatLng coord1 = new LatLng(-20.79185,	-49.361535);
        this.simpleModel.addOverlay(new Marker(coord1, "Agro-Fauna Comércio de Insumos Agrícolas LTDA"));
	}

	public MapModel getSimpleModel() {
		return simpleModel;
	}

	public void setSimpleModel(MapModel simpleModel) {
		this.simpleModel = simpleModel;
	}
}
