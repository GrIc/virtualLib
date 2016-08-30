package com.oitar.virtuallib.view;

import java.util.List;

import javax.servlet.annotation.WebServlet;

import com.oitar.virtuallib.model.Reference;
import com.oitar.virtuallib.service.ReferenceService;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of a html page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("com.oitar.virtuallib.MyAppWidgetset")
public class MyUI extends UI {

	private ReferenceService service = ReferenceService.getInstance();
	private Grid grid = new Grid();
	private TextField filterText = new TextField();

	@Override
	protected void init(VaadinRequest vaadinRequest) {
		final VerticalLayout layout = new VerticalLayout();
		grid.setColumns("title", "author", "publicationDate");
		filterText.setInputPrompt("Filtrer par titre...");
		filterText.addTextChangeListener(e -> {
			grid.setContainerDataSource(new BeanItemContainer<>(Reference.class, service.findAll(e.getText())));
		});
		Button clearFilterTextBtn = new Button(FontAwesome.TIMES);
		clearFilterTextBtn.setDescription("Remettre le filtre à zéro");
		clearFilterTextBtn.addClickListener(e-> {
			filterText.clear();
			updateList();
		});

		CssLayout filteringLayout = new CssLayout();
		filteringLayout.addComponents(filterText, clearFilterTextBtn);
		filteringLayout.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);

		layout.addComponents(filteringLayout, grid);
		updateList();

		layout.setMargin(true);
		setContent(layout);
	}

	public void updateList() {
		// fetch list of References from service and assign it to Grid
		List<Reference> reference = service.findAll(filterText.getValue());
		grid.setContainerDataSource(new BeanItemContainer<>(Reference.class, reference));
	}

	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
	@VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
	public static class MyUIServlet extends VaadinServlet {
	}
}
