/*
 * Copyright 2015 Viliam Repan (lazyman)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.difty.scipamato.common.web.component.table.column;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Viliam Repan (lazyman)
 */
public abstract class LinkIconColumn<T extends Serializable> extends AbstractColumn<T, String> {

    private static final long serialVersionUID = 1L;

    public LinkIconColumn(@Nullable IModel<String> displayModel) {
        super(displayModel);
    }

    @Override
    public void populateItem(@NotNull Item<ICellPopulator<T>> cellItem, @NotNull String componentId,
        @NotNull final IModel<T> rowModel) {
        cellItem.add(new LinkIconPanel(componentId, createIconModel(rowModel), createTitleModel(rowModel)) {
            private static final long serialVersionUID = 1L;

            @Override
            protected void onClickPerformed(@NotNull AjaxRequestTarget target) {
                LinkIconColumn.this.onClickPerformed(target, rowModel, getLink());
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                setVisible(shouldBeVisible());
            }
        });
    }

    protected boolean shouldBeVisible() {
        return true;
    }

    /**
     * Override to set the title model
     *
     * @param rowModel
     *     the row model to derive the title model from
     * @return title model
     */
    @Nullable
    protected IModel<String> createTitleModel(@NotNull final IModel<T> rowModel) {
        return null;
    }

    @Nullable
    protected abstract IModel<String> createIconModel(@NotNull final IModel<T> rowModel);

    protected abstract void onClickPerformed(@NotNull AjaxRequestTarget target, @NotNull IModel<T> rowModel,
        @NotNull AjaxLink<Void> link);
}
