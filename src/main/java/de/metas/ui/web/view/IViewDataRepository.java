package de.metas.ui.web.view;

import java.util.Collection;
import java.util.List;

import org.adempiere.exceptions.DBException;

import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.WindowId;
import de.metas.ui.web.window.descriptor.filters.DocumentFilterDescriptorsProvider;
import de.metas.ui.web.window.model.filters.DocumentFilter;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * View data repository.
 * This repository is responsible for fetching {@link IViewRow} or even their models.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
interface IViewDataRepository
{
	String getTableName();

	String getSqlWhereClause(ViewId viewId, Collection<DocumentId> rowIds);
	
	DocumentFilterDescriptorsProvider getViewFilterDescriptors();

	IViewRow retrieveById(ViewEvaluationCtx viewEvalCtx, ViewId viewId, DocumentId rowId);

	List<IViewRow> retrievePage(ViewEvaluationCtx viewEvalCtx, ViewRowIdsOrderedSelection orderedSelection, int firstRow, int pageLength) throws DBException;

	<T> List<T> retrieveModelsByIds(ViewId viewId, Collection<DocumentId> rowIds, Class<T> modelClass);

	IViewRowIdsOrderedSelectionFactory createOrderedSelectionFactory(ViewEvaluationCtx viewEvalCtx);

	ViewRowIdsOrderedSelection createOrderedSelection(ViewEvaluationCtx viewEvalCtx, WindowId windowId, List<DocumentFilter> filters);
}