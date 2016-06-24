package jp.co.rakus.ecommers.web;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.rakus.ecommers.domain.LoginUser;
import jp.co.rakus.ecommers.domain.User;
import jp.co.rakus.ecommers.service.CartService;

/**
 * カートを操作するコントローラ.
 * 
 * @author takeshi.fujimoto
 *
 */
@Controller
@Transactional
@RequestMapping(value = "/cinemaShop")
public class CartController {
	/** Longに変換した時に桁あふれせずに変換できる16進数の最大桁数 */
	private static final int LONG_DIGIT = 15;
	@Autowired
	private CartService service;
	@Autowired
	private HttpSession session;

	/**
	 * カートに商品を追加
	 * 
	 * @param principal
	 * @param form
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/insert")
	public String insertCart(Principal principal, InsertForm form, Model model) {
		// principalからユーザーの情報を受け取るための操作
		LoginUser loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
		User user = loginUser.getUser();
		service.insertCart(user, form);
		return "redirect:/cinemaShop/view";
	}

	/**
	 * カート内の商品一覧を表示
	 * 
	 * @param principal
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/view")
	public String viewCart(Principal principal, @CookieValue("JSESSIONID") String cookie, Model model) {
		// principalからユーザーの情報を受け取るための操作
		User user;
		if (principal == null) {
			user = new User();
			long id;
			try {
				id = Long.parseLong(cookie.substring(cookie.length() - LONG_DIGIT), 16);
			} catch (NumberFormatException e) {
				e.printStackTrace();
				id = 0L;
			}
			user.setId(id);
			user.setName("ゲスト");
		} else {
			LoginUser loginUser = (LoginUser) ((Authentication) principal).getPrincipal();
			if (loginUser.getCookieValue() == null) {
				loginUser.setCookieValue((String)session.getAttribute("guestid"));
			}
			user = loginUser.getUser();
		}
		CartListPage cartPage = service.findAllCart(user);
		if (cartPage == null) {
			model.addAttribute("cartPage", cartPage);
			return "viewShoppingCart";
		} else {
			model.addAttribute("cartPage", cartPage);
			return "viewShoppingCart";
		}
	}

	/**
	 * カート内の商品を削除
	 * 
	 * @param orderCinemaId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public String deleteCart(@RequestParam long orderCinemaId, Model model) {
		service.deleteCart(orderCinemaId);
		return "redirect:/cinemaShop/view";
	}

}
