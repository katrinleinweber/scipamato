package ch.difty.sipamato;

import org.apache.wicket.authroles.authentication.AbstractAuthenticatedWebSession;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class LoginPage extends WebPage {

    private static final long serialVersionUID = 1L;

    public LoginPage(PageParameters parameters) {
        super(parameters);

        if (((AbstractAuthenticatedWebSession) getSession()).isSignedIn()) {
            continueToOriginalDestination();
        }
        add(new LoginForm("loginForm"));
    }

    private class LoginForm extends Form<LoginForm> {
        private static final long serialVersionUID = 1L;

        private String username;

        private String password;

        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel<>(this));
            add(new FeedbackPanel("feedback"));
            add(new RequiredTextField<String>("username"));
            add(new PasswordTextField("password"));
        }

        @Override
        protected void onSubmit() {
            AuthenticatedWebSession session = AuthenticatedWebSession.get();
            if (session.signIn(username, password)) {
                setResponsePage(SipamatoHomePage.class);
            } else {
                error("Login failed");
            }
        }
    }
}
