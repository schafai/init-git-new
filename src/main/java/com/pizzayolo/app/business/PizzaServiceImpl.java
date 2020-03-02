package com.pizzayolo.app.business;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pizzayolo.app.data.model.*;
import com.pizzayolo.app.data.repo.IngredientRepository;
import com.pizzayolo.app.data.repo.PateRepository;
import com.pizzayolo.app.data.repo.PizzaRepository;
import com.pizzayolo.app.data.repo.SauceRepository;
import com.pizzayolo.app.rest.model.*;

@Service
public class PizzaServiceImpl implements PizzaService {

	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private PizzaRepository pizzaRepository;
	private IngredientRepository ingredientRepository;

	@Autowired
	public void setSauceRepository(SauceRepository sauceRepository) {
		this.sauceRepository = sauceRepository;
	}

	@Autowired
	public void setPateRepository(PateRepository pateRepository) {
		this.pateRepository = pateRepository;
	}

	@Autowired
	public void setPizzaRepository(PizzaRepository pizzaRepository) {
		this.pizzaRepository = pizzaRepository;
	}

	@Autowired
	public void setIngredientRepository(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public OrderResponseData orderPizza(@Valid OrderRequest order) {

		Pizza pizza = new Pizza();
		Set<Allergene> allergensInPizza = new HashSet<>();

		pizza.setNom(generatePizzaName(order));
		BigDecimal coef = handleSize(pizza, allergensInPizza, order);
		handleBase(pizza, allergensInPizza, order, coef);
		handleIngredients(pizza, allergensInPizza, order, coef);
		controlAllergens(allergensInPizza, order);

		pizzaRepository.save(pizza);

		return buildResponse(pizza, allergensInPizza);

	}

	private String generatePizzaName(OrderRequest order) {
		return UUID.randomUUID().toString();
//		return RandomStringUtils.randomAlphanumeric(36);
	}

	/**
	 * Handle the pizza base for the given order. Initialize the price of the given pizza and returns the multiplier for
	 * subsequent calculation. Also update the list of allergens.
	 * 
	 * @param pizza
	 * @param allergensInPizza
	 * @param order
	 * @return
	 */
	private BigDecimal handleSize(Pizza pizza, Set<Allergene> allergensInPizza, OrderRequest order)
			throws NoSuchSizeException {

		String size = order.getPizza().getSize();
		Pate pate = pateRepository.findById(size).orElseThrow(() -> new NoSuchSizeException(size));

		pizza.setPrix(pate.getPrix());
		pizza.setPate(pate);

		allergensInPizza.addAll(pate.getAllergenes());

		return pate.getCoefficient();

	}

	/**
	 * Handle the pizza size for the given order. Update the price of the given pizza and update the given list of
	 * allergens.
	 * 
	 * @param pizza
	 * @param allergensInPizza
	 * @param order
	 * @param coef
	 */
	private void handleBase(Pizza pizza, Set<Allergene> allergensInPizza, OrderRequest order, BigDecimal coef)
			throws NoSuchBaseException {

		String base = order.getPizza().getBase();
		Sauce sauce = sauceRepository.findById(base).orElseThrow(() -> new NoSuchBaseException(base));

		pizza.setPrix(pizza.getPrix().add(sauce.getPrix().multiply(coef)));
		pizza.setSauce(sauce);

		allergensInPizza.addAll(sauce.getAllergenes());

	}

	/**
	 * Handle the pizza ingredients for the given order. Update the price of the given pizza and update the given list
	 * of allergens.
	 * 
	 * @param pizza
	 * @param allergensInPizza
	 * @param order
	 * @param coef
	 */
	private void handleIngredients(Pizza pizza, Set<Allergene> allergensInPizza, OrderRequest order, BigDecimal coef) {

		Set<String> ingredientCodes = order.getPizza()
				.getIngredients()
				.stream()
				.map(IngredientCode::getCode)
				.collect(Collectors.toSet());

		for (Ingredient ingredient : ingredientRepository.findAllById(ingredientCodes)) {
			pizza.setPrix(pizza.getPrix().add(ingredient.getPrix().multiply(coef)));
			pizza.getIngredients().add(ingredient);
			allergensInPizza.addAll(ingredient.getAllergenes());
		}

	}

	private void controlAllergens(Set<Allergene> allergensInPizza, OrderRequest order) throws NotConformException {
		Set<String> allergensToAvoid = order.getAllergens()
				.stream()
				.map(AllergenCode::getCode)
				.collect(Collectors.toSet());

		Set<String> incompatibleAllergens = allergensInPizza.stream()
				.filter(alg -> allergensToAvoid.contains(alg.getCode()))
				.map(Allergene::getLibelle)
				.collect(Collectors.toSet());

		if (!incompatibleAllergens.isEmpty()) {
			throw new NotConformException(incompatibleAllergens);
		}

	}

	private OrderResponseData buildResponse(Pizza pizza, Set<Allergene> allergensInPizza) {
		OrderResponseData response = new OrderResponseData();
		response.setAllergens(allergensInPizza.stream()
				.map(a -> new AllergenLabel().label(a.getLibelle()))
				.collect(Collectors.toList()));
		response.setAmount(pizza.getPrix());
//		response.setCompliances(compliances); TODO
		return response;
	}

}
