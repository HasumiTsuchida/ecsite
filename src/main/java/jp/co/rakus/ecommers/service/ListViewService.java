package jp.co.rakus.ecommers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.ecommers.domain.Cinema;
import jp.co.rakus.ecommers.repository.CinemaRepository;

/**
 * 
 * @author yusuke.nakano
 *
 */
@Service
public class ListViewService {
	
	/** CinemaRepositoryを利用するためのDI */
	@Autowired
	private CinemaRepository repository;
	
	/**
	 * @return
	 */
	public List<Cinema> findAll(){
		return repository.findAll();
	}
	
}