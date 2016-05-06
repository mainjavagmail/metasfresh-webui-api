package de.metas.ui.web.vaadin.window.prototype.order.editor;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

import de.metas.ui.web.vaadin.Application;
import de.metas.ui.web.vaadin.window.prototype.order.PropertyDescriptor;
import de.metas.ui.web.vaadin.window.prototype.order.WindowPresenter;
import de.metas.ui.web.vaadin.window.prototype.order.datasource.SaveResult;
import de.metas.ui.web.vaadin.window.prototype.order.model.WindowModel;
import de.metas.ui.web.vaadin.window.prototype.order.propertyDescriptor.gridWindowVO.VOPropertyDescriptorProvider;
import de.metas.ui.web.vaadin.window.prototype.order.service.LocationService;
import de.metas.ui.web.vaadin.window.prototype.order.view.EditorView;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@SuppressWarnings("serial")
public class LocationEditorPopup extends Window
{
	private static final String STYLE = "mf-editor-location-popup";

	@Autowired(required = true)
	private LocationService locationService;

	private LookupValue lookupValue;

	private final WindowPresenter locationPresenter;

	public LocationEditorPopup(final LookupValue lookupValue)
	{
		super();
		Application.autowire(this);

		addStyleName(STYLE);

		setWidth(300, Unit.PIXELS); // FIXME: CSS hardcoded
		setModal(true);
		setCaption("Location");
		setIcon(FontAwesome.LOCATION_ARROW);
		setDraggable(true);
		setResizable(false);

		this.lookupValue = lookupValue;

		final PropertyDescriptor locationPropertyDescriptor = new VOPropertyDescriptorProvider().provideForWindow(121); // FIXME: hardcoded

		this.locationPresenter = new WindowPresenter();

		final EditorView locationView = new EditorView();
		locationPresenter.setView(locationView);
		locationPresenter.setRootPropertyDescriptor(locationPropertyDescriptor);

		final WindowModel locationModel = locationPresenter.getModel();
		locationModel.newRecordAsCopyById(lookupValue == null ? null : lookupValue.getId());

		final Button btnSave = new Button("Save");
		btnSave.addClickListener(event -> saveAndClose());

		final VerticalLayout content = new VerticalLayout(locationPresenter.getViewComponent(), btnSave);
		setContent(content);

		setErrorHandler(locationPresenter);
	}

	public LookupValue getLookupValue()
	{
		return lookupValue;
	}

	private void saveAndClose()
	{
		final SaveResult saveResult = locationPresenter.getModel().saveRecord();
		lookupValue = locationService.findLookupValueById(saveResult.getRecordId());
		close();
	}
}
