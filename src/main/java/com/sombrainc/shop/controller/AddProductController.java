package org.chocolate.shop.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.apache.log4j.Logger;
import org.chocolate.shop.connectionmanager.ConnectionManager;
import org.chocolate.shop.connectionmanager.DataBase;
import org.chocolate.shop.dao.impl.ProductDAOImpl;
import org.chocolate.shop.entity.Product;
import org.chocolate.shop.log.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AddProductController {

	private ConnectionManager connection = ConnectionManager.getInstance(DataBase.LOCALDB);
	private ProductDAOImpl productDAO = ProductDAOImpl.getInstance(connection);
	private static Logger logger = Log.init("AddProductController");

	@RequestMapping(value = "/product", method = RequestMethod.GET)
	public ModelAndView showForm() {
		return new ModelAndView("product", "product", new Product());
	}

	@RequestMapping(value = "/addproduct", method = RequestMethod.POST)
	public String Submit(@ModelAttribute("product") final Product product, @RequestParam("file") final MultipartFile file, final BindingResult result,
			final ModelMap model) {
		if (result.hasErrors()) {
			return "error";
		}
		File serverFile = null;
		if (!file.isEmpty()) {
			try {
				final byte[] bytes = file.getBytes();

				final String rootPath = System.getProperty("catalina.home");
				final File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists()) {
					dir.mkdirs();
				}
				serverFile = new File(dir.getAbsolutePath() + File.separator + file.getOriginalFilename());
				final BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				logger.info("Server File Location=" + serverFile.getAbsolutePath());
			} catch (final Exception e) {
				logger.error("failed to upload " + file.getOriginalFilename() + " => " + e.getMessage(), e);
			}
		} else {
			logger.error("You failed to upload " + file.getOriginalFilename() + " because the file was empty.");
		}
		model.addAttribute("name", product.getName());
		model.addAttribute("description", product.getDescription());
		model.addAttribute("price", product.getPrice());
		model.addAttribute("photo", serverFile.getAbsolutePath());
		model.addAttribute("msg", "Product added");
		productDAO.create(product);
		return "productView";
	}
}
