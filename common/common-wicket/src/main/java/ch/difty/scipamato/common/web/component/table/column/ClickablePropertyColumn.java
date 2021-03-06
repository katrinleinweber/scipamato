package ch.difty.scipamato.common.web.component.table.column;

import org.apache.wicket.model.IModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import ch.difty.scipamato.common.web.component.SerializableConsumer;

/**
 * ClickablePropertyColumn, a lambdified version of the one provided in
 * {@code Apache Wicket Cookbook} - thanks to Igor Vaynberg.
 *
 * @param <T>
 *     the type of the object that will be rendered in this column's
 *     cells
 * @param <S>
 *     the type of the sort property
 * @author u.joss
 */
@SuppressWarnings("SpellCheckingInspection")
public class ClickablePropertyColumn<T, S> extends AbstractClickablePropertyColumn<T, S> {
    private static final long serialVersionUID = 1L;

    private final SerializableConsumer<IModel<T>> consumer;

    public ClickablePropertyColumn(@NotNull final IModel<String> displayModel, @NotNull final String property,
        @NotNull final SerializableConsumer<IModel<T>> consumer) {
        this(displayModel, null, property, consumer, false);
    }

    public ClickablePropertyColumn(@NotNull final IModel<String> displayModel, @Nullable final S sort,
        @NotNull final String property, @NotNull final SerializableConsumer<IModel<T>> consumer) {
        this(displayModel, sort, property, consumer, false);
    }

    public ClickablePropertyColumn(@NotNull final IModel<String> displayModel, @Nullable final S sort,
        @NotNull final String property, @NotNull final SerializableConsumer<IModel<T>> consumer,
        final boolean inNewTab) {
        super(displayModel, sort, property, inNewTab);
        this.consumer = consumer;
    }

    @Override
    protected void onClick(final IModel<T> clicked) {
        consumer.accept(clicked);
    }

}