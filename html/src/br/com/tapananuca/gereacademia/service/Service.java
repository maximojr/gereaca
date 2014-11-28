package br.com.tapananuca.gereacademia.service;

import java.util.List;

import com.badlogic.gdx.utils.Array;

public abstract class Service {

	<T extends Object> Array<T> getArrayFromList(List<T> lista){
		
		final Array<T> ret = new Array<T>();
		
		for (T objeto : lista){
			
			ret.add(objeto);
		}
		
		return ret;
	}
}
