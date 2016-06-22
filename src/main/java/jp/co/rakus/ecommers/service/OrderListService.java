package jp.co.rakus.ecommers.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.ecommers.domain.Cinema;
import jp.co.rakus.ecommers.repository.CinemaRepository;
import jp.co.rakus.ecommers.web.CinemaDetailPage;
import jp.co.rakus.ecommers.web.OrderListChildPage;
import jp.co.rakus.ecommers.web.OrderListPage;

/**
 * 注文詳細一覧を表示するためのserviceクラス.
 * 
 * @author yusuke.nakano
 *
 */
@Service
public class OrderListService {

	/** CinemaRepositoryを利用するためのDI */
	@Autowired
	private CinemaRepository repository;
	
	/**
	 * DBへのinsert, updateを行うためのメソッド.
	 * 
	 * @param cinema
	 *            映画のオブジェクト
	 */
	public void save(Cinema cinema) {
		repository.save(cinema);
	}

	/**
	 * DBからfindAllするためのメソッド. 取得してきた映画のリストを別に定義してあるPageクラスに反映させる
	 * 
	 * @return 映画のリストを持つPageクラス
	 */
	public OrderListPage findAll() {
		List<Cinema> cinemaList = repository.findAll();

		OrderListPage page = new OrderListPage();
		List<OrderListChildPage> init = new ArrayList<>();

		page.setCinemaList(init);

		for (Cinema cinema : cinemaList) {
			OrderListChildPage child = new OrderListChildPage();
			BeanUtils.copyProperties(cinema, child);
			page.getCinemaList().add(child);
		}

		return page;
	}

	/**
	 * @param id
	 * @return
	 */
	public Cinema findOne(long id) {
		return repository.findOne(id);
	}

	/**
	 * Cinemaの情報をCinemaDetailPageにコピー.
	 * 
	 * @param cinema
	 *            Cinemaの詳細情報
	 * @return Cinemaの内容をコピーしたCinemaDetailPage
	 */
	public CinemaDetailPage copyCinemaToPage(Cinema cinema) {
		CinemaDetailPage cinemaDetail = new CinemaDetailPage();
		BeanUtils.copyProperties(cinema, cinemaDetail);
		return cinemaDetail;
	}
}