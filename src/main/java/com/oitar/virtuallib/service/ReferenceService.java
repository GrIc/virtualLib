package com.oitar.virtuallib.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.oitar.virtuallib.model.Book;
import com.oitar.virtuallib.model.Reference;

/**
 * An in memory dummy "database" for the example purposes. In a typical Java app
 * this class would be replaced by e.g. EJB or a Spring based service class.
 * <p>
 * In demos/tutorials/examples, get a reference to this service class with
 * {@link ReferenceService#getInstance()}.
 */
public class ReferenceService {

	private static ReferenceService instance;
	private static final Logger LOGGER = Logger.getLogger(ReferenceService.class.getName());

	private final HashMap<Long, Reference> references = new HashMap<>();
	private long nextId = 0;

	private ReferenceService() {
	}

	/**
	 * @return a reference to an example facade for Reference objects.
	 */
	public static ReferenceService getInstance() {
		if (instance == null) {
			instance = new ReferenceService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Reference objects.
	 */
	public synchronized List<Reference> findAll() {
		return findAll(null);
	}

	/**
	 * Finds all Reference's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Reference objects
	 */
	public synchronized List<Reference> findAll(String stringFilter) {
		ArrayList<Reference> arrayList = new ArrayList<>();
		for (Reference reference : references.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| reference.toSearchString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(reference.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ReferenceService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Reference>() {

			@Override
			public int compare(Reference o1, Reference o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		return arrayList;
	}

	/**
	 * Finds all Reference's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Reference objects
	 */
	public synchronized List<Reference> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Reference> arrayList = new ArrayList<>();
		for (Reference reference : references.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| reference.toSearchString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(reference.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(ReferenceService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Reference>() {

			@Override
			public int compare(Reference o1, Reference o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all References in the system
	 */
	public synchronized long count() {
		return references.size();
	}

	/**
	 * Deletes a Reference from a system
	 *
	 * @param value
	 *            the Reference to be deleted
	 */
	public synchronized void delete(Reference value) {
		references.remove(value.getId());
	}

	/**
	 * Persists or updates Reference in the system. Also assigns an identifier
	 * for new Reference instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Reference entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"Reference is null. Are you sure you have connected your form to the application as described in tutorial chapter 7?");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Reference) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		references.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (findAll().isEmpty()) {
			final String[] titles = new String[] { "Livre A", "Livre B", "Livre C",
					"Livre D", "Livre E", "Livre F", "Livre G" };
			Random r = new Random(0);
			for (String title : titles) {
				String[] split = title.split(" ");
				Reference c = new Book();
				c.setTitle(title);
				c.setAuthor(split[1]);
				Calendar cal = Calendar.getInstance();
				int daysOld = 0 - r.nextInt(365 * 15 + 365 * 60);
				cal.add(Calendar.DAY_OF_MONTH, daysOld);
				c.setPublicationDate(cal.getTime());
				save(c);
			}
		}
	}
}