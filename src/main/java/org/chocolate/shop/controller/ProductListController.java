package org.chocolate.shop.controller;

import java.util.List;

import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.ProductDAOImpl;
import org.chocolate.shop.entity.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductListController {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private ProductDAOImpl productDAO = ProductDAOImpl.getInstance(connection);

	@RequestMapping(value = "/productlist", method = RequestMethod.GET)
	public String products(final Model model) {
		final List<Product> list = productDAO.getAll();
		model.addAttribute("list", list);
		return "productlist";
	}

	@RequestMapping(value = "/deleteproduct", method = RequestMethod.GET)
	public String deleteProduct(@RequestParam(value = "id", required = true) final String id) {
		productDAO.delete(productDAO.readByID(id));
		return "redirect:/productlist";
	}

	@RequestMapping(value = "/editproduct", method = RequestMethod.GET)
	public String editProduct(@RequestParam(value = "id", required = true) final String id, final Model model) {
		final Product product = productDAO.readByID(id);
		model.addAttribute("product", product);
		return "editProduct";
	}

	@RequestMapping(value = "/editproduct", method = RequestMethod.POST)
	public String saveEdit(@ModelAttribute("product") final Product product, final Model model) {
		productDAO.update(product);
		model.addAttribute("product", product);
		model.addAttribute("msg", "Product changed");
		return "productView";
	}
}
