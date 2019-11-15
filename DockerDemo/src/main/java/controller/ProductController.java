package main.java.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import main.java.model.Product;

@RestController
@Scope(scopeName=BeanDefinition.SCOPE_PROTOTYPE)

@RequestMapping("/product")
public class ProductController {

	DataSource dataSource;
	JdbcTemplate jdbcTemplate;
	
	private final String addProduct = "INSERT INTO PRODUCT VALUES (? , ?)";
	private final String getAllProducts = "SELECT * FROM PRODUCT";
	private final String getProduct = "SELECT * FROM PRODUCT WHERE PRODUCTID = ?";
	private final String updateProduct = "UPDATE PRODUCT SET PRODUCTNAME = ? WHERE PRODUCTID = ?";
	
	private static Logger logger = LoggerFactory.getLogger(ProductController.class);
	public DataSource getDataSource() {
		return dataSource;
	}
	@Autowired
	public void setDataSource(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value="/getAll" , method=RequestMethod.GET , produces="application/json")
	public  List<Product> getAllProducts(){
		List<Product> temp = new ArrayList<>();
		temp = jdbcTemplate.query(getAllProducts,new BeanPropertyRowMapper(Product.class));
		return temp;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value="/{productId}" , method=RequestMethod.GET , produces="application/json")
	public  String getProduct(@PathVariable("productId") int productId){
		Product product = null;
		try{
			product = (Product) jdbcTemplate.queryForObject(getProduct, new Object[]{productId},  new BeanPropertyRowMapper(Product.class));
		}catch(DataAccessException | NumberFormatException e){
			logger.error("Record does not exist" , ProductController.class  , "getProduct()");
			e.printStackTrace();
		}catch(Exception e){
			logger.error("Exception occured in " , ProductController.class  , "getProduct()");
			e.printStackTrace();
		}
		return (null != product) ? product.toString() : "Product Record does not exist";
		
	}
	
	@RequestMapping( value="/add" ,method=RequestMethod.POST  ,produces="application/json")
	public  String addProduct(@RequestBody Product product ){
		int result = 0;
		try{
			if (null!= product){
				result = jdbcTemplate.update(addProduct, new Object[]{product.getProductId() , product.getProductName()});
			}
		logger.info("Number of rows inserted = " + result);
		
		}catch(DataAccessException | NumberFormatException e){
			logger.error(e.getMessage() , ProductController.class  , "addProduct()");
			e.printStackTrace();
		}catch(Exception e){
			logger.error("Exception occured in " , ProductController.class  , "addProduct()");
			e.printStackTrace();
		}
		return (result > 0) ? "successfully added " : "error in adding";
		
	}
	
	@RequestMapping(value="/update/{id}/{name}" , method=RequestMethod.PUT,produces="application/json")
	public  String updateProduct(@PathVariable("id") int productId , @PathVariable("name")String name){
		int result = 0;
		try{
			result = jdbcTemplate.update(updateProduct, new Object[] {name , productId});
		}catch(DataAccessException | NumberFormatException e){
			logger.error(e.getMessage() , ProductController.class  , "updateProduct()");
			e.printStackTrace();
		}catch(Exception e){
			logger.error("Exception occured in " , ProductController.class  , "updateProduct()");
			e.printStackTrace();
		}
		return (result > 0) ? "successfully updated " : "error in updating";
		
	}
	
}
