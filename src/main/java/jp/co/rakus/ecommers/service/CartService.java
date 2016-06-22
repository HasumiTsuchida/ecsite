package jp.co.rakus.ecommers.service;

import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.rakus.ecommers.domain.Cinema;
import jp.co.rakus.ecommers.domain.LoginUser;
import jp.co.rakus.ecommers.domain.Order;
import jp.co.rakus.ecommers.domain.Cart;
import jp.co.rakus.ecommers.repository.OrderCinemaRepository;
import jp.co.rakus.ecommers.web.CartListChildPage;
import jp.co.rakus.ecommers.web.CartListPage;
import jp.co.rakus.ecommers.web.InsertForm;

/**
 * カートの操作をするServiceクラス.
 * 
 * @author takeshi.fujimoto
 *
 */

@Service
public class CartService {

	@Autowired
	private OrderCinemaRepository repository;
	/**
	 * カートに商品を追加するメソッド.
	 * 
	 * @param principal
	 * @param form
	 */
	@SuppressWarnings("null")
	public void insertCart(Principal principal, InsertForm form) {
		LoginUser loginUser = (LoginUser)principal;
		Order order = repository.searchOrder(loginUser.getUser().getId());
		
		if(order == null){
		Calendar cal = Calendar.getInstance();
		
		order.setOrderNumber("00000000000000");
		order.setStatus(0);
		order.setTotalPrice(0);
		order.setDate((Timestamp)cal.getTime());
		}
		
		repository.insertOrderItem(form);
		
		List<Cart> orderList= repository.findAllOrder(order);
		
		int sum = 0;
		for(Cart cart : orderList){
			Cinema cinema = repository.findOne(cart.getCinemaId());
			sum = sum + cinema.getPrice() * cart.getQuantity();
		}
		
		order.setTotalPrice(sum);
		
		repository.updateOrder(order);
		
	}
	
	/**
	 * カート内の商品一覧表示
	 * 
	 * @param principal
	 * @return　page情報
	 */
	public CartListPage findAllCart(Principal principal) {
		CartListPage page = new CartListPage();
		LoginUser loginUser = (LoginUser)principal;
		Order order = repository.searchOrder(loginUser.getUser().getId());
		List<Cart> cartList = repository.findAllOrder(order);
		if(cartList == null){
			return null;
		}
		for(Cart cart : cartList){
//			CartListPage page;
			CartListChildPage childPage = new CartListChildPage();
			Cinema cinema = repository.findOne(cart.getCinemaId());
			childPage.setTitle(cinema.getTitle());
			childPage.setQuantity(cinema.getPrice());
			childPage.setQuantity(cart.getQuantity());
			page.getCartListChildPage().add(childPage);
		}
		return page;
	}
	
}