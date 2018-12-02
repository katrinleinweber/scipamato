package ch.difty.scipamato.core.web.newsletter.topic;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import ch.difty.scipamato.core.entity.newsletter.NewsletterTopicDefinition;
import ch.difty.scipamato.core.entity.newsletter.NewsletterTopicTranslation;
import ch.difty.scipamato.core.web.common.DefinitionEditTranslationPanel;

@SuppressWarnings("SameParameterValue")
class NewsletterTopicEditTranslationPanel
    extends DefinitionEditTranslationPanel<NewsletterTopicDefinition, NewsletterTopicTranslation> {

    NewsletterTopicEditTranslationPanel(final String id, final IModel<NewsletterTopicDefinition> model) {
        super(id, model);
    }

    @Override
    protected void addColumns(final Item<NewsletterTopicTranslation> item) {
        item.add(new Label("langCode"));
        item.add(new TextField<String>("title"));
    }
}
