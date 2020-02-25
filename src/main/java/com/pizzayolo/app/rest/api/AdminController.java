package com.pizzayolo.app.rest.api;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pizzayolo.app.data.model.Allergene;
import com.pizzayolo.app.data.model.Ingredient;
import com.pizzayolo.app.data.model.Pate;
import com.pizzayolo.app.data.model.Sauce;
import com.pizzayolo.app.data.repo.AllergeneRepository;
import com.pizzayolo.app.data.repo.IngredientRepository;
import com.pizzayolo.app.data.repo.PateRepository;
import com.pizzayolo.app.data.repo.SauceRepository;

@RestController
@RequestMapping("/admin")
public class AdminController {

	private AllergeneRepository allergeneRepository;
	private SauceRepository sauceRepository;
	private PateRepository pateRepository;
	private IngredientRepository ingredientRepository;

	@Autowired
	public void setAllergeneRepository(AllergeneRepository allergeneRepository) {
		this.allergeneRepository = allergeneRepository;
	}

	@Autowired
	public void setSauceRepository(SauceRepository sauceRepository) {
		this.sauceRepository = sauceRepository;
	}

	@Autowired
	public void setPateRepository(PateRepository pateRepository) {
		this.pateRepository = pateRepository;
	}

	@Autowired
	public void setIngredientRepository(IngredientRepository ingredientRepository) {
		this.ingredientRepository = ingredientRepository;
	}

	@GetMapping(value = "/init")
	public ResponseEntity<String> init() {

		Map<String, Allergene> allergenes = initAllergens();
		initSizes(allergenes);
		initBases(allergenes);
		initIngredients(allergenes);

		return ResponseEntity.ok("init done");

	}

	private Map<String, Allergene> initAllergens() {

		Iterable<Allergene> allergenes = null;

		if (allergeneRepository.count() != 0) {
			allergenes = allergeneRepository.findAll();

		} else {

			List<Allergene> tmpAlgs = new ArrayList<>();
			tmpAlgs.add(buildAllergene("gluten", "Gluten", ""));
			tmpAlgs.add(buildAllergene("crustacean", "Crustacean", ""));
			tmpAlgs.add(buildAllergene("egg", "Egg", ""));
			tmpAlgs.add(buildAllergene("fish", "Fish", ""));
			tmpAlgs.add(buildAllergene("peanut", "Peanut", ""));
			tmpAlgs.add(buildAllergene("soybean", "Soybean", ""));
			tmpAlgs.add(buildAllergene("milk", "Milk", ""));
			tmpAlgs.add(buildAllergene("nut", "Nut", ""));
			tmpAlgs.add(buildAllergene("celery", "Celery", ""));
			tmpAlgs.add(buildAllergene("mustard", "Mustard", ""));
			tmpAlgs.add(buildAllergene("sesame", "Sesame", ""));
			tmpAlgs.add(buildAllergene("sulphite", "Sulphite", ""));
			tmpAlgs.add(buildAllergene("lupin", "Lupin", ""));
			tmpAlgs.add(buildAllergene("mollusc", "Mollusc", ""));

			allergenes = allergeneRepository.saveAll(tmpAlgs);
		}

		return StreamSupport.stream(allergenes.spliterator(), false)
				.collect(Collectors.toMap(Allergene::getCode, Function.identity()));

	}

	private Allergene buildAllergene(String code, String label, String url) {
		Allergene allergene = new Allergene();
		allergene.setCode(code);
		allergene.setLibelle(label);
		allergene.setUrl(url);
		return allergene;
	}

	private void initSizes(Map<String, Allergene> allergenes) {

		if (pateRepository.count() != 0) {
			return;
		}

		List<Pate> pates = new ArrayList<>();
		pates.add(buildPate("small", "Small", "", 10f, 1f, allergenes, "gluten", "soybean", "milk"));
		pates.add(buildPate("medium", "Medium", "", 15f, 1.5f, allergenes, "gluten", "soybean", "milk"));
		pates.add(buildPate("large", "Large", "", 20f, 2f, allergenes, "gluten", "soybean", "milk"));
		pateRepository.saveAll(pates);

	}

	private Pate buildPate(String code,
			String name,
			String url,
			float price,
			float coef,
			Map<String, Allergene> allAllergens,
			String... allergenCodes) {
		Pate pate = new Pate();
		pate.setCode(code);
		pate.setNom(name);
		pate.setUrl(url);
		pate.setPrix(BigDecimal.valueOf(price));
		pate.setCoefficient(BigDecimal.valueOf(coef));
		pate.setAllergenes(retrieveAllergens(allAllergens, allergenCodes));
		return pate;
	}

	private void initBases(Map<String, Allergene> allergenes) {

		if (sauceRepository.count() != 0) {
			return;
		}

		List<Sauce> sauces = new ArrayList<>();
		sauces.add(buildSauce("tomatosauce", "Tomato", "", 1f, allergenes, "milk", "sulphite"));
		sauces.add(buildSauce("whitesauce", "White", "", 2f, allergenes, "egg", "milk"));
		sauceRepository.saveAll(sauces);

	}

	private Sauce buildSauce(String code,
			String name,
			String url,
			float price,
			Map<String, Allergene> allAllergens,
			String... allergenCodes) {
		Sauce sauce = new Sauce();
		sauce.setCode(code);
		sauce.setNom(name);
		sauce.setUrl(url);
		sauce.setPrix(BigDecimal.valueOf(price));
		sauce.setAllergenes(retrieveAllergens(allAllergens, allergenCodes));
		return sauce;
	}

	private void initIngredients(Map<String, Allergene> allergenes) {

		if (ingredientRepository.count() != 0) {
			return;
		}

		List<Ingredient> ingredients = new ArrayList<>();
		ingredients.add(buildIngredient("bacon", "Bacon", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("beef", "Beef", "", 1.5f, allergenes, "soybean"));
		ingredients.add(buildIngredient("chicken", "Chicken", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("ham", "Ham", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("blackolives", "Black Olives", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("onions", "Fried Onions", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("peppers", "Peppers", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("mushrooms", "Mushrooms", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("pineapple", "Pineapple", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("emmental", "Emmental", "", 1.5f, allergenes, "milk"));
		ingredients.add(buildIngredient("mozzarella", "Mozzarella", "", 1.5f, allergenes, "milk"));
		ingredients.add(buildIngredient("tomato", "Tomato", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("tuna", "Tuna", "", 1.5f, allergenes, "fish"));
		ingredients.add(buildIngredient("jalapenos", "Jalapeños", "", 1.5f, allergenes));
		ingredients.add(buildIngredient("pepperoni", "Pepperoni", "", 1.5f, allergenes, "mustard"));
		ingredientRepository.saveAll(ingredients);

	}

	private Ingredient buildIngredient(String code,
			String name,
			String url,
			float price,
			Map<String, Allergene> allAllergens,
			String... allergenCodes) {
		Ingredient ingredient = new Ingredient();
		ingredient.setCode(code);
		ingredient.setNom(name);
		ingredient.setUrl(url);
		ingredient.setPrix(BigDecimal.valueOf(price));
		ingredient.setAllergenes(retrieveAllergens(allAllergens, allergenCodes));
		return ingredient;
	}

	private Set<Allergene> retrieveAllergens(Map<String, Allergene> allAllergens, String[] allergenCodes) {

		Set<Allergene> allergens = new HashSet<>();

		if (allergenCodes != null) {
			for (String code : allergenCodes) {
				allergens.add(allAllergens.get(code));
			}
		}

		return allergens;
	}

}
